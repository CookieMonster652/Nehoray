package com.time.timing.Network.AllPreviousSchedule;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.time.timing.Data.DataManager;
import com.time.timing.MondaySchaduleModel;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PreviousScheduleMondayGET {

    private Application application;
    private MutableLiveData<List<MondaySchaduleModel>> data;
    private FirebaseAuth Mauth;
    private CollectionReference MyScheduleRef;
    private Executor executor;

    public PreviousScheduleMondayGET(Application application){
        this.application = application;
        Mauth = FirebaseAuth.getInstance();
        executor = Executors.newSingleThreadExecutor();
        MyScheduleRef = FirebaseFirestore.getInstance().collection(DataManager.MySchedule);
    }

    public LiveData<List<MondaySchaduleModel>> GetPreviousMondayScheduleData(String Date){
        data = new MutableLiveData<>();
        var FirebaseUser = Mauth.getCurrentUser();
        if(FirebaseUser != null){
            executor.execute(() -> MyScheduleRef.document(FirebaseUser.getUid()).collection(Date)
                    .addSnapshotListener((value, error) -> {
                        if (error != null) {
                            return;
                        }
                        if (!value.isEmpty()) {
                            for (var mydata : value.getDocumentChanges()) {
                                if (mydata.getType() == DocumentChange.Type.ADDED || mydata.getType() == DocumentChange.Type.MODIFIED || mydata.getType() == DocumentChange.Type.REMOVED) {
                                    data.setValue(value.toObjects(MondaySchaduleModel.class));
                                }
                            }
                        } else {
                            data.setValue(null);
                        }
                    }));

        }
        return data;
    }
}
