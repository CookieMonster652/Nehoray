package com.time.timing.Network.ViewModel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.time.timing.Data.ManageUserShiftModel;
import com.time.timing.EmployeesModel;
import com.time.timing.FridaySchaduleModel;
import com.time.timing.ManagerProfileModel;
import com.time.timing.Model.AllRegisterUserDataModel;
import com.time.timing.Model.AllRegisteredUsersModel;
import com.time.timing.Model.RefuseUserModel;
import com.time.timing.MondaySchaduleModel;
import com.time.timing.MyScheduleModel;
import com.time.timing.Network.AcceptScheduleRequestPOST;
import com.time.timing.Network.AllEmployeesGET;
import com.time.timing.Network.AllPreviousSchedule.PreviousScheduleFridayGET;
import com.time.timing.Network.AllPreviousSchedule.PreviousScheduleMondayGET;
import com.time.timing.Network.AllPreviousSchedule.PreviousScheduleSaturdayGET;
import com.time.timing.Network.AllPreviousSchedule.PreviousScheduleSundayGET;
import com.time.timing.Network.AllPreviousSchedule.PreviousScheduleThursdayGET;
import com.time.timing.Network.AllPreviousSchedule.PreviousScheduleTuesdayGET;
import com.time.timing.Network.AllPreviousSchedule.PreviousScheduleWednesdayGET;
import com.time.timing.Network.AllRegisterUserGET;
import com.time.timing.Network.AllRegisteredUsersGET;
import com.time.timing.Network.BusinessLogoUploadPOST;
import com.time.timing.Network.CheckUserLogin;
import com.time.timing.Network.CheckUserProfileExistsGET;
import com.time.timing.Network.DeleteNotConrimRemoveUser;
import com.time.timing.Network.EditEmployeesProfilePOST;
import com.time.timing.Network.EmployeesProfileDataPOST;
import com.time.timing.Network.EmployeesProfileGET;
import com.time.timing.Network.EmployeesProfilePicUploadPOST;
import com.time.timing.Network.EmployeesScheduleRequest.EmployeesFridayScheduleGET;
import com.time.timing.Network.EmployeesScheduleRequest.EmployeesMondayScheduleGET;
import com.time.timing.Network.EmployeesScheduleRequest.EmployeesSaturdayScheduleGET;
import com.time.timing.Network.EmployeesScheduleRequest.EmployeesSundayScheduleGET;
import com.time.timing.Network.EmployeesScheduleRequest.EmployeesThusdayScheduleGET;
import com.time.timing.Network.EmployeesScheduleRequest.EmployeesTuesdayScheduleGET;
import com.time.timing.Network.EmployeesScheduleRequest.EmployeesWednesdayScheduleGET;
import com.time.timing.Network.ManageRegistationShift;
import com.time.timing.Network.ManageUsersPOST;
import com.time.timing.Network.ManagerProfileGET;
import com.time.timing.Network.ManagerProfilePOST;
import com.time.timing.Network.ManagerRequest.IsScheduleUserExistsGET;
import com.time.timing.Network.MoveToAdditionalHoursPOST;
import com.time.timing.Network.MyAdditionalHoursScheduleUserGET;
import com.time.timing.Network.MyScheduleDataListGET;
import com.time.timing.Network.MyScheduleGET;
import com.time.timing.Network.MyScheduleListGET;
import com.time.timing.Network.MySchedulePOST;
import com.time.timing.Network.MyScheduleRequestExists.FridayRequestExists;
import com.time.timing.Network.MyScheduleRequestExists.MondayRequestExists;
import com.time.timing.Network.MyScheduleRequestExists.SaturdayRequestExists;
import com.time.timing.Network.MyScheduleRequestExists.SundayRequestExists;
import com.time.timing.Network.MyScheduleRequestExists.ThursdayRequestExists;
import com.time.timing.Network.MyScheduleRequestExists.TuesdayRequestExists;
import com.time.timing.Network.MyScheduleRequestExists.WednesdayRequestExists;
import com.time.timing.Network.MyScheduleUsersGET;
import com.time.timing.Network.MyUserRegShiftPOST;
import com.time.timing.Network.RefuseScheduleRequestPOST;
import com.time.timing.Network.RefuseUserGET;
import com.time.timing.Network.RefuseUserPOST;
import com.time.timing.Network.RegisterUserCount.FridayUserRegisterCount;
import com.time.timing.Network.RegisterUserCount.MondayUserRegisterCount;
import com.time.timing.Network.RegisterUserCount.SaturdayUserRegisterCount;
import com.time.timing.Network.RegisterUserCount.SundayUserRegisterCount;
import com.time.timing.Network.RegisterUserCount.ThursdayUserRegisterCount;
import com.time.timing.Network.RegisterUserCount.TuesdayUserRegisterCount;
import com.time.timing.Network.RegisterUserCount.WednesdayUserRegisterCount;
import com.time.timing.Network.RemoveMySchedule;
import com.time.timing.Network.RequestEmployeesScheduleGET;
import com.time.timing.Network.RequestForShiftUpdate.RequestForShiftUpdatePOST;
import com.time.timing.Network.RequestScheduleDocumentGET;
import com.time.timing.Network.SchedulePOST;
import com.time.timing.Network.ScheduleRequestPOST;
import com.time.timing.Network.SearchContactGET;
import com.time.timing.Network.SendRequestPOST;
import com.time.timing.Network.SendScheduleRequest;
import com.time.timing.Network.SignleScheduleGET;
import com.time.timing.Network.UserScheduleRequest.FridayScheduleRequestGET;
import com.time.timing.Network.UserScheduleRequest.MondayScheduleRequestGET;
import com.time.timing.Network.UserScheduleRequest.SaturdayScheduleRequestGET;
import com.time.timing.Network.UserScheduleRequest.SundayScheduleRequestGET;
import com.time.timing.Network.UserScheduleRequest.ThursdayScheduleRequestGET;
import com.time.timing.Network.UserScheduleRequest.TuesdayScheduleRequestGET;
import com.time.timing.Network.UserScheduleRequest.WednesdayScheduleRequestGET;
import com.time.timing.RefuseModel;
import com.time.timing.SaturdaySchaduleModel;
import com.time.timing.SearchContactModel;
import com.time.timing.SundayScheduleModel;
import com.time.timing.ThursdaySchaduleModel;
import com.time.timing.TuesdaySchaduleModel;
import com.time.timing.UI.Employees.Model.EmployeesFridayScheduleModel;
import com.time.timing.UI.Employees.Model.EmployeesMondayScheduleModel;
import com.time.timing.UI.Employees.Model.EmployeesSaturdayScheduleModel;
import com.time.timing.UI.Employees.Model.EmployeesSundayScheduleModel;
import com.time.timing.UI.Employees.Model.EmployeesThusdayScheduleModel;
import com.time.timing.UI.Employees.Model.EmployeesTuesdayScheduleModel;
import com.time.timing.UI.Employees.Model.EmployeesWednesdayScheduleModel;
import com.time.timing.UserModel;
import com.time.timing.WednesdaySchaduleModel;

