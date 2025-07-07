Feature: Actualizar calendario del docente
    Para estar siempre al tanto de las reprogramaciones
    Como profesor
    Quiero visualizar automáticamente la reprogramación de la clase en mi horario

    Scenario Outline: Visualización de reprogramación básica
        Given que el usuario tiene rol docente
        And tiene una clase reprogramada con courseId "<courseId>" y classroomId "<classroomId>"
        And el horario original era "<originalDay>" de "<originalStartTime>" a "<originalEndTime>"
        And fue reprogramada para "<newDay>" de "<newStartTime>" a "<newEndTime>"
        When accede a su calendario
        Then el sistema muestra "<shouldShow>" el horario actualizado y mensaje "<message>"

        Examples:
            | courseId | classroomId | originalDay | originalStartTime | originalEndTime | newDay   | newStartTime | newEndTime | shouldShow | message                           |
            | 1        | 101         | MONDAY      | 08:00            | 10:00          | TUESDAY  | 08:00        | 10:00      | true       | Horario actualizado correctamente |
            | 2        | 205         | WEDNESDAY   | 14:00            | 16:00          | FRIDAY   | 14:00        | 16:00      | true       | Horario actualizado correctamente |
            | 3        | 301         | FRIDAY      | 18:00            | 20:00          | MONDAY   | 18:00        | 20:00      | true       | Horario actualizado correctamente |
            | 4        | 102         | TUESDAY     | 10:00            | 12:00          | THURSDAY | 10:00        | 12:00      | true       | Horario actualizado correctamente |

    Scenario Outline: Visualización con múltiples reprogramaciones
        Given que el usuario tiene rol docente
        And tiene múltiples clases reprogramadas en la semana
        And clase con courseId "<courseId1>" fue movida a "<day1>" de "<startTime1>" a "<endTime1>"
        And clase con courseId "<courseId2>" fue movida a "<day2>" de "<startTime2>" a "<endTime2>"
        When accede a su calendario
        Then debe mostrar "<shouldShowAll>" todas las reprogramaciones y mensaje "<message>"

        Examples:
            | courseId1 | day1    | startTime1 | endTime1 | courseId2 | day2      | startTime2 | endTime2 | shouldShowAll | message                                 |
            | 1         | MONDAY  | 08:00      | 10:00    | 2         | WEDNESDAY | 14:00      | 16:00    | true          | Todas las reprogramaciones mostradas   |
            | 3         | TUESDAY | 10:00      | 12:00    | 4         | THURSDAY  | 18:00      | 20:00    | true          | Todas las reprogramaciones mostradas   |
            | 5         | FRIDAY  | 16:00      | 18:00    | 6         | MONDAY    | 20:00      | 22:00    | true          | Todas las reprogramaciones mostradas   |

    Scenario Outline: Validación de TimeRange actualizado
        Given que el usuario tiene rol docente
        And existe un Schedule con TimeRange de "<startTime>" a "<endTime>"
        And dayOfWeek es "<dayOfWeek>"
        When la clase es reprogramada con nuevo TimeRange "<newStartTime>" a "<newEndTime>"
        Then debe actualizar "<shouldUpdate>" el calendario y validar "<isValid>" el horario

        Examples:
            | startTime | endTime | dayOfWeek | newStartTime | newEndTime | shouldUpdate | isValid | message                    |
            | 08:00     | 10:00   | MONDAY    | 09:00        | 11:00      | true         | true    | TimeRange actualizado      |
            | 14:00     | 16:00   | WEDNESDAY | 15:00        | 17:00      | true         | true    | TimeRange actualizado      |
            | 18:00     | 20:00   | FRIDAY    | 19:00        | 21:00      | true         | true    | TimeRange actualizado      |
            | 10:00     | 12:00   | TUESDAY   | 23:00        | 01:00      | false        | false   | Horario inválido           |

    Scenario Outline: Actualización automática del WeeklySchedule
        Given que el usuario tiene rol docente
        And pertenece a un WeeklySchedule
        And tiene Schedule con courseId "<courseId>" y classroomId "<classroomId>"
        When la clase es reprogramada
        Then debe actualizar "<shouldUpdate>" automáticamente el WeeklySchedule y mostrar "<message>"

        Examples:
            | courseId | classroomId | shouldUpdate | message                             |
            | 1        | 101         | true         | WeeklySchedule actualizado          |
            | 2        | 205         | true         | WeeklySchedule actualizado          |
            | 3        | 301         | true         | WeeklySchedule actualizado          |
            | 4        | 102         | true         | WeeklySchedule actualizado          |

    Scenario Outline: Visualización con conflictos de horario
        Given que el usuario tiene rol docente
        And tiene una clase reprogramada para "<day>" de "<startTime>" a "<endTime>"
        And ya existe otra clase en el mismo horario
        When accede a su calendario
        Then debe mostrar "<shouldShowConflict>" el conflicto y mensaje "<message>"

        Examples:
            | day       | startTime | endTime | shouldShowConflict | message                           |
            | MONDAY    | 08:00     | 10:00   | true               | Conflicto detectado en horario    |
            | WEDNESDAY | 14:00     | 16:00   | true               | Conflicto detectado en horario    |
            | FRIDAY    | 18:00     | 20:00   | false              | Sin conflictos                    |
            | TUESDAY   | 20:00     | 22:00   | false              | Sin conflictos                    |

    Scenario Outline: Validación de permisos de visualización
        Given que el usuario tiene rol "<userRole>"
        And existe un Schedule con courseId "<courseId>"
        When intenta acceder al calendario
        Then debe permitir "<shouldAllow>" la visualización y mostrar "<message>"

        Examples:
            | userRole       | courseId | shouldAllow | message                        |
            | docente        | 1        | true        | Acceso autorizado al calendario|
            | profesor       | 2        | true        | Acceso autorizado al calendario|
            | administrativo | 3        | false       | Sin permisos de visualización  |
            | estudiante     | 4        | false       | Sin permisos de visualización  |