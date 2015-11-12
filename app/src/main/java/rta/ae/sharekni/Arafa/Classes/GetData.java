package rta.ae.sharekni.Arafa.Classes;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.pkmmte.view.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import rta.ae.sharekni.Arafa.Activities.Profile;
import rta.ae.sharekni.Arafa.DataModel.BestRouteDataModelDetails;
import rta.ae.sharekni.Arafa.DataModelAdapter.BestRouteDataModelAdapterDetails;
import rta.ae.sharekni.DriverCreatedRides;
import rta.ae.sharekni.HomePage;
import rta.ae.sharekni.QuickSearchDataModel;
import rta.ae.sharekni.QuickSearchResultAdapter;
import rta.ae.sharekni.R;
import rta.ae.sharekni.Ride_Details_Passengers_Adapter;
import rta.ae.sharekni.Ride_Details_Passengers_DataModel;

public class GetData {

    final JSONArray[] myJsonArray = new JSONArray[1];

//    public static final String DOMAIN = "http://sharekni.sdgstaff.com";
//http://sharekni.sdgstaff.com/_mobfiles/CLS_MobAndroid.asmx
    //http://sharekni-web.sdg.ae/_mobfiles/CLS_MobAndroid.asmx/


    // sharekni sdg staff
    //  public static final String DOMAIN = "http://sharekni.sdgstaff.com/_mobfiles/CLS_MobAndroid.asmx/";
    // public static final String NonOpDomain = "http://sharekni.sdgstaff.com/_mobfiles/CLS_MobAndroid.asmx";
    // public static final String PhotoURL = "http://sharekni.sdgstaff.com/uploads/personalphoto/";


//    public static final String DOMAIN = "http://213.42.51.219/_mobfiles/CLS_MobAndroid.asmx/";
//    public static final String NonOpDomain = "http://213.42.51.219/_mobfiles/CLS_MobAndroid.asmx";
//    public static final String PhotoURL = "http://213.42.51.219/uploads/personalphoto/";
//

//
//        public static final String DOMAIN = "http://sharekni-web.sdg.ae/_mobfiles/CLS_MobAndroid.asmx/";
//    public static final String NonOpDomain = "http://sharekni-web.sdg.ae/_mobfiles/CLS_MobAndroid.asmx";
//    public static final String PhotoURL = "http://sharekni-web.sdg.ae/uploads/personalphoto/";




    public static final String DOMAIN = "https://www.sharekni.ae/_mobfiles/CLS_MobAndroid.asmx/";
    public static final String NonOpDomain = "https://www.sharekni.ae/_mobfiles/CLS_MobAndroid.asmx";
    public static final String PhotoURL = "https://www.sharekni.ae/uploads/personalphoto/";


//    String data;
//    String loginFormUrl                 = DOMAIN + "/_mobfiles/CLS_MobAccount.asmx/CheckLogin?";
//    String getDriverById                = DOMAIN + "/_mobfiles/CLS_MobAccount.asmx/Get?id=";
//    String ChangePasswordUrl            = DOMAIN + "/_mobfiles/CLS_MobAccount.asmx/ChangePassword?";
//    String Driver_GetReview             = DOMAIN + "/_mobfiles/CLS_MobAccount.asmx/Driver_GetReviewList?";
//    String EditProfileUrl               = DOMAIN + "/_mobfiles/CLS_MobAccount.asmx/EditProfile?";
//    String ForgetPasswordUrl            = DOMAIN + "/_mobfiles/CLS_MobAccount.asmx/ForgetPassword?";
//    String getImage                     = DOMAIN + "/_mobfiles/CLS_MobAccount.asmx/GetPhotoPath?s_FileName=";
//
//    String getBestDriverUrl             = DOMAIN + "/_mobfiles/CLS_MobDriver.asmx/GetBestDrivers";
//    String DriverEditCarPoolUrl         = DOMAIN + "/_mobfiles/CLS_MobDriver.asmx/Driver_EditCarpool?";
//    String DriverCreateCarPoolUrl       = DOMAIN + "/_mobfiles/CLS_MobDriver.asmx/Driver_CreateCarpool?";
//    String QuickSearchUrl               = DOMAIN + "/_mobfiles/CLS_MobDriver.asmx/Passenger_FindRide?";
//
//    String getBestRouteUrl              = DOMAIN + "/_mobfiles/CLS_MobRoute.asmx/GetMostDesiredRides";
//    String getDriverRideUrl             = DOMAIN + "/_mobfiles/CLS_MobRoute.asmx/GetDriverDetailsByAccountId?AccountId=";
//    String getRouteByRouteId            = DOMAIN + "/_mobfiles/CLS_MobRoute.asmx/GetRouteByRouteId?RouteId=";
//    String Passenger_SendAlert          = DOMAIN + "/_mobfiles/CLS_MobRoute.asmx/Passenger_SendAlert?";
//    String GetPassengersByRouteIDUrl    = DOMAIN + "/_mobfiles/CLS_MobRoute.asmx/GetPassengersByRouteId?id=";
//    String Passenger_Review_Driver      = DOMAIN + "/_mobfiles/CLS_MobRoute.asmx/Passenger_ReviewDriver?";
//    String Driver_Remove_Passenger      = DOMAIN + "/_mobfiles/CLS_MobRoute.asmx/GetPassengersByRouteId?";
//    String Driver_RemoveReview          = DOMAIN + "/_mobfiles/CLS_MobRoute.asmx/Driver_RemoveReview?";
//    String DriverAlertsForRequestUrl    = DOMAIN + "/_mobfiles/CLS_MobRoute.asmx/Driver_AlertsForRequest?d_AccountId=";
//    String DriverAcceptPassengerUrl     = DOMAIN + "/_mobfiles/CLS_MobRoute.asmx/Driver_AcceptRequest?RequestId=";
//    String Driver_DeleteRouteUrl        = DOMAIN + "/_mobfiles/CLS_MobRoute.asmx/Route_Delete?RouteId=";
//    String Passenger_Rqs_From_Driver    = DOMAIN + "/_mobfiles/CLS_MobRoute.asmx/Passenger_GetAcceptedRequestsFromDriver?accountId=";
//
//    String Driver_RemovePassenger       = DOMAIN + "/_mobfiles/CLS_MobRoute.asmx/Driver_RemovePassenger?ID=";
//
//    String Regions_By_Em_Id             = DOMAIN + "/_mobfiles/CLS_MobMasterData.asmx/GetRegionsByEmirateId?id=";
//    String Emirates_By_ID               = DOMAIN + "/_mobfiles/CLS_MobMasterData.asmx/GetEmirates";
//    String getNationalitiesUrl          = DOMAIN + "/_mobfiles/CLS_MobMasterData.asmx/GetNationalities?id=0";
//    String getPrefLanguageUrl           = DOMAIN + "/_mobfiles/CLS_MobMasterData.asmx/GetPrefferedLanguages";
//    String GetAgeRanges                 = DOMAIN + "/_mobfiles/CLS_MobMasterData.asmx/GetAgeRanges";
//
//    String GetVehiclesUrl               = DOMAIN + "/_mobfiles/CLS_MobVehicle.asmx/GetByDriverId?id=20027";
//
//    String GetMapLookUpUrl              = DOMAIN + "/_mobfiles/CLS_MobRoute.asmx/GetFromOnlyMostDesiredRides";


