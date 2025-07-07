Feature: Eliminar una matrícula registrada
  Para gestionar solicitudes de retiro o deserción
  Como administrativo
  Quiero eliminar una matrícula

  Scenario Outline: Eliminación de matrícula
    Given existe una Enrollment con id "<enrollmentId>" y enrollmentStatus "ACTIVE"
    When solicito eliminación de la matrícula "<enrollmentId>"
    Then la matrícula debe quedar con enrollmentStatus "<newStatus>" y mostrarse "<message>"

    Examples:
      | enrollmentId | newStatus | message                    |
      | 1001         | DELETED   | Eliminación exitosa        |
      | 9999         | ACTIVE    | Error: matrícula no existe |