import java.util.List;

public class ViewModel extends AndroidViewModel {

    private CheckUserLogin checkUserLogin;
    private CheckUserProfileExistsGET checkUserProfileExistsGET;
    private BusinessLogoUploadPOST businessLogoUploadPOST;
    private ManagerProfilePOST managerProfilePOST;
    private EmployeesProfilePicUploadPOST employeesProfilePicUploadPOST;
    private EmployeesProfileDataPOST employeesProfileDataPOST;
    private ManagerProfileGET managerProfileGET;
    private AllEmployeesGET allEmployeesGET;
    private SearchContactGET searchContactGET;
    private SchedulePOST schedulePOST;
    private EmployeesProfileGET employeesProfileGET;
    private EditEmployeesProfilePOST editEmployeesProfilePOST;
    private SendScheduleRequest sendScheduleRequest;
    private RequestEmployeesScheduleGET requestEmployeesScheduleGET;


    private EmployeesFridayScheduleGET employeesFridayScheduleGET;
    private EmployeesMondayScheduleGET employeesMondayScheduleGET;
    private EmployeesSundayScheduleGET employeesSundayScheduleGET;
    private EmployeesThusdayScheduleGET employeesThusdayScheduleGET;
    private EmployeesTuesdayScheduleGET employeesTuesdayScheduleGET;
    private EmployeesWednesdayScheduleGET employeesWednesdayScheduleGET;
    private EmployeesSaturdayScheduleGET employeesSaturdayScheduleGET;

