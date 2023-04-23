package com.time.timing.Utils;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.time.timing.RefuseModel;

import java.util.List;

@Dao
public interface RefuseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void InsetRefuse(List<RefuseModel> refuseModels);

    @Query("SELECT * FROM RefuseUserDB")
    public LiveData<List<RefuseModel>> GetRefuseUserData();

    @Query("SELECT * FROM RefuseUserDB WHERE Name Like '%' || :NameData || '%' ")
    public LiveData<List<RefuseModel>> GetRefuseUserFromName(String NameData);

}
