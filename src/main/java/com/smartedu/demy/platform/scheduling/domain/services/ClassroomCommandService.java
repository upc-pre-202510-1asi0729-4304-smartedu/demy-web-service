package com.smartedu.demy.platform.scheduling.domain.services;

import com.smartedu.demy.platform.scheduling.domain.model.aggregates.Classroom;
import com.smartedu.demy.platform.scheduling.domain.model.commands.CreateClassroomCommand;
import com.smartedu.demy.platform.scheduling.domain.model.commands.DeleteClassroomCommand;
import com.smartedu.demy.platform.scheduling.domain.model.commands.UpdateClassroomCommand;

import java.util.Optional;

public interface ClassroomCommandService {

    Long handle(CreateClassroomCommand command);

    Optional<Classroom> handle(UpdateClassroomCommand command);

    void handle(DeleteClassroomCommand command);
}