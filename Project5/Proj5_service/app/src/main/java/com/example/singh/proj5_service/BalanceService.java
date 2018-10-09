package com.example.singh.proj5_service;

/**
 * Created by singh on 04/29/18.
 */

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.IBinder;
import android.os.Parcel;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.singh.treasurePackage.CommonInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

//treasury serv service
public class BalanceService extends Service {
    private DatabaseOpenHelper mDbHelper;

    private static final String TAG = "BalanceService";


    //service created
    @Override
    public void onCreate() {

        mDbHelper = new DatabaseOpenHelper(this);
        Log.i("main","After helper created");
        clearAll();
        //Cursor c_create = readTreasure();

        super.onCreate();

    }

    //service bound
    @Override
    public IBinder onBind(Intent intent) {

        return mBinder;
    }

    //implementation of interface
    private final CommonInterface.Stub mBinder = new CommonInterface.Stub(){
        /*
        public DailyCash getDailyCash(int day, int dmonth, int year, int working_days){
            String sDay = String.format("%02d", day);
            String sMonth = String.format("%02d", month);
            String sYear = String.format("%04d", year);
            String sWorkingDays = String.valueOf(workingDays);
            Log.i("main", "2nd button click");
            Cursor c = readTreasure(sDay,sMonth,sYear,sWorkingDays);

            if (c.moveToFirst()) {
                while(!c.isAfterLast()) {
                    DailyCash resultQuery= new DailyCash(c.getString(3),c.getString(2),c.getString(1),c.getString(6),c.getString(4));
                    c.moveToNext();
                }
            }

            Log.i("main", "2nd button settext");
            return resultQuery;

        }
        */

        public String dailyCash(int day, int month, int year, int workingDays){
            String sDay = String.format("%02d", day);
            String sMonth = String.format("%02d", month);
            String sYear = String.format("%04d", year);
            String sWorkingDays = String.valueOf(workingDays);
            Log.i("main", "2nd button click");
            Cursor c = readTreasure(sDay,sMonth,sYear,sWorkingDays);
            String resultQuery = "Treasure is as follows";
            if (c.moveToFirst()) {
                while(!c.isAfterLast()) {
                    resultQuery = resultQuery + "Treasure:" + " Date: "+ c.getString(2) + "/" +c.getString(3)+"/" + c.getString(1)+" Day: "+ c.getString(4)+ " Closing Balance " + c.getString(6);
                    c.moveToNext();
                }
            }
            Log.d("main value",resultQuery);
            Log.i("main", "2nd button settext");
            return resultQuery;

        }


        public boolean createData(int create){
            String flag = "False";
            ArrayList<String> listTreasure = new ArrayList<String>();
            BufferedReader reader = null;
            try {
                Log.i("main","1st button click");
                reader = new BufferedReader(new InputStreamReader(getAssets().open("treasuryfinal.txt")));
                String mLine;
                while ((mLine = reader.readLine()) != null) {
                    listTreasure.add(mLine);
                }

                Log.i("main","1st button after while");
                for (int counter =0; counter<listTreasure.size();counter++){
                    String [] eachLine = listTreasure.get(counter).split(",");
                    ContentValues values = new ContentValues();
                    values.put(DatabaseOpenHelper.YEAR, eachLine[0]);
                    values.put(DatabaseOpenHelper.MONTH, eachLine[1]);
                    values.put(DatabaseOpenHelper.DATE, eachLine[2]);
                    values.put(DatabaseOpenHelper.DAY, eachLine[3]);
                    values.put(DatabaseOpenHelper.OPENAMT, eachLine[4]);
                    values.put(DatabaseOpenHelper.CLOSEAMT, eachLine[5]);
                    Log.i("main","got values");
                    mDbHelper.getWritableDatabase().insert(DatabaseOpenHelper.TABLE_NAME, null, values);
                    values.clear();
                    if(counter == (listTreasure.size()-1) ){
                        flag = "True";

                    }
                    //Log.i("main","inside");
                }
                Log.i("main","1st button after for");
                //return flag;
                return true;

            } catch (IOException ex) {
                ex.printStackTrace();
                return false;

            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        //log the exception
                    }
                }


            }

        }





    };

    //service will unbind
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    //service will be destroyed
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    // Returns all artist records in the database
    private Cursor readTreasure(String dateString, String monthString, String yearString, String rangeString) {
        int rangeInt = Integer.parseInt(rangeString);
        Log.i("main","inside read");
        return mDbHelper.getWritableDatabase().rawQuery("SELECT * FROM treasure WHERE year >= " + yearString + "  and month  >= " + monthString + " and date >= " + dateString +" ORDER BY _ID" +" LIMIT " + rangeInt ,null);
        // return mDbHelper.getWritableDatabase().query(DatabaseOpenHelper.TABLE_NAME,
        //         DatabaseOpenHelper.columns, null, new String[] {}, null, null,
        //         null);
    }

    public void clearAll(){
        // Call SQLiteDatabase.delete() -- null arg deletes all rows in arg table.
        mDbHelper.getWritableDatabase().delete(DatabaseOpenHelper.TABLE_NAME, null, null);
    }




}


