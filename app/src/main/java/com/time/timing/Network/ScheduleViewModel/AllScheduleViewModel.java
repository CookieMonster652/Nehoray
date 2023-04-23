package com.time.timing.Network.ScheduleViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.time.timing.FridaySchaduleModel;
import com.time.timing.MondaySchaduleModel;
import com.time.timing.Network.AllSchedule.FridayScheduleGET;
import com.time.timing.Network.AllSchedule.MondayScheduleGET;
import com.time.timing.Network.AllSchedule.SaturdayScheduleGET;
import com.time.timing.Network.AllSchedule.SundayScheduleGET;
import com.time.timing.Network.AllSchedule.ThursdayScheduleGET;
import com.time.timing.Network.AllSchedule.TuesdayScheduleGET;
import com.time.timing.Network.AllSchedule.WednesdayScheduleGET;
import com.time.timing.SaturdaySchaduleModel;
import com.time.timing.SundayScheduleModel;
import com.time.timing.ThursdaySchaduleModel;
import com.time.timing.TuesdaySchaduleModel;
import com.time.timing.WednesdaySchaduleModel;

import java.util.List;

public class AllScheduleViewModel extends AndroidViewModel {

    private SaturdayScheduleGET saturdayScheduleGET;
    private SundayScheduleGET sundayScheduleGET;
    private MondayScheduleGET mondayScheduleGET;
    private TuesdayScheduleGET tuesdayScheduleGET;
    private WednesdayScheduleGET wednesdayScheduleGET;
    private ThursdayScheduleGET thursdayScheduleGET;
    private FridayScheduleGET fridayScheduleGET;

    public AllScheduleViewModel(@NonNull Application application) {
        super(application);

        saturdayScheduleGET = new SaturdayScheduleGET(application);
        sundayScheduleGET = new SundayScheduleGET(application);
        mondayScheduleGET = new MondayScheduleGET(application);
        tuesdayScheduleGET = new TuesdayScheduleGET(application);
        wednesdayScheduleGET = new WednesdayScheduleGET(application);
        thursdayScheduleGET = new ThursdayScheduleGET(application);
        fridayScheduleGET = new FridayScheduleGET(application);
    }

    public LiveData<List<SaturdaySchaduleModel>> GetSaturdaySchedule(String Date){
        return saturdayScheduleGET.GetSaturdaySchedule(Date);
    }
    public LiveData<List<SundayScheduleModel>> GetSundatSchedule(String Date){
        return sundayScheduleGET.GetSundaySchedule(Date);
    }
    public LiveData<List<MondaySchaduleModel>> GetMondaySchedule(String Date){
        return mondayScheduleGET.GetMondaySchedule(Date);
    }
    public LiveData<List<TuesdaySchaduleModel>> GetTuesdaySchedule(String Date){
        return tuesdayScheduleGET.GetThesdaySchedule(Date);
    }
    public LiveData<List<WednesdaySchaduleModel>> GetWednesdaySchedule(String Date){
        return wednesdayScheduleGET.GetWednesdaySchedule(Date);
    }
    public LiveData<List<ThursdaySchaduleModel>> GetThursdaySchedule(String Date){
        return thursdayScheduleGET.GetThursdaySchedule(Date);
    }
    public LiveData<List<FridaySchaduleModel>> GetFridaySchedule(String Date){
        return fridayScheduleGET.GetFridaySchedule(Date);
    }
}
