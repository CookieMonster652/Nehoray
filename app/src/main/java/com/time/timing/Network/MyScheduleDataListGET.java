package com.time.timing.Network;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.time.timing.Data.DataManager;
import com.time.timing.MyScheduleModel;
import com.time.timing.Utils.Toast;

import java.util.List;

public class MyScheduleDataListGET {

    private Application application;
    private MutableLiveData<List<MyScheduleModel>> data;
    private FirebaseAuth Mauth;
    private CollectionReference MyScheduleRef;

    public MyScheduleDataListGET(Application application){
        this.application = application;
        Mauth = FirebaseAuth.getInstance();
        MyScheduleRef = FirebaseFirestore.getInstance().collection(DataManager.MySchedule);
    }

    public LiveData<List<MyScheduleModel>> GetMyScheduleData(String Date){
        data = new MutableLiveData<>();
        var FirebaseUser = Mauth.getCurrentUser();
        if(FirebaseUser != null){
            MyScheduleRef.document(FirebaseUser.getUid()).collection(Date)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                data.setValue(task.getResult().toObjects(MyScheduleModel.class));
                            }else {
                                data.setValue(null);
                                Toast.SetToast(application, task.getException().getMessage());
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            data.setValue(null);
                            Toast.SetToast(application, e.getMessage());
                        }
                    });
        }
        return data;
    }
}
