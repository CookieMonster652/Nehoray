package com.time.timing.Network;

import android.app.Application;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.time.timing.Data.DataManager;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;

public class SchedulePOST {

    private Application application;
    private MutableLiveData<Boolean> data;
    private CollectionReference MScheduleRef;
    private FirebaseAuth MAuth;
    private CollectionReference ManagerRef;

    public SchedulePOST(Application application){
        this.application = application;
        MScheduleRef = FirebaseFirestore.getInstance().collection(DataManager.Schedule);
        MAuth = FirebaseAuth.getInstance();
        ManagerRef = FirebaseFirestore.getInstance().collection(DataManager.Manager);
    }

    public LiveData<Boolean> SendSchedule(String datename, int Schedule, String ShiftStartTime,  String ShiftEndTime, String NumberOfWorkerForThisShift, String AdditionalHoursYouWant, String MinimumShiftParWorker){
        data = new MutableLiveData<>();
        var firebaseuser = MAuth.getCurrentUser();
        if(firebaseuser != null){

            ManagerRef.document(firebaseuser.getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(error != null){
                        data.setValue(false);
                        return;
                    }

                    if(value.exists()){
                        var map = new HashMap<String, Object>();
                        var Timestamp = System.currentTimeMillis();
                        map.put(DataManager.ShiftStartTime, ShiftStartTime);
                        map.put(DataManager.ShiftEndTime, ShiftEndTime);
                        map.put(DataManager.NumberOFWorkersForThisShift, NumberOfWorkerForThisShift);
                        map.put(DataManager.AdditionalHoursYouWant, AdditionalHoursYouWant);
                        map.put(DataManager.MinimalShiftParWorkers  , MinimumShiftParWorker);
                        map.put(DataManager.ShifName, String.valueOf(Schedule));
                        map.put(DataManager.Timestamp, Timestamp);
                        map.put(DataManager.Data, datename);
                        map.put(DataManager.Shift_Manager, value.getString(DataManager.Name));
                        MScheduleRef.document(firebaseuser.getUid())
                                .collection(datename).document(String.valueOf(Schedule))
                                .set(map).addOnCompleteListener(task -> {
                                    if(task.isSuccessful()){
                                        data.setValue(true);
                                    }else {
                                        data.setValue(false);
                                    }
                                }).addOnFailureListener(e -> {
                                    data.setValue(false);
                                });
                    }
                }
            });

        }
        return data;
    }
}
