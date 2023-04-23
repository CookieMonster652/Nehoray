package com.time.timing.UI.ViewHolder;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.time.timing.databinding.ManageworkersitemBinding;
import com.time.timing.databinding.RegistationuseritemBinding;

public class ManageworkerUserViewHolder extends RecyclerView.ViewHolder {

    public ManageworkersitemBinding binding;

    public ManageworkerUserViewHolder(@NonNull ManageworkersitemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
