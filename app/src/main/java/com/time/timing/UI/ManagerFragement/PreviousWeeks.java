package com.time.timing.UI.ManagerFragement;

import android.os.Build;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.time.timing.DateModel;
import com.time.timing.Network.ViewModel.ViewModel;
import com.time.timing.R;
import com.time.timing.UI.PreviousWeekAdapter.PreviousFridayAdapter;
import com.time.timing.UI.PreviousWeekAdapter.PreviousTuesdayAdapter;
import com.time.timing.UI.PreviousWeekAdapter.WeekAdapter.PreviousMondayAdapter;
import com.time.timing.UI.PreviousWeekAdapter.WeekAdapter.PreviousSaturdayAdapter;
import com.time.timing.UI.PreviousWeekAdapter.WeekAdapter.PreviousSundayAdapter;
import com.time.timing.UI.PreviousWeekAdapter.WeekAdapter.PreviousThursdayAdapter;
import com.time.timing.UI.PreviousWeekAdapter.WeekAdapter.PreviousWednesdayAdapter;
import com.time.timing.Utils.DateToDayName;
import com.time.timing.databinding.PreviousweeksBinding;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class PreviousWeeks extends Fragment {

    private PreviousweeksBinding binding;
    private List<DateModel> list = new ArrayList<>();
    private static final String TAG = "PreviousWeeks";
    private int WeekCounter = 1;

    public PreviousWeeks() {
    }

    private ViewModel viewModel;


    private PreviousSundayAdapter previousSundayAdapter;
    private PreviousMondayAdapter previousMondayAdapter;
    private PreviousTuesdayAdapter previousTuesdayAdapter;
    private PreviousWednesdayAdapter previousWednesdayAdapter;
    private PreviousThursdayAdapter previousThursdayAdapter;
    private PreviousFridayAdapter previousFridayAdapter;
    private PreviousSaturdayAdapter previousSaturdayAdapter;

    private boolean SundayData = false;
    private boolean MondayData = false;
    private boolean TuesdayData = false;
    private boolean WednesdayData = false;
    private boolean ThursdayData = false;
    private boolean FridayData = false;
    private boolean SaturdayData = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.previousweeks, container, false);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);


        InitView();
        return binding.getRoot();
    }

    private void InitView() {
        previousSundayAdapter = new PreviousSundayAdapter();
        binding.SundayRecyclerView.setHasFixedSize(true);
        binding.SundayRecyclerView.setAdapter(previousSundayAdapter);

        previousMondayAdapter = new PreviousMondayAdapter();
        binding.MondayRecyclerView.setHasFixedSize(true);
        binding.MondayRecyclerView.setAdapter(previousMondayAdapter);



        previousTuesdayAdapter = new PreviousTuesdayAdapter();
        binding.TuesdayRecyclerView.setHasFixedSize(true);
        binding.TuesdayRecyclerView.setAdapter(previousTuesdayAdapter);

        previousWednesdayAdapter = new PreviousWednesdayAdapter();
        binding.WednesdayRecyclerView.setHasFixedSize(true);
        binding.WednesdayRecyclerView.setAdapter(previousWednesdayAdapter);

        previousThursdayAdapter = new PreviousThursdayAdapter();
        binding.ThursdayRecyclerView.setHasFixedSize(true);
        binding.ThursdayRecyclerView.setAdapter(previousThursdayAdapter);

        previousFridayAdapter = new PreviousFridayAdapter();
        binding.FridayRecyclerView.setHasFixedSize(true);
        binding.FridayRecyclerView.setAdapter(previousFridayAdapter);

        previousSaturdayAdapter = new PreviousSaturdayAdapter();
        binding.SaturdayRecyclerView.setHasFixedSize(true);
        binding.SaturdayRecyclerView.setAdapter(previousSaturdayAdapter);


        getnextweek();
        SetNextWeek();
        binding.PreviousWeekButton.setOnClickListener(view -> {
            WeekCounter = WeekCounter - 7;
            getnextweek();
            SetNextWeek();
        });

        binding.NextWeekButton.setOnClickListener(view -> {
            WeekCounter = WeekCounter + 7;
            getnextweek();
            SetNextWeek();
        });
    }



    private String[] getnextweek() {
        String[] days = new String[7];
        DateTimeFormatter dayOfMonthFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Jerusalem"));
        LocalDate day = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SATURDAY))
                .plusDays(WeekCounter);
        days[0] = day.format(dayOfMonthFormatter);
        for (int i = 1; i < 7; i++) {
            day = day.plusDays(1);
            days[i] = day.format(dayOfMonthFormatter);
        }
        return days;
    }


    private void SetNextWeek() {
        list.clear();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            for (int i = 0; i < getnextweek().length; i++) {
                var mydate = getnextweek()[i];
                var dateModel = new DateModel();
                dateModel.setDate(String.valueOf(mydate));
                list.add(dateModel);
            }
            var StartDate = list.get(0).getDate();
            var LastDate = list.get(list.size() - 1).getDate();
            binding.DateOfWeek.setText(DateToDayName.GetDate(StartDate) + " - " + DateToDayName.GetDate(LastDate));


            var Sunday = list.get(0).getDate();
            GetSundayScheduleRequest(Sunday);
            //Log.d(TAG, Sunday);

            var Monday = list.get(1).getDate();
            GetMondayScheduleRequest(Monday);
            //Log.d(TAG, Monday);


            var Tuesday = list.get(2).getDate();
            GetTuesdayScheduleRequest(Tuesday);
            //Log.d(TAG, Tuesday);


            var Wednesday = list.get(3).getDate();
            //Log.d(TAG, Wednesday);
            GetWednesdayScheduleRequest(Wednesday);


            var Thursday = list.get(4).getDate();
            GetThursdayScheduleRequest(Thursday);
            //Log.d(TAG, Thursday);

            var Friday = list.get(5).getDate();
            GetFridayScheduleRequest(Friday);

            var Saturday = list.get(list.size()-1).getDate();
            GetSaturdayScheduleRequest(Saturday);
            //Log.d(TAG, Friday);
        }
    }

    private void GetSundayScheduleRequest(String Date){
        viewModel.GetPreviousScheduleSunday(Date).observe(getActivity(), sundayScheduleModels -> {
            previousSundayAdapter.setList(sundayScheduleModels);
            previousSundayAdapter.notifyDataSetChanged();
            if(sundayScheduleModels != null){
                binding.SundayText.setVisibility(View.VISIBLE);
                binding.SundayText.setText(DateToDayName.GetDate(Date));
                SundayData = true;
                ShowNoDataMessage();
            }else {
                SundayData = false;
                binding.SundayText.setVisibility(View.GONE);
                binding.SundayText.setText(DateToDayName.GetDate(Date));
                ShowNoDataMessage();
            }
        });
    }

    private void GetMondayScheduleRequest(String Date){
        viewModel.GetPrevioysScheduleMonday(Date).observe(getActivity(), mondaySchaduleModels -> {
            previousMondayAdapter.setList(mondaySchaduleModels);
            previousMondayAdapter.notifyDataSetChanged();
            if(mondaySchaduleModels != null){
                MondayData = true;
                binding.MondayText.setVisibility(View.VISIBLE);
                binding.MondayText.setText(DateToDayName.GetDate(Date));
                ShowNoDataMessage();
            }else {
                MondayData = false;
                ShowNoDataMessage();
                binding.MondayText.setVisibility(View.GONE);
            }

        });
    }


    private void GetWednesdayScheduleRequest(String Date){
        viewModel.GetPreviousScheduleWednesday(Date).observe(getActivity(), wednesdaySchaduleModels -> {
            previousWednesdayAdapter.setList(wednesdaySchaduleModels);
            previousWednesdayAdapter.notifyDataSetChanged();
            if(wednesdaySchaduleModels != null){
                WednesdayData = true;
                binding.WednesdayText.setVisibility(View.VISIBLE);
                binding.WednesdayText.setText(DateToDayName.GetDate(Date));
                ShowNoDataMessage();
            }else {
                WednesdayData = false;
                binding.WednesdayText.setVisibility(View.GONE);
                ShowNoDataMessage();
            }
        });
    }

    private void GetTuesdayScheduleRequest(String Date) {
        viewModel.GetPreviousScheduleTuesday(Date).observe(getActivity(), tuesdaySchaduleModels -> {
            previousTuesdayAdapter.setList(tuesdaySchaduleModels);
            previousTuesdayAdapter.notifyDataSetChanged();
            if (tuesdaySchaduleModels != null) {
                TuesdayData = true;
                binding.TuesdayText.setVisibility(View.VISIBLE);
                binding.TuesdayText.setText(DateToDayName.GetDate(Date));
                ShowNoDataMessage();
            } else {
                TuesdayData = false;
                binding.TuesdayText.setVisibility(View.GONE);
                ShowNoDataMessage();
            }
        });
    }

    private void GetThursdayScheduleRequest(String Date){
        viewModel.GetPreviousScheduleThursday(Date).observe(getActivity(), thursdaySchaduleModels -> {
            previousThursdayAdapter.setList(thursdaySchaduleModels);
            previousThursdayAdapter.notifyDataSetChanged();
            if(thursdaySchaduleModels != null){
                ThursdayData = true;
                binding.ThursdayText.setVisibility(View.VISIBLE);
                binding.ThursdayText.setText(DateToDayName.GetDate(Date));
                ShowNoDataMessage();
            }else {
                ThursdayData = false;
                binding.ThursdayText.setVisibility(View.GONE);
                ShowNoDataMessage();
            }
        });
    }

    private void GetFridayScheduleRequest(String Date){
       viewModel.GetPreviousScheduleFriday(Date).observe(getActivity(), fridaySchaduleModels -> {
            previousFridayAdapter.setData(fridaySchaduleModels);
            previousFridayAdapter.notifyDataSetChanged();
            if(fridaySchaduleModels != null){
                FridayData = true;
                binding.FridayText.setVisibility(View.VISIBLE);
                binding.FridayText.setText(DateToDayName.GetDate(Date));
                ShowNoDataMessage();
            }else {
                FridayData = false;
                binding.FridayText.setVisibility(View.GONE);
                ShowNoDataMessage();
            }
       });
    }

    private void GetSaturdayScheduleRequest(String Date){
        viewModel.GetPreviousScheduleSaturday(Date).observe(getActivity(), saturdaySchaduleModels -> {
            previousSaturdayAdapter.setList(saturdaySchaduleModels);
            previousSaturdayAdapter.notifyDataSetChanged();
            if(saturdaySchaduleModels != null){
                SaturdayData = true;
                binding.SaturdayText.setText(DateToDayName.GetDate(Date));
                binding.SaturdayText.setVisibility(View.VISIBLE);
                ShowNoDataMessage();
            }else {
                SaturdayData = false;
                binding.SaturdayText.setVisibility(View.GONE);
                ShowNoDataMessage();
            }
        });
    }

    private void ShowNoDataMessage(){
        if(SundayData || MondayData || TuesdayData || WednesdayData || ThursdayData || FridayData || SaturdayData){
            binding.Icon.setVisibility(View.GONE);
            binding.Title.setVisibility(View.GONE);
        }else {
            binding.Icon.setVisibility(View.VISIBLE);
            binding.Title.setVisibility(View.VISIBLE);
        }
    }
}