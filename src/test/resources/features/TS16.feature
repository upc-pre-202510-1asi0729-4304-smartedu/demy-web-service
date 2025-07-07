Feature: Editar perfil de usuario administrador
    Para mantener actualizada la información de los usuarios con rol administrativo en Demy
    Como developer
    Quiero exponer un endpoint para editar los perfiles de administradores por su ID

    Scenario Outline: Edición exitosa del perfil de administrador
        Given que existe un administrador con ID válido <id>
        When hago una petición PUT al endpoint "/api/v1/users/admins/<id>" con datos válidos:
            | firstName | lastName | email                | role   |
            | <firstName> | <lastName> | <email>               | <role> |
        Then el sistema actualiza el perfil del administrador
        And responde con el perfil actualizado en formato UserAccountResource
        And el código de respuesta HTTP es 200

        Examples:
            | id | firstName | lastName | email                | role   |
            | 1  | Lucía     | Gómez    | lucia@demy.edu       | ADMIN  |
            | 2  | Marco     | Torres   | marco.torres@demy.pe | ADMIN  |

    Scenario: Usuario ADMIN no encontrado
        Given que el ID no corresponde a ningún administrador
        When hago una petición PUT al endpoint "/api/v1/users/admins/999"
        Then el sistema responde con un mensaje de error
        And el código de respuesta HTTP es 404

    Scenario: Datos inválidos
        Given que existe un administrador con ID existente
        When hago una petición PUT al endpoint "/api/v1/users/admins/1" con datos inválidos:
            | firstName | lastName | email       | role   |
            | ""        | Torres   | marco@bad   | ADMIN  |
        Then el sistema rechaza la solicitud
        And responde con errores de validación
        And el código de respuesta HTTP es 400
