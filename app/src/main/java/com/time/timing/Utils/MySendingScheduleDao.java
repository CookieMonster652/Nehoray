package com.time.timing.Utils;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.time.timing.MyScheduleDBModel;

import java.util.List;

@Dao
public interface MySendingScheduleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void AddMySchedule(MyScheduleDBModel myScheduleDBModel);

    @Query("SELECT * FROM MyScheduleDB")
    public LiveData<List<MyScheduleDBModel>> GetAllMySchedule();

    @Query("SELECT * FROM MyScheduleDB WHERE Data LIKE :Date")
    public LiveData<List<MyScheduleDBModel>> SearchByDate(String Date);

    @Query("DELETE FROM MyScheduleDB")
    public void RemoveAllData();
}
