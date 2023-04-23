package com.time.timing.UI.Adapter;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.time.timing.Data.DataManager;
import com.time.timing.ShiftModel;
import com.time.timing.UI.ViewHolder.AddShiftViewHolder;
import com.time.timing.databinding.ShiftitemBinding;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import lombok.Getter;
import lombok.Setter;

public class AddShiftAdapter extends RecyclerView.Adapter<AddShiftViewHolder> {

    private LayoutInflater layoutInflater;
    @Getter
    @Setter
    private List<ShiftModel> list;
    private OnClickInfo OnClickInfo;
    private int var;

    @NonNull
    @Override
    public AddShiftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        var binding = ShiftitemBinding.inflate(layoutInflater, parent, false);
        return new AddShiftViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AddShiftViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String val = getOrdinalFor(list.get(position).getShift());
        holder.binding.ShiftTitle.setText(val);

        holder.binding.InfoIcon.setOnClickListener(view -> {
            OnClickInfo.Click();
        });
        holder.binding.ShiftStartTime.setOnClickListener(view -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    holder.itemView.getContext(),
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    (timePicker, i, i1) -> {
                        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
                        calendar.set(0, 0, 0, i, i1);

                        holder.binding.ShiftStartDate.setText(DateFormat.format(DataManager.TimeFormat, calendar));
                        list.get(position).setShiftStartTime((String) DateFormat.format(DataManager.TimeFormat, calendar));
                    }, 24, 0, true
            );
            timePickerDialog.show();
            timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        });

        holder.binding.ShiftEndTime.setOnClickListener(view -> {
            var timePickerDialog = new TimePickerDialog(
                    holder.itemView.getContext(),
                    (timePicker, i, i1) -> {
                        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
                        calendar.set(0, 0, 0, i, i1);

                        holder.binding.ShiftEndTimeText.setText(DateFormat.format(DataManager.TimeFormat, calendar));
                        list.get(position).setShiftEndTime((String) DateFormat.format(DataManager.TimeFormat, calendar));
                    }, 24, 0, true
            );
            timePickerDialog.show();
        });

        holder.binding.NumberOFWorkersShift.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String val = editable.toString();
                if (!val.isEmpty()) {
                    list.get(position).setNumberOFWorkerOnTheShift(val);
                }
            }
        });

        holder.binding.AdditionalHours.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String value = editable.toString();
                if (!value.isEmpty()) {
                    list.get(position).setAdditionalHoursAdd(value);
                }
            }
        });

        holder.binding.MinimalShiftWorkers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String value = editable.toString();
                if (!value.isEmpty()) {
                    list.get(position).setMinimumShiftParWorker(value);
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

    public static String getOrdinalFor(int value) {
        int tenRemainder = value % 10;
        switch (tenRemainder) {
            case 1:
                return value + "st";
            case 2:
                return value + "nd";
            case 3:
                return value + "rd";
            default:
                return value + "th";
        }
    }

    public interface OnClickInfo {
        void Click();
    }

    public void OnClickInfoEvent(OnClickInfo OnClickInfo) {
        this.OnClickInfo = OnClickInfo;
    }
}
