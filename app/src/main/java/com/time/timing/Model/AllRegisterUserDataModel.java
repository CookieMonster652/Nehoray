package com.time.timing.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(tableName = "AllRegisterUserDataModelDB")
public class AllRegisterUserDataModel {
    private String Name;
    private String Phone;
    private String ShiftCount;
    @PrimaryKey(autoGenerate = false)
    private @NonNull String UID;
}
