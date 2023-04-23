package com.time.timing.Network;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.time.timing.Data.DataManager;
import com.time.timing.MyScheduleModel;
import com.time.timing.Utils.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class ScheduleRequestPOST {

    private Application application;
    private MutableLiveData<Boolean> data;
    private CollectionReference RequestForShift, EmployeesRef;
    private FirebaseAuth Mauth;

    public ScheduleRequestPOST(Application application){
        this.application = application;
        RequestForShift = FirebaseFirestore.getInstance().collection(DataManager.RequestForShift);
        Mauth = FirebaseAuth.getInstance();
        EmployeesRef = FirebaseFirestore.getInstance().collection(DataManager.Employees);
    }

    public LiveData<Boolean> SendScheduleRequest(MyScheduleModel myScheduleModel, String Name, String Phone, String UID, boolean AdditionHours){
        data = new MutableLiveData<>();
        var FirebaseUser = Mauth.getCurrentUser();
        if(FirebaseUser != null){

            var q = EmployeesRef.whereEqualTo(DataManager.Phone, Phone).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){

                        for(var ds : task.getResult().getDocuments()){
                            var map = new HashMap<String, Object>();
                            map.put(DataManager.AdditionalHoursYouWant, myScheduleModel.getAdditional_hours_you_want());
                            map.put(DataManager.Data, myScheduleModel.getData());
                            map.put(DataManager.MinimalShiftParWorkers, myScheduleModel.getMinimal_shift_par_workers());
                            map.put(DataManager.NumberOFWorkersForThisShift, myScheduleModel.getNumber_of_workers_for_this_shift());
                            map.put(DataManager.RegistrationEndDate, myScheduleModel.getRegistrationEndDate());
                            map.put(DataManager.RegistrationEndTime, myScheduleModel.getRegistrationEndTime());
                            map.put(DataManager.RegistrationStartDate, myScheduleModel.getRegistrationStartDate());
                            map.put(DataManager.RegistrationStartTime, myScheduleModel.getRegistrationStartTime());
                            map.put(DataManager.ShifName, myScheduleModel.getShiftName());
                            map.put(DataManager.ShiftEndTime, myScheduleModel.getShift_end_time());
                            map.put(DataManager.ShiftStartTime, myScheduleModel.getShift_start_time());
                            map.put(DataManager.UID, myScheduleModel.getUID());
                            map.put(DataManager.Type, DataManager.Request);
                            map.put(DataManager.RequestForAdditionalHour, AdditionHours);
                            map.put(DataManager.SenderUID, ds.getString(DataManager.UID));

                            RequestForShift.document(Phone).collection(myScheduleModel.getData()).document(myScheduleModel.getShiftName())
                                    .set(map)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                data.setValue(true);
                                            }else {
                                                data.setValue(false);
                                                Toast.SetToast(application, task.getException().getMessage());
                                            }
                                        }
                                    }).addOnFailureListener(e -> {
                                        data.setValue(false);
                                        Toast.SetToast(application, e.getMessage());
                                    });
                        }


                    }else {
                        data.setValue(false);
                    }
                }
            });


        }
        return data;
    }
}
