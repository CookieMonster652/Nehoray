package com.time.timing.UI.Manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.time.timing.Data.DataManager;
import com.time.timing.DateModel;
import com.time.timing.Network.ViewModel.ViewModel;
import com.time.timing.R;
import com.time.timing.ShiftModel;
import com.time.timing.UI.Adapter.CurrentWeekAdapter;
import com.time.timing.Utils.GetWeeksName;
import com.time.timing.Utils.HandleActivity;
import com.time.timing.databinding.CurrentweekBinding;

import java.util.ArrayList;
import java.util.List;

public class CurrentWeek extends AppCompatActivity {

    private CurrentweekBinding binding;
    private CurrentWeekAdapter adapter;
    private List<DateModel> list = new ArrayList<>();
    private int mapping = 100;
    private List<ShiftModel> data;
    private Bundle bundle;
    private static final String TAG = "CurrentWeek";
    private ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.currentweek);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        adapter = new CurrentWeekAdapter();
        bundle = getIntent().getExtras();

        InitView();
        GetCurrentWeek();
    }


    private void InitView() {
        binding.Toolbar.Title.setText(getResources().getString(R.string.Select_Days));
        binding.Toolbar.BackButton.setOnClickListener(view -> {
            finish();
            Animatoo.animateSlideRight(CurrentWeek.this);
        });
        binding.ConfirmButton.setOnClickListener(view -> {
            List<DateModel> models = adapter.getList();
            if (models != null) {
                for (DateModel ds : models) {
                    if (ds.isIsCheck()) {
                        if (bundle != null) {
                            data = bundle.getParcelableArrayList(DataManager.Data);
                            for (var ShiftModel : data) {
                                Log.d(TAG, String.valueOf(ShiftModel.getShift()));
                               viewModel.SendSchedule(ds.getDate(), ShiftModel.getShift(), ShiftModel.getShiftStartTime(), ShiftModel.getShiftEndTime(), ShiftModel.getNumberOFWorkerOnTheShift(), ShiftModel.getAdditionalHoursAdd(), ShiftModel.getNumberOFWorkerOnTheShift()).observe(this, new Observer<Boolean>() {
                                   @Override
                                   public void onChanged(Boolean aBoolean) {

                                   }
                               });

                            }
                        }
                        HandleActivity.GotoScheduleList(CurrentWeek.this);
                        Animatoo.animateSlideLeft(CurrentWeek.this);
                    }
                }
            }
        });
    }



    private void GetCurrentWeek() {
        binding.RecyclerView.setHasFixedSize(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            for (int i = 0; i < GetWeeksName.getDaysOfWeek().length; i++) {
                String mydate = GetWeeksName.getDaysOfWeek()[i];
                DateModel dateModel = new DateModel();
                dateModel.setDate(String.valueOf(mydate));
                list.add(dateModel);
                adapter.setList(list);
                binding.RecyclerView.setAdapter(adapter);

                adapter.OnClickEvent(SelectDate -> {

                });
            }

        }
    }


}