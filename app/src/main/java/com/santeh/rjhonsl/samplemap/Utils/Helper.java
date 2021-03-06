package com.santeh.rjhonsl.samplemap.Utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;
import com.santeh.rjhonsl.samplemap.Obj.CustInfoObject;
import com.santeh.rjhonsl.samplemap.Obj.Var;
import com.santeh.rjhonsl.samplemap.R;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Weeks;

import java.net.NetworkInterface;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by rjhonsl on 7/24/2015.
 */
public class Helper {

    public static class userActions{
        public static class TSR{

            public static String ADD_FARM   ="Add Farm Information";
            public static String DELETE_FARM="Delete Farm Information";
            public static String Edit_FARM  ="Edit Farm Information";

            public static String ADD_POND   ="Add Pond Information";
            public static String DELETE_POND="Delete Pond Information";
            public static String Edit_POND  ="Modify Pond Information";
            public static String ADD_MAIN_CUSTOMERINFO ="Add Customer Address Information";
            public static String DELETE_MAIN_CUSTOMERINFO ="DELETE Customer Address Information";
            public static String EDIT_MAIN_CUSTOMERINFO ="EDIT Customer Address Information";
            public static String ADD_WEEKLYREPORT ="Add Weekly Report";

            public static String USER_LOGIN = "Login";


        }
    }


    public static class variables{
        public static String sourceAddress_bizNF                    = "http://santeh.co.nf/phpsql/";
        public static String sourceAddress_goDaddy                  = "http://santeh-webservice.com/php/android_json_post/";
        public static String sourceAddress_goDaddy_downloadable     = "http://santeh.co.nf/downloadable/";
        public static String sourceAddress_goDaddy_FishBookUpload   = "http://santeh.co.nf/Images/androidimageupload_fishbook/";
        public static String sourceAddress_downloadable_downloadable       = "http://santeh-webservice.com/downloadables/";
        public static String sourceAddress_000webhost               = "http://mysanteh.site50.net/santehweb/";




        public static final String ACTIVITY_LOG_TYPE_FARM_REPORTING= "1";
        public static final String ACTIVITY_LOG_TYPE_TSR_MONITORING= "2";
        public static final String ACTIVITY_LOG_TYPE_ADMIN_ACTIVITY= "3";

        public static String URL_SELECT_ALL_CUSTINFO                        = sourceAddress_goDaddy + "selectAllCustomerInfo.php";
        public static String URL_SELECT_CURRENT_VERSION_NUMBER              = sourceAddress_goDaddy + "selectCurrentVersion.php";
        public static String URL_SELECT_ALL_CUSTINFO_LEFTJOIN_PONDINFO      = sourceAddress_goDaddy + "selectCustinfoLeftJoinPondinf.php";
        public static String URL_SELECT_CUSTINFO_BY_ID                      = sourceAddress_goDaddy + "selectByID.php";
        public static String URL_SELECT_PONDINFO_BY_CUSTOMER_ID             = sourceAddress_goDaddy + "selectPondByCustomerID.php";
        public static String URL_SELECT_PONDINFO_BY_CUSTOMERID_AND_PONDID   = sourceAddress_goDaddy + "selectPondByCustomerIDandPondID.php";
        public static String URL_SELECT_ALL_PONDINFO_GROUPBY_CUSTINFO       = sourceAddress_goDaddy + "selectAllPondGroupBy.php";
        public static String URL_SELECT_ALL_PONDINFO                        = sourceAddress_goDaddy + "selectAllPond.php";
        public static String URL_SELECT_ALL_USERS_IN_AREA                   = sourceAddress_goDaddy + "selectAlluserInAnArea.php";
        public static String URL_SELECT_ALL_USERS                           = sourceAddress_goDaddy + "selectAllUsers.php";
        public static String URL_SELECT_ALL_AREA                            = sourceAddress_goDaddy + "selectAllArea.php";
        public static String URL_SELECT_USERS_ACTIVITY_BY_DATE_AND_ID       = sourceAddress_goDaddy + "selectUserActivityByID.php";
        public static String URL_SELECT_POND_WEEKLY_UPDATES_BY_ID           = sourceAddress_goDaddy + "selectPondWeeklyUpdateByPondID.php";
        public static String URL_SELECT_ALL_USERS_ACTIVITY_BY_ID            = sourceAddress_goDaddy + "selectAllUserActivityByID.php";
        public static String URL_SELECT_ALL_PONDINFO_BETWEEN_DATES          = sourceAddress_goDaddy + "selectAllPondBetweenDate.php";
        public static String URL_SELECT_ALL_PONDINFO_LEFTJOIN_CUSTINFO      = sourceAddress_goDaddy + "selectAllPondInfoLeftJoinCustInfo.php";
        public static String URL_SELECT_FARM_BY_FARMID                      = sourceAddress_goDaddy + "selectFarmByFarmID.php";
        public static String URL_SELECT_CUSTOMERINFO_BY_ID                  = sourceAddress_goDaddy + "selectCustomerDetailsByID.php";
        public static String URL_SELECT_FARMINFO_LF_POND_AND_MCI_BY_FARMID  = sourceAddress_goDaddy + "selectCustomerInfoLeftJoinPondInfoLeftJoinMciByFarmID.php";
        public static String URL_SELECT_CUST_LOCATION_BY_ASSIGNED_AREA      = sourceAddress_goDaddy + "selectCustomerLocationByAssingedUser.php";
        public static String URL_SELECT_ALL_POND_WEEKLYUPDATES_INNERJOIN_PONDINFO_GROUPBY_FARMNAME
                = sourceAddress_bizNF + "selectPondWeeklyUpdate_InnerJoin_Pond_GroupByFarmName.php";

        public static String URL_DELETE_CUSTINFO_BY_ID                  = sourceAddress_goDaddy + "deleteByID.php";
        public static String URL_DELETE_POND_BY_ID                      = sourceAddress_goDaddy + "deletePondInfoByID.php";
        public static String URL_DELETE_POND_WEEKLY_DETAILS_BY_ID       = sourceAddress_goDaddy + "deletePondWeeklyDetailsByID.php";


