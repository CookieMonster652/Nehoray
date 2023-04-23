package com.time.timing.UI.Manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.time.timing.ContactModel;
import com.time.timing.Data.MarkContact;
import com.time.timing.MyScheduleContact;
import com.time.timing.Network.ViewModel.ContactViewModel;
import com.time.timing.Network.ViewModel.ManageWorkersViewModel;
import com.time.timing.Network.ViewModel.ViewModel;
import com.time.timing.R;
import com.time.timing.UI.Adapter.ContactAdapter;
import com.time.timing.Utils.HandleActivity;
import com.time.timing.Utils.Permission;
import com.time.timing.databinding.ImportemployeesBinding;

public class ImportEmployees extends AppCompatActivity {

    private ImportemployeesBinding binding;
    private static final int PermissionCode = 100;
    private ViewModel viewModel;
    private static final String TAG = "TAG";
    private ManageWorkersViewModel manageWorkersViewModel;
    private ContactViewModel contactViewModel;
    private ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.importemployees);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        manageWorkersViewModel = new ViewModelProvider(this).get(ManageWorkersViewModel.class);
        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        adapter = new ContactAdapter();


        InitView();
        GetContactFromDB();
    }

    private void InitView() {
        binding.Toolbar.Title.setText(getResources().getString(R.string.import_employees));
        binding.Toolbar.BackButton.setOnClickListener(view -> {
            finish();
            Animatoo.animateSlideRight(ImportEmployees.this);
        });

        if (Permission.ReadContact(ImportEmployees.this, PermissionCode)) {
            GetContact();
        }

        binding.SearchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                var SearchData = editable.toString();
                if(!SearchData.isEmpty()){
                    SearchByName(SearchData);
                }else {
                    SearchByName(null);
                }
            }
        });
    }

    private void SearchByName(String Name){
        if(Name != null){
            contactViewModel.SearchByName(Name).observe(this, contactModels -> {
                adapter.setList(contactModels);
                binding.RecyclerViewEmployees.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                if(adapter.getItemCount() == 0){
                    binding.SearchIcon.setVisibility(View.VISIBLE);
                    binding.SearchMessage.setVisibility(View.VISIBLE);
                }else {
                    binding.SearchIcon.setVisibility(View.GONE);
                    binding.SearchMessage.setVisibility(View.GONE);
                }
            });
        }else {
            binding.SearchIcon.setVisibility(View.GONE);
            binding.SearchMessage.setVisibility(View.GONE);
            GetContactFromDB();
        }
    }


    private void GetContact() {
        binding.RecyclerViewEmployees.setHasFixedSize(true);
        var cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null);

        while (cursor != null && cursor.moveToNext() && cursor.getCount() > 0) {
            try {
                var name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                var phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                SearchContact(phoneNumber, name);
                Log.d(TAG, phoneNumber);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    private void SearchContact(String Number, String Name) {
        viewModel.SearchContct(Number).observe(this, searchContactModels -> {
            if (searchContactModels != null) {
                for (var alldata : searchContactModels) {
                    var model = new ContactModel(alldata.getName(), alldata.getPhone());
                   contactViewModel.InsertContact(model);
                }
            }
        });
    }

    private void GetContactFromDB(){
        contactViewModel.GetContact().observe(this, contactModels -> {
            adapter.setList(contactModels);
            binding.RecyclerViewEmployees.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            adapter.OnCounterEvent(size -> {
                if (size > 0) {
                    binding.HowManyEmployeesSelect.setVisibility(View.VISIBLE);
                    binding.HowManyEmployeesSelect.setText(String.valueOf(size) + " employees was select");
                } else {
                    binding.HowManyEmployeesSelect.setVisibility(View.GONE);
                }
            });


            binding.ImportEmployeesButton.setOnClickListener(view -> {
                MarkContact.ClearContact();
                var list = adapter.getList();
                for (var item : list) {
                    if (item.isIsCheck()) {
                        var contact = new MyScheduleContact();
                        contact.setName(item.getName());
                        contact.setPhoneNumber(item.getPhoneNumber());
                        manageWorkersViewModel.AddDailySchedule(contact);
                        MarkContact.SetMarkNumber(new ContactModel(item.getName(), item.getPhoneNumber()));
                        HandleActivity.GotoSendEmployeesScheduleSelectTime(ImportEmployees.this);
                    }
                }
            });
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        Animatoo.animateSlideRight(ImportEmployees.this);
    }
}