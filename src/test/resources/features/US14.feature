Feature: Registrar pago de un estudiante
  Para llevar un control preciso de su estado financiero
  Como administrativo
  Quiero registrar el pago de un estudiante

  Scenario Outline: Registro de pago
    Given existe un Student con dni "<dni>" y una Invoice con dueDate "<dueDate>" y amount <dueAmount> "<currency>"
    When registro pago de "<dni>" por monto <payAmount> "<currency>"
    Then la Invoice refleja paidAmount <paid> "<currency>" y muestra "<message>"

    Examples:
      | dni       | dueDate    | dueAmount | payAmount | paid   | currency | message                        |
      | 12345678  | 2025-07-01 | 1000.00   | 1000.00   | 1000.00| PEN      | Pago registrado exitosamente   |
      | 12345678  | 2025-07-01 | 1000.00   |  500.00   |  500.00| PEN      | Pago parcial registrado        |
      | 12345678  | 2025-06-01 | 1000.00   | 1500.00   | 1500.00| PEN      | Pago excede el monto pendiente |