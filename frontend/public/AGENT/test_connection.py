import subprocess
import socket
import os
from dotenv import load_dotenv

load_dotenv()

print("🔍 DIAGNÓSTICO COMPLETO DE POSTGRESQL\n")
print("=" * 60)

# 1. Verificar si el puerto está abierto
print("\n1️⃣ Verificando si PostgreSQL está escuchando en el puerto...")
try:
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sock.settimeout(2)
    result = sock.connect_ex(('localhost', 5432))
    sock.close()
    
    if result == 0:
        print("   ✅ Puerto 5432 está ABIERTO")
    else:
        print("   ❌ Puerto 5432 está CERRADO")
        print("   💡 PostgreSQL no está corriendo o está en otro puerto")
except Exception as e:
    print(f"   ❌ Error verificando puerto: {e}")

# 2. Verificar servicios de PostgreSQL
print("\n2️⃣ Buscando servicios de PostgreSQL...")
try:
    result = subprocess.run(
        ['powershell', '-Command', 'Get-Service -Name *postgresql*'],
        capture_output=True,
        text=True,
        timeout=10
    )
    
    if result.stdout:
        print("   Servicios encontrados:")
        print(result.stdout)
    else:
        print("   ⚠️ No se encontraron servicios de PostgreSQL")
        print("   💡 Puede que PostgreSQL no esté instalado o el servicio tiene otro nombre")
except Exception as e:
    print(f"   ❌ Error verificando servicios: {e}")

# 3. Intentar conexión con psql
print("\n3️⃣ Intentando conectar con psql (cliente PostgreSQL)...")
try:
    result = subprocess.run(
        ['psql', '--version'],
        capture_output=True,
        text=True,
        timeout=5
    )
    
    if result.returncode == 0:
        print(f"   ✅ psql encontrado: {result.stdout.strip()}")
        
        # Intentar conectar
        print("\n   Intentando conexión con psql...")
        db_pass = os.getenv("DB_PASSWORD", "2025")
        os.environ['PGPASSWORD'] = db_pass
        
        result = subprocess.run(
            [
                'psql',
                '-h', 'localhost',
                '-U', 'postgres',
                '-d', 'barberia',
                '-c', 'SELECT version();'
            ],
            capture_output=True,
            text=True,
            timeout=10
        )
        
        if result.returncode == 0:
            print("   ✅ Conexión exitosa con psql")
            print(result.stdout)
        else:
            print("   ❌ Error conectando:")
            print(result.stderr)
    else:
        print("   ⚠️ psql no encontrado en PATH")
except FileNotFoundError:
    print("   ⚠️ psql no está instalado o no está en el PATH")
except Exception as e:
    print(f"   ❌ Error: {e}")

# 4. Verificar archivo .env
print("\n4️⃣ Verificando configuración .env...")
print(f"   DB_HOST: {os.getenv('DB_HOST', 'localhost')}")
print(f"   DB_PORT: {os.getenv('DB_PORT', '5432')} (por defecto)")
print(f"   DB_NAME: {os.getenv('DB_NAME', 'barberia')}")
print(f"   DB_USER: {os.getenv('DB_USER', 'postgres')}")
print(f"   DB_PASSWORD: {'*' * len(os.getenv('DB_PASSWORD', ''))}")

print("\n" + "=" * 60)
print("\n📋 POSIBLES SOLUCIONES:\n")
print("A) Si PostgreSQL no está corriendo:")
print("   - Abre 'Servicios' en Windows (services.msc)")
print("   - Busca 'postgresql' e inicia el servicio")
print("   - O reinstala PostgreSQL\n")

print("B) Si el puerto está cerrado:")
print("   - Verifica postgresql.conf (puerto por defecto: 5432)")
print("   - Verifica pg_hba.conf (debe permitir conexiones locales)\n")

print("C) Si la base de datos 'barberia' no existe:")
print("   - Conéctate como postgres y crea la base de datos:")
print("   - psql -U postgres -c 'CREATE DATABASE barberia;'\n")

print("D) Si es problema de permisos:")
print("   - Verifica que la contraseña sea correcta")
print("   - Verifica pg_hba.conf tenga: host all all 127.0.0.1/32 md5")