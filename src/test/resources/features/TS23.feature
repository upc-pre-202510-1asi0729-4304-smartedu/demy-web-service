Feature: Obtener datos de aulas
    Para que los usuarios puedan consultar la información de las aulas disponibles
    Como developer
    Quiero exponer un endpoint para obtener todas las aulas y otro para obtener una aula específica por su ID

    Scenario: Aulas encontradas
        Given que existen aulas registradas en el sistema
        When consulto el endpoint GET "/api/v1/classrooms"
        Then el sistema responde con HTTP 200
        And retorna una lista de aulas con los siguientes campos:
            | code   | capacity | campus     |
            | A101   | 30       | San Isidro |
            | B202   | 25       | Monterrico |

    Scenario: No hay aulas registradas
        Given que no existen aulas en el sistema
        When consulto el endpoint GET "/api/v1/classrooms"
        Then el sistema responde con HTTP 200
        And retorna una lista vacía

    Scenario Outline: Aula encontrada por ID
        Given que el aula con ID "<classroomId>" existe en el sistema
        When consulto el endpoint GET "/api/v1/classrooms/<classroomId>"
        Then el sistema responde con HTTP 200
        And retorna los detalles del aula:
            | code     | capacity | campus     |
            | <code>   | <capacity> | <campus> |

        Examples:
            | classroomId | code | capacity | campus      |
            | 1           | A105 | 35       | San Miguel  |
            | 2           | C303 | 40       | Villa        |

    Scenario: Aula no encontrada por ID
        Given que no existe un aula con ID "999"
        When consulto el endpoint GET "/api/v1/classrooms/999"
        Then el sistema responde con HTTP 404
        And retorna un mensaje indicando que el aula no fue encontrada