        public static String URL_INSERT_PONDINFO                        = sourceAddress_goDaddy + "insertPondInformation.php";
        public static String URL_INSERT_LOGINLOCATION                   = sourceAddress_goDaddy + "insertLoginLocationOffUser.php";
        public static String URL_INSERT_USER_ACTIVITY                   = sourceAddress_goDaddy + "insertUserActivity.php";
        public static String URL_INSERT_POND_REPORT                     = sourceAddress_goDaddy + "insertPondReport.php";
        public static String URL_INSERT_MAIN_CUSTOMERINFO               = sourceAddress_goDaddy + "insertMainCustomerInfo.php";
        public static String URL_INSERT_FARM_INFO                       = sourceAddress_goDaddy + "insertFarmInformation.php";
        public static String URL_PHP_RAW_QUERY_POST_SELECT              = sourceAddress_goDaddy + "insertSyncFarmInfo.php";
        public static String URL_PHP_INSERT_FEEDPOST                    = sourceAddress_goDaddy + "insertFeedContent.php";
        public static String URL_PHP_INSERT_FEEDPOST_PHOTO              = sourceAddress_goDaddy + "uploadimage.php";
        public static String URL_PHP_RAW_QUERY_POST_INSERT              = sourceAddress_goDaddy + "selectquery.php";
        public static String URL_PHP_RAW_QUERY_POST_SELECT_CUSTOMER     = sourceAddress_goDaddy + "selectQuery_customer.php";
        public static String URL_SELECT_POND_BY_ADDEDBY_USER            = sourceAddress_goDaddy + "aqua_select_ponds_by_addedbyUser.php";
        public static String URL_SELECT_WEEKLYUPDATES_BY_USER           = sourceAddress_goDaddy + "aqua_select_weekly_updates_by_useradded.php";
        public static String URL_SELECT_Harvestinfo_BY_USER             = sourceAddress_goDaddy + "aqua_select_harvestinfo_by_userid.php";

        public static String URL_UPDATE_PONDINFO_BY_ID                  = sourceAddress_goDaddy + "updatePondInformationByID.php";
        public static String URL_UPDATE_CUSTOMERINFORMATION_BY_ID       = sourceAddress_goDaddy + "updateCustomerInformationByID.php";
        public static String URL_UPDATE_POND_WEEKLY_DETAIL_BY_ID        = sourceAddress_goDaddy + "updatePodWeeklyDetails.php";

        public static String URL_LOGIN                                  = sourceAddress_goDaddy + "login.php";



        public static String[] ARRAY_SPECIES = {
                "Bangus",   //0
//                "Catfish",  //1
//                "Crab",     //2
//                "Grouper",  //3
//                "Pangasius",//5
//                "Pompano",  //6
//                "Prawns",   //7
                "Rabbit Fish",//8
//                "Seabass",  //9
//                "Snapper",  //10
                "Tilapia",  //12
                "Vannamei"   //11

        };


        public static String[] ARRAY_CULTURE_LEVEL = {
                "Extensive",
                "Intensive",
                "Semi-Intesive",
                "Mono-Culture",
                "Poly-Culture"
        };


        public static String[] ARRAY_CULTURE_SYSTEM = {
                "Cage",
                "Pen",
                "Pond"
        };


        public static double[] ARRAY_TILAPIA_FEEDING_RATE_PER_WEEK ={
                0,       //week 1
                0.1,     //week 2
                0.09,    //week 3
                0.09,    //week 4
                0.07,    //week 5
                0.06,    //week 6
                0.053,   //week 7
                0.048,   //week 8
                0.040,   //week 9
                0.038,   //week 10
                0.035,   //week 11
                0.032,   //week 12
                0.030,   //week 13
                0.030,   //weel 14
                0.028,   //week 15
                0.028,   //week 16
                0.025,   //week 17
                0.025,   //week 18
        };

        public static double[] ARRAY_BANGUS_FEEDING_RATE_PER_WEEK ={
                0.30,   //1
                0.2332, //2
                0.1668, //3
                0.10,   //4
                0.096,  //5
                0.092,  //6
                0.088,  //7
                0.084,  //8
                0.08,   //9
                0.075,  //10
                0.07,   //11
                0.065,  //12
                0.06,   //13
                0.055,  //14
                0.05,   //15
                0.045,  //16
                0.04,   //17
                0.036,  //18
                0.032,  //19
                0.024,  //21
                0.028,  //20
                0.02    //22
        };


        public static double[] ARRAY_TILAPIA_ABW_WEEKLY ={
                0,       //week 1
                7.5,     //week 2
                10.0,    //week 3
                12.5,    //week 4
                20.0,    //week 5
                25.0,    //week 6
                32.5,    //week 7
                42.5,    //week 8
                60.00,   //week 9
                67.50,   //week 10
                82.50,   //week 11
                95.00,   //week 12
                102.5,   //week 13
                135.0,   //weel 14
                155.0,   //week 15
                175.0,   //week 16
                197.5,   //week 17
                200.0,   //week 18
        };

        public static double[] ARRAY_BANGUS_ABW_WEEKLY={
                0,      //week 1
                3.33,   //week 2
                6.67,   //week 3
                10,     //week 4
                13,     //week 5
                16,     //week 6
                19,     //week 7
                22,     //week 8
                25,     //week 9
                56.25,  //week 10
                87.5,   //week 11
                118.75, //week 12
                150,    //week 13
                182.5,  //week 14
                215,    //week 15
                247.5,  //week 16
                280,    //week 17
                324,    //week 18
                368,    //week 19
                412,    //week 20
                456,    //week 21
                500     //week 22
        };


        public static double[] ARRAY_VANNAMEI_EXTENSIVE_ABW_WEEKLY={
                2.5,    //week 4
                3.67,   //week 5
                4.83,   //week 6
                6,      //week 7
                8,      //week 8
                10,     //week 9
                12,     //week 10
                14,     //week 11
                16.5,   //week 12
                19,     //week 13
                20.5,   //week 14
                22,     //week 15
                23.5,   //week 16
                25      //week 17
        };


        public static class tables{
            public static class PONDINFO{
                public static String id = "id";
                public static String pondID = "pondid";
                public static String specie = "specie";
                public static String dateOfStocking = "dateStocked";
                public static String quantity = "quantity";
                public static String areaOfPond = "area";
                public static String cultureSysten = "culturesystem";
                public static String remarks = "culturesystem";
                public static String customerID = "customerID";
            }
        }



        public static void setGlobalVar_currentUserID(int ID, Activity activity){
            ((Var) activity.getApplication()).setCurrentuser(ID);
        }

        public static void setGlobalVar_currentlevel(int lvl, Activity activity){
            ((Var) activity.getApplication()).setCurrentuserLvl(lvl);
        }

        public static int getGlobalVar_currentUserID( Activity activity ){
            return  ((Var) activity.getApplication()).getCurrentuser();
        }

        public static int getGlobalVar_currentLevel(Activity activity){
            return  ((Var) activity.getApplication()).getCurrentuserLvl();
        }


        public static String getGlobalVar_currentUserName(Activity activity){
            return  ((Var) activity.getApplication()).getCurrentUserName();
        }

