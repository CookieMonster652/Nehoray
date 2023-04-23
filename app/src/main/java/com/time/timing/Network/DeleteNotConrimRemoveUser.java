package com.time.timing.Network;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.time.timing.Data.DataManager;
import com.time.timing.Utils.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class DeleteNotConrimRemoveUser {
    private Application application;
    private MutableLiveData<Boolean> data;
    private CollectionReference Refuse;
    private FirebaseAuth Mauth;

    public DeleteNotConrimRemoveUser(Application application){
        this.application = application;
        Refuse = FirebaseFirestore.getInstance().collection(DataManager.Refuse);
        Mauth = FirebaseAuth.getInstance();
    }

    public LiveData<Boolean> DeleteNotConfr(String SenderUID, String UID, String StartDate, String EndDate){
        data = new MutableLiveData<>();
        var FirebaseUser = Mauth.getCurrentUser();
        if(FirebaseUser != null){
            Refuse.document(UID).collection(StartDate+"-"+EndDate).document(SenderUID)
                    .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                data.setValue(true);
                            }else {
                                data.setValue(false);
                                Toast.SetToast(application, task.getException().getMessage());
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            data.setValue(false);
                            Toast.SetToast(application, e.getMessage());
                        }
                    });
        }
        return data;
    }
}
