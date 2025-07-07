Feature: Consultar matrícula por DNI
    Para que los administradores puedan acceder a la matrícula sin conocer el ID interno
    Como developer
    Quiero exponer un endpoint GET que retorne la matrícula asociada a un DNI de estudiante

    Scenario Outline: Matrícula encontrada por DNI
        Given que el DNI "<dni>" corresponde a un estudiante con matrícula registrada
        When se consulta el endpoint GET "/enrollments/dni/<dni>"
        Then se responde con estado 200 OK
        And el cuerpo contiene studentId "<studentId>", academicPeriodId "<periodId>", weeklyScheduleId "<scheduleId>", amount "<amount>", currency "<currency>", enrollmentStatus "<enrollmentStatus>" y paymentStatus "<paymentStatus>"

        Examples:
            | dni        | studentId | periodId | scheduleId | amount | currency | enrollmentStatus | paymentStatus |
            | 45878965   | 1001      | 2025-1   | W01        | 500.00 | PEN      | ACTIVE           | PAID          |
            | 70654321   | 1002      | 2025-2   | W02        | 450.00 | USD      | ACTIVE           | UNPAID        |

    Scenario Outline: DNI no encontrado
        Given que no existe ningún estudiante con el DNI "<dni>"
        When se consulta el endpoint GET "/enrollments/dni/<dni>"
        Then se responde con estado 404 Not Found

        Examples:
            | dni      |
            | 99999999 |
            | 12345678 |

    Scenario Outline: Estudiante sin matrícula
        Given que el DNI "<dni>" corresponde a un estudiante registrado pero sin matrícula
        When se consulta el endpoint GET "/enrollments/dni/<dni>"
        Then se responde con estado 204 No Content

        Examples:
            | dni      |
            | 44556677 |
            | 77889900 |
