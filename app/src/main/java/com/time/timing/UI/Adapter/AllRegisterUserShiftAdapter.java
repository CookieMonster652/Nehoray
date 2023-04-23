package com.time.timing.UI.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.time.timing.Model.AllRegisterUserDataModel;
import com.time.timing.UI.ViewHolder.AllRegisterUserShiftVH;
import com.time.timing.databinding.SearchusershiftregisteritemBinding;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class AllRegisterUserShiftAdapter extends RecyclerView.Adapter<AllRegisterUserShiftVH> {
    @Setter @Getter
    private List<AllRegisterUserDataModel> list;
    private OnClick OnClick;

    @NonNull
    @Override
    public AllRegisterUserShiftVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var l = LayoutInflater.from(parent.getContext());
        var v = SearchusershiftregisteritemBinding.inflate(l, parent, false);
        return new AllRegisterUserShiftVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AllRegisterUserShiftVH holder, int position) {
        holder.binding.Name.setText(list.get(position).getName());
        holder.binding.RegisterShiftCounter.setText(list.get(position).getShiftCount());

        holder.itemView.setOnClickListener(view -> {
            OnClick.Click(list.get(position));
        });
    }

    @Override
    public int getItemCount() {
        if(list == null) {
            return 0;
        }return list.size();
    }

    public interface OnClick{
        void Click(AllRegisterUserDataModel allRegisterUserDataModel);
    }
    public void OnClickEvent(OnClick OnClick){
        this.OnClick = OnClick;
    }
}
