package com.time.timing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.os.Handler;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.auth.FirebaseAuth;
import com.time.timing.Data.DataManager;
import com.time.timing.Network.ViewModel.ViewModel;
import com.time.timing.Utils.HandleActivity;
import com.time.timing.Utils.SharePref;
import com.time.timing.Utils.Toast;
import com.time.timing.databinding.SplashscreenBinding;

public class SplashScreen extends AppCompatActivity {

    private SplashscreenBinding binding;
    private ViewModel viewModel;
    private SharePref sharePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.splashscreen);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        sharePref = new SharePref(SplashScreen.this);


        viewModel.CheckUserLogin().observe(this, aBoolean -> {
            if (aBoolean){
                if(sharePref.GetData(DataManager.LoginMode) != null){
                    if(sharePref.GetData(DataManager.LoginMode).equals(DataManager.Manager)){
                        HandleActivity.GotoManagerHome(SplashScreen.this);
                        finish();
                        Animatoo.animateSlideLeft(SplashScreen.this);
                    }
                    if(sharePref.GetData(DataManager.LoginMode).equals(DataManager.Employees)){
                        HandleActivity.GotoEmployeesHome(SplashScreen.this);
                        finish();
                        Animatoo.animateSlideLeft(SplashScreen.this);
                    }
                }
            }else {
                new Handler().postDelayed(() -> {
                    HandleActivity.GotoLoginDifferentUser(SplashScreen.this);
                    finish();
                    Animatoo.animateSlideLeft(SplashScreen.this);
                }, 3000);
            }
        });

    }
}