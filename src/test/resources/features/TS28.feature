Feature: Consultar facturas por estudiante
    Para facilitar la gestión financiera de los estudiantes
    Como developer
    Quiero exponer un endpoint para obtener todas las facturas asociadas a un estudiante por su ID

    Scenario Outline: Facturas encontradas
        Given que el estudiante con ID "<studentId>" tiene facturas registradas
        When se consulta el endpoint GET "/api/v1/invoices/<studentId>"
        Then el sistema responde con HTTP 200 OK
        And retorna una lista con los siguientes campos por cada factura:
            | id | dni       | name          | amount | dueDate    | status  |
            | 1  | <dni>     | <name>        | <amount> | <dueDate> | <status> |

        Examples:
            | studentId | dni       | name          | amount | dueDate    | status   |
            | 1         | 12345678  | Carlos Ruiz   | 150.00 | 2025-08-15 | PENDING  |
            | 2         | 87654321  | Ana Delgado   | 200.00 | 2025-09-01 | PAID     |

    Scenario: Estudiante sin facturas
        Given que el estudiante con ID "3" no tiene facturas registradas
        When se consulta el endpoint GET "/api/v1/invoices/3"
        Then el sistema responde con HTTP 200 OK
        And retorna una lista vacía

    Scenario: Estudiante inexistente
        Given que no existe un estudiante con ID "999"
        When se consulta el endpoint GET "/api/v1/invoices/999"
        Then el sistema responde con HTTP 404 Not Found
        And retorna un mensaje indicando que el estudiante no fue encontrado
