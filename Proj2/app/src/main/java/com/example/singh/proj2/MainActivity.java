package com.example.singh.proj2;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.MainThread;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView songListView;
    private ArrayList<Song> songs ;
    private SongAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        songListView = (ListView) findViewById(R.id.listitem);
        registerForContextMenu(songListView);
//        Song newObj= new Song();
//        songs = newObj.ShowSong();
        songs = Song.ShowSong();
        adapter = new SongAdapter(this, songs);
        songListView.setAdapter(adapter);
//        songListView.setOnItemClickListener(    new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Log.i("hi","i m inside");
//                //get the object on which the click is performed
//                Song selectedSong = songs.get(position);
//
//                //creat a new intent that will take the user to the new activity and will show the image
//                Intent showWeb = new Intent(MainActivity.this, WebViewSong.class);
//
//                //put image resource id, company name and the url of the company in extra to pass thos information to next activity
//                showWeb.putExtra("SongURL", selectedSong.songUrl);
//
//                //start the activity
//                startActivity(showWeb);
//            }
//
//        });


    }

//    //create option menu
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//        menu.clear();
//        MenuInflater inflater= getMenuInflater();
//        inflater.inflate(R.menu.optionmenu,menu);
//
//        return true;
//
//    }
//    // To dynamically populate submenu of remove song
//    public boolean onPrepareOptionsMenu(Menu menu){
//        invalidateOptionsMenu();
//        for (int i=0; i < songs.size();i++) {
//            MenuItem menuItem = menu.findItem(R.id.remove_song).getSubMenu().add(Menu.NONE,i, Menu.NONE, songs.get(i).songName);
//        }
//        return true;
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item){
//
//        if (item.getItemId()<100 ){     //for submenu
//            if (songs.size() >1){           // remove songs only if there is more than one song in active playlist
//                songs.remove(item.getItemId());
//                adapter.notifyDataSetChanged();}        //notify adapter of the data changed
//            else {Toast.makeText(this,"Only one song left in playlist ...cant remove that",Toast.LENGTH_LONG).show();}
//            return true;
//        }
//
//        if (item.getItemId() ==R.id.add_song){          // to add a song
//                                    // get prompts.xml view
//            LayoutInflater li = LayoutInflater.from(this);
//            View promptsView = li.inflate(R.layout.dialog_pop, null);
//            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//                                    // set prompts.xml to alertdialog builder
//            alertDialogBuilder.setView(promptsView);
//            final EditText addSongName = (EditText) promptsView.findViewById(R.id.songNameInput);
//            final EditText addArtistName = (EditText) promptsView.findViewById(R.id.artistNameInput);
//            final EditText addSongURL = (EditText) promptsView.findViewById(R.id.songURLInput);
//            final EditText addSongWikiURL = (EditText) promptsView.findViewById(R.id.songWikiURLInput);
//            final EditText addArtistWikiURL = (EditText) promptsView.findViewById(R.id.artistWikiURLInput);
//                                        // set dialog message
//            alertDialogBuilder
//                    .setCancelable(false)
//                    .setPositiveButton("OK",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog,int id) {
//                                                                // get user input and set it to result
//                                                                // edit text
//                                                                //result.setText(userInput.getText());
//                                    final Song addedSong = new Song();
//                                    addedSong.songName=addSongName.getText().toString();
//                                    addedSong.artist = addArtistName.getText().toString();
//                                    addedSong.songUrl=addSongURL.getText().toString();
//                                    addedSong.songWikiURL= addSongWikiURL.getText().toString();
//                                    addedSong.artistWiki =addArtistWikiURL.getText().toString();
//                                    if ((addedSong.songName.isEmpty() || addedSong.artist.isEmpty() ||addedSong.songUrl.isEmpty()||addedSong.songWikiURL.isEmpty()|| addedSong.artistWiki.isEmpty()) ){
//                                        Toast.makeText(MainActivity.this,"Please fill in all the fields",Toast.LENGTH_LONG).show();
//                                    }
//                                    else {
//                                        songs.add(addedSong);
//                                        adapter.notifyDataSetChanged();
//                                    }
//                                }
//                            })
//                    .setNegativeButton("Cancel",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog,int id) {
//                                    dialog.cancel();
//                                }
//                            });
//                                     // create alert dialog
//            AlertDialog alertDialog = alertDialogBuilder.create();
//                                    // show it
//            alertDialog.show();
//            return true;
//
//        }
//
//        if (item.getItemId()==R.id.remove_song){
//            return true;
//        }
//        if (item.getItemId()==R.id.Exit){
//            //Toast.makeText(MainActivity.this,"exit", Toast.LENGTH_LONG).show();
//            finish();
//            return true;
//        }
//        else return false;
//
//
//        }
//
//    // Create Context Menu
//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v,
//                                    ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.context_menu, menu);
//    }
//    // On Context menu item selected
//    public boolean onContextItemSelected(MenuItem context_item){
//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) context_item.getMenuInfo();
//
//        //get the information of the item user selected
//        Song selectedSong = songs.get(info.position);
//
//        switch(context_item.getItemId())
//        {
//            case(R.id.view):
//                //creat a new intent that will take the user to the new activity and will show the image
//                Intent showSong = new Intent(MainActivity.this, WebViewSong.class);
//
//                //put image resource id, company name and the url of the company in extra to pass thos information to next activity
//                showSong.putExtra("SongURL", selectedSong.songUrl);
//
//                //start the activity
//                startActivity(showSong);
//                return true;
//            case(R.id.song_wiki):
//                        //creat a new intent that will take the user to the new activity and will show the image
//                        Intent showSongWiki = new Intent(MainActivity.this, WebViewSong.class);
//
//                        //put image resource id, company name and the url of the company in extra to pass thos information to next activity
//                showSongWiki.putExtra("SongURL", selectedSong.songWikiURL);
//
//                        //start the activity
//                        startActivity(showSongWiki);
//
//                return true;
//            case(R.id.artist_wiki):
//                //creat a new intent that will take the user to the new activity and will show the image
//                Intent showArtistWiki = new Intent(MainActivity.this, WebViewSong.class);
//
//                //put image resource id, company name and the url of the company in extra to pass thos information to next activity
//                showArtistWiki.putExtra("SongURL", selectedSong.artistWiki);
//
//                //start the activity
//                startActivity(showArtistWiki);
//                return true;
//            default:
//                return false;
//        }
//    }





}
