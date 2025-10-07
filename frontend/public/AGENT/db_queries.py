import asyncpg
import os
from dotenv import load_dotenv

load_dotenv()

_pool = None

# ==============================
# 🔹 Conexión y Pool
# ==============================
async def get_db_pool():
    """Crea o devuelve un pool de conexiones global."""
    global _pool
    if _pool is None or _pool._closed:
        print("🔄 Creando nuevo pool de conexiones...")
        _pool = await asyncpg.create_pool(
            user=os.getenv("DB_USER", "postgres"),
            password=os.getenv("DB_PASSWORD", "2025"),
            database=os.getenv("DB_NAME", "barberia"),
            host=os.getenv("DB_HOST", "localhost"),
            min_size=2,
            max_size=10
        )
    return _pool


async def close_db_pool():
    """Cierra el pool de conexiones de forma segura."""
    global _pool
    if _pool is not None and not _pool._closed:
        print("🔌 Cerrando pool de conexiones...")
        await _pool.close()
        _pool = None
        print("✅ Pool cerrado correctamente.")


# ==============================
# 🔹 Utilidades de formato
# ==============================
def _formatear_cortes(rows):
    if not rows:
        return "No hay cortes disponibles."
    return "\n\n".join([
        f"• 💇‍♂️ **{r['nombre']}**\n💰 ${r['precio']:,.0f}\n⏱️ {r['duracion_minutos']} min\n📝 {r['descripcion']}"
        for r in rows
    ])


# ==============================
# 🔹 Consultas básicas
# ==============================
async def get_all_barberos():
    """Devuelve todos los barberos activos."""
    pool = await get_db_pool()
    async with pool.acquire() as conn:
        rows = await conn.fetch("SELECT * FROM barberos WHERE activo = true")
        if not rows:
            return "No hay barberos disponibles."
        return "\n".join([
            f"• 👨‍🔧 {r['nombre']} - ✂️ {r['especialidad']} - 📱 {r['telefono']}"
            for r in rows
        ])


async def get_all_tipos_corte():
    """Devuelve todos los tipos de corte."""
    pool = await get_db_pool()
    async with pool.acquire() as conn:
        rows = await conn.fetch("SELECT * FROM tipos_corte")
        return _formatear_cortes(rows)


async def get_barbero_by_name(nombre: str):
    """Busca un barbero por nombre (búsqueda parcial)."""
    pool = await get_db_pool()
    async with pool.acquire() as conn:
        row = await conn.fetchrow(
            "SELECT * FROM barberos WHERE LOWER(nombre) LIKE LOWER($1)",
            f"%{nombre}%"
        )
        if not row:
            return f"No encontré ningún barbero llamado {nombre}."
        return (
            f"👤 **{row['nombre']}**\n"
            f"✂️ Especialidad: {row['especialidad']}\n"
            f"📱 Teléfono: {row['telefono']}"
        )


async def buscar_corte_por_nombre(nombre_corte: str):
    """Busca un tipo de corte por nombre (búsqueda parcial)."""
    pool = await get_db_pool()
    async with pool.acquire() as conn:
        row = await conn.fetchrow(
            "SELECT * FROM tipos_corte WHERE LOWER(nombre) LIKE LOWER($1)",
            f"%{nombre_corte}%"
        )
        if not row:
            return f"No encontré un corte que coincida con '{nombre_corte}'."
        return (
            f"📋 **{row['nombre']}**\n"
            f"💰 Precio: ${row['precio']:,.0f}\n"
            f"⏱️ Duración: {row['duracion_minutos']} minutos\n"
            f"📝 {row['descripcion']}"
        )


