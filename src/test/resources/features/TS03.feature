Feature: Listar matrículas
    Para que los administradores puedan consultar todas las matrículas registradas
    Como developer
    Quiero exponer un endpoint GET que devuelva el listado completo de matrículas

    Scenario Outline: Con registros existentes
        Given que existen matrículas en el sistema
        When se consulta el endpoint GET "/enrollments"
        Then se responde con estado 200 OK
        And el cuerpo contiene una lista con al menos las siguientes matrículas:
            | enrollmentId | studentId | periodId | scheduleId | amount | currency | enrollmentStatus | paymentStatus |
            | <enrollmentId1> | <studentId1> | <periodId1> | <scheduleId1> | <amount1> | <currency1> | <enrollmentStatus1> | <paymentStatus1> |
            | <enrollmentId2> | <studentId2> | <periodId2> | <scheduleId2> | <amount2> | <currency2> | <enrollmentStatus2> | <paymentStatus2> |

        Examples:
            | enrollmentId1 | studentId1 | periodId1 | scheduleId1 | amount1 | currency1 | enrollmentStatus1 | paymentStatus1 |
            | 3001          | 1001       | 2025-1    | W01         | 500.00  | PEN       | ACTIVE             | PAID           |
            | 3002          | 1002       | 2025-2    | W02         | 450.00  | USD       | ACTIVE             | UNPAID         |

    Scenario: Sin registros
        Given que no existe ninguna matrícula en el sistema
        When se consulta el endpoint GET "/enrollments"
        Then se responde con estado 200 OK
        And el cuerpo contiene un arreglo vacío
