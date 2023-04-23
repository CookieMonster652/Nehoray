package com.time.timing.UI.ViewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.time.timing.databinding.AdditionalschedulehoursitemBinding;

public class AdditionhoursScheduleVH extends RecyclerView.ViewHolder {

    public AdditionalschedulehoursitemBinding binding;

    public AdditionhoursScheduleVH(@NonNull AdditionalschedulehoursitemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
