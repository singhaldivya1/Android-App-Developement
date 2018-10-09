package com.example.singh.proj5_service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by singh on 04/26/18.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    final static String TABLE_NAME = "treasure";
    final static String _ID = "_id";
    final static String YEAR = "year";
    final static String MONTH ="month";
    final static String DATE ="date";
    final static String DAY ="day";
    final static String OPENAMT ="openamt";
    final static String CLOSEAMT ="closeamt";
    final static String[] columns = { _ID, YEAR,MONTH,DATE,DAY,OPENAMT,CLOSEAMT};

    final private static String CREATE_CMD =

            "CREATE TABLE treasure (" + _ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + YEAR + " TEXT NOT NULL," + MONTH + " TEXT NOT NULL," +DATE+ " TEXT NOT NULL,"+DAY+ " TEXT NOT NULL," +OPENAMT+ " TEXT NOT NULL," +CLOSEAMT+ " TEXT NOT NULL)";

    final private static String NAME = "treasure_db";
    final private static Integer VERSION = 1;
    final private Context mContext;

    public DatabaseOpenHelper(Context context) {

        // Always call superclass's constructor
        super(context, NAME, null, VERSION);

        // Save the context that created DB in order to make calls on that context,
        // e.g., deleteDatabase() below.

        Log.i("Helper","before super");
        this.mContext = context;
        Log.i("Helper","After super");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("Helper","inside oncreate");
        db.execSQL(CREATE_CMD);
        Log.i("Helper","oncreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // N/A
    }

    // Calls ContextWrapper.deleteDatabase() by way of inheritance
    void deleteDatabase() {
        mContext.deleteDatabase(NAME);
        Log.i("Helper","del");
    }
}