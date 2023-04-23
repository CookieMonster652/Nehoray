package com.time.timing.UI.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.time.timing.RefuseModel;
import com.time.timing.UI.ViewHolder.RefuseVH;
import com.time.timing.databinding.RefuseuseritemBinding;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class RefuseUserAdapter extends RecyclerView.Adapter<RefuseVH> {
    @Setter @Getter
    private List<RefuseModel> list;

    @NonNull
    @Override
    public RefuseVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var l = LayoutInflater.from(parent.getContext());
        var v = RefuseuseritemBinding.inflate(l, parent, false);
        return new RefuseVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RefuseVH holder, int position) {
        holder.binding.setRefuse(list.get(position));
    }

    @Override
    public int getItemCount() {
        if(list == null){
        return 0;
        } return list.size();
    }
}
