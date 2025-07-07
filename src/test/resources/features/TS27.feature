Feature: Gestión de facturas (crear, actualizar, eliminar)
    Para que los administradores puedan gestionar correctamente los cobros a los estudiantes
    Como developer
    Quiero exponer endpoints para crear, actualizar y eliminar facturas

    Scenario Outline: Registro exitoso de factura
        Given que el cuerpo de la solicitud contiene datos válidos:
            | dni       | name       | amount | dueDate    |
            | <dni>     | <name>     | <amount> | <dueDate> |
        When se realiza una solicitud POST al endpoint "/api/v1/invoices"
        Then el sistema responde con HTTP 201 Created
        And retorna los datos de la factura registrada con estado "PENDING"

        Examples:
            | dni         | name         | amount | dueDate    |
            | 12345678    | Carlos Ruiz  | 150.00 | 2025-08-15 |
            | 87654321    | Ana Delgado  | 200.00 | 2025-09-01 |

    Scenario: Factura duplicada
        Given que ya existe una factura con los mismos datos (DNI, monto y fecha de vencimiento)
        When se realiza una solicitud POST al endpoint "/api/v1/invoices"
        Then el sistema responde con HTTP 409 Conflict
        And retorna un mensaje indicando que la factura ya existe

    Scenario Outline: Actualización exitosa de factura
        Given que existe una factura con ID "<invoiceId>"
        And el cuerpo contiene datos válidos:
            | dni     | name       | amount | dueDate    | status    |
            | <dni>   | <name>     | <amount> | <dueDate> | <status>  |
        When se realiza una solicitud PUT al endpoint "/api/v1/invoices/<invoiceId>"
        Then el sistema responde con HTTP 200 OK
        And retorna los datos actualizados de la factura

        Examples:
            | invoiceId | dni       | name         | amount | dueDate    | status     |
            | 1         | 12345678  | Carlos Ruiz  | 180.00 | 2025-08-20 | PAID       |
            | 2         | 87654321  | Ana Delgado  | 220.00 | 2025-09-10 | PENDING    |

    Scenario: Eliminación exitosa de factura
        Given que existe una factura con ID "3"
        When se realiza una solicitud DELETE al endpoint "/api/v1/invoices/3"
        Then el sistema responde con HTTP 204 No Content
        And la factura es eliminada del sistema

    Scenario Outline: ID inexistente en PUT o DELETE
        Given que no existe una factura con ID "<invalidId>"
        When se realiza una solicitud PUT o DELETE al endpoint con ese ID
        Then el sistema responde con HTTP 404 Not Found
        And retorna un mensaje indicando que la factura no fue encontrada

        Examples:
            | invalidId |
            | 999       |
            | 888       |
