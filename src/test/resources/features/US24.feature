Feature: Elegir plan de suscripción y método de pago
    Para escoger la suscripción que más se me acomode
    Como administrativo
    Quiero visualizar las distintas suscripciones que ofrece Demy

    Scenario Outline: Visualización de las suscripciones
        Given que el usuario tiene rol administrativo
        And es su primera vez ingresando a Demy
        And termina su registro de cuenta
        When el sistema verifica el estado de suscripción
        Then debe mostrar "<shouldShow>" los planes disponibles con tipo "<planType>", precio "<price>" y mensaje "<message>"

        Examples:
            | planType | price  | shouldShow | message                                  |
            | BASIC    | 29.99  | true       | Planes de suscripción disponibles       |
            | PREMIUM  | 59.99  | true       | Planes de suscripción disponibles       |
            | ENTERPRISE| 99.99 | true       | Planes de suscripción disponibles       |
            | FREE     | 0.00   | true       | Plan gratuito disponible                |

    Scenario Outline: Elegir método de pago
        Given que el usuario se encuentra en la vista de suscripciones
        And selecciona un plan "<selectedPlan>" con precio "<planPrice>"
        When confirma la selección del plan
        Then el sistema le muestra "<shouldShow>" los métodos de pago disponibles "<paymentMethod>" con mensaje "<message>"

        Examples:
            | selectedPlan | planPrice | paymentMethod | shouldShow | message                              |
            | BASIC        | 29.99     | CREDIT_CARD   | true       | Métodos de pago disponibles          |
            | PREMIUM      | 59.99     | BANK_TRANSFER | true       | Métodos de pago disponibles          |
            | ENTERPRISE   | 99.99     | PAYPAL        | true       | Métodos de pago disponibles          |
            | FREE         | 0.00      | NONE          | false      | Plan gratuito no requiere pago       |

    Scenario Outline: Validación de plan seleccionado
        Given que el usuario tiene rol administrativo
        And está en la vista de suscripciones
        When selecciona plan "<planType>" con características "<features>"
        Then debe validar "<isValid>" la selección y mostrar "<message>"

        Examples:
            | planType   | features                    | isValid | message                           |
            | BASIC      | 5 usuarios, 10GB storage   | true    | Plan válido seleccionado          |
            | PREMIUM    | 20 usuarios, 50GB storage  | true    | Plan válido seleccionado          |
            | ENTERPRISE | 100 usuarios, 500GB storage| true    | Plan válido seleccionado          |
            | CUSTOM     | características personalizadas| false   | Plan no disponible               |

    Scenario Outline: Proceso de confirmación de suscripción
        Given que el usuario selecciona plan "<planType>" con precio "<price>"
        And elige método de pago "<paymentMethod>"
        When confirma la suscripción
        Then debe procesar "<shouldProcess>" el pago y mostrar "<message>"

        Examples:
            | planType | price | paymentMethod | shouldProcess | message                           |
            | BASIC    | 29.99 | CREDIT_CARD   | true          | Suscripción procesada exitosamente|
            | PREMIUM  | 59.99 | BANK_TRANSFER | true          | Suscripción procesada exitosamente|
            | ENTERPRISE| 99.99| PAYPAL        | true          | Suscripción procesada exitosamente|
            | FREE     | 0.00  | NONE          | true          | Plan gratuito activado            |

    Scenario Outline: Validación de permisos de suscripción
        Given que el usuario tiene rol "<userRole>"
        When intenta acceder a la vista de suscripciones
        Then debe permitir "<shouldAllow>" el acceso y mostrar "<message>"

        Examples:
            | userRole       | shouldAllow | message                              |
            | administrativo | true        | Acceso autorizado a suscripciones    |
            | admin          | true        | Acceso autorizado a suscripciones    |
            | docente        | false       | Sin permisos para gestionar suscripciones|
            | estudiante     | false       | Sin permisos para gestionar suscripciones|

    Scenario Outline: Comparación de planes
        Given que el usuario está en la vista de suscripciones
        And existen planes con tipo "<planType1>" y "<planType2>"
        When solicita comparar los planes
        Then debe mostrar "<shouldShow>" las diferencias y características de cada plan con mensaje "<message>"

        Examples:
            | planType1 | planType2  | shouldShow | message                              |
            | BASIC     | PREMIUM    | true       | Comparación de planes disponible     |
            | PREMIUM   | ENTERPRISE | true       | Comparación de planes disponible     |
            | BASIC     | ENTERPRISE | true       | Comparación de planes disponible     |
            | FREE      | BASIC      | true       | Comparación de planes disponible     |

    Scenario Outline: Actualización de suscripción existente
        Given que el usuario ya tiene una suscripción activa "<currentPlan>"
        And quiere cambiar a plan "<newPlan>"
        When selecciona el nuevo plan
        Then debe permitir "<shouldAllow>" la actualización y mostrar "<message>"

        Examples:
            | currentPlan | newPlan    | shouldAllow | message                           |
            | BASIC       | PREMIUM    | true        | Actualización disponible          |
            | PREMIUM     | ENTERPRISE | true        | Actualización disponible          |
            | ENTERPRISE  | BASIC      | true        | Downgrade disponible              |
            | FREE        | BASIC      | true        | Upgrade desde plan gratuito       |