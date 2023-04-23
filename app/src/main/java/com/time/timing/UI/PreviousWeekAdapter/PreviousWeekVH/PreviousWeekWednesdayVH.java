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
import com.time.timing.UI.PreviousWeekAdapter.UserAdapter.PreviousWednesdayUserAdapter;
import com.time.timing.UI.PreviousWeekAdapter.UserWeekModel.WednesdayUserModel;
import com.time.timing.databinding.PreviousshiftitemBinding;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PreviousWeekWednesdayVH extends RecyclerView.ViewHolder {

    public PreviousshiftitemBinding binding;
    private FirebaseAuth Mauth;
    private CollectionReference MySchedule;
    private static final String TAG = "PreviousTuesdayViewHold";
    private PreviousWednesdayUserAdapter previousWednesdayUserAdapter;
    private Executor executor;

    public PreviousWeekWednesdayVH(@NonNull PreviousshiftitemBinding binding) {
        super(binding.getRoot());
        executor = Executors.newSingleThreadExecutor();
        this.binding = binding;
        previousWednesdayUserAdapter = new PreviousWednesdayUserAdapter();
        binding.RecyclerView.setHasFixedSize(true);
    }

    public void SetWednesdaydayUser(String Date, String ShiftName, String UID, Context context) {

        if (Date != null) {
            binding.RecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
            binding.RecyclerView.setAdapter(previousWednesdayUserAdapter);
            Mauth = FirebaseAuth.getInstance();
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
                                                    previousWednesdayUserAdapter.setList(value.toObjects(WednesdayUserModel.class));
                                                    previousWednesdayUserAdapter.notifyDataSetChanged();
                                                }
                                            }
                                        }
                                    });
                        } else {
                            MySchedule.document(UID).collection(Date)
                                    .document(ShiftName).collection(DataManager.Users).addSnapshotListener((value, error) -> {
                                        if (error != null) {
                                            return;
                                        }
                                        if (!value.isEmpty()) {
                                            for (var data : value.getDocumentChanges()) {
                                                if (data.getType() == DocumentChange.Type.ADDED || data.getType() == DocumentChange.Type.MODIFIED || data.getType() == DocumentChange.Type.REMOVED) {
                                                    previousWednesdayUserAdapter.setList(value.toObjects(WednesdayUserModel.class));
                                                    previousWednesdayUserAdapter.notifyDataSetChanged();
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
