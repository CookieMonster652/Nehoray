package com.time.timing.UI.PreviousWeekAdapter.WeekAdapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.time.timing.TuesdaySchaduleModel;
import com.time.timing.UI.PreviousWeekAdapter.PreviousWeekVH.PreviousWeekTuesdayVH;
import com.time.timing.Utils.ShiftName;
import com.time.timing.databinding.PreviousshiftitemBinding;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class PreviousTuesdayAdapter extends RecyclerView.Adapter<PreviousWeekTuesdayVH> {

    @Getter
    @Setter
    private List<TuesdaySchaduleModel> list;

    @NonNull
    @Override
    public PreviousWeekTuesdayVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var l = LayoutInflater.from(parent.getContext());
        var view = PreviousshiftitemBinding.inflate(l, parent, false);
        return new PreviousWeekTuesdayVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviousWeekTuesdayVH holder, int position) {
        holder.binding.ShiftTime.setText(list.get(position).getShift_start_time()+"-"+list.get(position).getShift_end_time());
        holder.SetTuesdayUser(list.get(position).getData(), list.get(position).getShiftName(), holder.itemView.getContext());
        var shift = Integer.parseInt(list.get(position).getShiftName());
        holder.binding.ShiftName.setText(ShiftName.getOrdinalFor(shift)+" Shift");
    }

    @Override
    public int getItemCount() {
        if(list == null) {
            return 0;
        }return list.size();
    }
}
