## Ejercicio 3. Implementación de la seguridad en la API

En esta parte se deben crear una serie de filtros para poder securizar el acceso a algunos endpoints que no deben ser accesibles para todo el mundo. Para realizar esta
seguridad se utilizarán tokens JWT.  
La aplicación dará la posibilidad a cualquier usuario de registrarse en la aplicación, lo cual insertará un nuevo usuario en la base de datos. *IMPORTANTE* El usuario quedará guardado en la base de datos junto con su
password **"hasheada"**.
Cuando el usuario esté correctamente guardado en la base de datos, entonces se le dará la posibilidad de realizar un **login**, tras lo cual se deberá generar un token JWT que se le suministrará para que pueda
realizar peticiones a los endpoints protegidos.

## **1. Estructura de la entidad extra**

1. **Usuario**:
    - `id` (Long): Identificador único del usuario.
    - `username` (String): Nombre del usuario (debe ser único).
    - `password` (String): Contraseña del usuario (debe estar hasheada).
    - `rol` (String): Rol del usuario, que puede ser `USER` o `ADMIN`.

## **2. Requisitos funcionales**

1. **Gestión de Usuarios**:
    - Los usuarios deben estar almacenados en la base de datos con los atributos mencionados.
    - Las contraseñas deben estar **hasheadas** utilizando un algoritmo seguro como `BCrypt`.
    - Cada usuario debe tener un rol:
        - `USER`: Tienen privilegios de tipo USER
        - `ADMIN`: Tienen privilegios de tipo ADMIN
    - Se deben tener en cuenta las posibles excepciones que puedan ocasionarse tanto al realizar el login como al realizar el registro de un nuevo usuario.

2. **Autenticación mediante JWT**:
    - Los usuarios deben autenticarse mediante el endpoint `POST /login`, enviando su nombre y contraseña.
    - Si las credenciales son válidas:
        - Se genera un **token** que debe enviarse al cliente para que lo almacene.
    - Los token JWT generados deberán tener una validez máxima de 1 hora, tras lo cual deberán de dejar de ser válidos.

## **3. Resumen de los endpoints**

### Endpoints para Usuarios
- **POST** `/usuarios/login`
    - *RUTA PÚBLICA* Cualquier usuario puede acceder a este endpoint
- **POST** `/usuarios/register`
    - *RUTA PÚBLICA* Cualquier usuario puede acceder a este endpoint

### Endpoints para Seguros
- **GET** `/seguros`
    - *RUTA PROTEGIDA* **SÓLO ADMIN** Sólo usuarios con `ROL ADMIN` pueden acceder a este recurso
- **GET** `/seguros/{idSeguro}`
    - *RUTA PROTEGIDA* **AUTHENTICATED** Sólo usuarios correctamente autenticados pueden acceder a este recurso
    - *RUTA PROTEGIDA* **ROL USER** Sólo usuarios con `ROL USER` que sean propietarios del seguro pueden acceder a este recurso
    - *RUTA PROTEGIDA* **ROL ADMIN** Usuarios con `ROL ADMIN` pueden acceder al recurso libremente
- **POST** `/seguros`
    - *RUTA PROTEGIDA* **SÓLO ADMIN** Sólo usuarios con `ROL ADMIN` pueden acceder a este recurso
- **PUT** `/seguros/{idSeguro}`
    - *RUTA PROTEGIDA* **SÓLO ADMIN** Sólo usuarios con `ROL ADMIN` pueden acceder a este recurso
- **DELETE** `/seguros/{idSeguro}`
    - *RUTA PROTEGIDA* **SÓLO ADMIN** Sólo usuarios con `ROL ADMIN` pueden acceder a este recurso

### Endpoints para Asistencias Médicas
- **GET** `/asistencias`
    - *RUTA PROTEGIDA* **SÓLO ADMIN** Sólo usuarios con `ROL ADMIN` pueden acceder a este recurso
- **GET** `/asistencias/{idAsistenciaMedica}`
    - *RUTA PROTEGIDA* **AUTHENTICATED** Sólo usuarios correctamente autenticados pueden acceder a este recurso
    - *RUTA PROTEGIDA* **ROL USER** Sólo usuarios con `ROL USER` que sean propietarios del seguro pueden acceder a este recurso
    - *RUTA PROTEGIDA* **ROL ADMIN** Usuarios con `ROL ADMIN` pueden acceder al recurso libremente
- **POST** `/seguros/{idSeguro}/asistencias`
    - *RUTA PROTEGIDA* **SÓLO ADMIN** Sólo usuarios con `ROL ADMIN` pueden acceder a este recurso
- **PUT** `/asistencias/{idAsistenciaMedica}`
    - *RUTA PROTEGIDA* **SÓLO ADMIN** Sólo usuarios con `ROL ADMIN` pueden acceder a este recurso
- **DELETE** `/asistencias/{idAsistenciaMedica}`
    - *RUTA PROTEGIDA* **SÓLO ADMIN** Sólo usuarios con `ROL ADMIN` pueden acceder a este recurso

### **4. Entregables**

1. **Código Fuente:**
    - Incluye todas las entidades, controladores, servicios, repositorios y excepciones.

2. **SQL de Prueba:**
    - Proporciona un script para poblar la base de datos con datos de prueba.

3. **Pruebas Funcionales:**
    - Evidencia de pruebas realizadas con Postman, Swagger o Insomnia:
        - Casos exitosos para cada operación.
        - Casos de validación fallida.

4. **Documentación:**
    - Esquema de la base de datos.
    - Descripción de los endpoints disponibles.