package com.time.timing.UI.ManagerSignIn;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Build;
import android.os.Bundle;

import com.time.timing.DateModel;
import com.time.timing.R;
import com.time.timing.UI.Adapter.NextWeekAdapter;
import com.time.timing.Utils.HandleActivity;
import com.time.timing.databinding.EditweekBinding;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class EditWeek extends AppCompatActivity {

    private EditweekBinding binding;
    private NextWeekAdapter adapter;
    private List<DateModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.editweek);

        SetNextWeek();
    }


    private void SetNextWeek() {
        binding.RecyclerViewDate.setHasFixedSize(true);
        adapter = new NextWeekAdapter();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            for(int i = 0; i< getDaysOfWeek().length; i++){
                String mydate = getDaysOfWeek()[i];
                DateModel dateModel = new DateModel();
                dateModel.setDate(String.valueOf(mydate));
                list.add(dateModel);
                adapter.setList(list);
                binding.RecyclerViewDate.setAdapter(adapter);

                adapter.OnClickEvent(SelectDate -> {
                    HandleActivity.GotoSelectShift(EditWeek.this, SelectDate);
                });
            }

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String[] getDaysOfWeek() {
        String[] days = new String[6];
        DateTimeFormatter dayOfMonthFormatter = DateTimeFormatter.ofPattern("EEEE dd-MM");
        LocalDate today = LocalDate.now(ZoneId.of("America/Montreal"));
        // go back to Sunday, then forward 1 day to get Monday
        LocalDate day = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
                .plusDays(7);
        days[0] = day.format(dayOfMonthFormatter);
        for (int i = 1; i < 6; i++) {
            day = day.plusDays(1);
            days[i] = day.format(dayOfMonthFormatter);
        }
        return days;
    }
}