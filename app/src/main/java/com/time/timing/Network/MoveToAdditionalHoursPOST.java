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

public class MoveToAdditionalHoursPOST {

    private Application application;
    private MutableLiveData<Boolean> data;
    private CollectionReference MySchedule;
    private FirebaseAuth Mauth;

    public MoveToAdditionalHoursPOST(Application application){
        this.application = application;
        MySchedule = FirebaseFirestore.getInstance().collection(DataManager.MySchedule);
        Mauth = FirebaseAuth.getInstance();
    }

    public LiveData<Boolean> MoveToAdditionalHours(String Date, String ShiftNumber, String SenderUID){
        data = new MutableLiveData<>();

        var FirebaseUser = Mauth.getCurrentUser();
        if(FirebaseUser != null){

            var map = new HashMap<String, Object>();
            map.put(DataManager.AdditionalHoursYouWant, true);

            MySchedule.document(FirebaseUser.getUid())
                    .collection(Date).document(ShiftNumber)
                    .collection(DataManager.Users).document(SenderUID)
                    .update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                data.setValue(true);
                            }else {
                                Toast.SetToast(application, task.getException().getMessage());
                                data.setValue(false);
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
