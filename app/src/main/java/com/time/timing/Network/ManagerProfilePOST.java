package com.time.timing.Network;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.time.timing.Data.DataManager;
import com.time.timing.Utils.Toast;

import java.util.HashMap;
import java.util.Map;

public class ManagerProfilePOST {

    private Application application;
    private MutableLiveData<Boolean> data;
    private CollectionReference MuserRef;
    private FirebaseAuth Mauth;

    public ManagerProfilePOST(Application application) {
        this.application = application;
        MuserRef = FirebaseFirestore.getInstance().collection(DataManager.Manager);
        Mauth = FirebaseAuth.getInstance();
    }

    public LiveData<Boolean> SetUpManagerProfile(String NameOFBusiness, String Name, String BusinessLogoDownloadUri) {
        data = new MutableLiveData<>();
        FirebaseUser Muser = Mauth.getCurrentUser();
        if (Muser != null) {


            FirebaseMessaging.getInstance().getToken().addOnSuccessListener(new OnSuccessListener<String>() {
                @Override
                public void onSuccess(String s) {
                    long Timestamp = System.currentTimeMillis();
                    Map<String, Object> map = new HashMap<>();
                    map.put(DataManager.Timestamp, Timestamp);
                    map.put(DataManager.NameOFBusiness, NameOFBusiness);
                    map.put(DataManager.Name, Name);
                    map.put(DataManager.BusinessLogoDownloadUri, BusinessLogoDownloadUri);
                    map.put(DataManager.UID, Muser.getUid());
                    map.put(DataManager.Token, s);

                    MuserRef.document(Muser.getUid()).set(map)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
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
                }
            });

        }
        return data;
    }
}
