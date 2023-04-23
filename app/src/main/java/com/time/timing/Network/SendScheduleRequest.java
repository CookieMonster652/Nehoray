package com.time.timing.Network;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.time.timing.Data.DataManager;
import com.time.timing.Utils.Toast;

import java.util.HashMap;

public class SendScheduleRequest {

    private Application application;
    private MutableLiveData<Boolean> data;
    private CollectionReference MRequestSchedule;
    private CollectionReference ScheduleRef;
    private FirebaseAuth Mauth;

    public SendScheduleRequest(Application application){
        this.application = application;
        MRequestSchedule = FirebaseFirestore.getInstance().collection(DataManager.ScheduleRequest);
        ScheduleRef = FirebaseFirestore.getInstance().collection(DataManager.Schedule);
        Mauth = FirebaseAuth.getInstance();
    }

    public LiveData<Boolean> SendRequest(String Name, String PhoneNumber, String Date, String RegistrationStartTime, String RegistrationStartDate, String RegistrationEndTime, String RegistrationEndDate){
        data = new MutableLiveData<>();

        var FirebaseUser = Mauth.getCurrentUser();
        if(FirebaseUser != null){
            ScheduleRef.document(FirebaseUser.getUid()).collection(Date).addSnapshotListener((value, error) -> {
                if(error != null){
                    data.setValue(false);
                    return;
                }
                if(!value.isEmpty()){
                    for(var document : value.getDocumentChanges()){
                        if(document.getType() == DocumentChange.Type.ADDED || document.getType() == DocumentChange.Type.MODIFIED || document.getType() == DocumentChange.Type.REMOVED){
                            var map = new HashMap<String, Object>();

                            var Timestamp = System.currentTimeMillis();
                            map.put(DataManager.Name, Name);
                            map.put(DataManager.Phone, PhoneNumber);
                            map.put(DataManager.AdditionalHoursYouWant, document.getDocument().getString(DataManager.AdditionalHoursYouWant));
                            map.put(DataManager.Data, document.getDocument().getString(DataManager.Data));
                            map.put(DataManager.MinimalShiftParWorkers, document.getDocument().getString(DataManager.MinimalShiftParWorkers));
                            map.put(DataManager.NumberOFWorkersForThisShift, document.getDocument().getString(DataManager.NumberOFWorkersForThisShift));
                            map.put(DataManager.ShifName, document.getDocument().getString(DataManager.ShifName));
                            map.put(DataManager.ShiftEndTime, document.getDocument().getString(DataManager.ShiftEndTime));
                            map.put(DataManager.ShiftStartTime, document.getDocument().getString(DataManager.ShiftStartTime));
                            map.put(DataManager.RegistrationStartTime, RegistrationStartTime);
                            map.put(DataManager.RegistrationStartDate, RegistrationStartDate);
                            map.put(DataManager.RegistrationEndTime, RegistrationEndTime);
                            map.put(DataManager.UID, Mauth.getCurrentUser().getUid());
                            map.put(DataManager.RegistrationEndDate, RegistrationEndDate);
                            map.put(DataManager.RegistrationUsers, "0");
                            map.put(DataManager.Timestamp, Timestamp);
                            map.put(DataManager.Shift_Manager, document.getDocument().getString(DataManager.Shift_Manager));

                            MRequestSchedule.document(PhoneNumber).collection(document.getDocument().getString(DataManager.Data)).document(document.getDocument().getString(DataManager.ShifName))
                                    .set(map).addOnCompleteListener(task -> {
                                        if(task.isSuccessful()){
                                            data.setValue(true);
                                        }else {
                                            data.setValue(false);
                                            Toast.SetToast(application, task.getException().getMessage());
                                        }
                                    }).addOnFailureListener(e -> {
                                        data.setValue(false);
                                        Toast.SetToast(application, e.getMessage());
                                    });
                        }
                    }
                }
            });
        }
        return data;
    }
}
