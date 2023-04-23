package com.time.timing.Utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class GetWeeksName {

    public static  String[] getDaysOfWeek() {
        String[] days = new String[7];
        DateTimeFormatter dayOfMonthFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Jerusalem"));
        // go back to Sunday, then forward 1 day to get Monday
        LocalDate day = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
                .plusDays(7);
        days[0] = day.format(dayOfMonthFormatter);
        for (int i = 1; i < 7; i++) {
            day = day.plusDays(1);
            days[i] = day.format(dayOfMonthFormatter);
        }
        return days;
    }

    public static String[] getCurrentDayOfWeek(){
        var days = new String[7];
        var dayOfMonthFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        var today = LocalDate.now(ZoneId.of("Asia/Jerusalem"));
        var day = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
                .plusDays(0);
        days[0] = day.format(dayOfMonthFormatter);
        for (int i = 1; i < 7; i++) {
            day = day.plusDays(1);
            days[i] = day.format(dayOfMonthFormatter);
        }
        return days;
    }
}
