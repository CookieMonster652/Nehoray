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

public class SundayUserRegisterCount {

    private Application application;
    private MutableLiveData<Integer> data;
    private CollectionReference MyScheduleRef;
    private FirebaseAuth Mauth;
    private int count;

    public SundayUserRegisterCount(Application application) {
        this.application = application;
        MyScheduleRef = FirebaseFirestore.getInstance().collection(DataManager.MySchedule);
        Mauth = FirebaseAuth.getInstance();
    }

    public LiveData<Integer> GetMyScheduleSundayUserCount(String UID, String Date) {

        data = new MutableLiveData<>();
        var FirebaseUser = Mauth.getCurrentUser();
        if (FirebaseUser != null) {


            var f = FirebaseFirestore.getInstance().collectionGroup(DataManager.Users).whereEqualTo(DataManager.SenderUID, FirebaseUser.getUid()).whereEqualTo(DataManager.Type, DataManager.Confirm);
            f.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error != null) {
                        return;
                    }
                    if (!value.isEmpty()) {
                        data.setValue(value.size());
                    }
                }
            });


/*            MyScheduleRef.document(UID)
                    .collection(Date).addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error != null) {
                                return;
                            }
                            if (!value.isEmpty()) {
                                for(var ds : value.getDocumentChanges()){
                                    if(ds.getType() == DocumentChange.Type.ADDED || ds.getType() == DocumentChange.Type.REMOVED || ds.getType() == DocumentChange.Type.MODIFIED){
                                        for (var id : value.getDocuments()) {
                                            var myid = id.getString(DataManager.ShifName);

                                            var q = MyScheduleRef.document(UID)
                                                    .collection(Date).document(myid).collection(DataManager.Users).whereEqualTo(DataManager.SenderUID, FirebaseUser.getUid()).whereEqualTo(DataManager.Type, DataManager.Confirm);

                                            q.addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                @Override
                                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                                    if (error != null) {
                                                        return;
                                                    }
                                                    if (!value.isEmpty()) {
                                                        count++;
                                                        data.setValue(count);
                                                    }
                                                }
                                            });

                                        }
                                    }
                                }

                            }


                        }

                    });*/
           /* q.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(error != null){
                        return;
                    }
                    if(!value.isEmpty()){
                        data.setValue(value.size());
                        for(var ds : value.getDocumentChanges()){
                            //Toast.SetToast(application, ds.getDocument().getString(DataManager.Type));
                        }
                    }else {
                        Toast.SetToast(application, "error");
                    }
                }
            });
*/
        }
        return data;
    }
}
