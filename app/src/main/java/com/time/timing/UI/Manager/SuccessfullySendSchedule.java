package com.time.timing.UI.Manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.time.timing.Data.DataManager;
import com.time.timing.Data.MarkContact;
import com.time.timing.DateModel;
import com.time.timing.Network.ViewModel.ViewModel;
import com.time.timing.R;
import com.time.timing.UI.ManagerHome.ManagerHome;
import com.time.timing.UI.Widget.ProgressDialog;
import com.time.timing.Utils.DateToDayName;
import com.time.timing.Utils.GetWeeksName;
import com.time.timing.Utils.SharePref;
import com.time.timing.databinding.SuccessfullysendscheduleBinding;

import java.util.ArrayList;
import java.util.List;

public class SuccessfullySendSchedule extends AppCompatActivity {

    private SuccessfullysendscheduleBinding binding;
    private SharePref sharePref;
    private ViewModel viewModel;
    private List<DateModel> list = new ArrayList<>();
    private String StartDate, EndDate;
    private ProgressDialog progressDialog = new ProgressDialog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.successfullysendschedule);
        sharePref = new SharePref(SuccessfullySendSchedule.this);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);





        SetNextWeek();
        InitView();
    }

    private void SetNextWeek() {
        for (int i = 0; i < GetWeeksName.getDaysOfWeek().length; i++) {
            var mydate = GetWeeksName.getDaysOfWeek()[i];
            var dateModel = new DateModel();
            dateModel.setDate(String.valueOf(mydate));
            list.add(dateModel);
        }
         StartDate = list.get(0).getDate();
         EndDate = list.get(list.size() - 1).getDate();
    }

    private void InitView() {
        binding.Toolbar.Title.setText(getResources().getString(R.string.successfully_sent));
        binding.Toolbar.BackButton.setOnClickListener(view -> {
            finish();
            Animatoo.animateSlideRight(SuccessfullySendSchedule.this);
        });

        var RegistrationStartDate = sharePref.GetData(DataManager.RegistrationStartDate);
        var RegistrationEndDate = sharePref.GetData(DataManager.RegistrationEndDate);

        var RegistrationStartTime = sharePref.GetData(DataManager.RegistrationStartTime);
        var RegistrationEndTime = sharePref.GetData(DataManager.RegistrationEndTime);

        binding.Time.setText(DateToDayName.GetDate(RegistrationEndDate) + " " + RegistrationEndTime);
        var date = GetWeeksName.getDaysOfWeek();


        binding.ConfirmButton.setOnClickListener(view -> {
            progressDialog.ShowProgress(SuccessfullySendSchedule.this);
            var SelectedNumber = MarkContact.GetContact();
            for (var data : SelectedNumber) {
                for (var getDate : date) {
                    viewModel.SendScheduleRequest(data.getName(), data.getPhoneNumber(), getDate, RegistrationStartTime, RegistrationStartDate, RegistrationEndTime, RegistrationEndDate).observe(this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            if(aBoolean){
                                GotoHome();
                            }else {

                            }
                        }
                    });

                    viewModel.SendMySchedule(data.getName(), data.getPhoneNumber(), getDate, RegistrationStartTime, RegistrationStartDate, RegistrationEndTime, RegistrationEndDate).observe(this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            if(aBoolean){

                            }else {

                            }
                        }
                    });

                    viewModel.MyUsersRegisShift(StartDate, EndDate, data.getPhoneNumber(), data.getName(), data.getUID()).observe(this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            if(aBoolean){

                            }else {

                            }
                        }
                    });

                }
            }

        });
    }


    private void GotoHome(){
        var intent = new Intent(this, ManagerHome.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }



    @Override
    public void onBackPressed() {
        finish();
        Animatoo.animateSlideRight(SuccessfullySendSchedule.this);
    }

}