    private RequestScheduleDocumentGET requestScheduleDocumentGET;
    private MySchedulePOST mySchedulePOST;
    private AcceptScheduleRequestPOST acceptScheduleRequestPOST;
    private RefuseScheduleRequestPOST refuseScheduleRequestPOST;

    private MyScheduleUsersGET myScheduleUsersGET;
    private SignleScheduleGET signleScheduleGET;
    private MyScheduleListGET myScheduleListGET;
    private MyScheduleDataListGET myScheduleDataListGET;


    //todo previous week
    private PreviousScheduleSundayGET previousScheduleSundayGET;
    private PreviousScheduleMondayGET previousScheduleMondayGET;
    private PreviousScheduleTuesdayGET previousScheduleTuesdayGET;
    private PreviousScheduleWednesdayGET previousScheduleWednesdayGET;
    private PreviousScheduleThursdayGET previousScheduleThursdayGET;
    private PreviousScheduleFridayGET previousScheduleFridayGET;
    private PreviousScheduleSaturdayGET previousScheduleSaturdayGET;
    //todo previous week

    private MyScheduleGET myScheduleGET;

    //todo all schedule request
    private SaturdayRequestExists saturdayRequestExists;
    private SundayRequestExists sundayRequestExists;
    private MondayRequestExists mondayRequestExists;
    private TuesdayRequestExists tuesdayRequestExists;
    private WednesdayRequestExists wednesdayRequestExists;
    private ThursdayRequestExists thursdayRequestExists;
    private FridayRequestExists fridayRequestExists;
    //todo all schedule request

    //todo manage users
    private ManageUsersPOST manageUsersPOST;
    private ManageRegistationShift manageRegistationShift;
    private RemoveMySchedule removeMySchedule;
    private MyUserRegShiftPOST myUserRegShiftPOST;
    private AllRegisteredUsersGET allRegisteredUsersGET;
    private ScheduleRequestPOST scheduleRequestPOST;

    private IsScheduleUserExistsGET isScheduleUserExistsGET;

    //todo all shift request
    private FridayScheduleRequestGET fridayScheduleRequestGET;
    private MondayScheduleRequestGET mondayScheduleRequestGET;
    private SaturdayScheduleRequestGET saturdayScheduleRequestGET;
    private SundayScheduleRequestGET sundayScheduleRequestGET;
    private ThursdayScheduleRequestGET thursdayScheduleRequestGET;
    private TuesdayScheduleRequestGET tuesdayScheduleRequestGET;
    private WednesdayScheduleRequestGET wednesdayScheduleRequestGET;
    //todo all shift request

    private RefuseUserPOST refuseUserPOST;
    private RefuseUserGET refuseUserGET;
    private RequestForShiftUpdatePOST requestForShiftUpdatePOST;

    //todo get refuse user
    private MyAdditionalHoursScheduleUserGET myRefuseScheduleUserGET;
    private AllRegisterUserGET allRegisterUserGET;


    //todo get my data
    private SundayUserRegisterCount sundayUserRegisterCount;
    private MondayUserRegisterCount mondayUserRegisterCount;
    private TuesdayUserRegisterCount tuesdayUserRegisterCount;
    private WednesdayUserRegisterCount wednesdayUserRegisterCount;
    private ThursdayUserRegisterCount thursdayUserRegisterCount;
    private FridayUserRegisterCount fridayUserRegisterCount;
    private SaturdayUserRegisterCount saturdayUserRegisterCount;

    private SendRequestPOST sendRequestPOST;
    private DeleteNotConrimRemoveUser deleteNotConrimRemoveUser;
    private MoveToAdditionalHoursPOST moveToAdditionalHoursPOST;