# ==============================
# 🔹 Respuestas específicas (IA más natural)
# ==============================
async def get_respuesta_especifica(pregunta: str):
    """Responde preguntas naturales relacionadas con los servicios."""
    pregunta = pregunta.lower()
    pool = await get_db_pool()
    async with pool.acquire() as conn:

        # Oficina
        if any(w in pregunta for w in ["oficina", "ejecutivo", "formal"]):
            row = await conn.fetchrow("SELECT * FROM tipos_corte WHERE LOWER(nombre) LIKE '%ejecutivo%'")
            if row:
                return (
                    f"🏢 El corte ideal para oficina es **{row['nombre']}**.\n"
                    f"💰 ${row['precio']:,.0f}\n⏱️ {row['duracion_minutos']} min\n📝 {row['descripcion']}"
                )

        # Barato
        if any(w in pregunta for w in ["barato", "económico", "económico", "precio bajo"]):
            row = await conn.fetchrow("SELECT * FROM tipos_corte ORDER BY precio ASC LIMIT 1")
            return (
                f"💸 El corte más económico es **{row['nombre']}**.\n"
                f"💰 ${row['precio']:,.0f}\n⏱️ {row['duracion_minutos']} min\n📝 {row['descripcion']}"
            )

        # Caro
        if any(w in pregunta for w in ["caro", "premium", "exclusivo", "lujo"]):
            row = await conn.fetchrow("SELECT * FROM tipos_corte ORDER BY precio DESC LIMIT 1")
            return (
                f"💎 El corte más costoso es **{row['nombre']}**.\n"
                f"💰 ${row['precio']:,.0f}\n⏱️ {row['duracion_minutos']} min\n📝 {row['descripcion']}"
            )

        # Barba
        if any(w in pregunta for w in ["barba", "afeitar", "perfilado"]):
            row = await conn.fetchrow("SELECT * FROM tipos_corte WHERE LOWER(nombre) LIKE '%barba%'")
            if row:
                return (
                    f"🧔 Te recomiendo el servicio **{row['nombre']}**.\n"
                    f"💰 ${row['precio']:,.0f}\n⏱️ {row['duracion_minutos']} min\n📝 {row['descripcion']}"
                )

        # Niños
        if any(w in pregunta for w in ["niño", "infantil", "pequeño", "kids"]):
            row = await conn.fetchrow("SELECT * FROM tipos_corte WHERE LOWER(nombre) LIKE '%infantil%'")
            if row:
                return (
                    f"🧒 El corte ideal para niños es **{row['nombre']}**.\n"
                    f"💰 ${row['precio']:,.0f}\n⏱️ {row['duracion_minutos']} min\n📝 {row['descripcion']}"
                )

        # Moderno o popular
        if any(w in pregunta for w in ["moderno", "popular", "tendencia", "nuevo"]):
            row = await conn.fetchrow("SELECT * FROM tipos_corte WHERE LOWER(nombre) LIKE '%moderno%'")
            if row:
                return (
                    f"🔥 El corte más popular ahora es **{row['nombre']}**.\n"
                    f"💰 ${row['precio']:,.0f}\n⏱️ {row['duracion_minutos']} min\n📝 {row['descripcion']}"
                )

        # Si no hay coincidencia
        return await get_all_tipos_corte()


# ==============================
# 🔹 Info general (nombre, horario, servicios)
# ==============================
async def get_info_general(tipo: str):
    """Devuelve información general sobre la barbería."""
    tipo = tipo.lower()
    if tipo == "nombre":
        return "🏠 La barbería se llama **Old Town Barber** 💈"
    elif tipo == "horario":
        return "🕐 Nuestro horario es de lunes a sábado de **9:00 a.m. a 7:00 p.m.** y domingos de **10:00 a.m. a 4:00 p.m.**"
    elif tipo == "servicios":
        return (
            "💈 En *Old Town Barber* ofrecemos:\n"
            "• Cortes modernos y clásicos ✂️\n"
            "• Arreglo y perfilado de barba 🧔\n"
            "• Corte infantil 👦\n"
            "• Asesoría personalizada 💬"
        )
    else:
        return "No tengo información sobre eso, pero puedo ayudarte con cortes, barberos o servicios. ¿Qué deseas saber?"
