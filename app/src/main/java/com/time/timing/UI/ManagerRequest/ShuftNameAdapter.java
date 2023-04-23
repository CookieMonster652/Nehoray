package com.time.timing.UI.ManagerRequest;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.time.timing.Data.DataManager;
import com.time.timing.UserModel;
import com.time.timing.databinding.RegistationuseritemBinding;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class ShuftNameAdapter extends RecyclerView.Adapter<ShiftUserRegisterVH> {

    @Getter @Setter
    private List<UserModel> list;
    public static final String ReceiverMessage = "ReceiverMessage";


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
          var intent = new Intent(ReceiverMessage);
          intent.putExtra(DataManager.Name, list.get(position).getName());
          intent.putExtra(DataManager.UID, list.get(position).getUID());
          intent.putExtra(DataManager.Phone, list.get(position).getPhone());
          LocalBroadcastManager.getInstance(holder.itemView.getContext()).sendBroadcast(intent);
      });
    }

    @Override
    public int getItemCount() {
        if(list == null) {
            return 0;
        }return list.size();
    }

}
