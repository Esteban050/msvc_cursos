# Microservicio de Cursos (msvc_cursos)

Este es un microservicio desarrollado con **Spring Boot** para la gestión de cursos y la inscripción de usuarios. Forma parte de una arquitectura de microservicios y se comunica con el servicio de usuarios (`msvc-usuarios`) utilizando **Spring Cloud OpenFeign**.

##  Tecnologías

- **Java 17**
- **Spring Boot 3.5.6**
- **Spring Data JPA** & **Hibernate**
- **PostgreSQL** (Base de datos relacional)
- **Spring Cloud OpenFeign** (Comunicación entre microservicios)
- **Lombok** (Reducción de código boilerplate)
- **Maven** (Gestión de dependencias)
- **Docker** (Contenedores)

## ️ Arquitectura y Estructura

El proyecto sigue una arquitectura por capas estándar:

- **`controller`**: Endpoints REST para la gestión de cursos.
- **`service`**: Lógica de negocio para cursos e inscripciones.
- **`repository`**: Interfaz de acceso a datos con Spring Data JPA.
- **`model/entities`**: Entidades JPA (`Curso`, `CursoUsuario`).
- **`model/dto`**: Objetos de transferencia de datos para peticiones y respuestas.
- **`client`**: Cliente Feign (`UsuarioClientRest`) para interactuar con el microservicio de usuarios.

##  Funcionalidades Principales

- CRUD completo de cursos (Crear, Leer, Actualizar, Eliminar).
- Asignación de usuarios a cursos existentes.
- Creación de nuevos usuarios desde el contexto de un curso.
- Desasignación de usuarios de un curso.
- Consulta de cursos con el detalle de los usuarios inscritos (obtenidos mediante Feign).
- **Eliminación en Cascada (Integración):** Endpoint específico para eliminar todas las relaciones de un usuario con los cursos cuando el usuario es eliminado en el microservicio `msvc-usuarios`.

##  Integración entre Microservicios

Este microservicio está diseñado para trabajar en conjunto con `msvc-usuarios`. 

### Comunicación Síncrona (Feign)

1. **Consulta de Usuarios:** `msvc-cursos` solicita información detallada de los usuarios al microservicio de usuarios para mostrarlos en el detalle de un curso.
2. **Eliminación Coordinada:** Expone un endpoint (`DELETE /api/cursos/eliminar-usuario/{usuarioId}`) que debe ser invocado por `msvc-usuarios` cuando un usuario es eliminado, asegurando la integridad referencial lógica entre servicios.

Para más detalles sobre cómo implementar esta integración en el lado de usuarios, consulta [IMPLEMENTACION_FEIGN_MSVC_USUARIOS.md](./IMPLEMENTACION_FEIGN_MSVC_USUARIOS.md).

##  Configuración

El servicio está configurado por defecto en el puerto **8002**.

### Requisitos Previos

- **PostgreSQL**: Se requiere una instancia de base de datos con el nombre `msvc_cursos`.
- **msvc-usuarios**: Para el funcionamiento completo de las inscripciones, el microservicio de usuarios debe estar disponible (usualmente en el puerto 8001).

### Variables de Entorno (application.properties)

Asegúrate de configurar correctamente las siguientes propiedades si deseas ejecutarlas localmente:

```properties
server.port=8002
spring.datasource.url=jdbc:postgresql://localhost:5432/msvc_cursos
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
# URL del microservicio de usuarios
msvc.usuarios.url=localhost:8001
```

##  Ejecución

### Usando Maven

```bash
./mvnw spring-boot:run
```

### Usando Docker

El proyecto incluye un `Dockerfile`. Puedes construir la imagen y ejecutarla:

```bash
docker build -t msvc-cursos .
docker run -p 8002:8002 msvc-cursos
```


