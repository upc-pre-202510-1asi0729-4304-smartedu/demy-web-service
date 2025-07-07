package com.smartedu.demy.platform.iam.domain.model.queries;

import com.smartedu.demy.platform.iam.domain.model.valueobjects.FullName;

/**
 * Query object used to request a teacher account by full name.
 * Typically handled by a query service that searches for a user with the TEACHER role
 * matching the provided full name.
 *
 * @param fullname the full name of the teacher to be searched.
 */
public record GetTeacherByFullNameQuery (FullName fullname){

}





