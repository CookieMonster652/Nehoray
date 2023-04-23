package com.time.timing.Network.SharePref;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePref {

    private SharedPreferences sharedPreferences;
    private String SharePrefData = "Data";

    public SharePref(Context context){
        sharedPreferences = context.getSharedPreferences(SharePrefData, Context.MODE_PRIVATE);
    }


    public void SetData(String key, String value){
        var pref = sharedPreferences.edit();
        pref.putString(key, value);
        pref.apply();
    }

    public String GetData(String value){
        return sharedPreferences.getString(value, null);
    }
}
