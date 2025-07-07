Feature: Crear materias por periodo de estudio
    Para mantener un control claro del contenido académico en cada ciclo
    Como administrativo
    Quiero crear materias por periodo de estudio

    Scenario Outline: Creación de materia exitosa
        Given que el usuario tiene rol administrativo
        When ingresa a su organización y navega a "Periodos" > "Gestión de materias"
        And completa los datos nombre "<name>", código "<code>" y descripción "<description>"
        Then el sistema guarda la materia "<shouldSave>" y muestra mensaje "<message>"

        Examples:
            | name           | code     | description                    | shouldSave | message                           |
            | Matemáticas    | MAT001   | Matemáticas básicas nivel 1    | true       | Materia creada exitosamente       |
            | Ciencias       | CIE002   | Ciencias naturales             | true       | Materia creada exitosamente       |
            | Historia       | HIS003   | Historia universal             | true       | Materia creada exitosamente       |
            | Inglés         | ING004   | Inglés básico                  | true       | Materia creada exitosamente       |
            | (vacío)        | MAT001   | Matemáticas básicas            | false      | Nombre es obligatorio             |
            | Física         | (vacío)  | Física aplicada                | false      | Código es obligatorio             |
            | Química        | QUI005   | (vacío)                        | true       | Materia creada exitosamente       |

    Scenario Outline: Validación de código único
        Given que existe una materia con código "<existingCode>"
        When se intenta crear nueva materia con código "<newCode>" y nombre "<name>"
        Then el resultado debe ser "<shouldCreate>" con mensaje "<message>"

        Examples:
            | existingCode | newCode | name           | shouldCreate | message                           |
            | MAT001       | MAT002  | Álgebra        | true         | Materia creada exitosamente       |
            | MAT001       | MAT001  | Geometría      | false        | Código ya existe                  |
            | CIE002       | CIE002  | Biología       | false        | Código ya existe                  |
            | HIS003       | HIS004  | Historia Perú  | true         | Materia creada exitosamente       |
            | ING004       | ING005  | Inglés Avanzado| true         | Materia creada exitosamente       |

    Scenario Outline: Actualización de materia existente
        Given que existe una materia con id "<courseId>" y código "<originalCode>"
        When se actualiza con nombre "<newName>", código "<newCode>" y descripción "<newDescription>"
        Then la materia debe actualizarse "<shouldUpdate>" con mensaje "<message>"

        Examples:
            | courseId | originalCode | newName        | newCode | newDescription              | shouldUpdate | message                           |
            | 1        | MAT001       | Matemáticas I  | MAT001  | Matemáticas nivel inicial   | true         | Materia actualizada exitosamente |
            | 2        | CIE002       | Ciencias I     | CIE002  | Ciencias naturales básicas  | true         | Materia actualizada exitosamente |
            | 3        | HIS003       | Historia I     | HIS003  | Historia del Perú           | true         | Materia actualizada exitosamente |
            | 1        | MAT001       | (vacío)        | MAT001  | Matemáticas básicas         | false        | Nombre es obligatorio             |
            | 2        | CIE002       | Física         | (vacío) | Física aplicada             | false        | Código es obligatorio             |

    Scenario Outline: Validación de permisos de usuario
        Given que el usuario tiene rol "<userRole>"
        When intenta crear materia con nombre "<name>" y código "<code>"
        Then el acceso debe ser "<shouldAllow>" con mensaje "<message>"

        Examples:
            | userRole      | name        | code   | shouldAllow | message                           |
            | administrativo| Matemáticas | MAT001 | true        | Materia creada exitosamente       |
            | profesor      | Ciencias    | CIE002 | false       | Permisos insuficientes            |
            | estudiante    | Historia    | HIS003 | false       | Permisos insuficientes            |
            | coordinador   | Inglés      | ING004 | true        | Materia creada exitosamente       |
            | invitado      | Física      | FIS005 | false       | Permisos insuficientes            |