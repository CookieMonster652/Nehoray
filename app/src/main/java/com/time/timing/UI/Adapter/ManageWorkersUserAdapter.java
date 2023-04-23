package com.time.timing.UI.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.time.timing.MyScheduleContact;
import com.time.timing.UI.ViewHolder.ManageworkerUserViewHolder;
import com.time.timing.databinding.ManageworkersitemBinding;

import java.util.List;

import lombok.Setter;

public class ManageWorkersUserAdapter extends RecyclerView.Adapter<ManageworkerUserViewHolder> {

    @Setter
    private List<MyScheduleContact> list;
    private OnClick OnClick;

    @NonNull
    @Override
    public ManageworkerUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var layoutInflater = LayoutInflater.from(parent.getContext());
        var manageworkersitemBinding = ManageworkersitemBinding.inflate(layoutInflater, parent, false);
        return new ManageworkerUserViewHolder(manageworkersitemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ManageworkerUserViewHolder holder, int position) {
        holder.binding.setUser(list.get(position));

        holder.binding.DeleteButton.setOnClickListener(view -> {
            OnClick.Click(list.get(position).getPhoneNumber());
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
        void Click(String PhoneNumber);
    }
    public void OnClickEvent(OnClick OnClick){
        this.OnClick = OnClick;
    }
}
