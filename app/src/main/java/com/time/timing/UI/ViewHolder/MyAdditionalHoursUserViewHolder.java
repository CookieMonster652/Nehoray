package com.time.timing.UI.ViewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.time.timing.databinding.AdditionalhourscheduleuseritemBinding;

public class MyAdditionalHoursUserViewHolder extends RecyclerView.ViewHolder {

    public AdditionalhourscheduleuseritemBinding binding;

    public MyAdditionalHoursUserViewHolder(@NonNull AdditionalhourscheduleuseritemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
