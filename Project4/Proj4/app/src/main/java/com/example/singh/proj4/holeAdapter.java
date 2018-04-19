package com.example.singh.proj4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by singh on 04/14/18.
 */

public class holeAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context mContext;
    public int[] mHoleIds;
    public ArrayList<ImageView> holeViews = new ArrayList<ImageView>();

    public holeAdapter(Context c, int[] mHoleIds ){
        mContext = c;
        this.mHoleIds = mHoleIds;
        //holeViews.clear();
    }
    public int getCount() {
        return mHoleIds.length;
    }

    public Object getItem(int position) {
        return this.mHoleIds[position];
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView listView;
        if (convertView == null) {
            listView = new ImageView(mContext);
            listView.setLayoutParams(new ListView.LayoutParams(150, 150));
            listView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            holeViews.add(listView);
        } else {
            listView = (ImageView) convertView;
        }

        listView.setImageResource(mHoleIds[position]);
        listView.setVisibility(View.VISIBLE);

        return listView;

    }


}