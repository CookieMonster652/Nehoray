package com.time.timing.Network;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.time.timing.Data.DataManager;
import com.time.timing.Utils.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class RefuseScheduleRequestPOST {

    private Application application;
    private MutableLiveData<Boolean> data;
    private CollectionReference MySchedule;
    private FirebaseAuth Mauth;
    private CollectionReference UserRef;

    public RefuseScheduleRequestPOST(Application application){
        this.application = application;
        Mauth = FirebaseAuth.getInstance();
        MySchedule = FirebaseFirestore.getInstance().collection(DataManager.MySchedule);
        UserRef = FirebaseFirestore.getInstance().collection(DataManager.Employees);
    }

    public LiveData<Boolean> RefuseScheduleRequest(String ReceiverUID, String Date, String Schedule, boolean IsAdditionHoursNeed, String Type){
        data = new MutableLiveData<>();
        var FirebaseUser = Mauth.getCurrentUser();

        if(FirebaseUser != null){
            UserRef.document(FirebaseUser.getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(error != null){
                        data.setValue(null);
                        return;
                    }

                    if(value.exists()){
                        var map = new HashMap<String, Object>();
                        map.put(DataManager.Name, value.getString(DataManager.Name));
                        map.put(DataManager.Phone, value.getString(DataManager.Phone));
                        map.put(DataManager.SenderUID, FirebaseUser.getUid());
                        map.put(DataManager.AdditionalHoursYouWant, IsAdditionHoursNeed);
                        map.put(DataManager.Type, Type);

                        MySchedule.document(ReceiverUID).collection(Date).document(Schedule).collection(DataManager.Users)
                                .document(FirebaseUser.getUid()).set(map)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            MySchedule.document(ReceiverUID).collection(Date).document(Schedule).collection(DataManager.Users).addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                @Override
                                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                                    if(error != null){
                                                        data.setValue(false);
                                                        return;
                                                    }
                                                    if(!value.isEmpty()){
                                                        int size = value.size();
                                                        var map = new HashMap<String, Object>();
                                                        map.put(DataManager.RegistrationUsers, String.valueOf(size));

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
                                        data.setValue(false);
                                        Toast.SetToast(application, e.getMessage());
                                    }
                                });
                    }
                }
            });
        }
        return data;
    }

}
