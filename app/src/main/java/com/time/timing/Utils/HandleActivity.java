package com.time.timing.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.time.timing.Data.DataManager;
import com.time.timing.LoginDifferentUser;
import com.time.timing.R;
import com.time.timing.ShiftModel;
import com.time.timing.SplashScreen;
import com.time.timing.UI.Employees.EmployeeProfileName;
import com.time.timing.UI.Employees.EmployeesShifts;
import com.time.timing.UI.Employees.Employeesotp;
import com.time.timing.UI.Employees.ProfilePicUploadEmployee;
import com.time.timing.UI.Employees.SignInForEmployee;
import com.time.timing.UI.EmployeesHome.EmployeesHome;
import com.time.timing.UI.Manager.AddEmployees;
import com.time.timing.UI.Manager.CurrentWeek;
import com.time.timing.UI.Manager.ImportEmployees;
import com.time.timing.UI.Manager.InviteEmployees;
import com.time.timing.UI.Manager.ManagerBusinessProfile;
import com.time.timing.UI.Manager.RegistrationEndTime;
import com.time.timing.UI.Manager.RegistrationStartTime;
import com.time.timing.UI.Manager.ScheduleList;
import com.time.timing.UI.Manager.SendEmployeeSheduleTime;
import com.time.timing.UI.Manager.SuccessfullySendSchedule;
import com.time.timing.UI.Manager.UploadBusinessLogo;
import com.time.timing.UI.ManagerHome.ManagerHome;
import com.time.timing.UI.ManagerSignIn.EditWeek;
import com.time.timing.UI.ManagerSignIn.ManagerOTP;
import com.time.timing.UI.ManagerSignIn.SelectShift;
import com.time.timing.UI.ManagerSignIn.SignInForManager;

import java.util.ArrayList;
import java.util.List;

public class HandleActivity {

    public static void GotoEditWeek(Context context) {
        var intent = new Intent(context, EditWeek.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void GotoSelectShift(Context context, String CurrentDate) {
        var intent = new Intent(context, SelectShift.class);
        intent.putExtra(DataManager.Data, CurrentDate);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        Animatoo.animateSlideLeft(context);
    }



    public static void GotoRegistationStartTime(Context context) {
        var intent = new Intent(context, RegistrationStartTime.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        Animatoo.animateSlideLeft(context);
    }

    public static void GotoRegistationEndTime(Context context) {
        var intent = new Intent(context, RegistrationEndTime.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        Animatoo.animateSlideLeft(context);
    }

    public static void BackGotoEmployeesSchaduleRegistrationStartDate(Context context) {
        var intent = new Intent(context, SendEmployeeSheduleTime.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        Animatoo.animateSlideRight(context);
    }

    public static void BackGotoEmployeesSchaduleRegistrationEndDate(Context context) {
        var intent = new Intent(context, SendEmployeeSheduleTime.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        Animatoo.animateSlideRight(context);
    }

    public static void GotoAdEmployees(Context context) {
        var intent = new Intent(context, AddEmployees.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        Animatoo.animateSlideLeft(context);
    }

    public static void GotoSignInEmployees(Context context) {
        var intent = new Intent(context, SignInForEmployee.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        Animatoo.animateSlideLeft(context);
    }

    public static void GotoSuccessfullySendSchedule(Context context) {
        var intent = new Intent(context, SuccessfullySendSchedule.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        Animatoo.animateSlideLeft(context);
    }

    public static void GotoSignInManager(Context context) {
        var intent = new Intent(context, SignInForManager.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        Animatoo.animateSlideLeft(context);
    }

    public static void GotoSendEmployeesScheduleSelectTime(Context context) {
        var intent = new Intent(context, SendEmployeeSheduleTime.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        Animatoo.animateSlideLeft(context);
    }

    public static void GotoLoginDifferentUser(Activity context) {
        var intent = new Intent(context, LoginDifferentUser.class);
        context.startActivity(intent);
        Animatoo.animateSlideLeft(context);
        context.finish();
    }

    public static void GotoEmployeesShifts(Activity context) {
        var intent = new Intent(context, EmployeesShifts.class);
        context.startActivity(intent);
        Animatoo.animateSlideLeft(context);
    }

    public static void HandleManagerFragment(Fragment fragment, AppCompatActivity activity) {
        var transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.FrameLayoutManager, fragment);
        transaction.commit();
    }

    public static void HandleEmployeesFragment(Fragment fragment, AppCompatActivity activity) {
        var fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.FrameLayoutEmployees, fragment);
        fragmentTransaction.commit();
    }

    public static void GotoSplashScreen(Context context) {
        var intent = new Intent(context, SplashScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        Animatoo.animateSlideLeft(context);
    }


    public static void GotoManagerOTPScreen(Context context, String Number, String VerificationID) {
        var intent = new Intent(context, ManagerOTP.class);
        intent.putExtra(DataManager.Phone, Number);
        intent.putExtra(DataManager.VerificationID, VerificationID);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void GotoEmployeesOTPScreen(Context context, String Number, String VerificationID) {
        var intent = new Intent(context, Employeesotp.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(DataManager.Phone, Number);
        intent.putExtra(DataManager.VerificationID, VerificationID);
        context.startActivity(intent);
    }

    public static void GotoManagerHome(Context context) {
        var intent = new Intent(context, ManagerHome.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void GotoEmployeesHome(Context context) {
        var intent = new Intent(context, EmployeesHome.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void GotoManagerProfileNameOFBusiness(Context context) {
        var intent = new Intent(context, ManagerBusinessProfile.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void GotoManagerBusinessLogoProfile(Context context, String NameOFBusiness, String Name) {
        var intent = new Intent(context, UploadBusinessLogo.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(DataManager.NameOFBusiness, NameOFBusiness);
        intent.putExtra(DataManager.Name, Name);
        context.startActivity(intent);
    }

    public static void GotoEmployeesProfileName(Context context) {
        var intent = new Intent(context, EmployeeProfileName.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void GotoEmployeesUploadPic(Context context, String Name) {
        var intent = new Intent(context, ProfilePicUploadEmployee.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(DataManager.Name, Name);
        context.startActivity(intent);
    }

    public static void GotoCurrentWeek(Context context, List<ShiftModel> shiftModels) {
        var intent = new Intent(context, CurrentWeek.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        var bundle = new Bundle();
        bundle.putParcelableArrayList(DataManager.Data, (ArrayList<? extends Parcelable>) shiftModels);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void GotoScheduleList(Context context) {
        var intent = new Intent(context, ScheduleList.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void GotoInviteEmployees(Context context) {
        var intent = new Intent(context, InviteEmployees.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void GotoImportEmployees(Context context) {
        var intent = new Intent(context, ImportEmployees.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }


}
