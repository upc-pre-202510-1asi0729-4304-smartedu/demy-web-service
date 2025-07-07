Feature: Gestionar matrículas mediante la API
    Para que los administradores puedan gestionar las inscripciones de estudiantes
    Como developer
    Quiero exponer endpoints RESTful para registrar, modificar y eliminar matrículas

    Scenario Outline: Registro exitoso de matrícula
        Given el cuerpo contiene datos válidos con studentId "<studentId>", academicPeriodId "<periodId>", weeklyScheduleId "<scheduleId>", amount "<amount>", currency "<currency>", enrollmentStatus "<enrollmentStatus>" y paymentStatus "<paymentStatus>"
        When se envía una solicitud POST a "/enrollments"
        Then se responde con estado 201 Created

        Examples:
            | studentId | periodId | scheduleId | amount | currency | enrollmentStatus | paymentStatus |
            | 1001      | 2025-1   | W01        | 500.00 | PEN      | ACTIVE           | PAID          |
            | 1002      | 2025-1   | W02        | 450.00 | USD      | ACTIVE           | UNPAID        |

    Scenario Outline: Matrícula duplicada
        Given que ya existe una matrícula con studentId "<studentId>" en el periodo "<periodId>" y horario "<scheduleId>"
        When se intenta registrar una nueva matrícula con los mismos datos
        Then se responde con estado 409 Conflict

        Examples:
            | studentId | periodId | scheduleId |
            | 1001      | 2025-1   | W01        |

    Scenario Outline: Actualización exitosa de matrícula
        Given que existe la matrícula con ID "<enrollmentId>" y el cuerpo contiene nuevos datos válidos
        When se envía una solicitud PUT a "/enrollments/<enrollmentId>"
        Then se responde con estado 200 OK

        Examples:
            | enrollmentId |
            | 3001         |
            | 3002         |

    Scenario Outline: Error por cuerpo inválido
        Given que el cuerpo de la solicitud contiene errores como campos faltantes o mal formateados
        When se envía una solicitud "<method>" a "/enrollments"
        Then se responde con estado 400 Bad Request

        Examples:
            | method |
            | POST   |
            | PUT    |

    Scenario Outline: Eliminación exitosa de matrícula
        Given que existe una matrícula con ID "<enrollmentId>"
        When se envía una solicitud DELETE a "/enrollments/<enrollmentId>"
        Then se responde con estado 204 No Content

        Examples:
            | enrollmentId |
            | 3001         |
            | 3002         |

    Scenario Outline: Matrícula no encontrada al eliminar
        Given que no existe una matrícula con ID "<enrollmentId>"
        When se intenta eliminar
        Then se responde con estado 404 Not Found

        Examples:
            | enrollmentId |
            | 9999         |
