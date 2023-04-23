package com.time.timing.UI.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.time.timing.CurrentWeekModel;
import com.time.timing.R;
import com.time.timing.UI.ViewHolder.RegistationViewHolder;
import com.time.timing.Utils.DateToDayName;
import com.time.timing.databinding.RegistationweekiteamBinding;

import java.util.List;

import lombok.Setter;

public class RegistationWeekAdapter extends RecyclerView.Adapter<RegistationViewHolder> {

    @Setter
    private List<CurrentWeekModel> list;
    private int Row_Index = -1;
    private OnDateSelect OnDateSelect;

    @NonNull
    @Override
    public RegistationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var layoutInflater = LayoutInflater.from(parent.getContext());
        var view = RegistationweekiteamBinding.inflate(layoutInflater, parent, false);
        return new RegistationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RegistationViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(position == 0){
            holder.binding.Date.setText(holder.itemView.getContext().getResources().getString(R.string.Today));
        }else {
           holder.binding.Date.setText(DateToDayName.GetDate(list.get(position).getDate()));
        }

        holder.itemView.setOnClickListener(view -> {
            Row_Index = position;
            OnDateSelect.Select(list.get(position).getDate());
            notifyDataSetChanged();
        });
        if(Row_Index == position){
            holder.binding.ShapeView.setBackgroundResource(R.color.carbon_black_6);
        }else {
            holder.binding.ShapeView.setBackgroundResource(R.color.white);
        }
    }

    @Override
    public int getItemCount() {
        if(list == null){
            return 0;
        }return list.size();
    }

    public interface OnDateSelect{
        void Select(String Date);
    }
    public void OnSelectEvent(OnDateSelect OnDateSelect){
        this.OnDateSelect = OnDateSelect;
    }
}
