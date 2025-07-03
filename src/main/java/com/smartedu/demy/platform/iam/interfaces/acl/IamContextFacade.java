package com.smartedu.demy.platform.iam.interfaces.acl;

/**
 * IamContextFacade
 */
public interface IamContextFacade {

    Long fetchTeacherIdByFullName(String firstName, String lastName);

}