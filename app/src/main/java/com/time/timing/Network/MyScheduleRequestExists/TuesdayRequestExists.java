package com.time.timing.Network.MyScheduleRequestExists;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.time.timing.Data.DataManager;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TuesdayRequestExists {
    private Application application;
    private MutableLiveData<Boolean> data;
    private CollectionReference MyRequestRef;
    private FirebaseAuth Mauth;
    private Executor executor;

    public TuesdayRequestExists(Application application){
        this.application = application;
        MyRequestRef = FirebaseFirestore.getInstance().collection(DataManager.MySchedule);
        Mauth = FirebaseAuth.getInstance();
        executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<Boolean> TuesdayRequestExists(String Date){
        data = new MutableLiveData<>();
        var FirebaseUser = Mauth.getCurrentUser();
        if(FirebaseUser != null){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    MyRequestRef.document(FirebaseUser.getUid()).collection(Date)
                            .addSnapshotListener((value, error) -> {
                                if (error != null) {
                                    data.setValue(false);
                                    return;
                                }
                                if(!value.isEmpty()){
                                    data.setValue(true);
                                }else {
                                    data.setValue(false);
                                }
                            });
                }
            });

        }
        return data;
    }
}
