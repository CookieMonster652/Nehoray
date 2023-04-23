package com.time.timing.Network;

import android.app.Application;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.time.timing.Data.DataManager;
import com.time.timing.Model.AllRegisterUserDataModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class AllRegisterUserGET {

    private Application application;
    private CollectionReference AllUserRegisterRef;
    private FirebaseAuth Mauth;
    private MutableLiveData<List<AllRegisterUserDataModel>> data;

    public AllRegisterUserGET(Application application){
        this.application = application;
        Mauth = FirebaseAuth.getInstance();
        AllUserRegisterRef = FirebaseFirestore.getInstance().collection(DataManager.AllRegisteredUsers);
    }

    public LiveData<List<AllRegisterUserDataModel>> GetAllRegisterUser(String  StartDate, String EndDate){
        data = new MutableLiveData<>();
        var FirebaseUser = Mauth.getCurrentUser();
        if(FirebaseUser != null){
            AllUserRegisterRef.document(FirebaseUser.getUid())
                    .collection(StartDate+"-"+EndDate).addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if(error != null){
                                data.setValue(null);
                                return;
                            }else {
                                if(!value.isEmpty()){
                                    for(var ds : value.getDocumentChanges()){
                                        if(ds.getType() == DocumentChange.Type.ADDED || ds.getType() == DocumentChange.Type.MODIFIED || ds.getType() == DocumentChange.Type.REMOVED){
                                            data.setValue(value.toObjects(AllRegisterUserDataModel.class));
                                        }
                                    }
                                }else {
                                    data.setValue(null);
                                }
                            }
                        }
                    });
        }
        return data;
    }
}
