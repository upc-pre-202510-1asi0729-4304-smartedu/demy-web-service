Feature: Registrar egresos
    Para mantener un control actualizado de los gastos y generar reportes precisos
    Como administrativo
    Quiero registrar los egresos financieros del centro educativo

    Scenario Outline: Registrar egreso básico
        Given que el administrador está en el módulo financiero
        When selecciona "Nuevo egreso" e ingresa concepto "<concept>", monto "<amount>" y fecha "<date>"
        Then el sistema registra el egreso "<shouldRegister>" y actualiza el balance con mensaje "<message>"

        Examples:
            | concept           | amount | date       | shouldRegister | message                           |
            | Pago electricidad | 150.00 | 2024-11-20 | true          | Egreso registrado correctamente   |
            | Compra útiles     | 250.50 | 2024-11-18 | true          | Egreso registrado correctamente   |
            | Mantenimiento     | 300.00 | 2024-11-15 | true          | Egreso registrado correctamente   |
            | (vacío)           | 100.00 | 2024-11-20 | false         | Concepto es obligatorio           |
            | Pago salarios     | 0.00   | 2024-11-20 | false         | Monto debe ser mayor a cero       |
            | Servicios varios  | 200.00 | (vacío)    | false         | Fecha es obligatoria              |

    Scenario Outline: Registrar egreso con categoría
        Given que se especifica una categoría "<category>" para el egreso
        When se guarda el egreso con concepto "<concept>" y monto "<amount>"
        Then el sistema lo agrupa "<shouldGroup>" bajo esa categoría con resultado "<result>"

        Examples:
            | category       | concept           | amount | shouldGroup | result                                      |
            | Pago Docentes  | Salario profesor  | 800.00 | true        | Egreso agrupado en Pago Docentes           |
            | Materiales     | Compra libros     | 150.00 | true        | Egreso agrupado en Materiales              |
            | Servicios      | Pago internet     | 80.00  | true        | Egreso agrupado en Servicios               |
            | Infraestructura| Reparación techo  | 500.00 | true        | Egreso agrupado en Infraestructura         |
            | (vacío)        | Gasto varios      | 100.00 | false       | Categoría es obligatoria                   |
            | Categoría Nueva| Compra software   | 200.00 | true        | Nueva categoría creada y egreso agrupado   |

    Scenario Outline: Actualizar balance después de egreso
        Given que existe un balance inicial de "<initialBalance>"
        When se registra un egreso de "<expenseAmount>" con estado "<status>"
        Then el nuevo balance debe ser "<newBalance>" con mensaje "<message>"

        Examples:
            | initialBalance | expenseAmount | status    | newBalance | message                           |
            | 5000.00        | 150.00        | CONFIRMED | 4850.00    | Balance actualizado correctamente |
            | 2000.00        | 300.00        | CONFIRMED | 1700.00    | Balance actualizado correctamente |
            | 1000.00        | 1200.00       | CONFIRMED | -200.00    | Advertencia: Balance negativo     |
            | 3000.00        | 100.00        | PENDING   | 3000.00    | Egreso pendiente de confirmación  |
            | 0.00           | 50.00         | CONFIRMED | -50.00     | Advertencia: Balance negativo     |