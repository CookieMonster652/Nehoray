package com.time.timing.Network.AllSchedule;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.time.timing.Data.DataManager;
import com.time.timing.FridaySchaduleModel;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FridayScheduleGET {

    private Application application;
    private MutableLiveData<List<FridaySchaduleModel>> data;
    private CollectionReference MSundayRef;
    private FirebaseAuth Mauth;
    private Executor executor;

    public FridayScheduleGET(Application application){
        this.application = application;
        MSundayRef = FirebaseFirestore.getInstance().collection(DataManager.Schedule);
        Mauth = FirebaseAuth.getInstance();
        executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<FridaySchaduleModel>> GetFridaySchedule(String dayName){
        data = new MutableLiveData<>();
        var FirebaseUser = Mauth.getCurrentUser();
        if(FirebaseUser != null){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    MSundayRef.document(FirebaseUser.getUid()).collection(dayName).addSnapshotListener((value, error) -> {
                        if(error != null){
                            data.setValue(null);
                            return;
                        }
                        if(value.isEmpty()){
                            data.setValue(null);
                        }else {
                            for(var ds : value.getDocumentChanges()){
                                if(ds.getType() == DocumentChange.Type.ADDED || ds.getType() == DocumentChange.Type.MODIFIED || ds.getType() == DocumentChange.Type.REMOVED){
                                    data.setValue(value.toObjects(FridaySchaduleModel.class));
                                }
                            }
                        }
                    });

                }
            });
        }
        return data;
    }
}