    public ViewModel(@NonNull Application application) {
        super(application);

        sendRequestPOST = new SendRequestPOST(application);

        sundayUserRegisterCount = new SundayUserRegisterCount(application);
        mondayUserRegisterCount = new MondayUserRegisterCount(application);
        tuesdayUserRegisterCount = new TuesdayUserRegisterCount(application);
        wednesdayUserRegisterCount = new WednesdayUserRegisterCount(application);
        thursdayUserRegisterCount = new ThursdayUserRegisterCount(application);
        fridayUserRegisterCount = new FridayUserRegisterCount(application);
        saturdayUserRegisterCount = new SaturdayUserRegisterCount(application);

        //todo my refuse user
        myRefuseScheduleUserGET = new MyAdditionalHoursScheduleUserGET(application);

        //todo all schedule request
        saturdayRequestExists = new SaturdayRequestExists(application);
        sundayRequestExists = new SundayRequestExists(application);
        mondayRequestExists = new MondayRequestExists(application);
        tuesdayRequestExists = new TuesdayRequestExists(application);
        wednesdayRequestExists = new WednesdayRequestExists(application);
        thursdayRequestExists = new ThursdayRequestExists(application);
        fridayRequestExists = new FridayRequestExists(application);
        //todo all schedule request

        //todo previous week
        previousScheduleSundayGET = new PreviousScheduleSundayGET(application);
        previousScheduleMondayGET = new PreviousScheduleMondayGET(application);
        previousScheduleTuesdayGET = new PreviousScheduleTuesdayGET(application);
        previousScheduleWednesdayGET = new PreviousScheduleWednesdayGET(application);
        previousScheduleThursdayGET = new PreviousScheduleThursdayGET(application);
        previousScheduleFridayGET = new PreviousScheduleFridayGET(application);
        previousScheduleSaturdayGET = new PreviousScheduleSaturdayGET(application);
        //todo previous week

        employeesFridayScheduleGET = new EmployeesFridayScheduleGET(application);
        employeesMondayScheduleGET = new EmployeesMondayScheduleGET(application);
        employeesSundayScheduleGET = new EmployeesSundayScheduleGET(application);
        employeesThusdayScheduleGET = new EmployeesThusdayScheduleGET(application);
        employeesTuesdayScheduleGET = new EmployeesTuesdayScheduleGET(application);
        employeesWednesdayScheduleGET = new EmployeesWednesdayScheduleGET(application);
        employeesSaturdayScheduleGET = new EmployeesSaturdayScheduleGET(application);


        checkUserLogin = new CheckUserLogin(application);
        checkUserProfileExistsGET = new CheckUserProfileExistsGET(application);
        businessLogoUploadPOST = new BusinessLogoUploadPOST(application);
        managerProfilePOST = new ManagerProfilePOST(application);
        employeesProfilePicUploadPOST = new EmployeesProfilePicUploadPOST(application);
        employeesProfileDataPOST = new EmployeesProfileDataPOST(application);
        managerProfileGET = new ManagerProfileGET(application);
        allEmployeesGET = new AllEmployeesGET(application);
        searchContactGET = new SearchContactGET(application);
        schedulePOST = new SchedulePOST(application);
        employeesProfileGET = new EmployeesProfileGET(application);
        editEmployeesProfilePOST = new EditEmployeesProfilePOST(application);
        sendScheduleRequest = new SendScheduleRequest(application);
        requestEmployeesScheduleGET = new RequestEmployeesScheduleGET(application);


        requestScheduleDocumentGET = new RequestScheduleDocumentGET(application);
        mySchedulePOST = new MySchedulePOST(application);
        acceptScheduleRequestPOST = new AcceptScheduleRequestPOST(application);
        refuseScheduleRequestPOST = new RefuseScheduleRequestPOST(application);

        myScheduleUsersGET = new MyScheduleUsersGET(application);
        signleScheduleGET = new SignleScheduleGET(application);
        myScheduleListGET = new MyScheduleListGET(application);
        myScheduleDataListGET = new MyScheduleDataListGET(application);

        myScheduleGET = new MyScheduleGET(application);

        //todo all schedule request

        //todo all schedule request

        manageUsersPOST = new ManageUsersPOST(application);
        manageRegistationShift = new ManageRegistationShift(application);
        removeMySchedule = new RemoveMySchedule(application);
        myUserRegShiftPOST = new MyUserRegShiftPOST(application);
        allRegisteredUsersGET = new AllRegisteredUsersGET(application);

        //todo schedule request
        scheduleRequestPOST = new ScheduleRequestPOST(application);
        isScheduleUserExistsGET = new IsScheduleUserExistsGET(application);

        //todo all shift request
        fridayScheduleRequestGET = new FridayScheduleRequestGET(application);
        mondayScheduleRequestGET = new MondayScheduleRequestGET(application);
        saturdayScheduleRequestGET = new SaturdayScheduleRequestGET(application);
        sundayScheduleRequestGET = new SundayScheduleRequestGET(application);
        thursdayScheduleRequestGET = new ThursdayScheduleRequestGET(application);
        tuesdayScheduleRequestGET = new TuesdayScheduleRequestGET(application);
        wednesdayScheduleRequestGET = new WednesdayScheduleRequestGET(application);

        refuseUserPOST = new RefuseUserPOST(application);
        refuseUserGET = new RefuseUserGET(application);
        requestForShiftUpdatePOST = new RequestForShiftUpdatePOST(application);
        allRegisterUserGET = new AllRegisterUserGET(application);

        deleteNotConrimRemoveUser = new DeleteNotConrimRemoveUser(application);
        moveToAdditionalHoursPOST = new MoveToAdditionalHoursPOST(application);
    }

