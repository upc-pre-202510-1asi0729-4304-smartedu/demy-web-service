package com.smartedu.demy.platform.enrollment.application.internal.commandservices;

import com.smartedu.demy.platform.enrollment.domain.model.aggregates.AcademicPeriod;
import com.smartedu.demy.platform.enrollment.domain.model.commands.CreateAcademicPeriodCommand;
import com.smartedu.demy.platform.enrollment.domain.model.commands.DeleteAcademicPeriodCommand;
import com.smartedu.demy.platform.enrollment.domain.model.commands.UpdateAcademicPeriodCommand;
import com.smartedu.demy.platform.enrollment.domain.services.AcademicPeriodCommandService;
import com.smartedu.demy.platform.enrollment.infrastructure.persistence.jpa.repositories.AcademicPeriodRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AcademicPeriodCommandServiceImpl implements AcademicPeriodCommandService {
    private final AcademicPeriodRepository academicPeriodRepository;

    public AcademicPeriodCommandServiceImpl(AcademicPeriodRepository academicPeriodRepository){
        this.academicPeriodRepository = academicPeriodRepository;
    }

    @Override
    public Long handle(CreateAcademicPeriodCommand command) {
        if (academicPeriodRepository.existsByPeriodName(command.periodName())) {
            throw new IllegalArgumentException("Period name already exists");
        }
        var academicPeriod = new AcademicPeriod(command);
        try {
            academicPeriodRepository.save(academicPeriod);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to save academic period");
        }
        return academicPeriod.getId();
    }

    @Override
    public void handle(DeleteAcademicPeriodCommand command) {
        if (!academicPeriodRepository.existsById(command.academicPeriodId())) {
            throw new IllegalArgumentException("Academic period does not exist");
        }
        try {
            academicPeriodRepository.deleteById(command.academicPeriodId());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting academic period");
        }
    }

    @Override
    public Optional<AcademicPeriod> handle(UpdateAcademicPeriodCommand command) {
        if (academicPeriodRepository.existsByPeriodNameAndIdIsNot(command.periodName(), command.academicPeriodId())) {
            throw new IllegalArgumentException("Academic period with name %s alredy exists".formatted(command.periodName()));
        }
        var result = academicPeriodRepository.findById(command.academicPeriodId());
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Academic period with id %s not found".formatted(command.academicPeriodId()));
        }
        var academicPeriodToUdpate = result.get();
        try {
            var updatedAcademicPeriod = academicPeriodRepository.save(academicPeriodToUdpate.updateInformation(command.periodName(), command.startDate(), command.endDate(), command.isActive()));
            return Optional.of(updatedAcademicPeriod);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating academic period");
        }
    }
}
