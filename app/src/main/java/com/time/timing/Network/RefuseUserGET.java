package com.time.timing.Network;

import android.app.Application;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.time.timing.Data.DataManager;
import com.time.timing.RefuseModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class RefuseUserGET {

    private Application application;
    private MutableLiveData<List<RefuseModel>> data;
    private CollectionReference RefuseRef;
    private FirebaseAuth Mauth;

    public RefuseUserGET(Application application){
        this.application = application;
        RefuseRef = FirebaseFirestore.getInstance().collection(DataManager.Refuse);
        Mauth = FirebaseAuth.getInstance();
    }

    public LiveData<List<RefuseModel>> GetUserRefuse(String StartDate, String EndDate){
        data = new MutableLiveData<>();
        var FirebaseUser = Mauth.getCurrentUser();
        if(FirebaseUser != null){
            RefuseRef.document(FirebaseUser.getUid()).collection(StartDate+"-"+EndDate)
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
                                        data.setValue(value.toObjects(RefuseModel.class));
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
