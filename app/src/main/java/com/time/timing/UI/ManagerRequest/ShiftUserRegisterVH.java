package com.time.timing.UI.ManagerRequest;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.time.timing.databinding.RegistationuseritemBinding;

public class ShiftUserRegisterVH extends RecyclerView.ViewHolder {

    public RegistationuseritemBinding binding;

    public ShiftUserRegisterVH(@NonNull RegistationuseritemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
