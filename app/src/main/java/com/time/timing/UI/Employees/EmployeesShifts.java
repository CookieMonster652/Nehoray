package com.time.timing.UI.Employees;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.time.timing.Data.DataManager;
import com.time.timing.DateModel;
import com.time.timing.MyScheduleDBModel;
import com.time.timing.Network.ViewModel.MySendingScheduleViewModel;
import com.time.timing.Network.ViewModel.ViewModel;
import com.time.timing.R;
import com.time.timing.UI.Employees.Adapter.EmployeesFridayScheduleAdapter;
import com.time.timing.UI.Employees.Adapter.EmployeesMondayScheduleAdapter;
import com.time.timing.UI.Employees.Adapter.EmployeesSaturdayScheduleAdapter;
import com.time.timing.UI.Employees.Adapter.EmployeesThursdayScheduleAdapter;
import com.time.timing.UI.Employees.Adapter.EmployeesTuesdayScheduleAdapter;
import com.time.timing.UI.Employees.Adapter.EmployeesWednesdayScheduleAdapter;
import com.time.timing.UI.Employees.Adapter.EmployesSundayScheduleAdapter;
import com.time.timing.UI.Widget.ProgressDialog;
import com.time.timing.Utils.DateToDayName;
import com.time.timing.Utils.GetWeeksName;
import com.time.timing.Utils.SharePref;
import com.time.timing.Utils.SpacingItemDecorator;
import com.time.timing.Utils.Toast;
import com.time.timing.databinding.EmployeesshiftsBinding;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class EmployeesShifts extends AppCompatActivity {

    private EmployeesshiftsBinding binding;
    private ViewModel viewModel;
    private List<DateModel> list = new ArrayList<>();
    private EmployesSundayScheduleAdapter sundayScheduleAdapter;
    private EmployeesMondayScheduleAdapter mondayScheduleAdapter;
    private EmployeesTuesdayScheduleAdapter tuesdayScheduleAdapter;
    private EmployeesWednesdayScheduleAdapter wednesdayScheduleAdapter;
    private EmployeesThursdayScheduleAdapter thursdayScheduleAdapter;
    private EmployeesFridayScheduleAdapter fridayScheduleAdapter;
    private EmployeesSaturdayScheduleAdapter saturdayScheduleAdapter;

    private int SundayShiftCount = 0, MondayShiftCount = 0, ThursdayShiftCount = 0, TuesdayShiftCount = 0, WednesdayShiftCount = 0, FridayShiftCount = 0, SaturdayShiftCount = 0;
    private int Count = 0;
    private static final String TAG = "EmployeesShifts";
    private MySendingScheduleViewModel mySendingScheduleViewModel;
    private Integer MinimumShiftParWorkers = null;
    private String StartDate, EndDate;
    private String UID = null;
    private CollectionReference MySchedule;
    private SharePref sharePref;
    private ProgressDialog progressDialog = new ProgressDialog();
    private String Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday;
    private int SundayRegUser = 0;
    private int MondayRegUser = 0;
    private int TuesdayRegUser = 0;
    private int WednesdayRegUser = 0;
    private int ThursdayRegUser = 0;
    private int FridayRegUser = 0;
    private int SaturdayRegUser = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.employeesshifts);
        sharePref = new SharePref(EmployeesShifts.this);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        mySendingScheduleViewModel = new ViewModelProvider(this).get(MySendingScheduleViewModel.class);
        MySchedule = FirebaseFirestore.getInstance().collection(DataManager.MySchedule);

        InitView();

        SetNextWeek();


    }

    private void SetNextWeek() {
        for (int i = 0; i < GetWeeksName.getDaysOfWeek().length; i++) {
            var dateModel = new DateModel();
            var mydate = GetWeeksName.getDaysOfWeek()[i];
            dateModel.setDate(String.valueOf(mydate));
            list.add(dateModel);
        }


        Sunday = list.get(0).getDate();
        GetSundayScheduleRequest(Sunday);
        GetSundayTotalRegisterUser(Sunday);
        Log.d("Sunday", Sunday);
        StartDate = Sunday;

        Monday = list.get(1).getDate();
        GetMondayScheduleRequest(Monday);
        Log.d("Monday", Monday);


        Tuesday = list.get(2).getDate();
        GetTuesdayScheduleRequest(Tuesday);
        Log.d("Tuesday", Tuesday);


        Wednesday = list.get(3).getDate();
        GetWednesdayScheduleRequest(Wednesday);
        Log.d("Wednesday", Wednesday);


        Thursday = list.get(4).getDate();
        GetThursdayScheduleRequest(Thursday);
        Log.d("Thursday", Thursday);

        Friday = list.get(5).getDate();
        GetFridayScheduleRequest(Friday);
        Log.d("Friday", Friday);

        Saturday = list.get(list.size() - 1).getDate();
        GetSaturdayScheduleRequest(Saturday);

        EndDate = Saturday;


//
//

        viewModel.GetSundayAllRegisterUser(sharePref.GetData(DataManager.UID), Sunday).observe(EmployeesShifts.this, integer -> {
            SundayRegUser = integer;
        });
        viewModel.GetMondayAllRegisterUser(sharePref.GetData(DataManager.UID), Monday).observe(EmployeesShifts.this, integer -> {
            MondayRegUser = integer;
        });

        viewModel.GetTuesdayAllRegisterUser(sharePref.GetData(DataManager.UID), Tuesday).observe(EmployeesShifts.this, integer -> {
            TuesdayRegUser = integer;
            //Toast.SetToast(getApplicationContext(), String.valueOf(TuesdayRegUser));
        });
        viewModel.GetWednesdayAllRegisterUser(sharePref.GetData(DataManager.UID), Wednesday).observe(EmployeesShifts.this, integer ->
                WednesdayRegUser = integer);
        viewModel.GetThursdayAllRegisterUser(sharePref.GetData(DataManager.UID), Thursday).observe(EmployeesShifts.this, integer -> {
            ThursdayRegUser = integer;
        });
        viewModel.GetFridayAllRegisterUser(sharePref.GetData(DataManager.UID), Friday).observe(EmployeesShifts.this, integer -> FridayShiftCount = integer);
        viewModel.GetSaturdayAllRegisterUser(sharePref.GetData(DataManager.UID), Saturday).observe(EmployeesShifts.this, integer -> {
            SaturdayRegUser = integer;
        });

    }

    private void GetFridayScheduleRequest(String Date) {
        viewModel.GetRequestEmployeesFridaySchedule(sharePref.GetData(DataManager.Phone), Date).observe(this, employeesFridayScheduleModels -> {
            fridayScheduleAdapter.setList(employeesFridayScheduleModels);
            fridayScheduleAdapter.notifyDataSetChanged();

            if (employeesFridayScheduleModels != null) {
                binding.FridayText.setVisibility(View.VISIBLE);
                binding.FridayText.setText(DateToDayName.GetDate(Date));

                for (var data : employeesFridayScheduleModels) {
                    MinimumShiftParWorkers = Integer.parseInt(data.getMinimal_shift_par_workers());
                    binding.Details.setText(getResources().getString(R.string.You_need_to_sign_up_for_at_least) + " " + String.valueOf(MinimumShiftParWorkers) + " " + getResources().getString(R.string.Shift));
                }
            }
        });
    }

    //todo get total reg user
    private void GetSundayTotalRegisterUser(String Sunday) {

    }
    //todo get total reg user


    private void GetSaturdayScheduleRequest(String Date) {
        viewModel.GetRequestSaturdaySchedule(sharePref.GetData(DataManager.Phone), Date).observe(this, employeesSaturdayScheduleModels -> {
            saturdayScheduleAdapter.setList(employeesSaturdayScheduleModels);
            saturdayScheduleAdapter.notifyDataSetChanged();
            if (employeesSaturdayScheduleModels != null) {
                binding.SaturdayText.setVisibility(View.VISIBLE);
                binding.SaturdayText.setText(DateToDayName.GetDate(Date));
                for (var data : employeesSaturdayScheduleModels) {
                    MinimumShiftParWorkers = Integer.parseInt(data.getMinimal_shift_par_workers());
                    binding.Details.setText(getResources().getString(R.string.You_need_to_sign_up_for_at_least) + " " + String.valueOf(MinimumShiftParWorkers) + " " + getResources().getString(R.string.Shift));
                }
            }
        });
    }

    private void GetThursdayScheduleRequest(String Date) {
        viewModel.GetRequestEmployeesThusdaySchedule(sharePref.GetData(DataManager.Phone), Date).observe(this, employeesThusdayScheduleModels -> {
            thursdayScheduleAdapter.setList(employeesThusdayScheduleModels);
            thursdayScheduleAdapter.notifyDataSetChanged();
            if (employeesThusdayScheduleModels != null) {
                binding.ThursdayText.setVisibility(View.VISIBLE);
                binding.ThursdayText.setText(DateToDayName.GetDate(Date));
                for (var data : employeesThusdayScheduleModels) {
                    MinimumShiftParWorkers = Integer.parseInt(data.getMinimal_shift_par_workers());
                    binding.Details.setText(getResources().getString(R.string.You_need_to_sign_up_for_at_least) + " " + String.valueOf(MinimumShiftParWorkers) + " " + getResources().getString(R.string.Shift));


                }
            }
        });
    }

    private void GetTuesdayScheduleRequest(String Date) {
        viewModel.GetRequestEmployeesTuesdaySchedule(sharePref.GetData(DataManager.Phone), Date).observe(this, employeesTuesdayScheduleModels -> {
            tuesdayScheduleAdapter.setList(employeesTuesdayScheduleModels);
            tuesdayScheduleAdapter.notifyDataSetChanged();
            if (employeesTuesdayScheduleModels != null) {
                binding.TuesdayText.setVisibility(View.VISIBLE);
                binding.TuesdayText.setText(DateToDayName.GetDate(Date));
                for (var data : employeesTuesdayScheduleModels) {
                    MinimumShiftParWorkers = Integer.parseInt(data.getMinimal_shift_par_workers());
                    binding.Details.setText(getResources().getString(R.string.You_need_to_sign_up_for_at_least) + " " + String.valueOf(MinimumShiftParWorkers) + " " + getResources().getString(R.string.Shift));
                }
            }
        });
    }

    private void GetWednesdayScheduleRequest(String Date) {
        viewModel.GetRequestWednesdaySchedule(sharePref.GetData(DataManager.Phone), Date).observe(this, employeesWednesdayScheduleModels -> {
            wednesdayScheduleAdapter.setList(employeesWednesdayScheduleModels);
            wednesdayScheduleAdapter.notifyDataSetChanged();
            if (employeesWednesdayScheduleModels != null) {
                binding.WednesdayText.setVisibility(View.VISIBLE);
                binding.WednesdayText.setText(DateToDayName.GetDate(Date));
                for (var data : employeesWednesdayScheduleModels) {
                    MinimumShiftParWorkers = Integer.parseInt(data.getMinimal_shift_par_workers());
                    binding.Details.setText(getResources().getString(R.string.You_need_to_sign_up_for_at_least) + " " + String.valueOf(MinimumShiftParWorkers) + " " + getResources().getString(R.string.Shift));
                }
            }
        });
    }

    private void GetSundayScheduleRequest(String Date) {
        viewModel.GetRequestEmployeesSundaySchedule(sharePref.GetData(DataManager.Phone), Date).observe(this, employeesSundayScheduleModels -> {
            sundayScheduleAdapter.setList(employeesSundayScheduleModels);
            sundayScheduleAdapter.notifyDataSetChanged();

            if (employeesSundayScheduleModels != null) {
                binding.SundayText.setVisibility(View.VISIBLE);
                binding.SundayText.setText(DateToDayName.GetDate(Date));
                for (var data : employeesSundayScheduleModels) {

                    MinimumShiftParWorkers = Integer.parseInt(data.getMinimal_shift_par_workers());
                    binding.Details.setText(getResources().getString(R.string.You_need_to_sign_up_for_at_least) + " " + String.valueOf(MinimumShiftParWorkers) + " " + getResources().getString(R.string.Shift));
                }
            }
        });
    }

    private void GetMondayScheduleRequest(String Date) {
        viewModel.GetRequestEmployeesMondaySchedule(sharePref.GetData(DataManager.Phone), Date).observe(this, employeesMondayScheduleModels -> {
            mondayScheduleAdapter.setList(employeesMondayScheduleModels);
            mondayScheduleAdapter.notifyDataSetChanged();
            if (employeesMondayScheduleModels != null) {
                binding.MondayText.setVisibility(View.VISIBLE);
                binding.MondayText.setText(DateToDayName.GetDate(Date));
                for (var data : employeesMondayScheduleModels) {
                    MinimumShiftParWorkers = Integer.parseInt(data.getMinimal_shift_par_workers());
                    binding.Details.setText(getResources().getString(R.string.You_need_to_sign_up_for_at_least) + " " + String.valueOf(MinimumShiftParWorkers) + " " + getResources().getString(R.string.Shift));
                }
            }
        });
    }

    private void InitView() {
        binding.SundayRecyclerView.setHasFixedSize(true);
        sundayScheduleAdapter = new EmployesSundayScheduleAdapter();
        binding.SundayRecyclerView.setAdapter(sundayScheduleAdapter);

        binding.MondayRecyclerView.setHasFixedSize(true);
        mondayScheduleAdapter = new EmployeesMondayScheduleAdapter();
        binding.MondayRecyclerView.setAdapter(mondayScheduleAdapter);

        binding.ThursdayRecyclerView.setHasFixedSize(true);
        thursdayScheduleAdapter = new EmployeesThursdayScheduleAdapter();
        binding.ThursdayRecyclerView.setAdapter(thursdayScheduleAdapter);

        binding.WednesdayRecyclerView.setHasFixedSize(true);
        wednesdayScheduleAdapter = new EmployeesWednesdayScheduleAdapter();
        binding.WednesdayRecyclerView.setAdapter(wednesdayScheduleAdapter);

        binding.TuesdayRecyclerView.setHasFixedSize(true);
        tuesdayScheduleAdapter = new EmployeesTuesdayScheduleAdapter();
        binding.TuesdayRecyclerView.setAdapter(tuesdayScheduleAdapter);

        binding.FridayRecyclerView.setHasFixedSize(true);
        fridayScheduleAdapter = new EmployeesFridayScheduleAdapter();
        binding.FridayRecyclerView.setAdapter(fridayScheduleAdapter);

        binding.SaturdayRecyclerView.setHasFixedSize(true);
        saturdayScheduleAdapter = new EmployeesSaturdayScheduleAdapter();
        binding.SaturdayRecyclerView.setAdapter(saturdayScheduleAdapter);

        var spaceing = new SpacingItemDecorator(0, 0, 10, 10);
        binding.SundayRecyclerView.addItemDecoration(spaceing);
        binding.MondayRecyclerView.addItemDecoration(spaceing);
        binding.ThursdayRecyclerView.addItemDecoration(spaceing);
        binding.WednesdayRecyclerView.addItemDecoration(spaceing);
        binding.FridayRecyclerView.addItemDecoration(spaceing);
        binding.TuesdayRecyclerView.addItemDecoration(spaceing);
        binding.SaturdayRecyclerView.addItemDecoration(spaceing);

        binding.Toolbar.Title.setText(getResources().getString(R.string.Select_Schedule));


        binding.Toolbar.BackButton.setOnClickListener(view -> {
            finish();
            Animatoo.animateSlideRight(EmployeesShifts.this);
        });

        viewModel.EmployeesProfileData().observe(this, employeesModel -> {
            if (employeesModel != null) {
                binding.setEmployees(employeesModel);
            }
        });


        sundayScheduleAdapter.OnItemLisiner(Size -> {
            SundayShiftCount = Size;
            Count = SundayShiftCount + MondayShiftCount + ThursdayShiftCount + TuesdayShiftCount + WednesdayShiftCount + FridayShiftCount + SaturdayShiftCount;
            binding.SelectCounter.setText(getResources().getString(R.string.You_have_selected_only) + " " + String.valueOf(Count) + " " + getResources().getString(R.string.shift_out_of) + " " + String.valueOf(MinimumShiftParWorkers) + " " + getResources().getString(R.string.needed));
        });
        mondayScheduleAdapter.OnItemLisiner(Size -> {
            MondayShiftCount = Size;
            Count = SundayShiftCount + MondayShiftCount + ThursdayShiftCount + TuesdayShiftCount + WednesdayShiftCount + FridayShiftCount + SaturdayShiftCount;
            binding.SelectCounter.setText(getResources().getString(R.string.You_have_selected_only) + " " + String.valueOf(Count) + " " + getResources().getString(R.string.shift_out_of) + " " + String.valueOf(MinimumShiftParWorkers) + " " + getResources().getString(R.string.needed));
        });
        thursdayScheduleAdapter.OnItemLisiner(Size -> {
            ThursdayShiftCount = Size;
            Count = SundayShiftCount + MondayShiftCount + ThursdayShiftCount + TuesdayShiftCount + WednesdayShiftCount + FridayShiftCount + SaturdayShiftCount;
            binding.SelectCounter.setText(getResources().getString(R.string.You_have_selected_only) + " " + String.valueOf(Count) + " " + getResources().getString(R.string.shift_out_of) + " " + String.valueOf(MinimumShiftParWorkers) + " " + getResources().getString(R.string.needed));
        });
        tuesdayScheduleAdapter.OnItemLisiner(Size -> {
            TuesdayShiftCount = Size;
            Count = SundayShiftCount + MondayShiftCount + ThursdayShiftCount + TuesdayShiftCount + WednesdayShiftCount + FridayShiftCount + SaturdayShiftCount;
            binding.SelectCounter.setText(getResources().getString(R.string.You_have_selected_only) + " " + String.valueOf(Count) + " " + getResources().getString(R.string.shift_out_of) + " " + String.valueOf(MinimumShiftParWorkers) + " " + getResources().getString(R.string.needed));
        });
        wednesdayScheduleAdapter.OnItemLisiner(Size -> {
            WednesdayShiftCount = Size;
            Count = SundayShiftCount + MondayShiftCount + ThursdayShiftCount + TuesdayShiftCount + WednesdayShiftCount + FridayShiftCount + SaturdayShiftCount;
            binding.SelectCounter.setText(getResources().getString(R.string.You_have_selected_only) + " " + String.valueOf(Count) + " " + getResources().getString(R.string.shift_out_of) + " " + String.valueOf(MinimumShiftParWorkers) + " " + getResources().getString(R.string.needed));
        });
        fridayScheduleAdapter.OnItemLisiner(Size -> {
            FridayShiftCount = Size;
            Count = SundayShiftCount + MondayShiftCount + ThursdayShiftCount + TuesdayShiftCount + WednesdayShiftCount + FridayShiftCount + SaturdayShiftCount;
            binding.SelectCounter.setText(getResources().getString(R.string.You_have_selected_only) + " " + String.valueOf(Count) + " " + getResources().getString(R.string.shift_out_of) + " " + String.valueOf(MinimumShiftParWorkers) + " " + getResources().getString(R.string.needed));
        });
        saturdayScheduleAdapter.OnItemLisiner(Size -> {
            SaturdayShiftCount = Size;
            Count = SundayShiftCount + MondayShiftCount + ThursdayShiftCount + TuesdayShiftCount + WednesdayShiftCount + FridayShiftCount + SaturdayShiftCount;
            binding.SelectCounter.setText(getResources().getString(R.string.You_have_selected_only) + " " + String.valueOf(Count) + " " + getResources().getString(R.string.shift_out_of) + " " + String.valueOf(MinimumShiftParWorkers) + " " + getResources().getString(R.string.needed));
        });

        binding.ConfirmSend.setOnClickListener(view -> {
            if (MinimumShiftParWorkers != null) {

                if (Count <= 0) {
                    Toast.SetToast(EmployeesShifts.this, getResources().getString(R.string.You_have_selected_only) + " " + String.valueOf(Count) + " " + getResources().getString(R.string.shift_out_of) + " " + String.valueOf(MinimumShiftParWorkers) + " " + getResources().getString(R.string.needed));
                } else {

                    if (sundayScheduleAdapter.getList() != null) {
                        for (var sunday : sundayScheduleAdapter.getList()) {
                            if (sunday.isIsEnable()) {
                                var model = new MyScheduleDBModel(sunday.getData(), sunday.getShift_start_time(), sunday.getShift_end_time(), sunday.getUID(), sunday.getShiftName(), sunday.getShiftManager());
                                mySendingScheduleViewModel.InsertSendingSchedule(model);

                                viewModel.AcceptSchedulePOST(sunday.getUID(), sunday.getData(), sunday.getShiftName(), false, DataManager.Confirm, sunday.getShiftName()).observe(this, aBoolean -> {

                                });
                            }
                            UID = sunday.getUID();
                            sharePref.SetData(DataManager.UID, UID);
                        }
                    }

                    if (mondayScheduleAdapter.getList() != null) {
                        for (var monday : mondayScheduleAdapter.getList()) {
                            if (monday.isIsEnable()) {
                                var model = new MyScheduleDBModel(monday.getData(), monday.getShift_start_time(), monday.getShift_end_time(), monday.getUID(), monday.getShiftName(), monday.getShiftManager());
                                mySendingScheduleViewModel.InsertSendingSchedule(model);

                                viewModel.AcceptSchedulePOST(monday.getUID(), monday.getData(), monday.getShiftName(), false, DataManager.Confirm, monday.getShiftName()).observe(this, aBoolean -> {

                                });
                                UID = monday.getUID();
                                sharePref.SetData(DataManager.UID, UID);
                            }
                        }
                    }

                    if (tuesdayScheduleAdapter.getList() != null) {
                        for (var tuesday : tuesdayScheduleAdapter.getList()) {
                            if (tuesday.isIsEnable()) {
                                var model = new MyScheduleDBModel(tuesday.getData(), tuesday.getShift_start_time(), tuesday.getShift_end_time(), tuesday.getUID(), tuesday.getShiftName(), tuesday.getShiftManager());
                                mySendingScheduleViewModel.InsertSendingSchedule(model);
                                viewModel.AcceptSchedulePOST(tuesday.getUID(), tuesday.getData(), tuesday.getShiftName(), false, DataManager.Confirm, tuesday.getShiftName()).observe(this, aBoolean -> {

                                });
                                UID = tuesday.getUID();
                                sharePref.SetData(DataManager.UID, UID);
                            }
                        }
                    }

                    if (wednesdayScheduleAdapter.getList() != null) {
                        for (var ds : wednesdayScheduleAdapter.getList()) {
                            if (ds.isIsEnable()) {
                                var model = new MyScheduleDBModel(ds.getData(), ds.getShift_start_time(), ds.getShift_end_time(), ds.getUID(), ds.getShiftName(), ds.getShiftManager());
                                mySendingScheduleViewModel.InsertSendingSchedule(model);
                                viewModel.AcceptSchedulePOST(ds.getUID(), ds.getData(), ds.getShiftName(), false, DataManager.Confirm, ds.getShiftName()).observe(this, aBoolean -> {

                                });
                                UID = ds.getUID();
                                sharePref.SetData(DataManager.UID, UID);
                            }
                        }
                    }


                    if (thursdayScheduleAdapter.getList() != null) {
                        for (var thursdaydata : thursdayScheduleAdapter.getList()) {
                            if (thursdaydata.isIsEnable()) {
                                Log.d(TAG, thursdaydata.getAdditional_hours_you_want());
                                var model = new MyScheduleDBModel(thursdaydata.getData(), thursdaydata.getShift_start_time(), thursdaydata.getShift_end_time(), thursdaydata.getUID(), thursdaydata.getShiftName(), thursdaydata.getShiftManager());
                                mySendingScheduleViewModel.InsertSendingSchedule(model);

                                viewModel.AcceptSchedulePOST(thursdaydata.getUID(), thursdaydata.getData(), thursdaydata.getShiftName(), false, DataManager.Confirm, thursdaydata.getShiftName()).observe(this, aBoolean -> {

                                });
                                UID = thursdaydata.getUID();
                                sharePref.SetData(DataManager.UID, UID);
                            }
                        }
                    }


                    if (fridayScheduleAdapter.getList() != null) {
                        for (var friday : fridayScheduleAdapter.getList()) {
                            if (friday.isIsEnable()) {
                                var model = new MyScheduleDBModel(friday.getData(), friday.getShift_start_time(), friday.getShift_end_time(), friday.getUID(), friday.getShiftName(), friday.getShiftManager());
                                mySendingScheduleViewModel.InsertSendingSchedule(model);
                                viewModel.AcceptSchedulePOST(friday.getUID(), friday.getData(), friday.getShiftName(), false, DataManager.Confirm, friday.getShiftName()).observe(this, aBoolean -> {

                                });
                                UID = friday.getUID();
                                sharePref.SetData(DataManager.UID, UID);
                            }
                        }
                    }

                    if (saturdayScheduleAdapter.getList() != null) {
                        for (var saturday : saturdayScheduleAdapter.getList()) {
                            if (saturday.isIsEnable()) {
                                var model = new MyScheduleDBModel(saturday.getData(), saturday.getShift_start_time(), saturday.getShift_end_time(), saturday.getUID(), saturday.getShiftName(), saturday.getShiftManager());
                                mySendingScheduleViewModel.InsertSendingSchedule(model);
                                viewModel.AcceptSchedulePOST(saturday.getUID(), saturday.getData(), saturday.getShiftName(), false, DataManager.Confirm, saturday.getShiftName()).observe(this, aBoolean -> {

                                });
                                UID = saturday.getUID();
                                sharePref.SetData(DataManager.UID, UID);
                            }
                        }
                    }


                    progressDialog.ShowProgress(EmployeesShifts.this);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.CancelDialog();
                            finish();
                        }
                    }, 6000);

                    if (UID != null) {
                        ManageUsers(UID, Count, StartDate, EndDate);
                    }
                }
            }

        });
    }


    private void ManageUsers(String UID, int Count, String StartDate, String EndDate) {
        viewModel.GetSundayAllRegisterUser(sharePref.GetData(DataManager.UID), Sunday).observe(EmployeesShifts.this, integer -> {
            SundayRegUser = integer;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewModel.EmployeesProfileData().observe(EmployeesShifts.this,
                            employeesModel -> {
                                if (employeesModel != null) {
                                    viewModel.ManageUser(UID, StartDate, EndDate, employeesModel.getPhone(), employeesModel.getName(), String.valueOf(SundayRegUser), String.valueOf(MinimumShiftParWorkers)).observe(EmployeesShifts.this, aBoolean -> {
                                    });
                                }
                            });
                }
            }, 2000);

        });
    }


    @Override
    public void onBackPressed() {
        finish();
        Animatoo.animateSlideRight(EmployeesShifts.this);
    }
}