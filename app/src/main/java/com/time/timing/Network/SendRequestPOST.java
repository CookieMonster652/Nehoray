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
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;


public class SendRequestPOST {

    private Application application;
    private MutableLiveData<Boolean> data;
    private FirebaseAuth Mauth;
    private CollectionReference RefuseRef;

    public SendRequestPOST(Application application){
        this.application = application;
        Mauth = FirebaseAuth.getInstance();
        RefuseRef = FirebaseFirestore.getInstance().collection(DataManager.Refuse);
    }

    public LiveData<Boolean> SendRequest(String StartDate, String EndDate, String SenderUID, String Name, String Phone){
        var FirebaseUser = Mauth.getCurrentUser();
        if(FirebaseUser != null){
            data = new MutableLiveData<>();

            var map = new HashMap<String, Object>();
            map.put(DataManager.StartDate, StartDate+"-"+EndDate);
            map.put(DataManager.Name, Name);
            map.put(DataManager.Phone, Phone);
            map.put(DataManager.SenderUID, SenderUID);
            map.put(DataManager.UID, FirebaseUser.getUid());

            RefuseRef.document(FirebaseUser.getUid()).collection(StartDate+"-"+EndDate)
                    .document(SenderUID).set(map)
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
        return data;
    }
}
