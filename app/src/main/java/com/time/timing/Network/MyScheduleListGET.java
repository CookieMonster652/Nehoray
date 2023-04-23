package com.time.timing.Network;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.time.timing.Data.DataManager;
import com.time.timing.MyScheduleModel;

import java.util.List;

public class MyScheduleListGET {

    private Application application;
    private MutableLiveData<List<MyScheduleModel>> data;
    private FirebaseAuth Mauth;
    private CollectionReference MyScheduleRef;

    public MyScheduleListGET(Application application){
        this.application = application;
        Mauth = FirebaseAuth.getInstance();
        MyScheduleRef = FirebaseFirestore.getInstance().collection(DataManager.MySchedule);
    }

    public LiveData<List<MyScheduleModel>> GetMyScheduleList(String Date){
        data = new MutableLiveData<>();
        var FirebaseUser = Mauth.getCurrentUser();
        if(FirebaseUser != null){
            MyScheduleRef.document(FirebaseUser.getUid())
                    .collection(Date).addSnapshotListener((value, error) -> {
                        if(error != null){
                            return;
                        }
                        if(!value.isEmpty()){
                            for(var ds : value.getDocumentChanges()){
                                if(ds.getType() == DocumentChange.Type.ADDED || ds.getType() == DocumentChange.Type.MODIFIED || ds.getType() == DocumentChange.Type.REMOVED){
                                    data.setValue(value.toObjects(MyScheduleModel.class));
                                }
                            }
                        }else{
                            data.setValue(null);
                        }
                    });
        }
        return data;
    }
}
