package com.time.timing.Network.ManagerRequest;

import android.app.Application;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.time.timing.Data.DataManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class IsScheduleUserExistsGET {

    private Application application;
    private MutableLiveData<Boolean> data;
    private FirebaseAuth Mauth;
    private CollectionReference MyRequestRef;

    public IsScheduleUserExistsGET(Application application){
        this.application = application;
        Mauth = FirebaseAuth.getInstance();
        MyRequestRef = FirebaseFirestore.getInstance().collection(DataManager.MySchedule);
    }

    public LiveData<Boolean> IsScheduleUserExists(String SenderID, String Date, String ShiftName){
        data = new MutableLiveData<>();
        var FirebaseUser = Mauth.getCurrentUser();
        if(FirebaseUser != null){
            MyRequestRef.document(FirebaseUser.getUid())
                    .collection(Date).document(ShiftName).collection(DataManager.Users)
                    .document(SenderID)
                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            if(error != null){
                                data.setValue(false);
                                return;
                            }
                            if(value.exists()){
                                data.setValue(true);
                            }else {
                                data.setValue(false);
                            }
                        }
                    });
        }
        return data;
    }
}
