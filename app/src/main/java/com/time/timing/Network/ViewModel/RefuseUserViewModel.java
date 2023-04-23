package com.time.timing.Network.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.time.timing.RefuseModel;
import com.time.timing.Utils.RefuseDao;
import com.time.timing.Utils.RefuseDatabase;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RefuseUserViewModel extends AndroidViewModel {

    private RefuseDao refuseDao;
    private RefuseDatabase refuseDatabase;
    private Executor executor;

    public RefuseUserViewModel(@NonNull Application application) {
        super(application);

        refuseDatabase = RefuseDatabase.getAddressDatabase(application);
        refuseDao = refuseDatabase.getdao();
        executor = Executors.newSingleThreadExecutor();
    }

    public void InsertRefuseUser(List<RefuseModel> list){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if(list != null){
                    refuseDao.InsetRefuse(list);
                }
            }
        });
    }

    public LiveData<List<RefuseModel>> GetRefuseUser(){
        return refuseDao.GetRefuseUserData();
    }

    public LiveData<List<RefuseModel>> SearchByName(String Name){
        return refuseDao.GetRefuseUserFromName(Name);
    }


}
