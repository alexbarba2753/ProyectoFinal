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
* **Validaciones Preventivas**: Control de tipos de datos (isNaN) en el frontend y excepciones personalizadas (`FindIdException, DuplicateProductException`) en el backend.


---

## Estructura del Proyecto
```text
.
├── src/main/java/edu/epn/...
│   ├── config/          # Configuración de CORS
│   ├── controller/      # Endpoints REST de la API
│   ├── dto/             # Objetos de transporte de datos
│   ├── exception/       # Manejador global y excepciones personalizadas
│   ├── model/           # Entidades JPA (Base de datos)
│   ├── repository/      # Interfaces de persistencia
│   └── service/         # Lógica de negocio y mapeos
├── src/main/resources/
│   ├── static/          # Frontend (HTML, CSS, JS, Assets)
│   ├── application.properties # Configuración del sistema
│   └── data.sql         # Datos semilla (Seeders)
├── build.gradle         # Gestión de dependencias (Gradle)
├── Dockerfile           # Receta para contenedorización en Render
└── README.md            # Documentación del proyecto
```

---

## Instalación y Ejecución Local

Para poner en marcha el proyecto en un entorno de desarrollo, sigue estos pasos detallados:

### Requisitos Previos
* **Java Development Kit (JDK) 21**: Necesario para compilar el código fuente del backend.
* **Navegador Web**: Recomendado Chrome o Edge para pruebas de consola.
* **IDE (Opcional)**: IntelliJ IDEA o VS Code con extensiones de Java.

###  Pasos para el Backend
1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/alexbarba2753/ProyectoFinal.git
   cd ProyectoFinal
2. **Cargar Dependencias**: El proyecto utiliza Gradle. Al ejecutarlo por primera vez, se descargarán automáticamente todas las librerías necesarias.

3. **Ejecutar la Aplicación**:Utiliza el wrapper de Gradle incluido en el proyecto:
   # En Windows:
   ```bash
   gradlew.bat bootRun
   ```
   # En Linux/Mac:
   ```bash
   ./gradlew bootRun
   ```
   El servidor estará activo en: *http://localhost:8080*

5. **Verificar Base de Datos (Opcional)**:Puedes acceder a la consola de H2 para auditar las tablas en: *http://localhost:8080/h2-console*.

###  Pasos para el Frontend

1. Localiza el archivo de lógica en la ruta:
   ```bash
   src/main/resources/static/script.js
   ```
2. Asegúrate de que la constante de conexión apunte a tu servidor local:
   ```bash
   const API_URL = 'http://localhost:8080/api/productos';
   ```
3. Abre el archivo *src/main/resources/static/index.html* directamente en tu navegador.
   
   ---

## Enlances del Proyecto

 * **Frontend (Vercel)**: [⚡MultiVerse Geek](https://proyecto-final-zeta-tan.vercel.app)
 * **Documentación API (Swagger/Render)**: [Interactiva API Docs](https://proyectofinal-ynd2.onrender.com/swagger-ui/index.html#/)
 * **Repositorio Código Fuente**: [GitHub Repository](https://github.com/alexbarba2753/ProyectoFinal.git)
---

## Autores

## MATEO BARBA - DANIEL DIAZ
