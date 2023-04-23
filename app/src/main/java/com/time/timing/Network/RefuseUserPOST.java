package com.time.timing.Network;

import android.app.Application;

import androidx.annotation.NonNull;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RefuseUserPOST {

    private Application application;
    private MutableLiveData<Boolean> data;
    private CollectionReference RefuseRef, UserRef;
    private FirebaseAuth Mauth;

    public RefuseUserPOST(Application application){
        this.application = application;
        RefuseRef = FirebaseFirestore.getInstance().collection(DataManager.Refuse);
        Mauth = FirebaseAuth.getInstance();
        UserRef = FirebaseFirestore.getInstance().collection(DataManager.Employees);
    }

    public LiveData<Boolean> RefuseUserHistory(String SenderUID, String StartDate, String EndDate){
        data = new MutableLiveData<>();
        var FirebaseUser = Mauth.getCurrentUser();
        if(FirebaseUser != null){
            UserRef.document(FirebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        var map = new HashMap<String, Object>();
                        map.put(DataManager.Name, task.getResult().getString(DataManager.Name));
                        map.put(DataManager.UID, task.getResult().getString(DataManager.UID));
                        map.put(DataManager.Phone, task.getResult().getString(DataManager.Phone));
                        map.put(DataManager.SenderUID, SenderUID);
                        map.put(DataManager.Date, StartDate+"-"+EndDate);

                        RefuseRef.document(SenderUID).collection(StartDate+"-"+EndDate)
                                .document(FirebaseUser.getUid()).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
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
                                        data.setValue(false);
                                        Toast.SetToast(application, e.getMessage());
                                    }
                                });

                    }else {
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
        return data;
    }

}
