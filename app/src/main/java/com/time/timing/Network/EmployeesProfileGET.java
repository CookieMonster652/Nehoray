package com.time.timing.Network;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.time.timing.Data.DataManager;
import com.time.timing.EmployeesModel;

public class EmployeesProfileGET {

    private Application application;
    private MutableLiveData<EmployeesModel> data;
    private CollectionReference MEmployeesRef;
    private FirebaseAuth Mauth;

    public EmployeesProfileGET(Application application){
        this.application = application;
        MEmployeesRef = FirebaseFirestore.getInstance().collection(DataManager.Employees);
        Mauth = FirebaseAuth.getInstance();
    }

    public LiveData<EmployeesModel> GetEmployeesProfileData(){
        data = new MutableLiveData<>();

        var FirebaseUser = Mauth.getCurrentUser();
        if(FirebaseUser != null){
            MEmployeesRef.document(FirebaseUser.getUid()).addSnapshotListener((value, error) -> {
               if(error != null){
                   data.setValue(null);
                   return;
               }
               if(value.exists()){
                   data.setValue(value.toObject(EmployeesModel.class));
               }else {
                   data.setValue(null);
               }
            });
        }
        return data;
    }

}
