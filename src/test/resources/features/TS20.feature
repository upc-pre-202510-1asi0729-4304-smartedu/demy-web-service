Feature: Consultar pagos de una factura
    Para hacer seguimiento del estado de los cobros
    Como developer
    Quiero exponer un endpoint que permita listar los pagos asociados a una factura

    Scenario Outline: Pagos encontrados para una factura existente
        Given que existe una factura con ID "<invoiceId>" y tiene pagos registrados
        When consulto el endpoint GET "/api/v1/payments/<invoiceId>"
        Then el sistema responde con HTTP 200
        And retorna una lista con los pagos asociados
        And cada pago incluye:
            | amount | method     | paidAt               |
            | <amount> | <method> | <paidAt>            |

        Examples:
            | invoiceId | amount   | method    | paidAt                 |
            | 1         | 100.00   | CREDIT_CARD | 2024-06-10T14:30:00  |
            | 1         | 50.00    | CASH      | 2024-07-01T10:15:00  |

    Scenario: Factura sin pagos registrados
        Given que existe una factura con ID "2" y no tiene pagos asociados
        When consulto el endpoint GET "/api/v1/payments/2"
        Then el sistema responde con HTTP 200
        And retorna un arreglo vacío

    Scenario: Factura inexistente
        Given que no existe una factura con ID "999"
        When consulto el endpoint GET "/api/v1/payments/999"
        Then el sistema responde con HTTP 404
        And retorna un mensaje indicando que la factura no fue encontrada
