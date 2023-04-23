package com.time.timing.Network;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.time.timing.Data.DataManager;
import com.time.timing.Data.ManageUserShiftModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ManageRegistationShift {

    private Application application;
    private MutableLiveData<List<ManageUserShiftModel>> data;
    private FirebaseAuth Mauth;
    private CollectionReference ManageUserRef;


    public ManageRegistationShift(Application application){
        this.application = application;
        Mauth = FirebaseAuth.getInstance();
        ManageUserRef = FirebaseFirestore.getInstance().collection(DataManager.ManageUsers);
    }

    public LiveData<List<ManageUserShiftModel>> ManageUserShiftModel(String StartDate, String EndDate){
        data = new MutableLiveData<>();
        var FirebaseUser = Mauth.getCurrentUser();
        if(FirebaseUser != null){
            ManageUserRef.document(FirebaseUser.getUid()).collection(StartDate+"-"+EndDate)
                    .addSnapshotListener((value, error) -> {
                        if(error != null){
                            data.setValue(null);
                            return;
                        }
                        if(value.isEmpty()){
                            data.setValue(null);
                        }else {
                            for(var mydata : value.getDocumentChanges()){
                                if(mydata.getType() == DocumentChange.Type.ADDED || mydata.getType() == DocumentChange.Type.MODIFIED || mydata.getType() == DocumentChange.Type.REMOVED){
                                    data.setValue(value.toObjects(ManageUserShiftModel.class));
                                }
                            }
                        }
                    });
        }
        return data;
    }
}
