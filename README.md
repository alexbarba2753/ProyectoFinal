# ⚡ MultiVerse GeeK API - Inventario Premium

![MultiVerse GeeK API](src/main/resources/static/assets/logo.png) 

##  Descripción del Proyecto
**MultiVerse GeeK API** es una solución integral para la gestión de inventarios de figuras coleccionables de anime y superhéroes. El sistema permite realizar operaciones **CRUD** completas sobre un catálogo dinámico, integrando validaciones de negocio robustas y una interfaz de usuario moderna.

---

##  Stack Tecnológico

### Backend
* **Java 21**: Lenguaje principal enfocado en alto rendimiento.
* **Spring Boot 3.3.0**: Framework para la creación de microservicios y APIs RESTful.
* **Spring Data JPA**: Abstracción para la persistencia de datos.
* **H2 Database**: Base de datos en memoria para entornos de desarrollo y pruebas ágiles.
* **Docker**: Contenedorización para garantizar la portabilidad del sistema.

### Frontend
* **HTML5 / CSS3**: Estructura y diseño "Fancy" con tipografías dinámicas (Bangers & Orbitron).
* **JavaScript**: Lógica de cliente y consumo asíncrono de la API mediante Fetch API.
* **Bootstrap 5**: Framework de estilos para una interfaz responsiva.

---

## Arquitectura de Despliegue
El proyecto utiliza una arquitectura desacoplada para optimizar recursos y escalabilidad:

1.  **Backend (Render)**: Desplegado en un contenedor Docker utilizando un archivo `Dockerfile` multi-etapa y Gradle 8.5 para la construcción.
2.  **Frontend (Vercel)**: Alojado como contenido estático, comunicándose con la API mediante políticas de **CORS** habilitadas en el servidor.


---

## Funcionalidades Principales
* **Visualización Dinámica**: Carga automática de productos desde la API.
* **Gestión Total (CRUD)**:
    * **Crear**: Registro de nuevas figuras con validación de campos obligatorios.
    * **Listar**: Consulta en tiempo real del catálogo.
    * **Actualizar (PUT/PATCH)**: Lógica flexible que permite editar atributos específicos sin afectar la integridad de la base de datos.
    * **Eliminar**: Remoción segura de registros con confirmación de usuario.
* **Validaciones Preventivas**: Control de tipos de datos (isNaN) en el frontend y excepciones personalizadas (`FindIdException`) en el backend.

---

## Instalación y Ejecución Local

1. Clonar el repositorio:
   ```bash
   git clone [https://github.com/tu-usuario/ProyectoFinal.git](https://github.com/tu-usuario/ProyectoFinal.git)
2. Ejecutar el backend (Requiere JDK 21):
   ```bash
   ./gradlew bootRun
3. Abrir el archivo index.html en el navegador (Asegúrate de cambiar la API_URL a localhost).

---

## Enlances del Proyecto

* ****:
    * **FrontEnd**: [Registro de nuevas figuras con validación de campos obligatorios.](https://proyecto-final-zeta-tan.vercel.app)
    * **API (Docs)**: [Consulta en tiempo real del catálogo.](https://proyectofinal-ynd2.onrender.com/swagger-ui/index.html#/)

---

## Autores

## MATEO BARBA - DANIEL DIAZ
