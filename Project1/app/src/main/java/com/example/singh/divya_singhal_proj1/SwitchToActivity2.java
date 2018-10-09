package com.example.singh.divya_singhal_proj1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

public class SwitchToActivity2 extends AppCompatActivity {

    EditText Phone_No;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_to2);
        Log.i("ac2","I m in oncreate");
        Phone_No = (EditText) findViewById(R.id.PhoneNo);
        Phone_No.setOnEditorActionListener( new EditText.OnEditorActionListener(){

            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                Log.i("ac2","I m in listener");
                Log.i("ac2" ,"keyevent" + actionId);
                Log.i("ac2" ,"EditorInfo" + EditorInfo.IME_ACTION_DONE);

                if ((actionId == EditorInfo.IME_ACTION_DONE)||(actionId == EditorInfo.IME_ACTION_NEXT) ) {
                    Log.i("ac2","i'm in done");
                    String callingno = Phone_No.getText().toString();
                    boolean b = Pattern.matches("^\\s*\\({1}([0-9]{3})\\){1}([0-9]{3})[-]{1}([0-9]{4})\\s*$", callingno);
                    Log.i("ac2","calling no" + callingno);
                    Intent data = getIntent();
                    data.putExtra("MyPhoneNo", callingno);
                    if (b == true) {
                        Log.i("ac2","b=true");
                        setResult(RESULT_OK, data);
                        finish();
                        return true;
                    }
                    else {
                        Log.i("ac2","b=FALSE");
                        setResult(RESULT_CANCELED,data);
                        Log.i("ac2","after set");
                        finish();
                        return true;
                    }
                }

                else return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        String callingno = Phone_No.getText().toString();
        boolean b = Pattern.matches("^\\s*\\({1}([0-9]{3})\\){1}([0-9]{3})[-]{1}([0-9]{4})\\s*$", callingno);
        Log.i("ac2","calling no" + callingno);
        //Intent data = new Intent();
        Intent data = getIntent();
        data.putExtra("MyPhoneNo", callingno);
        if (b == true) {
            Log.i("ac2","b=true");
            setResult(RESULT_OK, data);
            finish();
                    }
        else {
            Log.i("ac2", "b=FALSE");
            setResult(RESULT_CANCELED, data);
            Log.i("ac2", "after set");
            finish();

        }


        super.onBackPressed();


    }
}

