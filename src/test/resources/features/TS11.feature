Feature: Registrar asistencia de una sesión de clase
    Para permitir que los profesores registren la asistencia de una sesión
    Como developer
    Quiero exponer un endpoint POST que permita guardar la sesión con lista de asistencia

    Scenario Outline: Registro exitoso de una sesión de clase
        Given que los datos son válidos: ID del curso "<courseId>", fecha "<date>" y lista de asistencia
        When se envía una solicitud POST al endpoint "/class-sessions" con el cuerpo correspondiente
        Then se guarda la sesión de clase con su asistencia
        And se responde con el código 201 Created

        Examples:
            | courseId | date       |
            | C-101    | 2025-07-05 |
            | C-205    | 2025-07-06 |
