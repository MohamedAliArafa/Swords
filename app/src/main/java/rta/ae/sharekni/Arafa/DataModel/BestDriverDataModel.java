package rta.ae.sharekni.Arafa.DataModel;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Fantom on 03/09/2015.
 */
public class BestDriverDataModel extends ArrayList<Parcelable> implements Parcelable {

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String Name,PhotoURL,Nationality,Language,phoneNumber,LastSeen;
    public int ID,Rating;

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public Bitmap photo;


    public BestDriverDataModel(Parcel in) {
        Name = in.readString();
        PhotoURL = in.readString();
        Nationality = in.readString();
        ID = in.readInt();
        Rating = in.readInt();
        Language=in.readString();
        LastSeen=in.readString();
    }

    public static final Creator<BestDriverDataModel> CREATOR = new Creator<BestDriverDataModel>() {
        @Override
        public BestDriverDataModel createFromParcel(Parcel in) {
            return new BestDriverDataModel(in);
        }

        @Override
        public BestDriverDataModel[] newArray(int size) {
            return new BestDriverDataModel[size];
        }
    };


    public void setLanguage(String language) {
        Language = language;
    }

    public String getLanguage() {
        return Language;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPhotoURL(String photoURL) {
        PhotoURL = photoURL;
    }

    public void setNationality(String nationality) {
        Nationality = nationality;
    }

    public void setRating(int rating) {
        Rating = rating;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public String getPhotoURL() {
        return PhotoURL;
    }

    public String getNationality() {
        return Nationality;
    }

    public int getRating() {
        return Rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getLastSeen() {
        return LastSeen;
    }

    public void setLastSeen(String lastSeen) {
        LastSeen = lastSeen;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Name);
        parcel.writeString(PhotoURL);
        parcel.writeString(Nationality);
        parcel.writeInt(ID);
        parcel.writeInt(Rating);
        parcel.writeString(Language);
        parcel.writeString(LastSeen);
    }
}
