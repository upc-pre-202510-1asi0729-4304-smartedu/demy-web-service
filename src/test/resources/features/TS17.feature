Feature: Registrar un nuevo usuario Teacher
    Para registrar profesores en la plataforma Demy
    Como developer
    Quiero exponer un endpoint que permita crear perfiles de usuarios con rol "Teacher"

    Scenario Outline: Registro exitoso de profesor
        Given que el cuerpo contiene firstName "<firstName>", lastName "<lastName>", email "<email>" y password "<password>"
        When hago una solicitud POST al endpoint "/api/v1/users/teachers"
        Then se crea el perfil con rol "Teacher" y responde con HTTP 201

        Examples:
            | firstName | lastName | email                  | password   |
            | Ana       | Torres   | ana.torres@demy.pe     | P@ssw0rd1! |
            | Luis      | Gamarra  | luis.gamarra@demy.pe   | Segura123! |

    Scenario Outline: Registro fallido por email duplicado
        Given que ya existe un usuario con email "<email>"
        When hago una solicitud POST con ese mismo correo
        Then se responde con HTTP 409 Conflict y mensaje "Email ya registrado"

        Examples:
            | firstName | lastName | email                  | password   |
            | Mario     | Ruiz     | ana.torres@demy.pe     | Otro1234   |

    Scenario Outline: Datos inválidos
        Given que se omiten uno o más campos como "<firstName>", "<lastName>", "<email>", o "<password>"
        When hago una solicitud POST
        Then el sistema responde con HTTP 400 Bad Request y detalles de validación

        Examples:
            | firstName | lastName | email                | password   |
            |           | Torres   | torres@demy.pe       | P@ssword   |
            | Ana       |          | ana@demy.pe          | P@ssword   |
            | Luis      | Gamarra  |                      | P@ssword   |
            | Mario     | Díaz     | mario@demy.pe        |            |
