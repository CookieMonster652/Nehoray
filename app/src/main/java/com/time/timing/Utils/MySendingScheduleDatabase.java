package com.time.timing.Utils;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.time.timing.MyScheduleDBModel;

@Database(entities = {MyScheduleDBModel.class}, version = 3)
public abstract class MySendingScheduleDatabase extends RoomDatabase {

    public abstract MySendingScheduleDao getdao();
    public static volatile MySendingScheduleDatabase getInstance;

    public static MySendingScheduleDatabase getAddressDatabase(final Context context) {
        if (getInstance == null) {
            synchronized (MySendingScheduleDatabase.class) {
                if (getInstance == null) {
                    getInstance = Room.databaseBuilder(context, MySendingScheduleDatabase.class, "Name")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return getInstance;
    }
}
