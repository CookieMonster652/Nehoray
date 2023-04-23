package com.time.timing.UI.ManagerHome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.time.timing.Data.DataManager;
import com.time.timing.DateModel;
import com.time.timing.Network.ViewModel.ViewModel;
import com.time.timing.R;
import com.time.timing.UI.Manager.MySchedule;
import com.time.timing.UI.ManagerFragement.DailySchedule;
import com.time.timing.UI.ManagerFragement.ManageWorkers;
import com.time.timing.UI.ManagerFragement.NotConfrim;
import com.time.timing.UI.ManagerFragement.PreviousWeeks;
import com.time.timing.Utils.GetWeeksName;
import com.time.timing.Utils.HandleActivity;
import com.time.timing.databinding.ManagerhomeBinding;

import java.util.ArrayList;
import java.util.List;

public class ManagerHome extends AppCompatActivity {

    private ManagerhomeBinding binding;
    private ViewModel viewModel;
    private List<DateModel> list = new ArrayList<>();
    private boolean IsSundayExists = false;
    private boolean IsMondayExists = false;
    private boolean IsTuesdayExists = false;
    private boolean IsWednessdayExists = false;
    private boolean IsThursdayExists = false;
    private boolean IsFridayExists = false;
    private boolean IsSaturdayExists = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.managerhome);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);


        InitView();
        GetNextWeekData();
    }

    private void GetNextWeekData() {
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

            var Wednesday = list.get(3).getDate();
            GetWednesdaySchedule(Wednesday);

            var Thursday = list.get(4).getDate();
            GetThursdaySchedule(Thursday);

            var Friday = list.get(5).getDate();
            GetFridaySchedule(Friday);

            var Saturday = list.get(list.size() - 1).getDate();
            GetSaturdaySchedule(Saturday);

        }

    }

    private void InitView(){
        HandleActivity.HandleManagerFragment(new ManageWorkers(), ManagerHome.this);
        binding.setTitle(getResources().getString(R.string.manage_workers));
        binding.ChipNavigationBar.setItemSelected(R.id.ManageWorkers, true);
        binding.ChipNavigationBar.setOnItemSelectedListener(i -> {
            if(i == R.id.ManageWorkers){
                binding.setTitle(getResources().getString(R.string.manage_workers));
                HandleActivity.HandleManagerFragment(new ManageWorkers(), ManagerHome.this);
            }
            if(i == R.id.WhoNotConfrim){
                binding.setTitle(getResources().getString(R.string.not_confrim));
                HandleActivity.HandleManagerFragment(new NotConfrim(), ManagerHome.this);
            }
            if(i == R.id.DailySchedule){
                CheckScheduleExists();
            }
            if(i == R.id.PreviousWeeks){
                binding.setTitle(getResources().getString(R.string.previous_nweek));
                HandleActivity.HandleManagerFragment(new PreviousWeeks(), ManagerHome.this);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.CheckUserLogin().observe(this, aBoolean -> {
            if(!aBoolean){
                HandleActivity.GotoSplashScreen(ManagerHome.this);
            }else {
                viewModel.CheckUserProfileExists(DataManager.Manager).observe(this, aBoolean1 -> {
                    if(!aBoolean1){
                        GotoManagerProfile();
                    }
                });
            }
        });
    }

    private void GotoManagerProfile(){
        HandleActivity.GotoManagerProfileNameOFBusiness(ManagerHome.this);
        Animatoo.animateSlideLeft(ManagerHome.this);
        finish();
    }


    private void GetSundaySchedule(String Date){
        viewModel.GetSundayRequestExists(Date).observe(ManagerHome.this, aBoolean -> {
            IsSundayExists = aBoolean;
        });
    }
    private void GetMondaySchedule(String Date){
        viewModel.GetMondayRequestExists(Date).observe(ManagerHome.this, aBoolean -> {
            IsMondayExists = aBoolean;
        });
    }
    private void GetTuesdaySchedule(String Date){
        viewModel.GetTuesdayRequestExists(Date).observe(ManagerHome.this, aBoolean -> {
            IsTuesdayExists = aBoolean;
        });
    }
    private void GetWednesdaySchedule(String Date){
        viewModel.GetWednesdayRequestExists(Date).observe(ManagerHome.this, aBoolean -> {
            IsWednessdayExists = aBoolean;
        });
    }
    private void GetThursdaySchedule(String Date){
        viewModel.GetThursdayRequestExists(Date).observe(ManagerHome.this, aBoolean -> {
            IsThursdayExists = aBoolean;
        });
    }
    private void GetFridaySchedule(String Date){
        viewModel.GetFridayRequestExists(Date).observe(ManagerHome.this, aBoolean -> {
            IsFridayExists = aBoolean;
        });
    }
    private void GetSaturdaySchedule(String Date){
        viewModel.GetSaturdayRequestExists(Date).observe(ManagerHome.this, aBoolean -> {
            IsSaturdayExists = aBoolean;
        });
    }

    private void CheckScheduleExists(){
        if(!IsSundayExists && !IsMondayExists && !IsTuesdayExists && !IsWednessdayExists && !IsThursdayExists && !IsFridayExists && !IsSaturdayExists){
            binding.setTitle(getResources().getString(R.string.daily_schedule));
            HandleActivity.HandleManagerFragment(new DailySchedule(), ManagerHome.this);
        }else {
            HandleActivity.HandleManagerFragment(new MySchedule(), ManagerHome.this);
            binding.setTitle(getResources().getString(R.string.daily_schedule));
        }
    }
}