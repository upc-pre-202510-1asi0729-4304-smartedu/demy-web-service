Feature: Consultar asistencia de un estudiante en un periodo
  Para observar su desempeño en los cursos
  Como usuario
  Quiero consultar la asistencia de un estudiante en un periodo

  Scenario Outline: Consulta de asistencia
    Given existe un Student con dni "<dni>" y un AcademicPeriod con id "<periodId>"
    When consulto asistencia de "<dni>" en periodo "<periodId>"
    Then el sistema muestra "<result>"

    Examples:
      | dni       | periodId | result                            |
      | 12345678  | 1        | lista de fechas y estados         |
      | 12345678  | 99       | mensaje "sin registros para periodo"|
