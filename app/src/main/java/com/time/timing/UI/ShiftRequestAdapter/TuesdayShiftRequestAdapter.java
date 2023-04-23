package com.time.timing.UI.ShiftRequestAdapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.time.timing.Data.DataManager;
import com.time.timing.TuesdaySchaduleModel;
import com.time.timing.databinding.ManagerequestitemBinding;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class TuesdayShiftRequestAdapter extends RecyclerView.Adapter<ShiftRequestVH> {

    @Getter @Setter
    private List<TuesdaySchaduleModel> list;
    private OnClick OnClick;

    @NonNull
    @Override
    public ShiftRequestVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        var l = LayoutInflater.from(parent.getContext());
        var v = ManagerequestitemBinding.inflate(l, parent, false);
        return new ShiftRequestVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ShiftRequestVH holder, int position) {
        holder.binding.RefuseShiftBtn.setOnClickListener(view -> {
            OnClick.Click(list.get(position), list.get(position).getUID(), list.get(position).getData(), list.get(position).getShiftName(), list.get(position).isRequestForAdditionalHour(),  DataManager.Refuse);
        });
        holder.binding.ConfirmShiftBtn.setOnClickListener(view -> {
            OnClick.Click(list.get(position), list.get(position).getUID(), list.get(position).getData(), list.get(position).getShiftName(), list.get(position).isRequestForAdditionalHour(),  DataManager.Confirm);
        });
    }

    @Override
    public int getItemCount() {
        if(list == null) {
            return 0;
        }return list.size();
    }

    public interface OnClick{
        void Click(TuesdaySchaduleModel tuesdaySchaduleModel, String UID, String Date, String Type, boolean RequestForAdditionalHour, String ShiftName);
    }
    public void OnCLickEvent(OnClick OnClick){
        this.OnClick = OnClick;
    }
}
