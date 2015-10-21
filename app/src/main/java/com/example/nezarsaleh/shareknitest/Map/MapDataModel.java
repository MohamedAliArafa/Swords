package com.example.nezarsaleh.shareknitest.Map;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nezar on 9/4/2015.
 */
public class MapDataModel implements Parcelable {
    String FromEmirateEnName,ToEmirateEnName,FromRegionEnName,ToRegionEnName;
    String FromEmirateArName,FromRegionArName,ToRegionArName,ToEmirateArName;

    int NoOfRoutes;



    public Double longitude;
   public   Double latitude;

    int FromEmirateId,ToEmirateId,FromRegionId,ToRegionId;


    public MapDataModel(Parcel in) {
        FromEmirateEnName = in.readString();
        ToEmirateEnName = in.readString();
        FromRegionEnName = in.readString();
        ToRegionEnName = in.readString();
        FromEmirateArName = in.readString();
        FromRegionArName = in.readString();
        ToRegionArName = in.readString();
        ToEmirateArName = in.readString();
        FromEmirateId = in.readInt();
        ToEmirateId = in.readInt();
        FromRegionId = in.readInt();
        ToRegionId = in.readInt();
        NoOfRoutes=in.readInt();
    }

    public static final Creator<MapDataModel> CREATOR = new Creator<MapDataModel>() {
        @Override
        public MapDataModel createFromParcel(Parcel in) {
            return new MapDataModel(in);
        }

        @Override
        public MapDataModel[] newArray(int size) {
            return new MapDataModel[size];
        }
    };

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
            this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }


    public void setNoOfRoutes(int noOfRoutes) {
        NoOfRoutes = noOfRoutes;
    }

    public int getNoOfRoutes() {
        return NoOfRoutes;
    }

    public String getFromEmirateEnName() {
        return FromEmirateEnName;
    }

    public void setFromEmirateEnName(String fromEmirateEnName) {
        FromEmirateEnName = fromEmirateEnName;
    }

    public String getToEmirateEnName() {
        return ToEmirateEnName;
    }

    public void setToEmirateEnName(String toEmirateEnName) {
        ToEmirateEnName = toEmirateEnName;
    }

    public String getFromRegionEnName() {
        return FromRegionEnName;
    }

    public void setFromRegionEnName(String fromRegionEnName) {
        FromRegionEnName = fromRegionEnName;
    }

    public String getToRegionEnName() {
        return ToRegionEnName;
    }

    public void setToRegionEnName(String toRegionEnName) {
        ToRegionEnName = toRegionEnName;
    }

    public int getFromEmirateId() {
        return FromEmirateId;
    }

    public void setFromEmirateId(int fromEmirateId) {
        FromEmirateId = fromEmirateId;
    }

    public int getToEmirateId() {
        return ToEmirateId;
    }

    public void setToEmirateId(int toEmirateId) {
        ToEmirateId = toEmirateId;
    }

    public int getFromRegionId() {
        return FromRegionId;
    }

    public void setFromRegionId(int fromRegionId) {
        FromRegionId = fromRegionId;
    }

    public int getToRegionId() {
        return ToRegionId;
    }

    public void setToRegionId(int toRegionId) {
        ToRegionId = toRegionId;
    }


    public String getFromEmirateArName() {
        return FromEmirateArName;
    }

    public void setFromEmirateArName(String fromEmirateArName) {
        FromEmirateArName = fromEmirateArName;
    }

    public String getFromRegionArName() {
        return FromRegionArName;
    }

    public void setFromRegionArName(String fromRegionArName) {
        FromRegionArName = fromRegionArName;
    }

    public String getToRegionArName() {
        return ToRegionArName;
    }

    public void setToRegionArName(String toRegionArName) {
        ToRegionArName = toRegionArName;
    }

    public String getToEmirateArName() {
        return ToEmirateArName;
    }

    public void setToEmirateArName(String toEmirateArName) {
        ToEmirateArName = toEmirateArName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(FromEmirateEnName);
        dest.writeString(ToEmirateEnName);
        dest.writeString(FromRegionEnName);
        dest.writeString(ToRegionEnName);
        dest.writeString(FromEmirateArName);
        dest.writeString(FromRegionArName);
        dest.writeString(ToRegionArName);
        dest.writeString(ToEmirateArName);
        dest.writeInt(FromEmirateId);
        dest.writeInt(ToEmirateId);
        dest.writeInt(FromRegionId);
        dest.writeInt(ToRegionId);
        dest.writeInt(NoOfRoutes);
    }
}
