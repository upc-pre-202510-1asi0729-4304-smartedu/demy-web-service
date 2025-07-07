Feature: Editar perfiles de usuarios Teacher
    Para mantener actualizados los datos de los profesores en la plataforma Demy
    Como developer
    Quiero exponer un endpoint que permita modificar perfiles de profesores usando su ID

    Scenario Outline: Usuario Teacher actualizado exitosamente
        Given que existe un profesor con ID "<id>"
        When envío una petición PUT al endpoint "/api/v1/users/teachers/<id>" con:
        | firstName | lastName | email                  | role    |
        | <firstName> | <lastName> | <email> | <role> |
        Then el sistema actualiza el perfil correctamente
        And responde con el perfil actualizado
        And el código de respuesta HTTP es 200

        Examples:
            | id  | firstName | lastName | email                | role     |
            | 1   | Ricardo   | Mejía    | ricardo@demy.pe      | Teacher  |
            | 2   | Pamela    | Torres   | pamela@demy.edu.pe   | Teacher  |

    Scenario Outline: Usuario Teacher no encontrado
        Given que intento editar al profesor con ID "<id>" inexistente
        When hago una petición PUT a "/api/v1/users/teachers/<id>"
        Then el sistema responde con error de recurso no encontrado
        And el código de respuesta HTTP es 404

        Examples:
            | id  |
            | 999 |
            | 123 |

    Scenario Outline: Datos inválidos en actualización
        Given que existe un profesor con ID "<id>"
        When hago una petición PUT al endpoint "/api/v1/users/teachers/<id>" con datos inválidos:
        | firstName | lastName | email             | role    |
        | <firstName> | <lastName> | <email> | <role> |
        Then el sistema rechaza la solicitud
        And responde con mensajes de error de validación
        And el código de respuesta HTTP es 400

        Examples:
            | id | firstName | lastName | email              | role     |
            | 3  |           | Salas    | salas@demy.pe      | Teacher  |
            | 4  | Luis      |          | luis@demy.pe       | Teacher  |
            | 5  | Rosa      | Ríos     | rosa-demy.pe       | Teacher  |
            | 6  | Mario     | Díaz     | mario@demy.pe      | ""       |
