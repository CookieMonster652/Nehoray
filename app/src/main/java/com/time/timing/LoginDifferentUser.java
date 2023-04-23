package com.time.timing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.time.timing.UI.Widget.ProgressDialog;
import com.time.timing.Utils.HandleActivity;
import com.time.timing.databinding.LogindifferentuserBinding;

public class LoginDifferentUser extends AppCompatActivity {

    private LogindifferentuserBinding binding;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.logindifferentuser);
    }

    public void LoginEmployeeClick(View view) {
        HandleActivity.GotoSignInEmployees(LoginDifferentUser.this);
        finish();
    }

    public void LoginManagerClick(View view) {
        HandleActivity.GotoSignInManager(LoginDifferentUser.this);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
        Animatoo.animateSlideRight(LoginDifferentUser.this);
    }
}