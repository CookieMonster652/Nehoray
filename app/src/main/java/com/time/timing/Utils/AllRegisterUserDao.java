package com.time.timing.Utils;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.time.timing.Model.AllRegisterUserDataModel;
import java.util.List;


@Dao
public interface AllRegisterUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void InsertRegisterUser(List<AllRegisterUserDataModel> allRegisterUserDataModel);

    @Query("SELECT * FROM AllRegisterUserDataModelDB")
    public LiveData<List<AllRegisterUserDataModel>> GetAllRegisterUserData();


    @Query("SELECT * FROM AllRegisterUserDataModelDB WHERE Name Like '%' || :NameData || '%'")
    public LiveData<List<AllRegisterUserDataModel>> SearchByName(String NameData);

    @Query("DELETE  FROM AllRegisterUserDataModelDB")
    public void RemoveAllData();
}
