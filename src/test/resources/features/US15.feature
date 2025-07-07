Feature: Consultar estado de cuenta de un estudiante
    Para tener visibilidad de pagos realizados, deudas pendientes y fechas de vencimiento
    Como administrativo
    Quiero consultar el estado de cuenta de un estudiante

        Scenario Outline: Consultar estado de cuenta completo
            Given existe un estudiante con dni "<dni>" y facturas con estados "<invoiceStatuses>"
            When el administrador consulta el estado de cuenta para "<dni>"
            Then debe mostrarse el historial completo "<shouldShow>" y el mensaje "<message>"

            Examples:
                | dni       | invoiceStatuses | shouldShow | message                                   |
                | 12345678  | PAID,PENDING    | true       | Estado de cuenta mostrado correctamente   |
                | 87654321  | PAID,PAID       | true       | Historial de pagos completado             |
                | 11223344  | PENDING,PENDING | true       | Estado con deudas pendientes              |
                | 99887766  | (vacío)         | false      | No se encontraron registros               |

        Scenario Outline: Consultar estado con alerta de deuda
            Given existe un estudiante con dni "<dni>" y facturas con fecha de vencimiento "<dueDate>" y estado "<status>"
            When el administrador visualiza el estado de cuenta
            Then debe mostrarse alerta "<shouldShowAlert>" con el mensaje "<alertMessage>"

            Examples:
                | dni       | dueDate    | status  | shouldShowAlert | alertMessage                             |
                | 12345678  | 2024-12-01 | PENDING | true            | Alerta: Deuda pendiente próxima a vencer |
                | 87654321  | 2024-11-15 | PENDING | true            | Alerta: Deuda vencida - Acción requerida |
                | 11223344  | 2024-12-01 | PAID    | false           | Sin alertas                              |
                | 99887766  | 2025-02-01 | PENDING | false           | Sin alertas                              |

        Scenario Outline: Verificar montos y métodos de pago
            Given existe una factura con id "<invoiceId>" y monto "<amount>" en moneda "<currency>"
            And existe un pago con método "<paymentMethod>" y fecha "<paidAt>"
            When se consulta el detalle del estado de cuenta
            Then debe mostrarse el monto "<displayAmount>", método "<displayMethod>" y estado "<paymentStatus>"

            Examples:
                | invoiceId | amount | currency | paymentMethod | paidAt              | displayAmount | displayMethod | paymentStatus |
                | 1         | 500.00 | USD      | CREDIT_CARD   | 2024-11-20T10:00:00 | $500.00 USD   | Tarjeta       | Pagado        |
                | 2         | 250.50 | USD      | BANK_TRANSFER | 2024-11-18T14:30:00 | $250.50 USD   | Transferencia | Pagado        |
                | 3         | 300.00 | USD      | null          | null                | $300.00 USD   | -             | Pendiente     |