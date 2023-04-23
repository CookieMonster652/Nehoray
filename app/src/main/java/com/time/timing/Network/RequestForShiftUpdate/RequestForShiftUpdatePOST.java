package com.time.timing.Network.RequestForShiftUpdate;

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

public class RequestForShiftUpdatePOST {

    private Application application;
    private MutableLiveData<Boolean> data;
    private CollectionReference RequestForShiftRef;
    private FirebaseAuth Mauth;

    public RequestForShiftUpdatePOST(Application application){
        this.application = application;
        Mauth = FirebaseAuth.getInstance();
        RequestForShiftRef = FirebaseFirestore.getInstance().collection(DataManager.RequestForShift);
    }

    public LiveData<Boolean> UpdateRequestForShift(String Number, String Date, String ShiftName, String Type){
        data = new MutableLiveData<>();
        var FirebaseUser = Mauth.getCurrentUser();
        if(FirebaseUser != null){

            var map = new HashMap<String, Object>();
            map.put(DataManager.Type, Type);
            RequestForShiftRef.document(Number).collection(Date).document(ShiftName)
                    .update(map)
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
