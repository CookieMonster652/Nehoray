package com.time.timing.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePref {

    private SharedPreferences sharePref;
    private String Data = "Data";

    public SharePref(Context context){
        sharePref = context.getSharedPreferences(Data, Context.MODE_PRIVATE);
    }

    public void SetData(String Key, String Value){
        SharedPreferences.Editor editor = sharePref.edit();
        editor.putString(Key, Value);
        editor.apply();
    }

    public String GetData(String Key){
        return sharePref.getString(Key, null);
    }
}
