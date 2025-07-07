Feature: Ver bandeja de entrada con notificaciones
    Para estar al pendiente de alertas o recordatorios
    Como usuario
    Quiero visualizar las notificaciones en una bandeja de entrada

    Scenario Outline: Visualización de las notificaciones
        Given que el usuario se encuentra en la vista "Mi organización"
        And tiene notificaciones con tipo "<notificationType>" y estado "<status>"
        When presione el botón de campanita
        Then el sistema muestra "<shouldShow>" la bandeja de notificaciones con mensaje "<message>"

        Examples:
            | notificationType | status | shouldShow | message                              |
            | ALERT           | UNREAD | true       | Bandeja de notificaciones mostrada   |
            | REMINDER        | UNREAD | true       | Bandeja de notificaciones mostrada   |
            | INFO            | READ   | true       | Bandeja de notificaciones mostrada   |
            | WARNING         | UNREAD | true       | Bandeja de notificaciones mostrada   |

    Scenario Outline: Visualización con contador de notificaciones
        Given que el usuario tiene "<unreadCount>" notificaciones sin leer
        And "<readCount>" notificaciones leídas
        When presione el botón de campanita
        Then debe mostrar "<shouldShow>" el contador y mensaje "<message>"

        Examples:
            | unreadCount | readCount | shouldShow | message                           |
            | 5           | 3         | true       | 5 notificaciones sin leer         |
            | 0           | 10        | false      | Todas las notificaciones leídas   |
            | 15          | 0         | true       | 15 notificaciones sin leer        |
            | 0           | 0         | false      | Sin notificaciones                |

    Scenario Outline: Visualización por tipo de notificación
        Given que el usuario tiene notificaciones de tipo "<notificationType>"
        And con prioridad "<priority>"
        And fecha de creación "<createdDate>"
        When presione el botón de campanita
        Then debe mostrar "<shouldShow>" la notificación ordenada por prioridad y mensaje "<message>"

        Examples:
            | notificationType | priority | createdDate | shouldShow | message                              |
            | ALERT           | HIGH     | 2025-01-01  | true       | Alerta de alta prioridad mostrada    |
            | REMINDER        | MEDIUM   | 2025-01-02  | true       | Recordatorio de prioridad media      |
            | INFO            | LOW      | 2025-01-03  | true       | Información de baja prioridad        |
            | WARNING         | HIGH     | 2025-01-04  | true       | Advertencia de alta prioridad        |

    Scenario Outline: Filtrado de notificaciones
        Given que el usuario tiene notificaciones de diferentes tipos
        And selecciona filtro por tipo "<filterType>"
        When presione el botón de campanita
        Then debe mostrar "<shouldShow>" solo las notificaciones del tipo seleccionado y mensaje "<message>"

        Examples:
            | filterType | shouldShow | message                              |
            | ALERT      | true       | Solo alertas mostradas               |
            | REMINDER   | true       | Solo recordatorios mostrados         |
            | INFO       | true       | Solo información mostrada            |
            | ALL        | true       | Todas las notificaciones mostradas   |

    Scenario Outline: Marcar notificaciones como leídas
        Given que el usuario tiene notificaciones con estado "<initialStatus>"
        And presiona el botón de campanita
        When selecciona una notificación
        Then debe cambiar "<shouldChange>" el estado a "<finalStatus>" y mostrar "<message>"

        Examples:
            | initialStatus | finalStatus | shouldChange | message                           |
            | UNREAD        | READ        | true         | Notificación marcada como leída   |
            | READ          | READ        | false        | Notificación ya estaba leída      |
            | UNREAD        | READ        | true         | Estado actualizado correctamente  |

    Scenario Outline: Eliminar notificaciones
        Given que el usuario tiene notificaciones en la bandeja
        And selecciona notificación con id "<notificationId>"
        When presiona el botón eliminar
        Then debe eliminar "<shouldDelete>" la notificación y mostrar "<message>"

        Examples:
            | notificationId | shouldDelete | message                           |
            | 1              | true         | Notificación eliminada            |
            | 2              | true         | Notificación eliminada            |
            | 3              | true         | Notificación eliminada            |
            | 999            | false        | Notificación no encontrada        |

    Scenario Outline: Acciones desde notificaciones
        Given que el usuario tiene una notificación de tipo "<notificationType>"
        And la notificación tiene acción asociada "<actionType>"
        When presiona la notificación
        Then debe ejecutar "<shouldExecute>" la acción y mostrar "<message>"

        Examples:
            | notificationType | actionType     | shouldExecute | message                           |
            | ALERT           | VIEW_DETAILS   | true          | Redirigiendo a detalles           |
            | REMINDER        | MARK_COMPLETE  | true          | Recordatorio completado           |
            | INFO            | OPEN_LINK      | true          | Abriendo enlace                   |
            | WARNING         | TAKE_ACTION    | true          | Acción requerida                  |

    Scenario Outline: Validación de permisos de acceso
        Given que el usuario tiene rol "<userRole>"
        When intenta acceder a la bandeja de notificaciones
        Then debe permitir "<shouldAllow>" el acceso y mostrar "<message>"

        Examples:
            | userRole       | shouldAllow | message                              |
            | administrativo | true        | Acceso autorizado a notificaciones   |
            | docente        | true        | Acceso autorizado a notificaciones   |
            | estudiante     | true        | Acceso autorizado a notificaciones   |
            | guest          | false       | Sin permisos para ver notificaciones |

    Scenario Outline: Bandeja vacía
        Given que el usuario no tiene notificaciones
        When presione el botón de campanita
        Then debe mostrar "<shouldShow>" mensaje de bandeja vacía "<message>"

        Examples:
            | shouldShow | message                           |
            | true       | No tienes notificaciones          |
            | true       | Bandeja de entrada vacía          |
            | true       | Sin notificaciones pendientes     |