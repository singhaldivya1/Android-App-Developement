package com.example.singh.project3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.content.Intent.FLAG_INCLUDE_STOPPED_PACKAGES;

//*************************** Chicago App 1 used to launch App 2 *********************//

public class MainActivity extends AppCompatActivity {
    private static final String Attraction_action = "com.example.singh.proj3_2ndactivity.attraction";
    private static final String Restaurant_action = "com.example.singh.proj3_2ndactivity.restaurant";
    private static final int REQUEST_PERMISSION_ATTRRACTION = 0;
    private static final int REQUEST_PERMISSION_RESTAURANT = 1;
    private static final String MY_PERMISSION = "edu.uic.cs478.sp18.project3";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button attraction = (Button)findViewById(R.id.attr);
        Button restaurant = (Button)findViewById(R.id.rest);
        context = this;
        attraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Going to launch Attraction window",Toast.LENGTH_LONG).show();
                 /* Check if A1 has permission edu.uic.cs478.project3 */
                if (ContextCompat.checkSelfPermission(context, MY_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
                    /* Request permission */
                    ActivityCompat.requestPermissions((Activity)context, new String[]{MY_PERMISSION}, REQUEST_PERMISSION_ATTRRACTION);
                }else{
                    /* If A1 has permission then send broadcast for ATTRACTION */
                    broadcastAttraction();
                }
            }
        });

        restaurant.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
             Toast.makeText(MainActivity.this,"Going to launch Restaurant window",Toast.LENGTH_LONG).show();
                if (ContextCompat.checkSelfPermission(context, MY_PERMISSION) !=
                        PackageManager.PERMISSION_GRANTED) {
                    /* Request permission */
                    ActivityCompat.requestPermissions((Activity)context, new String[]{MY_PERMISSION}, REQUEST_PERMISSION_RESTAURANT);
                }else{
                    /* If A1 has permission then send broadcast for restaurant */
                    broadcastRestaurant();
                }
            }

        });


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        /* Check if permission is granted*/
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
            /* Check if permission was requested for Basketball or
             * Baseball and accordingly send broadcast */
            if(requestCode == REQUEST_PERMISSION_ATTRRACTION){
                Toast.makeText(MainActivity.this,"inside checking",Toast.LENGTH_LONG).show();

                broadcastAttraction();
            }else if (requestCode == REQUEST_PERMISSION_RESTAURANT){
                broadcastRestaurant();
            }
        }else {
            /* Permission not granted by user */
            Toast.makeText(context, "Permission not granted by user", Toast.LENGTH_LONG);
        }

    }

    public void broadcastAttraction(){
        /* Send broadcast for basketball */
        Intent aIntent = new Intent(Attraction_action) ;
        aIntent.setFlags(FLAG_INCLUDE_STOPPED_PACKAGES);
        aIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        sendOrderedBroadcast(aIntent, null);
    }

    public void broadcastRestaurant(){

        /* Send broadcast for baseball */
        Intent aIntent = new Intent(Restaurant_action) ;
        aIntent.setFlags(FLAG_INCLUDE_STOPPED_PACKAGES);
        aIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        sendOrderedBroadcast(aIntent, null);
    }
}







