package com.example.singh.proj5_client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.singh.treasurePackage.CommonInterface;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private CommonInterface mInterface;
    private boolean mIsBound = false;

    private static final int CREATEQUERY = 1;
    private static final int READQUERY = 2;

    int yearInt;
    int dateInt;
    int monthInt;
    public static int rangeInt;
    int createInt;



    EditText yeartext;
    EditText mnthtext;
    EditText datetext;
    EditText rangetext;
    Button createButton;
    Button queryButton;


    public static ArrayList<String> queries = new ArrayList<String>();
    public static String queryResults ;
    Handler serviceThreadHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createInt =0;

        yeartext = (EditText)findViewById(R.id.yearbox);
        mnthtext = (EditText)findViewById(R.id.monthbox);
        datetext = (EditText)findViewById(R.id.daybox);
        rangetext = (EditText)findViewById(R.id.rangebox);

        createButton = (Button) findViewById(R.id.create);
        queryButton = (Button) findViewById(R.id.read);



        createButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("main","inside onclick create");
                createInt = 1;
                queries.add("Query: create("+createInt+")");
                Message message = serviceThreadHandler.obtainMessage(CREATEQUERY);
                serviceThreadHandler.sendMessage(message);
                Log.i("main","leaving onclick create");
            }
        });

        queryButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean validFlag = false;
                Log.i("main","inside onclick query");
//                if((yeartext.getText().toString().length() == 0)|| (mnthtext.getText().toString().length() == 0 )|| (datetext.getText().toString().length() == 0)|| (rangetext.getText().toString().length() ==0) )
//                {
//                    Toast.makeText(MainActivity.this,"All fields are mandatory",Toast.LENGTH_LONG).show();
//                }
                 yearInt = Integer.parseInt(yeartext.getText().toString());
                 monthInt = Integer.parseInt(mnthtext.getText().toString());
                 dateInt = Integer.parseInt(datetext.getText().toString());
                 rangeInt = Integer.parseInt(rangetext.getText().toString());
                if(((yearInt == 2017) || (yearInt== 2018))&&(monthInt >= 1 && monthInt <= 12) && (dateInt >= 1 && dateInt <= 31)){
                    validFlag = true;
                }
//                if(monthInt >= 1 && monthInt <= 12){
//                    validFlag = true;
//                }
//                if(dateInt >= 1 && dateInt <= 31){
//                    validFlag = true;
//                }
                if (validFlag == true) {
                    queries.add("Query: dailyCash(" + dateInt + "," + monthInt + "," + yearInt + "," + rangeInt + ")");
                    Message message = serviceThreadHandler.obtainMessage(READQUERY);
                    serviceThreadHandler.sendMessage(message);
                    Log.i("main", "leaving onclick query");
                }
                else{
                    Toast.makeText(MainActivity.this,"Invalid data",Toast.LENGTH_LONG).show();
                }

            }
        });

        //seperate thread to run queries from service
        Thread queryThread = new Thread(new Runnable() {

            @Override
            public  void run() {
                Looper.prepare();

                //thread handlers
                serviceThreadHandler = new Handler(){

                    @Override
                    public void handleMessage(Message msg) {

                        Message message;
                        switch (msg.what){


                            case CREATEQUERY:
                                try {
                                    boolean output = mInterface.createData(createInt);
                                    Log.d("main create value:", (String.valueOf(output)) );
                                    //queryResults = output;
                                    Log.i("main","inside create");

                                    Toast.makeText(MainActivity.this,"Database successfully created",Toast.LENGTH_LONG).show();

                                } catch (RemoteException e) {
                                    Log.i("main","Exception"+e.getMessage());
                                }
                                break;

                            //query-2 : String dailyCash(int day, int month, int year, int working_days)
                            case READQUERY:
                                try {
                                    String output = mInterface.dailyCash(dateInt,monthInt,yearInt,rangeInt);
                                    Log.d("main read value:",output);
                                    queryResults = output;
                                    Intent showResult = new Intent(MainActivity.this, resultActivity.class);
                                    startActivity(showResult);
                                    Log.i("main","leaving readquery");

                                }
                                catch (RemoteException e){
                                    Log.i("main","Exception"+e.getMessage());
                                }
                                break;


                        }
                    }
                };
                Looper.loop();
            }
        });
        queryThread.start();


    }

    @Override
    protected void onResume() {
        super.onResume();

        //check if service is bound or not
        if (!mIsBound) {

            boolean b = false;
            Intent i = new Intent(CommonInterface.class.getName());
            getPackageManager().resolveService(i,Context.BIND_AUTO_CREATE);
            ResolveInfo info = getPackageManager().resolveService(i,Context.BIND_AUTO_CREATE);
            i.setComponent(new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name));

            b = bindService(i, this.mConnection,Context.BIND_AUTO_CREATE);
            if (b) {
                Log.i("main", "Service is bound.");
            } else {
                Log.i("main", "Couldn't bind the service.");
            }

        }
    }

    //unbind service if activity is destroyed
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBindService();
    }

    //function to unbind the service
    private void unBindService(){
        if (mIsBound){
            unbindService(this.mConnection);
        }
    }


    private final ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder iservice) {
            mInterface = CommonInterface.Stub.asInterface(iservice);
            mIsBound = true;
        }

        public void onServiceDisconnected(ComponentName className) {
            mInterface = null;
            mIsBound = false;
        }
    };
}


