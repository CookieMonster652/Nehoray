package com.time.timing.UI.Employees;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.time.timing.Data.DataManager;
import com.time.timing.Network.ViewModel.ViewModel;
import com.time.timing.R;
import com.time.timing.UI.Widget.ProgressDialog;
import com.time.timing.Utils.HandleActivity;
import com.time.timing.Utils.Permission;
import com.time.timing.Utils.Toast;
import com.time.timing.databinding.ProfilepicuploademployeeBinding;

public class ProfilePicUploadEmployee extends AppCompatActivity {

    private ProfilepicuploademployeeBinding binding;
    private ViewModel viewModel;
    private String DownloadUri;
    private ActivityResultLauncher<Intent> launcher;
    private static final int PermissionCode = 15;
    private String Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.profilepicuploademployee);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        Name = getIntent().getStringExtra(DataManager.Name);


        ReadExternalStoragePermission();
        InitView();
    }

    private void InitView(){
        binding.ImageInput.setOnClickListener(view -> {
            if(Permission.ReadExternalStorage(ProfilePicUploadEmployee.this, PermissionCode)){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                launcher.launch(intent);
            }
        });

        binding.Continue.setOnClickListener(view -> {
            if(DownloadUri == null){
                Toast.SetToast(ProfilePicUploadEmployee.this, "Profile image require");
            }else {
                UploadDataToServer();
            }
        });

        binding.Skip.setOnClickListener(view -> {
            var Progress = new ProgressDialog();
            Progress.ShowProgress(ProfilePicUploadEmployee.this);
            viewModel.UploadEmployeesProfileData(null, Name).observe(this, aBoolean -> {
                if(aBoolean){
                    Progress.CancelDialog();
                    HandleActivity.GotoEmployeesHome(ProfilePicUploadEmployee.this);
                    Animatoo.animateSlideRight(ProfilePicUploadEmployee.this);
                    finish();
                }else {
                    Progress.CancelDialog();
                }
            });
        });
    }

    private void UploadDataToServer(){
        var Progress = new ProgressDialog();
        viewModel.UploadEmployeesProfileData(DownloadUri, Name).observe(this, aBoolean -> {
            if(aBoolean){
                binding.Continue.setBackgroundResource(R.drawable.button_bg);
                Progress.ShowProgress(ProfilePicUploadEmployee.this);
                HandleActivity.GotoEmployeesHome(ProfilePicUploadEmployee.this);
                Animatoo.animateSlideRight(ProfilePicUploadEmployee.this);
                finish();
            }else {
                Progress.CancelDialog();
            }
        });
    }

    private void ReadExternalStoragePermission(){
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            var Progress  = new ProgressDialog();
            Progress.ShowProgress(ProfilePicUploadEmployee.this);
            if(result.getResultCode() == RESULT_OK){
                if(result.getData().getData() != null){
                    viewModel.EmployeesProfileImageUpload(result.getData().getData()).observe(this, s -> {
                        DownloadUri = s;
                        binding.Continue.setBackgroundResource(R.drawable.button_bg);
                        Progress.CancelDialog();
                    });
                }
            }else {
                Progress.CancelDialog();
            }
        });
    }
}