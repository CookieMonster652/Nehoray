package com.time.timing.UI.Employees.ViewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.time.timing.databinding.ShiftemployeesitemBinding;

public class ShiftRequestViewHolder extends RecyclerView.ViewHolder {

    public ShiftemployeesitemBinding binding;

    public ShiftRequestViewHolder(@NonNull ShiftemployeesitemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