    public LiveData<Boolean> ModeToAdditionalHours(String Date, String ShiftNumber, String SenderUID){
        return moveToAdditionalHoursPOST.MoveToAdditionalHours(Date, ShiftNumber, SenderUID);
    }

    public LiveData<Boolean> DeleteNotConfrimUser(String SenderUID, String UID, String StartDate, String EndDate){
        return deleteNotConrimRemoveUser.DeleteNotConfr(SenderUID, UID, StartDate, EndDate);
    }

    public LiveData<Boolean> SendRequestPOST(String StartDate, String EndDate, String SenderUID, String Name, String Phone){
        return sendRequestPOST.SendRequest(StartDate, EndDate, SenderUID, Name, Phone);
    }

    public LiveData<Integer> GetSundayAllRegisterUser(String UID, String Date){
        return sundayUserRegisterCount.GetMyScheduleSundayUserCount(UID, Date);
    }
    public LiveData<Integer> GetMondayAllRegisterUser(String UID, String Date){
        return mondayUserRegisterCount.GetMyScheduleMondayUserCount(UID, Date);
    }
    public LiveData<Integer> GetTuesdayAllRegisterUser(String UID, String Date){
        return tuesdayUserRegisterCount.GetMyScheduleTuesdayUserCount(UID, Date);
    }
    public LiveData<Integer> GetWednesdayAllRegisterUser(String UID, String Date){
        return wednesdayUserRegisterCount.GetMyScheduleWednesdayUserCount(UID, Date);
    }
    public LiveData<Integer> GetThursdayAllRegisterUser(String UID, String Date){
        return thursdayUserRegisterCount.GetMyScheduleThursdayUserCount(UID, Date);
    }
    public LiveData<Integer> GetFridayAllRegisterUser(String UID, String Date){
        return fridayUserRegisterCount.GetMyScheduleFridayUserCount(UID, Date);
    }
    public LiveData<Integer> GetSaturdayAllRegisterUser(String UID, String Date){
        return saturdayUserRegisterCount.GetMyScheduleSaturdayUserCount(UID, Date);
    }



    //todo refuse user
    public LiveData<List<RefuseUserModel>> GetAdditionalHoursScheduleUser(String Date, String ShiftName){
        return myRefuseScheduleUserGET.GetAdditionalHoursScheduleUser(Date, ShiftName);
    }

    //todo request shift update type
    public LiveData<Boolean> UpdateRequestForShift(String Number, String Date, String ShiftName, String Type){
        return requestForShiftUpdatePOST.UpdateRequestForShift(Number, Date, ShiftName, Type);
    }

    //todo refuse
    public LiveData<List<RefuseModel>> GetRefuseUsers(String StartDate, String EndDate){
        return refuseUserGET.GetUserRefuse(StartDate, EndDate);
    }

    public LiveData<Boolean> RefuseUserHistory(String SenderUID, String StartDate, String EndDate){
        return refuseUserPOST.RefuseUserHistory(SenderUID, StartDate, EndDate);
    }

