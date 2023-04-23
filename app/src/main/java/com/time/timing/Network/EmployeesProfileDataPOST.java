package com.time.timing.Network;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.time.timing.Data.DataManager;
import com.time.timing.Utils.SharePref;
import com.time.timing.Utils.Toast;

import java.util.HashMap;

public class EmployeesProfileDataPOST {

    private Application application;
    private MutableLiveData<Boolean> data;
    private CollectionReference MUserRef;
    private FirebaseAuth Mauth;
    private SharePref sharePref;

    public EmployeesProfileDataPOST(Application application) {
        this.application = application;
        Mauth = FirebaseAuth.getInstance();
        MUserRef = FirebaseFirestore.getInstance().collection(DataManager.Employees);
        sharePref = new SharePref(application);
    }

    public LiveData<Boolean> SetUpEmployeesProfileData(String ProfileImageUri, String Name) {
        data = new MutableLiveData<>();
        FirebaseUser Muser = Mauth.getCurrentUser();

        if (Muser != null) {


            long Timestamp = System.currentTimeMillis();
            var map = new HashMap<String, Object>();
            map.put(DataManager.Name, Name);
            map.put(DataManager.UerProfileImageDownloadUri, ProfileImageUri);
            map.put(DataManager.Timestamp, Timestamp);
          //  map.put(DataManager.Token, s);
            map.put(DataManager.UID, Muser.getUid());
            map.put(DataManager.Phone, sharePref.GetData(DataManager.Phone));

            MUserRef.document(Muser.getUid()).set(map).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    data.setValue(true);
                }else {
                    data.setValue(false);
                    Toast.SetToast(application, task.getException().getMessage());
                }
            }).addOnFailureListener(e -> {
                data.setValue(false);
                Toast.SetToast(application, e.getMessage());
            });

            FirebaseMessaging.getInstance().getToken().addOnSuccessListener(s -> {

            }).addOnFailureListener(e -> {
                data.setValue(false);
                Toast.SetToast(application, e.getMessage());
            });
        }
        return data;
    }

}
