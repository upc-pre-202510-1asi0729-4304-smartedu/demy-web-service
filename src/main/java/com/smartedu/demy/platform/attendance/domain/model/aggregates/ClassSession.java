package com.smartedu.demy.platform.attendance.domain.model.aggregates;

import com.smartedu.demy.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.CourseId;
import com.smartedu.demy.platform.attendance.domain.model.valueobjects.AttendanceRecord;
import com.smartedu.demy.platform.attendance.domain.model.commands.CreateClassSessionCommand;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import lombok.Getter;
/**
 * ClassSession Aggregate Root
 *
 * @summary
 * The ClassSession class is an aggregate root that represents a class session.
 * It is responsible for handling the CreateClassSessionCommand command.
 * @since 1.0
 */
@Entity
 public class ClassSession extends AuditableAbstractAggregateRoot<ClassSession> {
  @Embedded
  @Getter
  private CourseId  courseId;

  @Column(nullable = false)
  @Getter
  private LocalDate date;

  @ElementCollection
  @CollectionTable(name="attendance_record", joinColumns = @JoinColumn(name="class_session_id"))
  @Getter
  private List<AttendanceRecord> attendance = new ArrayList<>();

  protected ClassSession(){}

 /**
  *
  */
 public ClassSession(CreateClassSessionCommand command){
  this.courseId = command.courseId();
  this.date = command.date();
  this.attendance = new ArrayList<>(command.attendance());
 }
}
