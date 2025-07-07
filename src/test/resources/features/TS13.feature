Feature: Consultar asistencia de alumnos en un curso
    Para revisar las asistencias de los alumnos inscritos en un curso
    Como developer
    Quiero exponer un endpoint GET que devuelva todas las sesiones de clase con asistencia registradas para un curso específico

    Scenario Outline: Consulta exitosa de asistencias por curso
        Given que existen sesiones de clase con asistencia registradas para el curso "<courseId>"
        When se consulta el endpoint GET "/attendances/course/<courseId>"
        Then se responde con 200 OK
        And se retorna una lista de sesiones de clase con las asistencias de los alumnos:
            | date       | studentId | status   |
            | <date1>    | <sId1>     | <status1> |
            | <date1>    | <sId2>     | <status2> |
            | <date2>    | <sId1>     | <status3> |

        Examples:
            | courseId | date1      | sId1     | status1 | sId2     | status2 | status3 |
            | C-101    | 2025-07-05 | S-001    | PRESENT | S-002    | ABSENT  | PRESENT |

    Scenario Outline: Curso sin sesiones registradas
        Given que el curso "<courseId>" existe pero no tiene sesiones de clase con asistencia
        When se consulta el endpoint GET "/attendances/course/<courseId>"
        Then se responde con 204 No Content

        Examples:
            | courseId |
            | C-999    |

    Scenario Outline: Curso inexistente
        Given que el curso "<courseId>" no existe en el sistema
        When se consulta el endpoint GET "/attendances/course/<courseId>"
        Then se responde con 404 Not Found

        Examples:
            | courseId |
            | C-000    |
