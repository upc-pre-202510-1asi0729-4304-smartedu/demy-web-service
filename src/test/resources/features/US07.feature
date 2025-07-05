Feature: Consultar historial de matrículas por estudiante
  Para verificar su estado académico y tomar decisiones informadas
  Como administrativo
  Quiero consultar el historial de matrículas de un estudiante

  Scenario Outline: Búsqueda de historial
    Given existe un Student con dni "<dni>"
    When consulto el historial de matrículas con dni "<dni>"
    Then el sistema debe mostrar "<result>"

    Examples:
      | dni       | result                                             |
      | 12345678  | lista de matrículas con detalles                   |
      | 99999999  | mensaje "historial vacío"                          |