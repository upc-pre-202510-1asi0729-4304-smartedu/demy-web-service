Feature: Consultar matrícula por ID
    Para que los administradores puedan obtener los detalles de una inscripción específica
    Como developer
    Quiero exponer un endpoint GET que retorne la información de una matrícula según su ID

    Scenario Outline: Matrícula encontrada
        Given que existe una matrícula con ID "<enrollmentId>"
        When se consulta el endpoint GET "/enrollments/<enrollmentId>"
        Then se responde con estado 200 OK
        And el cuerpo contiene studentId "<studentId>", academicPeriodId "<periodId>", weeklyScheduleId "<scheduleId>", amount "<amount>", currency "<currency>", enrollmentStatus "<enrollmentStatus>" y paymentStatus "<paymentStatus>"

        Examples:
            | enrollmentId | studentId | periodId | scheduleId | amount | currency | enrollmentStatus | paymentStatus |
            | 3001         | 1001      | 2025-1   | W01        | 500.00 | PEN      | ACTIVE           | PAID          |
            | 3002         | 1002      | 2025-2   | W02        | 450.00 | USD      | ACTIVE           | UNPAID        |

    Scenario Outline: Matrícula no encontrada
        Given que no existe una matrícula con ID "<enrollmentId>"
        When se consulta el endpoint GET "/enrollments/<enrollmentId>"
        Then se responde con estado 404 Not Found

        Examples:
            | enrollmentId |
            | 9999         |
            | 8888         |
