package com.time.timing.UI.Manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.time.timing.CurrentWeekModel;
import com.time.timing.Data.DataManager;
import com.time.timing.DateModel;
import com.time.timing.R;
import com.time.timing.UI.Adapter.RegistationWeekAdapter;
import com.time.timing.Utils.GetWeeksName;
import com.time.timing.Utils.HandleActivity;
import com.time.timing.Utils.SharePref;
import com.time.timing.Utils.Toast;
import com.time.timing.databinding.RegistrationendtimeBinding;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RegistrationEndTime extends AppCompatActivity {

    private RegistrationendtimeBinding binding;
    private static final String TAG = "RegistrationStartTime";
    private List<DateModel> dateModelList = new ArrayList<>();
    private List<CurrentWeekModel> list = new ArrayList<>();
    private String SelectDate = null;
    private SharePref sharePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.registrationendtime);
        sharePref = new SharePref(RegistrationEndTime.this);

        InitView();
        GetCurrentWeekData();
        PickTime();
    }

    private void PickTime(){
        binding.ShiftEndTime.setOnClickListener(view -> {
            var timePickerDialog = new TimePickerDialog(
                    RegistrationEndTime.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    (timePicker, i, i1) -> {
                        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
                        calendar.set(0, 0, 0, i, i1);

                        binding.Time.setText(DateFormat.format(DataManager.TimeFormat, calendar));
                    }, 24, 0, true
            );
            timePickerDialog.show();
            timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        });
    }




    private void GetCurrentWeekData() {

        var timeMillis = System.currentTimeMillis();
        var calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(timeMillis);
        var CurrentDate = DateFormat.format("yyyy-MM-dd", calendar).toString();


        var someDay =  org.joda.time.LocalDate.parse(CurrentDate, org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd"));
        var dayBefore = someDay.minusDays(1);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            for (int i = 0; i < GetWeeksName.getCurrentDayOfWeek().length; i++) {
                String mydate = GetWeeksName.getCurrentDayOfWeek()[i];
                DateModel dateModel = new DateModel();
                dateModel.setDate(String.valueOf(mydate));
                dateModelList.add(dateModel);
            }

            var size = dateModelList.size();
            var enddate = dateModelList.get(size - 1).getDate();

            StringBuilder builder = new StringBuilder();
            LocalDate startDate = LocalDate.parse(dayBefore.toString());
            LocalDate endDate = LocalDate.parse(enddate);
            LocalDate d = startDate;

            while (d.isBefore(endDate) || d.equals(endDate)) {
                builder.append(d.format(DateTimeFormatter.ISO_DATE)).append(" ");
                d = d.plusDays(1);
                var date = d.format(DateTimeFormatter.ISO_DATE);

                var item = new CurrentWeekModel();
                item.setDate(date);
                list.add(item);
                var adapter = new RegistationWeekAdapter();
                adapter.setList(list);
                binding.RecyclerViewEndTime.setHasFixedSize(true);
                binding.RecyclerViewEndTime.setAdapter(adapter);
                adapter.notifyDataSetChanged();


                adapter.OnSelectEvent(Date -> {
                     SelectDate = Date;
                });
                binding.Apply.setOnClickListener(view -> {
                    var Time = binding.Time.getText().toString().trim();
                    if(SelectDate == null){
                        Toast.SetToast(RegistrationEndTime.this, getResources().getString(R.string.Select_date_require));
                    }else if(Time == ""){
                        Toast.SetToast(RegistrationEndTime.this, getResources().getString(R.string.Select_time_require));
                    }else {
                        sharePref.SetData(DataManager.RegistrationEndTime, Time);
                        sharePref.SetData(DataManager.RegistrationEndDate, SelectDate);
                        HandleActivity.BackGotoEmployeesSchaduleRegistrationEndDate(RegistrationEndTime.this);
                        finish();
                    }
                });
            }
        }
    }



    private void InitView(){
        binding.Toolbar.Title.setText(getResources().getString(R.string.Registration_End_Date));
        binding.Toolbar.BackButton.setOnClickListener(view -> {
            finish();
            Animatoo.animateSlideRight(RegistrationEndTime.this);
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        Animatoo.animateSlideRight(RegistrationEndTime.this);
    }
}