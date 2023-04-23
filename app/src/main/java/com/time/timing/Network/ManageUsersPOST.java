package com.time.timing.Network;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.time.timing.Data.DataManager;
import com.time.timing.Utils.SharePref;
import com.time.timing.Utils.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class ManageUsersPOST {

    private Application application;
    private MutableLiveData<Boolean> data;
    private CollectionReference ManageUserRef, AllRegisteredUsersRef;
    private FirebaseAuth Mauth;
    private SharePref sharePref;

    public ManageUsersPOST(Application application) {
        this.application = application;
        ManageUserRef = FirebaseFirestore.getInstance().collection(DataManager.ManageUsers);
        AllRegisteredUsersRef = FirebaseFirestore.getInstance().collection(DataManager.AllRegisteredUsers);
        Mauth = FirebaseAuth.getInstance();
        sharePref = new SharePref(application);
    }

    public LiveData<Boolean> ManageUserShift(String SenderUID, String StartDate, String EndDate, String Phone, String Name, String ShiftCount, String MinimumShiftWorkers) {
        data = new MutableLiveData<>();

        var FirebaseUser = Mauth.getCurrentUser();
        if (FirebaseUser != null) {

            var Timestamp = System.currentTimeMillis();
            var map = new HashMap<String, Object>();
            map.put(DataManager.UID, SenderUID);
            map.put(DataManager.TotalShiftRegister, ShiftCount);
            map.put(DataManager.Users, 0);
            map.put(DataManager.StartDate, StartDate);
            map.put(DataManager.EndDate, EndDate);

            var usermap = new HashMap<String, Object>();
            usermap.put(DataManager.Name, Name);
            usermap.put(DataManager.UID, FirebaseUser.getUid());
            usermap.put(DataManager.Phone, Phone);
            usermap.put(DataManager.ShiftCount, ShiftCount);
            usermap.put(DataManager.StartDate, StartDate);
            usermap.put(DataManager.EndDate, EndDate);


            var allregisteredUsersmap = new HashMap<String, Object>();
            allregisteredUsersmap.put(DataManager.ShiftCount, ShiftCount);

            AllRegisteredUsersRef.document(SenderUID).collection(StartDate+"-"+EndDate).document(sharePref.GetData(DataManager.Phone))
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.getResult() != null) {
                                if (task.isSuccessful()) {
                                    var count = task.getResult().getString(DataManager.ShiftCount);
                                    if(count != null) {

                                        if (Integer.valueOf(count) < Integer.valueOf(MinimumShiftWorkers)) {

                                          //  Toast.SetToast(application, count + " " + MinimumShiftWorkers);

                                          //  Toast.SetToast(application, "update");

                                            AllRegisteredUsersRef.document(SenderUID).collection(StartDate + "-" + EndDate).document(sharePref.GetData(DataManager.Phone))
                                                    .update(allregisteredUsersmap);

                                            ManageUserRef.document(SenderUID).collection(StartDate + "-" + EndDate)
                                                    .document(ShiftCount).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                ManageUserRef.document(SenderUID).collection(StartDate + "-" + EndDate).document(ShiftCount)
                                                                        .collection(DataManager.Users).document(FirebaseUser.getUid()).set(usermap)
                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                if (task.isSuccessful()) {
                                                                                    //todo success add data----


                                                                                    ManageUserRef.document(SenderUID).collection(StartDate + "-" + EndDate).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                            if (task.isSuccessful()) {
                                                                                                for (var data : task.getResult().getDocuments()) {
                                                                                                    var shift = data.getString("TotalShiftRegister");
                                                                                                    if (shift.equals(ShiftCount)) {
                                                                                                    } else {
                                                                                                        ManageUserRef.document(SenderUID).collection(StartDate + "-" + EndDate).document(shift).collection(DataManager.Users)
                                                                                                                .document(FirebaseUser.getUid()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                    @Override
                                                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                                                        if (task.isSuccessful()) {
                                                                                                                            ManageUserRef.document(SenderUID).collection(StartDate + "-" + EndDate).document(shift).collection(DataManager.Users).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                                                                @Override
                                                                                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                                                                    if (task.isSuccessful()) {
                                                                                                                                        if (task.getResult().isEmpty()) {
                                                                                                                                            ManageUserRef.document(SenderUID).collection(StartDate + "-" + EndDate).document(shift).delete();
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            });
                                                                                                                        }
                                                                                                                    }
                                                                                                                });

                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    });


                                                                                }
                                                                            }
                                                                        });
                                                            } else {
                                                                Toast.SetToast(application, task.getException().getMessage());
                                                            }
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.SetToast(application, e.getMessage());
                                                        }
                                                    });
                                        } else {
                                         //   Toast.SetToast(application, "delete");
                                            AllRegisteredUsersRef.document(SenderUID).collection(StartDate + "-" + EndDate).document(sharePref.GetData(DataManager.Phone)).delete();

                                            ManageUserRef.document(SenderUID).collection(StartDate + "-" + EndDate)
                                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                            if (task.isSuccessful()) {
                                                                for (var data : task.getResult().getDocuments()) {
                                                                    var shift = data.getString(DataManager.TotalShiftRegister);
                                                                    ManageUserRef.document(SenderUID).collection(StartDate + "-" + EndDate)
                                                                            .document(shift).collection(DataManager.Users).document(FirebaseUser.getUid())
                                                                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                    if (task.isSuccessful()) {
                                                                                        ManageUserRef.document(SenderUID).collection(StartDate + "-" + EndDate)
                                                                                                .document(shift).collection(DataManager.Users).document(FirebaseUser.getUid()).delete()
                                                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                    @Override
                                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                                        if (task.isSuccessful()) {
                                                                                                            ManageUserRef.document(SenderUID).collection(StartDate + "-" + EndDate).document(shift).collection(DataManager.Users).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                                                @Override
                                                                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                                                    if (task.isSuccessful()) {
                                                                                                                        if (task.getResult().isEmpty()) {
                                                                                                                            ManageUserRef.document(SenderUID).collection(StartDate + "-" + EndDate).document(shift).delete();

                                                                                                                        }
                                                                                                                    }
                                                                                                                }
                                                                                                            });
                                                                                                        }
                                                                                                    }
                                                                                                });
                                                                                    }
                                                                                }
                                                                            });

                                                                }
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                } else {

                                }
                            }
                        }
                    });






        }
        return data;
    }
}
