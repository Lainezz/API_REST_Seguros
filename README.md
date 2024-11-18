
# Ejercicio 1: Gestión de Seguros en una Aplicación Spring Boot con Kotlin

En este ejercicio, vamos a desarrollar una aplicación básica en **Spring Boot** utilizando **Kotlin** que permita gestionar entidades de tipo **Seguro**. La aplicación deberá implementar un conjunto de operaciones CRUD a través de métodos HTTP para interactuar con la tabla asociada al modelo de datos.

## Parte 1: Definición de la Entidad y Controlador

### 1. Entidad Seguro
Crea una clase de datos en Kotlin que represente la entidad **Seguro** con los siguientes atributos:

```kotlin
data class Seguro(
    val idSeguro: Int,
    val nif: String,
    val nombre: String,
    val ape1: String,
    val ape2: String?,
    val edad: Int,
    val numHijos: Int,
    val fechaCreacion: Date,
    val sexo: String,
    val casado: Boolean,
    val embarazada: Boolean
)
```

**Modelo de la base de datos (Tabla Seguro):**

```sql
CREATE TABLE seguros (
    id_seguro INTEGER PRIMARY KEY,
    nif VARCHAR(10) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    ape1 VARCHAR(100) NOT NULL,
    ape2 VARCHAR(100),
    edad INTEGER NOT NULL CHECK (edad > 0),
    num_hijos INTEGER NOT NULL CHECK (num_hijos >= 0),
    fecha_creacion TIMESTAMP NOT NULL,
    sexo VARCHAR(10) NOT NULL,
    casado BOOLEAN NOT NULL,
    embarazada BOOLEAN NOT NULL
);
```

### 2. Validaciones a implementar en la capa service

- El campo `nif` debe ser válido (formato correcto).
- El campo `nombre` no puede estar vacío.
- El campo `ape1` no puede estar vacío.
- El campo `edad` debe ser mayor que 0.
    - Si `edad` está entre 0 y 17 años, deberá indicar que **"No es posible ser menor de edad para hacer un seguro"**.
- El campo `sexo` no puede ser `null`.
- El campo `numHijos` no puede ser menor que 0.
- Si el campo `casado` es `false`, el campo `numHijos` debe ser igual a 0.
- Si el campo `embarazada` es `true`, el campo `sexo` debe ser **"Mujer"**.

### 3. Controlador SeguroController

Implementa una clase `SeguroController` con los endpoints necesarios para realizar operaciones CRUD sobre la entidad **Seguro**. Cada endpoint deberá cumplir con las validaciones descritas anteriormente.

### Operaciones CRUD

- **GET** `/seguros`:  
  Devuelve una lista de todos los seguros registrados.

- **GET** `/seguros/{id}`:  
  Devuelve un seguro por su identificador `idSeguro`. Si no existe, retorna un error 404.

- **POST** `/seguros`:  
  Crea un nuevo seguro.
    - Valida los campos siguiendo las restricciones mencionadas.

- **PUT** `/seguros/{id}`:  
  Actualiza un seguro existente identificado por `idSeguro`.
    - Si no existe, retorna un error 404.
    - Aplica las validaciones antes de guardar los cambios.

- **DELETE** `/seguros/{id}`:  
  Elimina un seguro identificado por `idSeguro`. Si no existe, retorna un error 404.

### Requisitos del Entorno

1. Crea un proyecto en **Spring Boot** con las siguientes dependencias:
    - **Spring Web**
    - **Spring Data JPA**
    - **MySQL Database** (o el sistema de base de datos de tu elección)
2. Configura la base de datos en el archivo `application.properties`.

### Entrega

1. Código de la entidad `Seguro` con sus campos.
2. Código del service `SeguroService` con sus validaciones.
3. Código del controlador `SeguroController` con los endpoints definidos.
4. Pruebas funcionales utilizando Postman o Swagger para demostrar el correcto funcionamiento de las operaciones CRUD.
5. Realiza un documento donde incluyas todos los puntos anteriores, junto con las explicaciones, pantallazos y comentarios que creas convenientes para que esta parte de la aplicación quede correctamente explicada.

## Sugerencia de Validación en Spring Boot

Puedes usar anotaciones de validación estándar como `@NotNull`, `@Min`, `@Max`, `@Pattern`, o crear validadores personalizados según sea necesario.


# Ejercicio 2: Gestión de Seguros y Asistencias Médicas en Spring Boot con Kotlin

En este ejercicio, desarrollarás una API REST completa en **Spring Boot** para la gestión de seguros médicos (**Seguro**) y asistencias médicas asociadas (**AsistenciaMedica**). Implementarás validaciones, relaciones entre entidades, manejo de excepciones personalizadas y retorno de códigos de estado HTTP adecuados.

---

## **Enunciado del Ejercicio**

