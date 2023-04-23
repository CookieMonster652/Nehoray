package com.time.timing.UI.ManagerFragement;

import android.os.Build;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.time.timing.DateModel;
import com.time.timing.Network.ViewModel.ViewModel;
import com.time.timing.R;
import com.time.timing.UI.Adapter.NextWeekAdapter;
import com.time.timing.Utils.GetWeeksName;
import com.time.timing.Utils.HandleActivity;
import com.time.timing.Utils.SpacingItemDecorator;
import com.time.timing.databinding.DailyscheduleBinding;

import java.util.ArrayList;
import java.util.List;

public class DailySchedule extends Fragment {

    private DailyscheduleBinding binding;
    private NextWeekAdapter adapter;
    private List<DateModel> list = new ArrayList<>();
    private ViewModel viewModel;

    public DailySchedule() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.dailyschedule, container, false);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);


        SetNextWeek();
        GetDataFromServer();

        return binding.getRoot();
    }


    private void GetDataFromServer() {
        viewModel.ManagerProfileData().observe(getActivity(), managerProfileModel -> {
            if (managerProfileModel != null) {
                binding.setUser(managerProfileModel);
            }
        });
    }

    private void SetNextWeek() {
        binding.RecyclerViewDate.setHasFixedSize(true);
        adapter = new NextWeekAdapter();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            for (int i = 0; i < GetWeeksName.getDaysOfWeek().length; i++) {
                var mydate = GetWeeksName.getDaysOfWeek()[i];
                var dateModel = new DateModel();
                dateModel.setDate(String.valueOf(mydate));
                list.add(dateModel);
                adapter.setList(list);
                binding.RecyclerViewDate.setAdapter(adapter);
                var spacingItemDecorator = new SpacingItemDecorator(0,0,2,0);
                binding.RecyclerViewDate.addItemDecoration(spacingItemDecorator);

                adapter.OnClickEvent(SelectDate -> {
                    HandleActivity.GotoSelectShift(getActivity(), SelectDate);
                });
            }

        }

    }

}