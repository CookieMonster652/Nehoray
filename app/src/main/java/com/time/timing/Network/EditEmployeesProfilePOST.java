package com.time.timing.Network;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.time.timing.Data.DataManager;
import com.time.timing.Utils.Toast;

import java.util.HashMap;

public class EditEmployeesProfilePOST {

    private Application application;
    private MutableLiveData<Boolean> data;
    private CollectionReference MEmployeesRef;
    private FirebaseAuth Mauth;

    public EditEmployeesProfilePOST(Application application){
        this.application = application;
        MEmployeesRef = FirebaseFirestore.getInstance().collection(DataManager.Employees);
        Mauth = FirebaseAuth.getInstance();
    }

    public LiveData<Boolean> EditEmployeesProfileData(String Name, String Phone){
        data = new MutableLiveData<>();
        var FirebaseUser = Mauth.getCurrentUser();
        if(FirebaseUser != null){
            var map = new HashMap<String, Object>();
            map.put(DataManager.Name, Name);
            map.put(DataManager.Phone, Phone);

            MEmployeesRef.document(FirebaseUser.getUid()).update(map).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    data.setValue(true);
                    Toast.SetToast(application, "Success");
                }else {
                    data.setValue(false);
                    Toast.SetToast(application, task.getException().getMessage());
                }
            }).addOnFailureListener(e -> {
               data.setValue(false);
                Toast.SetToast(application, e.getMessage());
            });
        }
        return data;
    }
}
