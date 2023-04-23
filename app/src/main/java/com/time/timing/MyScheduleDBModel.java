package com.time.timing;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MyScheduleDB")
public class MyScheduleDBModel {

    @PrimaryKey(autoGenerate = false)
    private @NonNull String Data;

    private String Additional_hours_you_want, Minimal_shift_par_workers, Name, Number_of_workers_for_this_shift;
    private String Phone, RegistrationEndDate, RegistrationEndTime, RegistrationStartDate, RegistrationStartTime;
    private String RegistrationUsers, ShiftName, Shift_end_time, Shift_start_time;
    private long Timestamp;
    private String UID;
    private String ShiftManager;

    public MyScheduleDBModel(){}

    public MyScheduleDBModel(String data, String shift_end_time, String shift_start_time, String UID, String ShiftName, String ShiftManager) {
        Data = data;
        Shift_end_time = shift_end_time;
        Shift_start_time = shift_start_time;
        this.UID = UID;
        this.ShiftName = ShiftName;
        this.ShiftManager = ShiftManager;
    }



    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public String getAdditional_hours_you_want() {
        return Additional_hours_you_want;
    }

    public void setAdditional_hours_you_want(String additional_hours_you_want) {
        Additional_hours_you_want = additional_hours_you_want;
    }

    public String getMinimal_shift_par_workers() {
        return Minimal_shift_par_workers;
    }

    public void setMinimal_shift_par_workers(String minimal_shift_par_workers) {
        Minimal_shift_par_workers = minimal_shift_par_workers;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNumber_of_workers_for_this_shift() {
        return Number_of_workers_for_this_shift;
    }

    public void setNumber_of_workers_for_this_shift(String number_of_workers_for_this_shift) {
        Number_of_workers_for_this_shift = number_of_workers_for_this_shift;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getRegistrationEndDate() {
        return RegistrationEndDate;
    }

    public void setRegistrationEndDate(String registrationEndDate) {
        RegistrationEndDate = registrationEndDate;
    }

    public String getRegistrationEndTime() {
        return RegistrationEndTime;
    }

    public void setRegistrationEndTime(String registrationEndTime) {
        RegistrationEndTime = registrationEndTime;
    }

    public String getRegistrationStartDate() {
        return RegistrationStartDate;
    }

    public void setRegistrationStartDate(String registrationStartDate) {
        RegistrationStartDate = registrationStartDate;
    }

    public String getRegistrationStartTime() {
        return RegistrationStartTime;
    }

    public void setRegistrationStartTime(String registrationStartTime) {
        RegistrationStartTime = registrationStartTime;
    }

    public String getRegistrationUsers() {
        return RegistrationUsers;
    }

    public void setRegistrationUsers(String registrationUsers) {
        RegistrationUsers = registrationUsers;
    }

    public String getShiftName() {
        return ShiftName;
    }

    public void setShiftName(String shiftName) {
        ShiftName = shiftName;
    }

    public String getShift_end_time() {
        return Shift_end_time;
    }

    public void setShift_end_time(String shift_end_time) {
        Shift_end_time = shift_end_time;
    }

    public String getShift_start_time() {
        return Shift_start_time;
    }

    public void setShift_start_time(String shift_start_time) {
        Shift_start_time = shift_start_time;
    }

    public long getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(long timestamp) {
        Timestamp = timestamp;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getShiftManager() {
        return ShiftManager;
    }

    public void setShiftManager(String shiftManager) {
        ShiftManager = shiftManager;
    }
}
