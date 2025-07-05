Feature: Registrarse como usuario en la plataforma
  Para acceder a las funcionalidades de la plataforma según mi rol
  Como visitante
  Quiero registrarme creando una cuenta

  Scenario Outline: Registro de nuevo usuario
    Given el formulario de registro está disponible
    When elijo registrarme como "<role>" y completo nombre "<fullName>" y correo "<email>" y contraseña "<password>"
    Then debe crearse un UserAccount con role "<role>" y status "PENDING"

    Examples:
      | role         | fullName         | email             | password     |
      | ADMINISTRATIVO | Juan Pérez      | juan@edu.com      | Admin123!    |
      | DOCENTE      | María López     | maria@edu.com     | Teach456$    |
      | ESTUDIANTE   | Luis Gómez      | luis@edu.com      | Stud789#     |