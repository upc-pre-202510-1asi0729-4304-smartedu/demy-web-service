Feature: Registrar matrícula de un estudiante
  Para que se almacenen sus datos y se acceda a funcionalidades adicionales
  Como administrativo
  Quiero registrar alumnos en la aplicación web

  Scenario Outline: Registro de matrícula
    Given existe un Student con id <studentId>
    And existe un AcademicPeriod con id <academicPeriodId>
    And existe un WeeklySchedule con id <weeklyScheduleId>
    When intento registrar la matrícula con amount <amount> y currency <currency>
    Then debe crearse una Enrollment con
      | studentId         | <studentId>         |
      | academicPeriodId  | <academicPeriodId>  |
      | weeklyScheduleId  | <weeklyScheduleId>  |
      | amount            | <amount>            |
      | currency          | "<currency>"        |
      | enrollmentStatus  | "ACTIVE"            |
      | paymentStatus     | "PAID"              |
    And el mensaje final es "<message>"

    Examples:
      |studentId|academicPeriodId |weeklyScheduleId| amount  | currency | message     |
      | 5       | 7               |1               | 1500.00 | PEN      | Test Passed |
      | 6       | 8               |2               | -500.00 | PEN      | Error       |
      | 7       | 9               | 1              | 1500.00 | PEN      | Test Passed |