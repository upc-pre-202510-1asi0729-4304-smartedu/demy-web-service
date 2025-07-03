package com.smartedu.demy.platform.attendance.domain.model.aggregates;

import com.smartedu.demy.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.CourseId;
import com.smartedu.demy.platform.attendance.domain.model.entities.AttendanceRecord;
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

 @OneToMany(mappedBy = "classSession", cascade = CascadeType.ALL, orphanRemoval = true)
 @Getter
 private List<AttendanceRecord> attendance = new ArrayList<>();

  protected ClassSession(){}

 /**
  *
  */
 public ClassSession(CreateClassSessionCommand command){
  this.courseId = command.courseId();
  this.date = command.date();
  command.attendance().forEach(draft -> this.addAttendance(
          new AttendanceRecord(draft.studentId(), draft.status())
  ));
 }
 /** Agregar asistencia garantizando consistencia */
 public void addAttendance(AttendanceRecord record) {
  record.setClassSession(this);
  this.attendance.add(record);
 }

 /** Quitar asistencia garantizando consistencia */
 public void removeAttendance(AttendanceRecord record) {
  record.setClassSession(null);
  this.attendance.remove(record);
 }
}
