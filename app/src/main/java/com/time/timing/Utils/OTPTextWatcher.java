package com.time.timing.Utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OTPTextWatcher implements TextWatcher {

    private @NonNull EditText CodeOne;
    private @NonNull EditText CodeTwo;
    private @NonNull EditText CodeThree;
    private @NonNull EditText CodeFour;
    private @NonNull EditText CodeFive;
    private @NonNull EditText CodeSix;
    private OnComplete OnComplete;

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(CodeOne.getText().toString().trim().length() > 0 && CodeTwo.getText().toString().trim().length() > 0
        && CodeThree.getText().toString().trim().length() > 0 && CodeFour.getText().toString().trim().length() > 0
        && CodeFive.getText().toString().trim().length() > 0 && CodeSix.getText().toString().trim().length() > 0){
            OnComplete.Done(true);
        }else {
            OnComplete.Done(false);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public interface OnComplete{
        void Done(boolean val);
    }
    public void OnCompleteEvent(OnComplete OnComplete){
        this.OnComplete = OnComplete;
    }
}