    String data;
    String loginFormUrl = DOMAIN + "CheckLogin?";
    String getDriverById = DOMAIN + "Get?id=";
    String ChangePasswordUrl = DOMAIN + "ChangePassword?";
    String Driver_GetReview = DOMAIN + "Driver_GetReviewList?";
    String EditProfileUrl = DOMAIN + "EditProfile?";
    String ForgetPasswordUrl = DOMAIN + "ForgetPassword?";
    String getImage = DOMAIN + "GetPhotoPath?s_FileName=";

    String getBestDriverUrl = DOMAIN + "GetBestDrivers";
    String DriverEditCarPoolUrl = DOMAIN + "Driver_EditCarpool?";
    String DriverCreateCarPoolUrl = DOMAIN + "Driver_CreateCarpool?";
    String QuickSearchUrl = DOMAIN + "Passenger_FindRide?";

    String getBestRouteUrl = DOMAIN + "GetMostDesiredRides";
    String getDriverRideUrl = DOMAIN + "GetDriverDetailsByAccountId?AccountId=";
    String getRouteByRouteId = DOMAIN + "GetRouteByRouteId?RouteId=";
    String Passenger_SendAlert = DOMAIN + "Passenger_SendAlert?";
    String GetPassengersByRouteIDUrl = DOMAIN + "GetPassengersByRouteId?id=";
    String GetAcceptedRequestsURL = DOMAIN + "Route_GetAcceptedRequests?RouteId=";
    String Passenger_Review_Driver = DOMAIN + "Passenger_ReviewDriver?";
    String Driver_RemoveReview = DOMAIN + "Driver_RemoveReview?";
    String DriverAlertsForRequestUrl = DOMAIN + "Driver_AlertsForRequest?d_AccountId=";
    String DriverAcceptPassengerUrl = DOMAIN + "Driver_AcceptRequest?RequestId=";
    String DriverRegisterVehicleUrl = DOMAIN + "Driver_RegisterVehicleWithETService?";
    String Driver_DeleteRouteUrl = DOMAIN + "Route_Delete?RouteId=";
    String Passenger_Rqs_From_Driver = DOMAIN + "Passenger_GetAcceptedRequestsFromDriver?accountId=";
    String Driver_RemovePassenger = DOMAIN + "Driver_RemovePassenger?";
    String Passenger_LeaveRideUrl = DOMAIN + "Passenger_RemovePassenger?RoutePassengerId=";
    String Regions_By_Em_Id = DOMAIN + "GetRegionsByEmirateId?id=";
    String Emirates_By_ID = DOMAIN + "GetEmirates";
    String getNationalitiesUrl = DOMAIN + "GetNationalities?id=0";
    String getPrefLanguageUrl = DOMAIN + "GetPrefferedLanguages";
    String GetAgeRanges = DOMAIN + "GetAgeRanges";

    String GetVehiclesUrl = DOMAIN + "GetByDriverId?id=";

    String GetMapLookUpUrl = DOMAIN + "GetFromOnlyMostDesiredRides";


    public void QuickSearchForm(int myId, char gender, String time
            , int fromEmId, int fromRegId, int toEmId, int toRegId
            , int pref_lnag, int pref_nat, int age_Ranged_id
            , String startDate, int saveFind, ListView lv, Activity context) {

        QuickSearchStringRequest(QuickSearchUrl + "AccountID=" + myId
                + "&PreferredGender=" + gender
                + "&Time="
                + "&FromEmirateID=" + fromEmId
                + "&FromRegionID=" + fromRegId
                + "&ToEmirateID=" + toEmId
                + "&ToRegionID=" + toRegId
                + "&PrefferedLanguageId=" + pref_lnag
                + "&PrefferedNationlaities="
                + "&AgeRangeId=" + age_Ranged_id
                + "&StartDate="
                + "&SaveFind=" + saveFind
                + "&IsPeriodic="

                , lv
                , myId, context);


    }

    public void GetPassengersByRouteIdForm(int ID, ListView lv, Activity context) {
        GetPassengersByRouteIdStringRequest(GetPassengersByRouteIDUrl + ID, lv, context);
    }

