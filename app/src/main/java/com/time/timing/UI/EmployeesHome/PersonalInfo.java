package com.time.timing.UI.EmployeesHome;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.time.timing.Network.ViewModel.ViewModel;
import com.time.timing.R;
import com.time.timing.Utils.Toast;
import com.time.timing.databinding.PersonalinfoBinding;

public class PersonalInfo extends Fragment {

    private PersonalinfoBinding binding;
    private ViewModel viewModel;

    public PersonalInfo() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.personalinfo, container, false);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);


        GetProfileData();
        GetAllPhoneNumber();
        return binding.getRoot();
    }

    private void GetAllPhoneNumber(){
      viewModel.GetRequestScheduleDocument().observe(getActivity(), s -> {
          if (s != null) {
              Toast.SetToast(getActivity(), s);
          }
      });
    }

    private void GetProfileData(){
        viewModel.EmployeesProfileData().observe(getActivity(), employeesModel -> {
            if(employeesModel != null){
                binding.setUser(employeesModel);
            }
        });
    }


}