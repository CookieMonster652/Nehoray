package com.time.timing.Network;

import android.app.Application;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.time.timing.Data.DataManager;
import com.time.timing.MyScheduleModel;
import com.time.timing.Network.SharePref.SharePref;

import java.util.List;

public class MyScheduleGET {

    private Application application;
    private CollectionReference ScheduleRequest;
    private FirebaseAuth Mauth;
    private SharePref sharePref;
    private MutableLiveData<List<MyScheduleModel>> data;

    public MyScheduleGET(Application application){
        this.application = application;
        Mauth = FirebaseAuth.getInstance();
        ScheduleRequest = FirebaseFirestore.getInstance().collection(DataManager.ScheduleRequest);
        sharePref = new SharePref(application);
        data = new MutableLiveData<>();
    }

    public LiveData<List<MyScheduleModel>> GetMySchedule(String PhoneNumber, String Date){
        data = new MutableLiveData<>();
        var FirebaseUser = Mauth.getCurrentUser();
        if(FirebaseUser != null){
            ScheduleRequest.document(PhoneNumber).collection(Date)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if(error != null){
                                data.setValue(null);
                                return;
                            }
                            if(!value.isEmpty()){
                                for(var mydata : value.getDocumentChanges()){
                                    if(mydata.getType() == DocumentChange.Type.ADDED || mydata.getType() == DocumentChange.Type.MODIFIED || mydata.getType() == DocumentChange.Type.REMOVED){
                                        data.setValue(value.toObjects(MyScheduleModel.class));
                                    }
                                }
                            }else {
                                data.setValue(null);
                            }
                        }
                    });
        }
        return data;
    }
}
