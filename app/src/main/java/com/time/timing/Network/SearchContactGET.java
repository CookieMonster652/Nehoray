package com.time.timing.Network;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.time.timing.Data.DataManager;
import com.time.timing.SearchContactModel;

import java.util.List;

public class SearchContactGET {

    private Application application;
    private MutableLiveData<List<SearchContactModel>> data;
    private CollectionReference MEmployeesRef;
    private FirebaseAuth MAuth;

    public SearchContactGET(Application application){
        this.application = application;
        MEmployeesRef = FirebaseFirestore.getInstance().collection(DataManager.Employees);
        MAuth = FirebaseAuth.getInstance();
    }

    public LiveData<List<SearchContactModel>> SearchingUsers(String PhoneNumber){
        data = new MutableLiveData<>();
        var user = MAuth.getCurrentUser();
        if(user != null){
            var query = MEmployeesRef.whereEqualTo(DataManager.Phone, PhoneNumber);
            query.addSnapshotListener((value, error) -> {
                if(error != null){
                    data.setValue(null);
                    return;
                }
                if(!value.isEmpty()){
                    for(var action : value.getDocumentChanges()){
                        if(action.getType() == DocumentChange.Type.ADDED || action.getType() == DocumentChange.Type.MODIFIED || action.getType() == DocumentChange.Type.REMOVED){
                            data.setValue(value.toObjects(SearchContactModel.class));
                        }
                    }
                }else {
                    data.setValue(null);
                }
            });
        }
        return data;
    }
}
