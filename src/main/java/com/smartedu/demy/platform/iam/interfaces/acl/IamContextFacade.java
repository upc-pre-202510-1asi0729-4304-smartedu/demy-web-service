package com.smartedu.demy.platform.iam.interfaces.acl;

/**
 * Facade interface that provides a simplified context-based access
 * to IAM (Identity and Access Management) services for other bounded contexts.
 * <p>
 * Typically used to abstract internal IAM query logic from external modules or subsystems.
 * </p>
 */
public interface IamContextFacade {

    /**
     * Retrieves the ID of a teacher given their full name.
     *
     * @param firstName the teacher's first name
     * @param lastName  the teacher's last name
     * @return the teacher's ID if found and valid, otherwise 0L
     */
    Long fetchTeacherIdByFullName(String firstName, String lastName);

}