package com.time.timing.Network;

import android.app.Application;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.time.timing.Data.DataManager;
import com.time.timing.Model.AllRegisteredUsersModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class AllRegisteredUsersGET {

    private Application application;
    private MutableLiveData<List<AllRegisteredUsersModel>> data;
    private CollectionReference AllRegisteredUsersRef;
    private FirebaseAuth Mauth;

    public AllRegisteredUsersGET(Application application){
        this.application = application;
        Mauth = FirebaseAuth.getInstance();
        AllRegisteredUsersRef = FirebaseFirestore.getInstance().collection(DataManager.AllRegisteredUsers);
    }

    public LiveData<List<AllRegisteredUsersModel>> AllRegisteredUsersGET(String StartDate, String EndDate){
        data = new MutableLiveData<>();
        var FirebaseUser = Mauth.getCurrentUser();
        if(FirebaseUser != null){

            var q = AllRegisteredUsersRef.document(FirebaseUser.getUid()).collection(StartDate+"-"+EndDate)
                            .whereEqualTo(DataManager.ShiftCount, "0");

            q
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if(error != null){
                                data.setValue(null);
                                return;
                            }
                            if(!value.isEmpty()){
                                for(var ds : value.getDocumentChanges()){
                                    if(ds.getType() == DocumentChange.Type.ADDED || ds.getType() == DocumentChange.Type.MODIFIED || ds.getType() == DocumentChange.Type.REMOVED){
                                        data.setValue(value.toObjects(AllRegisteredUsersModel.class));
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
