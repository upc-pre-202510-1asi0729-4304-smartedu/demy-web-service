Feature: Visualizar resumen de estudiantes matriculados
  Para tener una visión clara del número de alumnos
  Como administrativo
  Quiero visualizar un resumen de estudiantes matriculados

  Scenario Outline: Resumen por estado
    Given hay <total> matrículas con estado "<status>"
    When consulto el resumen filtrado por "<status>"
    Then debe mostrarse <total> registros

    Examples:
      | status   | total |
      | ACTIVE   | 45    |
      | CANCELLED| 5     |