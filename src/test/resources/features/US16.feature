Feature: Enviar alertas automáticas por pagos vencidos
    Para mantener informados a los apoderados y asegurar una mejor gestión financiera
    Como administrativo
    Quiero que el sistema envíe alertas automáticas sobre el estado de los pagos

    Scenario Outline: Alerta automática por pago vencido
        Given que un estudiante con dni "<dni>" tiene una deuda con fecha de vencimiento "<dueDate>" y estado "<status>"
        When se evalúa el estado de la deuda en fecha "<currentDate>"
        Then debe enviarse alerta "<shouldSend>" por "<channel>" con mensaje "<message>"

        Examples:
            | dni       | dueDate    | status  | currentDate | shouldSend | channel  | message                                    |
            | 12345678  | 2024-11-15 | PENDING | 2024-11-16 | true       | email    | Pago vencido - Acción requerida           |
            | 87654321  | 2024-11-15 | PENDING | 2024-11-16 | true       | whatsapp | Pago vencido - Acción requerida           |
            | 11223344  | 2024-11-15 | PAID    | 2024-11-16 | false      | none     | No se requiere alerta                     |
            | 99887766  | 2024-12-01 | PENDING | 2024-11-16 | false      | none     | Pago aún no vencido                       |

    Scenario Outline: Alerta de pago completado
        Given que existe una factura con id "<invoiceId>" y dni "<dni>"
        When se confirma el pago con método "<paymentMethod>" en fecha "<paidAt>"
        Then debe enviarse confirmación "<shouldSend>" por "<channel>" con mensaje "<message>"

        Examples:
            | invoiceId | dni       | paymentMethod | paidAt              | shouldSend | channel  | message                                   |
            | 1         | 12345678  | CREDIT_CARD   | 2024-11-20T10:00:00 | true       | email    | Pago confirmado - Factura cancelada       |
            | 2         | 87654321  | BANK_TRANSFER | 2024-11-18T14:30:00 | true       | whatsapp | Pago confirmado - Factura cancelada       |
            | 3         | 11223344  | CASH          | 2024-11-19T09:15:00 | true       | email    | Pago confirmado - Factura cancelada       |
            | 4         | 99887766  | null          | null                | false      | none     | Pago no confirmado                        |

    Scenario Outline: Recordatorio previo al vencimiento
        Given que un estudiante con dni "<dni>" tiene una deuda con vencimiento "<dueDate>"
        When se evalúa en fecha "<currentDate>" con días de anticipación "<reminderDays>"
        Then debe enviarse recordatorio "<shouldSend>" por "<channel>" con mensaje "<message>"

        Examples:
            | dni       | dueDate    | currentDate | reminderDays | shouldSend | channel  | message                                   |
            | 12345678  | 2024-11-20 | 2024-11-17  | 3            | true       | email    | Recordatorio: Pago vence en 3 días        |
            | 87654321  | 2024-11-20 | 2024-11-19  | 1            | true       | whatsapp | Recordatorio: Pago vence mañana           |
            | 11223344  | 2024-11-20 | 2024-11-15  | 3            | false      | none     | Muy temprano para recordatorio            |
            | 99887766  | 2024-11-20 | 2024-11-21  | 3            | false      | none     | Fecha ya vencida                          |