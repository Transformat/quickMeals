package com.creditcard;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.bitekite.Log;

/**
 * Created by kauserali on 05/05/14.
 */
public class CreditCardTextWatcher implements TextWatcher {

    private final EditText mEditText;
    private final TextWatcherListener mListener;

    public interface TextWatcherListener {
        void onTextChanged(EditText view, String text);
    }

    public CreditCardTextWatcher(EditText editText, TextWatcherListener listener) {
        mEditText = editText;
        mListener = listener;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mListener.onTextChanged(mEditText, s.toString());
        
        Log.d("onTextChanged","onTextChanged");
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}