        public static void setGlobalVar_currentUsername(String username, Activity activity ){
            ((Var) activity.getApplication()).setCurrentUserName(username);
        }


        public static String getGlobalVar_currentUserFirstname( Activity activity ){
            return  ((Var) activity.getApplication()).getCurrentUserFirstname();
        }


        public static String getGlobalVar_DateAdded( Activity activity ){
            return  ((Var) activity.getApplication()).getDateAddedToDB();
        }

        public static void setGlobalVar_DateAddedToDb(String date, Activity activity ){
            ((Var) activity.getApplication()).setDateAddedToDB(date);
        }


        public static void setGlobalVar_currentFirstname(String firstname, Activity activity ){
            ((Var) activity.getApplication()).setCurrentUserFirstname(firstname);
        }


        public static String getGlobalVar_currentUserLastname( Activity activity ){
            return  ((Var) activity.getApplication()).getCurrentUserLastname();
        }

        public static void setGlobalVar_currentLastname(String lastname, Activity activity ){
            ((Var) activity.getApplication()).setCurrentUserLastname(lastname);
        }


        public static String getGlobalVar_currentUserPassword(Activity activity){
            return  ((Var) activity.getApplication()).getCurrentPassword();
        }

        public static void setGlobalVar_currentUserpassword(String password, Activity activity ){
            ((Var) activity.getApplication()).setCurrentPassword(password);
        }


        public static String getGlobalVar_currentAssignedArea( Activity activity ){
            return  ((Var) activity.getApplication()).getAssignedArea();
        }

        public static void setGlobalVar_currentAssignedArea(String area, Activity activity ){
            ((Var) activity.getApplication()).setAssignedArea(area);
        }

        public static int getGlobalVar_currentisActive( Activity activity ){
            return  ((Var) activity.getApplication()).getIsactive();
        }

        public static void setGlobalVar_currentIsActive(int isactive, Activity activity ){
            ((Var) activity.getApplication()).setIsactive(isactive);
        }

        public static String getGlobalVar_currentDeviceID(Activity activity){
            return  ((Var) activity.getApplication()).getDeviceID();
        }

        public static void setGlobalVar_deviceID(String deviceID, Activity activity ){
            ((Var) activity.getApplication()).setDeviceID(deviceID);
        }


        public static CustInfoObject getGlobalVar_CustInfoInterface(Activity activity){
            return  ((Var) activity.getApplication()).getCustInfoObject();
        }

        public static void setGlobalVar_CustInfoInterface(Activity activity, CustInfoObject obj){
            ((Var) activity.getApplication()).setCustInfoObject(obj);
        }



    }


    public static class map {

        public static float getDifference(LatLng center, LatLng touchLocation ){

            float[] results = new float[1];
            Location.distanceBetween(center.latitude, center.longitude,
                    touchLocation.latitude, touchLocation.longitude, results);
            return results[0];
        }

        public static Marker map_addMarker(GoogleMap map, LatLng latlng, int iconResID,
                                            final String farmname, final String address, String id, String totalstock, String specie){


            Marker marker = map.addMarker(new MarkerOptions()
                            .title(id + "#-#" + farmname + "#-#" + totalstock + "#-#" + specie)
                            .icon(BitmapDescriptorFactory.fromResource(iconResID))
                            .snippet(address)
                            .position(latlng)
                            .draggable(false)
            );
            return marker;
        }

        public static Marker map_addMarkerIconGen(GoogleMap map, LatLng latlng, Bitmap iconResID, final String activity, String datetime){
            Marker marker = map.addMarker(new MarkerOptions()
                    .title(activity)
                    .icon(BitmapDescriptorFactory.fromBitmap(iconResID))
                    .snippet(datetime)
                    .position(latlng)
                    .draggable(false)
            );
            return marker;
        }

