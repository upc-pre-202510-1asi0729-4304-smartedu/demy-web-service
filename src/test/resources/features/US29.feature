Feature: Agregar periodo académico
    Para organizar los ciclos y relacionar materias, matrículas y horarios
    Como administrativo
    Quiero definir nuevos periodos académicos (nombre, fecha de inicio y fin)

    Scenario Outline: Creación exitosa de periodo
        Given el administrador accede a "Periodos Académicos"
        And completa nombre "<name>", fecha de inicio "<startDate>" y fecha de fin "<endDate>"
        And no existe solapamiento con otros periodos
        When confirma la creación
        Then el sistema registra el periodo "<shouldCreate>" y muestra mensaje "<message>"
        And lo muestra en el listado con sus fechas "<shouldShowInList>"

        Examples:
            | name            | startDate  | endDate    | shouldCreate | message                        | shouldShowInList |
            | Semestre 2025-1 | 2025-03-01 | 2025-07-31 | true         | Periodo creado exitosamente    | true             |
            | Semestre 2025-2 | 2025-08-01 | 2025-12-15 | true         | Periodo creado exitosamente    | true             |
            | Verano 2025     | 2025-01-15 | 2025-02-28 | true         | Periodo creado exitosamente    | true             |
            | Curso Intensivo | 2025-06-01 | 2025-06-30 | true         | Periodo creado exitosamente    | true             |

    Scenario Outline: Fecha de inicio posterior a la de fin
        Given que el administrador ingresa nombre "<name>", fecha de inicio "<startDate>" y fecha de fin "<endDate>"
        When intenta guardar
        Then el sistema muestra "<shouldShowError>" error y mensaje "<message>"

        Examples:
            | name            | startDate  | endDate    | shouldShowError | message                                            |
            | Semestre 2025-1 | 2025-07-31 | 2025-03-01 | true            | La fecha de inicio debe ser anterior a la fecha de fin |
            | Semestre 2025-2 | 2025-12-15 | 2025-08-01 | true            | La fecha de inicio debe ser anterior a la fecha de fin |
            | Verano 2025     | 2025-02-28 | 2025-01-15 | true            | La fecha de inicio debe ser anterior a la fecha de fin |
            | Curso Válido    | 2025-01-01 | 2025-02-01 | false           | Fechas válidas                                     |

    Scenario Outline: Solapamiento con periodo existente
        Given que existe un periodo del "<existingStartDate>" al "<existingEndDate>"
        And el administrador intenta crear periodo con fechas "<newStartDate>" a "<newEndDate>"
        When intenta guardar
        Then el sistema muestra "<shouldShowOverlap>" error y mensaje "<message>"

        Examples:
            | existingStartDate | existingEndDate | newStartDate | newEndDate | shouldShowOverlap | message                              |
            | 2025-03-01        | 2025-07-31      | 2025-05-01   | 2025-09-01 | true              | El periodo se solapa con uno existente |
            | 2025-03-01        | 2025-07-31      | 2025-01-01   | 2025-05-01 | true              | El periodo se solapa con uno existente |
            | 2025-03-01        | 2025-07-31      | 2025-04-01   | 2025-06-01 | true              | El periodo se solapa con uno existente |
            | 2025-03-01        | 2025-07-31      | 2025-08-01   | 2025-12-01 | false             | Sin solapamiento                     |

    Scenario Outline: Campos obligatorios vacíos
        Given que el administrador omite nombre "<name>", fecha de inicio "<startDate>" o fecha de fin "<endDate>"
        When confirma
        Then el sistema resalta los campos faltantes "<shouldHighlight>" y muestra mensaje "<message>"

        Examples:
            | name            | startDate  | endDate    | shouldHighlight | message                           |
            | ""              | 2025-03-01 | 2025-07-31 | true            | Nombre es obligatorio             |
            | Semestre 2025-1 | ""         | 2025-07-31 | true            | Fecha de inicio es obligatoria    |
            | Semestre 2025-1 | 2025-03-01 | ""         | true            | Fecha de fin es obligatoria       |
            | ""              | ""         | ""         | true            | Todos los campos son obligatorios |
            | Semestre 2025-1 | 2025-03-01 | 2025-07-31 | false           | Formulario válido                 |

    Scenario Outline: Validación de formato de fechas
        Given que el administrador ingresa nombre "<name>", fecha de inicio "<startDate>" y fecha de fin "<endDate>"
        When intenta guardar
        Then el sistema valida "<shouldValidate>" el formato de fechas y muestra mensaje "<message>"

        Examples:
            | name            | startDate  | endDate    | shouldValidate | message                     |
            | Semestre 2025-1 | 2025-03-01 | 2025-07-31 | true           | Formato de fechas válido    |
            | Semestre 2025-2 | 01-03-2025 | 31-07-2025 | false          | Formato de fechas inválido  |
            | Verano 2025     | 2025/03/01 | 2025/07/31 | false          | Formato de fechas inválido  |
            | Curso Intensivo | 2025-13-01 | 2025-07-31 | false          | Fecha inválida              |

    Scenario Outline: Validación de duración mínima
        Given que el administrador ingresa nombre "<name>", fecha de inicio "<startDate>" y fecha de fin "<endDate>"
        When intenta guardar
        Then el sistema valida "<shouldValidate>" la duración mínima y muestra mensaje "<message>"

        Examples:
            | name            | startDate  | endDate    | shouldValidate | message                           |
            | Semestre 2025-1 | 2025-03-01 | 2025-07-31 | true           | Duración válida                   |
            | Curso Corto     | 2025-03-01 | 2025-03-02 | false          | Duración mínima no cumplida       |
            | Curso Intensivo | 2025-03-01 | 2025-04-01 | true           | Duración válida                   |
            | Curso Largo     | 2025-03-01 | 2026-03-01 | true           | Duración válida                   |