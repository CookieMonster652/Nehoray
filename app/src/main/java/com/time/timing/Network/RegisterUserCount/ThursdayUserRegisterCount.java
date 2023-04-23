package com.time.timing.Network.RegisterUserCount;

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

public class ThursdayUserRegisterCount {

    private Application application;
    private MutableLiveData<Integer> data;
    private CollectionReference MyScheduleRef;
    private FirebaseAuth Mauth;
    private int count ;

    public ThursdayUserRegisterCount(Application application){
        this.application = application;
        MyScheduleRef = FirebaseFirestore.getInstance().collection(DataManager.MySchedule);
        Mauth = FirebaseAuth.getInstance();
    }

    public LiveData<Integer> GetMyScheduleThursdayUserCount(String UID, String Date){

        data = new MutableLiveData<>();
        var FirebaseUser = Mauth.getCurrentUser();
        if(FirebaseUser != null){

            MyScheduleRef.document(UID)
                    .collection(Date).addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if(error != null){
                                return;
                            }
                            if(!value.isEmpty()){
                                        for(var id : value.getDocuments()){
                                            var myid = id.getString(DataManager.ShifName);

                                            var q = MyScheduleRef.document(UID)
                                                    .collection(Date).document(myid).collection(DataManager.Users).whereEqualTo(DataManager.SenderUID, FirebaseUser.getUid()).whereEqualTo(DataManager.Type, DataManager.Confirm);

                                            q.addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                @Override
                                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                                    if(error != null){
                                                        return;
                                                    }
                                                    if(!value.isEmpty()){
                                                        count++;
                                                        data.setValue(count);
                                                    }
                                                }
                                            });

                                        }
                                    }
                        }
                    });
        }
        return data;
    }
}
