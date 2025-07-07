Feature: Consultar una factura por su ID
    Para que los usuarios puedan revisar la información completa de una factura
    Como developer
    Quiero exponer un endpoint para obtener los detalles de una factura por su ID

    Scenario Outline: Factura encontrada
        Given que el ID de la factura "<invoiceId>" es válido y existe en el sistema
        When se consulta el endpoint GET "/api/v1/invoices/<invoiceId>"
        Then el sistema responde con HTTP 200 OK
        And retorna los siguientes datos de la factura:
            | id  | dni       | name          | amount | dueDate    | status   |
            | <id> | <dni>    | <name>        | <amount> | <dueDate> | <status> |

        Examples:
            | invoiceId | id | dni       | name          | amount | dueDate    | status   |
            | 1         | 1  | 12345678  | Carlos Ruiz   | 150.00 | 2025-08-15 | PENDING  |
            | 2         | 2  | 87654321  | Ana Delgado   | 200.00 | 2025-09-01 | PAID     |

    Scenario: Factura no encontrada
        Given que no existe una factura con ID "999"
        When se consulta el endpoint GET "/api/v1/invoices/999"
        Then el sistema responde con HTTP 404 Not Found
        And retorna un mensaje indicando que la factura no fue encontrada
