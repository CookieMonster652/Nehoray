package com.time.timing.Utils;

import android.content.Context;

import com.time.timing.Data.DataManager;

import java.text.SimpleDateFormat;

public class ComparingTimeDate {

    private static OnTime OnTime;

    public static void ComparingTime(String TimeStart, String TimeEnd){
        try {
            var simpledateformat = new SimpleDateFormat(DataManager.TimeFormat);
            var first_time = simpledateformat.parse(TimeStart);

            var last_time = simpledateformat.parse(TimeEnd);
            if(first_time.compareTo(last_time) < 0){
                OnTime.Time(DataManager.RegistrationFirstTimeOccursBeforeEndTime);
            }else if(first_time.compareTo(last_time) > 0){
                OnTime.Time(DataManager.RegistrationFirstTimeOccursAfterEndTime);
            }else if(first_time.compareTo(last_time) == 0){
                OnTime.Time(DataManager.RegistrationTimeSame);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public interface OnTime{
        void Time(String val);
    }
    public void OnTimeEvent(OnTime OnTime){
        this.OnTime = OnTime;
    }




    private static OnDate OnDate;
    public static void ComparingDate(String StartDate, String EndDate, Context context){
        try {
            var simpledateformat = new SimpleDateFormat(DataManager.DateFormat);
            var first_date = simpledateformat.parse(StartDate);

            var last_date = simpledateformat.parse(EndDate);
            if(first_date.compareTo(last_date) < 0) {
                OnDate.Date(DataManager.RegistrationFirstDateOccursBeforeEndDate);
            } else if(first_date.compareTo(last_date) > 0) {
                OnDate.Date(DataManager.RegistrationFirstDateOccursAfterEndDate);
            }else if(first_date.compareTo(last_date) == 0){
                OnDate.Date(DataManager.RegistrationDateIsSame);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public interface OnDate{
        void Date(String val);
    }
    public void OnDateEvent(OnDate OnDate){
        this.OnDate = OnDate;
    }
}
