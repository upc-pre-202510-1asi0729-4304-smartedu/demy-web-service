
Feature: Acceder con permisos según el rol
  Para interactuar únicamente con lo que me corresponde
  Como usuario del sistema
  Quiero que mis permisos estén definidos por mi rol

  Scenario Outline: Acceso según rol
    Given un UserAccount activo con role "<role>"
    When accedo a la sección "<section>"
    Then el acceso debe ser "<access>"

    Examples:
      | role      | section             | access      |
      | ADMIN     | Gestor de matrículas| permitido   |
      | DOCENTE   | Módulo de asistencia| permitido   |
      |ESTUDIANTE | Gestión financiera  | denegado    |