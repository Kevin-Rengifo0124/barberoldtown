import React from "react";

const Footer = () => {
  return (
    <footer class="bg-gray-900 text-gray-200 py-10 flex flex-col items-center justify-center p-20">
      <div class="container mx-auto grid grid-cols-1 md:grid-cols-3 gap-8 px-6 md:px-0">
        <div>
          <h3 class="text-xl font-semibold mb-4">Acerca de nosotros</h3>
          <p class="text-sm">
            Bienvenido a Old Town Barber, su destino único para servicios de salón de belleza de primera calidad.
            Reserve citas con facilidad y experimente el lujo al alcance de su mano.
          </p>
        </div>

        <div>
          <h3 class="text-xl font-semibold mb-4">Enlaces rápidos</h3>
          <ul class="space-y-2 text-sm">
            <li>
              <a href="/" class="hover:text-gray-400">
                Inicio
              </a>
            </li>
            <li>
              <a href="/" class="hover:text-gray-400">
                Servicios
              </a>
            </li>
            <li>
              <a href="/" class="hover:text-gray-400">
                Reservar cita
              </a>
            </li>
            <li>
              <a href="/" class="hover:text-gray-400">
                Acerca de nosotros
              </a>
            </li>
            <li>
              <a href="/" class="hover:text-gray-400">
                Contacto
              </a>
            </li>
          </ul>
        </div>

        <div>
          <h3 class="text-xl font-semibold mb-4">Contáctenos</h3>
          <ul class="space-y-2 text-sm">
            <li>
              <i class="fas fa-phone-alt"></i> +57 3225647485
            </li>
            <li>
              <i class="fas fa-envelope"></i> support@salon.com
            </li>
            <li>
              <i class="fas fa-map-marker-alt"></i> Cra. 14 #9-52, Armenia, Quindío, Colombia

            </li>
          </ul>
          <div class="mt-4 flex space-x-4">
            <a href="/" class="text-gray-400 hover:text-gray-200">
              <i class="fab fa-facebook-f"></i>
            </a>
            <a href="/" class="text-gray-400 hover:text-gray-200">
              <i class="fab fa-twitter"></i>
            </a>
            <a href="/" class="text-gray-400 hover:text-gray-200">
              <i class="fab fa-instagram"></i>
            </a>
            <a href="/" class="text-gray-400 hover:text-gray-200">
              <i class="fab fa-linkedin-in"></i>
            </a>
          </div>
        </div>
      </div>

      <div class="border-t border-gray-700 mt-8 pt-4 text-center text-sm">
        &copy; 2025 Old Town Barber. All Rights Reserved.
      </div>
    </footer>
  );
};

export default Footer;
