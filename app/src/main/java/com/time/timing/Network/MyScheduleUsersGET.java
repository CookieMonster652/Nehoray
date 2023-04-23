package com.time.timing.Network;
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
import com.time.timing.UserModel;
import java.util.List;


public class MyScheduleUsersGET {

    private Application application;
    private MutableLiveData<List<UserModel>> data;
    private CollectionReference MyScheduleRef;
    private FirebaseAuth Mauth;

    public MyScheduleUsersGET(Application application){
        this.application = application;
        Mauth = FirebaseAuth.getInstance();
        MyScheduleRef = FirebaseFirestore.getInstance().collection(DataManager.MySchedule);
    }

    public LiveData<List<UserModel>> GetMySchedule(String Date, String ShiftName){
        var FirebaseUser = Mauth.getCurrentUser();
        data = new MutableLiveData<>();

        if(FirebaseUser != null){
            var q = MyScheduleRef.document(FirebaseUser.getUid()).collection(Date).document(ShiftName).collection(DataManager.Users)
                            .whereEqualTo(DataManager.AdditionalHoursYouWant, false);
            q.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(error != null){
                        data.setValue(null);
                        return;
                    }

                    if(!value.isEmpty()){
                        for(var ds : value.getDocumentChanges()){
                            if(ds.getType() == DocumentChange.Type.ADDED || ds.getType() == DocumentChange.Type.MODIFIED || ds.getType() == DocumentChange.Type.REMOVED){
                                data.setValue(value.toObjects(UserModel.class));
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
