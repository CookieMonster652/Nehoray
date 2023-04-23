package com.time.timing.Utils;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.time.timing.RefuseModel;

@Database(entities = {RefuseModel.class}, version = 2)
public abstract class RefuseDatabase extends RoomDatabase {

    public abstract RefuseDao getdao();
    public static volatile RefuseDatabase getInstance;

    public static RefuseDatabase getAddressDatabase(final Context context) {
        if (getInstance == null) {
            synchronized (RefuseDatabase.class) {
                if (getInstance == null) {
                    getInstance = Room.databaseBuilder(context, RefuseDatabase.class, "Name")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return getInstance;
    }
}
