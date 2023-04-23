package com.time.timing;

import lombok.Data;

@Data
public class FridaySchaduleModel {
    private String Additional_hours_you_want;
    private String Minimal_shift_par_workers;
    private String Number_of_workers_for_this_shift;
    private String Shift_end_time;
    private String Shift_start_time;
    private String ShiftName;
    private String Data;
    private String UID;
    private boolean RequestForAdditionalHour;
    private String SenderUID;
    private String ShiftManager;
}
