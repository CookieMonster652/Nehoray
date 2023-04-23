package com.time.timing.Network;

import android.app.Application;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.time.timing.Data.DataManager;
import com.time.timing.ManagerProfileModel;

public class ManagerProfileGET {

    private Application application;
    private MutableLiveData<ManagerProfileModel> data;
    private CollectionReference MManagerRef;
    private FirebaseAuth MAuth;


    public ManagerProfileGET(Application application){
        this.application = application;
        MManagerRef = FirebaseFirestore.getInstance().collection(DataManager.Manager);
        MAuth = FirebaseAuth.getInstance();
    }

    public LiveData<ManagerProfileModel> GetManagerProfileData(){
        data = new MutableLiveData<>();
        FirebaseUser Muser = MAuth.getCurrentUser();
        if(Muser != null){
            MManagerRef.document(Muser.getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(error != null){
                        data.setValue(null);
                        return;
                    }
                    if(value.exists()){
                        data.setValue(value.toObject(ManagerProfileModel.class));
                    }else {
                        data.setValue(null);
                    }
                }
            });
        }
        return data;
    }
}
