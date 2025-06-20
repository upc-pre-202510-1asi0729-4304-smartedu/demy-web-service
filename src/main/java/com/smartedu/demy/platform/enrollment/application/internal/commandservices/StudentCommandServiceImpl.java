package com.smartedu.demy.platform.enrollment.application.internal.commandservices;

import com.smartedu.demy.platform.enrollment.domain.model.aggregates.Student;
import com.smartedu.demy.platform.enrollment.domain.model.commands.CreateStudentCommand;
import com.smartedu.demy.platform.enrollment.domain.model.commands.DeleteStudentCommand;
import com.smartedu.demy.platform.enrollment.domain.model.commands.UpdateStudentCommand;
import com.smartedu.demy.platform.enrollment.domain.services.StudentCommandService;
import com.smartedu.demy.platform.enrollment.infrastructure.persistence.jpa.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentCommandServiceImpl implements StudentCommandService {

    private final StudentRepository studentRepository;

    public StudentCommandServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Long handle(CreateStudentCommand command) {
        if (studentRepository.existsStudentByDni_Dni(command.dni())) {
            throw new IllegalArgumentException("Student with DNI %s already exists".formatted(command.dni()));
        }

        var student = new Student(command);

        try {
            studentRepository.save(student);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving student");
        }

        return student.getId();
    }

    @Override
    public void handle(DeleteStudentCommand command) {
        if (!studentRepository.existsById(command.studentId())) {
            throw new IllegalArgumentException("Student with ID %s does not exist".formatted(command.studentId()));
        }

        try {
            studentRepository.deleteById(command.studentId());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting student");
        }
    }

    @Override
    public Optional<Student> handle(UpdateStudentCommand command) {
        var result = studentRepository.findById(command.studentId());

        if (result.isEmpty()) {
            throw new IllegalArgumentException("Student with ID %s not found".formatted(command.studentId()));
        }

        var studentToUpdate = result.get();

        try {
            studentToUpdate.updateInformation(
                    command.firstName(),
                    command.lastName(),
                    command.dni(),
                    command.sex(),
                    command.birthDate(),
                    command.address(),
                    command.phoneNumber()
            );

            var updatedStudent = studentRepository.save(studentToUpdate);
            return Optional.of(updatedStudent);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating student");
        }
    }
}