    public void DriverEditCarPoolFrom(int myId, String En_Name, int From_EmID
            , int To_EmID, int From_RegId, int To_RegId, String isRounded
            , String Time, String sat, String sun, String mon, String tue, String wed
            , String thu, String fri, char gender, int VehicleID, int NoOfSeats
            , double StartLat, double StartLng, double EndLat, double EndLng
            , int Pref_Lang, int Nat, int AgeRangedId, String Start_Date, Activity context) {

        DriverCreateCarPoolStringRequest(DriverEditCarPoolUrl + "RouteID=" + myId
                + "&EnName=" + URLEncoder.encode(En_Name)
                + "&FromEmirateID=" + From_EmID
                + "&ToEmirateID=" + To_EmID
                + "&FromRegionID=" + From_RegId
                + "&ToRegionID=" + To_RegId
                + "&IsRounded=" + isRounded
                + "&Time=" + Time
                + "&Saturday=" + sat
                + "&Sunday=" + sun
                + "&Monday=" + mon
                + "&Tuesday=" + tue
                + "&Wednesday=" + wed
                + "&Thursday=" + thu
                + "&Friday=" + fri
                + "&PreferredGender=" + gender
                + "&VehicleID=" + VehicleID
                + "&NoOfSeats=" + NoOfSeats
                + "&StartLat=" + StartLat
                + "&StartLng=" + StartLng
                + "&EndLat=" + EndLat
                + "&EndLng=" + EndLng
                + "&PrefferedLanguageId=" + Pref_Lang
                + "&PrefferedNationlaities=" + Nat
                + "&AgeRangeId=" + AgeRangedId
                + "&StartDate=" + Start_Date
                , context);
    }

    public void DriverCreateCarPoolFrom(int myId, String En_Name, int From_EmID
            , int To_EmID, int From_RegId, int To_RegId, String isRounded
            , String Time, String sat, String sun, String mon, String tue, String wed
            , String thu, String fri, char gender, int VehicleID, int NoOfSeats
            , double StartLat, double StartLng, double EndLat, double EndLng
            , int Pref_Lang, int Nat, int AgeRangedId, String Start_Date, Activity context) {

        DriverCreateCarPoolStringRequest(DriverCreateCarPoolUrl + "AccountID=" + myId
                + "&EnName=" + URLEncoder.encode(En_Name)
                + "&FromEmirateID=" + From_EmID
                + "&ToEmirateID=" + To_EmID
                + "&FromRegionID=" + From_RegId
                + "&ToRegionID=" + To_RegId
                + "&IsRounded=" + isRounded
                + "&Time=" + Time
                + "&Saturday=" + sat
                + "&Sunday=" + sun
                + "&Monday=" + mon
                + "&Tuesday=" + tue
                + "&Wednesday=" + wed
                + "&Thursday=" + thu
                + "&Friday=" + fri
                + "&PreferredGender=" + gender
                + "&VehicleID=" + VehicleID
                + "&NoOfSeats=" + NoOfSeats
                + "&StartLat=" + StartLat
                + "&StartLng=" + StartLng
                + "&EndLat=" + EndLat
                + "&EndLng=" + EndLng
                + "&PrefferedLanguageId=" + Pref_Lang
                + "&PrefferedNationlaities=" + Nat
                + "&AgeRangeId=" + AgeRangedId
                + "&StartDate=" + Start_Date
                , context);
    }

    public void ForgetPasswordForm(String mobileNumber, String email, Context context) {
        ForgetPasswordFormStringRequest(ForgetPasswordUrl + "mobile=" + mobileNumber + "&email=" + email, context);
    }

    public void ChangePasswordForm(int id, String oldPass, String newPAss, Context context, TextView textview) {
        ChangePasswordFormStringRequest(ChangePasswordUrl + "id=" + id + "&oldPassword=" + oldPass + "&newPassword=" + newPAss, context, textview);
    }

    public void EditProfileForm(int id, String firstName
            , String lastName
            , String mobile
            , String gender
            , String BirthDate
            , String NationalityId
            , String PreferredLanguageId
            , String NewPhotoName
            , Context context) {

        EditProfileFormStringRequest(EditProfileUrl
                + "id=" + id
                + "&firstName=" + URLEncoder.encode(firstName)
                + "&lastName=" + URLEncoder.encode(lastName)
                + "&Mobile=" + mobile
                + "&gender=" + gender
                + "&BirthDate=" + BirthDate
                + "&NationalityId=" + NationalityId
                + "&PreferredLanguageId=" + PreferredLanguageId
                + "&NewPhotoName=" + NewPhotoName
                , context);
    }

    public JSONArray GetAllDrivers() throws JSONException {
        int ID = 0;
        HandleXML obj = new HandleXML(getDriverById + ID);
        obj.fetchXML();
        while ((obj.parsingComplete && !obj.error)) ;
        return new JSONArray(obj.getData());
    }

    public JSONArray MostRidesDetails(String url) throws JSONException {
        HandleXML obj = new HandleXML(url);
        obj.fetchXML();
        while (obj.parsingComplete && !obj.error) ;
        return new JSONArray(obj.getData());
    }

    public JSONArray Search(int myId, char gender, String time
            , int fromEmId, int fromRegId, int toEmId, int toRegId
            , int pref_lnag, int pref_nat, int age_Ranged_id
            , String startDate, int saveFind, final Activity activity) throws JSONException {
        HandleXML obj = new HandleXML(QuickSearchUrl
                + "AccountID=" + myId
                + "&PreferredGender=" + gender
                + "&Time="
                + "&FromEmirateID=" + fromEmId
                + "&FromRegionID=" + fromRegId
                + "&ToEmirateID=" + toEmId
                + "&ToRegionID=" + toRegId
                + "&PrefferedLanguageId=" + pref_lnag
                + "&PrefferedNationlaities="
                + "&AgeRangeId=" + age_Ranged_id
                + "&StartDate="
                + "&SaveFind=" + saveFind
                + "&IsPeriodic=");
        obj.fetchXML();
        while ((obj.parsingComplete && !obj.error)) ;
        Log.d("Test big", obj.getData());
        return new JSONArray(obj.getData());
    }

