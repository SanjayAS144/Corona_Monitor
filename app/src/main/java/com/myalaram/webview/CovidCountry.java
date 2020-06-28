package com.myalaram.webview;

import android.os.Parcel;
import android.os.Parcelable;

public class CovidCountry implements Parcelable {

    String mCovidCountry,mcases,mtodaycases,mDeaths,mtodayDeaths,mrecovered,mFlage;


    public CovidCountry(String mCovidCountry, String mcases, String mtodaycases, String mDeaths, String mtodayDeaths, String mrecovered, String mFlage) {
        this.mCovidCountry = mCovidCountry;
        this.mcases = mcases;
        this.mtodaycases = mtodaycases;
        this.mDeaths = mDeaths;
        this.mtodayDeaths = mtodayDeaths;
        this.mrecovered = mrecovered;
        this.mFlage = mFlage;
    }

    public String getmCovidCountry() {
        return mCovidCountry;
    }

    public String getMcases() {
        return mcases;
    }

    public String getMtodaycases() {
        return mtodaycases;
    }

    public String getmDeaths() {
        return mDeaths;
    }

    public String getMtodayDeaths() {
        return mtodayDeaths;
    }

    public String getMrecovered() {
        return mrecovered;
    }

    public String getmFlage() {
        return mFlage;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mCovidCountry);
        dest.writeString(this.mcases);
        dest.writeString(this.mtodaycases);
        dest.writeString(this.mDeaths);
        dest.writeString(this.mtodayDeaths);
        dest.writeString(this.mrecovered);
        dest.writeString(this.mFlage);
    }

    protected CovidCountry(Parcel in) {
        this.mCovidCountry = in.readString();
        this.mcases = in.readString();
        this.mtodaycases = in.readString();
        this.mDeaths = in.readString();
        this.mtodayDeaths = in.readString();
        this.mrecovered = in.readString();
        this.mFlage = in.readString();
    }

    public static final Creator<CovidCountry> CREATOR = new Creator<CovidCountry>() {
        @Override
        public CovidCountry createFromParcel(Parcel source) {
            return new CovidCountry(source);
        }

        @Override
        public CovidCountry[] newArray(int size) {
            return new CovidCountry[size];
        }
    };
}
