package com.time.timing.Utils;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.time.timing.MyScheduleContact;

@Database(entities = {MyScheduleContact.class}, version = 4)
public abstract class ManageWorkrsDatabase extends RoomDatabase {

    public abstract MyScheduleDao getdao();
    public static volatile ManageWorkrsDatabase getInstance;

    public static ManageWorkrsDatabase getAddressDatabase(final Context context) {
        if (getInstance == null) {
            synchronized (ManageWorkrsDatabase.class) {
                if (getInstance == null) {
                    getInstance = Room.databaseBuilder(context, ManageWorkrsDatabase.class, "Name")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return getInstance;
    }

}
