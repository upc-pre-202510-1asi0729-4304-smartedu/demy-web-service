Feature: Generar reporte financiero mensual
    Para visualizar el estado económico del centro educativo y tomar decisiones informadas
    Como administrativo
    Quiero generar reportes financieros

    Scenario Outline: Generar reporte mensual estándar
        Given que se selecciona mes "<month>" y año "<year>"
        When el administrador hace clic en "Generar reporte"
        Then el sistema muestra "<shouldShow>" con ingresos "<income>", egresos "<expenses>" y balance "<balance>"

        Examples:
            | month | year | shouldShow | income  | expenses | balance |
            | 11    | 2024 | true       | 5000.00 | 3000.00  | 2000.00 |
            | 10    | 2024 | true       | 4500.00 | 2800.00  | 1700.00 |
            | 12    | 2024 | true       | 6000.00 | 4000.00  | 2000.00 |
            | 02    | 2025 | false      | 0.00    | 0.00     | 0.00    |
            | 13    | 2024 | false      | 0.00    | 0.00     | 0.00    |
            | 11    | 2023 | true       | 3000.00 | 2500.00  | 500.00  |

    Scenario Outline: Reporte con filtros personalizados por categoría
        Given que el administrador aplica filtro de categoría "<category>" para mes "<month>" y año "<year>"
        When genera el reporte
        Then el sistema muestra información detallada "<shouldShow>" con total "<total>" y mensaje "<message>"

        Examples:
            | category       | month | year | shouldShow | total   | message                                   |
            | Pago Docentes  | 11    | 2024 | true       | 1500.00 | Reporte de Pago Docentes generado         |
            | Materiales     | 11    | 2024 | true       | 800.00  | Reporte de Materiales generado            |
            | Servicios      | 11    | 2024 | true       | 400.00  | Reporte de Servicios generado             |
            | Colegiaturas   | 11    | 2024 | true       | 4000.00 | Reporte de Colegiaturas generado          |
            | Categoria X    | 11    | 2024 | false      | 0.00    | No se encontraron registros para categoría|
            | (vacío)        | 11    | 2024 | true       | 5000.00 | Reporte general sin filtros               |

    Scenario Outline: Reporte con filtros por tipo de movimiento
        Given que el administrador aplica filtro de tipo "<movementType>" para mes "<month>" y año "<year>"
        When genera el reporte
        Then el sistema muestra información "<shouldShow>" con total "<total>" y detalle "<detail>"

        Examples:
            | movementType | month | year | shouldShow | total   | detail                                |
            | INGRESO      | 11    | 2024 | true       | 5000.00 | Detalle de todos los ingresos del mes |
            | EGRESO       | 11    | 2024 | true       | 3000.00 | Detalle de todos los egresos del mes  |
            | AMBOS        | 11    | 2024 | true       | 8000.00 | Detalle completo de movimientos       |
            | INGRESO      | 02    | 2025 | false      | 0.00    | No hay datos para el período          |
            | EGRESO       | 13    | 2024 | false      | 0.00    | Mes inválido                          |

    Scenario Outline: Validación de parámetros de reporte
        Given que se intenta generar reporte con mes "<month>", año "<year>" y filtros "<filters>"
        When se validan los parámetros
        Then el resultado debe ser "<isValid>" con mensaje "<message>"

        Examples:
            | month | year | filters           | isValid | message                              |
            | 11    | 2024 | category:Docentes | true    | Parámetros válidos                   |
            | 0     | 2024 | none              | false   | Mes debe estar entre 1 y 12          |
            | 12    | 1999 | none              | false   | Año debe ser mayor a 2000            |
            | 11    | 2024 | type:INVALID      | false   | Tipo de movimiento inválido          |
            | 11    | 2024 | none              | true    | Parámetros válidos                   |