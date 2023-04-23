package com.time.timing.UI.Manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.time.timing.R;
import com.time.timing.Utils.HandleActivity;
import com.time.timing.Utils.ManagerBusinessProfileTextWatcher;
import com.time.timing.Utils.Toast;
import com.time.timing.databinding.ManagerbusinessprofileBinding;

public class ManagerBusinessProfile extends AppCompatActivity {

    private ManagerbusinessprofileBinding binding;
    private ManagerBusinessProfileTextWatcher businessProfileTextWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.managerbusinessprofile);

        InitView();
    }

    private void InitView(){
        businessProfileTextWatcher = new ManagerBusinessProfileTextWatcher(binding.NameOFBusinessInput, binding.ImageInput);
        binding.NameOFBusinessInput.addTextChangedListener(businessProfileTextWatcher);
        binding.ImageInput.addTextChangedListener(businessProfileTextWatcher);

        businessProfileTextWatcher.OnCompleteEvent(val -> {
            if(val){
               binding.Continue.setBackgroundResource(R.drawable.button_bg);
            }else {
                binding.Continue.setBackgroundResource(R.drawable.invisiable_buttonbg);
            }
        });

        binding.Continue.setOnClickListener(view -> {
            String NameOfBusinessText = binding.NameOFBusinessInput.getText().toString().trim();
            String Name = binding.ImageInput.getText().toString().trim();

            if(NameOfBusinessText.isEmpty()){
                Toast.SetToast(getApplicationContext(), "Name of business require");
            }else if(Name.isEmpty()){
                Toast.SetToast(getApplicationContext(), "Name require");
            }else {
                HandleActivity.GotoManagerBusinessLogoProfile(ManagerBusinessProfile.this, NameOfBusinessText, Name);
                Animatoo.animateSlideLeft(ManagerBusinessProfile.this);
                finish();
            }
        });
    }
}