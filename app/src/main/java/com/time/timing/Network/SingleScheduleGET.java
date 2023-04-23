package com.time.timing.Network;

import android.app.Application;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.time.timing.Data.DataManager;
import com.time.timing.MyScheduleModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class SingleScheduleGET {

    private Application application;
    private MutableLiveData<MyScheduleModel> data;
    private CollectionReference MyScheduleRef;
    private FirebaseAuth Mauth;

    public SingleScheduleGET(Application application){
        this.application = application;
        MyScheduleRef = FirebaseFirestore.getInstance().collection(DataManager.MySchedule);
        Mauth = FirebaseAuth.getInstance();
    }

    public LiveData<MyScheduleModel> GetSingleMySchedule(String Date, String ShiftName){
        data = new MutableLiveData<>();
        var FirebaseUser = Mauth.getCurrentUser();
        if(FirebaseUser != null){
            MyScheduleRef.document(FirebaseUser.getUid())
                    .collection(Date).document(ShiftName).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            if(error != null){
                                data.setValue(null);
                                return;
                            }
                            if(value.exists()){
                                data.setValue(value.toObject(MyScheduleModel.class));
                            }else {
                               data.setValue(null);
                            }
                        }
                    });
        }
        return data;
    }
}
