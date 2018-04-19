package com.example.singh.proj3_2ndactivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by singh on 03/29/18.
 */

//************************ Chicago app receiver activated by app1 **********************//
//************ Implemented Broadcast Receiever , Permission and Fragments**************//
public class ChicagoReciever extends BroadcastReceiver {
    private static final String Attraction_ACTION = "com.example.singh.proj3_2ndactivity.attraction";
    private static final String Restaurant_ACTION = "com.example.singh.proj3_2ndactivity.restaurant";

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction() == Restaurant_ACTION){
            Intent intent1 = new Intent(context, RestaurantActivity.class);// call restaurant activity
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }else if(intent.getAction() == Attraction_ACTION){
            Intent intent1 = new Intent(context, AttractionActivity.class); // call attraction activity
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }
    }
}
