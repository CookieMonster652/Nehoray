package com.time.timing.UI.ViewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.time.timing.databinding.SearchusershiftregisteritemBinding;

public class AllRegisterUserShiftVH extends RecyclerView.ViewHolder {

    public SearchusershiftregisteritemBinding binding;

    public AllRegisterUserShiftVH(@NonNull SearchusershiftregisteritemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
