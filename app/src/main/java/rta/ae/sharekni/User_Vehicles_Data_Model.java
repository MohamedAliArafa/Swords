package rta.ae.sharekni;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Nezar Saleh on 11/14/2015.
 */

public class User_Vehicles_Data_Model extends ArrayList<Parcelable> implements Parcelable {
    public String PlateCode, PlateNumber;

    protected User_Vehicles_Data_Model(Parcel in) {
        PlateCode = in.readString();
        PlateNumber = in.readString();
    }

    public static final Creator<User_Vehicles_Data_Model> CREATOR = new Creator<User_Vehicles_Data_Model>() {
        @Override
        public User_Vehicles_Data_Model createFromParcel(Parcel in) {
            return new User_Vehicles_Data_Model(in);
        }

        @Override
        public User_Vehicles_Data_Model[] newArray(int size) {
            return new User_Vehicles_Data_Model[size];
        }
    };

    public String getPlateCode() {
        return PlateCode;
    }

    public void setPlateCode(String plateCode) {
        PlateCode = plateCode;
    }

    public String getPlateNumber() {
        return PlateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        PlateNumber = plateNumber;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(PlateCode);
        dest.writeString(PlateNumber);
    }
}
