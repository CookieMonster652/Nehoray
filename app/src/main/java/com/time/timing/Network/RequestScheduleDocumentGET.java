package com.time.timing.Network;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;

public class RequestScheduleDocumentGET {

    private Application application;
    private FirebaseAuth Mauth;
    private DocumentReference ScheduleRequest;
    private MutableLiveData<String> data;


    public RequestScheduleDocumentGET(Application application) {
        this.application = application;
        Mauth = FirebaseAuth.getInstance();
    }

    public LiveData<String> GetRequestScheduleDocument() {
        var FirebaseUser = Mauth.getCurrentUser();
        data = new MutableLiveData<>();

        if (FirebaseUser != null) {

        /*    ScheduleRequest.collection(DataManager.ScheduleRequest).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        var ds = task.getResult().getDocuments();
                        if (!ds.isEmpty()) {
                            for (var documet : task.getResult().getDocuments()) {
                                Toast.SetToast(application, documet.getId());
                            }
                        }
                    }
                }
            });*/

        }
        return data;
    }

}
