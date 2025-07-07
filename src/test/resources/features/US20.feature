Feature: Asignar horarios a los docentes y cursos
    Para evitar conflictos de disponibilidad
    Como administrativo
    Quiero asignar horarios a los docentes y cursos

    Scenario Outline: Asignación de horario exitosa
        Given que el usuario tiene rol administrativo
        And existe una materia con id "<courseId>" registrada en el periodo
        When selecciona horario "<startTime>" a "<endTime>" día "<dayOfWeek>" y aula "<classroomId>"
        Then el sistema guarda la asignación "<shouldSave>" y muestra mensaje "<message>"

        Examples:
            | courseId | startTime | endTime | dayOfWeek | classroomId | shouldSave | message                           |
            | 1        | 08:00     | 10:00   | MONDAY    | 101         | true       | Horario asignado exitosamente     |
            | 2        | 10:00     | 12:00   | TUESDAY   | 102         | true       | Horario asignado exitosamente     |
            | 3        | 14:00     | 16:00   | WEDNESDAY | 103         | true       | Horario asignado exitosamente     |
            | 4        | 16:00     | 18:00   | THURSDAY  | 104         | true       | Horario asignado exitosamente     |
            | 0        | 08:00     | 10:00   | MONDAY    | 101         | false      | Curso no válido                   |
            | 1        | (vacío)   | 10:00   | MONDAY    | 101         | false      | Hora de inicio es obligatoria     |
            | 1        | 08:00     | (vacío) | MONDAY    | 101         | false      | Hora de fin es obligatoria        |

    Scenario Outline: Validación de conflictos de horario
        Given que existe un horario con aula "<existingClassroom>" día "<existingDay>" de "<existingStart>" a "<existingEnd>"
        When se intenta asignar nuevo horario aula "<newClassroom>" día "<newDay>" de "<newStart>" a "<newEnd>"
        Then el resultado debe ser "<shouldAssign>" con mensaje "<message>"

        Examples:
            | existingClassroom | existingDay | existingStart | existingEnd | newClassroom | newDay    | newStart | newEnd | shouldAssign | message                           |
            | 101               | MONDAY      | 08:00         | 10:00       | 101          | MONDAY    | 09:00    | 11:00  | false        | Conflicto de horario detectado    |
            | 101               | MONDAY      | 08:00         | 10:00       | 101          | MONDAY    | 10:00    | 12:00  | true         | Horario asignado exitosamente     |
            | 101               | MONDAY      | 08:00         | 10:00       | 102          | MONDAY    | 08:00    | 10:00  | true         | Horario asignado exitosamente     |
            | 101               | MONDAY      | 08:00         | 10:00       | 101          | TUESDAY   | 08:00    | 10:00  | true         | Horario asignado exitosamente     |
            | 101               | MONDAY      | 08:00         | 10:00       | 101          | MONDAY    | 07:00    | 09:00  | false        | Conflicto de horario detectado    |

    Scenario Outline: Actualización de horario existente
        Given que existe un horario con id "<scheduleId>" para curso "<courseId>"
        When se actualiza con hora "<newStart>" a "<newEnd>" día "<newDay>" y aula "<newClassroom>"
        Then la actualización debe ser "<shouldUpdate>" con mensaje "<message>"

        Examples:
            | scheduleId | courseId | newStart | newEnd | newDay    | newClassroom | shouldUpdate | message                           |
            | 1          | 1        | 08:00    | 10:00  | MONDAY    | 101          | true         | Horario actualizado exitosamente |
            | 2          | 2        | 10:00    | 12:00  | TUESDAY   | 102          | true         | Horario actualizado exitosamente |
            | 3          | 3        | 14:00    | 16:00  | WEDNESDAY | 103          | true         | Horario actualizado exitosamente |
            | 1          | 1        | (vacío)  | 10:00  | MONDAY    | 101          | false        | Hora de inicio es obligatoria     |
            | 2          | 2        | 10:00    | (vacío)| TUESDAY   | 102          | false        | Hora de fin es obligatoria        |
            | 3          | 3        | 14:00    | 16:00  | (vacío)   | 103          | false        | Día de semana es obligatorio      |

    Scenario Outline: Validación de disponibilidad de aula
        Given que el aula "<classroomId>" tiene capacidad "<capacity>" y estado "<status>"
        When se intenta asignar horario para curso "<courseId>" con "<studentsCount>" estudiantes
        Then la asignación debe ser "<shouldAssign>" con mensaje "<message>"

        Examples:
            | classroomId | capacity | status     | courseId | studentsCount | shouldAssign | message                           |
            | 101         | 30       | AVAILABLE  | 1        | 25            | true         | Horario asignado exitosamente     |
            | 102         | 20       | AVAILABLE  | 2        | 25            | false        | Capacidad insuficiente del aula   |
            | 103         | 40       | MAINTENANCE| 3        | 30            | false        | Aula no disponible                |
            | 104         | 35       | AVAILABLE  | 4        | 30            | true         | Horario asignado exitosamente     |
            | 105         | 25       | AVAILABLE  | 5        | 25            | true         | Horario asignado exitosamente     |

    Scenario Outline: Validación de permisos de usuario
        Given que el usuario tiene rol "<userRole>"
        When intenta asignar horario para curso "<courseId>" en aula "<classroomId>"
        Then el acceso debe ser "<shouldAllow>" con mensaje "<message>"

        Examples:
            | userRole      | courseId | classroomId | shouldAllow | message                           |
            | administrativo| 1        | 101         | true        | Horario asignado exitosamente     |
            | coordinador   | 2        | 102         | true        | Horario asignado exitosamente     |
            | profesor      | 3        | 103         | false       | Permisos insuficientes            |
            | estudiante    | 4        | 104         | false       | Permisos insuficientes            |
            | invitado      | 5        | 105         | false       | Permisos insuficientes            |