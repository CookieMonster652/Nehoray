package com.time.timing.Utils;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.time.timing.Model.AllRegisterUserDataModel;

@Database(entities = {AllRegisterUserDataModel.class}, version = 1)
public abstract class AllRegisterUserDatabase extends RoomDatabase {


    public abstract AllRegisterUserDao getdao();
    public static volatile AllRegisterUserDatabase getInstance;

    public static AllRegisterUserDatabase getAllRegisterUserDatabase(final Context context) {
        if (getInstance == null) {
            synchronized (AllRegisterUserDatabase.class) {
                if (getInstance == null) {
                    getInstance = Room.databaseBuilder(context, AllRegisterUserDatabase.class, "Name")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return getInstance;
    }
}
