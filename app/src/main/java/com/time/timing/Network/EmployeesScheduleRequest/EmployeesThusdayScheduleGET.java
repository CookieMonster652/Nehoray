package com.time.timing.Network.EmployeesScheduleRequest;

import android.app.Application;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.time.timing.Data.DataManager;
import com.time.timing.UI.Employees.Model.EmployeesThusdayScheduleModel;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EmployeesThusdayScheduleGET {
    private Application application;
    private FirebaseAuth Mauth;
    private CollectionReference ScheduleRequestRef;
    private MutableLiveData<List<EmployeesThusdayScheduleModel>> data;
    private Executor executor;


    public EmployeesThusdayScheduleGET(Application application){
        this.application = application;
        Mauth = FirebaseAuth.getInstance();
        ScheduleRequestRef = FirebaseFirestore.getInstance().collection(DataManager.ScheduleRequest);
        executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<EmployeesThusdayScheduleModel>> GetRequestThuesdayEmployeesSchedule(String Number, String Date){
        var FirebaseUser = Mauth.getCurrentUser();
        data = new MutableLiveData<>();
        if(FirebaseUser != null){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    ScheduleRequestRef.document(Number).collection(Date).addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if(error != null){
                                return;
                            }
                            if(!value.isEmpty()){
                                for (var serverdata : value.getDocumentChanges()){
                                    if(serverdata.getType() == DocumentChange.Type.ADDED || serverdata.getType() == DocumentChange.Type.MODIFIED || serverdata.getType() == DocumentChange.Type.REMOVED)
                                        data.setValue(value.toObjects(EmployeesThusdayScheduleModel.class));
                                }
                            }else {
                                data.setValue(null);
                            }
                        }
                    });
                }
            });
        }
        return data;
    }

}
