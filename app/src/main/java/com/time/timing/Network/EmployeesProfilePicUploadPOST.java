package com.time.timing.Network;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.time.timing.Data.DataManager;
import com.time.timing.Utils.Toast;

public class EmployeesProfilePicUploadPOST {

    private Application application;
    private MutableLiveData<String> data;
    private StorageReference MEmployeesRef;
    private FirebaseAuth Mauth;

    public EmployeesProfilePicUploadPOST(Application application){
        this.application = application;
        MEmployeesRef = FirebaseStorage.getInstance().getReference().child(DataManager.Employees);
        Mauth = FirebaseAuth.getInstance();
    }

    public LiveData<String> EmployeesProfilePicUpload(Uri ImageUri){
        data = new MutableLiveData<>();
        FirebaseUser Muser = Mauth.getCurrentUser();
        if(Muser != null){
            StorageReference ImageRef = MEmployeesRef.child(ImageUri.getLastPathSegment()+Muser.getUid());
            ImageRef.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    if(taskSnapshot.getMetadata() != null){
                        if(taskSnapshot.getMetadata().getReference() != null){
                            Task<Uri> ResultUri = taskSnapshot.getStorage().getDownloadUrl();
                            ResultUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    data.setValue(uri.toString());
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    data.setValue(null);
                                    Toast.SetToast(application, e.getMessage());
                                }
                            });
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    data.setValue(null);
                    Toast.SetToast(application, e.getMessage());
                }
            });
        }
        return data;
    }
}
