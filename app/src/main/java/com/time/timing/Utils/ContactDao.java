package com.time.timing.Utils;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.time.timing.ContactModel;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void InsertContact(ContactModel contactModel);

    @Query("SELECT * FROM ContactDB")
    public LiveData<List<ContactModel>> GetAllContact();

    @Query("SELECT * FROM ContactDB WHERE Name Like '%' || :NameData || '%'")
    public LiveData<List<ContactModel>> SearchByName(String NameData);

}