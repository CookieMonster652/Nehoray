package com.time.timing.UI.ViewHolder;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.time.timing.R;
import com.time.timing.databinding.ShifttimeitemBinding;

public class ShiftTimeViewHolder extends RecyclerView.ViewHolder {

    public ShifttimeitemBinding binding;

    public ShiftTimeViewHolder(@NonNull ShifttimeitemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
