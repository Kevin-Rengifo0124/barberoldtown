import asyncio
import os
from contextlib import asynccontextmanager
from dataclasses import dataclass
from typing import AsyncIterator, List, Dict, Any
from datetime import datetime

import asyncpg
from pydantic import BaseModel, Field
from mcp.server.fastmcp import FastMCP, Context

# --- MODELOS DE DATOS ---
class Barbero(BaseModel):
    id: int
    nombre: str
    especialidad: str
    telefono: str
    activo: bool

class TipoCorte(BaseModel):
    id: int
    nombre: str
    precio: float
    duracion_minutos: int
    descripcion: str

class CortesPorBarbero(BaseModel):
    barbero: str
    cortes_disponibles: List[str]

# --- CONTEXTO Y CICLO DE VIDA ---
@dataclass
class AppContext:
    db_pool: asyncpg.Pool

@asynccontextmanager
async def app_lifespan(server: FastMCP) -> AsyncIterator[AppContext]:
    print("🔌 Conectando a PostgreSQL...")
    try:
        pool = await asyncpg.create_pool(
            user=os.getenv("DB_USER", "postgres"),
            password=os.getenv("DB_PASSWORD", "2025"),
            database=os.getenv("DB_NAME", "barberia"),
            host=os.getenv("DB_HOST", "localhost"),
        )
        print("✅ Pool de conexiones creado")
        yield AppContext(db_pool=pool)
    finally:
        if 'pool' in locals() and pool:
            await pool.close()
            print("🔌 Pool cerrado")

# --- SERVIDOR MCP ---
mcp = FastMCP("BarberiaServer", lifespan=app_lifespan)

# --- HERRAMIENTAS ---

@mcp.tool()
async def get_all_barberos(ctx: Context) -> List[Barbero]:
    """
    Devuelve la lista completa de todos los barberos registrados en el sistema.
    """
    pool: asyncpg.Pool = ctx.request_context.lifespan_context.db_pool
    async with pool.acquire() as conn:
        rows = await conn.fetch("SELECT * FROM barberos WHERE activo = true")
        return [Barbero(**row) for row in rows]

@mcp.tool()
async def get_all_tipos_corte(ctx: Context) -> List[TipoCorte]:
    """
    Devuelve todos los tipos de corte disponibles en la barbería.
    """
    pool: asyncpg.Pool = ctx.request_context.lifespan_context.db_pool
    async with pool.acquire() as conn:
        rows = await conn.fetch("SELECT * FROM tipos_corte")
        return [TipoCorte(**row) for row in rows]

@mcp.tool()
async def get_barbero_by_name(ctx: Context, nombre: str) -> Barbero:
    """
    Busca un barbero específico por su nombre.
    
    Args:
        nombre: El nombre del barbero a buscar
    """
    pool: asyncpg.Pool = ctx.request_context.lifespan_context.db_pool
    async with pool.acquire() as conn:
        row = await conn.fetchrow(
            "SELECT * FROM barberos WHERE LOWER(nombre) LIKE LOWER($1) AND activo = true",
            f"%{nombre}%"
        )
        if row:
            return Barbero(**row)
        raise ValueError(f"No se encontró el barbero: {nombre}")

@mcp.tool()
async def get_cortes_por_barbero(ctx: Context) -> List[CortesPorBarbero]:
    """
    Muestra qué tipos de corte hay disponibles para cada barbero.
    Esta herramienta es ideal cuando preguntan "qué cortes hace cada barbero".
    """
    pool: asyncpg.Pool = ctx.request_context.lifespan_context.db_pool
    async with pool.acquire() as conn:
        barberos_rows = await conn.fetch("SELECT * FROM barberos WHERE activo = true")
        cortes_rows = await conn.fetch("SELECT nombre FROM tipos_corte")
        
        resultado = []
        cortes_lista = [c['nombre'] for c in cortes_rows]
        
        for barbero in barberos_rows:
            resultado.append(CortesPorBarbero(
                barbero=barbero['nombre'],
                cortes_disponibles=cortes_lista
            ))
        
        return resultado

@mcp.tool()
async def buscar_corte_por_nombre(ctx: Context, nombre_corte: str) -> TipoCorte:
    """
    Busca información detallada de un tipo de corte específico.
    
    Args:
        nombre_corte: El nombre del corte a buscar (ej: "Corte Clásico")
    """
    pool: asyncpg.Pool = ctx.request_context.lifespan_context.db_pool
    async with pool.acquire() as conn:
        row = await conn.fetchrow(
            "SELECT * FROM tipos_corte WHERE LOWER(nombre) LIKE LOWER($1)",
            f"%{nombre_corte}%"
        )
        if row:
            return TipoCorte(**row)
        raise ValueError(f"No se encontró el tipo de corte: {nombre_corte}")

# --- EJECUCIÓN ---
if __name__ == "__main__":
    print("🚀 Iniciando servidor MCP para Barbería...")
    mcp.run()