package com.time.timing.UI.Employees;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.time.timing.R;
import com.time.timing.Utils.EmployeesProfileNameTextWatcher;
import com.time.timing.Utils.HandleActivity;
import com.time.timing.Utils.Toast;
import com.time.timing.databinding.EmployeeprofilenameBinding;

public class EmployeeProfileName extends AppCompatActivity {

    private EmployeeprofilenameBinding binding;
    private EmployeesProfileNameTextWatcher employeesProfileNameTextWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.employeeprofilename);

        InitView();
    }

    private void InitView(){
        employeesProfileNameTextWatcher = new EmployeesProfileNameTextWatcher(binding.NameInput);
        binding.NameInput.addTextChangedListener(employeesProfileNameTextWatcher);
        employeesProfileNameTextWatcher.OnCompleteEvent(val -> {
            if(val){
                binding.Continue.setBackgroundResource(R.drawable.button_bg);
            }else {
                binding.Continue.setBackgroundResource(R.drawable.invisiable_buttonbg);
            }
        });

        binding.Continue.setOnClickListener(view -> {
            String Name = binding.NameInput.getText().toString().trim();
            if(Name.isEmpty()){
                Toast.SetToast(getApplicationContext(), "Name require");
            }else {
                HandleActivity.GotoEmployeesUploadPic(EmployeeProfileName.this, Name);
                Animatoo.animateSlideRight(EmployeeProfileName.this);
                finish();
            }
        });
    }
}