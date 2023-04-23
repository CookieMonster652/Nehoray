package com.time.timing.UI.EmployeesHome;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.time.timing.Data.DataManager;
import com.time.timing.DateModel;
import com.time.timing.FridaySchaduleModel;
import com.time.timing.MondaySchaduleModel;
import com.time.timing.MyScheduleDBModel;
import com.time.timing.Network.SharePref.SharePref;
import com.time.timing.Network.ViewModel.MySendingScheduleViewModel;
import com.time.timing.Network.ViewModel.ViewModel;
import com.time.timing.R;
import com.time.timing.SaturdaySchaduleModel;
import com.time.timing.SundayScheduleModel;
import com.time.timing.ThursdaySchaduleModel;
import com.time.timing.TuesdaySchaduleModel;
import com.time.timing.UI.PreviousWeekAdapter.PreviousFridayAdapter;
import com.time.timing.UI.PreviousWeekAdapter.PreviousTuesdayAdapter;
import com.time.timing.UI.PreviousWeekAdapter.WeekAdapter.PreviousMondayAdapter;
import com.time.timing.UI.PreviousWeekAdapter.WeekAdapter.PreviousSaturdayAdapter;
import com.time.timing.UI.PreviousWeekAdapter.WeekAdapter.PreviousSundayAdapter;
import com.time.timing.UI.PreviousWeekAdapter.WeekAdapter.PreviousThursdayAdapter;
import com.time.timing.UI.PreviousWeekAdapter.WeekAdapter.PreviousWednesdayAdapter;
import com.time.timing.UI.ShiftRequestAdapter.FridayShiftRequestAdapter;
import com.time.timing.UI.ShiftRequestAdapter.MondayShiftRequestAdapter;
import com.time.timing.UI.ShiftRequestAdapter.SaturdayShiftRequestAdapter;
import com.time.timing.UI.ShiftRequestAdapter.SundayShiftRequestAdapter;
import com.time.timing.UI.ShiftRequestAdapter.ThursdayShiftRequestAdapter;
import com.time.timing.UI.ShiftRequestAdapter.TuesdayShiftRequestAdapter;
import com.time.timing.UI.ShiftRequestAdapter.WednessdayShiftRequestAdapter;
import com.time.timing.UI.Widget.ProgressDialog;
import com.time.timing.Utils.DateToDayName;
import com.time.timing.Utils.GetWeeksName;
import com.time.timing.Utils.HandleActivity;
import com.time.timing.WednesdaySchaduleModel;
import com.time.timing.databinding.EmployeesdailyscheduleBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DailySchedule extends Fragment {

    private EmployeesdailyscheduleBinding binding;
    private ViewModel viewModel;
    private List<DateModel> list = new ArrayList<>();
    private String RegistrationStartDate = null;
    private String RegistrationStartTime = null;

    private String RegistrationEndDate = null;
    private String RegistrationEndTime = null;
    private SharePref sharePref;
    private static final String TAG = "DailySchedule";
    private MySendingScheduleViewModel mySendingScheduleViewModel;


    private List<WednesdaySchaduleModel> WednesdaySchaduleModelList = new ArrayList<>();
    private List<SundayScheduleModel> sundayScheduleModelslist = new ArrayList<>();
    private List<MondaySchaduleModel> mondaySchaduleModelslist = new ArrayList<>();
    private List<TuesdaySchaduleModel> tuesdaySchaduleModelslist = new ArrayList<>();
    private List<ThursdaySchaduleModel> thursdaySchaduleModelslist = new ArrayList<>();
    private List<FridaySchaduleModel> fridaySchaduleModelslist = new ArrayList<>();
    private List<SaturdaySchaduleModel> saturdaySchaduleModelslist = new ArrayList<>();

    private PreviousSundayAdapter previousSundayAdapter;
    private PreviousMondayAdapter previousMondayAdapter;
    private PreviousTuesdayAdapter previousTuesdayAdapter;
    private PreviousWednesdayAdapter previousWednesdayAdapter;
    private PreviousThursdayAdapter previousThursdayAdapter;
    private PreviousFridayAdapter previousFridayAdapter;
    private PreviousSaturdayAdapter previousSaturdayAdapter;


    private SundayShiftRequestAdapter sundayShiftRequestAdapter;
    private MondayShiftRequestAdapter mondayShiftRequestAdapter;
    private TuesdayShiftRequestAdapter tuesdayShiftRequestAdapter;
    private WednessdayShiftRequestAdapter wednessdayShiftRequestAdapter;
    private ThursdayShiftRequestAdapter thursdayShiftRequestAdapter;
    private FridayShiftRequestAdapter fridayShiftRequestAdapter;
    private SaturdayShiftRequestAdapter saturdayShiftRequestAdapter;


    private String Sunday;
    private String Saturday;
    private String MinimumShiftParWorkers;
    private ProgressDialog progressDialog = new ProgressDialog();



    public DailySchedule() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.employeesdailyschedule, container, false);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        sharePref = new SharePref(getActivity());
        mySendingScheduleViewModel = new ViewModelProvider(this).get(MySendingScheduleViewModel.class);


        SetNextWeek();
        InitView();
        SetName();

        return binding.getRoot();
    }


    private void GetSundayScheduleData(String Date) {
        mySendingScheduleViewModel.SearchByMyAllSchedule(Date).observe(getActivity(), myScheduleDBModels -> {
            if (myScheduleDBModels != null) {
                if (myScheduleDBModels.size() > 0) {
                    binding.SundayText.setText(DateToDayName.GetDate(Date));
                    binding.SundayText.setVisibility(View.VISIBLE);

                    for (var data : myScheduleDBModels) {
                        var model = new SundayScheduleModel();
                        model.setData(data.getData());
                        model.setShift_start_time(data.getShift_start_time());
                        model.setShift_end_time(data.getShift_end_time());
                        model.setShiftName(data.getShiftName());
                        model.setUID(data.getUID());
                        model.setShiftManager(data.getShiftManager());
                        sundayScheduleModelslist.add(model);
                    }
                } else {
                    binding.SundayText.setVisibility(View.GONE);
                }
                previousSundayAdapter.setList(sundayScheduleModelslist);
                previousSundayAdapter.notifyDataSetChanged();
            }
        });
    }

    private void GetMondayScheduleData(String Date) {
        mySendingScheduleViewModel.SearchByMyAllSchedule(Date).observe(getActivity(), myScheduleDBModels -> {
            if (myScheduleDBModels != null) {
                if (myScheduleDBModels.size() > 0) {
                    binding.MondayText.setText(DateToDayName.GetDate(Date));
                    binding.MondayText.setVisibility(View.VISIBLE);

                    for (var data : myScheduleDBModels) {
                        var model = new MondaySchaduleModel();
                        model.setData(data.getData());
                        model.setShift_start_time(data.getShift_start_time());
                        model.setShift_end_time(data.getShift_end_time());
                        model.setShiftName(data.getShiftName());
                        model.setUID(data.getUID());
                        model.setShiftManager(data.getShiftManager());
                        mondaySchaduleModelslist.add(model);
                    }
                } else {
                    binding.MondayText.setVisibility(View.GONE);
                }
                previousMondayAdapter.setList(mondaySchaduleModelslist);
                previousMondayAdapter.notifyDataSetChanged();
            }
        });
    }

    private void GetDataWednesday(String Date) {
        mySendingScheduleViewModel.SearchByMyAllSchedule(Date).observe(getActivity(), new Observer<List<MyScheduleDBModel>>() {
            @Override
            public void onChanged(List<MyScheduleDBModel> myScheduleDBModels) {
                if (myScheduleDBModels != null) {
                    if (myScheduleDBModels.size() > 0) {
                        for (var data : myScheduleDBModels) {
                            var model = new WednesdaySchaduleModel();
                            model.setData(data.getData());
                            model.setShift_start_time(data.getShift_start_time());
                            model.setShift_end_time(data.getShift_end_time());
                            model.setShiftName(data.getShiftName());
                            model.setUID(data.getUID());
                            model.setShiftManager(data.getShiftManager());
                            WednesdaySchaduleModelList.add(model);
                        }
                        binding.WednesdayText.setVisibility(View.VISIBLE);
                        binding.WednesdayText.setText(DateToDayName.GetDate(Date));
                    } else {
                        binding.WednesdayText.setVisibility(View.GONE);
                    }

                    previousWednesdayAdapter.setList(WednesdaySchaduleModelList);
                    previousWednesdayAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void GetThursdayData(String Date) {
        mySendingScheduleViewModel.SearchByMyAllSchedule(Date).observe(getActivity(), myScheduleDBModels -> {
            if (myScheduleDBModels != null) {
                if (myScheduleDBModels.size() > 0) {
                    binding.ThursdayText.setVisibility(View.VISIBLE);
                    binding.ThursdayText.setText(DateToDayName.GetDate(Date));

                    for (var data : myScheduleDBModels) {
                        var model = new ThursdaySchaduleModel();
                        model.setData(data.getData());
                        model.setShift_start_time(data.getShift_start_time());
                        model.setShift_end_time(data.getShift_end_time());
                        model.setShiftName(data.getShiftName());
                        model.setUID(data.getUID());
                        model.setShiftManager(data.getShiftManager());
                        thursdaySchaduleModelslist.add(model);
                    }
                } else {
                    binding.ThursdayText.setVisibility(View.GONE);
                }
                previousThursdayAdapter.setList(thursdaySchaduleModelslist);
                previousThursdayAdapter.notifyDataSetChanged();
            }
        });
    }

    private void GetFridayScheduleData(String Date) {
        mySendingScheduleViewModel.SearchByMyAllSchedule(Date).observe(getActivity(), myScheduleDBModels -> {
            if (myScheduleDBModels != null) {
                if (myScheduleDBModels.size() > 0) {
                    binding.FridayText.setVisibility(View.VISIBLE);
                    binding.FridayText.setText(DateToDayName.GetDate(Date));

                    for (var data : myScheduleDBModels) {
                        var model = new FridaySchaduleModel();
                        model.setData(data.getData());
                        model.setShift_start_time(data.getShift_start_time());
                        model.setShift_end_time(data.getShift_end_time());
                        model.setShiftName(data.getShiftName());
                        model.setUID(data.getUID());
                        model.setShiftManager(data.getShiftManager());
                        fridaySchaduleModelslist.add(model);
                    }
                } else {
                    binding.FridayText.setVisibility(View.GONE);
                }
                previousFridayAdapter.setData(fridaySchaduleModelslist);
                previousFridayAdapter.notifyDataSetChanged();
            }
        });
    }

    private void GetSaturdayScheduleData(String Date) {
        mySendingScheduleViewModel.SearchByMyAllSchedule(Date).observe(getActivity(), myScheduleDBModels -> {
            if (myScheduleDBModels != null) {
                if (myScheduleDBModels.size() > 0) {
                    binding.SaturdayText.setVisibility(View.VISIBLE);
                    binding.SaturdayText.setText(DateToDayName.GetDate(Date));

                    for (var data : myScheduleDBModels) {
                        var model = new SaturdaySchaduleModel();
                        model.setData(data.getData());
                        model.setShift_start_time(data.getShift_start_time());
                        model.setShift_end_time(data.getShift_end_time());
                        model.setShiftName(data.getShiftName());
                        model.setUID(data.getUID());
                        model.setShiftManager(data.getShiftManager());

                        saturdaySchaduleModelslist.add(model);
                    }
                } else {
                    binding.SaturdayText.setVisibility(View.GONE);
                }
                previousSaturdayAdapter.setList(saturdaySchaduleModelslist);
                previousSaturdayAdapter.notifyDataSetChanged();
            }
        });
    }


    private void GetDataTuesday(String Date) {
        mySendingScheduleViewModel.SearchByMyAllSchedule(Date).observe(getActivity(), new Observer<List<MyScheduleDBModel>>() {
            @Override
            public void onChanged(List<MyScheduleDBModel> myScheduleDBModels) {
                if (myScheduleDBModels != null) {
                    if (myScheduleDBModels != null) {
                        if (myScheduleDBModels.size() > 0) {
                            for (var data : myScheduleDBModels) {
                                var model = new TuesdaySchaduleModel();
                                model.setData(data.getData());
                                model.setShift_start_time(data.getShift_start_time());
                                model.setShift_end_time(data.getShift_end_time());
                                model.setShiftName(data.getShiftName());
                                model.setUID(data.getUID());
                                model.setShiftManager(data.getShiftManager());
                                tuesdaySchaduleModelslist.add(model);

                            }
                            binding.TuesdayText.setVisibility(View.VISIBLE);
                            binding.TuesdayText.setText(DateToDayName.GetDate(Date));
                        } else {
                            binding.TuesdayText.setVisibility(View.GONE);
                        }

                        previousTuesdayAdapter.setList(tuesdaySchaduleModelslist);
                        previousTuesdayAdapter.notifyDataSetChanged();
                    }
                }

            }
        });
    }


    private void SetNextWeek() {
        for (int i = 0; i < GetWeeksName.getDaysOfWeek().length; i++) {
            var dateModel = new DateModel();
            var mydate = GetWeeksName.getDaysOfWeek()[i];
            dateModel.setDate(String.valueOf(mydate));
            list.add(dateModel);
        }


        Sunday = list.get(0).getDate();
        GetSundayScheduleRequest(sharePref.GetData(DataManager.Phone), Sunday);
        GetSundayShiftRequest(sharePref.GetData(DataManager.Phone), Sunday);

        GetSundayScheduleData(Sunday);
        GetSundayMinimumShiftParWorker(sharePref.GetData(DataManager.Phone), Sunday);
        Log.d("Sunday", Sunday);

        var Monday = list.get(1).getDate();
        GetMondayScheduleRequest(sharePref.GetData(DataManager.Phone), Monday);
        GetMondayShiftRequest(sharePref.GetData(DataManager.Phone), Monday);
        GetMondayScheduleData(Monday);
        Log.d("Monday", Monday);
        GetMondayMinimumShiftParWorker(sharePref.GetData(DataManager.Phone), Monday);


        var Tuesday = list.get(2).getDate();
        GetTuesdayScheduleRequest(sharePref.GetData(DataManager.Phone), Tuesday);
        GetTuesdayShiftRequest(sharePref.GetData(DataManager.Phone), Tuesday);
        Log.d("Tuesday", Tuesday);
        GetDataTuesday(Tuesday);
        GetTuesdayMinimumShiftParWorker(sharePref.GetData(DataManager.Phone), Tuesday);


        var Wednesday = list.get(3).getDate();
        GetWednesdayScheduleRequest(sharePref.GetData(DataManager.Phone), Wednesday);
        GetWednesdayShiftRequest(sharePref.GetData(DataManager.Phone), Wednesday);
        Log.d("Wednesday", Wednesday);
        GetDataWednesday(Wednesday);
        GetWednesdayMinimumShiftParWorker(sharePref.GetData(DataManager.Phone), Wednesday);


        var Thursday = list.get(4).getDate();
        GetThursdayScheduleRequest(sharePref.GetData(DataManager.Phone), Thursday);
        GetThursdayShiftRequest(sharePref.GetData(DataManager.Phone), Thursday);
        Log.d("Thursday", Thursday);
        GetThursdayData(Thursday);
        GetThursdayMinimumShiftParWorker(sharePref.GetData(DataManager.Phone), Thursday);

        var Friday = list.get(5).getDate();
        GetFridayScheduleRequest(sharePref.GetData(DataManager.Phone), Friday);
        GetFridayShiftRequest(sharePref.GetData(DataManager.Phone), Friday);
        Log.d("Friday", Friday);
        GetFridayScheduleData(Friday);
        GetFridayMinimumShiftParWorker(sharePref.GetData(DataManager.Phone), Friday);


        Saturday = list.get(list.size() - 1).getDate();
        GetSaturdayScheduleRequest(sharePref.GetData(DataManager.Phone), Saturday);
        GetSaturdayShiftRequest(sharePref.GetData(DataManager.Phone), Saturday);
        GetSaturdayScheduleData(Saturday);
        GetSaturdayMinimumShiftParWorker(sharePref.GetData(DataManager.Phone), Saturday);


        SetDemoTimer();
    }

    //todo Minimum Shift
    private void GetSundayMinimumShiftParWorker(String Phone, String Date) {
        viewModel.GetRequestEmployeesSundaySchedule(Phone, Date).observe(getActivity(), employeesSundayScheduleModels -> {

            if (employeesSundayScheduleModels != null) {
                for (var data : employeesSundayScheduleModels) {
                    MinimumShiftParWorkers = data.getMinimal_shift_par_workers();
                }
            }
        });
    }

    private void GetMondayMinimumShiftParWorker(String Phone, String Date) {
        viewModel.GetRequestEmployeesMondaySchedule(Phone, Date).observe(getActivity(), employeesMondayScheduleModels -> {
            if (employeesMondayScheduleModels != null) {
                for (var data : employeesMondayScheduleModels) {
                    MinimumShiftParWorkers = data.getMinimal_shift_par_workers();
                }
            }
        });
    }

    private void GetTuesdayMinimumShiftParWorker(String Phone, String Date) {
        viewModel.GetRequestEmployeesTuesdaySchedule(Phone, Date).observe(getActivity(), employeesTuesdayScheduleModels -> {
            if (employeesTuesdayScheduleModels != null) {
                for (var data : employeesTuesdayScheduleModels) {
                    MinimumShiftParWorkers = data.getMinimal_shift_par_workers();
                }
            }
        });
    }

    private void GetWednesdayMinimumShiftParWorker(String Phone, String Date) {
        viewModel.GetRequestWednesdaySchedule(Phone, Date).observe(getActivity(), employeesWednesdayScheduleModels -> {
            if (employeesWednesdayScheduleModels != null) {
                for (var data : employeesWednesdayScheduleModels) {
                    MinimumShiftParWorkers = data.getMinimal_shift_par_workers();
                }
            }
        });
    }

    private void GetThursdayMinimumShiftParWorker(String Phone, String Date) {
        viewModel.GetRequestEmployeesThusdaySchedule(Phone, Date).observe(getActivity(), employeesThusdayScheduleModels -> {
            if (employeesThusdayScheduleModels != null) {
                for (var data : employeesThusdayScheduleModels) {
                    MinimumShiftParWorkers = data.getMinimal_shift_par_workers();
                }
            }
        });
    }

    private void GetFridayMinimumShiftParWorker(String Phone, String Date) {
        viewModel.GetRequestEmployeesFridaySchedule(Phone, Date).observe(getActivity(), employeesFridayScheduleModels -> {
            if (employeesFridayScheduleModels != null) {
                for (var data : employeesFridayScheduleModels) {
                    MinimumShiftParWorkers = data.getMinimal_shift_par_workers();
                }
            }
        });
    }

    public void GetSaturdayMinimumShiftParWorker(String Phone, String Date) {
        viewModel.GetRequestSaturdaySchedule(Phone, Date).observe(getActivity(), employeesSaturdayScheduleModels -> {
            if (employeesSaturdayScheduleModels != null) {
                for (var data : employeesSaturdayScheduleModels) {
                    MinimumShiftParWorkers = data.getMinimal_shift_par_workers();
                }
            }
        });
    }
    //todo Minimum Shift


    private void SetDemoTimer() {

        try {
            var timestamp = System.currentTimeMillis();
            var calender = Calendar.getInstance(Locale.ENGLISH);
            calender.setTimeInMillis(timestamp);
            var currentdate = DateFormat.format("yyyy-MM-dd", calender).toString();
            var currenttime = DateFormat.format("HH:mm", calender).toString();

            var Date = "2022-10-18";
            var Time = "23:00";


            var simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
            var simpletimeformat = new SimpleDateFormat("HH:mm");

            var currentdate_data = simpledateformat.parse(currentdate);
            var serverdate_data = simpledateformat.parse(sharePref.GetData(DataManager.RegistrationStartDate));

            var current_time_data = simpletimeformat.parse(currenttime);
            var servertime_data = simpletimeformat.parse(sharePref.GetData(DataManager.RegistrationStartTime));

            var comparedate = currentdate_data.compareTo(serverdate_data);
            var comparetime = current_time_data.compareTo(servertime_data);

            if (comparedate >= 0 && comparetime >= 0) {
                binding.ToregisterBtn.setVisibility(View.VISIBLE);
                binding.RDateText.setVisibility(View.VISIBLE);

                var sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                var countdate = sharePref.GetData(DataManager.RegistrationEndDate) + " " + sharePref.GetData(DataManager.RegistrationEndTime);
                var now = new Date();
                try {
                    var Fdate = sdf.parse(countdate);

                    var timeofcurrent = now.getTime();
                    var feturedate = Fdate.getTime();
                    var counttime = feturedate - timeofcurrent;
                    binding.CountdownView.start(counttime);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else {
                binding.RDateText.setVisibility(View.GONE);
                binding.ToregisterBtn.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void InitView() {
        previousSundayAdapter = new PreviousSundayAdapter();
        binding.SundayRecyclerView.setHasFixedSize(true);
        binding.SundayRecyclerView.setAdapter(previousSundayAdapter);

        previousMondayAdapter = new PreviousMondayAdapter();
        binding.MondayRecyclerView.setHasFixedSize(true);
        binding.MondayRecyclerView.setAdapter(previousMondayAdapter);


        previousTuesdayAdapter = new PreviousTuesdayAdapter();
        binding.TuesdayRecyclerView.setHasFixedSize(true);
        binding.TuesdayRecyclerView.setAdapter(previousTuesdayAdapter);

        previousWednesdayAdapter = new PreviousWednesdayAdapter();
        binding.WednesdayRecyclerView.setHasFixedSize(true);
        binding.WednesdayRecyclerView.setAdapter(previousWednesdayAdapter);

        previousThursdayAdapter = new PreviousThursdayAdapter();
        binding.ThursdayRecyclerView.setHasFixedSize(true);
        binding.ThursdayRecyclerView.setAdapter(previousThursdayAdapter);

        previousFridayAdapter = new PreviousFridayAdapter();
        binding.FridayRecyclerView.setHasFixedSize(true);
        binding.FridayRecyclerView.setAdapter(previousFridayAdapter);

        previousSaturdayAdapter = new PreviousSaturdayAdapter();
        binding.SaturdayRecyclerView.setHasFixedSize(true);
        binding.SaturdayRecyclerView.setAdapter(previousSaturdayAdapter);

        //todo onclick button
        binding.ToregisterBtn.setOnClickListener(view -> {
            HandleActivity.GotoEmployeesShifts(getActivity());
        });
    }

    private void SetName() {
        viewModel.EmployeesProfileData().observe(getActivity(), employeesModel -> {
            if (employeesModel != null) {
                binding.setName(getResources().getString(R.string.Welcome) + " " + employeesModel.getName());
            }
        });
    }


    private void GetSundayScheduleRequest(String PhoneNumber, String Date) {
        viewModel.GetMySchedule(PhoneNumber, Date).observe(getActivity(), myScheduleModels -> {
            if (myScheduleModels != null) {
                for (var model : myScheduleModels) {
                    sharePref.SetData(DataManager.RegistrationStartDate, model.getRegistrationStartDate());
                    sharePref.SetData(DataManager.RegistrationStartTime, model.getRegistrationStartTime());

                    sharePref.SetData(DataManager.RegistrationEndDate, model.getRegistrationEndDate());
                    sharePref.SetData(DataManager.RegistrationEndTime, model.getRegistrationEndTime());
                }
            }
        });
    }

    private void GetMondayScheduleRequest(String PhoneNumber, String Date) {
        viewModel.GetMySchedule(PhoneNumber, Date).observe(getActivity(), myScheduleModels -> {
            if (myScheduleModels != null) {
                for (var model : myScheduleModels) {
                    sharePref.SetData(DataManager.RegistrationStartDate, model.getRegistrationStartDate());
                    sharePref.SetData(DataManager.RegistrationStartTime, model.getRegistrationStartTime());

                    sharePref.SetData(DataManager.RegistrationEndDate, model.getRegistrationEndDate());
                    sharePref.SetData(DataManager.RegistrationEndTime, model.getRegistrationEndTime());
                }
            }
        });
    }

    private void GetTuesdayScheduleRequest(String PhoneNumber, String Date) {
        viewModel.GetMySchedule(PhoneNumber, Date).observe(getActivity(), myScheduleModels -> {
            if (myScheduleModels != null) {
                for (var model : myScheduleModels) {
                    sharePref.SetData(DataManager.RegistrationStartDate, model.getRegistrationStartDate());
                    sharePref.SetData(DataManager.RegistrationStartTime, model.getRegistrationStartTime());

                    sharePref.SetData(DataManager.RegistrationEndDate, model.getRegistrationEndDate());
                    sharePref.SetData(DataManager.RegistrationEndTime, model.getRegistrationEndTime());
                }
            }
        });
    }

    private void GetWednesdayScheduleRequest(String PhoneNumber, String Date) {
        viewModel.GetMySchedule(PhoneNumber, Date).observe(getActivity(), myScheduleModels -> {
            if (myScheduleModels != null) {

                for (var model : myScheduleModels) {
                    sharePref.SetData(DataManager.RegistrationStartDate, model.getRegistrationStartDate());
                    sharePref.SetData(DataManager.RegistrationStartTime, model.getRegistrationStartTime());

                    sharePref.SetData(DataManager.RegistrationEndDate, model.getRegistrationEndDate());
                    sharePref.SetData(DataManager.RegistrationEndTime, model.getRegistrationEndTime());
                }
            }
        });
    }

    private void GetThursdayScheduleRequest(String PhoneNumber, String Date) {
        viewModel.GetMySchedule(PhoneNumber, Date).observe(getActivity(), myScheduleModels -> {
            if (myScheduleModels != null) {
                for (var model : myScheduleModels) {
                    sharePref.SetData(DataManager.RegistrationStartDate, model.getRegistrationStartDate());
                    sharePref.SetData(DataManager.RegistrationStartTime, model.getRegistrationStartTime());

                    sharePref.SetData(DataManager.RegistrationEndDate, model.getRegistrationEndDate());
                    sharePref.SetData(DataManager.RegistrationEndTime, model.getRegistrationEndTime());
                }
            }
        });
    }

    private void GetFridayScheduleRequest(String PhoneNumber, String Date) {
        viewModel.GetMySchedule(PhoneNumber, Date).observe(getActivity(), myScheduleModels -> {
            if (myScheduleModels != null) {
                for (var model : myScheduleModels) {
                    sharePref.SetData(DataManager.RegistrationStartDate, model.getRegistrationStartDate());
                    sharePref.SetData(DataManager.RegistrationStartTime, model.getRegistrationStartTime());

                    sharePref.SetData(DataManager.RegistrationEndDate, model.getRegistrationEndDate());
                    sharePref.SetData(DataManager.RegistrationEndTime, model.getRegistrationEndTime());
                }
            }
        });
    }

    private void GetSaturdayScheduleRequest(String PhoneNumber, String Date) {
        viewModel.GetMySchedule(PhoneNumber, Date).observe(getActivity(), myScheduleModels -> {
            if (myScheduleModels != null) {
                for (var model : myScheduleModels) {
                    sharePref.SetData(DataManager.RegistrationStartDate, model.getRegistrationStartDate());
                    sharePref.SetData(DataManager.RegistrationStartTime, model.getRegistrationStartTime());

                    sharePref.SetData(DataManager.RegistrationEndDate, model.getRegistrationEndDate());
                    sharePref.SetData(DataManager.RegistrationEndTime, model.getRegistrationEndTime());
                }
            }
        });
    }


    //todo request ------------------------------
    private void GetSundayShiftRequest(String PhoneNumber, String Date) {
        binding.SundayUserRequestRecyclerView.setHasFixedSize(true);
        sundayShiftRequestAdapter = new SundayShiftRequestAdapter();
        binding.SundayUserRequestRecyclerView.setAdapter(sundayShiftRequestAdapter);
        viewModel.SundayScheduleRequestGET(PhoneNumber, Date).observe(getActivity(), new Observer<List<SundayScheduleModel>>() {
            @Override
            public void onChanged(List<SundayScheduleModel> sundayScheduleModels) {
                sundayShiftRequestAdapter.setList(sundayScheduleModels);
                sundayShiftRequestAdapter.notifyDataSetChanged();
                if (sundayScheduleModels != null) {
                    binding.SundayUserRequestText.setVisibility(View.VISIBLE);
                    binding.SundayUserRequestText.setText(DateToDayName.GetDate(Date));
                } else {
                    binding.SundayUserRequestText.setVisibility(View.GONE);
                }
            }
        });
        sundayShiftRequestAdapter.OnCLickEvent((SundayScheduleModel, UID, Date1, ShiftName, RequestForAdditionalHour, Type) -> {
            if (Type.equals(DataManager.Refuse)) {
                progressDialog.ShowProgress(getActivity());
                viewModel.UpdateRequestForShift(DataManager.Phone, Date1, ShiftName, DataManager.Refuse)
                        .observe(getActivity(), aBoolean1 -> {
                            if (aBoolean1) {
                                viewModel.RefuseSchedulePOST(UID, Date1, ShiftName, RequestForAdditionalHour, DataManager.Refuse).observe(getActivity(),
                                        aBoolean2 -> {
                                            if(aBoolean2){
                                                RemoveFromNotConfrimList(SundayScheduleModel.getSenderUID(), UID, Sunday, Saturday);
                                            }else {
                                                progressDialog.CancelDialog();
                                            }

                                        });
                            }else {
                                progressDialog.CancelDialog();
                            }
                        });
            }
            if (Type.equals(DataManager.Confirm)) {
                progressDialog.ShowProgress(getActivity());
                viewModel.AcceptSchedulePOST(UID, Date1, ShiftName, RequestForAdditionalHour, DataManager.Confirm, SundayScheduleModel.getShiftName()).observe(getActivity(), aBoolean -> {
                    ManageUsers(UID, Integer.valueOf(ShiftName), Sunday, Saturday);
                    viewModel.UpdateRequestForShift(sharePref.GetData(DataManager.Phone), Date1, ShiftName, DataManager.Confirm)
                            .observe(getActivity(), aBoolean1 -> {
                                if (aBoolean) {
                                    var model = new MyScheduleDBModel(SundayScheduleModel.getData(), SundayScheduleModel.getShift_start_time(), SundayScheduleModel.getShift_end_time(), SundayScheduleModel.getUID(), SundayScheduleModel.getShiftName(), SundayScheduleModel.getShiftManager());
                                    mySendingScheduleViewModel.InsertSendingSchedule(model);
                                    RemoveFromNotConfrimList(SundayScheduleModel.getSenderUID(), UID, Sunday, Saturday);
                                }else {
                                    progressDialog.CancelDialog();
                                }
                            });
                });
            }
        });
    }

    private void GetMondayShiftRequest(String PhoneNumber, String Date) {
        binding.MondayUserRequestRecyclerView.setHasFixedSize(true);
        mondayShiftRequestAdapter = new MondayShiftRequestAdapter();
        binding.MondayUserRequestRecyclerView.setAdapter(mondayShiftRequestAdapter);

        viewModel.MondayScheduleRequestGET(PhoneNumber, Date).observe(getActivity(), new Observer<List<MondaySchaduleModel>>() {
            @Override
            public void onChanged(List<MondaySchaduleModel> mondaySchaduleModels) {
                mondayShiftRequestAdapter.setList(mondaySchaduleModels);
                mondayShiftRequestAdapter.notifyDataSetChanged();
                if (mondaySchaduleModels == null) {
                    binding.MondayUserRequestText.setVisibility(View.GONE);
                } else {
                    binding.MondayUserRequestText.setText(DateToDayName.GetDate(Date));
                    binding.MondayUserRequestText.setVisibility(View.VISIBLE);
                }
            }
        });
        mondayShiftRequestAdapter.OnCLickEvent((MondayScheduleModel, UID, Date1, ShiftName, IsAdditionHoursNeed, Type) -> {
            if (Type.equals(DataManager.Refuse)) {
                progressDialog.ShowProgress(getActivity());
                viewModel.UpdateRequestForShift(sharePref.GetData(DataManager.Phone), Date1, ShiftName, DataManager.Refuse)
                        .observe(getActivity(), aBoolean1 -> {
                            if (aBoolean1) {
                                viewModel.RefuseSchedulePOST(UID, Date1, ShiftName, IsAdditionHoursNeed, DataManager.Refuse).observe(getActivity(),
                                        aBoolean2 -> {
                                            if (aBoolean2) {
                                                RemoveFromNotConfrimList(MondayScheduleModel.getSenderUID(), UID, Sunday, Saturday);
                                            }else {
                                                progressDialog.CancelDialog();
                                            }
                                        });
                            }else {
                                progressDialog.CancelDialog();
                            }
                        });
            }
            if (Type.equals(DataManager.Confirm)) {
                progressDialog.ShowProgress(getActivity());
                viewModel.AcceptSchedulePOST(UID, Date1, ShiftName, IsAdditionHoursNeed, DataManager.Confirm, MondayScheduleModel.getShiftName()).observe(getActivity(), aBoolean -> {
                    ManageUsers(UID, Integer.valueOf(ShiftName), Sunday, Saturday);
                    viewModel.UpdateRequestForShift(sharePref.GetData(DataManager.Phone), Date1, ShiftName, DataManager.Confirm)
                            .observe(getActivity(), aBoolean1 -> {
                                if (aBoolean) {
                                    var model = new MyScheduleDBModel(MondayScheduleModel.getData(), MondayScheduleModel.getShift_start_time(), MondayScheduleModel.getShift_end_time(), MondayScheduleModel.getUID(), MondayScheduleModel.getShiftName(), MondayScheduleModel.getShiftManager());
                                    mySendingScheduleViewModel.InsertSendingSchedule(model);
                                    RemoveFromNotConfrimList(MondayScheduleModel.getSenderUID(), UID, Sunday, Saturday);
                                }else {
                                    progressDialog.CancelDialog();
                                }
                            });
                });
            }
        });
    }

    private void GetTuesdayShiftRequest(String PhoneNumber, String Date) {
        binding.TuesdayUserRequestRecyclerView.setHasFixedSize(true);
        tuesdayShiftRequestAdapter = new TuesdayShiftRequestAdapter();
        binding.TuesdayUserRequestRecyclerView.setAdapter(tuesdayShiftRequestAdapter);
        viewModel.TuesdayScheduleRequestGET(PhoneNumber, Date).observe(getActivity(), new Observer<List<TuesdaySchaduleModel>>() {
            @Override
            public void onChanged(List<TuesdaySchaduleModel> tuesdaySchaduleModels) {
                tuesdayShiftRequestAdapter.setList(tuesdaySchaduleModels);
                tuesdayShiftRequestAdapter.notifyDataSetChanged();
                if (tuesdaySchaduleModels != null) {
                    binding.TuesdayUserRequestText.setText(DateToDayName.GetDate(Date));
                    binding.TuesdayUserRequestText.setVisibility(View.VISIBLE);
                } else {
                    binding.TuesdayUserRequestText.setVisibility(View.GONE);
                }
            }
        });
        tuesdayShiftRequestAdapter.OnCLickEvent((tuesdaySchaduleModel, UID, Date1, ShiftName, RequestForAdditionalHour, Type) -> {
            if (Type.equals(DataManager.Refuse)) {
                progressDialog.ShowProgress(getActivity());
                viewModel.UpdateRequestForShift(sharePref.GetData(DataManager.Phone), Date1, ShiftName, DataManager.Refuse)
                        .observe(getActivity(), aBoolean1 -> {
                            if (aBoolean1) {
                                viewModel.RefuseSchedulePOST(UID, Date1, ShiftName, RequestForAdditionalHour, DataManager.Refuse).observe(getActivity(),
                                        aBoolean2 -> {
                                            if (aBoolean2) {
                                                RemoveFromNotConfrimList(tuesdaySchaduleModel.getSenderUID(), UID, Sunday, Saturday);
                                            } else {
                                                progressDialog.CancelDialog();
                                            }
                                        });
                            }else {
                                progressDialog.CancelDialog();
                            }
                        });
            }
            if (Type.equals(DataManager.Confirm)) {
                progressDialog.ShowProgress(getActivity());
                viewModel.AcceptSchedulePOST(UID, Date1, ShiftName, RequestForAdditionalHour, DataManager.Confirm, tuesdaySchaduleModel.getShiftName()).observe(getActivity(), aBoolean -> {
                    ManageUsers(UID, Integer.valueOf(ShiftName), Sunday, Saturday);
                    viewModel.UpdateRequestForShift(sharePref.GetData(DataManager.Phone), Date1, ShiftName, DataManager.Confirm)
                            .observe(getActivity(), aBoolean1 -> {
                                if (aBoolean) {
                                    var model = new MyScheduleDBModel(tuesdaySchaduleModel.getData(), tuesdaySchaduleModel.getShift_start_time(), tuesdaySchaduleModel.getShift_end_time(), tuesdaySchaduleModel.getUID(), tuesdaySchaduleModel.getShiftName(), tuesdaySchaduleModel.getShiftManager());
                                    mySendingScheduleViewModel.InsertSendingSchedule(model);
                                    RemoveFromNotConfrimList(tuesdaySchaduleModel.getSenderUID(), UID, Sunday, Saturday);
                                } else {
                                    progressDialog.CancelDialog();
                                }
                            });
                });
            }
        });
    }

    private void GetWednesdayShiftRequest(String PhoneNumber, String Date) {
        binding.WednesdayUserRequestRecyclerView.setHasFixedSize(true);
        wednessdayShiftRequestAdapter = new WednessdayShiftRequestAdapter();
        binding.WednesdayUserRequestRecyclerView.setAdapter(wednessdayShiftRequestAdapter);
        viewModel.WednesdayScheduleRequestGET(PhoneNumber, Date).observe(getActivity(), new Observer<List<WednesdaySchaduleModel>>() {
            @Override
            public void onChanged(List<WednesdaySchaduleModel> wednesdaySchaduleModels) {
                wednessdayShiftRequestAdapter.setList(wednesdaySchaduleModels);
                wednessdayShiftRequestAdapter.notifyDataSetChanged();
                if (wednesdaySchaduleModels != null) {
                    binding.WednesdayUserRequestText.setText(DateToDayName.GetDate(Date));
                    binding.WednesdayUserRequestText.setVisibility(View.VISIBLE);
                } else {
                    binding.WednesdayUserRequestText.setVisibility(View.GONE);
                }
            }
        });

        wednessdayShiftRequestAdapter.OnCLickEvent((wednesdaySchaduleModel, UID, Date1, ShiftName, RequestForAdditionalHour, Type) -> {
            if (Type.equals(DataManager.Refuse)) {
                progressDialog.ShowProgress(getActivity());
                viewModel.UpdateRequestForShift(sharePref.GetData(DataManager.Phone), Date1, ShiftName, DataManager.Refuse)
                        .observe(getActivity(), aBoolean1 -> {
                            if (aBoolean1) {
                                viewModel.RefuseSchedulePOST(UID, Date1, ShiftName, RequestForAdditionalHour, DataManager.Refuse).observe(getActivity(),
                                        aBoolean2 -> {
                                            if (aBoolean2) {
                                                RemoveFromNotConfrimList(wednesdaySchaduleModel.getSenderUID(), UID, Sunday, Saturday);
                                            } else {
                                                progressDialog.CancelDialog();
                                            }

                                        });
                            }else {
                                progressDialog.CancelDialog();
                            }
                        });
            }
            if (Type.equals(DataManager.Confirm)) {
                progressDialog.ShowProgress(getActivity());
                viewModel.AcceptSchedulePOST(UID, Date1, ShiftName, RequestForAdditionalHour, DataManager.Confirm, wednesdaySchaduleModel.getShiftName()).observe(getActivity(), aBoolean -> {
                    ManageUsers(UID, Integer.valueOf(ShiftName), Sunday, Saturday);
                    viewModel.UpdateRequestForShift(sharePref.GetData(DataManager.Phone), Date1, ShiftName, DataManager.Confirm)
                            .observe(getActivity(), aBoolean1 -> {
                                if (aBoolean) {
                                    var model = new MyScheduleDBModel(wednesdaySchaduleModel.getData(), wednesdaySchaduleModel.getShift_start_time(), wednesdaySchaduleModel.getShift_end_time(), wednesdaySchaduleModel.getUID(), wednesdaySchaduleModel.getShiftName(), wednesdaySchaduleModel.getShiftManager());
                                    mySendingScheduleViewModel.InsertSendingSchedule(model);
                                    RemoveFromNotConfrimList(wednesdaySchaduleModel.getSenderUID(), UID, Sunday, Saturday);
                                }
                            });
                });
            }
        });

    }

    private void ManageUsers(String UID, int Count, String StartDate, String EndDate) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                viewModel.GetSundayAllRegisterUser(sharePref.GetData(DataManager.UID), Sunday).observe(getActivity(), new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        viewModel.EmployeesProfileData().observe(getActivity(),
                                employeesModel -> {
                                    if (employeesModel != null) {
                                        if (MinimumShiftParWorkers != null) {
                                            viewModel.ManageUser(UID, StartDate, EndDate, employeesModel.getPhone(), employeesModel.getName(), String.valueOf(integer), MinimumShiftParWorkers).observe(getActivity(), aBoolean -> {
                                                progressDialog.CancelDialog();
                                            });
                                        }
                                    }
                                });
                    }
                });
            }
        }, 3000);

    }


    private void GetThursdayShiftRequest(String PhoneNumber, String Date) {
        binding.ThursdayUserRequestRecyclerView.setHasFixedSize(true);
        thursdayShiftRequestAdapter = new ThursdayShiftRequestAdapter();
        binding.ThursdayUserRequestRecyclerView.setAdapter(thursdayShiftRequestAdapter);
        viewModel.ThursdayScheduleRequestGET(PhoneNumber, Date).observe(getActivity(), new Observer<List<ThursdaySchaduleModel>>() {
            @Override
            public void onChanged(List<ThursdaySchaduleModel> thursdaySchaduleModels) {
                thursdayShiftRequestAdapter.setList(thursdaySchaduleModels);
                thursdayShiftRequestAdapter.notifyDataSetChanged();
                if (thursdaySchaduleModels != null) {
                    binding.ThursdayUserRequestText.setText(DateToDayName.GetDate(Date));
                    binding.ThursdayUserRequestText.setVisibility(View.VISIBLE);
                } else {
                    binding.ThursdayUserRequestText.setVisibility(View.GONE);
                }
            }
        });
        thursdayShiftRequestAdapter.OnCLickEvent((thursdaySchaduleModel, UID, Date1, ShiftName, RequestForAdditionalHour, Type) -> {
            if (Type.equals(DataManager.Refuse)) {
                progressDialog.ShowProgress(getActivity());
                viewModel.UpdateRequestForShift(sharePref.GetData(DataManager.Phone), Date1, ShiftName, DataManager.Refuse)
                        .observe(getActivity(), aBoolean1 -> {
                            if (aBoolean1) {
                                viewModel.RefuseSchedulePOST(UID, Date1, ShiftName, RequestForAdditionalHour, DataManager.Refuse).observe(getActivity(),
                                        aBoolean2 -> {
                                            if (aBoolean2) {
                                                RemoveFromNotConfrimList(thursdaySchaduleModel.getSenderUID(), UID, Sunday, Saturday);
                                            } else {
                                                progressDialog.CancelDialog();
                                            }
                                        });
                            }else {
                                progressDialog.CancelDialog();
                            }
                        });
            }
            if (Type.equals(DataManager.Confirm)) {
                progressDialog.ShowProgress(getActivity());
                viewModel.AcceptSchedulePOST(UID, Date1, ShiftName, RequestForAdditionalHour, DataManager.Confirm, thursdaySchaduleModel.getShiftName()).observe(getActivity(), aBoolean -> {
                    ManageUsers(UID, Integer.valueOf(ShiftName), Sunday, Saturday);
                    viewModel.UpdateRequestForShift(sharePref.GetData(DataManager.Phone), Date1, ShiftName, DataManager.Confirm)
                            .observe(getActivity(), aBoolean1 -> {
                                if (aBoolean) {
                                    var model = new MyScheduleDBModel(thursdaySchaduleModel.getData(), thursdaySchaduleModel.getShift_start_time(), thursdaySchaduleModel.getShift_end_time(), thursdaySchaduleModel.getUID(), thursdaySchaduleModel.getShiftName(), thursdaySchaduleModel.getShiftManager());
                                    mySendingScheduleViewModel.InsertSendingSchedule(model);
                                    RemoveFromNotConfrimList(thursdaySchaduleModel.getSenderUID(), UID, Sunday, Saturday);
                                }else {
                                    progressDialog.CancelDialog();
                                }
                            });
                });
            }
        });
    }

    private void GetFridayShiftRequest(String PhoneNumber, String Date) {
        binding.FridayUserRequestRecyclerView.setHasFixedSize(true);
        fridayShiftRequestAdapter = new FridayShiftRequestAdapter();
        binding.FridayUserRequestRecyclerView.setAdapter(fridayShiftRequestAdapter);
        viewModel.FridayScheduleRequestGET(PhoneNumber, Date).observe(getActivity(), new Observer<List<FridaySchaduleModel>>() {
            @Override
            public void onChanged(List<FridaySchaduleModel> fridaySchaduleModels) {
                fridayShiftRequestAdapter.setList(fridaySchaduleModels);
                fridayShiftRequestAdapter.notifyDataSetChanged();
                if (fridaySchaduleModels != null) {
                    binding.FridayUserRequestText.setVisibility(View.VISIBLE);
                    binding.FridayUserRequestText.setText(DateToDayName.GetDate(Date));
                } else {
                    binding.FridayUserRequestText.setVisibility(View.GONE);
                }
            }
        });
        fridayShiftRequestAdapter.OnCLickEvent((fridaySchaduleModel, UID, Date1, ShiftName, RequestForAdditionalHour, Type) -> {
            if (Type.equals(DataManager.Refuse)) {
                progressDialog.ShowProgress(getActivity());
                viewModel.UpdateRequestForShift(sharePref.GetData(DataManager.Phone), Date1, ShiftName, DataManager.Refuse)
                        .observe(getActivity(), aBoolean1 -> {
                            if (aBoolean1) {
                                viewModel.RefuseSchedulePOST(UID, Date1, ShiftName, RequestForAdditionalHour, DataManager.Refuse).observe(getActivity(),
                                        aBoolean2 -> {
                                            if (aBoolean2) {
                                                RemoveFromNotConfrimList(fridaySchaduleModel.getSenderUID(), UID, Sunday, Saturday);
                                            } else {
                                                progressDialog.CancelDialog();
                                            }
                                        });
                            }else {
                                progressDialog.CancelDialog();
                            }
                        });
            }
            if (Type.equals(DataManager.Confirm)) {
                progressDialog.ShowProgress(getActivity());
                viewModel.AcceptSchedulePOST(UID, Date1, ShiftName, RequestForAdditionalHour, DataManager.Confirm, fridaySchaduleModel.getShiftName()).observe(getActivity(), aBoolean -> {
                    ManageUsers(UID, Integer.valueOf(ShiftName), Sunday, Saturday);
                    viewModel.UpdateRequestForShift(sharePref.GetData(DataManager.Phone), Date1, ShiftName, DataManager.Confirm)
                            .observe(getActivity(), aBoolean1 -> {
                                if (aBoolean) {
                                    var model = new MyScheduleDBModel(fridaySchaduleModel.getData(), fridaySchaduleModel.getShift_start_time(), fridaySchaduleModel.getShift_end_time(), fridaySchaduleModel.getUID(), fridaySchaduleModel.getShiftName(), fridaySchaduleModel.getShiftManager());
                                    mySendingScheduleViewModel.InsertSendingSchedule(model);
                                    RemoveFromNotConfrimList(fridaySchaduleModel.getSenderUID(), UID, Sunday, Saturday);
                                } else {
                                    progressDialog.CancelDialog();
                                }
                            });
                });
            }
        });
    }

    private void GetSaturdayShiftRequest(String PhoneNumber, String Date) {
        binding.SaturdayUserRequestRecyclerView.setHasFixedSize(true);
        saturdayShiftRequestAdapter = new SaturdayShiftRequestAdapter();
        binding.SaturdayUserRequestRecyclerView.setAdapter(saturdayShiftRequestAdapter);
        viewModel.SaturdayScheduleRequestGET(PhoneNumber, Date).observe(getActivity(), new Observer<List<SaturdaySchaduleModel>>() {
            @Override
            public void onChanged(List<SaturdaySchaduleModel> saturdaySchaduleModels) {
                saturdayShiftRequestAdapter.setList(saturdaySchaduleModels);
                saturdayShiftRequestAdapter.notifyDataSetChanged();
                if (saturdaySchaduleModels != null) {
                    binding.SaturdayUserRequestText.setVisibility(View.VISIBLE);
                    binding.SaturdayUserRequestText.setText(DateToDayName.GetDate(Date));
                } else {
                    binding.SaturdayUserRequestText.setVisibility(View.GONE);
                }
            }
        });


        saturdayShiftRequestAdapter.OnCLickEvent((saturdaySchaduleModel, UID, Date1, ShiftName, RequestForAdditionalHour, Type) -> {
            if (Type.equals(DataManager.Refuse)) {
                progressDialog.ShowProgress(getActivity());
                viewModel.UpdateRequestForShift(sharePref.GetData(DataManager.Phone), Date1, ShiftName, DataManager.Refuse)
                        .observe(getActivity(), aBoolean1 -> {
                            if (aBoolean1) {

                                viewModel.RefuseSchedulePOST(UID, Date1, ShiftName, RequestForAdditionalHour, DataManager.Refuse).observe(getActivity(),
                                        aBoolean2 -> {
                                            if (aBoolean2) {
                                                RemoveFromNotConfrimList(saturdaySchaduleModel.getSenderUID(), UID, Sunday, Saturday);
                                            } else {
                                                progressDialog.CancelDialog();
                                            }
                                        });
                            }else {
                                progressDialog.CancelDialog();
                            }
                        });
            }

            if (Type.equals(DataManager.Confirm)) {
                progressDialog.ShowProgress(getActivity());
                viewModel.AcceptSchedulePOST(UID, Date1, ShiftName, RequestForAdditionalHour, DataManager.Confirm, saturdaySchaduleModel.getShiftName()).observe(getActivity(), aBoolean -> {
                    ManageUsers(UID, Integer.valueOf(ShiftName), Sunday, Saturday);
                    viewModel.UpdateRequestForShift(sharePref.GetData(DataManager.Phone), Date1, ShiftName, DataManager.Confirm)
                            .observe(getActivity(), aBoolean1 -> {
                                if (aBoolean) {
                                    var model = new MyScheduleDBModel(saturdaySchaduleModel.getData(), saturdaySchaduleModel.getShift_start_time(), saturdaySchaduleModel.getShift_end_time(), saturdaySchaduleModel.getUID(), saturdaySchaduleModel.getShiftName(), saturdaySchaduleModel.getShiftManager());
                                    mySendingScheduleViewModel.InsertSendingSchedule(model);
                                    RemoveFromNotConfrimList(saturdaySchaduleModel.getSenderUID(), UID, Sunday, Saturday);
                                }else {
                                    progressDialog.CancelDialog();
                                }
                            });
                });
            }
        });
    }
    //todo request ------------------------------

    //todo not confrim
    private void RemoveFromNotConfrimList(String SenderID, String UID, String StartDate, String EndDate) {
        viewModel.DeleteNotConfrimUser(SenderID, UID, StartDate, EndDate).observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    progressDialog.CancelDialog();
                }else {
                    progressDialog.CancelDialog();
                }
            }
        });
    }
}