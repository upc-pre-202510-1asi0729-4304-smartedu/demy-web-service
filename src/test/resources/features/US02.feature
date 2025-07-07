Feature: Cerrar sesión de forma segura
  Para evitar el uso indebido de mi cuenta
  Como usuario autenticado
  Quiero cerrar sesión de forma segura

  Scenario Outline: Cierre de sesión
    Given un UserAccount autenticado con email "<email>"
    When cierro sesión
    Then el estado de sesión debe ser "<sessionState>"

    Examples:
      | email         | sessionState |
      | admin@edu.com | finalizada   |
      | user@edu.com  | finalizada   |