    public JSONArray GetBestDrivers() throws JSONException {
        HandleXML obj = new HandleXML(getBestDriverUrl);
        obj.fetchXML();
        while ((obj.parsingComplete && !obj.error)) ;
        return new JSONArray(obj.getData());
    }

    public JSONArray GetDriverRides(int ID) throws JSONException {
        HandleXML obj = new HandleXML(getDriverRideUrl + ID);
        obj.fetchXML();
        while ((obj.parsingComplete && !obj.error)) ;
        return new JSONArray(obj.getData());
    }

    public int GetDesiredCount(int FromEmId, int FromRegId, int ToEmId, int ToRegId) throws JSONException {
        String url = DOMAIN + "GetMostDesiredRideDetails?AccountID=" + 0 + "&FromEmirateID=" + FromEmId + "&FromRegionID=" + FromRegId + "&ToEmirateID=" + ToEmId + "&ToRegionID=" + ToRegId;
        HandleXML obj = new HandleXML(url);
        obj.fetchXML();
        while ((obj.parsingComplete && !obj.error)) ;
        JSONArray json = new JSONArray(obj.getData());
        return json.length();
    }

    public JSONArray GetDriverAlertsForRequest(int id) throws JSONException {

        HandleXML obj = new HandleXML(DriverAlertsForRequestUrl + id);
        obj.fetchXML();
        while ((obj.parsingComplete && !obj.error)) ;
        JSONArray json = new JSONArray(obj.getData());
        return json;
    }

    public JSONArray Get_Passenger_GetAcceptedRequestsFromDriver(int id) throws JSONException {

        HandleXML obj = new HandleXML(Passenger_Rqs_From_Driver + id);
        obj.fetchXML();
        while ((obj.parsingComplete && !obj.error)) ;
        JSONArray json = new JSONArray(obj.getData());
        return json;
    }


    public String RegisterVehicle(int Driver_ID, String FileNo, String Birth_Date) throws JSONException {
        HandleXML obj = new HandleXML(DriverRegisterVehicleUrl + "AccountId=" + Driver_ID + "&TrafficFileNo=" + FileNo + "&BirthDate=" + Birth_Date);
        obj.fetchXML();
        while ((obj.parsingComplete && !obj.error)) ;
        return obj.getData();
    }


    public String DriverAcceptPassenger(int Request_ID, int isAccepted) throws JSONException {
        HandleXML obj = new HandleXML(DriverAcceptPassengerUrl + Request_ID + "&IsAccept=" + isAccepted);
        obj.fetchXML();
        while ((obj.parsingComplete && !obj.error)) ;
        return obj.getData();
    }

    public JSONObject GetDriverById(int ID) throws JSONException {
        JSONObject json;
        HandleXML obj = new HandleXML(getDriverById + ID);
        obj.fetchXML();
        while ((obj.parsingComplete && !obj.error)) ;
        json = jsonArrayToObject(obj.getData().toString());
        return json;
    }

    public JSONObject GetRouteById(int ID) throws JSONException {
        JSONObject json;
        HandleXML obj = new HandleXML(getRouteByRouteId + ID);
        obj.fetchXML();
        while ((obj.parsingComplete && !obj.error)) ;
        JSONArray jsonArray = new JSONArray(obj.getData());
        json = jsonArray.getJSONObject(0);
        return json;
    }

    public JSONArray MostDesiredRoutes() throws JSONException {
        Log.d("most rides", getBestRouteUrl);
        JSONArray json;
        HandleXML obj = new HandleXML(getBestRouteUrl);
        obj.fetchXML();
        while ((obj.parsingComplete && !obj.error)) ;
        json = new JSONArray(obj.getData().toString());
        return json;
    }

    public String Passenger_SendAlert(int Driver_ID, int Passenger_ID, int Route_ID, String Remarks) throws JSONException {
        String json;
        String Url = Passenger_SendAlert + "RouteID=" + Route_ID + "&PassengerID="
                + Passenger_ID + "&DriverID=" + Driver_ID + "&s_Remarks=" + Remarks;
        HandleXML obj = new HandleXML(Url);
        obj.fetchXML();
        while ((obj.parsingComplete && !obj.error)) ;
        json = obj.getData().toString();
        return json;

    }


    public String Passenger_Review_Driver(int Driver_ID, int Passenger_ID, int Route_ID, String Remarks) throws JSONException {
        String Url = Passenger_Review_Driver + "PassengerId=" + Passenger_ID + "&DriverId="
                + Driver_ID + "&RouteId=" + Route_ID + "&&ReviewText=" + Remarks;
        HandleXML obj = new HandleXML(Url);
        obj.fetchXML();
        while ((obj.parsingComplete && !obj.error)) ;
        return obj.getData();

    }

    public String Driver_Remove_Passenger(int Passenger_ID) throws JSONException {
        String Url = Driver_RemovePassenger + "RoutePassengerId=" + Passenger_ID;
        HandleXML obj = new HandleXML(Url);
        obj.fetchXML();
        while ((obj.parsingComplete && !obj.error)) ;
        return obj.getData();

    }


    public String Passenger_LeaveRide(int Passenger_ID) throws JSONException {
        String Url = Passenger_LeaveRideUrl + Passenger_ID;
        HandleXML obj = new HandleXML(Url);
        obj.fetchXML();
        while ((obj.parsingComplete && !obj.error)) ;
        return obj.getData();

    }


