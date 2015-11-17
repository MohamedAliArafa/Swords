package rta.ae.sharekni;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Nezar Saleh on 10/6/2015.
 */
public class Ride_Details_Passengers_DataModel extends ArrayList<Parcelable> implements Parcelable {


    public static final Creator<Ride_Details_Passengers_DataModel> CREATOR = new Creator<Ride_Details_Passengers_DataModel>() {
        @Override
        public Ride_Details_Passengers_DataModel createFromParcel(Parcel in) {
            return new Ride_Details_Passengers_DataModel(in);
        }

        @Override
        public Ride_Details_Passengers_DataModel[] newArray(int size) {
            return new Ride_Details_Passengers_DataModel[size];
        }
    };
    String AccountName;
    String AccountNationalityEn;
    String AccountMobile;
    String AccountPhoto;
    int PassengerId;
    int DriverId;
    int RouteId;
    int Rate;

    public int getRate() {
        return Rate;
    }

    public void setRate(int rate) {
        Rate = rate;
    }

    public int getRouteId() {
        return RouteId;
    }

    public void setRouteId(int routeId) {
        RouteId = routeId;
    }

    public int getDriverId() {
        return DriverId;
    }

    public void setDriverId(int driverId) {
        DriverId = driverId;
    }

    public Ride_Details_Passengers_DataModel(Parcel in) {

        AccountName=in.readString();
        AccountNationalityEn=in.readString();
        AccountMobile=in.readString();

    }

    public String getAccountPhoto() {
        return AccountPhoto;
    }

    public void setAccountPhoto(String accountPhoto) {
        AccountPhoto = accountPhoto;
    }

    public int getPassengerId() {
        return PassengerId;
    }

    public void setPassengerId(int passengerId) {
        PassengerId = passengerId;
    }

    public String getAccountMobile() {
        return AccountMobile;
    }

    public void setAccountMobile(String accountMobile) {
        AccountMobile = accountMobile;
    }

    public String getAccountName() {
        return AccountName;
    }

    public void setAccountName(String accountName) {
        AccountName = accountName;
    }

    public String getAccountNationalityEn() {
        return AccountNationalityEn;
    }

    public void setAccountNationalityEn(String accountNationalityEn) {
        AccountNationalityEn = accountNationalityEn;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(AccountName);
        dest.writeString(AccountNationalityEn);
        dest.writeString(AccountMobile);
    }
}
