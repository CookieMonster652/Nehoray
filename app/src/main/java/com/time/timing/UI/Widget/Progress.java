package com.time.timing.UI.Widget;

import android.app.AlertDialog;
import android.content.Context;

import dmax.dialog.SpotsDialog;

public class Progress {

    private static AlertDialog spotsDialog;

    public static void ShowProgressBar(Context context, String Title){
        spotsDialog = new SpotsDialog.Builder()
                .setContext(context)
                .setMessage("PLease wait")
                .setCancelable(false)
                .build();

        spotsDialog.show();
    }

    public static void DisMissProgressBar(){
        spotsDialog.dismiss();
    }
}
