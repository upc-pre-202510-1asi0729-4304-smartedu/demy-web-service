Feature: Consultar período académico por ID
    Para ver sus fechas y estado
    Como developer
    Quiero exponer un endpoint GET que devuelva un período académico por su ID

    Scenario Outline: Período académico encontrado
        Given que el ID "<periodId>" corresponde a un período académico registrado
        When se consulta el endpoint GET "/academic-periods/<periodId>"
        Then se responde con estado 200 OK
        And el cuerpo contiene:
            | periodId | periodName      | startDate  | endDate    | isActive |
            | <periodId> | <periodName>  | <startDate> | <endDate> | <isActive> |

        Examples:
            | periodId | periodName      | startDate  | endDate    | isActive |
            | 3001     | Semestre 2025-1 | 2025-03-01 | 2025-07-31 | true     |
            | 3002     | Verano 2025     | 2025-01-15 | 2025-02-28 | false    |

    Scenario Outline: Período académico no encontrado
        Given que el ID "<periodId>" no corresponde a ningún período registrado
        When se consulta el endpoint GET "/academic-periods/<periodId>"
        Then se responde con estado 404 Not Found

        Examples:
            | periodId |
            | 9999     |
            | 8888     |
