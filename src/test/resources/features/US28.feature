Feature: Agregar aulas
    Para poder asignarlas eficientemente a las clases
    Como administrativo
    Quiero registrar nuevas aulas físicas (código único y capacidad)

    Scenario Outline: Creación exitosa de aula
        Given el administrador abre "Aulas"
        And ingresa código "<code>", capacidad "<capacity>" y campus "<campus>"
        When intenta guardar el aula
        Then el sistema registra el aula "<shouldCreate>" y muestra mensaje "<message>"

        Examples:
            | code   | capacity | campus     | shouldCreate | message                  |
            | A-101  | 30       | Norte      | true         | Aula creada con éxito    |
            | B-205  | 50       | Sur        | true         | Aula creada con éxito    |
            | C-301  | 25       | Centro     | true         | Aula creada con éxito    |
            | D-102  | 40       | Norte      | true         | Aula creada con éxito    |
            | LAB-01 | 20       | Tecnología | true         | Aula creada con éxito    |

    Scenario Outline: Código ya existente
        Given que existe un aula con código "<existingCode>"
        And el administrador intenta crear aula con código "<newCode>", capacidad "<capacity>" y campus "<campus>"
        When intenta guardar
        Then el sistema muestra "<shouldShowError>" error y mensaje "<message>"
        And no crea el registro "<shouldNotCreate>"

        Examples:
            | existingCode | newCode | capacity | campus | shouldShowError | message                | shouldNotCreate |
            | A-101        | A-101   | 30       | Norte  | true            | Código de aula en uso  | true            |
            | B-205        | B-205   | 50       | Sur    | true            | Código de aula en uso  | true            |
            | C-301        | C-301   | 25       | Centro | true            | Código de aula en uso  | true            |
            | D-102        | E-103   | 40       | Norte  | false           | Código disponible      | false           |

    Scenario Outline: Capacidad inválida
        Given que el administrador ingresa código "<code>", capacidad "<capacity>" y campus "<campus>"
        When intenta guardar
        Then el sistema señala "<shouldShowError>" error y mensaje "<message>"

        Examples:
            | code   | capacity | campus | shouldShowError | message                                      |
            | A-101  | 0        | Norte  | true            | La capacidad debe ser un número mayor a cero |
            | B-205  | -5       | Sur    | true            | La capacidad debe ser un número mayor a cero |
            | C-301  | -10      | Centro | true            | La capacidad debe ser un número mayor a cero |
            | D-102  | 30       | Norte  | false           | Capacidad válida                             |
            | E-103  | 25       | Sur    | false           | Capacidad válida                             |

    Scenario Outline: Campos obligatorios incompletos
        Given que el administrador omite código "<code>", capacidad "<capacity>" o campus "<campus>"
        When confirma
        Then el sistema resalta los campos vacíos "<shouldHighlight>" y muestra mensaje "<message>"

        Examples:
            | code   | capacity | campus | shouldHighlight | message                           |
            | ""     | 30       | Norte  | true            | Código es obligatorio             |
            | A-101  | ""       | Norte  | true            | Capacidad es obligatoria          |
            | A-101  | 30       | ""     | true            | Campus es obligatorio             |
            | ""     | ""       | ""     | true            | Todos los campos son obligatorios |
            | A-101  | 30       | Norte  | false           | Formulario válido                 |

    Scenario Outline: Validación de formato de código
        Given que el administrador ingresa código "<code>", capacidad "<capacity>" y campus "<campus>"
        When intenta guardar
        Then el sistema valida "<shouldValidate>" el formato y muestra mensaje "<message>"

        Examples:
            | code     | capacity | campus | shouldValidate | message                        |
            | A-101    | 30       | Norte  | true           | Formato de código válido       |
            | LAB-01   | 25       | Centro | true           | Formato de código válido       |
            | B205     | 40       | Sur    | false          | Formato de código inválido     |
            | 101      | 35       | Norte  | false          | Formato de código inválido     |
            | A-       | 30       | Centro | false          | Formato de código inválido     |

    Scenario Outline: Validación de capacidad numérica
        Given que el administrador ingresa código "<code>", capacidad "<capacity>" y campus "<campus>"
        When intenta guardar
        Then el sistema valida "<shouldValidate>" que capacidad sea numérica y muestra mensaje "<message>"

        Examples:
            | code   | capacity | campus | shouldValidate | message                          |
            | A-101  | 30       | Norte  | true           | Capacidad numérica válida        |
            | B-205  | 50       | Sur    | true           | Capacidad numérica válida        |
            | C-301  | abc      | Centro | false          | Capacidad debe ser un número     |
            | D-102  | 25.5     | Norte  | false          | Capacidad debe ser un número entero |
            | E-103  | 30x      | Sur    | false          | Capacidad debe ser un número     |