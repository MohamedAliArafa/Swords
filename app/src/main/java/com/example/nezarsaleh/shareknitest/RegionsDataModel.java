package com.example.nezarsaleh.shareknitest;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Nezar Saleh on 10/21/2015.
 */
public class RegionsDataModel extends ArrayList<Parcelable> implements Parcelable  {

    int ID;
    String RegionEnName;
    Double RegionLatitude,RegionLongitude;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getRegionEnName() {
        return RegionEnName;
    }

    public void setRegionEnName(String regionEnName) {
        RegionEnName = regionEnName;
    }

    public Double getRegionLatitude() {
        return RegionLatitude;
    }

    public void setRegionLatitude(Double regionLatitude) {
        RegionLatitude = regionLatitude;
    }

    public Double getRegionLongitude() {
        return RegionLongitude;
    }

    public void setRegionLongitude(Double regionLongitude) {
        RegionLongitude = regionLongitude;
    }

    protected RegionsDataModel(Parcel in) {
        ID = in.readInt();
        RegionEnName = in.readString();
        RegionLatitude=in.readDouble();
        RegionLongitude=in.readDouble();

    }

    public static final Creator<RegionsDataModel> CREATOR = new Creator<RegionsDataModel>() {
        @Override
        public RegionsDataModel createFromParcel(Parcel in) {
            return new RegionsDataModel(in);
        }

        @Override
        public RegionsDataModel[] newArray(int size) {
            return new RegionsDataModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(RegionEnName);
        dest.writeDouble(RegionLatitude);
        dest.writeDouble(RegionLongitude);
    }
}
