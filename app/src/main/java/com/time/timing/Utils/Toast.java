package com.time.timing.Utils;

import android.content.Context;

public class Toast {

    public static void SetToast(Context context, String Message){
        android.widget.Toast.makeText(context, Message, android.widget.Toast.LENGTH_SHORT).show();
    }
}
