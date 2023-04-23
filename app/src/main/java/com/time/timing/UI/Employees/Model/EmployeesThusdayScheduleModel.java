package com.time.timing.UI.Employees.Model;

import lombok.Data;

@Data
public class EmployeesThusdayScheduleModel {
    private String Additional_hours_you_want;
    private String Data;
    private String Minimal_shift_par_workers;
    private String Name;
    private String Number_of_workers_for_this_shift;
    private String Phone;
    private String RegistrationEndDate;
    private String RegistrationEndTime;
    private String RegistrationStartDate;
    private String RegistrationStartTime;
    private String ShiftName;
    private String Shift_end_time;
    private String Shift_start_time;
    private String UID;
    private long Timestamp;
    private boolean IsEnable;
    private String ShiftManager;
}
