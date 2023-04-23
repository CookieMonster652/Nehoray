package com.time.timing.Network;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.time.timing.Data.DataManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class MyUserRegShiftPOST {

    private Application application;
    private MutableLiveData<Boolean> data;
    private FirebaseAuth Mauth;
    private CollectionReference MyRegisterUser;
    private CollectionReference EmployeesRef;

    public MyUserRegShiftPOST(Application application) {
        this.application = application;
        Mauth = FirebaseAuth.getInstance();
        MyRegisterUser = FirebaseFirestore.getInstance().collection(DataManager.AllRegisteredUsers);
        EmployeesRef = FirebaseFirestore.getInstance().collection(DataManager.Employees);
    }

    public LiveData<Boolean> MyUserRegShift(String StartDate, String EndDate, String Number, String Name, String UID) {
        data = new MutableLiveData<>();
        var FirebaseUser = Mauth.getCurrentUser();
        if (FirebaseUser != null) {


            EmployeesRef.whereEqualTo(DataManager.Phone, Number).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (var document : task.getResult()) {
                        var map = new HashMap<String, Object>();
                        map.put(DataManager.Name, Name);
                        map.put(DataManager.Phone, Number);
                        map.put(DataManager.ShiftCount, "0");
                        map.put(DataManager.UID, document.getString(DataManager.UID));

                        MyRegisterUser.document(FirebaseUser.getUid())
                                .collection(StartDate + "-" + EndDate)
                                .document(Number).set(map).addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        data.setValue(true);
                                    } else {
                                        data.setValue(false);
                                    }
                                }).addOnFailureListener(e -> data.setValue(false));
                    }
                }
            });


        }
        return data;
    }
}
