Feature: Obtener datos de horarios semanales
    Para que los usuarios puedan consultar la planificación académica
    Como developer
    Quiero exponer un endpoint para obtener todos los horarios semanales y otro para obtener un horario semanal por su ID

    Scenario: Horarios semanales encontrados
        Given que existen horarios semanales registrados en el sistema
        When consulto el endpoint GET "/api/v1/weeklyschedules"
        Then el sistema responde con HTTP 200
        And retorna una lista de horarios semanales, incluyendo:
            | id | name        |
            | 1  | Horario A   |
            | 2  | Horario UPC |

    Scenario: No hay horarios semanales registrados
        Given que no existen horarios semanales en el sistema
        When consulto el endpoint GET "/api/v1/weeklyschedules"
        Then el sistema responde con HTTP 200
        And retorna una lista vacía

    Scenario Outline: Horario semanal encontrado por ID
        Given que el horario semanal con ID "<scheduleId>" existe
        When consulto el endpoint GET "/api/v1/weeklyschedules/<scheduleId>"
        Then el sistema responde con HTTP 200
        And retorna los detalles del horario semanal, incluyendo:
            | name          | schedules                                               |
            | <name>        | Lista de objetos con: dayOfWeek, startTime, endTime, courseId, classroomId |

        Examples:
            | scheduleId | name        |
            | 1          | Horario A   |
            | 2          | Horario UPC |

    Scenario: Horario semanal no encontrado por ID
        Given que no existe un horario semanal con ID "999"
        When consulto el endpoint GET "/api/v1/weeklyschedules/999"
        Then el sistema responde con HTTP 404 Not Found
        And retorna un mensaje indicando que el horario semanal no fue encontrado
