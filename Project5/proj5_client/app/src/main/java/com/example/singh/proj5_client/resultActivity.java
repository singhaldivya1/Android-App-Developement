package com.example.singh.proj5_client;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by singh on 04/29/18.
 */

public class resultActivity extends AppCompatActivity{

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listlayout);
        Log.i("resultActivity","oncreate");
        mListView = (ListView) findViewById(R.id.listTreasure);
// 1
        final ArrayList<String> treasureList;
// 2

        Log.i("resultActivity","after find");
        Log.d("resultActivity",MainActivity.queryResults);

        String[] QueryArray = MainActivity.queryResults.split("Treasure:");
        Log.i("resultActivity","after split");
        //Log.d("resultActivity array",QueryArray[3]);
       ArrayAdapter adapter = new ArrayAdapter(this, R.layout.simplelist1,R.id.listQuery, QueryArray);
       Log.i("resultActivity","after adapter");
       mListView.setAdapter(adapter);

    }

}
