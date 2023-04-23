package com.time.timing.Network;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CheckUserLogin {

    private Application application;
    private MutableLiveData<Boolean> data;
    private FirebaseAuth MAuth;

    public CheckUserLogin(Application application){
        this.application = application;
        MAuth = FirebaseAuth.getInstance();
    }

    public LiveData<Boolean> IsUserLogin(){
        data = new MutableLiveData<>();
        FirebaseUser MUser = MAuth.getCurrentUser();
        if(MUser != null){
            data.setValue(true);
        }else {
            data.setValue(false);
        }
        return data;
    }
}
