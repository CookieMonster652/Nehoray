package com.time.timing.UI.ManagerSignIn;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.time.timing.Data.DataManager;
import com.time.timing.R;
import com.time.timing.ShiftModel;
import com.time.timing.UI.Adapter.AddShiftAdapter;
import com.time.timing.Utils.DateToDayName;
import com.time.timing.Utils.HandleActivity;
import com.time.timing.databinding.SelectshiftBinding;
import com.time.timing.databinding.ShiftinfodialogBinding;

import java.util.ArrayList;
import java.util.List;

public class SelectShift extends AppCompatActivity {

    private SelectshiftBinding binding;
    private String CurrentDate;
    private List<ShiftModel> shiftModel = new ArrayList<>();
    private AddShiftAdapter adapter;
    private int Count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.selectshift);
        adapter = new AddShiftAdapter();

        CurrentDate = getIntent().getStringExtra(DataManager.Data);
        if(CurrentDate != null){
            binding.setSetDateText(DateToDayName.GetDate(CurrentDate));
        }

        InitView();
        SetData();
    }



    private void InitView(){
        binding.Toolbar.BackButton.setOnClickListener(view -> {
            finish();
            Animatoo.animateSlideRight(SelectShift.this);
        });
        binding.Toolbar.Title.setText(getResources().getString(R.string.Select_Shift));

        binding.ApplyAdditionalShift.setOnClickListener(view -> {
          List<ShiftModel> newmodel = adapter.getList();
          for(ShiftModel shiftModel : newmodel){
              if(shiftModel != null){
                  String ShiftStartTime = shiftModel.getShiftStartTime();
                  String ShiftEndTime = shiftModel.getShiftEndTime();
                  String NumberOfWorkerOnTheShift = shiftModel.getNumberOFWorkerOnTheShift();
                  String AdditionalHours = shiftModel.getAdditionalHoursAdd();
                  String MinimumShiftParWorker = shiftModel.getMinimumShiftParWorker();

                  if(ShiftStartTime == null){
                      com.time.timing.Utils.Toast.SetToast(getApplicationContext(), getResources().getString(R.string.Shift_start_time_empty));
                  }else if(ShiftEndTime == null){
                      com.time.timing.Utils.Toast.SetToast(getApplicationContext(), getResources().getString(R.string.Shift_end_time_empty));
                  }else if(NumberOfWorkerOnTheShift == null){
                      com.time.timing.Utils.Toast.SetToast(getApplicationContext(), getResources().getString(R.string.Number_of_worker_empty));
                  }else if(AdditionalHours == null){
                      com.time.timing.Utils.Toast.SetToast(getApplicationContext(), getResources().getString(R.string.Additional_hours_empty));
                  }else if(MinimumShiftParWorker == null){
                      com.time.timing.Utils.Toast.SetToast(getApplicationContext(), getResources().getString(R.string.Minimum_shift_par_worker_empty));
                  }else {
                      Goto_CurrentWeek(newmodel);
                  }


              }
          }
        });

        binding.ShowWeeklyScheduleButton.setOnClickListener(view -> {
            finish();
            Animatoo.animateSlideRight(SelectShift.this);
        });
    }

    private void Goto_CurrentWeek(List<ShiftModel> newmodel){
        HandleActivity.GotoCurrentWeek(SelectShift.this, newmodel);
        Animatoo.animateSlideLeft(SelectShift.this);
    }

    private void SetData(){
        long TimesTamp = System.currentTimeMillis();
        adapter = new AddShiftAdapter();
        binding.RecyclerViewAddShift.setHasFixedSize(true);
        ShiftModel First = new ShiftModel();
        First.setShift(Count);
        shiftModel.add(First);
        adapter.setList(shiftModel);
        binding.RecyclerViewAddShift.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.OnClickInfoEvent(() -> {
            OpenDialog();
        });

        binding.ApplyShift.setOnClickListener(view -> {
            Count ++;
            long UpdateTime = System.currentTimeMillis();
            ShiftModel Second = new ShiftModel();
            Second.setTimestamp(UpdateTime);
            Second.setShift(Count);
            shiftModel.add(Second);
            binding.RecyclerViewAddShift.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            binding.RecyclerViewAddShift.scrollToPosition(adapter.getItemCount() - 1);
        });

    }

    private void OpenDialog(){
        AlertDialog.Builder Mbuilder = new AlertDialog.Builder(SelectShift.this);
        ShiftinfodialogBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.shiftinfodialog, null, false);
        Mbuilder.setView(binding.getRoot());

        AlertDialog alertDialog = Mbuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        binding.CancelButton.setOnClickListener(view -> {
            alertDialog.dismiss();
        });
        binding.ConfirmButton.setOnClickListener(view -> {
            alertDialog.dismiss();
        });
    }


    @Override
    public void onBackPressed() {
        finish();
        Animatoo.animateSlideRight(SelectShift.this);
    }
}