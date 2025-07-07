Feature: Consultar asistencia de un alumno por DNI
    Para permitir al usuario ver la asistencia de un alumno
    Como developer
    Quiero exponer un endpoint GET que devuelva la lista de asistencias de un estudiante identificado por su DNI

    Scenario Outline: Consulta exitosa de asistencias
        Given que existen sesiones de clase registradas con asistencia del alumno con DNI "<dni>"
        When se consulta el endpoint GET "/attendances/dni/<dni>"
        Then se responde con 200 OK
        And se retorna un arreglo con las fechas de clase y su estado de asistencia:
            | courseId | date       | status   |
            | <courseId1> | <date1> | <status1> |
            | <courseId2> | <date2> | <status2> |

        Examples:
            | dni       | courseId1 | date1      | status1 | courseId2 | date2      | status2 |
            | 12345678  | C-101     | 2025-07-05 | PRESENT | C-102     | 2025-07-06 | ABSENT  |

    Scenario Outline: DNI no encontrado
        Given que no existe ningún estudiante con DNI "<dni>"
        When se consulta el endpoint GET "/attendances/dni/<dni>"
        Then se responde con 404 Not Found

        Examples:
            | dni      |
            | 00000000 |

    Scenario Outline: Estudiante sin asistencia registrada
        Given que el estudiante con DNI "<dni>" existe pero no tiene sesiones con asistencia
        When se consulta el endpoint GET "/attendances/dni/<dni>"
        Then se responde con 204 No Content

        Examples:
            | dni      |
            | 87654321 |
