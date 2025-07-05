Feature: Enviar alertas sobre inasistencias
  Para informar a los responsables
  Como usuario
  Quiero enviar alertas sobre inasistencias

  Scenario Outline: Alerta de inasistencia
    Given existe un Student con dni "<dni>" y attendance count <count> ausencias
    When solicito alerta para "<dni>"
    Then debe enviarse alerta si <shouldSend> y mostrarse "<message>"

    Examples:
      | dni       | count | shouldSend | message                         |
      | 12345678  | 3     | true       | Alerta enviada correctamente    |
      | 87654321  | 0     | false      | No hay inasistencias            |
