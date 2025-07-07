Feature: Obtener datos de cursos
    Para que los usuarios puedan consultar la oferta académica
    Como developer
    Quiero exponer un endpoint para obtener todos los cursos y otro para obtener un curso específico por su ID

    Scenario: Cursos encontrados
        Given que existen cursos registrados en el sistema
        When consulto el endpoint GET "/api/v1/courses"
        Then el sistema responde con HTTP 200
        And retorna una lista de cursos con los siguientes campos:
            | name        | code     | description                    |
            | Matemática  | MAT101   | Curso de fundamentos matemáticos |
            | Historia    | HIS201   | Historia universal moderna       |

    Scenario: No hay cursos registrados
        Given que no existen cursos en el sistema
        When consulto el endpoint GET "/api/v1/courses"
        Then el sistema responde con HTTP 200
        And retorna una lista vacía

    Scenario Outline: Curso encontrado por ID
        Given que el curso con ID "<courseId>" existe en el sistema
        When consulto el endpoint GET "/api/v1/courses/<courseId>"
        Then el sistema responde con HTTP 200
        And retorna los detalles del curso:
            | name      | code    | description          |
            | <name>    | <code>  | <description>        |

        Examples:
            | courseId | name         | code    | description                   |
            | 1        | Física       | FIS101  | Conceptos básicos de física   |
            | 2        | Programación | PROG01  | Introducción a la programación |

    Scenario: Curso no encontrado por ID
        Given que no existe un curso con ID "999"
        When consulto el endpoint GET "/api/v1/courses/999"
        Then el sistema responde con HTTP 404
        And retorna un mensaje indicando que el curso no fue encontrado