    public String Driver_Delete_Review(int ReviewId) throws JSONException {
        String Url = Driver_RemoveReview + "ReviewId=" + ReviewId;
        HandleXML obj = new HandleXML(Url);
        obj.fetchXML();
        while ((obj.parsingComplete && !obj.error)) ;
        return obj.getData();

    }

    public JSONArray Driver_GetReview(int Driver_ID, int Route_ID) throws JSONException {
        JSONArray json;
        String Url = Driver_GetReview + "DriverId=" + Driver_ID + "&RouteId=" + Route_ID;
        HandleXML obj = new HandleXML(Url);
        obj.fetchXML();
        while ((obj.parsingComplete && !obj.error)) ;
        json = new JSONArray(obj.getData());
        return json;

    }

    public JSONArray GetPassengers_ByRouteID(int Route_ID) throws JSONException {
        JSONArray json;
        String Url = GetPassengersByRouteIDUrl + Route_ID;
        HandleXML obj = new HandleXML(Url);
        obj.fetchXML();
        while ((obj.parsingComplete && !obj.error)) ;
        json = new JSONArray(obj.getData());
        return json;

    }


    public JSONArray GetAcceptedRequests_ByRouteID(int Route_ID) throws JSONException {
        JSONArray json;
        String Url = GetAcceptedRequestsURL + Route_ID;
        HandleXML obj = new HandleXML(Url);
        obj.fetchXML();
        while ((obj.parsingComplete && !obj.error)) ;
        json = new JSONArray(obj.getData());
        return json;

    }


    public String Driver_DeleteRoute(int Route_ID) throws JSONException {
        String json;
        String Url = Driver_DeleteRouteUrl + Route_ID;
        HandleXML obj = new HandleXML(Url);
        obj.fetchXML();
        while ((obj.parsingComplete && !obj.error)) ;
        json = obj.getData().toString();
        return json;

    }

    public JSONArray GetEmitares() throws JSONException {
        JSONArray json;
        HandleXML obj = new HandleXML(Emirates_By_ID);
        obj.fetchXML();
        while ((obj.parsingComplete && !obj.error)) ;
        json = new JSONArray(obj.getData().toString());
        return json;
    }

    public JSONArray GetRegionsByEmiratesID(int id) throws JSONException {
        JSONArray json = null;
        HandleXML obj = new HandleXML(Regions_By_Em_Id + id);
        obj.fetchXML();
        while ((obj.parsingComplete && !obj.error)) ;
        json = new JSONArray(obj.getData().toString());
        return json;
    }

    public JSONArray GetPrefLanguage() throws JSONException {
        JSONArray json = null;
        HandleXML obj = new HandleXML(getPrefLanguageUrl);
        obj.fetchXML();
        while ((obj.parsingComplete && !obj.error) && !obj.error) ;
        json = new JSONArray(obj.getData().toString());
        return json;
    }

    public JSONArray GetVehiclesForCreateCarPool(int id) throws JSONException {

        JSONArray json;
        HandleXML obj = new HandleXML(GetVehiclesUrl + id);
        obj.fetchXML();
        while (((obj.parsingComplete && !obj.error) && !obj.error)) ;
        json = new JSONArray(obj.getData().toString());
        return json;
    }

    public JSONArray GetAgeRanges() throws JSONException {
        JSONArray json = null;
        HandleXML obj = new HandleXML(GetAgeRanges);
        obj.fetchXML();
        while ((obj.parsingComplete && !obj.error)) ;
        json = new JSONArray(obj.getData().toString());
        return json;
    }

    public JSONArray GetNationalities() throws JSONException {
        JSONArray json = null;
        HandleXML obj = new HandleXML(getNationalitiesUrl);
        obj.fetchXML();
        while ((obj.parsingComplete && !obj.error) && !obj.error) ;
        json = new JSONArray(obj.getData().toString());
        return json;
    }

    public Bitmap GetImage(String photoUrl) {
        HandleXML obj = new HandleXML(getImage + photoUrl);
        obj.fetchXML();
        while (((obj.parsingComplete && !obj.error) && !obj.error)) ;
        Log.d("obj : ", obj.getData());
        byte[] decodedByte = Base64.decode(obj.getData(), Base64.DEFAULT);
        Bitmap decoded = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
        return decoded;
    }

    public Void SetImage(CircularImageView cm,String photoUrl) {
        HandleXML obj = new HandleXML(getImage + photoUrl);
        obj.fetchXML();
        while (((obj.parsingComplete && !obj.error) && !obj.error)) ;
        Log.d("obj : ", obj.getData());
        byte[] decodedByte = Base64.decode(obj.getData(), Base64.DEFAULT);
        Bitmap decoded = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
        cm.setImageBitmap(decoded);
        return null;
    }

    public static JSONObject jsonArrayToObject(String data) throws JSONException {
        JSONArray jarray = new JSONArray(data);
        JSONObject json = null;
        json = jarray.getJSONObject(0);
        //Log.d("Json : ", json.getString("LastName"));
        return json;
    }


    public JSONArray GetMapLookUp() throws JSONException {
        HandleXML obj = new HandleXML(GetMapLookUpUrl);
        obj.fetchXML();
        while ((obj.parsingComplete && !obj.error)) ;
        return new JSONArray(obj.getData());
    }


