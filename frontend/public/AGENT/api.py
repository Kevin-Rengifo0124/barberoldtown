from flask import Flask, request, render_template
from flask_cors import CORS
from client_mcp import main
from db_queries import close_db_pool
import asyncio
import atexit

app = Flask(__name__)
CORS(app)

# ✅ Crear un único loop global reutilizable
loop = asyncio.new_event_loop()
asyncio.set_event_loop(loop)

@app.route("/")
def index():
    return render_template("index.html")

@app.route("/query", methods=["POST"])
def leer_consulta():
    try:
        datos = request.get_json()
        if not datos or "consulta" not in datos:
            return {"error": "Falta el campo 'consulta' en el JSON"}, 400

        consulta = datos["consulta"]
        resultado = loop.run_until_complete(main(consulta))
        return {"data": resultado}

    except Exception as e:
        print(f"❌ Error en /query: {str(e)}")
        return {"error": f"❌ Error: {str(e)}"}, 500

# ✅ Cerrar correctamente al salir
@atexit.register
def cleanup():
    try:
        loop.run_until_complete(close_db_pool())
        loop.close()
        print("🧹 Recursos limpiados correctamente.")
    except Exception as e:
        print(f"⚠️ Error cerrando pool o loop: {e}")

if __name__ == "__main__":
    # 🚫 Desactivar completamente el modo debug y reloader
    app.run(debug=False, port=5000, use_reloader=False)
