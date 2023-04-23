package com.time.timing.UI.PreviousWeekAdapter.PreviousWeekVH;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.time.timing.Data.DataManager;
import com.time.timing.UI.PreviousWeekAdapter.UserAdapter.PreviousSundayUserAdapter;
import com.time.timing.UI.PreviousWeekAdapter.UserWeekModel.SundayUserModel;
import com.time.timing.databinding.PreviousshiftitemBinding;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PreviousWeekSundayVH extends RecyclerView.ViewHolder {

    public PreviousshiftitemBinding binding;
    private FirebaseAuth Mauth;
    private CollectionReference MySchedule;
    private static final String TAG = "PreviousTuesdayViewHold";
    private PreviousSundayUserAdapter previousSundayUserAdapter;
    private Executor executor;


    public PreviousWeekSundayVH(@NonNull PreviousshiftitemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        previousSundayUserAdapter = new PreviousSundayUserAdapter();
        executor = Executors.newSingleThreadExecutor();
        binding.RecyclerView.setHasFixedSize(true);
        Mauth = FirebaseAuth.getInstance();
    }

    public void SetSundayUser(String Date, String ShiftName, String UID, Context context) {

        if (Date != null) {
            binding.RecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
            binding.RecyclerView.setAdapter(previousSundayUserAdapter);
            var FirebaseUser = Mauth.getCurrentUser();
            MySchedule = FirebaseFirestore.getInstance().collection(DataManager.MySchedule);

            if (FirebaseUser != null) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (FirebaseUser.getUid().equals(UID)) {
                            MySchedule.document(FirebaseUser.getUid()).collection(Date)
                                    .document(ShiftName).collection(DataManager.Users).addSnapshotListener((value, error) -> {
                                        if (error != null) {
                                            return;
                                        }
                                        if (!value.isEmpty()) {
                                            for (var data : value.getDocumentChanges()) {
                                                if (data.getType() == DocumentChange.Type.ADDED || data.getType() == DocumentChange.Type.MODIFIED || data.getType() == DocumentChange.Type.REMOVED) {
                                                    previousSundayUserAdapter.setList(value.toObjects(SundayUserModel.class));
                                                    previousSundayUserAdapter.notifyDataSetChanged();
                                                }
                                            }
                                        }
                                    });

                        } else {
                            MySchedule.document(UID).collection(Date)
                                    .document(ShiftName).collection(DataManager.Users).addSnapshotListener((value, error) -> {
                                        if(error != null){
                                            return;
                                        }
                                        if(!value.isEmpty()){
                                            for(var data : value.getDocumentChanges()){
                                                if(data.getType() == DocumentChange.Type.ADDED || data.getType() == DocumentChange.Type.MODIFIED || data.getType() == DocumentChange.Type.REMOVED){
                                                    previousSundayUserAdapter.setList(value.toObjects(SundayUserModel.class));
                                                    previousSundayUserAdapter.notifyDataSetChanged();
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
