package com.time.timing.UI.ViewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.time.timing.DateModel;
import com.time.timing.databinding.CurrentweekiteamBinding;

public class CurrentWeekHolder extends RecyclerView.ViewHolder {

    public CurrentweekiteamBinding binding;

    public CurrentWeekHolder(@NonNull CurrentweekiteamBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void setdata(DateModel model) {
        binding.setDate(model);
        itemView.setOnClickListener(view -> {
            model.setIsCheck(!model.isIsCheck());
            if(model.isIsCheck()){
                binding.RadioButton.setChecked(true);
            }else {
                binding.RadioButton.setChecked(false);
            }
        });
    }
}
