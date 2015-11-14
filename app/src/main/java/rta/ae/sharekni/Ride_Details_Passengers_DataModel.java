package rta.ae.sharekni;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Nezar Saleh on 10/6/2015.
 */
public class Ride_Details_Passengers_DataModel extends ArrayList<Parcelable> implements Parcelable {


    String AccountName;
    String AccountNationalityEn;
    String AccountMobile;

    public String getAccountPhoto() {
        return AccountPhoto;
    }

    public void setAccountPhoto(String accountPhoto) {
        AccountPhoto = accountPhoto;
    }

    String AccountPhoto;

    public int getPassengerId() {
        return PassengerId;
    }

    public void setPassengerId(int passengerId) {
        PassengerId = passengerId;
    }

    int PassengerId;




    public Ride_Details_Passengers_DataModel(Parcel in) {

        AccountName=in.readString();
        AccountNationalityEn=in.readString();
        AccountMobile=in.readString();

    }


    public String getAccountMobile() {
        return AccountMobile;
    }

    public void setAccountMobile(String accountMobile) {
        AccountMobile = accountMobile;
    }

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


    public void setAccountName(String accountName) {
        AccountName = accountName;
    }

    public void setAccountNationalityEn(String accountNationalityEn) {
        AccountNationalityEn = accountNationalityEn;
    }


    public String getAccountName() {
        return AccountName;
    }

    public String getAccountNationalityEn() {
        return AccountNationalityEn;
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
