package com.time.timing.UI.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.time.timing.DateModel;
import com.time.timing.UI.ViewHolder.NextWeekViewHolder;
import com.time.timing.Utils.DateToDayName;
import com.time.timing.databinding.NextweakiteamBinding;

import java.util.List;

import lombok.Setter;

public class NextWeekAdapter extends RecyclerView.Adapter<NextWeekViewHolder> {

    public LayoutInflater layoutInflater;
    private @Setter  List<DateModel> list;
    private OnClick OnClick;

    @NonNull
    @Override
    public NextWeekViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        NextweakiteamBinding binding = NextweakiteamBinding.inflate(layoutInflater, parent, false);
        return new NextWeekViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NextWeekViewHolder holder, int position) {
        holder.binding.DayNameText.setText(DateToDayName.GetDate(list.get(position).getDate()));

        holder.itemView.setOnClickListener(view -> {
            OnClick.Click(list.get(position).getDate());
        });
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
