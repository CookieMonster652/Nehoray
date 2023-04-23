package com.time.timing.UI.Manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.animation.AnimationUtils;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.time.timing.Data.DataManager;
import com.time.timing.DateModel;
import com.time.timing.Network.ViewModel.ViewModel;
import com.time.timing.PhoneContactModel;
import com.time.timing.R;
import com.time.timing.Utils.DateToDayName;
import com.time.timing.Utils.GetWeeksName;
import com.time.timing.Utils.HandleActivity;
import com.time.timing.Utils.Permission;
import com.time.timing.Utils.Toast;
import com.time.timing.databinding.InviteemployeesBinding;

import java.util.ArrayList;
import java.util.List;

public class InviteEmployees extends AppCompatActivity {

    private InviteemployeesBinding binding;
    private ViewModel viewModel;
    private ArrayList<PhoneContactModel> phonecontact = new ArrayList<>();
    private List<DateModel> list = new ArrayList<>();
    private int PermissionCode = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.inviteemployees);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);

        GetData();
        SetNextWeek();
        InitView();
    }


    private void SetNextWeek() {
        for (int i = 0; i < GetWeeksName.getDaysOfWeek().length; i++) {
            var mydate = GetWeeksName.getDaysOfWeek()[i];
            var dateModel = new DateModel();
            dateModel.setDate(String.valueOf(mydate));
            list.add(dateModel);

            var StartDate = list.get(0).getDate();
            var LastDate = list.get(list.size() - 1).getDate();
            binding.InviteDateOf.setText(getResources().getString(R.string.invite_workers_for_this_schedule)+"\n" + DateToDayName.GetDate(StartDate) + " - " + DateToDayName.GetDate(LastDate));
        }
    }


    private void InitView() {
        binding.Toolbar.Title.setText(R.string.invite_workers_for_this_schedule);
        final var appPackageName = getPackageName();
        var ApplicationLink = DataManager.PlayStoreUrl + appPackageName;
        binding.AppLink.setSelected(true);
        binding.AppLink.setText(ApplicationLink);
        binding.CopyBtn.setOnClickListener(view -> {
            var clipboard = (ClipboardManager) getSystemService(getApplication().CLIPBOARD_SERVICE);
            var clip = ClipData.newPlainText("copy", ApplicationLink);
            clipboard.setPrimaryClip(clip);
            Toast.SetToast(getApplicationContext(), "Copy success");
        });

        var animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoominzoomout);
        binding.ImportEmployeesBtn.setAnimation(animation);
    }

    private void GetData() {
        binding.ImportEmployeesBtn.setOnClickListener(view -> {
            if(Permission.ReadContact(InviteEmployees.this, PermissionCode)){
                HandleActivity.GotoImportEmployees(InviteEmployees.this);
                Animatoo.animateSlideLeft(InviteEmployees.this);
            }
        });
    }
}