La aplicación debe cumplir los siguientes objetivos:

### **1. Entidades a Gestionar**

1. **Entidad Seguro:**
    - Representa un seguro médico.
    - Propiedades:
        - `idSeguro`: Identificador único del seguro.
        - `nif`: Número de identificación fiscal (validar formato).
        - `nombre`: Nombre del asegurado (no vacío).
        - `ape1`: Primer apellido del asegurado (no vacío).
        - `ape2`: Segundo apellido del asegurado (opcional).
        - `edad`: Edad del asegurado (mayor que 0; no menor de edad).
        - `numHijos`: Número de hijos (mayor o igual que 0; debe ser 0 si el asegurado no está casado).
        - `fechaCreacion`: Fecha de creación del seguro.
        - `sexo`: Sexo del asegurado (no puede ser nulo).
        - `casado`: Estado civil del asegurado.
        - `embarazada`: Indica si el asegurado está embarazado (no permitido para hombres).

2. **Entidad AsistenciaMedica:**
    - Representa una asistencia médica asociada a un seguro.
    - Propiedades:
        - `idAsistenciaMedica`: Identificador único de la asistencia.
        - `seguro`: Referencia al seguro asociado.
        - `breveDescripcion`: Breve descripción de la asistencia (no vacío).
        - `lugar`: Lugar de la asistencia (no vacío).
        - `explicacion`: Explicación detallada de la asistencia (no vacío).
        - `tipoAsistencia`: Tipo de asistencia (no nulo).
        - `fecha`: Fecha de la asistencia (no nula).
        - `hora`: Hora de la asistencia (no nula).
        - `importe`: Importe asociado (double; mayor que 0, con 2 decimales).

```kotlin
data class AsistenciaMedica(
    val idAsistenciaMedica: Int,
    val seguro: Seguro, // Relación con Seguro
    val breveDescripcion: String,
    val lugar: String,
    val explicacion: String,
    val tipoAsistencia: String,
    val fecha: LocalDate,
    val hora: LocalTime,
    val importe: Double
)
```

**Modelo de la base de datos (Tabla asistencias_medicas):**

```sql
CREATE TABLE asistencias_medicas (
    id_asistencia_medica INTEGER PRIMARY KEY,
    id_seguro INTEGER NOT NULL,
    breve_descripcion VARCHAR(255) NOT NULL,
    lugar VARCHAR(255) NOT NULL,
    explicacion TEXT NOT NULL,
    tipo_asistencia VARCHAR(100) NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    importe DECIMAL(12, 2) NOT NULL CHECK (importe > 0),
    CONSTRAINT FK_Seguro FOREIGN KEY (id_seguro) REFERENCES seguros(id_seguro) ON DELETE CASCADE
);
```

---

### **2. Requisitos Funcionales**

1. **Operaciones CRUD:**
    - **Seguro:**
        - Crear un seguro.
        - Consultar un seguro por su identificador.
        - Listar todos los seguros.
        - Actualizar un seguro existente.
        - Eliminar un seguro (debe eliminar también las asistencias asociadas).

    - **AsistenciaMedica:**
        - Crear una asistencia médica asociada a un seguro.
        - Consultar una asistencia médica por su identificador.
        - Listar todas las asistencias médicas.
        - Actualizar una asistencia médica existente.
        - Eliminar una asistencia médica.

2. **Validaciones:**
    - Implementa las siguientes validaciones para cada entidad (ver tabla de validaciones más abajo).
    - Retorna un código de estado **400 Bad Request** y un mensaje descriptivo cuando no se cumplan las reglas.

3. **Manejo de Excepciones:**
    - Crea excepciones personalizadas para validaciones específicas (**ValidationException**) y recursos no encontrados (**ResourceNotFoundException**).
    - Centraliza el manejo de excepciones con una clase anotada con `@ControllerAdvice`.
    - Responde con códigos de estado y mensajes adecuados:
        - **400 Bad Request**: Para errores de validación.
        - **404 Not Found**: Para recursos inexistentes.
        - **500 Internal Server Error**: Para errores inesperados.

4. **Códigos de Respuesta:**
    - Operaciones exitosas:
        - **201 Created**: Para creación de recursos.
        - **200 OK**: Para consultas y actualizaciones exitosas.
        - **204 No Content**: Para eliminaciones exitosas.
    - Operaciones fallidas:
        - **400 Bad Request**: Para errores de validación.
        - **404 Not Found**: Para recursos inexistentes.

---

### **3. Requisitos Técnicos**

1. **Relación entre Entidades:**
    - Relación **1 a N** entre `Seguro` y `AsistenciaMedica`. Cada seguro puede tener múltiples asistencias médicas.

2. **Base de Datos:**
    - Modelo relacional:
        - Tabla `Seguro` con las columnas correspondientes a sus propiedades.
        - Tabla `AsistenciaMedica` con una columna `idSeguro` como clave foránea.

