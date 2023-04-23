package com.time.timing.UI.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.time.timing.Data.DataManager;
import com.time.timing.Model.RefuseUserModel;
import com.time.timing.R;
import com.time.timing.UI.ViewHolder.MyAdditionalHoursUserViewHolder;
import com.time.timing.databinding.AdditionalhourscheduleuseritemBinding;
import com.time.timing.databinding.MyscheduleuseritemBinding;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class AdditionalHoursScheduleUserAdapter extends RecyclerView.Adapter<MyAdditionalHoursUserViewHolder> {
    @Setter @Getter
    private List<RefuseUserModel> list;

    @NonNull
    @Override
    public MyAdditionalHoursUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var l = LayoutInflater.from(parent.getContext());
        var v = AdditionalhourscheduleuseritemBinding.inflate(l, parent, false);
        return new MyAdditionalHoursUserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdditionalHoursUserViewHolder holder, int position) {
        holder.binding.User.setText(list.get(position).getName());
        if(list.get(position).getType().equals(DataManager.Refuse)){
            holder.binding.BackGround.setBackgroundResource(R.drawable.refusebf);
        }else {
            holder.binding.BackGround.setBackgroundResource(R.drawable.stokebg);
        }
    }

    @Override
    public int getItemCount() {
        if(list == null) {
            return 0;
        }
        return list.size();
    }
}
