Feature: Agregar perfiles de usuarios Teacher
    Para gestionar profesores en la plataforma Demy
    Como developer
    Quiero exponer un endpoint que permita registrar nuevos usuarios con rol "Teacher"

    Scenario Outline: Registro exitoso de profesor
        Given que envío una petición POST al endpoint "/api/v1/users/teachers" con:
        | firstName | lastName | email              | password   |
        | <firstName> | <lastName> | <email> | <password> |
        When los datos cumplen con las validaciones
        Then el sistema crea un nuevo usuario con rol "Teacher"
        And responde con HTTP 201 y los datos del nuevo perfil

        Examples:
            | firstName | lastName | email                | password   |
            | Ana       | Torres   | ana.torres@demy.pe   | P@ssword1! |
            | Luis      | Gamarra  | luis.gamarra@demy.pe | StrongPass9! |

    Scenario Outline: Fallo por datos inválidos
        Given que envío una petición POST con:
        | firstName | lastName | email              | password   |
        | <firstName> | <lastName> | <email> | <password> |
        When intento crear el usuario
        Then el sistema rechaza la petición
        And responde con mensaje de validación
        And el código HTTP es 400

        Examples:
            | firstName | lastName | email              | password  |
            |           | Torres   | torres@demy.pe     | Abc12345  |
            | Luis      |          | luis@demy.pe       | Abc12345  |
            | Carla     | Ramos    | carla.demy.pe      | Abc12345  |
            | Mario     | Díaz     | mario@demy.pe      |           |
