-- 1. Elimina registros huérfanos (enrollments sin estudiante válido)
DELETE FROM enrollments
WHERE student_id NOT IN (SELECT id FROM students);

-- 2. O convierte los IDs inválidos a NULL si permites nulls
UPDATE enrollments
SET student_id = NULL
WHERE student_id NOT IN (SELECT id FROM students);

-- 3. Luego crea la FK
ALTER TABLE enrollments
    ADD CONSTRAINT FK_enrollments_student_id
        FOREIGN KEY (student_id)
            REFERENCES students (id);