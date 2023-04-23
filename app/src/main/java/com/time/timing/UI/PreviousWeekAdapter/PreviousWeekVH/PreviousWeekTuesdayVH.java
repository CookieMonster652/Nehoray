package com.time.timing.UI.PreviousWeekAdapter.PreviousWeekVH;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.time.timing.Data.DataManager;
import com.time.timing.UI.PreviousWeekAdapter.UserAdapter.PreviousTuesdayUserAdapter;
import com.time.timing.UI.PreviousWeekAdapter.UserWeekModel.TuesdayUserModel;
import com.time.timing.databinding.PreviousshiftitemBinding;

public class PreviousWeekTuesdayVH extends RecyclerView.ViewHolder {

    public PreviousshiftitemBinding binding;
    private FirebaseAuth Mauth;
    private CollectionReference MySchedule;
    private static final String TAG = "PreviousTuesdayViewHold";
    private PreviousTuesdayUserAdapter previousTuesdayUserAdapter;

    public PreviousWeekTuesdayVH(@NonNull PreviousshiftitemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        previousTuesdayUserAdapter = new PreviousTuesdayUserAdapter();
        binding.RecyclerView.setHasFixedSize(true);
    }

    public void SetTuesdayUser(String Date, String ShiftName, Context context) {

        if (Date != null) {
            binding.RecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
            binding.RecyclerView.setAdapter(previousTuesdayUserAdapter);
            Mauth = FirebaseAuth.getInstance();
            var FirebaseUser = Mauth.getCurrentUser();
            MySchedule = FirebaseFirestore.getInstance().collection(DataManager.MySchedule);
            if (FirebaseUser != null) {
                MySchedule.document(FirebaseUser.getUid()).collection(Date)
                        .document(ShiftName).collection(DataManager.Users).get().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                previousTuesdayUserAdapter.setList(task.getResult().toObjects(TuesdayUserModel.class));
                                previousTuesdayUserAdapter.notifyDataSetChanged();
                            }
                        });
            }
        }
    }

}
