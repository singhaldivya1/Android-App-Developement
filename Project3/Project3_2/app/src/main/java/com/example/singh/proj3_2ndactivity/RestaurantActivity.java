package com.example.singh.proj3_2ndactivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

//***************************** Restaurant main activity***************************//

public class RestaurantActivity extends AppCompatActivity implements RestaurantList.Rest_ListSelectionListener
{
    public static String[] mRestaurantArray;
    public static String[] mRestaurantDetailArray;
    private RestaurantList mResturantfragment = new RestaurantList();
    private DetailRestaurant mDetailFragment = new DetailRestaurant();
    private FragmentManager mFragmentManager;
    private FrameLayout mRestaurantFrameLayout, mRestDetailFrameLayout;
    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
    private static final String TAG = "DetailViewerActivity";
    private int selectedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
        // Get the string arrays with the titles and qutoes
        mRestaurantArray = getResources().getStringArray(R.array.Restaurant);
        mRestaurantDetailArray = getResources().getStringArray(R.array.RestaurantURL);
        setContentView(R.layout.activity_restaurant);

        // Get references to the TitleFragment and to the QuotesFragment
        mRestaurantFrameLayout = (FrameLayout) findViewById(R.id.restaurant_fragment_container);
        mRestDetailFrameLayout = (FrameLayout) findViewById(R.id.rest_detail_fragment_container);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle(R.string.toolbarTitle);
        setSupportActionBar(toolbar);


        // Get a reference to the FragmentManager
        mFragmentManager = getFragmentManager();

        // Start a new FragmentTransaction
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

         /* Get fragment if already created*/
        mResturantfragment = (RestaurantList) mFragmentManager.findFragmentByTag("RestaurantFragment");
        mDetailFragment = (DetailRestaurant) mFragmentManager.findFragmentByTag("RestaurantDetailFragment");

        // Add the TitleFragment to the layout
        // UB: 10/2/2016 Changed add() to replace() to avoid overlapping fragments
        if(mResturantfragment == null) {
            mResturantfragment = new RestaurantList();
            fragmentTransaction.replace(R.id.restaurant_fragment_container, mResturantfragment,"RestaurantFragment");
            // Commit the FragmentTransaction
            fragmentTransaction.commit();
        }
        else {
            fragmentTransaction.replace(R.id.restaurant_fragment_container, mResturantfragment,"RestaurantFragment");
            // Commit the FragmentTransaction
            fragmentTransaction.commit();
            if (mDetailFragment == null) {
                mDetailFragment = new DetailRestaurant();

            } else {
                if (!mDetailFragment.isAdded()) {

                    // Start a new FragmentTransaction
                    FragmentTransaction fragmentTransaction1 = mFragmentManager.beginTransaction();

                    // Add the QuoteFragment to the layout
                    fragmentTransaction1.replace(R.id.rest_detail_fragment_container, mDetailFragment,"RestaurantDetailFragment");

                    // Add this FragmentTransaction to the backstack
                    fragmentTransaction1.addToBackStack(null);

                    // Commit the FragmentTransaction
                    fragmentTransaction1.commit();

                    // Force Android to execute the committed FragmentTransaction
                    mFragmentManager.executePendingTransactions();

                }
            }
        }



        if(mDetailFragment == null){
            mDetailFragment = new DetailRestaurant();
        }
        // Add a OnBackStackChangedListener to reset the layout when the back stack changes
        mFragmentManager
                .addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        setLayout();
                    }
                });
    }

    private void setLayout() {

        // Determine whether the QuoteFragment has been added
        if (!mDetailFragment.isAdded()) {

            // Make the TitleFragment occupy the entire layout
            mRestaurantFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    MATCH_PARENT, MATCH_PARENT));
            mRestDetailFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT));
        } else {    //if landscape view list fragment size to 1/3rd and web view fragment size to 2/3rd
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {


                mRestaurantFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT,1f));


                mRestDetailFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 2f));
            }
            else{       // if portrait view list fragment size 0 and web view fragment size 100
                mRestaurantFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 0));


                mRestDetailFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 100));

            }
        }
    }

    // Called when the user selects an item in the TitlesFragment
    @Override
    public void onListSelection(int index) {
        //checking permission
        if (ContextCompat.checkSelfPermission(this, "edu.uic.cs478.sp18.project3") != PackageManager.PERMISSION_GRANTED) {
            selectedIndex = index;
            ActivityCompat.requestPermissions(this, new String[]{"edu.uic.cs478.sp18.project3"}, 0);
        } else {        //if permission granted show web view
            showWebView(index);
        }
    }

    public void showWebView(int index){
        // If the QuoteFragment has not been added, add it now
        if (mDetailFragment == null || !mDetailFragment.isAdded()) {

            // Start a new FragmentTransaction
            FragmentTransaction fragmentTransaction = mFragmentManager
                    .beginTransaction();

            // Add the QuoteFragment to the layout
            fragmentTransaction.replace(R.id.rest_detail_fragment_container,
                    mDetailFragment,"RestaurantDetailFragment");

            // Add this FragmentTransaction to the backstack
            fragmentTransaction.addToBackStack(null);

            // Commit the FragmentTransaction
            fragmentTransaction.commit();

            // Force Android to execute the committed FragmentTransaction
            mFragmentManager.executePendingTransactions();
        }


        if (mDetailFragment.getShownIndex() != index) {

            // Tell the QuoteFragment to show the quote string at position index
            mDetailFragment.showQuoteAtIndex(index);

        }


    }
    // Option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.optionmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /* Attraction option */
            case R.id.Attraction_option: {
                //Toast.makeText(this,"inattractiin",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this,AttractionActivity.class);
                startActivity(intent);
                return true;
            }

            /* Restaurant option */
            case R.id.Restaurant_option: {
                //Toast.makeText(this,"inrestaurant",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this,RestaurantActivity.class);
                startActivity(intent);
                return true;
            }
            default: return super.onOptionsItemSelected(item);
        }
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){   //checking permission granted or not
            Log.i(TAG,"Permission granted");
            showWebView(selectedIndex);
        }else {
            Log.i(TAG,"Permission not granted");
        }
    }


    @Override
    protected void onDestroy() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onDestroy()");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onPause()");
        super.onPause();
    }

    @Override
    protected void onRestart() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onRestart()");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onResume()");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStart()");
        super.onStart();
        setLayout();
        mDetailFragment.showQuoteAtIndex(mResturantfragment.currIndex);
    }

    @Override
    protected void onStop() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStop()");
        super.onStop();
    }
}

