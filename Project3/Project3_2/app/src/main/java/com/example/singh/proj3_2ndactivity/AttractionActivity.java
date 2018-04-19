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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.singh.proj3_2ndactivity.AttractionList.ListSelectionListener;

//***************************** Attraction main activity***************************//

public class AttractionActivity extends AppCompatActivity  implements ListSelectionListener
{
    public static String[] mAttractionArray;
    public static String[] mAttractionDetailArray;
    private DetailAttraction mDetailFragment = new DetailAttraction();
    private AttractionList mAttractionFragment = new AttractionList();
    private FragmentManager mFragmentManager;
    private FrameLayout mAttractionFrameLayout, mDetailFrameLayout;
    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
    private static final String TAG = "DetailViewerActivity";
    private int selectedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
        // Get the string arrays with the titles and qutoes
        mAttractionArray = getResources().getStringArray(R.array.Attraction);
        mAttractionDetailArray = getResources().getStringArray(R.array.AttractionURL);
        setContentView(R.layout.activity_main);

        // Get references to the TitleFragment and to the QuotesFragment
        mAttractionFrameLayout = (FrameLayout) findViewById(R.id.attraction_fragment_container);
        mDetailFrameLayout = (FrameLayout) findViewById(R.id.detail_fragment_container);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle(R.string.toolbarTitle);
        setSupportActionBar(toolbar);

        // Get a reference to the FragmentManager
        mFragmentManager = getFragmentManager();

        // Start a new FragmentTransaction
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        /* Get fragment if already created*/
        mAttractionFragment = (AttractionList) mFragmentManager.findFragmentByTag("AttractionFragment");
        mDetailFragment = (DetailAttraction) mFragmentManager.findFragmentByTag("AttractionDetailFragment");


        if(mAttractionFragment == null) {
            mAttractionFragment = new AttractionList();
            fragmentTransaction.replace(R.id.attraction_fragment_container, mAttractionFragment,"AttractionFragment");

            // Commit the FragmentTransaction
            fragmentTransaction.commit();
        }

    else{
            fragmentTransaction.replace(R.id.attraction_fragment_container, mAttractionFragment,"AttractionFragment");

            // Commit the FragmentTransaction
            fragmentTransaction.commit();
            if (mDetailFragment == null) {
                mDetailFragment = new DetailAttraction();

            } else{
                if (!mDetailFragment.isAdded()) {

                // Start a new FragmentTransaction
                FragmentTransaction fragmentTransaction1 = mFragmentManager.beginTransaction();

                // Add the QuoteFragment to the layout
                fragmentTransaction1.replace(R.id.detail_fragment_container, mDetailFragment,"AttractionDetailFragment");

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
            mDetailFragment = new DetailAttraction();
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
            mAttractionFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    MATCH_PARENT, MATCH_PARENT));
            mDetailFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT));
        } else {
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

                // Make the TitleLayout take 1/3 of the layout's width
                mAttractionFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 1f));

                // Make the QuoteLayout take 2/3's of the layout's width
                mDetailFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 2f));
            }
            else {
                mAttractionFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT,0));


                mDetailFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 100));
            }
            }
        }


    // Called when the user selects an item in the TitlesFragment
    @Override
    public void onListSelection(int index) {

        if (ContextCompat.checkSelfPermission(this, "edu.uic.cs478.sp18.project3") != PackageManager.PERMISSION_GRANTED) {
            selectedIndex = index;
            ActivityCompat.requestPermissions(this, new String[]{"edu.uic.cs478.sp18.project3"}, 0);
        } else {
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
            fragmentTransaction.replace(R.id.detail_fragment_container,
                    mDetailFragment,"AttractionDetailFragment");

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.optionmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /* Basketball option */
            case R.id.Attraction_option: {
                //Toast.makeText(this,"inattractiin",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this,AttractionActivity.class);
                startActivity(intent);
                return true;
            }

            /* Baseball option */
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

        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
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
        mDetailFragment.showQuoteAtIndex(mAttractionFragment.currIndex);
    }

    @Override
    protected void onStop() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStop()");
        super.onStop();
    }
}
