package com.time.timing.UI.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.time.timing.ContactModel;
import com.time.timing.UI.ViewHolder.ContactViewHolder;
import com.time.timing.databinding.MycontactitemBinding;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder> {

    @Setter @Getter
    private List<ContactModel> list;
    private static final String TAG = "ContactAdapter";
    private List<String> counterlist = new ArrayList<>();
    private OnCount OnCount;

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var layoutInflater = LayoutInflater.from(parent.getContext());
        var contactitemBinding = MycontactitemBinding.inflate(layoutInflater, parent, false);
        return new ContactViewHolder(contactitemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.binding.setContact(list.get(position));
        setdata(list.get(position), list, holder);
    }

    public void setdata(ContactModel model, List<ContactModel> list, ContactViewHolder holder) {
        holder.binding.setContact(model);
        holder.itemView.setOnClickListener(view -> {
            counterlist.clear();
            model.setIsCheck(!model.isIsCheck());
            if (model.isIsCheck()) {
                holder.binding.RadioButton.setChecked(true);
            } else {
                holder.binding.RadioButton.setChecked(false);
            }

            for (var item : list) {
                if (item.isIsCheck()) {
                    counterlist.add(item.getName());
                    OnCount.Counter(counterlist.size());
                } else {
                    Log.d(TAG, String.valueOf(list.size()));
                    OnCount.Counter(counterlist.size());
                }
            }

        });
    }


    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public interface OnCount {
        void Counter(int size);
    }

    public void OnCounterEvent(OnCount OnCount) {
        this.OnCount = OnCount;
    }
}
