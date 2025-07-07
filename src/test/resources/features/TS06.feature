Feature: Consultar estudiante por ID
    Para que los usuarios puedan visualizar los datos personales de un estudiante
    Como developer
    Quiero exponer un endpoint GET que devuelva un estudiante dado su ID

    Scenario Outline: Estudiante encontrado
        Given que el ID "<studentId>" corresponde a un estudiante registrado
        When se consulta el endpoint GET "/students/<studentId>"
        Then se responde con estado 200 OK
        And el cuerpo contiene: firstName "<firstName>", lastName "<lastName>", dni "<dni>", sex "<sex>", birthDate "<birthDate>", address "<address>", phoneNumber "<phoneNumber>"

        Examples:
            | studentId | firstName | lastName  | dni      | sex    | birthDate  | address              | phoneNumber |
            | 1001      | Juan      | Pérez     | 45878965 | MALE   | 2000-05-15 | Av. Las Palmeras 123 | 987654321   |
            | 1002      | María     | López     | 70654321 | FEMALE | 1998-08-21 | Jr. San Martín 456   | 912345678   |

    Scenario Outline: Estudiante no encontrado
        Given que el ID "<studentId>" no corresponde a ningún estudiante registrado
        When se consulta el endpoint GET "/students/<studentId>"
        Then se responde con estado 404 Not Found

        Examples:
            | studentId |
            | 9999      |
            | 8888      |
