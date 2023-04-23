package com.time.timing.Network.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.time.timing.Model.AllRegisterUserDataModel;
import com.time.timing.Utils.AllRegisterUserDao;
import com.time.timing.Utils.AllRegisterUserDatabase;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AllRegisterUserViewModel extends AndroidViewModel {

    private AllRegisterUserDatabase allRegisterUserDatabase;
    private AllRegisterUserDao allRegisterUserDao;
    private Executor executor;

    public AllRegisterUserViewModel(@NonNull Application application) {
        super(application);

        allRegisterUserDatabase = AllRegisterUserDatabase.getAllRegisterUserDatabase(application);
        allRegisterUserDao = allRegisterUserDatabase.getdao();
        executor = Executors.newSingleThreadExecutor();
    }

    public void InsertAllRegisterUser(List<AllRegisterUserDataModel> allRegisterUserDataModel){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                allRegisterUserDao.InsertRegisterUser(allRegisterUserDataModel);
            }
        });
    }

    public LiveData<List<AllRegisterUserDataModel>> GetAllRegisterUserData(){
        return allRegisterUserDao.GetAllRegisterUserData();
    }

    public LiveData<List<AllRegisterUserDataModel>> SearchByName(String Name){
        return allRegisterUserDao.SearchByName(Name);
    }

    public void DeleteAllData(){
        executor.execute(() -> {
            allRegisterUserDao.RemoveAllData();
        });
    }
}
