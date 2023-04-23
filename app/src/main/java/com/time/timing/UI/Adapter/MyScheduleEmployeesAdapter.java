package com.time.timing.UI.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.time.timing.Data.DataManager;
import com.time.timing.R;
import com.time.timing.UI.ViewHolder.MyScheduleUserViewHolder;
import com.time.timing.UserModel;
import com.time.timing.databinding.MyscheduleuseritemBinding;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class MyScheduleEmployeesAdapter extends RecyclerView.Adapter<MyScheduleUserViewHolder> {

    @Setter @Getter
    private List<UserModel> list;
    private Onclick Onclick;

    @NonNull
    @Override
    public MyScheduleUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var layoutInflater = LayoutInflater.from(parent.getContext());
        var view = MyscheduleuseritemBinding.inflate(layoutInflater, parent, false);
        return new MyScheduleUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyScheduleUserViewHolder holder, int position) {
        holder.binding.setUser(list.get(position));
        if(list.get(position).getType() != null) {
            var type = list.get(position).getType();
            if (type.equals(DataManager.Refuse)) {
                holder.binding.BackGround.setBackgroundResource(R.drawable.refusebf);
            }else {
                holder.binding.BackGround.setBackgroundResource(R.drawable.stokebg);

                holder.itemView.setOnClickListener(view -> {
                    Onclick.Click(list.get(position));
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        if(list == null){
            return 0;
        }
        return list.size();
    }

    public interface Onclick{
        void Click(UserModel userModel);
    }
    public void OnClickLisiner(Onclick Onclick){
        this.Onclick = Onclick;
    }

}
