Feature: Gestión de horarios semanales (crear, actualizar, eliminar)
    Para permitir la administración de horarios académicos
    Como developer
    Quiero exponer endpoints para registrar, actualizar y eliminar horarios semanales

    Scenario Outline: Horario semanal registrado correctamente
        Given que los datos del horario semanal son válidos:
            | name     | schedules                                                                      |
            | <name>   | Lista de objetos con: dayOfWeek, startTime, endTime, courseId, classroomId    |
        When envío una petición POST al endpoint "/api/v1/weeklyschedules"
        Then el sistema responde con HTTP 201 Created
        And retorna los detalles del horario semanal registrado

        Examples:
            | name        |
            | Horario A   |
            | Ciclo 2025A |

    Scenario Outline: Datos inválidos para registro
        Given que los datos del horario semanal son inválidos:
            | name     | schedules             |
            | <name>   | Datos incorrectos     |
        When envío una petición POST al endpoint "/api/v1/weeklyschedules"
        Then el sistema responde con HTTP 400 Bad Request
        And retorna un mensaje con los errores de validación

        Examples:
            | name     |
            | A        |

    Scenario Outline: Horario semanal actualizado correctamente
        Given que existe un horario semanal con ID "<scheduleId>"
        And los datos enviados son válidos:
            | name     | schedules                                                                      |
            | <name>   | Lista de objetos con: dayOfWeek, startTime, endTime, courseId, classroomId    |
        When envío una petición PUT al endpoint "/api/v1/weeklyschedules/<scheduleId>"
        Then el sistema responde con HTTP 200 OK
        And retorna los detalles actualizados del horario semanal

        Examples:
            | scheduleId | name          |
            | 1          | Horario 2025A |
            | 2          | Plan B        |

    Scenario: Horario semanal no encontrado para actualizar
        Given que no existe un horario semanal con ID "999"
        When envío una petición PUT al endpoint "/api/v1/weeklyschedules/999"
        Then el sistema responde con HTTP 404 Not Found
        And retorna un mensaje indicando que el horario semanal no fue encontrado

    Scenario: Horario semanal eliminado correctamente
        Given que existe un horario semanal con ID "3"
        When envío una petición DELETE al endpoint "/api/v1/weeklyschedules/3"
        Then el sistema responde con HTTP 204 No Content
        And el horario es eliminado del sistema

    Scenario: Horario semanal no encontrado para eliminar
        Given que no existe un horario semanal con ID "999"
        When envío una petición DELETE al endpoint "/api/v1/weeklyschedules/999"
        Then el sistema responde con HTTP 404 Not Found
        And retorna un mensaje indicando que el horario semanal no fue encontrado
