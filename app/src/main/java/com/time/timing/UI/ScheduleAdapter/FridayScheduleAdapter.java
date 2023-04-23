package com.time.timing.UI.ScheduleAdapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.time.timing.FridaySchaduleModel;
import com.time.timing.R;
import com.time.timing.UI.ViewHolder.ShiftTimeViewHolder;
import com.time.timing.Utils.Shifts;
import com.time.timing.databinding.ShifttimeitemBinding;

import java.util.List;

import lombok.Setter;

public class FridayScheduleAdapter extends RecyclerView.Adapter<ShiftTimeViewHolder> {

    @Setter
    private List<FridaySchaduleModel> list;

    @NonNull
    @Override
    public ShiftTimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var layoutInflater = LayoutInflater.from(parent.getContext());
        var view = ShifttimeitemBinding.inflate(layoutInflater, parent, false);
        return new ShiftTimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShiftTimeViewHolder holder, int position) {
        holder.binding.Time.setText(list.get(position).getShift_start_time() +" - "+list.get(position).getShift_end_time());
        holder.binding.ShiftName.setText(Shifts.getOrdinalFor(Integer.parseInt(list.get(position).getShiftName()))+" "+holder.itemView.getContext().getResources().getString(R.string.Shift));
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }
}


