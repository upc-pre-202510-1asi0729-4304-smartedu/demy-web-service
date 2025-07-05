Feature: Recuperar acceso mediante correo

  Scenario Outline: Solicitud de recuperación de contraseña
    Dado existe un UserAccount con "<email>" y "<status>"
    Cuando solicito recuperación de contraseña ingresando "<email>"
    Entonces el sistema debe "<notification>"

    Examples:
      | email             | status  | notification                                                  |
      | registered@edu.com| ACTIVE  | enviar enlace temporal al correo                              |
      | unknown@edu.com   | —       | informar que no existe cuenta vinculada a ese correo          |
