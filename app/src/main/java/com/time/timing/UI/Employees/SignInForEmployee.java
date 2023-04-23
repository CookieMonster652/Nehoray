package com.time.timing.UI.Employees;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.time.timing.UI.Widget.TermsConditionBottomSheet;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.time.timing.Data.DataManager;
import com.time.timing.R;
import com.time.timing.UI.Widget.ProgressDialog;
import com.time.timing.Utils.HandleActivity;
import com.time.timing.Utils.SharePref;
import com.time.timing.Utils.Toast;
import com.time.timing.databinding.SigninforemployeeBinding;

import java.util.concurrent.TimeUnit;

public class SignInForEmployee extends AppCompatActivity {

    private SigninforemployeeBinding binding;
    private boolean ButtonEnable  = false;
    private FirebaseAuth Mauth;
    private SharePref sharePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.signinforemployee);
        Mauth = FirebaseAuth.getInstance();
        sharePref = new SharePref(SignInForEmployee.this);


        binding.Skip.setOnClickListener(v ->{
            ProgressDialog.ShowProgress(SignInForEmployee.this);
                Mauth.signInAnonymously().addOnCompleteListener(task -> {
               //     sharePref.SetData(DataManager.Phone, "+8801724541206");
                    sharePref.SetData(DataManager.LoginMode, DataManager.Employees);
                    HandleActivity.GotoEmployeesHome(SignInForEmployee.this);
                    Animatoo.animateSlideLeft(SignInForEmployee.this);
                    finish();
                    ProgressDialog.CancelDialog();
                }).addOnFailureListener(e -> {
                    Toast.SetToast(SignInForEmployee.this, e.getMessage());
                    ProgressDialog.CancelDialog();
                });
        });

        InitView();
    }


    private void InitView() {

        binding.TCondition.setOnClickListener(view -> {
            var bottomsheet = new TermsConditionBottomSheet();
            bottomsheet.OpenBottomSheed(SignInForEmployee.this);
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
            if(ButtonEnable){
                VerificationProcess();
            }
        });
    }

    private void VerificationProcess() {
        var Code = binding.CountryCode.getText().toString().trim();
        var Number = binding.InputNumber.getText().toString().trim();
        if (Number.isEmpty()) {
            AlertDialog.Builder MBuilder = new AlertDialog.Builder(SignInForEmployee.this);
            MBuilder.setTitle("שְׁגִיאָה");
            MBuilder.setMessage("הטלפון שלך ריק אנא הזן את המספר שלך תחילה");
            MBuilder.setPositiveButton("בסדר", (dialogInterface, i) -> dialogInterface.dismiss());
            AlertDialog alertDialog = MBuilder.create();
            alertDialog.show();
        } else {

            ProgressDialog.ShowProgress(SignInForEmployee.this);
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    Code + Number,
                    60,
                    TimeUnit.SECONDS,
                    SignInForEmployee.this,
                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                        }

                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {
                            Toast.SetToast(getApplicationContext(), e.getMessage());
                            ProgressDialog.CancelDialog();
                        }

                        @Override
                        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                            sharePref.SetData(DataManager.Phone, Code+Number);
                            sharePref.SetData(DataManager.LoginMode, DataManager.Employees);
                            HandleActivity.GotoEmployeesOTPScreen(SignInForEmployee.this, Code+Number, s);
                            Animatoo.animateSlideLeft(SignInForEmployee.this);

                            finish();
                        }
                    }
            );


        }
    }


}