package com.time.timing.Utils;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.time.timing.MyScheduleContact;
import java.util.List;

@Dao
public interface MyScheduleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void AddMySchedule(MyScheduleContact myScheduleContact);

    @Query("SELECT * FROM ScheduleContactDB")
    public LiveData<List<MyScheduleContact>> AddScheduleContact();

    @Query("DELETE FROM ScheduleContactDB WHERE PhoneNumber = :PhoneID")
    public void RemoveItem(String PhoneID);

}
