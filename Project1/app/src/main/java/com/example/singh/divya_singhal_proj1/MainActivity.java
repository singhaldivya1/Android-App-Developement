package com.example.singh.divya_singhal_proj1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Fields to be bound to GUI widgets
    Button activity1; 				// the "1st activity" button in the GUI
    Button activity2 ; 			   // the "2nd activity" button in the GUI
    private static String no;
    private static boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i("abc","i'm here");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity1 = (Button)findViewById(R.id.Activity_1);
        activity2 = (Button)findViewById(R.id.Activity_2);
        Log.i("abc","i'm here@@@@@@@@@@@@@@@");

        activity1.setOnClickListener(Activity_1_Listener);
        activity2.setOnClickListener(Activity_2_Listener);

    }
    // This will be called when the app loses focus; save
    // current state
    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Always do this
        super.onSaveInstanceState(outState)  ;

        // Save the counter's state
        outState.clear() ;
    }

    public View.OnClickListener Activity_1_Listener = new View.OnClickListener() {

        // Called when up button is selected
        @Override
        public void onClick(View v) {

            Log.i("abc","i'm switching@@@@@@@");

            switchToActivity () ;

        }
    } ;

    public View.OnClickListener Activity_2_Listener = new View.OnClickListener() {

        // Called when up button is selected
        @Override
        public void onClick(View v) {

            Log.i("abc","i'm switching to dialer");

            switchToDialer() ;

        }
    } ;




    private void switchToActivity() {
        Intent i = new Intent(MainActivity.this, SwitchToActivity2.class) ;
        Log.i("abc","i'm after intent call");
        startActivityForResult(i,1);
    }

    protected void onActivityResult(int code, int result_code, Intent i) {
        super.onActivityResult(code, result_code, i);
        if (code ==1) {
            String PhoneNo=i.getStringExtra("MyPhoneNo");
            no="tel:"+PhoneNo;
            Log.i("abc","phone NO"+no);
            if (result_code == RESULT_OK){
                flag= true;
                Log.i("abc","phone in  TRUE"+ PhoneNo);
            }
            else {
                flag= false;
                Log.i("abc","phone in  false"+ PhoneNo);
            }
            Log.i("Mainactivity: ", "Returned result is: " + result_code);
        }
    }
    private void switchToDialer(){
        if (flag == true) {

            Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse(no));
            startActivity(i);
        }
        else{
            if (no==null){
                Toast.makeText(MainActivity.this, "Please enter a No. first", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(MainActivity.this, "You have entered an invalid no: " + no, Toast.LENGTH_LONG).show();
            }

        }

    }
}
