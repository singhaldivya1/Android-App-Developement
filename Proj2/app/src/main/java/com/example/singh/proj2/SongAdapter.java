package com.example.singh.proj2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by singh on 02/22/18.
 */

public class SongAdapter extends BaseAdapter {
    private Context songcontext;
    private List<Song> songid;
    private LayoutInflater mInflater;
    //Songadapter constructor
    public SongAdapter(Context c, List<Song> id){
        songcontext = c;
        this.songid = id;

        mInflater = (LayoutInflater) songcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //1
    @Override
    public int getCount() {
        return songid.size();
    }

    //2
    @Override
    public Object getItem(int position) {
        return songid.get(position);
    }

    //3
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View rowView = mInflater.inflate(R.layout.simple_list_item_1, parent, false);
        TextView titleTextView = (TextView) rowView.findViewById(R.id.listSongName);


// Get subtitle element
        TextView subtitleTextView = (TextView) rowView.findViewById(R.id.listArtistName);
        Song everySong= (Song) getItem(position);
        titleTextView.setText(everySong.songName);
        subtitleTextView.setText(everySong.artist);
        return rowView;
    }
}
