package com.time.timing.UI.ViewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.time.timing.databinding.MycontactitemBinding;

public class ContactViewHolder extends RecyclerView.ViewHolder {

    public MycontactitemBinding binding;
    private static final String TAG = "ContactViewHolder";

    public ContactViewHolder(@NonNull MycontactitemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}
