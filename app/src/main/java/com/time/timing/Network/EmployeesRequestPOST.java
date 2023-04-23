package com.time.timing.Network;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.time.timing.Data.DataManager;
import com.time.timing.Utils.Toast;

import java.util.HashMap;

public class EmployeesRequestPOST {

    private Application application;
    private MutableLiveData<Boolean> data;
    private CollectionReference ScheduleRequest;
    private DocumentReference MyScheduleUpdate;
    private FirebaseAuth Mauth;

    public EmployeesRequestPOST(Application application){
        this.application = application;
        Mauth = FirebaseAuth.getInstance();
    }

    public LiveData<Boolean> SendRequestManager(String Date, String ScheduleNo, String Name, String SenderUID){
        var FirebaseUser = Mauth.getCurrentUser();
        if(FirebaseUser != null){
            ScheduleRequest = FirebaseFirestore.getInstance().collection(DataManager.MySchedule)
                    .document(FirebaseUser.getUid()).collection(Date).document(ScheduleNo).collection(DataManager.Users);

            MyScheduleUpdate =  FirebaseFirestore.getInstance().collection(DataManager.MySchedule).document(FirebaseUser.getUid())
                    .collection(Date).document(ScheduleNo);


            var Timestamp = System.currentTimeMillis();
            var map = new HashMap<String, Object>();
            map.put(DataManager.Name, Name);
            map.put(DataManager.SenderUID, SenderUID);
            map.put(DataManager.Timestamp, Timestamp);

            ScheduleRequest.document(SenderUID).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        ScheduleRequest.addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                if(error != null){
                                    data.setValue(false);
                                    return;
                                }
                                if(!value.isEmpty()){
                                    var size = value.size();
                                    var map = new HashMap<String, Object>();
                                    map.put(DataManager.RegistrationUsers, size);

                                    MyScheduleUpdate.update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                data.setValue(true);
                                            }else {
                                                data.setValue(false);
                                                Toast.SetToast(application, task.getException().getMessage());
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.SetToast(application, e.getMessage());
                                            data.setValue(false);
                                        }
                                    });
                                }
                            }
                        });
                    }else {
                        data.setValue(false);
                        Toast.SetToast(application, task.getException().getMessage());
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.SetToast(application, e.getMessage());
                    data.setValue(false);
                }
            });
        }
        return data;
    }
}
