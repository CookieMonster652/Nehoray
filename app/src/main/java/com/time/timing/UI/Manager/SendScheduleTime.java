package com.time.timing.UI.Manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.time.timing.R;
import com.time.timing.databinding.SendscheduletimeBinding;

public class SendScheduleTime extends AppCompatActivity {

    private SendscheduletimeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.sendemployeesheduletime);


    }
}