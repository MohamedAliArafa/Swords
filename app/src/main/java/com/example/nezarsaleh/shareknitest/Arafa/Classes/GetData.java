package com.example.nezarsaleh.shareknitest.Arafa.Classes;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nezarsaleh.shareknitest.Arafa.Activities.Profile;
import com.example.nezarsaleh.shareknitest.Arafa.Activities.Route;
import com.example.nezarsaleh.shareknitest.Arafa.DataModel.BestDriverDataModel;
import com.example.nezarsaleh.shareknitest.Arafa.DataModel.BestRouteDataModel;
import com.example.nezarsaleh.shareknitest.Arafa.DataModel.BestRouteDataModelDetails;
import com.example.nezarsaleh.shareknitest.Arafa.DataModel.DriverRideDataModel;
import com.example.nezarsaleh.shareknitest.Arafa.DataModel.RouteDataModel;
import com.example.nezarsaleh.shareknitest.Arafa.DataModelAdapter.BestDriverDataModelAdapter;
import com.example.nezarsaleh.shareknitest.Arafa.DataModelAdapter.BestRouteDataModelAdapter;
import com.example.nezarsaleh.shareknitest.Arafa.DataModelAdapter.BestRouteDataModelAdapterDetails;
import com.example.nezarsaleh.shareknitest.Arafa.DataModelAdapter.DriverRideDataModelAdapter;
import com.example.nezarsaleh.shareknitest.DriverCreatedRides;
import com.example.nezarsaleh.shareknitest.HomePage;
import com.example.nezarsaleh.shareknitest.MostRidesDetails;
import com.example.nezarsaleh.shareknitest.QuickSearchDataModel;
import com.example.nezarsaleh.shareknitest.QuickSearchResultAdapter;
import com.example.nezarsaleh.shareknitest.R;
import com.example.nezarsaleh.shareknitest.RideDetailsPassenger;
import com.example.nezarsaleh.shareknitest.Ride_Details_Passengers_Adapter;
import com.example.nezarsaleh.shareknitest.Ride_Details_Passengers_DataModel;
import com.example.nezarsaleh.shareknitest.SearchOptions;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class GetData {

    final JSONArray[] myJsonArray = new JSONArray[1];

    public static final String DOMAIN = "http://sharekni.sdgstaff.com";

    String data;
    String loginFormUrl                 = DOMAIN + "/_mobfiles/CLS_MobAccount.asmx/CheckLogin?";
    String getDriverById                = DOMAIN + "/_mobfiles/CLS_MobAccount.asmx/Get?id=";
    String ChangePasswordUrl            = DOMAIN + "/_mobfiles/CLS_MobAccount.asmx/ChangePassword?";
    String Driver_GetReview             = DOMAIN + "/_mobfiles/CLS_MobAccount.asmx/Driver_GetReviewList?";
    String EditProfileUrl               = DOMAIN + "/_mobfiles/CLS_MobAccount.asmx/EditProfile?";
    String ForgetPasswordUrl            = DOMAIN + "/_mobfiles/CLS_MobAccount.asmx/ForgetPassword?";
    String getImage                     = DOMAIN + "/_mobfiles/CLS_MobAccount.asmx/GetPhotoPath?s_FileName=";

    String getBestDriverUrl             = DOMAIN + "/_mobfiles/CLS_MobDriver.asmx/GetBestDrivers";
    String DriverEditCarPoolUrl         = DOMAIN + "/_mobfiles/CLS_MobDriver.asmx/Driver_EditCarpool?";
    String DriverCreateCarPoolUrl       = DOMAIN + "/_mobfiles/CLS_MobDriver.asmx/Driver_CreateCarpool?";
    String QuickSearchUrl               = DOMAIN + "/_mobfiles/CLS_MobDriver.asmx/Passenger_FindRide?";

    String getBestRouteUrl              = DOMAIN + "/_mobfiles/CLS_MobRoute.asmx/GetMostDesiredRides";
    String getDriverRideUrl             = DOMAIN + "/_mobfiles/CLS_MobRoute.asmx/GetDriverDetailsByAccountId?AccountId=";
    String getRouteByRouteId            = DOMAIN + "/_mobfiles/CLS_MobRoute.asmx/GetRouteByRouteId?RouteId=";
    String Passenger_SendAlert          = DOMAIN + "/_mobfiles/CLS_MobRoute.asmx/Passenger_SendAlert?";
    String GetPassengersByRouteIDUrl    = DOMAIN + "/_mobfiles/CLS_MobRoute.asmx/GetPassengersByRouteId?id=";
    String Passenger_Review_Driver      = DOMAIN + "/_mobfiles/CLS_MobRoute.asmx/Passenger_ReviewDriver?";
    String DriverAlertsForRequestUrl    = DOMAIN + "/_mobfiles/CLS_MobRoute.asmx/Driver_AlertsForRequest?d_AccountId=";
    String DriverAcceptPassengerUrl     = DOMAIN + "/_mobfiles/CLS_MobRoute.asmx/Driver_AcceptRequest?RequestId=";
    String Driver_DeleteRouteUrl        = DOMAIN + "/_mobfiles/CLS_MobRoute.asmx/Route_Delete?RouteId=";
    String Passenger_Rqs_From_Driver    = DOMAIN + "/_mobfiles/CLS_MobRoute.asmx/Passenger_GetAcceptedRequestsFromDriver?accountId=";

    String Regions_By_Em_Id             = DOMAIN + "/_mobfiles/CLS_MobMasterData.asmx/GetRegionsByEmirateId?id=";
    String Emirates_By_ID               = DOMAIN + "/_mobfiles/CLS_MobMasterData.asmx/GetEmirates";
    String getNationalitiesUrl          = DOMAIN + "/_mobfiles/CLS_MobMasterData.asmx/GetNationalities?id=0";
    String getPrefLanguageUrl           = DOMAIN + "/_mobfiles/CLS_MobMasterData.asmx/GetPrefferedLanguages";
    String GetAgeRanges                 = DOMAIN + "/_mobfiles/CLS_MobMasterData.asmx/GetAgeRanges";

    String GetVehiclesUrl               = DOMAIN + "/_mobfiles/CLS_MobVehicle.asmx/GetByDriverId?id=20027";

    String GetMapLookUpUrl              = DOMAIN + "/_mobfiles/CLS_MobRoute.asmx/GetFromOnlyMostDesiredRides";


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
        while (obj.parsingComplete) ;
        return new JSONArray(obj.getData());
    }

    public JSONArray GetBestDrivers() throws JSONException {
        HandleXML obj = new HandleXML(getBestDriverUrl);
        obj.fetchXML();
        while (obj.parsingComplete) ;
        return new JSONArray(obj.getData());
    }

    public JSONArray GetDriverRides(int ID) throws JSONException {
        HandleXML obj = new HandleXML(getDriverRideUrl + ID);
        obj.fetchXML();
        while (obj.parsingComplete) ;
        return new JSONArray(obj.getData());
    }

    public int GetDesiredCount(int FromEmId, int FromRegId, int ToEmId, int ToRegId) throws JSONException {
        String url = DOMAIN +  "/_mobfiles/CLS_MobRoute.asmx/GetMostDesiredRideDetails?AccountID=" + 0 + "&FromEmirateID=" + FromEmId + "&FromRegionID=" + FromRegId + "&ToEmirateID=" + ToEmId + "&ToRegionID=" + ToRegId;
        HandleXML obj = new HandleXML(url);
        obj.fetchXML();
        while (obj.parsingComplete) ;
        JSONArray json = new JSONArray(obj.getData());
        return json.length();
    }

    public JSONArray GetDriverAlertsForRequest(int id) throws JSONException {

        HandleXML obj = new HandleXML(DriverAlertsForRequestUrl + id);
        obj.fetchXML();
        while (obj.parsingComplete) ;
        JSONArray json = new JSONArray(obj.getData());
        return json;
    }

    public JSONArray Get_Passenger_GetAcceptedRequestsFromDriver(int id) throws JSONException {

        HandleXML obj = new HandleXML(Passenger_Rqs_From_Driver + id);
        obj.fetchXML();
        while (obj.parsingComplete) ;
        JSONArray json = new JSONArray(obj.getData());
        return json;
    }





    public String DriverAcceptPassenger(int Request_ID, int isAccepted) throws JSONException {
        HandleXML obj = new HandleXML(DriverAcceptPassengerUrl + Request_ID + "&IsAccept=" + isAccepted);
        obj.fetchXML();
        while (obj.parsingComplete) ;
        return obj.getData();
    }

    public JSONObject GetDriverById(int ID) throws JSONException {
        JSONObject json;
        HandleXML obj = new HandleXML(getDriverById + ID);
        obj.fetchXML();
        while (obj.parsingComplete) ;
        json = jsonArrayToObject(obj.getData().toString());
        return json;
    }

    public JSONObject GetRouteById(int ID) throws JSONException {
        JSONObject json;
        HandleXML obj = new HandleXML(getRouteByRouteId + ID);
        obj.fetchXML();
        while (obj.parsingComplete) ;
        JSONArray jsonArray = new JSONArray(obj.getData());
        json = jsonArray.getJSONObject(0);
        return json;
    }

    public JSONArray MostDesiredRoutes() throws JSONException {
        JSONArray json;
        HandleXML obj = new HandleXML(getBestRouteUrl);
        obj.fetchXML();
        while (obj.parsingComplete) ;
        json = new JSONArray(obj.getData().toString());
        return json;
    }

    public String Passenger_SendAlert(int Driver_ID, int Passenger_ID, int Route_ID, String Remarks) throws JSONException {
        String json;
        String Url = Passenger_SendAlert + "RouteID=" + Route_ID + "&PassengerID="
                + Passenger_ID + "&DriverID=" + Driver_ID + "&s_Remarks=" + Remarks;
        HandleXML obj = new HandleXML(Url);
        obj.fetchXML();
        while (obj.parsingComplete) ;
        json = obj.getData().toString();
        return json;

    }


    public String Passenger_Review_Driver(int Driver_ID, int Passenger_ID, int Route_ID, String Remarks) throws JSONException {

        String Url = Passenger_Review_Driver + "PassengerId=" + Passenger_ID + "&DriverId="
                + Driver_ID + "&RouteId=" + Route_ID + "&&ReviewText=" + Remarks;
        HandleXML obj = new HandleXML(Url);
        obj.fetchXML();
        while (obj.parsingComplete) ;
        return obj.getData();

    }

    public JSONArray Driver_GetReview(int Driver_ID, int Route_ID) throws JSONException {
        JSONArray json;
        String Url = Driver_GetReview + "DriverId=" + Driver_ID + "&RouteId=" + Route_ID;
        HandleXML obj = new HandleXML(Url);
        obj.fetchXML();
        while (obj.parsingComplete) ;
        json = new JSONArray(obj.getData());
        return json;

    }

    public JSONArray GetPassengers_ByRouteID(   int Route_ID) throws JSONException {
        JSONArray json;
        String Url = GetPassengersByRouteIDUrl + Route_ID;
        HandleXML obj = new HandleXML(Url);
        obj.fetchXML();
        while (obj.parsingComplete) ;
        json = new JSONArray(obj.getData());
        return json;

    }







    public String Driver_DeleteRoute(int Route_ID) throws JSONException {
        String json;
        String Url = Driver_DeleteRouteUrl + Route_ID;
        HandleXML obj = new HandleXML(Url);
        obj.fetchXML();
        while (obj.parsingComplete) ;
        json = obj.getData().toString();
        return json;

    }

    public JSONArray GetEmitares() throws JSONException {
        JSONArray json;
        HandleXML obj = new HandleXML(Emirates_By_ID);
        obj.fetchXML();
        while (obj.parsingComplete) ;
        json = new JSONArray(obj.getData().toString());
        return json;
    }

    public JSONArray GetRegionsByEmiratesID(int id) throws JSONException {
        JSONArray json = null;
        HandleXML obj = new HandleXML(Regions_By_Em_Id + id);
        obj.fetchXML();
        while (obj.parsingComplete) ;
        json = new JSONArray(obj.getData().toString());
        return json;
    }

    public JSONArray GetPrefLanguage() throws JSONException {
        JSONArray json = null;
        HandleXML obj = new HandleXML(getPrefLanguageUrl);
        obj.fetchXML();
        while (obj.parsingComplete) ;
        json = new JSONArray(obj.getData().toString());
        return json;
    }

    public JSONArray GetVehiclesForCreateCarPool() throws JSONException {
        JSONArray json;
        HandleXML obj = new HandleXML(GetVehiclesUrl);
        obj.fetchXML();
        while (obj.parsingComplete) ;
        json = new JSONArray(obj.getData().toString());
        return json;
    }

    public JSONArray GetAgeRanges() throws JSONException {
        JSONArray json = null;
        HandleXML obj = new HandleXML(GetAgeRanges);
        obj.fetchXML();
        while (obj.parsingComplete) ;
        json = new JSONArray(obj.getData().toString());
        return json;
    }

    public JSONArray GetNationalities() throws JSONException {
        JSONArray json = null;
        HandleXML obj = new HandleXML(getNationalitiesUrl);
        obj.fetchXML();
        while (obj.parsingComplete) ;
        json = new JSONArray(obj.getData().toString());
        return json;
    }

    public Bitmap GetImage(String photoUrl) {
        HandleXML obj = new HandleXML(getImage + photoUrl);
        obj.fetchXML();
        while (obj.parsingComplete) ;
        Log.d("obj : ", obj.getData());
        byte[] decodedByte = Base64.decode(obj.getData(), Base64.DEFAULT);
        Bitmap decoded = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
        return decoded;
    }

    public static JSONObject jsonArrayToObject(String data) throws JSONException {
        JSONArray jarray = new JSONArray(data);
        JSONObject json = null;
        json = jarray.getJSONObject(0);
        //Log.d("Json : ", json.getString("LastName"));
        return json;
    }


    public JSONArray GetMapLookUp() throws JSONException {
        HandleXML obj = new HandleXML(GetMapLookUpUrl );
        obj.fetchXML();
        while (obj.parsingComplete) ;
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
                            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
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

    public void bestRouteStringRequestDetails(String url, final ListView lv, final Context context) {
        // Get a RequestQueue
        RequestQueue queue = VolleySingleton.getInstance(context.getApplicationContext()).getRequestQueue();
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        response = response.replaceAll("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
                        response = response.replaceAll("<string xmlns=\"http://tempuri.org/\">", "");
                        response = response.replaceAll("</string>", "");
                        // Display the first 500 characters of the response string.
                        String data = response.substring(40);
                        try {
                            myJsonArray[0] = new JSONArray(data);
                            Log.d("First Array : ", myJsonArray[0].toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        bestRouteJsonParseMostDetails(myJsonArray[0], lv, context);
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

    private void bestRouteJsonParseMostDetails(JSONArray jArray, ListView lv, final Context context) {
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
                            if (ID != null) {
                                assert AccountType != null;
                                try {

                                    if (AccountType.equals("D")) {

                                        Intent in = new Intent(context, Route.class);
                                        in.putExtra("RouteID", finalJson.getInt("RouteId"));
                                        in.putExtra("PassengerID", ID);
                                        in.putExtra("DriverID", finalJson.getInt("AccountId"));
                                        Bundle b = new Bundle();
                                        b.putParcelable("Data", item);
                                        in.putExtras(b);
                                        context.startActivity(in);
                                    } else {

                                        Intent in = new Intent(context, RideDetailsPassenger.class);
                                        in.putExtra("RouteID", finalJson.getInt("RouteId"));
                                        in.putExtra("PassengerID", ID);
                                        in.putExtra("DriverID", finalJson.getInt("AccountId"));

                                        Bundle b = new Bundle();
                                        b.putParcelable("Data", item);
                                        in.putExtras(b);
                                        context.startActivity(in);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }else {
                                Toast.makeText(context, "Please Login First", Toast.LENGTH_SHORT).show();
                            }
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
                        response = response.replaceAll("<string xmlns=\"http://tempuri.org/\">", "");
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
                        response = response.replaceAll("<string xmlns=\"http://tempuri.org/\">", "");
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
                        response = response.replaceAll("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
                        response = response.replaceAll("<string xmlns=\"http://tempuri.org/\">", "");
                        response = response.replaceAll("</string>", "");
                        Log.d("TEst url", url);
                        Log.d("Search  Array Output : ", response);
                        Toast.makeText(context, "Ride Updated!!", Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(context, DriverCreatedRides.class);
                        context.startActivity(in);
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
                        response = response.replaceAll("<string xmlns=\"http://tempuri.org/\">", "");
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
                        response = response.replaceAll("<string xmlns=\"http://tempuri.org/\">", "");
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

    private void EditProfileFormStringRequest(String url, final Context context) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        response = response.replaceAll("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
                        response = response.replaceAll("<string xmlns=\"http://MobAccount.org/\">", "");
                        response = response.replaceAll("</string>", "");
                        try {
                            String data = response.substring(40);
                            Log.d("First Array Json : ", data);

                            if (data.equals("\"1\"")){
                                Toast.makeText(context, "Account has been Changed", Toast.LENGTH_SHORT).show();
                                Intent in = new Intent(context,HomePage.class);
                                context.startActivity(in);
                            }else if(data.equals("\"0\"")){
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
