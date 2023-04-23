package com.time.timing.UI.ManagerFragement;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.time.timing.DateModel;
import com.time.timing.Network.ViewModel.RefuseUserViewModel;
import com.time.timing.Network.ViewModel.ViewModel;
import com.time.timing.R;
import com.time.timing.UI.Adapter.RefuseUserAdapter;
import com.time.timing.Utils.GetWeeksName;
import com.time.timing.databinding.NotconfrimBinding;

import java.util.ArrayList;
import java.util.List;

public class NotConfrim extends Fragment {

    private NotconfrimBinding binding;
    private ViewModel viewModel;
    private RefuseUserAdapter refuseUserAdapter;
    private List<DateModel> list = new ArrayList<>();
    private RefuseUserViewModel refuseUserViewModel;

    public NotConfrim() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.notconfrim, container, false);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        refuseUserViewModel = new ViewModelProvider(this).get(RefuseUserViewModel.class);

        InitView();
        SetNextWeek();
        GetRefuseUser();
        SearchUser();
        return binding.getRoot();
    }

    private void InitView(){
        refuseUserAdapter = new RefuseUserAdapter();
        binding.SearchNotConfirmRecyclerView.setHasFixedSize(true);
        binding.SearchNotConfirmRecyclerView.setAdapter(refuseUserAdapter);
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

        viewModel.GetRefuseUsers(StartDate, LastDate).observe(getActivity(), refuseModels -> {
            
            refuseUserViewModel.InsertRefuseUser(refuseModels);
        });
    }

    private void GetRefuseUser(){
        refuseUserViewModel.GetRefuseUser().observe(getActivity(), refuseModels -> {
            if(refuseModels != null){
                refuseUserAdapter.setList(refuseModels);
                refuseUserAdapter.notifyDataSetChanged();
                if(refuseModels.size() > 0){
                    HaveData();
                }else {
                    NoData();
                }
            }
        });
    }

    private void SearchUser(){
        binding.SearchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                var Name = editable.toString();
                if(Name.isEmpty()){
                    GetRefuseUser();
                }else {
                    SearchByName(Name);
                }
            }
        });
    }

    private void SearchByName(String Name){
        refuseUserViewModel.SearchByName(Name).observe(getActivity(), refuseModels -> {
            if(refuseModels != null){
                refuseUserAdapter.setList(refuseModels);
                refuseUserAdapter.notifyDataSetChanged();
                if(refuseModels.size() > 0){
                    HaveData();
                }else {
                    NoData();
                }
            }
        });
    }

    private void HaveData(){
        binding.Message.setVisibility(View.GONE);
        binding.Icon.setVisibility(View.GONE);
    }
    private void NoData(){
        binding.Message.setVisibility(View.VISIBLE);
        binding.Icon.setVisibility(View.VISIBLE);
    }

}