package com.time.timing.UI.ManagerRequest;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.time.timing.Data.ManageUserShiftModel;
import com.time.timing.R;
import com.time.timing.databinding.ShiftnameBinding;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class ShiftNameAdapter extends RecyclerView.Adapter<ShiftNameVH> {

    @Getter @Setter
    private List<ManageUserShiftModel> list;

    @NonNull
    @Override
    public ShiftNameVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var l = LayoutInflater.from(parent.getContext());
        var view = ShiftnameBinding.inflate(l, parent, false);
        return new ShiftNameVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShiftNameVH holder, int position) {
        holder.binding.ShiftName.setText(list.get(position).getTotalShiftRegister()+" "+holder.itemView.getContext().getResources().getString(R.string.Shift));
        holder.SetUserList(holder.itemView.getContext(), list.get(position).getTotalShiftRegister(), list.get(position).getStartDate(), list.get(position).getEndDate());


    }

    @Override
    public int getItemCount() {
        if(list == null){
            return 0;
        }return list.size();
    }
}
