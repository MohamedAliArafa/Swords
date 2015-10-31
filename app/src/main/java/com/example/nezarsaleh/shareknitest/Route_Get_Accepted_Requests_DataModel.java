package com.example.nezarsaleh.shareknitest;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Nezar Saleh on 10/31/2015.
 */
public class Route_Get_Accepted_Requests_DataModel  extends ArrayList<Parcelable> implements  Parcelable{

    int RoutePassengerId,AccountId,VehicleId,RouteId;

    public Route_Get_Accepted_Requests_DataModel(Parcel in) {
        RoutePassengerId = in.readInt();
        AccountId = in.readInt();
        VehicleId = in.readInt();
        RouteId = in.readInt();
    }

    public static final Creator<Route_Get_Accepted_Requests_DataModel> CREATOR = new Creator<Route_Get_Accepted_Requests_DataModel>() {
        @Override
        public Route_Get_Accepted_Requests_DataModel createFromParcel(Parcel in) {
            return new Route_Get_Accepted_Requests_DataModel(in);
        }

        @Override
        public Route_Get_Accepted_Requests_DataModel[] newArray(int size) {
            return new Route_Get_Accepted_Requests_DataModel[size];
        }
    };

    public int getRoutePassengerId() {
        return RoutePassengerId;
    }

    public void setRoutePassengerId(int routePassengerId) {
        RoutePassengerId = routePassengerId;
    }

    public int getAccountId() {
        return AccountId;
    }

    public void setAccountId(int passengerId) {
        AccountId = passengerId;
    }

    public int getVehicleId() {
        return VehicleId;
    }

    public void setVehicleId(int vehicleId) {
        VehicleId = vehicleId;
    }

    public int getRouteId() {
        return RouteId;
    }

    public void setRouteId(int routeId) {
        RouteId = routeId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(RoutePassengerId);
        dest.writeInt(AccountId);
        dest.writeInt(VehicleId);
        dest.writeInt(RouteId);
    }
}
