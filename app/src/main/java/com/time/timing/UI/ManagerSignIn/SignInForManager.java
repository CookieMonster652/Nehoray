package com.time.timing.UI.ManagerSignIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.time.timing.UI.Widget.TermsConditionBottomSheet;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.time.timing.Network.ViewModel.ViewModel;
import com.time.timing.R;
import com.time.timing.UI.Widget.ProgressDialog;
import com.time.timing.Utils.HandleActivity;
import com.time.timing.Utils.Toast;
import com.time.timing.databinding.SigninformanagerBinding;

import java.util.concurrent.TimeUnit;

public class SignInForManager extends AppCompatActivity {

    private ViewDataBinding binding;
    private ViewModel viewModel;
    private boolean ButtonEnable = false;
    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.signinformanager);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);

        InitView();
    }

    private void InitView() {
        binding.TCondition.setOnClickListener(view -> {
            var bottomsheet = new TermsConditionBottomSheet();
            bottomsheet.OpenBottomSheed(SignInForManager.this);
        });

        binding.LoginCheckBox.setOnClickListener(view -> {
            if (binding.LoginCheckBox.isChecked()) {
                binding.Continue.setBackgroundResource(R.drawable.button_bg);
                ButtonEnable = true;
            } else {
                ButtonEnable = false;
                binding.Continue.setBackgroundResource(R.drawable.invisiable_buttonbg);
            }
        });


        binding.Continue.setOnClickListener(view1 -> {
            if (ButtonEnable) {
                VerificationProcess();
            }
        });
    }

    private void VerificationProcess() {
        var CountryCode = binding.CountryCode.getText().toString().trim();
        var Number = binding.InputNumber.getText().toString().trim();
        if (Number.isEmpty()) {
            AlertDialog.Builder MBuilder = new AlertDialog.Builder(SignInForManager.this);
            MBuilder.setTitle("שְׁגִיאָה");
            MBuilder.setMessage("הטלפון שלך ריק אנא הזן את המספר שלך תחילה");
            MBuilder.setPositiveButton("בסדר", (dialogInterface, i) -> dialogInterface.dismiss());
            AlertDialog alertDialog = MBuilder.create();
            alertDialog.show();
        } else {
            ProgressDialog.ShowProgress(SignInForManager.this);
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    CountryCode + Number,
                    60,
                    TimeUnit.SECONDS,
                    SignInForManager.this,
                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                        }

                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {
                            Toast.SetToast(getApplicationContext(), e.getMessage());
                            ProgressDialog.CancelDialog();
                            Log.d(TAG, e.getMessage());
                        }

                        @Override
                        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            ProgressDialog.CancelDialog();
                            HandleActivity.GotoManagerOTPScreen(SignInForManager.this, CountryCode + Number, s);
                            Animatoo.animateSlideLeft(SignInForManager.this);
                            finish();
                        }
                    }
            );

        }
    }
}