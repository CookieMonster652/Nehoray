package com.time.timing.Utils;

import android.text.format.DateFormat;

import com.time.timing.Data.DataManager;

import java.util.Calendar;
import java.util.Locale;

public class Date {

    public static String GetCurrentDate(){
        var timestamp = System.currentTimeMillis();
        var calender = Calendar.getInstance(Locale.ENGLISH);
        calender.setTimeInMillis(timestamp);
        var CurrentDate = DateFormat.format(DataManager.DateFormat, calender).toString();
        return CurrentDate;
    }
}
