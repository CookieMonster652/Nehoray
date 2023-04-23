package com.time.timing;
import lombok.Data;

@Data
public class DateModel {
    private String Date;
    private String DayName;
    private boolean isIsCheck;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDayName() {
        return DayName;
    }

    public void setDayName(String dayName) {
        DayName = dayName;
    }

    public boolean isIsCheck() {
        return isIsCheck;
    }

    public void setIsCheck(boolean isCheck) {
        isIsCheck = isCheck;
    }
}
