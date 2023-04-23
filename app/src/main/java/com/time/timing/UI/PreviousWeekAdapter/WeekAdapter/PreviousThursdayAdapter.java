package com.time.timing.UI.PreviousWeekAdapter.WeekAdapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.time.timing.R;
import com.time.timing.ThursdaySchaduleModel;
import com.time.timing.UI.PreviousWeekAdapter.PreviousWeekVH.PreviousWeekThursdayVH;
import com.time.timing.Utils.ShiftName;
import com.time.timing.databinding.PreviousshiftitemBinding;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class PreviousThursdayAdapter extends RecyclerView.Adapter<PreviousWeekThursdayVH> {
    @Setter @Getter
    private List<ThursdaySchaduleModel> list;

    @NonNull
    @Override
    public PreviousWeekThursdayVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var l = LayoutInflater.from(parent.getContext());
        var view = PreviousshiftitemBinding.inflate(l, parent, false);
        return new PreviousWeekThursdayVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviousWeekThursdayVH holder, int position) {
        holder.binding.ShiftTime.setText(list.get(position).getShift_start_time()+"-"+list.get(position).getShift_end_time());
        holder.SetThurdayUser(list.get(position).getData(), list.get(position).getShiftName(), list.get(position).getUID(), holder.itemView.getContext());
        var shift = Integer.parseInt(list.get(position).getShiftName());
        holder.binding.ShiftName.setText(ShiftName.getOrdinalFor(shift)+" "+holder.itemView.getContext().getResources().getString(R.string.Shift));
        holder.binding.ShiftManagerName.setText(holder.itemView.getContext().getResources().getString(R.string.Shift_Manager)+": "+list.get(position).getShiftManager());
    }

    @Override
    public int getItemCount() {
        if(list == null) {
            return 0;
        }return list.size();
    }
}
