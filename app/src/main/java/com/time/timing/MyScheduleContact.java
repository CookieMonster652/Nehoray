package com.time.timing;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

@Entity(tableName = "ScheduleContactDB")
@Setter @Getter
public class MyScheduleContact {

    @PrimaryKey
    private @NonNull
    String PhoneNumber;
    private String Name;



}
