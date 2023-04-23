package com.time.timing.Network.EmployeesScheduleRequest;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.time.timing.Data.DataManager;
import com.time.timing.UI.Employees.Model.EmployeesSaturdayScheduleModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EmployeesSaturdayScheduleGET {

    private Application application;
    private MutableLiveData<List<EmployeesSaturdayScheduleModel>> data;
    private CollectionReference ScheduleRequestRef;
    private FirebaseAuth Mauth;
    private Executor executor;

    public EmployeesSaturdayScheduleGET(Application application){
        this.application = application;
        ScheduleRequestRef = FirebaseFirestore.getInstance().collection(DataManager.ScheduleRequest);
        Mauth = FirebaseAuth.getInstance();
        executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<EmployeesSaturdayScheduleModel>> GetRequestSaturdayEmployeesSchedule(String Number, String Date){
        data = new MutableLiveData<>();
        var FirebaseUser = Mauth.getCurrentUser();
        if(FirebaseUser != null){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    ScheduleRequestRef.document(Number).collection(Date).addSnapshotListener((value, error) -> {
                        if(error != null){
                            data.setValue(null);
                            return;
                        }
                        if(!value.isEmpty()){
                            for(var mydata : value.getDocumentChanges()){
                                if(mydata.getType() == DocumentChange.Type.ADDED || mydata.getType() == DocumentChange.Type.MODIFIED || mydata.getType() == DocumentChange.Type.REMOVED){
                                    data.setValue(value.toObjects(EmployeesSaturdayScheduleModel.class));
                                }
                            }
                        }else{
                            data.setValue(null);
                        }
                    });
                }
            });
        }
        return data;
    }
}
