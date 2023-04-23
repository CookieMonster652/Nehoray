package com.time.timing.UI.Employees.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.time.timing.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.time.timing.Data.DataManager;
import com.time.timing.UI.Employees.Model.EmployeesTuesdayScheduleModel;
import com.time.timing.UI.Employees.ViewHolder.ShiftRequestViewHolder;
import com.time.timing.Utils.ShiftName;
import com.time.timing.databinding.ShiftemployeesitemBinding;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class
EmployeesTuesdayScheduleAdapter extends RecyclerView.Adapter<ShiftRequestViewHolder> {
    @Setter
    @Getter
    private List<EmployeesTuesdayScheduleModel> list;
    private List<String> counterlist = new ArrayList<>();
    private OnCount OnCount;
    private CollectionReference MyScheduleRef;
    private static final String TAG = "EmployeesTuesdaySchedul";

    @NonNull
    @Override
    public ShiftRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var layoutInflater = LayoutInflater.from(parent.getContext());
        var view = ShiftemployeesitemBinding.inflate(layoutInflater, parent, false);
        return new ShiftRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShiftRequestViewHolder holder, @SuppressLint("RecyclerView") int position) {
        MyScheduleRef = FirebaseFirestore.getInstance().collection(DataManager.MySchedule);
        holder.binding.Time.setText(list.get(position).getShift_start_time() + "-" + list.get(position).getShift_end_time());
        var shift = Integer.parseInt(list.get(position).getShiftName());
        holder.binding.ShiftName.setText(ShiftName.getOrdinalFor(shift) + " "+holder.itemView.getContext().getResources().getString(R.string.Shift));

        setdata(list.get(position), list, holder);

        MyScheduleRef.document(list.get(position).getUID()).collection(list.get(position).getData()).document(list.get(position).getShiftName()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }
                if (value.exists()) {

                    var RegistationUsers = value.getString(DataManager.RegistrationUsers);
                    var TotalRegistationUser = list.get(position).getNumber_of_workers_for_this_shift();
                    holder.binding.Counter.setText(holder.itemView.getContext().getResources().getString(R.string.This_shift_has)+" "+RegistationUsers+" "+holder.itemView.getContext().getResources().getString(R.string.employees_out_of)+" "+list.get(position).getNumber_of_workers_for_this_shift()+" "+holder.itemView.getContext().getResources().getString(R.string.who_are_needed));

                    holder.binding.ProgressID.setMax(Integer.valueOf(TotalRegistationUser));
                    holder.binding.ProgressID.setProgress(Integer.valueOf(RegistationUsers));
                    var ratio = Integer.valueOf(TotalRegistationUser)-Integer.valueOf(RegistationUsers);
                    double res = (Double.valueOf(ratio) / Double.valueOf(TotalRegistationUser));
                    double result = res*100;
                    int x = (int) (result - 100);
                    holder.binding.UserRegistrationPersent.setText(String.valueOf(Math.abs(x))+"%");
                }
            }
        });
    }

    public void setdata(EmployeesTuesdayScheduleModel model, List<EmployeesTuesdayScheduleModel> list, ShiftRequestViewHolder holder) {
        holder.itemView.setOnClickListener(view -> {
            counterlist.clear();
            model.setIsEnable(!model.isIsEnable());
            if (model.isIsEnable()) {
                holder.binding.CheckBox.setChecked(true);
            } else {
                holder.binding.CheckBox.setChecked(false);
            }

            for (var item : list) {
                if (item.isIsEnable()) {
                    counterlist.add("");
                    OnCount.Count(counterlist.size());
                } else {
                    OnCount.Count(counterlist.size());
                }
            }

        });
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }


    public interface OnCount {
        void Count(int Size);
    }

    public void OnItemLisiner(OnCount OnCount) {
        this.OnCount = OnCount;
    }

}
