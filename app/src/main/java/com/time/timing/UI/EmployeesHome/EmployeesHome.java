package com.time.timing.UI.EmployeesHome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.time.timing.Data.DataManager;
import com.time.timing.Network.ViewModel.ViewModel;
import com.time.timing.R;
import com.time.timing.Utils.HandleActivity;
import com.time.timing.Utils.SharePref;
import com.time.timing.databinding.EmployeeshomeBinding;

public class   EmployeesHome extends AppCompatActivity {

    private EmployeeshomeBinding binding;
    private ViewModel viewModel;
    private SharePref sharePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.employeeshome);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        sharePref = new SharePref(EmployeesHome.this);

        InitView();
    }

    private void InitView(){
        binding.setTitle(getResources().getString(R.string.personal_info));
        HandleActivity.HandleEmployeesFragment(new PersonalInfo(), EmployeesHome.this);
        binding.ChipNavigationBar.setItemSelected(R.id.PersonalInfo, true);
        binding.ChipNavigationBar.setOnItemSelectedListener(i -> {
            if(i == R.id.PersonalInfo){
                binding.setTitle(getResources().getString(R.string.personal_info));
                HandleActivity.HandleEmployeesFragment(new PersonalInfo(), EmployeesHome.this);
            } if(i == R.id.DailySchedule){
                binding.setTitle(getResources().getString(R.string.daily_schedule));
                HandleActivity.HandleEmployeesFragment(new DailySchedule(), EmployeesHome.this);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        viewModel.CheckUserLogin().observe(this, aBoolean -> {
            if(!aBoolean){
                HandleActivity.GotoSplashScreen(EmployeesHome.this);
            }else {
                viewModel.CheckUserProfileExists(DataManager.Employees).observe(this, aBoolean1 -> {
                    if(!aBoolean1){
                        GotoEmployeesProfile();
                    }
                });
            }
        });
    }


    private void GotoEmployeesProfile(){
        HandleActivity.GotoEmployeesProfileName(EmployeesHome.this);
        Animatoo.animateSlideLeft(EmployeesHome.this);
        finish();
    }
}