Feature: Consultar disponibilidad de salones
    Para asignar clases de manera eficiente
    Como administrativo
    Quiero consultar la disponibilidad de salones

    Scenario Outline: Consulta de disponibilidad básica
        Given que el usuario tiene rol administrativo
        And existen salones con código "<code>", capacidad "<capacity>" y campus "<campus>"
        When selecciona un rango horario de "<startTime>" a "<endTime>"
        Then el sistema muestra "<shouldShow>" salones disponibles y mensaje "<message>"

        Examples:
            | code   | capacity | campus     | startTime | endTime | shouldShow | message                              |
            | A-101  | 30       | Norte      | 08:00     | 10:00   | true       | Salones disponibles encontrados     |
            | B-205  | 50       | Sur        | 14:00     | 16:00   | true       | Salones disponibles encontrados     |
            | C-301  | 25       | Centro     | 18:00     | 20:00   | true       | Salones disponibles encontrados     |
            | D-102  | 40       | Norte      | 22:00     | 24:00   | false      | No hay salones disponibles          |

    Scenario Outline: Consulta por capacidad específica
        Given que el usuario tiene rol administrativo
        And existen salones con código "<code>", capacidad "<capacity>" y campus "<campus>"
        When selecciona un rango horario y capacidad mínima de "<minCapacity>" estudiantes
        Then debe mostrar "<shouldShow>" el salón si cumple capacidad y mensaje "<message>"

        Examples:
            | code   | capacity | campus     | minCapacity | shouldShow | message                           |
            | A-101  | 30       | Norte      | 25          | true       | Salón cumple capacidad requerida  |
            | B-205  | 50       | Sur        | 45          | true       | Salón cumple capacidad requerida  |
            | C-301  | 25       | Centro     | 30          | false      | Capacidad insuficiente            |
            | D-102  | 40       | Norte      | 50          | false      | Capacidad insuficiente            |

    Scenario Outline: Consulta por campus específico
        Given que el usuario tiene rol administrativo
        And existen salones con código "<code>", capacidad "<capacity>" y campus "<campus>"
        When selecciona campus "<selectedCampus>" y rango horario
        Then debe mostrar "<shouldShow>" el salón si pertenece al campus y mensaje "<message>"

        Examples:
            | code   | capacity | campus     | selectedCampus | shouldShow | message                        |
            | A-101  | 30       | Norte      | Norte          | true       | Salón disponible en campus     |
            | B-205  | 50       | Sur        | Sur            | true       | Salón disponible en campus     |
            | C-301  | 25       | Centro     | Norte          | false      | Salón no pertenece al campus   |
            | D-102  | 40       | Norte      | Sur            | false      | Salón no pertenece al campus   |

    Scenario Outline: Validación de código de salón
        Given que el usuario tiene rol administrativo
        When consulta disponibilidad para salón con código "<code>"
        Then debe validar "<isValid>" el formato del código y mostrar "<message>"

        Examples:
            | code   | isValid | message                     |
            | A-101  | true    | Código válido               |
            | B-205  | true    | Código válido               |
            | XYZ    | false   | Formato de código inválido  |
            | 123    | false   | Formato de código inválido  |
            | ""     | false   | Código no puede estar vacío |