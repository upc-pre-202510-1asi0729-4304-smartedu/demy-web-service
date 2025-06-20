package com.smartedu.demy.platform.attendance.interfaces.rest.resources;

import java.util.List;

/**
 * Resource record for a class session.
 * @summary
 * This record represents the resource for a class session.
 * It contains the
 */
public record ClassSessionResource( String courseId,String date, List<AttendanceRecordResource> attendance) {
}
