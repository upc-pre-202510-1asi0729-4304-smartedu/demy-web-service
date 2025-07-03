package com.smartedu.demy.platform.scheduling.interfaces.acl;

/**
 * WeeklySchedulesContextFacade
 */
public interface WeeklySchedulesContextFacade {

    /**
     * Fetch a weekly schedule ID by its name.
     * @param name The name of the weekly schedule
     * @return The weekly schedule ID
     */
    Long fetchWeeklyScheduleIdByName(String name);
}