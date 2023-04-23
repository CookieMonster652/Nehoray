package com.time.timing.Utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ManagerBusinessProfileTextWatcher implements TextWatcher {

    private @NonNull EditText NameOfBusiness;
    private @NonNull EditText Name;
    private OnComplete OnComplete;

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(NameOfBusiness.getText().toString().trim().length() > 0 && Name.getText().toString().trim().length() > 0){
            OnComplete.Complete(true);
        }else {
            OnComplete.Complete(false);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public interface OnComplete{
        void Complete(boolean val);
    }
    public void OnCompleteEvent(OnComplete OnComplete){
        this.OnComplete = OnComplete;
    }
}
