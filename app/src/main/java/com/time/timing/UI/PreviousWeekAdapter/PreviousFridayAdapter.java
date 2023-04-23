package com.time.timing.UI.PreviousWeekAdapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.time.timing.FridaySchaduleModel;
import com.time.timing.R;
import com.time.timing.UI.PreviousWeekAdapter.PreviousWeekVH.PreviousWeekFridayVH;
import com.time.timing.Utils.ShiftName;
import com.time.timing.databinding.PreviousshiftitemBinding;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class PreviousFridayAdapter extends RecyclerView.Adapter<PreviousWeekFridayVH> {

    @Getter @Setter
    private List<FridaySchaduleModel> data;

    @NonNull
    @Override
    public PreviousWeekFridayVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var l = LayoutInflater.from(parent.getContext());
        var view = PreviousshiftitemBinding.inflate(l, parent, false);
        return new PreviousWeekFridayVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviousWeekFridayVH holder, int position) {
        holder.binding.ShiftTime.setText(data.get(position).getShift_start_time()+"-"+ data.get(position).getShift_end_time());
        holder.SetFridayUser(data.get(position).getData(), data.get(position).getShiftName(), data.get(position).getUID(),  holder.itemView.getContext());
        var shift = Integer.parseInt(data.get(position).getShiftName());
        holder.binding.ShiftName.setText(ShiftName.getOrdinalFor(shift)+" "+holder.itemView.getContext().getResources().getString(R.string.Shift));
        holder.binding.ShiftManagerName.setText(holder.itemView.getContext().getResources().getString(R.string.Shift_Manager)+": "+data.get(position).getShiftManager());
    }

    @Override
    public int getItemCount() {
        if(data == null) {
            return 0;
        } return data.size();
    }
}
