Feature: Reprogramar clase por cambio de horario
    Para garantizar el aprendizaje de mis alumnos sin retrasos
    Como profesor
    Quiero reprogramar una clase por cambio de horario

    Scenario Outline: Reprogramación exitosa
        Given que el usuario tiene rol docente
        And accede a la gestión de clases
        And selecciona "Reprogramar clase" para clase con id "<classId>"
        And indica la nueva hora de "<newStartTime>" a "<newEndTime>"
        And el sistema le muestra salones disponibles con código "<availableClassroom>"
        When el docente selecciona el salón "<selectedClassroom>"
        Then el sistema guarda la nueva programación "<shouldSave>" y muestra mensaje "<message>"

        Examples:
            | classId | newStartTime | newEndTime | availableClassroom | selectedClassroom | shouldSave | message                           |
            | 1       | 08:00        | 10:00      | A-101              | A-101             | true       | Clase reprogramada exitosamente   |
            | 2       | 14:00        | 16:00      | B-205              | B-205             | true       | Clase reprogramada exitosamente   |
            | 3       | 18:00        | 20:00      | C-301              | C-301             | true       | Clase reprogramada exitosamente   |
            | 4       | 10:00        | 12:00      | D-102              | D-102             | true       | Clase reprogramada exitosamente   |

    Scenario Outline: Reprogramación con conflicto de horario
        Given que el usuario tiene rol docente
        And existe una clase con id "<classId>" programada para "<originalTime>"
        And indica la nueva hora de "<newStartTime>" a "<newEndTime>"
        When el sistema verifica disponibilidad
        Then debe mostrar "<shouldShowConflict>" conflicto y mensaje "<message>"

        Examples:
            | classId | originalTime | newStartTime | newEndTime | shouldShowConflict | message                              |
            | 1       | 08:00-10:00  | 09:00        | 11:00      | true               | Conflicto: Horario ya ocupado        |
            | 2       | 14:00-16:00  | 15:00        | 17:00      | true               | Conflicto: Horario ya ocupado        |
            | 3       | 18:00-20:00  | 06:00        | 08:00      | false              | Horario disponible                   |
            | 4       | 10:00-12:00  | 20:00        | 22:00      | false              | Horario disponible                   |

    Scenario Outline: Reprogramación sin salones disponibles
        Given que el usuario tiene rol docente
        And selecciona "Reprogramar clase" para clase con id "<classId>"
        And indica la nueva hora de "<newStartTime>" a "<newEndTime>"
        When el sistema busca salones disponibles
        Then debe mostrar "<hasAvailableRooms>" salones y mensaje "<message>"

        Examples:
            | classId | newStartTime | newEndTime | hasAvailableRooms | message                           |
            | 1       | 08:00        | 10:00      | true              | Salones disponibles encontrados   |
            | 2       | 12:00        | 14:00      | true              | Salones disponibles encontrados   |
            | 3       | 19:00        | 21:00      | false             | No hay salones disponibles        |
            | 4       | 22:00        | 24:00      | false             | No hay salones disponibles        |

    Scenario Outline: Validación de permisos docente
        Given que el usuario tiene rol "<userRole>"
        And intenta reprogramar clase con id "<classId>"
        When el sistema verifica permisos
        Then debe permitir "<shouldAllow>" la reprogramación y mostrar "<message>"

        Examples:
            | userRole      | classId | shouldAllow | message                              |
            | docente       | 1       | true        | Acceso autorizado                    |
            | profesor      | 2       | true        | Acceso autorizado                    |
            | administrativo| 3       | false       | Sin permisos para reprogramar        |
            | estudiante    | 4       | false       | Sin permisos para reprogramar        |

    Scenario Outline: Notificación a estudiantes
        Given que el docente reprograma exitosamente clase con id "<classId>"
        And la clase tiene "<studentCount>" estudiantes inscritos
        When el sistema guarda la nueva programación
        Then debe enviar "<shouldNotify>" notificaciones y mostrar "<message>"

        Examples:
            | classId | studentCount | shouldNotify | message                                    |
            | 1       | 25           | true         | Notificaciones enviadas a 25 estudiantes  |
            | 2       | 15           | true         | Notificaciones enviadas a 15 estudiantes  |
            | 3       | 0            | false        | No hay estudiantes para notificar          |
            | 4       | 30           | true         | Notificaciones enviadas a 30 estudiantes  |