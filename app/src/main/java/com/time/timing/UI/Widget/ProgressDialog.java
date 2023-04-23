package com.time.timing.UI.Widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import com.time.timing.R;
import com.time.timing.databinding.ProgressdialogBinding;

public class ProgressDialog {

    private static AlertDialog alertDialog;

    public static void ShowProgress(Context context) {
        var Mbuilder = new AlertDialog.Builder(context);
        var layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ProgressdialogBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.progressdialog, null, false);
        Mbuilder.setView(binding.getRoot());

        alertDialog = Mbuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    public static void CancelDialog(){
        if(alertDialog != null){
            alertDialog.dismiss();
        }
    }
}
