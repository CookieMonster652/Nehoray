package com.time.timing.UI.ManagerFragement;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.time.timing.DateModel;
import com.time.timing.Network.ViewModel.ManageWorkersViewModel;
import com.time.timing.Network.ViewModel.ViewModel;
import com.time.timing.R;
import com.time.timing.UI.Adapter.ManageWorkersUserAdapter;
import com.time.timing.MyScheduleContact;
import com.time.timing.Utils.DateToDayName;
import com.time.timing.Utils.GetWeeksName;
import com.time.timing.Utils.HandleActivity;
import com.time.timing.Utils.Permission;
import com.time.timing.Utils.SpacingItemDecorator;
import com.time.timing.databinding.ManageworkersBinding;

import java.util.ArrayList;
import java.util.List;

public class ManageWorkers extends Fragment {

    public static final String TAG = "ManageWorkers";
    private ManageworkersBinding binding;
    private static final int PermissionCode = 100;
    private List<MyScheduleContact> myScheduleContacts = new ArrayList<>();
    private ViewModel viewModel;
    private ManageWorkersViewModel manageWorkersViewModel;
    private List<DateModel> list = new ArrayList<>();

    public ManageWorkers() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.manageworkers, container, false);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        manageWorkersViewModel = new ViewModelProvider(this).get(ManageWorkersViewModel.class);

        InitView();
        GetData();
        SetNextWeek();

        return binding.getRoot();
    }

    private void SetNextWeek() {
        for (int i = 0; i < GetWeeksName.getDaysOfWeek().length; i++) {
            var mydate = GetWeeksName.getDaysOfWeek()[i];
            var dateModel = new DateModel();
            dateModel.setDate(String.valueOf(mydate));
            list.add(dateModel);
        }
        var StartDate = list.get(0).getDate();
        var LastDate = list.get(list.size() - 1).getDate();
        binding.Date.setText(getResources().getString(R.string.invite_workers_for_this_schedule) + "\n" + DateToDayName.GetDate(StartDate) + " - " + DateToDayName.GetDate(LastDate));
    }

    private void GetData() {
        manageWorkersViewModel.GetMySchedule().observe(getActivity(), myScheduleContacts -> {
            if (myScheduleContacts != null) {
                var adapter = new ManageWorkersUserAdapter();
                adapter.setList(myScheduleContacts);
                binding.EmployeesRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                adapter.OnClickEvent(PhoneNumber -> {
                    manageWorkersViewModel.DeleteUser(PhoneNumber);
                });

                if (myScheduleContacts.size() == 0) {
                    binding.Message.setVisibility(View.VISIBLE);
                    binding.Icon.setVisibility(View.VISIBLE);
                    binding.SendToMyList.setBackgroundResource(R.drawable.buttoninvisiblebg);
                } else {
                    binding.Message.setVisibility(View.GONE);
                    binding.Icon.setVisibility(View.GONE);
                    binding.SendToMyList.setBackgroundResource(R.drawable.button_bg);

                    binding.SendToMyList.setOnClickListener(view -> {
                        HandleActivity.GotoSendEmployeesScheduleSelectTime(getActivity());
                    });
                }
            } else {
            }
        });
    }


    private void InitView() {
        var spacingItemDecorator = new SpacingItemDecorator(10, 10, 10, 10);
        binding.EmployeesRecyclerView.setHasFixedSize(true);
        binding.EmployeesRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        binding.EmployeesRecyclerView.addItemDecoration(spacingItemDecorator);

        binding.AddEmployees.setOnClickListener(view -> {
            if (Permission.ReadContact(getActivity(), PermissionCode)) {
                HandleActivity.GotoAdEmployees(getActivity());
            }
        });
    }


}