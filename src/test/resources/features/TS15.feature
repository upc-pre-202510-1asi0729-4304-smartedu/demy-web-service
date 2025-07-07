Feature: Consultar perfiles de administradores
    Para identificar a los usuarios con rol administrativo dentro de la plataforma Demy
    Como developer
    Quiero exponer un endpoint que devuelva una lista de usuarios con rol "Admin"

    Scenario Outline: Consulta de perfiles con rol Admin
        Given que existen usuarios con el rol "<role>"
        When se realiza una petición GET al endpoint "/api/v1/users/admins"
        Then el sistema responde con código <statusCode>
        And retorna una lista con <resultCount> resultados

        Examples:
            | role  | statusCode | resultCount |
            | ADMIN | 200        | >0          |
            | USER  | 200        | 0           |

    Scenario: Lista vacía si no existen administradores
        Given que no hay usuarios con rol "Admin"
        When se realiza una petición GET al endpoint "/api/v1/users/admins"
        Then el sistema responde con código 200
        And retorna una lista vacía []

    Scenario: Validación del contenido de los perfiles
        Given que existen usuarios con rol "Admin"
        When se hace una petición GET al endpoint "/api/v1/users/admins"
        Then cada elemento contiene:
            | Campo     | Tipo    |
            | id        | Long    |
            | fullName  | String  |
            | email     | String  |
            | role      | String  |
            | status    | String  |
        And ningún campo sensible como passwordHash es expuesto
