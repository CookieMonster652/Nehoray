package com.time.timing;

import java.util.List;

public class PreviousShiftUserModel {

    private String Shift_start_time;
    private String Shift_end_time;
    private String ShiftName;

    private List<UserModel> userModelList;

    public PreviousShiftUserModel(String shift_start_time, String shift_end_time, String shiftName, List<UserModel> userModelList) {
        Shift_start_time = shift_start_time;
        Shift_end_time = shift_end_time;
        ShiftName = shiftName;
        this.userModelList = userModelList;
    }

    public String getShift_start_time() {
        return Shift_start_time;
    }

    public void setShift_start_time(String shift_start_time) {
        Shift_start_time = shift_start_time;
    }

    public String getShift_end_time() {
        return Shift_end_time;
    }

    public void setShift_end_time(String shift_end_time) {
        Shift_end_time = shift_end_time;
    }

    public String getShiftName() {
        return ShiftName;
    }

    public void setShiftName(String shiftName) {
        ShiftName = shiftName;
    }

    public List<UserModel> getUserModelList() {
        return userModelList;
    }

    public void setUserModelList(List<UserModel> userModelList) {
        this.userModelList = userModelList;
    }
}
