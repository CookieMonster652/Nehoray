package com.time.timing.UI.Manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.time.timing.DateModel;
import com.time.timing.Network.ScheduleViewModel.AllScheduleViewModel;
import com.time.timing.R;
import com.time.timing.UI.Adapter.AddShiftAdapter;
import com.time.timing.UI.ScheduleAdapter.FridayScheduleAdapter;
import com.time.timing.UI.ScheduleAdapter.MondayScheduleAdapter;
import com.time.timing.UI.ScheduleAdapter.SaturdayScheduleAdapter;
import com.time.timing.UI.ScheduleAdapter.SundayScheduleAdapter;
import com.time.timing.UI.ScheduleAdapter.ThursdayScheduleAdapter;
import com.time.timing.UI.ScheduleAdapter.TuesdayScheduleAdapter;
import com.time.timing.UI.ScheduleAdapter.WednesdayScheduleAdapter;
import com.time.timing.Utils.DateToDayName;
import com.time.timing.Utils.GetWeeksName;
import com.time.timing.Utils.HandleActivity;
import com.time.timing.Utils.SpacingItemDecorator;
import com.time.timing.databinding.ScheduleListBinding;

import java.util.ArrayList;
import java.util.List;

public class ScheduleList extends AppCompatActivity {

    private ScheduleListBinding binding;
    private AddShiftAdapter addShiftAdapter;
    private AllScheduleViewModel sundayViewModel;
    private SpacingItemDecorator spacingItemDecorator;
    private List<DateModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.schedule_list);
        sundayViewModel = new ViewModelProvider(this).get(AllScheduleViewModel.class);


        InitView();
        SetNextWeek();
    }


    private void SetNextWeek() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            for (int i = 0; i < GetWeeksName.getDaysOfWeek().length; i++) {
                var mydate = GetWeeksName.getDaysOfWeek()[i];
                var dateModel = new DateModel();
                dateModel.setDate(String.valueOf(mydate));
                list.add(dateModel);

            }
            var Sunday = list.get(0).getDate();
            GetSundaySchedule(Sunday);

            var Monday = list.get(1).getDate();
            GetMondaySchedule(Monday);

            var Tuesday = list.get(2).getDate();
            GetTuesdaySchedule(Tuesday);

            var Wednessday = list.get(3).getDate();
            GetWednessdaySchedule(Wednessday);

            var Thursday = list.get(4).getDate();
            GetThursdaySchedule(Thursday);

            var Friday = list.get(5).getDate();
            GetFridaySchedule(Friday);

            var Saturday = list.get(list.size() - 1).getDate();
            GetSaturdaySchedule(Saturday);

        }

    }



    private void GetFridaySchedule(String Friday) {
        binding.FridayText.setText(DateToDayName.GetDate(Friday));
        var fridayScheduleAdapter = new FridayScheduleAdapter();
        var layoutManager = new LinearLayoutManager(ScheduleList.this, LinearLayoutManager.HORIZONTAL, false);
        binding.FridayRecyclerView.setHasFixedSize(true);
        binding.FridayRecyclerView.setLayoutManager(layoutManager);
        binding.FridayRecyclerView.setAdapter(fridayScheduleAdapter);
        spacingItemDecorator = new SpacingItemDecorator(15, 0, 0, 0);
        binding.FridayRecyclerView.addItemDecoration(spacingItemDecorator);
        sundayViewModel.GetFridaySchedule(Friday).observe(this, fridaySchaduleModels -> {
            fridayScheduleAdapter.setList(fridaySchaduleModels);
            fridayScheduleAdapter.notifyDataSetChanged();
            if (fridaySchaduleModels != null) {
                binding.FridayEdit.setVisibility(View.GONE);
            } else {
                binding.FridayEdit.setVisibility(View.VISIBLE);
                binding.FridayEdit.setOnClickListener(view -> {
                    GotoSelectShift(Friday);
                });
            }
        });
    }

    private void GetThursdaySchedule(String Thusday) {
        binding.ThursdayText.setText(DateToDayName.GetDate(Thusday));
        var thursdayScheduleAdapter = new ThursdayScheduleAdapter();
        var layoutManager = new LinearLayoutManager(ScheduleList.this, LinearLayoutManager.HORIZONTAL, false);
        binding.ThursdayRecyclerView.setHasFixedSize(true);
        binding.ThursdayRecyclerView.setLayoutManager(layoutManager);
        binding.ThursdayRecyclerView.setAdapter(thursdayScheduleAdapter);
        spacingItemDecorator = new SpacingItemDecorator(15, 0, 0, 0);
        binding.ThursdayRecyclerView.addItemDecoration(spacingItemDecorator);
        sundayViewModel.GetThursdaySchedule(Thusday).observe(this, thursdaySchaduleModels -> {
            thursdayScheduleAdapter.setList(thursdaySchaduleModels);
            thursdayScheduleAdapter.notifyDataSetChanged();
            if (thursdaySchaduleModels != null) {
                binding.ThursdayEdit.setVisibility(View.GONE);
            } else {
                binding.ThursdayEdit.setVisibility(View.VISIBLE);
                binding.ThursdayEdit.setOnClickListener(view -> {
                    GotoSelectShift(Thusday);
                });
            }
        });
    }

    private void GetWednessdaySchedule(String Wednessday) {
        binding.WednesdayText.setText(DateToDayName.GetDate(Wednessday));
        var wednesdayScheduleAdapter = new WednesdayScheduleAdapter();
        var layoutManager = new LinearLayoutManager(ScheduleList.this, LinearLayoutManager.HORIZONTAL, false);
        binding.WednesdayRecyclerView.setHasFixedSize(true);
        binding.WednesdayRecyclerView.setLayoutManager(layoutManager);
        binding.WednesdayRecyclerView.setAdapter(wednesdayScheduleAdapter);
        spacingItemDecorator = new SpacingItemDecorator(15, 0, 0, 0);
        binding.WednesdayRecyclerView.addItemDecoration(spacingItemDecorator);
        sundayViewModel.GetWednesdaySchedule(Wednessday).observe(this, wednesdaySchaduleModels -> {
            wednesdayScheduleAdapter.setList(wednesdaySchaduleModels);
            wednesdayScheduleAdapter.notifyDataSetChanged();
            if (wednesdaySchaduleModels != null) {
                binding.WednesdayEdit.setVisibility(View.GONE);
            } else {
                binding.WednesdayEdit.setVisibility(View.VISIBLE);
                binding.WednesdayEdit.setOnClickListener(view -> {
                    GotoSelectShift(Wednessday);
                });
            }
        });
    }


    private void GetTuesdaySchedule(String Tuesday) {
        binding.TuesdayText.setText(DateToDayName.GetDate(Tuesday));
        var tuesdayScheduleAdapter = new TuesdayScheduleAdapter();
        var layoutManager = new LinearLayoutManager(ScheduleList.this, LinearLayoutManager.HORIZONTAL, false);
        binding.TuesdayRecyclerView.setHasFixedSize(true);
        binding.TuesdayRecyclerView.setLayoutManager(layoutManager);
        binding.TuesdayRecyclerView.setAdapter(tuesdayScheduleAdapter);
        spacingItemDecorator = new SpacingItemDecorator(15, 0, 0, 0);
        binding.TuesdayRecyclerView.addItemDecoration(spacingItemDecorator);
        sundayViewModel.GetTuesdaySchedule(Tuesday).observe(this, tuesdaySchaduleModels -> {
            tuesdayScheduleAdapter.setList(tuesdaySchaduleModels);
            tuesdayScheduleAdapter.notifyDataSetChanged();
            if (tuesdaySchaduleModels != null) {
                binding.TuesdayEdit.setVisibility(View.GONE);
            } else {
                binding.TuesdayEdit.setVisibility(View.VISIBLE);
                binding.TuesdayEdit.setOnClickListener(view -> {
                    GotoSelectShift(Tuesday);
                });
            }
        });
    }

    private void GetMondaySchedule(String Monday) {
        binding.MondayText.setText(DateToDayName.GetDate(Monday));
        var mondayScheduleAdapter = new MondayScheduleAdapter();
        var layoutManager = new LinearLayoutManager(ScheduleList.this, LinearLayoutManager.HORIZONTAL, false);
        binding.MondayRecyclerView.setHasFixedSize(true);
        binding.MondayRecyclerView.setLayoutManager(layoutManager);
        binding.MondayRecyclerView.setAdapter(mondayScheduleAdapter);
        spacingItemDecorator = new SpacingItemDecorator(15, 0, 0, 0);
        binding.MondayRecyclerView.addItemDecoration(spacingItemDecorator);
        sundayViewModel.GetMondaySchedule(Monday).observe(this, mondaySchaduleModels -> {
            if (mondaySchaduleModels != null) {
                mondayScheduleAdapter.setList(mondaySchaduleModels);
                mondayScheduleAdapter.notifyDataSetChanged();
                binding.MondayEdit.setVisibility(View.GONE);
            } else {
                binding.MondayEdit.setVisibility(View.VISIBLE);
                binding.MondayEdit.setOnClickListener(view -> {
                    GotoSelectShift(Monday);
                });
            }
        });
    }

    private void GetSundaySchedule(String Sunday) {
        binding.SundayText.setText(DateToDayName.GetDate(Sunday));
        var sundayScheduleAdapter = new SundayScheduleAdapter();
        var layoutManager = new LinearLayoutManager(ScheduleList.this, LinearLayoutManager.HORIZONTAL, false);
        binding.SundayRecyclerView.setHasFixedSize(true);
        binding.SundayRecyclerView.setLayoutManager(layoutManager);
        binding.SundayRecyclerView.setAdapter(sundayScheduleAdapter);
        spacingItemDecorator = new SpacingItemDecorator(15, 0, 0, 0);
        binding.SundayRecyclerView.addItemDecoration(spacingItemDecorator);
        sundayViewModel.GetSundatSchedule(Sunday).observe(this, sundayScheduleModels -> {
            sundayScheduleAdapter.setList(sundayScheduleModels);
            sundayScheduleAdapter.notifyDataSetChanged();
            if (sundayScheduleModels != null) {
                binding.SundayEdit.setVisibility(View.GONE);
            } else {
                binding.SundayEdit.setVisibility(View.VISIBLE);
                binding.SundayEdit.setOnClickListener(view -> {
                    GotoSelectShift(Sunday);
                });
            }
        });
    }

    private void GetSaturdaySchedule(String Saturday) {
        binding.SaturdayText.setText(DateToDayName.GetDate(Saturday));
        var saturdayScheduleAdapter = new SaturdayScheduleAdapter();
        var layoutManager = new LinearLayoutManager(ScheduleList.this, LinearLayoutManager.HORIZONTAL, false);
        binding.SaturdayRecyclerView.setHasFixedSize(true);
        binding.SaturdayRecyclerView.setLayoutManager(layoutManager);
        binding.SaturdayRecyclerView.setAdapter(saturdayScheduleAdapter);
        spacingItemDecorator = new SpacingItemDecorator(15, 0, 0, 0);
        binding.SaturdayRecyclerView.addItemDecoration(spacingItemDecorator);
        sundayViewModel.GetSaturdaySchedule(Saturday).observe(this, saturdaySchaduleModels -> {
            saturdayScheduleAdapter.setList(saturdaySchaduleModels);
            saturdayScheduleAdapter.notifyDataSetChanged();
            if (saturdaySchaduleModels != null) {
                binding.SaturdayEdit.setVisibility(View.GONE);
            } else {
                binding.SaturdayEdit.setVisibility(View.VISIBLE);
                binding.SaturdayEdit.setOnClickListener(view -> {
                    GotoSelectShift(Saturday);
                });
            }
        });
    }

    private void InitView() {
        binding.Indicator.Title.setText(getResources().getString(R.string.Invite_to_worker));
        binding.Indicator.BackButton.setOnClickListener(view -> {
            finish();
            Animatoo.animateSlideRight(ScheduleList.this);
        });

        binding.InviteWorkerButton.setOnClickListener(view -> {
            HandleActivity.GotoInviteEmployees(ScheduleList.this);
            Animatoo.animateSlideLeft(ScheduleList.this);
        });
    }


    @Override
    public void onBackPressed() {
        finish();
        Animatoo.animateSlideRight(ScheduleList.this);
    }

    private void GotoSelectShift(String Date){
        HandleActivity.GotoSelectShift(ScheduleList.this, Date);
    }
}