package com.time.timing.Network.AllPreviousSchedule;

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

public class PreviousScheduleFridayGET {

    private Application application;
    private MutableLiveData<List<FridaySchaduleModel>> data;
    private FirebaseAuth Mauth;
    private CollectionReference MyScheduleRef;
    private Executor executor;

    public PreviousScheduleFridayGET(Application application){
        this.application = application;
        Mauth = FirebaseAuth.getInstance();
        MyScheduleRef = FirebaseFirestore.getInstance().collection(DataManager.MySchedule);
        executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<FridaySchaduleModel>> GetPreviousFridayScheduleData(String Date){
        data = new MutableLiveData<>();
        var FirebaseUser = Mauth.getCurrentUser();
        if(FirebaseUser != null){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    MyScheduleRef.document(FirebaseUser.getUid()).collection(Date)
                            .addSnapshotListener((value, error) -> {
                                if (error != null) {
                                    data.setValue(null);
                                    return;
                                }
                                if (!value.isEmpty()) {
                                    for (var mydata : value.getDocumentChanges()) {
                                        if (mydata.getType() == DocumentChange.Type.ADDED || mydata.getType() == DocumentChange.Type.REMOVED || mydata.getType() == DocumentChange.Type.MODIFIED) {
                                            data.setValue(value.toObjects(FridaySchaduleModel.class));
                                        }
                                    }
                                } else {
                                    data.setValue(null);
                                }
                            });
                }
            });

        }
        return data;
    }
}
