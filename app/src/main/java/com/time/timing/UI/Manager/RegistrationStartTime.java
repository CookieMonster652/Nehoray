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
import com.time.timing.databinding.RegistrationstarttimeBinding;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RegistrationStartTime extends AppCompatActivity {

    private RegistrationstarttimeBinding binding;
    private static final String TAG = "RegistrationStartTime";
    private List<DateModel> dateModelList = new ArrayList<>();
    private List<CurrentWeekModel> list = new ArrayList<>();
    private RegistationWeekAdapter adapter;
    private String SelectDate = null;
    private SharePref sharePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.registrationstarttime);
        adapter = new RegistationWeekAdapter();
        sharePref = new SharePref(RegistrationStartTime.this);

        InitView();
        GetCurrentWeek();
        PickTime();
    }

    private void PickTime(){
        binding.ShiftStartTime.setOnClickListener(view -> {
            var timePickerDialog = new TimePickerDialog(
                    RegistrationStartTime.this,
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

    private void InitView() {
        binding.Toolbar.Title.setText(getResources().getString(R.string.Registration_Start_Date));
        binding.Toolbar.BackButton.setOnClickListener(view -> {
            finish();
            Animatoo.animateSlideRight(RegistrationStartTime.this);
        });

        adapter.OnSelectEvent(Date -> {
            SelectDate = Date;
        });
        binding.Apply.setOnClickListener(view -> {
            var time = binding.Time.getText().toString().trim();
            if(SelectDate == null){
                Toast.SetToast(RegistrationStartTime.this, getResources().getString(R.string.Select_date_require));
            }else if(time.isEmpty()){
                Toast.SetToast(RegistrationStartTime.this, getResources().getString(R.string.Select_time_require));
            }else {
                sharePref.SetData(DataManager.RegistrationStartTime, time);
                sharePref.SetData(DataManager.RegistrationStartDate, SelectDate);
                HandleActivity.BackGotoEmployeesSchaduleRegistrationStartDate(RegistrationStartTime.this);
                finish();
            }
        });
    }


    @Override
    public void onBackPressed() {
        finish();
        Animatoo.animateSlideRight(RegistrationStartTime.this);
    }


    private void GetCurrentWeek() {

        var timeMillis = System.currentTimeMillis();
        var calendar = Calendar.getInstance();
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

                adapter.setList(list);
                binding.RecyclerViewStartTime.setHasFixedSize(true);
                binding.RecyclerViewStartTime.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }


}