Feature: Gestión de aulas (crear, actualizar, eliminar)
    Para mantener actualizada la información de aulas en el sistema
    Como developer
    Quiero exponer endpoints para registrar, actualizar y eliminar aulas

    Scenario Outline: Aula registrada correctamente
        Given que los datos enviados del aula son válidos:
            | code    | capacity | campus      |
            | <code>  | <capacity> | <campus> |
        When envío una petición POST al endpoint "/api/v1/classrooms"
        Then el sistema responde con HTTP 201 Created
        And retorna los detalles del aula registrada

        Examples:
            | code | capacity | campus     |
            | A301 | 40       | San Isidro |
            | B102 | 25       | Monterrico |

    Scenario Outline: Datos inválidos para registro
        Given que los datos enviados del aula son inválidos:
            | code   | capacity | campus   |
            | <code> | <capacity> | <campus> |
        When envío una petición POST al endpoint "/api/v1/classrooms"
        Then el sistema responde con HTTP 400 Bad Request
        And retorna un mensaje de error con los campos inválidos

        Examples:
            | code | capacity | campus   |
            |      | 30       | Surco    |
            | C101 | -10      | Villa    |
            | D202 | 20       |          |

    Scenario Outline: Aula actualizada correctamente
        Given que existe un aula con ID "<classroomId>"
        And los nuevos datos enviados son válidos:
            | code   | capacity | campus     |
            | <code> | <capacity> | <campus> |
        When envío una petición PUT al endpoint "/api/v1/classrooms/<classroomId>"
        Then el sistema responde con HTTP 200 OK
        And retorna los detalles actualizados del aula

        Examples:
            | classroomId | code | capacity | campus   |
            | 1           | A105 | 35       | Surco    |
            | 2           | B204 | 50       | San Miguel |

    Scenario: Aula no encontrada para actualizar
        Given que no existe un aula con ID "999"
        When envío una petición PUT al endpoint "/api/v1/classrooms/999"
        Then el sistema responde con HTTP 404 Not Found
        And retorna un mensaje indicando que el aula no fue encontrada

    Scenario: Aula eliminada correctamente
        Given que existe un aula con ID "3"
        When envío una petición DELETE al endpoint "/api/v1/classrooms/3"
        Then el sistema responde con HTTP 204 No Content
        And el aula es eliminada del sistema

    Scenario: Aula no encontrada para eliminar
        Given que no existe un aula con ID "999"
        When envío una petición DELETE al endpoint "/api/v1/classrooms/999"
        Then el sistema responde con HTTP 404 Not Found
        And retorna un mensaje indicando que el aula no fue encontrada
