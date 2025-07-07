Feature: Gestionar transacciones financieras
    Para registrar y consultar ingresos y egresos en la plataforma Demy
    Como developer
    Quiero exponer los endpoints POST y GET en /financial-transactions

    Scenario Outline: Registro exitoso de transacción
        Given que el cuerpo de la solicitud contiene datos válidos:
            | tipo        | categoría     | concepto             | fecha                | monto  | moneda  |
            | <type>      | <category>    | <concept>            | <date>               | <amount> | <currency> |
        When se realiza el POST a "/financial-transactions"
        Then se responde con 201 Created
        And la transacción queda registrada en el historial

        Examples:
            | type      | category   | concept                   | date                | amount | currency |
            | INCOME    | ENROLLMENT | Pago de matrícula         | 2025-07-05T10:30:00 | 300.00 | PEN      |
            | EXPENSE   | SALARY     | Pago a docente invitado   | 2025-07-06T14:45:00 | 150.00 | PEN      |

    Scenario: Consulta de transacciones con registros existentes
        Given que existen una o más transacciones registradas
        When se realiza un GET a "/financial-transactions"
        Then se responde con 200 OK
        And se devuelve la lista de transacciones con sus datos: tipo, categoría, concepto, fecha y monto

    Scenario: Consulta sin transacciones registradas
        Given que aún no se ha registrado ninguna transacción
        When se realiza un GET a "/financial-transactions"
        Then se responde con 200 OK
        And se retorna un arreglo vacío
