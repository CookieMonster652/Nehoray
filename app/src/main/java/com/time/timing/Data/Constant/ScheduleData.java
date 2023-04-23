package com.time.timing.Data.Constant;

import com.time.timing.ShiftTimeModel;

import java.util.ArrayList;
import java.util.List;

public class ScheduleData {

    public static List<ShiftTimeModel> GetAllShift(){
        List<ShiftTimeModel> data = new ArrayList<>();

        ShiftTimeModel one = new ShiftTimeModel();
        one.setShift("First Shift");
        one.setTime("10:00 - 12:00");

        ShiftTimeModel two = new ShiftTimeModel();
        two.setTime("12:00 - 14:00");
        two.setShift("Second Shift");

        data.add(one);
        data.add(two);

        return data;
    }
}
