package com.time.timing.Network.AllPreviousSchedule;

import android.app.Application;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.time.timing.Data.DataManager;
import com.time.timing.ThursdaySchaduleModel;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PreviousScheduleThursdayGET {

    private Application application;
    private MutableLiveData<List<ThursdaySchaduleModel>> data;
    private FirebaseAuth Mauth;
    private CollectionReference MyScheduleRef;
    private Executor executor;

    public PreviousScheduleThursdayGET(Application application){
        this.application = application;
        Mauth = FirebaseAuth.getInstance();
        MyScheduleRef = FirebaseFirestore.getInstance().collection(DataManager.MySchedule);
        executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<ThursdaySchaduleModel>> GetPreviousThursdayScheduleData(String Date){
        data = new MutableLiveData<>();
        var FirebaseUser = Mauth.getCurrentUser();
        if(FirebaseUser != null){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    MyScheduleRef.document(FirebaseUser.getUid()).collection(Date)
                            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                    if(error != null){
                                        data.setValue(null);
                                        return;
                                    }
                                    if(!value.isEmpty()){
                                        for(var mydata : value.getDocumentChanges()){
                                            if(mydata.getType() == DocumentChange.Type.ADDED || mydata.getType() == DocumentChange.Type.MODIFIED || mydata.getType() == DocumentChange.Type.REMOVED){
                                                data.setValue(value.toObjects(ThursdaySchaduleModel.class));
                                            }
                                        }
                                    }else {
                                        data.setValue(null);
                                    }
                                }
                            });
                }
            });

        }
        return data;
    }
}
