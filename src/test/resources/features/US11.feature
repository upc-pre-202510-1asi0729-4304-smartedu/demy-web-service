Feature: Registrar asistencia de estudiantes por clase
  Para llevar registro de asistencia
  Como profesor
  Quiero registrar la asistencia de los estudiantes

  Scenario Outline: Registro de asistencia
    Given existe una ClassSession con id "<classId>" y un Student con dni "<dni>"
    When marco asistencia de "<dni>" como "<presence>"
    Then el sistema guarda attendance con status "<presence>"

    Examples:
      | classId | dni       | presence |
      | 2001    | 12345678  | PRESENT  |
      | 2001    | 87654321  | ABSENT   |