    //todo all shift request
    public LiveData<List<FridaySchaduleModel>> FridayScheduleRequestGET(String PhoneNumber, String Date){
        return fridayScheduleRequestGET.GetFridayRequest(PhoneNumber, Date);
    }
    public LiveData<List<MondaySchaduleModel>>  MondayScheduleRequestGET(String PhoneNumber, String Date){
        return mondayScheduleRequestGET.GetMOndayRequest(PhoneNumber, Date);
    }
    public LiveData<List<SaturdaySchaduleModel>> SaturdayScheduleRequestGET(String PhoneNumber, String Date){
        return saturdayScheduleRequestGET.GetSaturdayRequest(PhoneNumber, Date);
    }
    public LiveData<List<SundayScheduleModel>> SundayScheduleRequestGET(String PhoneNumber, String Date){
        return sundayScheduleRequestGET.GetSundayRequest(PhoneNumber, Date);
    }
    public LiveData<List<ThursdaySchaduleModel>> ThursdayScheduleRequestGET(String PhoneNumber, String Date){
        return thursdayScheduleRequestGET.GetThursdayRequest(PhoneNumber, Date);
    }
    public LiveData<List<TuesdaySchaduleModel>> TuesdayScheduleRequestGET(String PhoneNumber, String Date){
        return tuesdayScheduleRequestGET.GetTuesdayRequest(PhoneNumber, Date);
    }
    public LiveData<List<WednesdaySchaduleModel>> WednesdayScheduleRequestGET(String PhoneNumber, String Date){
        return wednesdayScheduleRequestGET.GetWednesdayRequest(PhoneNumber, Date);
    }


    public LiveData<Boolean> IsScheduleUserExists(String SenderID, String Date, String ShiftName){
        return isScheduleUserExistsGET.IsScheduleUserExists(SenderID, Date, ShiftName);
    }

    //todo schedule request
    public LiveData<Boolean> ScheduleRequest(MyScheduleModel myScheduleModel, String Name, String Phone, String UID, boolean AdditionHours){
        return scheduleRequestPOST.SendScheduleRequest(myScheduleModel, Name, Phone, UID, AdditionHours);
    }


    public LiveData<List<AllRegisteredUsersModel>> GetAllRegisterUser(String StartDate, String EndDate){
        return allRegisteredUsersGET.AllRegisteredUsersGET(StartDate, EndDate);
    }

    //todo remove my schedule
    public LiveData<Boolean> RemoveMySchedule(){
        return removeMySchedule.RemoveMySchedule();
    }

    public LiveData<Boolean> MyUsersRegisShift(String StartDate, String EndDate, String Number, String Name, String UID){
        return myUserRegShiftPOST.MyUserRegShift(StartDate, EndDate, Number, Name, UID);
    }

    //todo manage user
    public LiveData<Boolean> ManageUser(String SenderUID, String StartDate, String EndDate, String Phone, String Name, String ShiftCount, String MinimumShiftWorkers){
        return manageUsersPOST.ManageUserShift(SenderUID, StartDate, EndDate, Phone, Name, ShiftCount, MinimumShiftWorkers);
    }
    public LiveData<List<ManageUserShiftModel>> ManageRegistationShift(String StartDate, String EndDate){
        return manageRegistationShift.ManageUserShiftModel(StartDate, EndDate);
    }


    //todo all schedule request
    public LiveData<Boolean> GetSaturdayRequestExists(String Date){
        return saturdayRequestExists.SaturdayRequestExists(Date);
    }
    public LiveData<Boolean> GetSundayRequestExists(String Date){
        return sundayRequestExists.SundayRequestExists(Date);
    }
    public LiveData<Boolean> GetMondayRequestExists(String Date){
        return mondayRequestExists.MondayRequestExists(Date);
    }
    public LiveData<Boolean> GetTuesdayRequestExists(String Date){
        return tuesdayRequestExists.TuesdayRequestExists(Date);
    }
    public LiveData<Boolean> GetWednesdayRequestExists(String Date){
        return wednesdayRequestExists.WednesdayRequestExists(Date);
    }
    public LiveData<Boolean> GetThursdayRequestExists(String Date){
        return thursdayRequestExists.ThursdayRequestExists(Date);
    }
    public LiveData<Boolean> GetFridayRequestExists(String Date){
        return fridayRequestExists.FridayRequestExists(Date);
    }
    //todo all schedule request


