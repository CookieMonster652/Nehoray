package com.time.timing.UI.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.time.timing.Model.AdditionHoursTimeModel;
import com.time.timing.UI.ViewHolder.AdditionhoursScheduleVH;
import com.time.timing.databinding.AdditionalschedulehoursitemBinding;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class ScheduleAdditionHoursAdapter extends RecyclerView.Adapter<AdditionhoursScheduleVH> {
    @Getter @Setter
    private List<AdditionHoursTimeModel> list;


    @NonNull
    @Override
    public AdditionhoursScheduleVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var l = LayoutInflater.from(parent.getContext());
        var v = AdditionalschedulehoursitemBinding.inflate(l, parent, false);
        return new AdditionhoursScheduleVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdditionhoursScheduleVH holder, int position) {
        holder.binding.AdditionHoursTime.setText(list.get(position).getTimeHour());
    }

    @Override
    public int getItemCount() {
        if(list == null) {
            return 0;
        }return list.size();
    }
}
