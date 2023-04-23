package com.time.timing;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity(tableName = "ContactDB")
public class ContactModel {

    private @NonNull
    String Name;

    @PrimaryKey(autoGenerate = false)
    private @androidx.annotation.NonNull
    String PhoneNumber;
    String UID;
    private boolean isIsCheck;
}
