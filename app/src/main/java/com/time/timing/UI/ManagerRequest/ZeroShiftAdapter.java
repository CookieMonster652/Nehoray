package com.time.timing.UI.ManagerRequest;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.time.timing.Model.AllRegisteredUsersModel;
import com.time.timing.databinding.RegistationuseritemBinding;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class ZeroShiftAdapter extends RecyclerView.Adapter<ShiftUserRegisterVH> {

    @Getter @Setter
    private List<AllRegisteredUsersModel> list;
    private OnClick OnClick;


    @NonNull
    @Override
    public ShiftUserRegisterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var l = LayoutInflater.from(parent.getContext());
        var v = RegistationuseritemBinding.inflate(l, parent, false);
        return new ShiftUserRegisterVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ShiftUserRegisterVH holder, int position) {
        holder.binding.Name.setText(list.get(position).getName());

        holder.itemView.setOnClickListener(view -> {
            OnClick.Lisiner(list.get(position).getName(), list.get(position).getPhone(), list.get(position).getUID());
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
        void Lisiner(String Name, String PhoneNumber, String UID);
    }
    public void OnClickLisiner(OnClick OnClick){
        this.OnClick = OnClick;
    }
}
