Feature: Listar períodos académicos
    Para consultar todas las ventanas de matrícula definidas
    Como developer
    Quiero exponer un endpoint GET que devuelva todos los períodos académicos registrados

    Scenario: Existen períodos académicos registrados
        Given que existen uno o más períodos académicos en el sistema
        When se consulta el endpoint GET "/academic-periods"
        Then se responde con estado 200 OK
        And el cuerpo contiene un arreglo de objetos con los siguientes campos:
            | periodId | periodName      | startDate  | endDate    | isActive |
            | 3001     | Semestre 2025-1 | 2025-03-01 | 2025-07-31 | true     |
            | 3002     | Verano 2025     | 2025-01-15 | 2025-02-28 | false    |

    Scenario: No existen períodos académicos registrados
        Given que no existe ningún período académico en el sistema
        When se consulta el endpoint GET "/academic-periods"
        Then se responde con estado 200 OK
        And el cuerpo contiene un arreglo vacío
