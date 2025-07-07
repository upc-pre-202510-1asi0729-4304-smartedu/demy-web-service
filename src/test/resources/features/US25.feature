Feature: Ver plan actual y sus características
    Para saber los beneficios que tengo actualmente
    Como administrativo
    Quiero visualizar mi plan actual de suscripción

    Scenario Outline: Visualización del plan actual
        Given que el usuario tiene un plan "<currentPlan>" activo
        And el plan incluye características "<features>"
        When presiona el botón "Ver mi Plan Actual"
        Then el sistema muestra "<shouldShow>" el plan actual junto a sus características y mensaje "<message>"

        Examples:
            | currentPlan | features                         | shouldShow | message                              |
            | BASIC       | 5 usuarios, 10GB storage        | true       | Plan actual mostrado correctamente   |
            | PREMIUM     | 20 usuarios, 50GB storage       | true       | Plan actual mostrado correctamente   |
            | ENTERPRISE  | 100 usuarios, 500GB storage     | true       | Plan actual mostrado correctamente   |
            | FREE        | 1 usuario, 1GB storage          | true       | Plan actual mostrado correctamente   |

    Scenario Outline: Visualización con detalles de facturación
        Given que el usuario tiene plan "<planType>" con precio "<monthlyPrice>"
        And fecha de próxima facturación "<nextBillingDate>"
        And estado de suscripción "<subscriptionStatus>"
        When presiona el botón "Ver mi Plan Actual"
        Then debe mostrar "<shouldShow>" los detalles de facturación y mensaje "<message>"

        Examples:
            | planType   | monthlyPrice | nextBillingDate | subscriptionStatus | shouldShow | message                           |
            | BASIC      | 29.99        | 2025-01-15     | ACTIVE             | true       | Detalles de facturación mostrados |
            | PREMIUM    | 59.99        | 2025-01-20     | ACTIVE             | true       | Detalles de facturación mostrados |
            | ENTERPRISE | 99.99        | 2025-01-25     | ACTIVE             | true       | Detalles de facturación mostrados |
            | FREE       | 0.00         | null           | ACTIVE             | false      | Plan gratuito sin facturación     |

    Scenario Outline: Visualización con límites de uso
        Given que el usuario tiene plan "<planType>"
        And límite de usuarios "<userLimit>"
        And límite de almacenamiento "<storageLimit>"
        And uso actual de usuarios "<currentUsers>" y almacenamiento "<currentStorage>"
        When presiona el botón "Ver mi Plan Actual"
        Then debe mostrar "<shouldShow>" los límites y uso actual con mensaje "<message>"

        Examples:
            | planType | userLimit | storageLimit | currentUsers | currentStorage | shouldShow | message                        |
            | BASIC    | 5         | 10GB         | 3            | 6GB           | true       | Límites y uso mostrados        |
            | PREMIUM  | 20        | 50GB         | 15           | 30GB          | true       | Límites y uso mostrados        |
            | ENTERPRISE| 100      | 500GB        | 80           | 400GB         | true       | Límites y uso mostrados        |
            | FREE     | 1         | 1GB          | 1            | 0.5GB         | true       | Límites y uso mostrados        |

    Scenario Outline: Visualización con alertas de límite
        Given que el usuario tiene plan "<planType>"
        And uso actual de usuarios "<currentUsers>" de límite "<userLimit>"
        And uso actual de almacenamiento "<currentStorage>" de límite "<storageLimit>"
        When presiona el botón "Ver mi Plan Actual"
        Then debe mostrar "<shouldShowAlert>" alerta de límite y mensaje "<alertMessage>"

        Examples:
            | planType | currentUsers | userLimit | currentStorage | storageLimit | shouldShowAlert | alertMessage                    |
            | BASIC    | 5            | 5         | 9GB           | 10GB          | true            | Límite de usuarios alcanzado    |
            | PREMIUM  | 18           | 20        | 45GB          | 50GB          | true            | Próximo a límite de almacenamiento|
            | ENTERPRISE| 50          | 100       | 200GB         | 500GB         | false           | Uso dentro de límites normales  |
            | FREE     | 1            | 1         | 1GB           | 1GB           | true            | Límites alcanzados              |

    Scenario Outline: Visualización con estado de suscripción
        Given que el usuario tiene plan "<planType>"
        And estado de suscripción "<status>"
        And fecha de vencimiento "<expiryDate>"
        When presiona el botón "Ver mi Plan Actual"
        Then debe mostrar "<shouldShow>" el estado y mensaje "<message>"

        Examples:
            | planType | status    | expiryDate | shouldShow | message                              |
            | BASIC    | ACTIVE    | 2025-12-31 | true       | Suscripción activa                   |
            | PREMIUM  | EXPIRED   | 2024-12-31 | true       | Suscripción vencida - Renovar        |
            | ENTERPRISE| CANCELLED| 2025-01-15 | true       | Suscripción cancelada                |
            | FREE     | ACTIVE    | null       | true       | Plan gratuito activo                 |

    Scenario Outline: Validación de permisos de visualización
        Given que el usuario tiene rol "<userRole>"
        When intenta presionar "Ver mi Plan Actual"
        Then debe permitir "<shouldAllow>" la visualización y mostrar "<message>"

        Examples:
            | userRole       | shouldAllow | message                           |
            | administrativo | true        | Acceso autorizado al plan actual  |
            | admin          | true        | Acceso autorizado al plan actual  |
            | docente        | false       | Sin permisos para ver suscripción |
            | estudiante     | false       | Sin permisos para ver suscripción |

    Scenario Outline: Visualización con opciones de actualización
        Given que el usuario tiene plan "<currentPlan>"
        And existen planes superiores disponibles "<availableUpgrades>"
        When presiona el botón "Ver mi Plan Actual"
        Then debe mostrar "<shouldShow>" opciones de actualización y mensaje "<message>"

        Examples:
            | currentPlan | availableUpgrades      | shouldShow | message                           |
            | FREE        | BASIC,PREMIUM,ENTERPRISE| true      | Opciones de upgrade disponibles   |
            | BASIC       | PREMIUM,ENTERPRISE     | true       | Opciones de upgrade disponibles   |
            | PREMIUM     | ENTERPRISE             | true       | Opciones de upgrade disponibles   |
            | ENTERPRISE  | none                   | false      | Ya tienes el plan más alto        |