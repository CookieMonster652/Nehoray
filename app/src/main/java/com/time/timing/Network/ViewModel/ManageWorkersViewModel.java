package com.time.timing.Network.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.time.timing.MyScheduleContact;
import com.time.timing.Utils.ManageWorkrsDatabase;
import com.time.timing.Utils.MyScheduleDao;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ManageWorkersViewModel extends AndroidViewModel {

    private ManageWorkrsDatabase manageWorkrsDatabase;
    private MyScheduleDao dao;
    private Executor executor;

    public ManageWorkersViewModel(@NonNull Application application) {
        super(application);

        manageWorkrsDatabase = ManageWorkrsDatabase.getAddressDatabase(application);
        dao = manageWorkrsDatabase.getdao();
        executor = Executors.newSingleThreadExecutor();
    }

    public void AddDailySchedule(MyScheduleContact myScheduleContact){
        executor.execute(() -> dao.AddMySchedule(myScheduleContact));
    }

    public LiveData<List<MyScheduleContact>> GetMySchedule(){
        return dao.AddScheduleContact();
    }

    public void DeleteUser(String PhoneID){
        executor.execute(() -> dao.RemoveItem(PhoneID));
    }


}