    //todo previous week
    public LiveData<List<SundayScheduleModel>> GetPreviousScheduleSunday(String Date){
        return previousScheduleSundayGET.GetPreviousSundayScheduleData(Date);
    }
    public LiveData<List<MondaySchaduleModel>> GetPrevioysScheduleMonday(String Date){
        return previousScheduleMondayGET.GetPreviousMondayScheduleData(Date);
    }
    public LiveData<List<TuesdaySchaduleModel>> GetPreviousScheduleTuesday(String Date){
        return previousScheduleTuesdayGET.GetPreviousTuesdayScheduleData(Date);
    }
    public LiveData<List<WednesdaySchaduleModel>> GetPreviousScheduleWednesday(String Date){
        return previousScheduleWednesdayGET.GetPreviousWednesdayScheduleData(Date);
    }
    public LiveData<List<ThursdaySchaduleModel>> GetPreviousScheduleThursday(String Date){
        return previousScheduleThursdayGET.GetPreviousThursdayScheduleData(Date);
    }
    public LiveData<List<FridaySchaduleModel>> GetPreviousScheduleFriday(String Date){
        return previousScheduleFridayGET.GetPreviousFridayScheduleData(Date);
    }
    public LiveData<List<SaturdaySchaduleModel>> GetPreviousScheduleSaturday(String Date){
        return previousScheduleSaturdayGET.GetPreviousSaturdayScheduleData(Date);
    }

    //todo previous week


    public LiveData<Boolean> CheckUserLogin() {
        return checkUserLogin.IsUserLogin();
    }

    public LiveData<Boolean> CheckUserProfileExists(String ScreenName) {
        return checkUserProfileExistsGET.GetUserProfileExists(ScreenName);
    }

    public LiveData<String> BusinessLogoUpload(Uri ImageUri) {
        return businessLogoUploadPOST.UploadBusinessLogo(ImageUri);
    }

    public LiveData<Boolean> ManagerProfilePost(String NameOFBusiness, String Name, String BusinessLogoDownloadUri) {
        return managerProfilePOST.SetUpManagerProfile(NameOFBusiness, Name, BusinessLogoDownloadUri);
    }

    public LiveData<String> EmployeesProfileImageUpload(Uri ImageUri) {
        return employeesProfilePicUploadPOST.EmployeesProfilePicUpload(ImageUri);
    }

    public LiveData<Boolean> UploadEmployeesProfileData(String ProfileImageUri, String Name) {
        return employeesProfileDataPOST.SetUpEmployeesProfileData(ProfileImageUri, Name);
    }

    public LiveData<ManagerProfileModel> ManagerProfileData() {
        return managerProfileGET.GetManagerProfileData();
    }

    public LiveData<List<EmployeesModel>> AllEmployees() {
        return allEmployeesGET.GetAllEmployees();
    }

    public LiveData<List<SearchContactModel>> SearchContct(String PhoneNumber) {
        return searchContactGET.SearchingUsers(PhoneNumber);
    }

    public LiveData<Boolean> SendSchedule(String datename, int Schedule, String ShiftStartTime, String ShiftEndTime, String NumberOfWorkerForThisShift, String AdditionalHoursYouWant, String MinimumShiftParWorker) {
        return schedulePOST.SendSchedule(datename, Schedule, ShiftStartTime, ShiftEndTime, NumberOfWorkerForThisShift, AdditionalHoursYouWant, MinimumShiftParWorker);
    }

    public LiveData<EmployeesModel> EmployeesProfileData() {
        return employeesProfileGET.GetEmployeesProfileData();
    }

    public LiveData<Boolean> EditEmployees(String Name, String Phone) {
        return editEmployeesProfilePOST.EditEmployeesProfileData(Name, Phone);
    }

    public LiveData<Boolean> SendScheduleRequest(String Name, String PhoneNumber, String Date, String RegistrationStartTime, String RegistrationStartDate, String RegistrationEndTime, String RegistrationEndDate) {
        return sendScheduleRequest.SendRequest(Name, PhoneNumber, Date, RegistrationStartTime, RegistrationStartDate, RegistrationEndTime, RegistrationEndDate);
    }

