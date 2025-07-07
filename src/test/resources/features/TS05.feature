Feature: Gestionar estudiantes mediante la API
    Para registrar, actualizar o eliminar la información de estudiantes
    Como developer
    Quiero exponer endpoints RESTful para el manejo de entidades Student

    Scenario Outline: Registro exitoso de estudiante
        Given que el cuerpo contiene datos válidos: nombre "<firstName>", apellido "<lastName>", dni "<dni>", sexo "<sex>", fecha de nacimiento "<birthDate>", dirección "<address>" y teléfono "<phoneNumber>"
        When se envía una solicitud POST a "/students"
        Then se responde con estado 201 Created

        Examples:
            | firstName | lastName   | dni      | sex    | birthDate  | address               | phoneNumber |
            | Juan      | Pérez      | 45878965 | MALE   | 2000-05-15 | Av. Los Álamos 123    | 987654321   |
            | María     | Rodríguez  | 70654321 | FEMALE | 1998-08-21 | Calle Lima 456        | 912345678   |

    Scenario Outline: Estudiante duplicado
        Given que ya existe un estudiante registrado con el DNI "<dni>"
        When se intenta registrar nuevamente
        Then se responde con estado 409 Conflict

        Examples:
            | dni      |
            | 45878965 |

    Scenario Outline: Actualización exitosa
        Given que existe un estudiante con ID "<studentId>" y el cuerpo contiene datos válidos actualizados
        When se envía una solicitud PUT a "/students/<studentId>"
        Then se responde con estado 200 OK

        Examples:
            | studentId |
            | 1001      |
            | 1002      |

    Scenario Outline: Actualización con ID inválido
        Given que no existe un estudiante con ID "<studentId>"
        When se envía una solicitud PUT a "/students/<studentId>"
        Then se responde con estado 404 Not Found

        Examples:
            | studentId |
            | 9999      |

    Scenario Outline: Eliminación exitosa
        Given que existe un estudiante con ID "<studentId>"
        When se envía una solicitud DELETE a "/students/<studentId>"
        Then se responde con estado 204 No Content

        Examples:
            | studentId |
            | 1001      |

    Scenario Outline: Eliminación con ID inválido
        Given que no existe un estudiante con ID "<studentId>"
        When se intenta eliminar
        Then se responde con estado 404 Not Found

        Examples:
            | studentId |
            | 8888      |
