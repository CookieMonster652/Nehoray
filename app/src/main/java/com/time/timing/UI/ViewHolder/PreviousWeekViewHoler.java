package com.time.timing.UI.ViewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.time.timing.databinding.PreviousshiftitemBinding;

public class PreviousWeekViewHoler extends RecyclerView.ViewHolder {

    public PreviousshiftitemBinding binding;

    public PreviousWeekViewHoler(@NonNull PreviousshiftitemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
