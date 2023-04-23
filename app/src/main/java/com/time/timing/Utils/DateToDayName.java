package com.time.timing.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateToDayName {

    public static String GetDate(String Date){
        var sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        {
            try {
                date = sdf.parse(Date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        sdf.applyPattern("EEEE, d MMM");
        String str = sdf.format(date);
        return str;
    }

}
