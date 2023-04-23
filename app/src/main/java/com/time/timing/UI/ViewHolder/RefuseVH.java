package com.time.timing.UI.ViewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.time.timing.databinding.RefuseuseritemBinding;

public class RefuseVH extends RecyclerView.ViewHolder {

    public RefuseuseritemBinding binding;

    public RefuseVH(@NonNull RefuseuseritemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