    public LiveData<List<EmployeesSundayScheduleModel>> RequestEmployeesSchedule(String Number, String Date) {
        return requestEmployeesScheduleGET.GetRequestEmployeesSchedule(Number, Date);
    }


    public LiveData<List<EmployeesFridayScheduleModel>> GetRequestEmployeesFridaySchedule(String Number, String Date) {
        return employeesFridayScheduleGET.GetRequestFridayEmployeesSchedule(Number, Date);
    }

    public LiveData<List<EmployeesMondayScheduleModel>> GetRequestEmployeesMondaySchedule(String Number, String Date) {
        return employeesMondayScheduleGET.GetRequestMondayEmployeesSchedule(Number, Date);
    }

    public LiveData<List<EmployeesSundayScheduleModel>> GetRequestEmployeesSundaySchedule(String Number, String Date) {
        return employeesSundayScheduleGET.GetRequestSundayEmployeesSchedule(Number, Date);
    }

    public LiveData<List<EmployeesThusdayScheduleModel>> GetRequestEmployeesThusdaySchedule(String Number, String Date) {
        return employeesThusdayScheduleGET.GetRequestThuesdayEmployeesSchedule(Number, Date);
    }

    public LiveData<List<EmployeesTuesdayScheduleModel>> GetRequestEmployeesTuesdaySchedule(String Number, String Date) {
        return employeesTuesdayScheduleGET.GetRequestTuesdayEmployeesSchedule(Number, Date);
    }

    public LiveData<List<EmployeesWednesdayScheduleModel>> GetRequestWednesdaySchedule(String Phone, String Date) {
        return employeesWednesdayScheduleGET.GetRequestWednesdayEmployeesSchedule(Phone, Date);
    }
    public LiveData<List<EmployeesSaturdayScheduleModel>> GetRequestSaturdaySchedule(String Phone, String Date){
        return employeesSaturdayScheduleGET.GetRequestSaturdayEmployeesSchedule(Phone, Date);
    }


    public LiveData<String> GetRequestScheduleDocument() {
        return requestScheduleDocumentGET.GetRequestScheduleDocument();
    }

    public LiveData<Boolean> SendMySchedule(String Name, String PhoneNumber, String Date, String RegistrationStartTime, String RegistrationStartDate, String RegistrationEndTime, String RegistrationEndDate) {
        return mySchedulePOST.MySchedulePOST(Name, PhoneNumber, Date, RegistrationStartTime, RegistrationStartDate, RegistrationEndTime, RegistrationEndDate);
    }

    public LiveData<Boolean> AcceptSchedulePOST(String ReceiverUID, String Date, String Schedule, boolean IsAdditionHoursNeed, String Type, String ShiftNo) {
        return acceptScheduleRequestPOST.AcceptScheduleRequest(ReceiverUID, Date, Schedule, IsAdditionHoursNeed, Type, ShiftNo);
    }

    public LiveData<Boolean> RefuseSchedulePOST(String ReceiverUID, String Date, String Schedule, boolean IsAdditionHoursNeed, String Type){
        return refuseScheduleRequestPOST.RefuseScheduleRequest(ReceiverUID, Date, Schedule, IsAdditionHoursNeed, Type);
    }

    public LiveData<List<UserModel>> MyScheduleUserGET(String Date, String ShiftName) {
        return myScheduleUsersGET.GetMySchedule(Date, ShiftName);
    }

    public LiveData<MyScheduleModel> GetMySingleSchedule(String Date, String ShiftName) {
        return signleScheduleGET.GetSingleSchedule(Date, ShiftName);
    }

    public LiveData<List<MyScheduleModel>> GetMyScheduleList(String Date) {
        return myScheduleListGET.GetMyScheduleList(Date);
    }

    public LiveData<List<MyScheduleModel>> GetMyScheduleDataList(String Date) {
        return myScheduleDataListGET.GetMyScheduleData(Date);
    }

    public LiveData<List<MyScheduleModel>> GetMySchedule(String PhoneNumber, String Date){
        return myScheduleGET.GetMySchedule(PhoneNumber, Date);
    }

    public LiveData<List<AllRegisterUserDataModel>> AllRegisterUser(String StartDate, String EndDate){
        return allRegisterUserGET.GetAllRegisterUser(StartDate, EndDate);
    }

}
