package com.time.timing.UI.Manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.time.timing.Data.DataManager;
import com.time.timing.R;
import com.time.timing.Utils.ComparingTimeDate;
import com.time.timing.Utils.Date;
import com.time.timing.Utils.DateToDayName;
import com.time.timing.Utils.HandleActivity;
import com.time.timing.Utils.SharePref;
import com.time.timing.Utils.Toast;
import com.time.timing.databinding.SendemployeesheduletimeBinding;

public class SendEmployeeSheduleTime extends AppCompatActivity {

    private SendemployeesheduletimeBinding binding;
    private SharePref sharePref;
    private String ComparingDate, ComparingTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.sendemployeesheduletime);
        sharePref = new SharePref(SendEmployeeSheduleTime.this);


        InitView();
    }


    private void InitView() {

        binding.Toolbar.Title.setText(getResources().getString(R.string.Select_Schedule_Time));
        binding.Toolbar.BackButton.setOnClickListener(view -> {
            finish();
            Animatoo.animateSlideRight(SendEmployeeSheduleTime.this);
        });

        binding.ScheduleStartTime.setOnClickListener(view -> {
            HandleActivity.GotoRegistationStartTime(SendEmployeeSheduleTime.this);
        });
        binding.RegistrationEnd.setOnClickListener(view -> {
            HandleActivity.GotoRegistationEndTime(SendEmployeeSheduleTime.this);
        });

        if (sharePref.GetData(DataManager.RegistrationStartDate) != null && sharePref.GetData(DataManager.RegistrationStartTime) != null) {
            if (Date.GetCurrentDate().equals(sharePref.GetData(DataManager.RegistrationStartDate))) {
                binding.ScheduleStartTime.setText(getResources().getString(R.string.Today) + " " + sharePref.GetData(DataManager.RegistrationStartTime));
            } else {
                binding.ScheduleStartTime.setText(DateToDayName.GetDate(sharePref.GetData(DataManager.RegistrationStartDate)) + " " + sharePref.GetData(DataManager.RegistrationStartTime));
            }
        }
        if (sharePref.GetData(DataManager.RegistrationEndDate) != null && sharePref.GetData(DataManager.RegistrationEndTime) != null) {
            if (Date.GetCurrentDate().equals(sharePref.GetData(DataManager.RegistrationEndDate))) {
                binding.RegistrationEnd.setText(getResources().getString(R.string.Today) + " " + sharePref.GetData(DataManager.RegistrationEndTime));
            } else {
                binding.RegistrationEnd.setText(DateToDayName.GetDate(sharePref.GetData(DataManager.RegistrationEndDate)) + " " + sharePref.GetData(DataManager.RegistrationEndTime));
            }
        }


        binding.ConfirmButton.setOnClickListener(view -> {

            if (sharePref.GetData(DataManager.RegistrationStartDate) == null && sharePref.GetData(DataManager.RegistrationStartTime) == null) {
                Toast.SetToast(SendEmployeeSheduleTime.this, getResources().getString(R.string.Select_registration_start_date_and_time));
            } else if (sharePref.GetData(DataManager.RegistrationEndDate) == null && sharePref.GetData(DataManager.RegistrationEndTime) == null) {
                Toast.SetToast(SendEmployeeSheduleTime.this, getResources().getString(R.string.Select_registration_end_date_and_time));
            } else {

                var comparingTimeDate = new ComparingTimeDate();
              //  comparingTimeDate.ComparingDate(sharePref.GetData(DataManager.RegistrationStartDate), sharePref.GetData(DataManager.RegistrationEndDate));
                comparingTimeDate.ComparingDate(sharePref.GetData(DataManager.RegistrationStartDate), sharePref.GetData(DataManager.RegistrationEndDate), SendEmployeeSheduleTime.this);
                comparingTimeDate.ComparingTime(sharePref.GetData(DataManager.RegistrationStartTime), sharePref.GetData(DataManager.RegistrationEndTime));

                comparingTimeDate.OnTimeEvent(val -> {
                   ComparingTime  = val;
                    CheckTimeDate();
                });
                comparingTimeDate.OnDateEvent(val -> {
                    ComparingDate = val;
                    CheckTimeDate();
                });


                Log.d("TAG", "End "+sharePref.GetData(DataManager.RegistrationEndDate));
                Log.d("TAG", "Start "+sharePref.GetData(DataManager.RegistrationStartDate));
                Log.d("TAG", String.valueOf(ComparingDate));


            }

        });
    }

    private void CheckTimeDate(){
        if (ComparingDate.equals(DataManager.RegistrationFirstDateOccursAfterEndDate)) {
            Toast.SetToast(SendEmployeeSheduleTime.this, "Start date is small");
        } else if (ComparingDate.equals(DataManager.RegistrationDateIsSame)) {
            if(ComparingTime.equals(DataManager.RegistrationFirstTimeOccursAfterEndTime)){
                Toast.SetToast(SendEmployeeSheduleTime.this, "time error");
            }else if(ComparingTime.equals(DataManager.RegistrationTimeSame)){
                Toast.SetToast(SendEmployeeSheduleTime.this, "time is same");
            }else {
               HandleActivity.GotoSuccessfullySendSchedule(SendEmployeeSheduleTime.this);
            }
        }else {
            HandleActivity.GotoSuccessfullySendSchedule(SendEmployeeSheduleTime.this);
        }

    }

    @Override
    public void onBackPressed() {
        finish();
        Animatoo.animateSlideRight(SendEmployeeSheduleTime.this);
    }
}