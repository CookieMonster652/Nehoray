package com.time.timing.Network;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CheckUserProfileExistsGET {

    private Application application;
    private MutableLiveData<Boolean> data;
    private CollectionReference MUserRef;
    private FirebaseAuth Mauth;

    public CheckUserProfileExistsGET(Application application){
        this.application = application;
        Mauth = FirebaseAuth.getInstance();
    }

    public LiveData<Boolean> GetUserProfileExists(String CollectionName){
        FirebaseUser Muser = Mauth.getCurrentUser();
        data = new MutableLiveData<>();
        if(Muser != null){
            MUserRef = FirebaseFirestore.getInstance().collection(CollectionName);
            MUserRef.document(Muser.getUid()).addSnapshotListener((value, error) -> {
                if(error != null){
                    data.setValue(false);
                    return;
                }
                if(value.exists()){
                    data.setValue(true);
                }else {
                    data.setValue(false);
                }
            });
        }
        return data;
    }
}
