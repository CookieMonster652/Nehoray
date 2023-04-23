package com.time.timing.UI.ShiftRequestAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.time.timing.databinding.ManagerequestitemBinding;

public class ShiftRequestVH extends RecyclerView.ViewHolder {

    public ManagerequestitemBinding binding;

    public ShiftRequestVH(@NonNull ManagerequestitemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
