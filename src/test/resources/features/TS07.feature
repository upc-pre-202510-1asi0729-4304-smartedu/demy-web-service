Feature: Listar estudiantes
    Para que los usuarios puedan consultar todos los registros de estudiantes
    Como developer
    Quiero exponer un endpoint GET que devuelva el listado completo de estudiantes registrados

    Scenario Outline: Existen estudiantes registrados
        Given que existen uno o más estudiantes en el sistema
        When se consulta el endpoint GET "/students"
        Then se responde con estado 200 OK
        And el cuerpo contiene una lista de estudiantes con los siguientes campos:
            | studentId | firstName | lastName | dni      | sex    | birthDate  | address              | phoneNumber |
            | <id1>     | <name1>   | <last1>  | <dni1>   | <sex1> | <birth1>   | <address1>           | <phone1>    |
            | <id2>     | <name2>   | <last2>  | <dni2>   | <sex2> | <birth2>   | <address2>           | <phone2>    |

        Examples:
            | id1  | name1 | last1  | dni1     | sex1  | birth1     | address1             | phone1     |
            | 1001 | Juan  | Pérez  | 45878965 | MALE  | 2000-05-15 | Av. Primavera 123    | 987654321  |
            | 1002 | Carla | Torres | 70654321 | FEMALE| 1999-03-12 | Calle Lima 456       | 912345678  |

    Scenario: No existen estudiantes registrados
        Given que no existe ningún estudiante en el sistema
        When se consulta el endpoint GET "/students"
        Then se responde con estado 200 OK
        And el cuerpo contiene un arreglo vacío
