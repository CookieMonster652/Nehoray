package com.time.timing;

import lombok.Data;

@Data
public class MyScheduleModel {

    private String Additional_hours_you_want,Data, Minimal_shift_par_workers, Name, Number_of_workers_for_this_shift;
    private String Phone, RegistrationEndDate, RegistrationEndTime, RegistrationStartDate, RegistrationStartTime;
    private String RegistrationUsers, ShiftName, Shift_end_time, Shift_start_time;
    private long Timestamp;
    private String UID;
}
