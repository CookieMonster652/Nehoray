package com.time.timing.Network.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.time.timing.ContactModel;
import com.time.timing.Utils.ContactDao;
import com.time.timing.Utils.ContactDatabase;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ContactViewModel extends AndroidViewModel {

    private ContactDatabase contactDatabase;
    private ContactDao contactDao;
    private Executor executor;

    public ContactViewModel(@NonNull Application application) {
        super(application);

        contactDatabase = ContactDatabase.GetContactDatabase(application);
        contactDao = contactDatabase.contactDao();
        executor = Executors.newSingleThreadExecutor();
    }

    public void InsertContact(ContactModel contactModel){
        executor.execute(() -> {
            contactDao.InsertContact(contactModel);
        });
    }

    public LiveData<List<ContactModel>> GetContact(){
        return contactDao.GetAllContact();
    }

    public LiveData<List<ContactModel>> SearchByName(String Name){
        return contactDao.SearchByName(Name);
    }
}
