Feature: Agregar profesores
    Para que puedan acceder y gestionar sus tareas en el sistema
    Como administrativo
    Quiero agregar profesores a la academia

    Scenario Outline: Creación de profesor exitoso
        Given el administrador accede a la sección de "Profesores"
        And completa nombre "<firstName>", apellido "<lastName>", DNI "<dni>" y correo "<email>"
        When confirma el registro
        Then el sistema crea el perfil del profesor "<shouldCreate>" y muestra mensaje "<message>"
        And muestra los credenciales para su ingreso en el sistema "<showCredentials>"

        Examples:
            | firstName | lastName | dni      | email                 | shouldCreate | message                      | showCredentials |
            | Juan      | Pérez    | 12345678 | juan.perez@email.com  | true         | Profesor creado exitosamente | true            |
            | María     | González | 87654321 | maria.gonzalez@edu.pe | true         | Profesor creado exitosamente | true            |
            | Carlos    | Rodríguez| 11223344 | carlos.rod@academia.pe| true         | Profesor creado exitosamente | true            |
            | Ana       | López    | 99887766 | ana.lopez@school.com  | true         | Profesor creado exitosamente | true            |

    Scenario Outline: DNI o correo ya existente
        Given que existe un profesor con DNI "<existingDni>" o correo "<existingEmail>"
        And el administrador intenta crear profesor con DNI "<newDni>" y correo "<newEmail>"
        When intenta guardar los datos
        Then el sistema muestra "<shouldShowError>" error y mensaje "<message>"
        And no crea el profesor "<shouldNotCreate>"

        Examples:
            | existingDni | existingEmail        | newDni   | newEmail             | shouldShowError | message                      | shouldNotCreate |
            | 12345678    | juan@email.com       | 12345678 | nuevo@email.com      | true            | DNI ya registrado            | true            |
            | 87654321    | maria@email.com      | 99999999 | maria@email.com      | true            | Correo ya registrado         | true            |
            | 11223344    | carlos@email.com     | 11223344 | carlos@email.com     | true            | DNI y correo ya registrados  | true            |
            | 55566677    | ana@email.com        | 88899900 | nuevo@email.com      | false           | Datos válidos                | false           |

    Scenario Outline: Formato de correo o DNI inválido
        Given que el administrador ingresa firstName "<firstName>", lastName "<lastName>", DNI "<dni>" y correo "<email>"
        When intenta guardar
        Then el sistema resalta el campo erróneo "<shouldHighlight>" y muestra mensaje "<message>"

        Examples:
            | firstName | lastName | dni        | email           | shouldHighlight | message                    |
            | Juan      | Pérez    | 12345678   | juanemail.com   | true            | Formato de correo inválido |
            | María     | González | 87654321   | maria@          | true            | Formato de correo inválido |
            | Carlos    | Rodríguez| abc12345   | carlos@edu.pe   | true            | Formato de DNI inválido    |
            | Ana       | López    | 123abc78   | ana@school.com  | true            | Formato de DNI inválido    |
            | Luis      | Martín   | 12345678   | luis@email.com  | false           | Formato válido             |

    Scenario Outline: Campos obligatorios vacíos
        Given que el administrador deja en blanco firstName "<firstName>", lastName "<lastName>", DNI "<dni>" o correo "<email>"
        When intenta confirmar
        Then el sistema marca los campos faltantes "<shouldMark>" y muestra mensaje "<message>"

        Examples:
            | firstName | lastName | dni      | email             | shouldMark | message                           |
            | ""        | Pérez    | 12345678 | juan@email.com    | true       | Nombre es obligatorio             |
            | Juan      | ""       | 12345678 | juan@email.com    | true       | Apellido es obligatorio           |
            | Juan      | Pérez    | ""       | juan@email.com    | true       | DNI es obligatorio                |
            | Juan      | Pérez    | 12345678 | ""                | true       | Correo es obligatorio             |
            | ""        | ""       | ""       | ""                | true       | Todos los campos son obligatorios |
            | Juan      | Pérez    | 12345678 | juan@email.com    | false      | Formulario válido                 |

    Scenario Outline: Generación de credenciales
        Given que se crea exitosamente un profesor con email "<email>"
        When el sistema genera las credenciales
        Then debe crear password "<shouldGeneratePassword>" y mostrar credenciales "<shouldShowCredentials>"

        Examples:
            | email                 | shouldGeneratePassword | shouldShowCredentials |
            | juan.perez@email.com  | true                   | true                  |
            | maria.gonzalez@edu.pe | true                   | true                  |
            | carlos.rod@academia.pe| true                   | true                  |
            | ana.lopez@school.com  | true                   | true                  |