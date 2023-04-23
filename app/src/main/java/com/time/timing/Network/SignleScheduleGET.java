package com.time.timing.Network;

import android.app.Application;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.time.timing.Data.DataManager;
import com.time.timing.MyScheduleModel;

public class SignleScheduleGET {

    private Application application;
    private MutableLiveData<MyScheduleModel> data;
    private CollectionReference MySchedule;
    private FirebaseAuth Mauth;

    public SignleScheduleGET(Application application){
        this.application = application;
        Mauth = FirebaseAuth.getInstance();
        MySchedule = FirebaseFirestore.getInstance().collection(DataManager.MySchedule);
    }

    public LiveData<MyScheduleModel> GetSingleSchedule(String Date, String ShiftName){
        var FirebaseUser = Mauth.getCurrentUser();
        data = new MutableLiveData<>();


        if(FirebaseUser != null){
            MySchedule.document(FirebaseUser.getUid()).collection(Date).document(ShiftName)
                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
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
