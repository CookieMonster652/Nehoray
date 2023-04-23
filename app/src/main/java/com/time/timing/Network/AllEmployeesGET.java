package com.time.timing.Network;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.time.timing.Data.DataManager;
import com.time.timing.EmployeesModel;
import java.util.List;

public class AllEmployeesGET {

    private Application application;
    private MutableLiveData<List<EmployeesModel>> data;
    private CollectionReference MEmployeesRef;
    private FirebaseAuth Mauth;

    public AllEmployeesGET(Application application){
        this.application = application;
        MEmployeesRef = FirebaseFirestore.getInstance().collection(DataManager.Employees);
        Mauth = FirebaseAuth.getInstance();
    }

    public LiveData<List<EmployeesModel>> GetAllEmployees(){
        data = new MutableLiveData<>();
        FirebaseUser Muser = Mauth.getCurrentUser();
        if(Muser != null){
            MEmployeesRef.addSnapshotListener((value, error) -> {
                if(error != null){
                    data.setValue(null);
                    return;
                }
                if(!value.isEmpty()){
                    data.setValue(value.toObjects(EmployeesModel.class));
                }else {
                    data.setValue(null);
                }
            });
        }
        return data;
    }
}
