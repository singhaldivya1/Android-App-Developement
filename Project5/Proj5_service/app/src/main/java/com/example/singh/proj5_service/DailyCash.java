package com.example.singh.proj5_service;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by singh on 04/30/18.
 */

public class DailyCash implements Parcelable {
    int mDate  ;
    int mMonth ;
    int mYear ;
    int mCash ;
    String mDayOfWeek  ;

    public DailyCash(int date, int month, int year, int cash, String day) {
        this.mDate = date;
        this.mMonth = month;
        this.mYear = year;
        this.mCash = cash;
        this.mDayOfWeek = day;

    }

    public DailyCash(Parcel in) {
        mDate = in.readInt() ;
        mMonth = in.readInt() ;
        mYear = in.readInt() ;
        mCash = in.readInt() ;
        mDayOfWeek = in.readString() ;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mDate);
        out.writeInt(mMonth) ;
        out.writeInt(mYear) ;
        out.writeInt(mCash) ;
        out.writeString(mDayOfWeek) ;
    }

    public static final Parcelable.Creator<DailyCash> CREATOR
            = new Parcelable.Creator<DailyCash>() {

        public DailyCash createFromParcel(Parcel in) {
            return new DailyCash(in) ;
        }

        public DailyCash[] newArray(int size) {
            return new DailyCash[size];
        }
    };

    public int describeContents()  {
        return 0 ;
    }

}
