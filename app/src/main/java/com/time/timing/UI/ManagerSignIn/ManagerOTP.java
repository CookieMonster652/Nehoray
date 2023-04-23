package com.time.timing.UI.ManagerSignIn;

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

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;

public class ManagerOTP extends AppCompatActivity {

    private static final String TAG = "TAG";
    private ShapeableImageView SubmitButton;
    private TextView ResentText;
    private TextView NumberText;
    private FirebaseAuth Mauth;
    private SharePref sharePref;
    private String Number;
    private String VerificationID;
    private OtpTextView otpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.managerotp);
        sharePref = new SharePref(ManagerOTP.this);
        Number = getIntent().getStringExtra(DataManager.Phone);
        VerificationID = getIntent().getStringExtra(DataManager.VerificationID);

        otpTextView = findViewById(R.id.otp_view);

        SubmitButton = findViewById(R.id.Continue);
        ResentText = findViewById(R.id.ResentCodeText);
        NumberText = findViewById(R.id.Number);
        NumberText.setText(Number);
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



    private void Verification(String Code){
        var Progress = new ProgressDialog();
            if(VerificationID != null){
                Progress.ShowProgress(ManagerOTP.this);
                var PhoneAuth = PhoneAuthProvider.getCredential(
                        VerificationID,
                        Code
                );
                FirebaseAuth.getInstance().signInWithCredential(PhoneAuth).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        sharePref.SetData(DataManager.LoginMode, DataManager.Manager);
                        sharePref.SetData(DataManager.Phone, Number);
                        Progress.CancelDialog();
                        Toast.SetToast(getApplicationContext(), "Success");
                        HandleActivity.GotoManagerHome(ManagerOTP.this);
                        Animatoo.animateSlideLeft(ManagerOTP.this);
                        finish();
                    }else {
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
        Animatoo.animateSlideRight(ManagerOTP.this);
    }
}