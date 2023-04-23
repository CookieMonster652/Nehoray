package com.time.timing.Data.Constant;

import com.time.timing.Data.DataManager;
import com.time.timing.DayNameModel;
import java.util.ArrayList;
import java.util.List;

public class DayNameData {

    public static List<DayNameModel> GetDays(){
        List<DayNameModel> data = new ArrayList<>();

        DayNameModel Saturday = new DayNameModel();
        Saturday.setDayName(DataManager.Saturday);
        Saturday.setModels(ScheduleData.GetAllShift());

        DayNameModel Sunday = new DayNameModel();
        Sunday.setDayName(DataManager.Sunday);
        Sunday.setModels(ScheduleData.GetAllShift());

        DayNameModel Monday = new DayNameModel();
        Monday.setDayName(DataManager.Monday);
        Monday.setModels(ScheduleData.GetAllShift());

        DayNameModel Tuesday = new DayNameModel();
        Tuesday.setDayName(DataManager.Tuesday);
        Tuesday.setModels(ScheduleData.GetAllShift());

        DayNameModel Wednesday =  new DayNameModel();
        Wednesday.setDayName(DataManager.Wednesday);
        Wednesday.setModels(ScheduleData.GetAllShift());

        DayNameModel Thursday = new DayNameModel();
        Thursday.setDayName(DataManager.Thursday);
        Thursday.setModels(ScheduleData.GetAllShift());

        DayNameModel Friday = new DayNameModel();
        Friday.setDayName(DataManager.Friday);
    //    Friday.setModels(ScheduleData.GetAllShift());

        data.add(Saturday);
        data.add(Sunday);
        data.add(Monday);
        data.add(Tuesday);
        data.add(Wednesday);
        data.add(Thursday);
        data.add(Friday);

        return data;
    }
}
