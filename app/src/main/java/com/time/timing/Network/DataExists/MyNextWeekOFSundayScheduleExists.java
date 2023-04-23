package com.time.timing.Network.DataExists;

import android.app.Application;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.time.timing.Data.DataManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class MyNextWeekOFSundayScheduleExists {

    private Application application;
    private MutableLiveData<Boolean> data;
    private FirebaseAuth Mauth;
    private CollectionReference MyScheduleRef;

    public MyNextWeekOFSundayScheduleExists(Application application){
        this.application = application;
        Mauth = FirebaseAuth.getInstance();
        MyScheduleRef = FirebaseFirestore.getInstance().collection(DataManager.MySchedule);
    }

    public LiveData<Boolean> MyNextDayOFSundayScheduleExists(String Date){
        data = new MutableLiveData<>();
        var FirebaseUser = Mauth.getCurrentUser();
        if(FirebaseUser != null){
            MyScheduleRef.document(FirebaseUser.getUid()).collection(Date)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if(error != null){
                                data.setValue(false);
                                return;
                            }
                            if(!value.isEmpty()){
                                data.setValue(false);
                            }else {
                                data.setValue(true);
                            }
                        }
                    });
        }
        return data;
    }
}
