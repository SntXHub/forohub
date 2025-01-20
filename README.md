# ForoHub - API REST para Gestión de Tópicos

ForoHub es una API RESTful desarrollada con **Spring Boot** para gestionar tópicos de discusión. Proporciona operaciones CRUD completas (Crear, Leer, Actualizar, Eliminar) para la gestión de tópicos, junto con autenticación y autorización basada en JWT.

## Características principales

- **Autenticación y Autorización**:
    - Autenticación mediante tokens JWT.
    - Autorización basada en roles (actualmente `ROLE_ADMIN`).

- **Gestión de Tópicos**:
    - Crear, leer, actualizar y eliminar tópicos.
    - Cada tópico incluye campos como título, contenido, curso, estado y fecha de creación.

- **Persistencia de Datos**:
    - Base de datos relacional MySQL para la persistencia de datos de usuarios y tópicos.

- **Documentación Interactiva**:
    - **Swagger** para la visualización y prueba de los endpoints de la API.

## Tecnologías utilizadas

- **Backend**:
    - Java 17
    - Spring Framework
    - Spring Boot 3.4.1
    - Spring Security
    - JWT (Json Web Token) para autenticación

- **Base de datos**:
    - MySQL
    - Flyway para migraciones

- **Herramientas y utilidades**:
    - Swagger para la documentación interactiva
    - Maven como gestor de dependencias
    - IntelliJ IDEA como entorno de desarrollo
    - Postman para automatización de pruebas y tests

## Instalación y configuración

1. **Clona el repositorio**:
   ```bash
   git clone https://github.com/SntXHub/forohub.git
   cd forohub
   ```
   
2. **Configura la base de datos**:
    - Asegúrate de tener MySQL instalado y ejecutándose.
    - Crea una base de datos llamada foro_hub.
    - Configura las credenciales en el archivo application.properties:
    ```plain
    spring.datasource.url=jdbc:mysql://localhost:3306/foro_hub
    spring.datasource.username=tu_usuario
    spring.datasource.password=tu_contraseña
    ```
3. **Ejecutar las migraciones**: Flyway aplicará automáticamente las migraciones al iniciar la aplicación.
4. **Ejecuta la aplicación.**
    
5. **Accede a la documentación de la API**:
    - Swagger UI: http://localhost:8080/swagger-ui.html
    - Documentación JSON: http://localhost:8080/v3/api-docs

## Endpoints de la API
### Autenticación
    
- POST /api/auth/login: Genera un token JWT

### Usuarios (solo para administradores)

- POST /api/users: Crea un nuevo usuario.
- GET /api/users: Obtiene la lista de usuarios.
- GET /api/users/{id}: Obtiene un usuario por su ID.
- PUT /api/users/{id}: Actualiza los datos de un usuario.
- DELETE /api/users/{id}: Elimina un usuario.

### Tópicos

- GET /api/topics: Lista todos los tópicos.
- POST /api/topics: Crea un nuevo tópico.
- GET /api/topics/{id}: Obtiene un tópico específico por su ID.
- PUT /api/topics/{id}: Actualiza un tópico existente.
- DELETE /api/topics/{id}: Elimina un tópico.

## Pruebas

1. **Generar un token de administrador**:
    - En Postman, realiza una solicitud POST a /api/auth/login con:
     ```Plain
        {
          "username": "admin",
          "password": "12345"
        }
     ```
2. **Usar el token en los demás endpoints**:
    - Agrega el token JWT en el encabezado de autorización de cada solicitud:
    ```Plain
       Authorization: Bearer <token>
    ```
## Futuras mejoras
- **Gestión de comentarios:**

    - Permitir a los usuarios agregar y gestionar comentarios en los tópicos.

- **Roles adicionales:**

    - Implementar otros roles `ROLE_USER` y `ROLE_MODERATOR` con diferentes permisos.

- **Paginación y filtrado:**

    - Agregar paginación y criterios de búsqueda para listar tópicos.

- **Pruebas automatizadas:**

    - Agregar cobertura de pruebas unitarias e integradas con JUnit.
  
- **Logs mejorados:**

    - Configurar logs avanzados para un mejor monitoreo y depuración.

## Créditos
   Proyecto desarrollado por Santiago Gabriel Cabrera - https://github.com/SntXHub
   
   (2024/25)

## Agradecimientos

- Al Programa Alura Latam + Oracle Next Education por su profesionalismo, calidad de servicio y compromiso con la 
  educación en nuestras comunidades. Su dedicación y esfuerzo han sido fundamentales para el éxito de nuestro 
  aprendizaje, nuestros proyectos y el crecimiento educativo. ¡Gracias por su invaluable oportunidad, apoyo y 
  colaboración!
  
  [Alura ONE](https://www.oracle.com/ar/education/oracle-next-education/)

## Licencia
   Este proyecto está licenciado bajo la licencia MIT. Puedes usarlo, modificarlo y distribuirlo libremente.
   
###    ***Toda colaboración es bienvenida.***
   