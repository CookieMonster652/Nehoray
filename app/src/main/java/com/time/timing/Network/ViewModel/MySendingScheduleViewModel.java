package com.time.timing.Network.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.time.timing.MyScheduleDBModel;
import com.time.timing.Utils.MySendingScheduleDao;
import com.time.timing.Utils.MySendingScheduleDatabase;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MySendingScheduleViewModel extends AndroidViewModel {

    private MySendingScheduleDatabase mySendingScheduleDatabase;
    private MySendingScheduleDao mySendingScheduleDao;
    private Executor executor;

    public MySendingScheduleViewModel(@NonNull Application application) {
        super(application);

        mySendingScheduleDatabase = MySendingScheduleDatabase.getAddressDatabase(application);
        mySendingScheduleDao = mySendingScheduleDatabase.getdao();
        executor = Executors.newSingleThreadExecutor();
    }

    public void InsertSendingSchedule(MyScheduleDBModel myScheduleDBModel){
        executor.execute(() -> {
            mySendingScheduleDao.AddMySchedule(myScheduleDBModel);
        });
    }

    public LiveData<List<MyScheduleDBModel>> GetMyAllSendingSchedule(){
        return mySendingScheduleDao.GetAllMySchedule();
    }

    public LiveData<List<MyScheduleDBModel>> SearchByMyAllSchedule(String Date){
        return mySendingScheduleDao.SearchByDate(Date);
    }

    public void RemoveAllData(){
        executor.execute(() -> {
            mySendingScheduleDao.RemoveAllData();
        });
    }

}
