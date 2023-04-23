package com.time.timing.Network.UserScheduleRequest;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.time.timing.Data.DataManager;
import com.time.timing.WednesdaySchaduleModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class WednesdayScheduleRequestGET {

    private Application application;
    private MutableLiveData<List<WednesdaySchaduleModel>> data;
    private FirebaseAuth Mauth;
    private CollectionReference RequestForShiftRef;

    public WednesdayScheduleRequestGET(Application application) {
        this.application = application;
        Mauth = FirebaseAuth.getInstance();
        RequestForShiftRef = FirebaseFirestore.getInstance().collection(DataManager.RequestForShift);
    }

    public LiveData<List<WednesdaySchaduleModel>> GetWednesdayRequest(String PhoneNumber, String Date) {
        data = new MutableLiveData<>();
        var FirebaseUser = Mauth.getCurrentUser();
        if (FirebaseUser != null) {
            var q = RequestForShiftRef.document(PhoneNumber).collection(Date).whereEqualTo(DataManager.Type, DataManager.Request);
            q.addSnapshotListener((value, error) -> {
                if (error != null) {
                    data.setValue(null);
                    return;
                }
                if (!value.isEmpty()) {
                    for (var mydata : value.getDocumentChanges()) {
                        if (mydata.getType() == DocumentChange.Type.ADDED || mydata.getType() == DocumentChange.Type.MODIFIED || mydata.getType() == DocumentChange.Type.REMOVED) {
                            data.setValue(value.toObjects(WednesdaySchaduleModel.class));
                        }
                    }
                } else {
                    data.setValue(null);
                }
            });
        }
        return data;
    }
}
