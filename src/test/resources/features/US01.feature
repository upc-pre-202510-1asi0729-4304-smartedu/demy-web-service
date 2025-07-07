Feature: Iniciar sesión como usuario autenticado
  Para acceder a las funcionalidades permitidas por mi rol
  Como usuario del sistema
  Quiero iniciar sesión con mis credenciales

  Scenario Outline: Intento de inicio de sesión
    Given existe un UserAccount con email "<email>" y passwordHash correspondiente a la contraseña "<password>" y rol "<role>" y status "<status>"
    When intento iniciar sesión con email "<email>" y contraseña "<password>"
    Then el resultado debe ser "<outcome>"

    Examples:
      | email               | password     | role      | status  | outcome                             |
      | admin@edu.com       | Admin123!    | ADMIN     | ACTIVE  | acceso concedido                    |
      | user@edu.com        | WrongPass    | USER      | ACTIVE  | credenciales inválidas              |
      | locked@edu.com      | LockPass456  | USER      | LOCKED  | cuenta bloqueada