Feature: Gestión de cursos (crear, actualizar, eliminar)
    Para mantener actualizada la información de cursos
    Como developer
    Quiero exponer endpoints para registrar, actualizar y eliminar cursos

    Scenario Outline: Curso registrado correctamente
        Given que los datos enviados son válidos:
            | name     | code   | description       |
            | <name>   | <code> | <description>     |
        When envío una petición POST al endpoint "/api/v1/courses"
        Then el sistema responde con HTTP 201 Created
        And retorna los detalles del curso registrado

        Examples:
            | name            | code   | description                |
            | Álgebra         | MAT202 | Álgebra lineal básica      |
            | Literatura      | LIT101 | Introducción a la literatura |

    Scenario Outline: Datos inválidos para registro
        Given que los datos del curso no son válidos:
            | name     | code   | description   |
            | <name>   | <code> | <description> |
        When envío una petición POST al endpoint "/api/v1/courses"
        Then el sistema responde con HTTP 400 Bad Request
        And retorna un mensaje de error indicando los campos inválidos

        Examples:
            | name  | code | description         |
            |       | MAT1 | Curso sin nombre    |
            | Física|      | Código faltante     |
            | Química| QCH1 |                     |

    Scenario Outline: Curso actualizado correctamente
        Given que el curso con ID "<courseId>" existe
        And los datos enviados son válidos:
            | name     | code   | description     |
            | <name>   | <code> | <description>   |
        When envío una petición PUT al endpoint "/api/v1/courses/<courseId>"
        Then el sistema responde con HTTP 200 OK
        And retorna los detalles del curso actualizado

        Examples:
            | courseId | name         | code    | description                     |
            | 1        | Biología     | BIO101  | Fundamentos de biología         |
            | 2        | Filosofía    | FIL202  | Teorías filosóficas contemporáneas |

    Scenario: Curso no encontrado para actualizar
        Given que no existe un curso con ID "999"
        When envío una petición PUT al endpoint "/api/v1/courses/999"
        Then el sistema responde con HTTP 404 Not Found
        And retorna un mensaje indicando que el curso no fue encontrado

    Scenario: Curso eliminado correctamente
        Given que existe un curso con ID "3"
        When envío una petición DELETE al endpoint "/api/v1/courses/3"
        Then el sistema responde con HTTP 204 No Content
        And el curso es eliminado del sistema

    Scenario: Curso no encontrado para eliminar
        Given que no existe un curso con ID "999"
        When envío una petición DELETE al endpoint "/api/v1/courses/999"
        Then el sistema responde con HTTP 404 Not Found
        And retorna un mensaje indicando que el curso no fue encontrado
