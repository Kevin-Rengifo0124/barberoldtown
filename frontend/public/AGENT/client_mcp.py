import asyncio
from db_queries import (
    get_all_barberos,
    get_barbero_by_name,
    get_all_tipos_corte,
    buscar_corte_por_nombre,
    get_respuesta_especifica,
    get_info_general
)

async def main(prompt: str):
    """Procesa la consulta del usuario de forma natural e inteligente."""
    prompt_lower = prompt.lower().strip()

    try:
        # ==========================
        # 🔹 1. Información general
        # ==========================
        if any(word in prompt_lower for word in ["nombre", "llama", "barbería", "barberia", "negocio", "sitio"]):
            return await get_info_general("nombre")

        if any(word in prompt_lower for word in ["horario", "hora", "abren", "cierran", "domingo", "sábado", "sabado"]):
            return await get_info_general("horario")

        if any(word in prompt_lower for word in ["servicio", "ofrecen", "tienen", "trabajan", "qué hacen", "que hacen"]):
            return await get_info_general("servicios")

        # ==========================
        # 🔹 2. Consultas sobre barberos
        # ==========================
        if any(w in prompt_lower for w in ["barbero", "barberos", "peluquero", "estilista"]):
            posibles_nombres = ["carlos", "andrés", "andres", "camilo", "felipe", "juan", "david"]
            for nombre in posibles_nombres:
                if nombre in prompt_lower:
                    return await get_barbero_by_name(nombre)
            return await get_all_barberos()

        # ==========================
        # 🔹 3. Consultas sobre cortes
        # ==========================
        if any(w in prompt_lower for w in ["corte", "cortes", "peinado", "look", "cabello", "estilo"]):
            palabras_clave = [
                "barba", "niño", "infantil", "moderno", "fade", "clasico", "clásico",
                "ejecutivo", "oficina", "barato", "económico", "caro", "premium", "popular", "tendencia"
            ]
            for palabra in palabras_clave:
                if palabra in prompt_lower:
                    return await get_respuesta_especifica(prompt_lower)
            return await get_all_tipos_corte()

        # ==========================
        # 🔹 4. Recomendaciones personalizadas
        # ==========================
        if any(w in prompt_lower for w in ["recomienda", "recomiendas", "me queda", "aconseja", "me sugieres"]):
            return await get_respuesta_especifica(prompt_lower)

        # ==========================
        # 🔹 5. Fallback (no entiende)
        # ==========================
        return (
            "🤖 Lo siento, no entendí bien tu pregunta. Pero puedo ayudarte con:\n"
            "• Información de barberos y sus especialidades 💈\n"
            "• Tipos de cortes disponibles 💇‍♂️\n"
            "• Horarios y servicios de la barbería 🕐\n"
            "Prueba decir algo como:\n"
            "👉 '¿Cómo se llama la barbería?'\n"
            "👉 '¿A qué hora abren?'\n"
            "👉 'Dame el número de Camilo Marín'\n"
            "👉 '¿Qué corte recomiendas para oficina?'"
        )

    except Exception as e:
        return f"❌ Error procesando la solicitud: {str(e)}"


if __name__ == "__main__":
    while True:
        pregunta = input("\n💬 Pregunta: ")
        if pregunta.lower() in ["salir", "exit", "quit"]:
            break
        respuesta = asyncio.run(main(pregunta))
        print(f"\n🧠 Respuesta:\n{respuesta}\n")
