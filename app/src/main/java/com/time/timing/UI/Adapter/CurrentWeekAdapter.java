package com.time.timing.UI.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.time.timing.DateModel;
import com.time.timing.UI.ViewHolder.CurrentWeekHolder;
import com.time.timing.Utils.DateToDayName;
import com.time.timing.databinding.CurrentweekiteamBinding;
import com.time.timing.databinding.NextweakiteamBinding;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class CurrentWeekAdapter extends RecyclerView.Adapter<CurrentWeekHolder> {

    public LayoutInflater layoutInflater;
    private @Setter @Getter
    List<DateModel> list;
    private OnClick OnClick;

    @NonNull
    @Override
    public CurrentWeekHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        var binding = CurrentweekiteamBinding.inflate(layoutInflater, parent, false);
        return new CurrentWeekHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentWeekHolder holder, int position) {
        DateModel dateModel = list.get(position);

        holder.binding.setDate(list.get(position));
        holder.binding.WeekName.setText(DateToDayName.GetDate(list.get(position).getDate()));

        holder.setdata(dateModel);
    }

    @Override
    public int getItemCount() {
        if(list == null){
            return 0;
        }
        return list.size();
    }

    public interface OnClick{
        void Click(String SelectDate);
    }
    public void OnClickEvent(OnClick OnClick){
        this.OnClick = OnClick;
    }
}
