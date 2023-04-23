package com.time.timing.UI.Manager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;

import com.time.timing.Data.DataManager;
import com.time.timing.DateModel;
import com.time.timing.Model.AdditionHoursTimeModel;
import com.time.timing.Model.AllRegisterUserDataModel;
import com.time.timing.Model.HoursTimeModel;
import com.time.timing.Model.RefuseUserModel;
import com.time.timing.MyScheduleModel;
import com.time.timing.Network.ViewModel.AllRegisterUserViewModel;
import com.time.timing.Network.ViewModel.ViewModel;
import com.time.timing.R;
import com.time.timing.UI.Adapter.AdditionalHoursScheduleUserAdapter;
import com.time.timing.UI.Adapter.AllRegisterUserShiftAdapter;
import com.time.timing.UI.Adapter.MyScheduleEmployeesAdapter;
import com.time.timing.UI.Adapter.OutAdditionTimeAdapter;
import com.time.timing.UI.Adapter.ScheduleAdditionHoursAdapter;
import com.time.timing.UI.ManagerRequest.ShiftNameAdapter;
import com.time.timing.UI.ManagerRequest.ZeroShiftAdapter;
import com.time.timing.UI.Widget.ProgressDialog;
import com.time.timing.Utils.DateToDayName;
import com.time.timing.Utils.GetWeeksName;
import com.time.timing.Utils.SpacingItemDecorator;
import com.time.timing.Utils.Toast;
import com.time.timing.databinding.AllregisteruserdialogBinding;
import com.time.timing.databinding.MovedialogBinding;
import com.time.timing.databinding.MyscheduleBinding;
import com.time.timing.databinding.RequestdialogBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MySchedule<HoursTimeScheduleAdapter> extends Fragment {

    private MyscheduleBinding binding;
    private List<DateModel> list = new ArrayList<>();
    private static final String TAG = "MySchedule";
    private int CurrentPosition = 0;
    private ViewModel viewModel;
    private String[] Schedule;
    private MyScheduleEmployeesAdapter userAdapter;
    private CollectionReference MyScheduleRef;
    private FirebaseAuth Mauth;
    private List<MyScheduleModel> myScheduleModels = new ArrayList<>();
    private ShiftNameAdapter shiftNameAdapter;
    private BroadcastReceiver broadcastReceiver;
    private String ShiftName = null;
    private String Date = null;
    private RadioButton radioButton;
    private int radioid;
    private AdditionalHoursScheduleUserAdapter additionalHoursScheduleUserAdapter;
    private ProgressDialog progressDialog = new ProgressDialog();
    private ScheduleAdditionHoursAdapter scheduleAdditionHoursAdapter;
    private List<AdditionHoursTimeModel> additionHoursTimeModelList = new ArrayList<>();
    private List<HoursTimeModel> hoursTimeModelslist = new ArrayList<>();
    private OutAdditionTimeAdapter outAdditionTimeAdapter = new OutAdditionTimeAdapter();
    private String hours;

    private AllRegisterUserShiftAdapter allRegisterUserShiftAdapter = new AllRegisterUserShiftAdapter();
    private AllRegisterUserViewModel allRegisterUserViewModel;
    private String Sunday, Saturday;

    public MySchedule() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.myschedule, container, false);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        allRegisterUserViewModel = new ViewModelProvider(this).get(AllRegisterUserViewModel.class);
        GetNextWeek();
        SetNextWeek();
        InitView();

        Dialog();
        SearchView();

        return binding.getRoot();
    }


    private void GetNextWeek() {
        for (int i = 0; i < GetWeeksName.getDaysOfWeek().length; i++) {
            var dateModel = new DateModel();
            var mydate = GetWeeksName.getDaysOfWeek()[i];
            dateModel.setDate(String.valueOf(mydate));
            list.add(dateModel);
        }


        Sunday = list.get(0).getDate();

        var Monday = list.get(1).getDate();

        var Tuesday = list.get(2).getDate();

        var Wednesday = list.get(3).getDate();

        var Thursday = list.get(4).getDate();

        var Friday = list.get(5).getDate();

        Saturday = list.get(list.size() - 1).getDate();
    }

    private void SearchView() {
        allRegisterUserViewModel.DeleteAllData();
        binding.SearchInput.setOnClickListener(view -> {
            var dialog = new AlertDialog.Builder(getActivity());
            AllregisteruserdialogBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.allregisteruserdialog, null, false);
            dialog.setView(binding.getRoot());
            binding.RegisterUserRV.setHasFixedSize(true);
            var space = new SpacingItemDecorator(10, 10, 10, 10);
            binding.RegisterUserRV.addItemDecoration(space);
            binding.RegisterUserRV.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            binding.RegisterUserRV.setAdapter(allRegisterUserShiftAdapter);
            viewModel.AllRegisterUser(Sunday, Saturday).observe(getActivity(), new Observer<List<AllRegisterUserDataModel>>() {
                @Override
                public void onChanged(List<AllRegisterUserDataModel> allRegisterUserDataModels) {

                    allRegisterUserViewModel.InsertAllRegisterUser(allRegisterUserDataModels);

                }
            });


            allRegisterUserViewModel.GetAllRegisterUserData().observe(getActivity(), new Observer<List<AllRegisterUserDataModel>>() {
                @Override
                public void onChanged(List<AllRegisterUserDataModel> allRegisterUserDataModels) {
                    allRegisterUserShiftAdapter.setList(allRegisterUserDataModels);
                    allRegisterUserShiftAdapter.notifyDataSetChanged();
                }
            });

            var alertdialog = dialog.create();
            alertdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertdialog.show();


            allRegisterUserShiftAdapter.OnClickEvent(allRegisterUserDataModel -> {
                if (allRegisterUserDataModel != null) {
                    if (allRegisterUserDataModel.getShiftCount().equals("0")) {
                        ImplementZeroShift(allRegisterUserDataModel.getName(), allRegisterUserDataModel.getPhone(), allRegisterUserDataModel.getUID());
                    } else {
                        RequestOtherShift(allRegisterUserDataModel.getName(), allRegisterUserDataModel.getPhone(), allRegisterUserDataModel.getUID());
                    }
                }
            });

            binding.SearchInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    var name = editable.toString();
                    if (name.isEmpty()) {
                        GetAllData();
                    } else {
                        SearchByName(name);
                    }
                }
            });

        });
    }

    private void GetAllData() {
        allRegisterUserViewModel.GetAllRegisterUserData().observe(getActivity(), new Observer<List<AllRegisterUserDataModel>>() {
            @Override
            public void onChanged(List<AllRegisterUserDataModel> allRegisterUserDataModels) {
                allRegisterUserShiftAdapter.setList(allRegisterUserDataModels);
                allRegisterUserShiftAdapter.notifyDataSetChanged();
            }
        });
    }

    private void SearchByName(String Name) {
        allRegisterUserViewModel.SearchByName(Name).observe(this, allRegisterUserDataModels -> {
            allRegisterUserShiftAdapter.setList(allRegisterUserDataModels);
            allRegisterUserShiftAdapter.notifyDataSetChanged();
        });
    }


    private void SetAdditionHoursTimer(String AdditionHours, String ShiftStartDate, String ShiftEndDate) {
        additionHoursTimeModelList.clear();
        scheduleAdditionHoursAdapter = new ScheduleAdditionHoursAdapter();
        binding.AdditionHoursTimeRV.setHasFixedSize(true);

        binding.HoursRV.setHasFixedSize(true);
        binding.HoursRV.setAdapter(outAdditionTimeAdapter);


        int i = 0;
        String myTime = ShiftStartDate;
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        java.util.Date d = null;
        try {
            d = df.parse(myTime);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        for (i = 0; i <= Integer.valueOf(AdditionHours); i++) {
            if (i == 0) {
                cal.add(Calendar.HOUR, 0);
            } else {
                cal.add(Calendar.HOUR, 1);
            }

            String newTime = df.format(cal.getTime());
            var model = new AdditionHoursTimeModel();
            model.setTimeHour(newTime);
            additionHoursTimeModelList.add(model);
            scheduleAdditionHoursAdapter.setList(additionHoursTimeModelList);
        }
        binding.AdditionHoursTimeRV.setAdapter(scheduleAdditionHoursAdapter);


        SimpleDateFormat myFormat = new SimpleDateFormat("HH:mm");
        String inputString1 = String.valueOf(additionHoursTimeModelList.get(additionHoursTimeModelList.size() - 2).getTimeHour());
        String inputString2 = ShiftEndDate;

        try {
            Date date1 = myFormat.parse(inputString1);
            Date date2 = myFormat.parse(inputString2);
            long diff = date2.getTime() - date1.getTime();
            System.out.println("Days: " + TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS));
            hours = String.valueOf(TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS));


        } catch (ParseException e) {
            e.printStackTrace();
        }


        //todo bottom apply
        hoursTimeModelslist.clear();
        int it = 0;
        String myTimes = String.valueOf(additionHoursTimeModelList.get(additionHoursTimeModelList.size() - 1).getTimeHour());
        SimpleDateFormat dfs = new SimpleDateFormat("HH:mm");
        java.util.Date ss = null;
        try {
            ss = dfs.parse(myTimes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar cals = Calendar.getInstance();
        cals.setTime(ss);
        for (it = 0; it <= Integer.valueOf(hours); it++) {

            if (it == 0) {
                cals.add(Calendar.HOUR, 0);
            } else {
                cals.add(Calendar.HOUR, 1);

            }

            String newTime = dfs.format(cals.getTime());
            var my = new HoursTimeModel();
            my.setTimeHour(newTime);
            hoursTimeModelslist.add(my);
            outAdditionTimeAdapter.setList(hoursTimeModelslist);
        }
    }

    private void Dialog() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (ShiftName != null || Date != null) {
                    viewModel.GetMySingleSchedule(Date, ShiftName).observe(getActivity(), new Observer<MyScheduleModel>() {
                        @Override
                        public void onChanged(MyScheduleModel myScheduleModel) {
                            if (myScheduleModel != null) {
                                var Name = intent.getStringExtra(DataManager.Name);
                                var UID = intent.getStringExtra(DataManager.UID);
                                var Phone = intent.getStringExtra(DataManager.Phone);
                                //todo Toast.SetToast(getActivity(), Phone);
                                viewModel.IsScheduleUserExists(UID, Date, ShiftName).observe(getActivity(), new Observer<Boolean>() {
                                    @Override
                                    public void onChanged(Boolean aBoolean) {
                                        if (aBoolean) {
                                            Toast.SetToast(getActivity(), getResources().getString(R.string.already_exists_shift));
                                        } else {
                                            var dialog = new BottomSheetDialog(getActivity());
                                            RequestdialogBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.requestdialog, null, false);
                                            binding.AdditionalHoursRadioButton.setText(myScheduleModel.getAdditional_hours_you_want() + " Addition hours");
                                            binding.ShiftStartTimeAndEndTimeRadioButton.setText(myScheduleModel.getShift_start_time() + " - " + myScheduleModel.getShift_end_time());
                                            dialog.setContentView(binding.getRoot());
                                            binding.DateShift.setText(ShiftName + " " + getResources().getString(R.string.Shift) + " " + DateToDayName.GetDate(Date));
                                            dialog.show();

                                            binding.CancelButton.setOnClickListener(view -> {
                                                dialog.dismiss();
                                            });
                                            binding.RequestButton.setOnClickListener(view -> {
                                                radioid = binding.TimeRadioGroupButton.getCheckedRadioButtonId();
                                                radioButton = dialog.findViewById(radioid);
                                                if (radioButton != null) {
                                                    if (radioButton.getText().toString().isEmpty()) {
                                                        Toast.SetToast(getActivity(), getResources().getString(R.string.Select_hours_you_want));
                                                    } else {
                                                        if (radioButton.getText().toString().equals(myScheduleModel.getAdditional_hours_you_want() + " Addition hours")) {
                                                            // todo ?
                                                            SendRequest(myScheduleModel, Name, Phone, UID, true);
                                                        } else {
                                                            SendRequest(myScheduleModel, Name, Phone, UID, false);
                                                        }

                                                    }
                                                } else {
                                                    Toast.SetToast(getActivity(), getResources().getString(R.string.Select_hours_you_want));
                                                }
                                            });
                                        }
                                    }
                                });


                            }
                        }
                    });

                } else {
                    Toast.SetToast(getActivity(), getResources().getString(R.string.empty_shift_message));
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, new IntentFilter("ReceiverMessage"));
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);

    }

    private void CreateSpinner() {
        if (Schedule != null) {
            var arrayadapter = new ArrayAdapter<>(getActivity(), R.layout.shiftspinneritem, R.id.ShiftName, Schedule);
            binding.ShiftSpinner.setAdapter(arrayadapter);
            binding.ShiftSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    GetMySchedule(list.get(CurrentPosition).getDate(), adapterView.getItemAtPosition(i).toString());
                    GetMyRefuseSchedule(list.get(CurrentPosition).getDate(), adapterView.getItemAtPosition(i).toString());
                    ShiftName = adapterView.getItemAtPosition(i).toString();
                    Date = list.get(CurrentPosition).getDate();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }
    }


    private void InitView() {
        additionalHoursScheduleUserAdapter = new AdditionalHoursScheduleUserAdapter();
        binding.RefuseUserRecyclerView.setHasFixedSize(true);
        binding.RefuseUserRecyclerView.setAdapter(additionalHoursScheduleUserAdapter);
        binding.RefuseUserRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        var space = new SpacingItemDecorator(10, 10, 10, 10);
        binding.RefuseUserRecyclerView.addItemDecoration(space);

        binding.RegistrationUseListRecyclerView.setAdapter(userAdapter);
        binding.RegistrationUseListRecyclerView.addItemDecoration(space);

        userAdapter = new MyScheduleEmployeesAdapter();
        binding.RegistrationUseListRecyclerView.setHasFixedSize(true);
        var spacingItemDecorator = new SpacingItemDecorator(10, 10, 10, 10);
        binding.RegistrationUseListRecyclerView.addItemDecoration(spacingItemDecorator);
        binding.RegistrationUseListRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        binding.RegistrationUseListRecyclerView.setAdapter(userAdapter);


        binding.NextShiftBtn.setOnClickListener(view -> {
            if (CurrentPosition < list.size() - 1) {
                ++CurrentPosition;
                binding.Date.setText(DateToDayName.GetDate(list.get(CurrentPosition).getDate()));
                Log.d(TAG, list.get(CurrentPosition).getDate());
                GetMyScheduleShift(list.get(CurrentPosition).getDate());
            }
        });

        binding.PreviousShiftBtn.setOnClickListener(view -> {
            if (CurrentPosition > 0) {
                --CurrentPosition;
                binding.Date.setText(DateToDayName.GetDate(list.get(CurrentPosition).getDate()));
                Log.d(TAG, String.valueOf(CurrentPosition));
                Log.d(TAG, String.valueOf(list.size()));
                GetMyScheduleShift(list.get(CurrentPosition).getDate());
            }
        });

        //binding.Date.setText(DateToDayName.GetDate(list.get(1).getDate()));


        userAdapter.OnClickLisiner(userModel -> {
            var Dialog = new AlertDialog.Builder(getActivity());
            var binding = MovedialogBinding.inflate(getLayoutInflater(), null, false);
            Dialog.setView(binding.getRoot());

            var alertdialog = Dialog.create();
            alertdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertdialog.show();
            binding.SendRequest.setOnClickListener(view -> {
                alertdialog.dismiss();
                progressDialog.ShowProgress(getActivity());
                viewModel.ModeToAdditionalHours(userModel.getDate(), userModel.getShiftName(), userModel.getSenderUID())
                        .observe(this, new Observer<Boolean>() {
                            @Override
                            public void onChanged(Boolean aBoolean) {
                                if(aBoolean){
                                    progressDialog.CancelDialog();
                                }else {
                                    progressDialog.CancelDialog();
                                }
                            }
                        });
            });
        });
    }


    private void GetMyScheduleShift(String Date) {
        Schedule = null;

        viewModel.GetMyScheduleList(Date).observe(getActivity(), myScheduleModels -> {
            if (myScheduleModels != null) {
                Schedule = new String[myScheduleModels.size()];

                for (int i = 0; i < myScheduleModels.size(); i++) {
                    Schedule[i] = myScheduleModels.get(i).getShiftName();
                }

                CreateSpinner();
            }
        });

    }


    private void GetEmployeesSize(String Date, String ShiftName) {
        binding.ProgressID.setMax(0);
        binding.ProgressID.setProgress(0);
        viewModel.GetMySingleSchedule(Date, ShiftName).observe(this, myScheduleModel -> {

            if (myScheduleModel != null) {
                var RegistrationUser = myScheduleModel.getRegistrationUsers();
                var NumberOFWorkerThisShift = myScheduleModel.getNumber_of_workers_for_this_shift();

                binding.ProgressID.setMax(Integer.valueOf(NumberOFWorkerThisShift));
                binding.ProgressID.setProgress(Integer.valueOf(RegistrationUser));

                var ratio = Integer.valueOf(NumberOFWorkerThisShift) - Integer.valueOf(RegistrationUser);
                double res = (Double.valueOf(ratio) / Double.valueOf(NumberOFWorkerThisShift));
                double result = res * 100;
                int x = (int) (result - 100);
                binding.ProgressBarText.setText(String.valueOf(Math.abs(x)) + "%");
                binding.CounterUser.setText(getResources().getString(R.string.This_shift_has) + " " + myScheduleModel.getRegistrationUsers() + getResources().getString(R.string.employees_out_of) + " " + myScheduleModel.getNumber_of_workers_for_this_shift() + getResources().getString(R.string.who_are_needed));

                SetAdditionHoursTimer(myScheduleModel.getAdditional_hours_you_want(), myScheduleModel.getShift_start_time(), myScheduleModel.getShift_end_time());
            }
        });
    }


    private void GetMySchedule(String Date, String ShiftName) {
        viewModel.MyScheduleUserGET(Date, ShiftName).observe(this, userModels -> {
            userAdapter.setList(userModels);
            userAdapter.notifyDataSetChanged();
            binding.ProgressID.setMax(0);
            binding.ProgressID.setProgress(0);
            GetEmployeesSize(Date, ShiftName);

        });
    }

    private void GetMyRefuseSchedule(String Date, String ShiftName) {
        viewModel.GetAdditionalHoursScheduleUser(Date, ShiftName).observe(this, new Observer<List<RefuseUserModel>>() {
            @Override
            public void onChanged(List<RefuseUserModel> refuseUserModels) {
                additionalHoursScheduleUserAdapter.setList(refuseUserModels);
                additionalHoursScheduleUserAdapter.notifyDataSetChanged();

            }
        });
    }

    private void SetNextWeek() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            for (int i = 0; i < GetWeeksName.getDaysOfWeek().length; i++) {
                var mydate = GetWeeksName.getDaysOfWeek()[i];
                var dateModel = new DateModel();
                dateModel.setDate(String.valueOf(mydate));
                list.add(dateModel);
            }

            if (list != null) {
                for (var date : list) {
                    Log.d(TAG, date.getDate());
                }
            }
        }
        if (list != null) {
            binding.Date.setText(list.get(CurrentPosition).getDate());
        }

        var StartDate = list.get(0).getDate();
        var LastDate = list.get(list.size() - 1).getDate();
        shiftNameAdapter = new ShiftNameAdapter();
        binding.MyScheduleRecyclerView.setHasFixedSize(true);
        binding.MyScheduleRecyclerView.setAdapter(shiftNameAdapter);
        viewModel.ManageRegistationShift(StartDate, LastDate).observe(getActivity(), manageUserShiftModels -> {
            shiftNameAdapter.setList(manageUserShiftModels);
            shiftNameAdapter.notifyDataSetChanged();
        });


        GetZeroShift(StartDate, LastDate);
    }


    private void GetZeroShift(String StartDate, String EndDate) {
        var zeroshiftadapter = new ZeroShiftAdapter();
        binding.ZeroShiftRecyclerView.setHasFixedSize(true);
        var space = new SpacingItemDecorator(8, 8, 10, 0);
        binding.ZeroShiftRecyclerView.addItemDecoration(space);
        binding.ZeroShiftRecyclerView.setAdapter(zeroshiftadapter);
        binding.ZeroShiftRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        viewModel.GetAllRegisterUser(StartDate, EndDate).observe(getActivity(), userModels -> {
            zeroshiftadapter.setList(userModels);
            zeroshiftadapter.notifyDataSetChanged();
            if (zeroshiftadapter != null) {
                binding.ZeroShiftText.setVisibility(View.VISIBLE);
            } else {
                binding.ZeroShiftText.setVisibility(View.GONE);
            }
        });

        zeroshiftadapter.OnClickLisiner((Name, PhoneNumber, UID) -> {
            ImplementZeroShift(Name, PhoneNumber, UID);
        });

    }


    //todo implement zero shift -------------------
    public void ImplementZeroShift(String Name, String PhoneNumber, String UID) {
        if (ShiftName != null || Date != null) {
            viewModel.GetMySingleSchedule(Date, ShiftName).observe(getActivity(), myScheduleModel -> {
                if (myScheduleModel != null) {
                    viewModel.IsScheduleUserExists(UID, Date, ShiftName).observe(getActivity(), new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            if (aBoolean) {
                                Toast.SetToast(getActivity(), getResources().getString(R.string.already_exists_shift));
                            } else {

                                var dialog = new BottomSheetDialog(getActivity());
                                RequestdialogBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.requestdialog, null, false);
                                binding.AdditionalHoursRadioButton.setText(myScheduleModel.getAdditional_hours_you_want() + " "+getResources().getString(R.string.Addition_hours));
                                binding.ShiftStartTimeAndEndTimeRadioButton.setText(myScheduleModel.getShift_start_time() + " - " + myScheduleModel.getShift_end_time());
                                dialog.setContentView(binding.getRoot());
                                binding.DateShift.setText(ShiftName + " " + getResources().getString(R.string.Shift) + " " + DateToDayName.GetDate(Date));
                                dialog.show();

                                binding.CancelButton.setOnClickListener(view -> {
                                    dialog.dismiss();
                                });

                                //  binding.TimeRadioButton.
                                binding.ShiftStartTimeAndEndTimeRadioButton.setOnClickListener(view -> {
                                    radioid = binding.TimeRadioGroupButton.getCheckedRadioButtonId();
                                    radioButton = view.findViewById(radioid);
                                });
                                binding.AdditionalHoursRadioButton.setOnClickListener(view -> {
                                    radioid = binding.TimeRadioGroupButton.getCheckedRadioButtonId();
                                    radioButton = view.findViewById(radioid);
                                });

                                binding.RequestButton.setOnClickListener(view -> {
                                    radioid = binding.TimeRadioGroupButton.getCheckedRadioButtonId();
                                    radioButton = dialog.findViewById(radioid);
                                    if (radioButton != null) {
                                        if (radioButton.getText().toString().isEmpty()) {
                                            Toast.SetToast(getActivity(), getResources().getString(R.string.Select_hours_you_want));
                                        } else {
                                            dialog.dismiss();
                                            progressDialog.ShowProgress(getActivity());
                                            if (radioButton.getText().toString().equals(myScheduleModel.getAdditional_hours_you_want() + " "+getResources().getString(R.string.Addition_hours))) {
                                                SendRequest(myScheduleModel, Name, PhoneNumber, UID, true);
                                            } else {
                                                SendRequest(myScheduleModel, Name, PhoneNumber, UID, false);
                                            }

                                        }
                                    } else {
                                        progressDialog.CancelDialog();
                                        Toast.SetToast(getActivity(), getResources().getString(R.string.Select_hours_you_want));
                                    }
                                });
                            }
                        }
                    });

                }
            });

        } else {
            Toast.SetToast(getActivity(), getResources().getString(R.string.empty_shift_message));
        }
    }
    //todo implement zero shift -------------------


    //todo implement other shift -------------------------
    private void RequestOtherShift(String Name, String PhoneNumber, String UID) {
        if (ShiftName != null || Date != null) {
            viewModel.GetMySingleSchedule(Date, ShiftName).observe(getActivity(), new Observer<MyScheduleModel>() {
                @Override
                public void onChanged(MyScheduleModel myScheduleModel) {
                    if (myScheduleModel != null) {
                        //todo Toast.SetToast(getActivity(), Phone);
                        viewModel.IsScheduleUserExists(UID, Date, ShiftName).observe(getActivity(), new Observer<Boolean>() {
                            @Override
                            public void onChanged(Boolean aBoolean) {
                                if (aBoolean) {
                                    Toast.SetToast(getActivity(), getResources().getString(R.string.already_exists_shift));
                                } else {
                                    var dialog = new BottomSheetDialog(getActivity());
                                    RequestdialogBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.requestdialog, null, false);
                                    binding.AdditionalHoursRadioButton.setText(myScheduleModel.getAdditional_hours_you_want() + " Addition hours");
                                    binding.ShiftStartTimeAndEndTimeRadioButton.setText(myScheduleModel.getShift_start_time() + " - " + myScheduleModel.getShift_end_time());
                                    dialog.setContentView(binding.getRoot());
                                    binding.DateShift.setText(ShiftName + " " + getResources().getString(R.string.Shift) + " " + DateToDayName.GetDate(Date));
                                    dialog.show();

                                    binding.CancelButton.setOnClickListener(view -> {
                                        dialog.dismiss();
                                    });
                                    binding.RequestButton.setOnClickListener(view -> {
                                        radioid = binding.TimeRadioGroupButton.getCheckedRadioButtonId();
                                        radioButton = dialog.findViewById(radioid);
                                        if (radioButton != null) {
                                            if (radioButton.getText().toString().isEmpty()) {
                                                Toast.SetToast(getActivity(), getResources().getString(R.string.Select_hours_you_want));
                                            } else {
                                                if (radioButton.getText().toString().equals(myScheduleModel.getAdditional_hours_you_want() + " Addition hours")) {
                                                    // todo ?
                                                    SendRequest(myScheduleModel, Name, PhoneNumber, UID, true);
                                                } else {
                                                    SendRequest(myScheduleModel, Name, PhoneNumber, UID, false);
                                                }

                                            }
                                        } else {
                                            Toast.SetToast(getActivity(), getResources().getString(R.string.Select_hours_you_want));
                                        }
                                    });
                                }
                            }
                        });


                    }
                }
            });

        } else {
            Toast.SetToast(getActivity(), getResources().getString(R.string.empty_shift_message));
        }
    }
    //todo implement other shift -------------------------


    private void SendRequest(MyScheduleModel myScheduleModel, String Name, String Phone, String UID, boolean AdditionHoursApply) {
        progressDialog.ShowProgress(getActivity());
        viewModel.ScheduleRequest(myScheduleModel, Name, Phone, UID, AdditionHoursApply).observe(getActivity(), aBoolean -> {
            if (aBoolean) {
                progressDialog.CancelDialog();
                viewModel.SendRequestPOST(Sunday, Saturday, UID, Name, myScheduleModel.getPhone()).observe(getActivity(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {

                    }
                });
                //Toast.SetToast(getActivity(), "Send");
            } else {
                progressDialog.CancelDialog();
            }
        });
    }


}