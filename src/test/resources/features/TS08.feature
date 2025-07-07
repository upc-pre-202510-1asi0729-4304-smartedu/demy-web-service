Feature: Gestionar períodos académicos mediante la API
    Para registrar, actualizar o eliminar períodos académicos
    Como developer
    Quiero exponer endpoints RESTful para manejar la entidad AcademicPeriod

    Scenario Outline: Registro exitoso de período académico
        Given que los datos son válidos: nombre "<periodName>", fecha de inicio "<startDate>", fecha de fin "<endDate>", y estado activo "<isActive>"
        When se envía una solicitud POST a "/academic-periods"
        Then se responde con estado 201 Created

        Examples:
            | periodName      | startDate  | endDate    | isActive |
            | Semestre 2025-1 | 2025-03-01 | 2025-07-31 | true     |
            | Verano 2025     | 2025-01-15 | 2025-02-28 | false    |

    Scenario Outline: Fechas inválidas
        Given que la fecha de inicio "<startDate>" es posterior a la fecha de fin "<endDate>"
        When se intenta registrar un nuevo período
        Then se responde con estado 400 Bad Request

        Examples:
            | periodName      | startDate  | endDate    |
            | Semestre 2025-1 | 2025-08-01 | 2025-03-01 |
            | Verano 2025     | 2025-02-28 | 2025-01-01 |

    Scenario Outline: Período académico duplicado
        Given que ya existe un período académico con el nombre "<periodName>" y fechas similares
        When se intenta registrar nuevamente
        Then se responde con estado 409 Conflict

        Examples:
            | periodName      | startDate  | endDate    |
            | Semestre 2025-1 | 2025-03-01 | 2025-07-31 |

    Scenario Outline: Actualización exitosa
        Given que existe un período académico con ID "<periodId>" y el cuerpo contiene datos válidos
        When se envía una solicitud PUT a "/academic-periods/<periodId>"
        Then se responde con estado 200 OK

        Examples:
            | periodId |
            | 3001     |
            | 3002     |

    Scenario Outline: Actualización con ID inexistente
        Given que no existe un período académico con ID "<periodId>"
        When se envía una solicitud PUT a "/academic-periods/<periodId>"
        Then se responde con estado 404 Not Found

        Examples:
            | periodId |
            | 9999     |

    Scenario Outline: Eliminación exitosa
        Given que existe un período académico con ID "<periodId>"
        When se envía una solicitud DELETE a "/academic-periods/<periodId>"
        Then se responde con estado 204 No Content

        Examples:
            | periodId |
            | 3001     |

    Scenario Outline: Eliminación con ID inválido
        Given que no existe un período académico con ID "<periodId>"
        When se intenta eliminar
        Then se responde con estado 404 Not Found

        Examples:
            | periodId |
            | 9999     |