    public void GetPassengersByRouteIDJsonParse(JSONArray jArray, ListView listView, final Activity context) {


        final List<Ride_Details_Passengers_DataModel> searchArray = new ArrayList<>();
        Ride_Details_Passengers_Adapter adapter;
        adapter = new Ride_Details_Passengers_Adapter(context, searchArray);
        listView.setAdapter(adapter);


        try {
            JSONObject json = null;
            for (int i = 0; i < jArray.length(); i++) {
                try {
                    final Ride_Details_Passengers_DataModel item = new Ride_Details_Passengers_DataModel(Parcel.obtain());
                    json = jArray.getJSONObject(i);
                    Log.d("test account email", json.getString("AccountName"));
                    item.setAccountName(json.getString("AccountName"));
                    item.setAccountNationalityEn(json.getString("AccountNationalityEn"));
                    searchArray.add(item);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        } catch (NullPointerException e) {

        }
    }

    public void QuickSearchJsonParse(JSONArray jArray, ListView listView, final int Passenger_ID, final Activity context) {
        //  final QuickSearchDataModel[] quickSearchDataModelsArray = new QuickSearchDataModel[jArray.length()];
        String days = "";
        final List<QuickSearchDataModel> searchArray = new ArrayList<>();
        QuickSearchResultAdapter adapter;
        adapter = new QuickSearchResultAdapter(context, searchArray);
        listView.setAdapter(adapter);


        try {
            JSONObject json = null;
            for (int i = 0; i < jArray.length(); i++) {
                try {
                    final QuickSearchDataModel item = new QuickSearchDataModel(Parcel.obtain());
                    json = jArray.getJSONObject(i);
                    Log.d("test account email", json.getString("AccountName"));
                    item.setAccountName(json.getString("AccountName"));
                    item.setDriverId(json.getInt("DriverId"));
                    item.setAccountPhoto(json.getString("AccountPhoto"));
                    item.setDriverEnName(json.getString("DriverEnName"));
//                    item.setFrom_EmirateName_en(json.getString("From_EmirateName_en"));
//                    item.setFrom_RegionName_en(json.getString("From_RegionName_en"));
//                    item.setTo_EmirateName_en(json.getString("To_EmirateName_en"));
//                    item.setTo_RegionName_en(json.getString("To_RegionName_en"));
                    item.setAccountMobile(json.getString("AccountMobile"));
                    item.setSDG_Route_Start_FromTime(json.getString("SDG_Route_Start_FromTime"));
                    item.setNationality_en(json.getString("Nationality_en"));
                    item.setRating(json.getString("Rating"));

                    days = "";

                    if (json.getString("Saturday").equals("true")) {
                        days += "Sat , ";
                    }
                    if (json.getString("SDG_RouteDays_Sunday").equals("true")) {
                        days += "Sun , ";

                    }
                    if (json.getString("SDG_RouteDays_Monday").equals("true")) {
                        days += "Mon , ";

                    }
                    if (json.getString("SDG_RouteDays_Tuesday").equals("true")) {
                        days += "Tue , ";
                    }
                    if (json.getString("SDG_RouteDays_Wednesday").equals("true")) {
                        days += "Wed , ";
                    }
                    if (json.getString("SDG_RouteDays_Thursday").equals("true")) {
                        days += "Thu , ";

                    }
                    if (json.getString("SDG_RouteDays_Friday").equals("true")) {
                        days += "Fri ";
                    }

                    item.setSDG_RouteDays(days);
                    days = "";


                    searchArray.add(item);


                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent in = new Intent(context, Profile.class);
                            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            in.putExtra("DriverID", searchArray.get(position).getDriverId());
                            in.putExtra("PassengerID", Passenger_ID);
                            in.putExtra("RouteID", searchArray.get(position).getSDG_Route_ID());
                            Log.d("Array Id :", String.valueOf(searchArray.get(position).getDriverId()));
                            context.startActivity(in);
                            Log.d("Array id : ", searchArray.get(position).getAccountName());
                            Log.d("on click id : ", String.valueOf(searchArray.get(position).getDriverId()));


                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        } catch (NullPointerException e) {

        }
    }

    public void bestRouteStringRequestDetails(String url, final ListView lv, final int Passenger_ID, final Context context) {
        // Get a RequestQueue
        RequestQueue queue = VolleySingleton.getInstance(context.getApplicationContext()).getRequestQueue();
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        response = response.replaceAll("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
                        response = response.replaceAll("<string xmlns=\"http://Sharekni-MobAndroid-Data.org/\">", "");
                        response = response.replaceAll("</string>", "");
                        // Display the first 500 characters of the response string.
                        String data = response.substring(40);
                        try {
                            myJsonArray[0] = new JSONArray(data);
                            Log.d("First Array : ", myJsonArray[0].toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        bestRouteJsonParseMostDetails(myJsonArray[0], lv, Passenger_ID, context);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error : ", error.toString());
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
//      Add a request (in this example, called stringRequest) to your RequestQueue.
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    private void bestRouteJsonParseMostDetails(JSONArray jArray, ListView lv, final int Passenger_ID, final Context context) {
        String days = "";
        final BestRouteDataModelDetails[] driver = new BestRouteDataModelDetails[jArray.length()];
        final ArrayList<BestRouteDataModelDetails> ar = new ArrayList<>();
        SharedPreferences myPrefs = context.getSharedPreferences("myPrefs", 0);
        final String ID = myPrefs.getString("account_id", null);
        final String AccountType = myPrefs.getString("account_type", null);
        try {
            JSONObject json;
            for (int i = 0; i < jArray.length(); i++) {
                try {
                    final BestRouteDataModelDetails item = new BestRouteDataModelDetails(Parcel.obtain());
                    json = jArray.getJSONObject(i);
                    item.setFromEmId(json.getInt("FromEmirateId"));
                    item.setFromRegid(json.getInt("FromRegionId"));
                    item.setToEmId(json.getInt("ToEmirateId"));
                    item.setToRegId(json.getInt("ToRegionId"));
                    item.setDriverName(json.getString("DriverName"));
                    item.setNationality_en(json.getString("NationlityEnName"));
                    item.setSDG_Route_Start_FromTime(json.getString("StartTime"));
                    item.setDriverMobile(json.getString("StartTime"));
                    item.setDriverId(json.getInt("AccountId"));
                    item.setRouteId(json.getInt("RouteId"));
                    item.setPhotoURl(json.getString("DriverPhoto"));
                    days = "";

                    if (json.getString("Saturday").equals("true")) {
                        days += "Sat , ";
                    }
                    if (json.getString("Sunday").equals("true")) {
                        days += "Sun , ";

                    }
                    if (json.getString("Monday").equals("true")) {
                        days += "Mon , ";

                    }
                    if (json.getString("Tuesday").equals("true")) {
                        days += "Tue , ";
                    }
                    if (json.getString("Wendenday").equals("true")) {
                        days += "Wed , ";
                    }
                    if (json.getString("Thrursday").equals("true")) {
                        days += "Thu , ";

                    }
                    if (json.getString("Friday").equals("true")) {
                        days += "Fri ";
                    }
                    item.setSDG_RouteDays(days);
                    days = "";

                    driver[i] = item;
                    BestRouteDataModelAdapterDetails arrayAdapter = new BestRouteDataModelAdapterDetails(context, R.layout.quick_search_list_item_2, driver);
                    lv.setAdapter(arrayAdapter);
                    ar.add(i, driver[i]);

                    final JSONObject finalJson = json;
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                            if (ID != null) {
//                                assert AccountType != null;

                            Intent in = new Intent(context, Profile.class);
                            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            in.putExtra("DriverID", driver[i].getDriverId());
                            in.putExtra("PassengerID", Passenger_ID);
                            in.putExtra("RouteID", driver[i].getRouteId());
                            //  Log.d("Array Id :", String.valueOf(searchArray.get(position).getDriverId()));
                            context.startActivity(in);
                            //   Log.d("Array id : ", searchArray.get(position).getAccountName());
                            //  Log.d("on click id : ", String.valueOf(searchArray.get(position).getDriverId()));


//                                    if (AccountType.equals("D")) {
//
//                                        Intent in = new Intent(context, Route.class);
//                                        in.putExtra("RouteID", finalJson.getInt("RouteId"));
//                                        in.putExtra("PassengerID", ID);
//                                        in.putExtra("DriverID", finalJson.getInt("AccountId"));
//                                        Bundle b = new Bundle();
//                                        b.putParcelable("Data", item);
//                                        in.putExtras(b);
//                                        context.startActivity(in);
//                                  }

                            // else {
//
//                                        Intent in = new Intent(context, RideDetailsPassenger.class);
//                                        in.putExtra("RouteID", finalJson.getInt("RouteId"));
//                                        in.putExtra("PassengerID", ID);
//                                        in.putExtra("DriverID", finalJson.getInt("AccountId"));
//
//                                        Bundle b = new Bundle();
//                                        b.putParcelable("Data", item);
//                                        in.putExtras(b);
//                                        context.startActivity(in);
//                                    }


//
//                            }else {
//
//                                final Dialog dialog = new Dialog(context);
//                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                                dialog.setContentView(R.layout.please_log_in_dialog);
//                                Button btn = (Button) dialog.findViewById(R.id.noroute_id);
//                                TextView Text_3 = (TextView) dialog.findViewById(R.id.Text_3);
//                                Button No_Btn = (Button) dialog.findViewById(R.id.No_Btn);
//                                Text_3.setText("In order to proceed you have to login first");
//                                dialog.show();
//
//                                No_Btn.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        dialog.dismiss();
//                                    }
//                                });
//
//                                btn.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        dialog.dismiss();
//                                        Intent intent = new Intent(context, LoginApproved.class);
//                                        context.startActivity(intent);
//
//                                    }
//                                });
//
//
//
//                                //Toast.makeText(context, "Please Login First", Toast.LENGTH_SHORT).show();
//
//                            }
                        }

                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (NullPointerException e) {

        }
    }

    private void QuickSearchStringRequest(final String url, final ListView lv, final int Passenger_ID, final Activity context) {
        // Get a RequestQueue
        //RequestQueue queue = VolleySingleton.getInstance(context.getApplicationContext()).getRequestQueue();
        // Request a string response from the provided URL.


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        response = response.replaceAll("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
                        response = response.replaceAll("<string xmlns=\"http://Sharekni-MobAndroid-Data.org/\">", "");
                        response = response.replaceAll("</string>", "");
                        // Display the first 500 characters of the response string.
                        String data = response.substring(40);
                        Log.d("Search  Array Output : ", data);
                        Log.d("Url is : ", url);
                        try {
                            myJsonArray[0] = new JSONArray(data);
                            //Log.d("First Array : ", myJsonArray[0].toString());
                        } catch (StringIndexOutOfBoundsException e) {
                            Toast.makeText(context, "Out of bound", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "No Routes Available ", Toast.LENGTH_SHORT).show();

                            final Dialog dialog = new Dialog(context);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.noroutesdialog);
                            Button btn = (Button) dialog.findViewById(R.id.noroute_id);
                            dialog.show();

                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    context.finish();
                                }
                            });


                            Log.d("Error Json : ", e.toString());
                        }
                        JSONArray jArray = myJsonArray[0];
                        QuickSearchJsonParse(jArray, lv, Passenger_ID, context);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error Json 2  : ", error.toString());
            }
        });
        // Add the request to the RequestQueue.
        //queue.add(stringRequest);
//      Add a request (in this example, called stringRequest) to your RequestQueue.
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    private void GetPassengersByRouteIdStringRequest(final String url, final ListView lv, final Activity context) {
        // Get a RequestQueue
        //RequestQueue queue = VolleySingleton.getInstance(context.getApplicationContext()).getRequestQueue();
        // Request a string response from the provided URL.

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        response = response.replaceAll("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
                        response = response.replaceAll("<string xmlns=\"http://Sharekni-MobAndroid-Data.org/\">", "");
                        response = response.replaceAll("</string>", "");
                        // Display the first 500 characters of the response string.
                        String data = response.substring(40);
                        Log.d("Search  Array Output : ", data);
                        Log.d("Url is : ", url);
                        try {
                            myJsonArray[0] = new JSONArray(data);
                            //Log.d("First Array : ", myJsonArray[0].toString());
                        } catch (StringIndexOutOfBoundsException e) {
                            Toast.makeText(context, "Out of bound", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Error Json 1 ", Toast.LENGTH_SHORT).show();
                            Log.d("Error Json : ", e.toString());
                        }


                        JSONArray jArray = myJsonArray[0];
                        GetPassengersByRouteIDJsonParse(jArray, lv, context);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error Json 2  : ", error.toString());
            }
        });
        // Add the request to the RequestQueue.
        //queue.add(stringRequest);
//      Add a request (in this example, called stringRequest) to your RequestQueue.
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    private void DriverCreateCarPoolStringRequest(final String url, final Activity context) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        response = response.replaceAll("<?xml version=1.0 encoding=utf-8?>", "");
                        response = response.replaceAll("<string xmlns=\"http://Sharekni-MobAndroid-Data.org/\">", "");
                        response = response.replaceAll("</string>", "");
                        response = response.replaceAll("\"", "");
                        response = response.substring(36);
                        Log.d("TEst url", url);
                        Log.d("Search  Array Output : ", response);
                        switch (response) {
                            case "-1":
                                Toast.makeText(context, "Route Name Exists!!", Toast.LENGTH_SHORT).show();
                                break;
                            case "-2":
                                Toast.makeText(context, "You Have Reached Max Rides Allowable!!", Toast.LENGTH_SHORT).show();
                                Intent in = new Intent(context, HomePage.class);
                                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(in);
                                break;
                            default:
                                Toast.makeText(context, "Ride Created!!", Toast.LENGTH_SHORT).show();
                                Intent in2 = new Intent(context, DriverCreatedRides.class);
                                in2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(in2);
                                break;
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error Json 2  : ", error.toString());
            }
        });
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    private void ForgetPasswordFormStringRequest(String url, final Context context) {
        // Get a RequestQueue
        //RequestQueue queue = VolleySingleton.getInstance(context.getApplicationContext()).getRequestQueue();
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        response = response.replaceAll("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
                        response = response.replaceAll("<string xmlns=\"http://Sharekni-MobAndroid-Data.org/\">", "");
                        response = response.replaceAll("</string>", "");
                        // Display the first 500 characters of the response string.
                        try {
                            String data = response.substring(40);
                            Log.d("First Array Json : ", data);
                            JSONObject json;
                            json = new JSONObject(data);
                            Log.d("Json : ", json.toString());
                            // ForgetPassFormJsonParse(json, context);
                        } catch (StringIndexOutOfBoundsException e) {
                            Toast.makeText(context, "Wrong MObile Number Or Email", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Wrong Mobil Number Or Email", Toast.LENGTH_SHORT).show();
                            Log.d("Error Json : ", e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error Json : ", error.toString());
            }
        });
        // Add the request to the RequestQueue.
        //queue.add(stringRequest);
//      Add a request (in this example, called stringRequest) to your RequestQueue.
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    private void ChangePasswordFormStringRequest(String url, final Context context, final TextView txt_error) {
        // Get a RequestQueue
        //RequestQueue queue = VolleySingleton.getInstance(context.getApplicationContext()).getRequestQueue();
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        response = response.replaceAll("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
                        response = response.replaceAll("<string xmlns=\"http://Sharekni-MobAndroid-Data.org/\">", "");
                        response = response.replaceAll("</string>", "");
                        // Display the first 500 characters of the response string.
                        try {
                            String data = response.substring(40);
                            Log.d("First Array Json : ", data);
//                            if (data=="1"){
//                                Toast.makeText(context, "Password has been Changed", Toast.LENGTH_SHORT).show();
//                            }else if(data=="0"){
//                                Toast.makeText(context, "please try again", Toast.LENGTH_SHORT).show();
//                            }
                            JSONArray jsonArray = null;
                            jsonArray = new JSONArray(data);
                            Log.d("Json : ", jsonArray.toString());
                            //ForgetPassFormJsonParse(json, context);
                        } catch (StringIndexOutOfBoundsException e) {
                            Toast.makeText(context, "check ur password 2", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            txt_error.setText("Please check ur password");
                            // Toast.makeText(context, "check ur password 3", Toast.LENGTH_SHORT).show();
                            Log.d("Error Json : ", e.toString());
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error Json : ", error.toString());
                txt_error.setText("please insert your old password and the new one to chnage it.");
                // Toast.makeText(context, "Please type ur old pass and new pass to change password", Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        //queue.add(stringRequest);
//      Add a request (in this example, called stringRequest) to your RequestQueue.
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    private void EditProfileFormStringRequest(final String url, final Context context) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        response = response.replaceAll("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
                        response = response.replaceAll("<string xmlns=\"http://Sharekni-MobAndroid-Data.org/\">", "");
                        response = response.replaceAll("</string>", "");
                        Log.d("Edit Profile", url);
                        try {
                            String data = response.substring(40);
                            Log.d("First Array Json : ", data);

                            if (data.equals("\"1\"")) {
                                Toast.makeText(context, R.string.edit_msg, Toast.LENGTH_SHORT).show();
                                Intent in = new Intent(context, HomePage.class);
                                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(in);
                            } else if (data.equals("\"0\"")) {
                                Toast.makeText(context, "please try again", Toast.LENGTH_SHORT).show();
                            }
                        } catch (StringIndexOutOfBoundsException e) {
                            Toast.makeText(context, "Edit profile 2", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error : ", error.toString());
                Toast.makeText(context, "Check Your Internet Connection /n" + error.networkResponse.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        //queue.add(stringRequest);
//      Add a request (in this example, called stringRequest) to your RequestQueue.
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }
}
