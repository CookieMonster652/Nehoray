package com.time.timing.Utils;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.time.timing.ContactModel;

@Database(entities = {ContactModel.class}, version = 2)
public abstract class ContactDatabase extends RoomDatabase {

    public abstract ContactDao contactDao();
    public static volatile ContactDatabase getInstance;

    public static ContactDatabase GetContactDatabase(Context context){
        if(getInstance == null){
            synchronized (ContactDatabase.class){
                if(getInstance == null){
                    getInstance = Room.databaseBuilder(context, ContactDatabase.class, "contact")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return getInstance;
    }
}
