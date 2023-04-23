package com.time.timing.UI.ViewHolder;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.time.timing.databinding.ShiftitemBinding;

public class AddShiftViewHolder extends RecyclerView.ViewHolder {

    public ShiftitemBinding binding;

    public AddShiftViewHolder(@NonNull ShiftitemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
