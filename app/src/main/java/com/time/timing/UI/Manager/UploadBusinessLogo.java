package com.time.timing.UI.Manager;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
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
import com.time.timing.databinding.UploadbusinesslogoBinding;

public class UploadBusinessLogo extends AppCompatActivity {

    private UploadbusinesslogoBinding binding;
    private String NameOFBusiness, Name;
    private int PermissionCode = 100;
    private ActivityResultLauncher<Intent> launcher;
    private String BusinessLogoDownloadUri;
    private ViewModel viewModel;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.uploadbusinesslogo);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);

        progressDialog = new ProgressDialog();
        NameOFBusiness = getIntent().getStringExtra(DataManager.NameOFBusiness);
        Name = getIntent().getStringExtra(DataManager.Name);

        InitView();
        GetExternalStorageData();
    }

    private void InitView() {
        binding.UploadBusinessLogo.setOnClickListener(view -> {
                if(Permission.ReadExternalStorage(UploadBusinessLogo.this, PermissionCode)){
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    launcher.launch(intent);
                }
        });

        binding.SkipButton.setOnClickListener(view -> {
            progressDialog.ShowProgress(UploadBusinessLogo.this);
            viewModel.ManagerProfilePost(NameOFBusiness, Name, null).observe(this, aBoolean -> {
                if(aBoolean){
                    progressDialog.CancelDialog();
                    HandleActivity.GotoManagerHome(UploadBusinessLogo.this);
                    Animatoo.animateSlideRight(UploadBusinessLogo.this);
                    finish();
                }else {
                    progressDialog.CancelDialog();
                }
            });
        });

        binding.Continue.setOnClickListener(view -> {
            if(BusinessLogoDownloadUri == null){
                Toast.SetToast(getApplicationContext(), "Your business logo require");
            }else {
                viewModel.ManagerProfilePost(NameOFBusiness, Name, BusinessLogoDownloadUri).observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if(aBoolean){
                            progressDialog.CancelDialog();
                            HandleActivity.GotoManagerHome(UploadBusinessLogo.this);
                            Animatoo.animateSlideRight(UploadBusinessLogo.this);
                            finish();
                        }else {
                            progressDialog.CancelDialog();
                        }
                    }
                });
            }
        });
    }



    private void GetExternalStorageData(){
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            progressDialog.ShowProgress(UploadBusinessLogo.this);
            if(result.getResultCode() == RESULT_OK){
                if(result.getData().getData() != null){
                    binding.Continue.setBackgroundResource(R.drawable.button_bg);
                    viewModel.BusinessLogoUpload(result.getData().getData()).observe(this, new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            BusinessLogoDownloadUri = s;
                            progressDialog.CancelDialog();
                        }
                    });
                }
            }else {
                progressDialog.CancelDialog();
                return;
            }

        });
    }
}