3. **Configuración:**
    - Configura el proyecto con las siguientes dependencias:
        - **Spring Web**
        - **Spring Data JPA**
        - **MySQL Database** (o un sistema de base de datos a elección).

4. **Documentación y Pruebas:**
    - Proporciona pruebas funcionales que incluyan casos de éxito y error, utilizando herramientas como **Insomnia** o **Postman**.

---

### **4. Validaciones**

| Campo                   | Regla de Validación                                                       | Código HTTP  | Mensaje de Error                                     |
|-------------------------|---------------------------------------------------------------------------|--------------|-----------------------------------------------------|
| `nif`                  | Formato válido (regex).                                                   | 400          | "El campo NIF no tiene un formato válido."          |
| `nombre`, `ape1`       | No puede estar vacío.                                                     | 400          | "El campo {nombre/ape1} no puede estar vacío."      |
| `edad`                 | Mayor que 0. No menor de edad (<18).                                      | 400          | "No es posible ser menor de edad para hacer un seguro." |
| `numHijos`             | Mayor o igual que 0. Si `casado = false`, debe ser 0.                    | 400          | "Un seguro no puede registrar hijos si no está casado." |
| `embarazada`           | No puede ser `true` si `sexo = Hombre`.                                  | 400          | "El campo embarazada no puede ser true si el asegurado es hombre." |
| `breveDescripcion`     | No puede estar vacío.                                                     | 400          | "El campo breveDescripcion no puede estar vacío."   |
| `lugar`                | No puede estar vacío.                                                     | 400          | "El campo lugar no puede estar vacío."              |
| `explicacion`          | No puede estar vacío.                                                     | 400          | "El campo explicacion no puede estar vacío."        |
| `tipoAsistencia`       | No puede ser null.                                                        | 400          | "El campo tipoAsistencia no puede ser nulo."        |
| `fecha`, `hora`        | No pueden ser null.                                                       | 400          | "El campo {fecha/hora} no puede ser nulo."          |
| `importe`              | Mayor que 0.                                                             | 400          | "El campo importe debe ser mayor que 0."            |

---

### **5. Entregables**

1. **Código Fuente:**
    - Incluye todas las entidades, controladores, servicios, repositorios y excepciones.

2. **SQL de Prueba:**
    - Proporciona un script para poblar la base de datos con datos de prueba.

3. **Pruebas Funcionales:**
    - Evidencia de pruebas realizadas con Insomnia, Postman o Swagger:
        - Casos exitosos para cada operación.
        - Casos de validación fallida.

4. **Documentación:**
    - Esquema de la base de datos.
    - Descripción de los endpoints disponibles.

---

## **Resumen de Endpoints**

### Endpoints para Seguros
- **GET** `/seguros`
- **GET** `/seguros/{idSeguro}`
- **POST** `/seguros`
- **PUT** `/seguros/{idSeguro}`
- **DELETE** `/seguros/{idSeguro}`

### Endpoints para Asistencias Médicas
- **GET** `/asistencias`
- **GET** `/asistencias/{idAsistenciaMedica}`
- **POST** `/seguros/{idSeguro}/asistencias`
- **PUT** `/asistencias/{idAsistenciaMedica}`
- **DELETE** `/asistencias/{idAsistenciaMedica}`

## SQL de Inserción de Datos de Prueba

```sql
-- Insertar seguros
INSERT INTO seguro (id_eguro, nif, nombre, ape1, ape2, edad, num_hijos, fecha_creacion, sexo, casado, embarazada)
VALUES
    (1, '12345678A', 'Juan', 'Pérez', 'García', 35, 2, '2024-11-01 10:00:00', 'Hombre', TRUE, FALSE),
    (2, '87654321B', 'María', 'López', NULL, 28, 1, '2024-10-20 14:30:00', 'Mujer', TRUE, TRUE);

-- Insertar asistencias médicas
INSERT INTO asistencias_medicas (id_asistencia_medica, id_seguro, breve_descripcion, lugar, explicacion, tipo_asistencia, fecha, hora, importe)
VALUES
    (1, 1, 'Consulta médica general', 'Madrid', 'Consulta por síntomas leves', 'Consulta', '2024-11-02', '09:30:00', 50.00),
    (2, 1, 'Urgencia médica', 'Barcelona', 'Dolor abdominal intenso', 'Urgencia', '2024-11-03', '12:15:00', 150.00),
    (3, 2, 'Revisión ginecológica', 'Sevilla', 'Control durante embarazo', 'Consulta', '2024-11-04', '10:00:00', 70.00);
```

Con este ejercicio, aplicarás tus conocimientos en el diseño y desarrollo de APIs REST, validaciones, manejo de excepciones y buenas prácticas en Spring Boot.

