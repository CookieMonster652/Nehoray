package com.time.timing.UI.ViewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.time.timing.databinding.MyscheduleuseritemBinding;

public class MyScheduleUserViewHolder extends RecyclerView.ViewHolder {

    public MyscheduleuseritemBinding binding;

    public MyScheduleUserViewHolder(@NonNull MyscheduleuseritemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
