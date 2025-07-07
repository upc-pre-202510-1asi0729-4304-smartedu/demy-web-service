Feature: Editar datos de una matrícula existente
  Para mantener el registro académico al día
  Como administrativo
  Quiero editar los datos de una matrícula

  Scenario Outline: Edición de matrícula
    Given existe una Enrollment con id "<enrollmentId>" y amount <oldAmount> "<currency>"
    When actualizo amount a <newAmount> "<currency>"
    Then la matrícula debe reflejar amount <newAmount> "<currency>" y mensaje "<message>"

    Examples:
      | enrollmentId | oldAmount | newAmount | currency | message               |
      | 1001         | 1500.00   | 1600.00   | PEN      | Actualización exitosa |
      | 1002         | 1500.00   | -200.00   | PEN      | Error: monto inválido |