        public static void isLocationAvailablePrompt(final Context context, final Activity activity){
            LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
            boolean gps_enabled = false;
            boolean network_enabled = false;
            try {
                gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch(Exception ex) {}
            try {
                network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            } catch(Exception ex) {}

            if(!gps_enabled) {
                final Dialog d = dialog.themedOkOnly(activity, "GPS Service", "Location services is needed to use this application. Please turn on Location in settings and set it to High Accuracy", "OK");
                Button b1 = (Button) d.findViewById(R.id.btn_dialog_okonly_OK);

                d.setCancelable(false);
                d.show();

                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.hide();
                        Intent gpsOptionsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        context.startActivity(gpsOptionsIntent);
                    }
                });

            }
        }

        public static boolean isLocationEnabled(Context context) {
            int locationMode = 0;
            String locationProviders;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                try {
                    locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

                } catch (Settings.SettingNotFoundException e) {
                    e.printStackTrace();
                }

                return locationMode != Settings.Secure.LOCATION_MODE_OFF;

            }else{
                locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                return !TextUtils.isEmpty(locationProviders);
            }
        }

        public static void moveCameraAnimate(final GoogleMap map, final LatLng latlng, final int zoom) {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, zoom));
        }

        public static Bitmap iconGeneratorSample(Context context, String str, Activity activity) {

            IconGenerator iconGenerator = new IconGenerator(context);
            iconGenerator.setBackground(activity.getResources().getDrawable(R.drawable.ic_place_red_24dp));
            iconGenerator.setStyle(IconGenerator.STYLE_GREEN);
            iconGenerator.setTextAppearance(R.style.IconGeneratorTextView);
            return  iconGenerator.makeIcon(str);
        }

        public static CircleOptions addCircle(Activity activity, LatLng latlng, float strokeWidth, int strokeColor, int fillColor, int radius){

            CircleOptions circleOptions = new  CircleOptions()
                    .center(new LatLng(latlng.latitude, latlng.longitude))
                    .radius(radius)
                    .strokeColor(activity.getResources().getColor(strokeColor))
                    .fillColor(fillColor)
                    .strokeWidth(strokeWidth);
            return circleOptions;
        }

    }


    public static class forFishComputation{

        public static String computeWeeklyFeedConsumption(double ABW, double NumberofStock, double feedingrate, double survivalrate) {
            DecimalFormat df = new DecimalFormat("#.##");
            return df.format (ABW*NumberofStock*feedingrate*survivalrate*7);
        }

        public static double get_TilapiaFeedingRate_by_WeekNum(int weeknum){
            if (weeknum > 18){
                return variables.ARRAY_TILAPIA_FEEDING_RATE_PER_WEEK[17];
            }else{
                return variables.ARRAY_TILAPIA_FEEDING_RATE_PER_WEEK[weeknum-1];
            }

        }

        public static double get_BangusFeedingRate_by_WeekNum(int weeknum){
            if (weeknum > 21){
                return variables.ARRAY_BANGUS_FEEDING_RATE_PER_WEEK[21];
            }else{
                return variables.ARRAY_BANGUS_FEEDING_RATE_PER_WEEK[weeknum-1];
            }

        }

        public static int get_currentWeek_by_stockedDate(String stockedDate, int abw){

            DateTime dt = new DateTime();
            GregorianCalendar jdkGcal = dt.toGregorianCalendar();
            dt = new DateTime(jdkGcal);


            String[] datesplitter = stockedDate.split("/");
            int weekStocked = get_Tilapia_WeekNum_byABW(abw);
            long currendate = System.currentTimeMillis();
            long stockeddate = convert.convertDateToLong(Integer.parseInt(datesplitter[1]), Integer.parseInt(datesplitter[0]), Integer.parseInt(datesplitter[2]));

            Log.d("Week Summary", stockeddate + " " + currendate + " " + weekStocked);
            DateTime start = new DateTime(
                    Integer.parseInt(datesplitter[2]), //year
                    Integer.parseInt(datesplitter[0]), //month
                    Integer.parseInt(datesplitter[1]), //day
                    0, 0, 0, 0);

            datesplitter = convert.convertLongtoDateString(currendate).split("/");
            DateTime end = new DateTime(
                    Integer.parseInt(datesplitter[2]), //year
                    Integer.parseInt(datesplitter[1]), //month
                    Integer.parseInt(datesplitter[0]), //day
                    0, 0, 0, 0);


            Weeks weeks = Weeks.weeksBetween(start, end);

            int currentWeekOfStock = weekStocked + weeks.getWeeks();

            return currentWeekOfStock;
        }

        public static int get_Bangus_WeekNum_byABW(int abw){

            if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[21]){
                return 22;
            }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[20]){
                return 21;
            }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[19]){
                return 20;
            }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[18]){
                return 19;
            }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[17]){
                return 18;
            }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[16]){
                return 17;
            }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[15]){
                return 16;
            }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[14]){
                return 15;
            }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[13]){
                return 14;
            }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[12]){
                return 13;
            }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[11]){
                return 12;
            }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[10]){
                return 11;
            }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[9]){
                return 10;
            }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[8]){
                return 9;
            }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[7]){
                return 8;
            }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[6]){
                return 7;
            }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[5]){
                return 6;
            }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[4]){
                return 5;
            }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[3]){
                return 4;
            }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[2]){
                return 3;
            }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[1]){
                return 2;
            }else{
                return 1;
            }//
        }

        public static int get_Vannamei_Extenxive_WeekNum_byABW(int abw){

            if (abw >= variables.ARRAY_VANNAMEI_EXTENSIVE_ABW_WEEKLY[13]){
                return 17;
            }else if (abw >= variables.ARRAY_VANNAMEI_EXTENSIVE_ABW_WEEKLY[12]){
                return 16;
            }else if (abw >= variables.ARRAY_VANNAMEI_EXTENSIVE_ABW_WEEKLY[11]){
                return 15;
            }else if (abw >= variables.ARRAY_VANNAMEI_EXTENSIVE_ABW_WEEKLY[10]){
                return 14;
            }else if (abw >= variables.ARRAY_VANNAMEI_EXTENSIVE_ABW_WEEKLY[9]){
                return 13;
            }else if (abw >= variables.ARRAY_VANNAMEI_EXTENSIVE_ABW_WEEKLY[8]){
                return 12;
            }else if (abw >= variables.ARRAY_VANNAMEI_EXTENSIVE_ABW_WEEKLY[7]){
                return 11;
            }else if (abw >= variables.ARRAY_VANNAMEI_EXTENSIVE_ABW_WEEKLY[6]){
                return 10;
            }else if (abw >= variables.ARRAY_VANNAMEI_EXTENSIVE_ABW_WEEKLY[5]){
                return 9;
            }else if (abw >= variables.ARRAY_VANNAMEI_EXTENSIVE_ABW_WEEKLY[4]){
                return 8;
            }else if (abw >= variables.ARRAY_VANNAMEI_EXTENSIVE_ABW_WEEKLY[3]){
                return 7;
            }else if (abw >= variables.ARRAY_VANNAMEI_EXTENSIVE_ABW_WEEKLY[2]){
                return 6;
            }else if (abw >= variables.ARRAY_VANNAMEI_EXTENSIVE_ABW_WEEKLY[1]){
                return 5;
            }else{
                return 4;
            }
        }

        public static int get_Tilapia_WeekNum_byABW(int abw){

            if (abw >= variables.ARRAY_TILAPIA_ABW_WEEKLY[17]){
                return 18;
            }else if (abw >= variables.ARRAY_TILAPIA_ABW_WEEKLY[16]){
                return 17;
            }else if (abw >= variables.ARRAY_TILAPIA_ABW_WEEKLY[15]){
                return 16;
            }else if (abw >= variables.ARRAY_TILAPIA_ABW_WEEKLY[14]){
                return 15;
            }else if (abw >= variables.ARRAY_TILAPIA_ABW_WEEKLY[13]){
                return 14;
            }else if (abw >= variables.ARRAY_TILAPIA_ABW_WEEKLY[12]){
                return 13;
            }else if (abw >= variables.ARRAY_TILAPIA_ABW_WEEKLY[11]){
                return 12;
            }else if (abw >= variables.ARRAY_TILAPIA_ABW_WEEKLY[10]){
                return 11;
            }else if (abw >= variables.ARRAY_TILAPIA_ABW_WEEKLY[9]){
                return 10;
            }else if (abw >= variables.ARRAY_TILAPIA_ABW_WEEKLY[8]){
                return 9;
            }else if (abw >= variables.ARRAY_TILAPIA_ABW_WEEKLY[7]){
                return 8;
            }else if (abw >= variables.ARRAY_TILAPIA_ABW_WEEKLY[6]){
                return 7;
            }else if (abw >= variables.ARRAY_TILAPIA_ABW_WEEKLY[5]){
                return 6;
            }else if (abw >= variables.ARRAY_TILAPIA_ABW_WEEKLY[4]){
                return 5;
            }else if (abw >= variables.ARRAY_TILAPIA_ABW_WEEKLY[3]){
                return 4;
            }else if (abw >= variables.ARRAY_TILAPIA_ABW_WEEKLY[2]){
                return 3;
            }else if (abw >= variables.ARRAY_TILAPIA_ABW_WEEKLY[1]){
                return 2;
            }else{
                return 1;
            }
        }

        public static String getTilapiaFeedTypeByNumberOfWeeks(int week){
            String feedtype;

            if (week <= 6) {
                feedtype = "Frymash";
            } else if(week <= 8){
                feedtype = "Crumble";
            } else if(week <= 10){
                feedtype = "Starter";
            } else if(week <= 14){
                feedtype = "Grower";
            } else {
                feedtype = "Finisher";
            }

            return feedtype;
        }

        public static String getBangusFeedtypeByABW(double abw){
            String feedtype;

            if (abw <= 10) {
                feedtype = "Pre-Starter Zero";
            } else if(abw <= 25){
                feedtype = "Pre-Starter";
            } else if(abw <= 150){
                feedtype = "Starter";
            } else if(abw <= 280){
                feedtype = "Grower";
            } else {
                feedtype = "Finisher";
            }

            return feedtype;
        }
    }


    public static class dialog{

        public static Dialog yesno(Activity activity, int dialogResID, String prompt, String title, String strButton1, String strButton2){
            final Dialog d = new Dialog(activity);//
            d.requestWindowFeature(Window.FEATURE_NO_TITLE);
            d.setContentView(dialogResID);//Set the xml view of the dialog
            Button btn1 = (Button) d.findViewById(R.id.btn_dialog_yesno_opt1);
            Button btn2 = (Button) d.findViewById(R.id.btn_dialog_yesno_opt2);
            TextView txttitle = (TextView) d.findViewById(R.id.dialog_yesno_title);
            TextView txtprompt = (TextView) d.findViewById(R.id.dialog_yesno_prompt);

            txtprompt.setText(prompt);
            txttitle.setText(title);
            btn1.setText(strButton1);
            btn2.setText(strButton2);
            return d;
        }

        public static Dialog themedYesNo(Activity activity, String prompt, String title, String strButton1, String strButton2, int resIdColor){
            final Dialog d = new Dialog(activity);//
            d.requestWindowFeature(Window.FEATURE_NO_TITLE);
            d.setContentView(R.layout.dialog_material_themed_yesno);//Set the xml view of the dialog
            Button btn1 = (Button) d.findViewById(R.id.btn_dialog_yesno_opt1);
            Button btn2 = (Button) d.findViewById(R.id.btn_dialog_yesno_opt2);
            TextView txttitle = (TextView) d.findViewById(R.id.dialog_yesno_title);
            TextView txtprompt = (TextView) d.findViewById(R.id.dialog_yesno_prompt);

            txtprompt.setText(prompt);
            txttitle.setText(title);
    //        txttitle.setBackground(activity.getResources().getDrawable(resIdColor));
            btn1.setText(strButton1);
            btn2.setText(strButton2);


            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            Window window = d.getWindow();
            lp.copyFrom(window.getAttributes());
    //This makes the dialog take up the full width
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);

            d.show();
            return d;
        }

        public static Dialog yesNo_withEditText(Activity activity, String prompt, String edtEntry, String title, String strButton1, String strButton2, int resIdColor){
            final Dialog d = new Dialog(activity);//
            d.requestWindowFeature(Window.FEATURE_NO_TITLE);
            d.setContentView(R.layout.dialog_material_themed_yesno_with_edittext);//Set the xml view of the dialog
            Button btn1 = (Button) d.findViewById(R.id.btn_dialog_yesno_opt1);
            Button btn2 = (Button) d.findViewById(R.id.btn_dialog_yesno_opt2);
            EditText edt = (EditText) d.findViewById(R.id.dialog_edttext);
            TextView txttitle = (TextView) d.findViewById(R.id.dialog_yesno_title);
            TextView txtprompt = (TextView) d.findViewById(R.id.dialog_yesno_prompt);

            txtprompt.setText(prompt);
            edt.setText(edtEntry);
            txttitle.setText(title);
            txttitle.setBackground(activity.getResources().getDrawable(resIdColor));
            btn1.setText(strButton1);
            btn2.setText(strButton2);
            d.show();
            return d;
        }

        public static Dialog numberPicker(Activity activity, String dialogTitle, int minVal, int maxValue){
            final Dialog d = new Dialog(activity);//
    //        d.requestWindowFeature(Window.FEATURE_NO_TITLE); //notitle
            d.setContentView(R.layout.dialog_numberpicker);
            d.setTitle(dialogTitle);
            final NumberPicker nbp = (NumberPicker) d.findViewById(R.id.dialog_numberpicker);
            Button set = (Button) d.findViewById(R.id.btn_numberpicker_set);
            nbp.setMaxValue(maxValue);
            nbp.setMinValue(minVal);

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(d.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;

            d.show();
            return d;
        }

        public static Dialog decimalPicker(Activity activity, String dialogTitle, int minVal, int maxValue){
            final Dialog d = new Dialog(activity);//
    //        d.requestWindowFeature(Window.FEATURE_NO_TITLE); //notitle

            d.setContentView(R.layout.dialog_decimalpicker);
            d.setTitle(dialogTitle);
            NumberPicker wholeNum = (NumberPicker) d.findViewById(R.id.dialog_decipicker_whole);
            wholeNum.setMaxValue(maxValue);
            wholeNum.setMinValue(0);

            NumberPicker decimal = (NumberPicker) d.findViewById(R.id.dialog_decipicker_deci);
            decimal.setMaxValue(maxValue);
            decimal.setMinValue(0);

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(d.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;

            d.show();
            return d;
        }

        public static Dialog okOnly(Activity activity, String title, String prompt, String button){
            final Dialog d = new Dialog(activity);//
            d.requestWindowFeature(Window.FEATURE_NO_TITLE); //notitle
            d.setContentView(R.layout.dialog_material_okonly);//Set the xml view of the dialog
            TextView txttitle = (TextView) d.findViewById(R.id.dialog_okonly_title);
            TextView txtprompt = (TextView) d.findViewById(R.id.dialog_okonly_prompt);
            Button txtok = (Button) d.findViewById(R.id.btn_dialog_okonly_OK);
            txtok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.hide();
                }
            });
            txtprompt.setText(prompt);
            txttitle.setText(title);
            txtok.setText(button);
            d.show();

            return d;
        }

        public static Dialog themedOkOnly(Activity activity, String title, String prompt, String button){
            final Dialog d = new Dialog(activity);//
            d.requestWindowFeature(Window.FEATURE_NO_TITLE); //notitle
            d.setContentView(R.layout.dialog_material_themed_okonly);//Set the xml view of the dialog
            TextView txttitle = (TextView) d.findViewById(R.id.dialog_okonly_title);
            TextView txtprompt = (TextView) d.findViewById(R.id.dialog_okonly_prompt);
            Button txtok = (Button) d.findViewById(R.id.btn_dialog_okonly_OK);
            txtok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.hide();
                }
            });
    //        txttitle.setBackground(activity.getResources().getDrawable(resIdColor));
            txtok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.hide();
                }
            });
            txtprompt.setText(prompt);
            txttitle.setText(title);
            txtok.setText(button);

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            Window window = d.getWindow();
            lp.copyFrom(window.getAttributes());
    //This makes the dialog take up the full width
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);

            d.show();
            return d;
        }

        public static Dialog themedOkOnly_scrolling(Activity activity, String title, String prompt, String button, int resIdColor){
            final Dialog d = new Dialog(activity);//
            d.requestWindowFeature(Window.FEATURE_NO_TITLE); //notitle
            d.setContentView(R.layout.dialog_material_themed_okonly_scrollview);//Set the xml view of the dialog
            TextView txttitle = (TextView) d.findViewById(R.id.dialog_okonly_title);
            TextView txtprompt = (TextView) d.findViewById(R.id.dialog_okonly_prompt);
            Button txtok = (Button) d.findViewById(R.id.btn_dialog_okonly_OK);
            txtok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.hide();
                }
            });
            txttitle.setBackground(activity.getResources().getDrawable(resIdColor));
            txtok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.hide();
                }
            });
            txtprompt.setText(prompt);
            txttitle.setText(title);
            txtok.setText(button);
            d.show();
            return d;
        }

        public static Dialog list(Activity activity, String[] options, String title){
            final Dialog d = new Dialog(activity);//
            d.requestWindowFeature(Window.FEATURE_NO_TITLE); //notitle
            d.setContentView(R.layout.dialog_material_list);//Set the xml view of the dialog

            ListView listview = (ListView) d.findViewById(R.id.dialog_list_listview);
            ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(activity, R.layout.select_dialog_item_material, options); //selected item will look like a spinner set from XML
            listViewAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            listview.setAdapter(listViewAdapter);

            TextView txtTitle = (TextView) d.findViewById(R.id.dialog_okonly_title);
            txtTitle.setText(title);
            d.show();
            return d;
        }

        public static Dialog themedList(Activity activity, String[] options, String title, int resIdColor){
            final Dialog d = new Dialog(activity);//
            d.requestWindowFeature(Window.FEATURE_NO_TITLE); //notitle
            d.setContentView(R.layout.dialog_material_themed_list);//Set the xml view of the dialog

            ListView listview = (ListView) d.findViewById(R.id.dialog_list_listview);
            ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(activity, R.layout.select_dialog_item_material, options); //selected item will look like a spinner set from XML
            listViewAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            listview.setAdapter(listViewAdapter);

            TextView txtTitle = (TextView) d.findViewById(R.id.dialog_okonly_title);
            txtTitle.setBackground(activity.getResources().getDrawable(resIdColor));
            txtTitle.setText(title);
            d.show();
            return d;
        }

        public static Dialog list_withPrompt(Activity activity, String[] options, String title, String message, int resIdColor){
            final Dialog d = new Dialog(activity);//
            d.requestWindowFeature(Window.FEATURE_NO_TITLE); //notitle
            d.setContentView(R.layout.dialog_material_themed_list_with_prompt);//Set the xml view of the dialog

            ListView listview = (ListView) d.findViewById(R.id.dialog_list_listview);
            ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(activity, R.layout.select_dialog_item_material, options); //selected item will look like a spinner set from XML
            listViewAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            listview.setAdapter(listViewAdapter);

            TextView txtTitle = (TextView) d.findViewById(R.id.dialog_okonly_title);
            TextView txtmessage = (TextView) d.findViewById(R.id.txt_message);

            txtTitle.setBackground(activity.getResources().getDrawable(resIdColor));
            txtTitle.setText(title);
            txtmessage.setText(message);

            d.show();
            return d;
        }

        public static Dialog initProgressDialog(Activity activity){
            Dialog PD = new Dialog(activity);
            PD.requestWindowFeature(Window.FEATURE_NO_TITLE);
            PD.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            PD.setCancelable(false);
            PD.setContentView(R.layout.progressdialog);
            return  PD;
        }
    }


    public static class deviceInfo{

        public static String getMacAddress(Context context){
            String macaddress = "";
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1){
                try {
                    String interfaceName = "wlan0";
                    List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
                    for (NetworkInterface intf : interfaces) {
                        if (!intf.getName().equalsIgnoreCase(interfaceName)){
                            continue;
                        }

                        byte[] mac = intf.getHardwareAddress();
                        if (mac==null){
                            macaddress = "";
                        }

                        StringBuilder buf = new StringBuilder();
                        for (byte aMac : mac) {
                            buf.append(String.format("%02X:", aMac));
                        }
                        if (buf.length()>0) {
                            buf.deleteCharAt(buf.length() - 1);
                        }
                        macaddress = buf.toString();
                    }
                } catch (Exception ex) { } // for now eat exceptions
            }else{
                WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo info = manager.getConnectionInfo();
                macaddress = info.getMacAddress();
            }

            return macaddress;

        }

        public static String getIMEI(Context context){
            TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager.getDeviceId();
        }
    }


    public static class isnull {

        public static boolean isGlobalUserIDNull(Activity activity){
            boolean isEmpty = false;
            if(variables.getGlobalVar_currentUserID(activity) <= 0){
                isEmpty = true;
            }
            return  isEmpty;
        }

        public static boolean isIntentKeywordNotNull(String keyword, Intent extras){
            if (extras != null){
                if (extras.hasExtra(keyword)) {
                    Log.d("EXTRAS", "true");
                    return true;
                }else {
                    Log.d("EXTRAS", "false");
                    return false;
                }
            }else {
                return false;
            }
        }
    }


    public static class activityActions {

        public static void startActivityClearStack(Activity currentActivity, Class nextActivity) {
            Intent intent = new Intent(currentActivity, nextActivity);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            currentActivity.startActivity(intent);
        }
    }


    public static class random {

        public static String trimFirstAndLast(String string){
            String trimmed = "";

            trimmed = string.substring(1,string.length() );
            return  trimmed = trimmed.substring(0, trimmed.length() - 1);
        }

        public static boolean checkSD(Activity activity) {
            Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
            if(isSDPresent) {
                Log.d("CheckExternalStorage", "External available");
            }
            else
            {
                Log.d("CheckExternalStorage", "External NOT available");
            }
            return isSDPresent;
        }

        public static String deciformat(double num, int numberOfDecimalPlace) {
            String str = "";

            for (int i = 0; i < numberOfDecimalPlace; i++) {
                    str = str +"#";
            }
            DecimalFormat df = new DecimalFormat("#."+str);
            return df.format(num);
        }

        public static void setCursorOnEnd(EditText edt) {
            edt.setSelection(edt.getText().length());
        }

        public static int removeSuffix(String wholeValue, String unitsToRemove){
            int initialValue;

            if (wholeValue.equalsIgnoreCase("") || wholeValue.equalsIgnoreCase(null)) {
                initialValue = 1;
            }else {
                if (wholeValue.substring(wholeValue.length() - unitsToRemove.length(), wholeValue.length()) .equalsIgnoreCase(unitsToRemove)){
                    initialValue = Integer.parseInt(wholeValue.substring(0, wholeValue.length() - unitsToRemove.length()));
                }else{
                    initialValue = Integer.parseInt(wholeValue.toString());
                }
            }

            return  initialValue;
        }

        public static boolean isNetworkAvailable(Context context) {

            ConnectivityManager connectivityManager
                    = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }

        public static void hideKeyboardOnLoad(Activity activity){
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }

        public static void moveCursortoEnd(Activity activity, int resId){
            EditText et = (EditText)activity.findViewById(resId);
            et.setSelection(et.getText().length());
        }

        public static String extractResponseCodeBySplit(String response) {
            String[] splitted = response.split(":");
            return splitted[1].substring(0,1);
        }

        public static String[] splitter(String string, String splitter) {
            return  string.split(splitter);
        }

    }


    public static class animate{

        // To animate view slide out from left to right
        public static void slideToRight(View view){
            TranslateAnimation animate = new TranslateAnimation(0,view.getWidth(),0,0);
            animate.setDuration(500);
            animate.setFillAfter(true);
            view.startAnimation(animate);
            view.setVisibility(View.GONE);
        }

        // To animate view slide out from right to left
        public static void slideToLeft(View view){
            TranslateAnimation animate = new TranslateAnimation(0,-view.getWidth(),0,0);
            animate.setDuration(500);
            animate.setFillAfter(true);
            view.startAnimation(animate);
            view.setVisibility(View.GONE);
        }

        // To animate view slide out from top to bottom
        public static void slideToBottom(final View view, float height , final float alpha,final int vis){
            TranslateAnimation animate = new TranslateAnimation(0,0,0,view.getHeight());
            animate.setDuration(500);
            animate.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    view.animate()
                            .alpha(alpha)
                            .setDuration(300)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                }
                            });
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.setVisibility(vis);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            view.startAnimation(animate);
        }

        // To animate view slide out from bottom to top
        public static void slideToTop(final View view, float height , final float alpha,final int vis){
            final TranslateAnimation animate = new TranslateAnimation(0,0,0,-height);
            animate.setDuration(500);
            animate.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    view.animate()
                            .alpha(alpha)
                            .setDuration(200)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                }
                            });

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.setVisibility(vis);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            view.startAnimation(animate);

        }
    }


    public static class convert{

        public static String LongToTime12Hour(long dateInMillis){
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mmaa");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(dateInMillis);
            return formatter.format(calendar.getTime());
        }

        public static String LongToDate_ShortGregorian(long dateInMillis){
            SimpleDateFormat formatter = new SimpleDateFormat("MMM dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(dateInMillis);
            return formatter.format(calendar.getTime());
        }

        public static String LongToDate_Gregorian(long dateInMillis){
            SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(dateInMillis);
            return formatter.format(calendar.getTime());
        }

        public static int getDateDifferenceHour(long start, long end){

            DateTime datestart = new DateTime(start);
            DateTime dateend = new DateTime(end);
//            Interval interval = new Interval(start, end);

            return Hours.hoursBetween(dateend,datestart).getHours();
        }

        public static int getDateDifferenceMinute(long start, long end){

            DateTime datestart = new DateTime(start);
            DateTime dateend = new DateTime(end);
//            Interval interval = new Interval(start, end);

            return Minutes.minutesBetween(dateend,datestart).getMinutes();
        }

        public static String getDateTimePassed(String endTime){
            int difInHour = Helper.convert.getDateDifferenceHour(System.currentTimeMillis(), Long.valueOf(endTime));
            int difInMinutes = Helper.convert.getDateDifferenceMinute(System.currentTimeMillis(), Long.valueOf(endTime));
            String time = "";

            if (difInMinutes  <= 0 ){
                time = "Just now";
            }else if (difInMinutes  < 2 ){
                time = difInMinutes + " minute ago";
            }else if(difInMinutes < 60){
                time = difInMinutes +  " minutes ago";
            }else if(difInHour == 1){
                time = difInHour + " hour ago";
            }else if(difInHour < 24){
                time = difInHour + " hours ago";
            }
            else if (difInHour < 48){
                time = "Yesterday "+ Helper.convert.LongToTime12Hour(Long.valueOf(endTime));
            }else {
                time = Helper.convert.LongToDate_ShortGregorian(Long.valueOf(endTime));
            }

            return time;
        }



        public static long convertDateToLong(int dd, int MM, int yyyy){
            long startDate=000000;
            try {
                String dateString = dd+"/"+MM+"/"+yyyy;
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date date = sdf.parse(dateString);
                startDate = date.getTime();

            } catch (ParseException e) {
                e.printStackTrace();
            }
            return startDate;
        }

        public static String convertDatetoGregorian(int yyyy, int MM, int dd){
            String dateString_gregorian="";
            try {
                String dateString = dd+"/"+MM+"/"+yyyy;
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date date = sdf.parse(dateString);
                dateString_gregorian = convertLongShortGregorian(date.getTime());

            } catch (ParseException e) {
                e.printStackTrace();
            }
            return dateString_gregorian;
        }

        public static String convertDateToShortGregorian(int yyyy, int MM, int dd){
            String dateString_gregorian="";
            try {
                String dateString = dd+"/"+MM+"/"+yyyy;
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date date = sdf.parse(dateString);
                dateString_gregorian = convertLongtoDate_Gregorian(date.getTime());

            } catch (ParseException e) {
                e.printStackTrace();
            }
            return dateString_gregorian;
        }

        public static long convertDateTimeStringToMilis_DB_Format(String datetime){
            long startDate=000000;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = sdf.parse(datetime);
                startDate = date.getTime();

            } catch (ParseException e) {
                e.printStackTrace();
            }
            return startDate;
        }

        public static long convertDateToMilis_DB_Format(String yyyymmdd){
            long startDate=000000;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(yyyymmdd);
                startDate = date.getTime();

            } catch (ParseException e) {
                e.printStackTrace();
            }
            return startDate;
        }

        public static String convertLongtoDateString(long dateInMillis){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(dateInMillis);
            return formatter.format(calendar.getTime());
        }

        public static String convertLongtoDateString_DB_format(long dateInMillis){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(dateInMillis);
            return formatter.format(calendar.getTime());
        }

        public static String convertLongShortGregorian(long dateInMillis){
            SimpleDateFormat formatter = new SimpleDateFormat("MMM dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(dateInMillis);
            return formatter.format(calendar.getTime());
        }

        public static String convertLongtoDate_Gregorian(long dateInMillis){
            SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(dateInMillis);
            return formatter.format(calendar.getTime());
        }

        public static String convertLongtoDate_GregorianWithTime(long dateInMillis){
            SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy hh:mmaa");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(dateInMillis);
            return formatter.format(calendar.getTime());
        }

        public static String convertLongtoDateTimeString(long dateInMillis){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mmaa");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(dateInMillis);
            return formatter.format(calendar.getTime());
        }

        public static String convertLongtoDateTime_DB_Format(long dateInMillis){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(dateInMillis);
            return formatter.format(calendar.getTime());
        }

        public static String convertLongtoDate_DB_Format(long dateInMillis){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(dateInMillis);
            return formatter.format(calendar.getTime());
        }

        public static String getDateTimeDBformat(){
            return convertLongtoDateTime_DB_Format(System.currentTimeMillis());
        }

        public static String getDateDBformat(){
            return convertLongtoDate_DB_Format(System.currentTimeMillis());
        }

        public static long getDateDifference(long date1, long date2){

            long diff = date1 - date2; //result in millis
            long days = diff / (24 * 60 * 60 * 1000);

            Log.d("DIFF", "days: "+days+"");
            return days;
        }

        public static int[] convertLongtoDateFormat(long dateinMilis) {
    //        Calendar calendar = null;
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String dateString = formatter.format(new Date(dateinMilis));
            String[] date = dateString.split("/");

    //        calendar.setTimeInMillis(dateinMilis);
            int day = Integer.parseInt(date[0]);
            int month = Integer.parseInt(date[1]);
            int year = Integer.parseInt(date[2]);


            return new int[]{month,day,year};
        }
    }


    public static class toast{

        public static void short_(Activity context, String msg){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                Snackbar snackbar = Snackbar.make(context.findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT)
                        .setActionTextColor(context.getResources().getColor(R.color.gray_100));

                View view = snackbar.getView();
                TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(context.getResources().getColor(R.color.gray_100));
                tv.setMaxLines(5);
                snackbar.show();
            }else{
                LayoutInflater inflater = context.getLayoutInflater();
                final View layout = inflater.inflate(R.layout.toast,
                        (ViewGroup) context.findViewById(R.id.toast_layout_root));

                TextView text = (TextView) layout.findViewById(R.id.text);
                Typeface font = Typeface.createFromAsset(context.getAssets(), "Roboto-Light.ttf");
                text.setTypeface(font);
                text.setText(msg);

                Toast toast = new Toast(context.getApplicationContext());
                toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setMargin(0, 0);
                toast.setView(layout);

                toast.show();
            }

        }

        public static void long_(Activity context, String msg){

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

                Snackbar snackbar = Snackbar.make(context.findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT)
                        .setActionTextColor(context.getResources().getColor(R.color.gray_100));

                View view = snackbar.getView();
                TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(context.getResources().getColor(R.color.gray_100));
                tv.setMaxLines(5);
                snackbar.show();

            }else{

                LayoutInflater inflater = context.getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast,
                        (ViewGroup) context.findViewById(R.id.toast_layout_root));

                TextView text = (TextView) layout.findViewById(R.id.text);
                Typeface font = Typeface.createFromAsset(context.getAssets(), "Roboto-Light.ttf");
                text.setTypeface(font);
                text.setText(msg);

                Toast toast = new Toast(context.getApplicationContext());
                toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
                toast.setMargin(0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();

            }
        }

        public static void indefinite(Activity context, String msg){

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                final Snackbar snackbar = Snackbar.make(context.findViewById(android.R.id.content), msg, Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(context.getResources().getColor(R.color.gray_100));

                View view = snackbar.getView();
                TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(context.getResources().getColor(R.color.gray_100));
                tv.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        snackbar.dismiss();
                        return false;
                    }
                });
                tv.setMaxLines(5);
                snackbar.show();
            }else{
                LayoutInflater inflater = context.getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast,
                        (ViewGroup) context.findViewById(R.id.toast_layout_root));

                TextView text = (TextView) layout.findViewById(R.id.text);
                Typeface font = Typeface.createFromAsset(context.getAssets(), "Roboto-Light.ttf");
                text.setTypeface(font);
                text.setText(msg);

                Toast toast = new Toast(context.getApplicationContext());
                toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
                toast.setMargin(0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }


        }


        public static Snackbar snackbarWithAction(Activity context, String msg){
            Snackbar snackbar = null;
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                snackbar = Snackbar.make(context.findViewById(android.R.id.content), msg, Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(context.getResources().getColor(R.color.gray_100))
                ;

                View view = snackbar.getView();
                TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(context.getResources().getColor(R.color.gray_100));
                final Snackbar finalSnackbar = snackbar;
                tv.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        finalSnackbar.dismiss();
                        return false;
                    }
                });
                tv.setMaxLines(5);
                snackbar.show();
            }

            return snackbar;

        }

    }


    public static class LocationUtil{

        public static String getAddress(Context context1,String lat, String lon) {
            String address1 = "";
            try {
                Geocoder geocoder = new Geocoder(context1);
                List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(lat), Double.parseDouble(lon), 1);
                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getAddressLine(1);
                String country = addresses.get(0).getAddressLine(2);
                String country1 = addresses.get(0).getAddressLine(3);
                address1 =
//                        "" + address + ", " +
                        city + ", " + country
//                        + ", " + country1
                ;
                Log.d("TAG",address1 );
            } catch (Exception e) {
                e.printStackTrace();
            }

            return address1;
        }
    }


    public static class fileInfo{

        public static String getSize(Intent returnIntent, Context context){
            Uri returnUri = returnIntent.getData();
            Cursor returnCursor =
                    context.getContentResolver().query(returnUri, null, null, null, null);
            assert returnCursor != null;
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
            returnCursor.moveToFirst();

            return Long.toString(returnCursor.getLong(sizeIndex));
        }


        public static String getName(Intent returnIntent, Context context){
            Uri returnUri = returnIntent.getData();
            Cursor returnCursor =
                    context.getContentResolver().query(returnUri, null, null, null, null);
            assert returnCursor != null;
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
            returnCursor.moveToFirst();

            return returnCursor.getString(nameIndex);
        }
    }



}//end of class
