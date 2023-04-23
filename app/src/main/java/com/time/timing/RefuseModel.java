package com.time.timing;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(tableName = "RefuseUserDB")
public class RefuseModel {

    private String Date;
    private String Name;
    private String Phone;
    private String SenderUID;

    @PrimaryKey
    @NonNull
    private String UID;
}
