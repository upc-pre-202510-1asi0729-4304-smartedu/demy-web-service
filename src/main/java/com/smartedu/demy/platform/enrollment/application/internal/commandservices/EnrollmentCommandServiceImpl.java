package com.smartedu.demy.platform.enrollment.application.internal.commandservices;

import com.smartedu.demy.platform.enrollment.domain.model.aggregates.Enrollment;
import com.smartedu.demy.platform.enrollment.domain.model.commands.CreateEnrollmentCommand;
import com.smartedu.demy.platform.enrollment.domain.model.commands.DeleteEnrollmentCommand;
import com.smartedu.demy.platform.enrollment.domain.model.commands.UpdateEnrollmentCommand;
import com.smartedu.demy.platform.enrollment.domain.services.EnrollmentCommandService;
import com.smartedu.demy.platform.enrollment.infrastructure.persistence.jpa.repositories.EnrollmentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EnrollmentCommandServiceImpl implements EnrollmentCommandService {

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentCommandServiceImpl(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public Long handle(CreateEnrollmentCommand command) {
        var studentId = command.studentId();
        var periodId = command.periodId();

        var existing = enrollmentRepository.findByStudentIdAndPeriodId(
                new com.smartedu.demy.platform.enrollment.domain.model.valueobjects.StudentId(studentId),
                new com.smartedu.demy.platform.enrollment.domain.model.valueobjects.PeriodId(periodId)
        );

        if (existing.isPresent()) {
            throw new IllegalArgumentException("Enrollment already exists for student %d in period %d".formatted(studentId, periodId));
        }

        var enrollment = new Enrollment(command);

        try {
            enrollmentRepository.save(enrollment);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving enrollment");
        }

        return enrollment.getId();
    }

    @Override
    public void handle(DeleteEnrollmentCommand command) {
        if (!enrollmentRepository.existsById(command.enrollmentId())) {
            throw new IllegalArgumentException("Enrollment with ID %d does not exist".formatted(command.enrollmentId()));
        }

        try {
            enrollmentRepository.deleteById(command.enrollmentId());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting enrollment");
        }
    }

    @Override
    public Optional<Enrollment> handle(UpdateEnrollmentCommand command) {
        var result = enrollmentRepository.findById(command.enrollmentId());

        if (result.isEmpty()) {
            throw new IllegalArgumentException("Enrollment with ID %d not found".formatted(command.enrollmentId()));
        }

        var enrollmentToUpdate = result.get();

        try {
            enrollmentToUpdate.updateInformation(
                    command.amount(),
                    command.currency(),
                    command.enrollmentStatus(),
                    command.paymentStatus()
            );
            var updatedEnrollment = enrollmentRepository.save(enrollmentToUpdate);
            return Optional.of(updatedEnrollment);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating enrollment");
        }
    }
}
