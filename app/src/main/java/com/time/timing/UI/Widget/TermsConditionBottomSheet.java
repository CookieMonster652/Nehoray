package com.time.timing.UI.Widget;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;
import com.time.timing.R;
import com.time.timing.databinding.TermsandconditionbottomsheedBinding;

public class TermsConditionBottomSheet {

    private static AlertDialog.Builder builder;

    public static void OpenBottomSheed(Context context){

        builder = new AlertDialog.Builder(context);
        var inflater = (LayoutInflater)context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        TermsandconditionbottomsheedBinding binding = DataBindingUtil.inflate(inflater, R.layout.termsandconditionbottomsheed, null, false);

        builder.setView(binding.getRoot());
        var alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        binding.OkButton.setOnClickListener(view -> {
            if(builder != null){
                alertDialog.dismiss();
            }
        });
    }

}
