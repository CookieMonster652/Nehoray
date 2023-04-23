package com.time.timing.UI.Employees;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;
import com.time.timing.Data.DataManager;
import com.time.timing.R;
import com.time.timing.UI.Widget.ProgressDialog;
import com.time.timing.Utils.HandleActivity;
import com.time.timing.Utils.SharePref;
import com.time.timing.Utils.Toast;
import com.time.timing.databinding.EmployeesotpBinding;

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;

public class Employeesotp extends AppCompatActivity {

    private ShapeableImageView SubmitButton;
    private TextView ResentText;
    private TextView NumberText;
    private FirebaseAuth Mauth;
    private SharePref sharePref;
    private String PhoneNumber;

    private String VerificationID;
    private OtpTextView otpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employeesotp);
        sharePref = new SharePref(Employeesotp.this);

        otpTextView = findViewById(R.id.otp_view);
        VerificationID = getIntent().getStringExtra(DataManager.VerificationID);

        PhoneNumber = getIntent().getStringExtra(DataManager.Phone);
        SubmitButton = findViewById(R.id.Continue);
        ResentText = findViewById(R.id.ResentCodeText);
        NumberText = findViewById(R.id.Number);
        if (PhoneNumber != null) {
            NumberText.setText(PhoneNumber);
        }
        Mauth = FirebaseAuth.getInstance();


        otpTextView.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {

            }

            @Override
            public void onOTPComplete(@NonNull String s) {
                Verification(s);
            }
        });
    }


    private void Verification(String Code) {
        var Progress = new ProgressDialog();
        if (VerificationID != null) {
            Progress.ShowProgress(Employeesotp.this);
            var PhoneAuth = PhoneAuthProvider.getCredential(
                    VerificationID,
                    Code
            );
            FirebaseAuth.getInstance().signInWithCredential(PhoneAuth).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    sharePref.SetData(DataManager.Phone, PhoneNumber);
                    sharePref.SetData(DataManager.LoginMode, DataManager.Employees);
                    Progress.CancelDialog();
                    Toast.SetToast(getApplicationContext(), "Success");
                    HandleActivity.GotoEmployeesHome(Employeesotp.this);
                    Animatoo.animateSlideLeft(Employeesotp.this);
                    finish();
                } else {
                    Progress.CancelDialog();
                    Toast.SetToast(getApplicationContext(), task.getException().getMessage());
                }
            }).addOnFailureListener(e -> {
                ProgressDialog.CancelDialog();
                Toast.SetToast(getApplicationContext(), e.getMessage());
            });
        }

    }

    @Override
    public void onBackPressed() {
        finish();
        Animatoo.animateSlideRight(Employeesotp.this);
    }
}