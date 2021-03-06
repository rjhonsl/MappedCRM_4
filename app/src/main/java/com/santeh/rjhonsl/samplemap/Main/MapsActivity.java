package com.santeh.rjhonsl.samplemap.Main;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.santeh.rjhonsl.samplemap.APIs.MyVolleyAPI;
import com.santeh.rjhonsl.samplemap.DBase.GPSHelper;
import com.santeh.rjhonsl.samplemap.DBase.GPSQuery;
import com.santeh.rjhonsl.samplemap.Obj.CustInfoObject;
import com.santeh.rjhonsl.samplemap.Obj.Var;
import com.santeh.rjhonsl.samplemap.Parsers.CustAndPondParser;
import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.FusedLocation;
import com.santeh.rjhonsl.samplemap.Utils.GPSTracker;
import com.santeh.rjhonsl.samplemap.Utils.Helper;
import com.santeh.rjhonsl.samplemap.Utils.Logging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    ProgressDialog PD;
    boolean isDrawerOpen = false;

    LinearLayout layout;

    ViewGroup.LayoutParams params;
    LinearLayout mainLayout;
    Location mLastLocation;
    String username, firstname, lastname, userdescription;
    int userlevel, userid, zoom = 15, activeFilter;
    DrawerLayout drawerLayout;
    ImageButton btn_add_marker, btn_cancelAddmarker;
    ActionBarDrawerToggle drawerListener;
    Marker clickedMarker;
    int strikes = 0;

    double curlat, curLong;

    Activity activity;
    Context context;
    GoogleApiClient mGoogleApiClient;
    GoogleMap maps;

    LatLng curLatlng, lastlatlng;

    TextView  tvBottomPopUp,txtfishbook, txtViewTop, nav_fingerlings, nav_customerAddress, nav_sperms, nav_logout, nav_maptype, nav_displayAllMarkers, nav_settings, nav_growout,nav_usermonitoring, txtusername;

    String activeSelection;
    List<CustInfoObject> custInfoObjectList;

    Bundle extrass;
    Intent passedintent;

    CircleOptions circleOptions_addLocation;
    Circle mapcircle;

    boolean isMarkerSelected = false;
    FusedLocation fusedLocation;
    GPSQuery db;
    int userlvl;

    String[] markerdetails;
    ImageButton btnOwnerLocation, btnCasePond, btnFarmDetails, btnChangeLocation, btnDeleteMarker, btnBurgermenu;

    ViewGroup hiddenPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        activity = MapsActivity.this;
        context = MapsActivity.this;
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        passedintent = getIntent();
        extrass = getIntent().getExtras();
        db = new GPSQuery(this);
        db.open();

        userlvl = Helper.variables.getGlobalVar_currentLevel(activity);

        layout = new LinearLayout(this);
        mainLayout = new LinearLayout(this);
        tvBottomPopUp = new TextView(this);

        fusedLocation = new FusedLocation(context, activity);
        fusedLocation.buildGoogleApiClient(context);
        fusedLocation.connectToApiClient();
        activeSelection = "farm";
        lastlatlng = fusedLocation.getLastKnowLocation();




        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        nav_displayAllMarkers = (TextView) findViewById(R.id.txt_Nav_displayAll);
        nav_fingerlings = (TextView) findViewById(R.id.txt_Nav_fingerlings);
        nav_customerAddress = (TextView) findViewById(R.id.txt_Nav_customeraddress);
        nav_sperms = (TextView) findViewById(R.id.txt_Nav_sperms);
        nav_maptype = (TextView) findViewById(R.id.txt_Nav_changeMapType);
        nav_settings = (TextView) findViewById(R.id.txt_Nav_settings);
        btn_add_marker = (ImageButton) findViewById(R.id.btnaddMarker);
        btn_cancelAddmarker = (ImageButton) findViewById(R.id.btnCloseAddMarker);
        nav_growout = (TextView) findViewById(R.id.txt_Nav_growOut);
        nav_logout = (TextView) findViewById(R.id.txt_Nav_logout);
        txtViewTop = (TextView) findViewById(R.id.txtTopTextView);
        nav_usermonitoring = (TextView) findViewById(R.id.txt_Nav_UserMonitoring);
        txtusername = (TextView) findViewById(R.id.username);
        txtfishbook = (TextView) findViewById(R.id.txtFishbook);


        hiddenPanel = (ViewGroup)findViewById(R.id.ll_bottomedit);
        btnOwnerLocation = (ImageButton) findViewById(R.id.btn_ownerLocation);
        btnCasePond = (ImageButton) findViewById(R.id.btn_Cases);
        btnChangeLocation = (ImageButton) findViewById(R.id.btn_changeMarkerLocation);
        btnFarmDetails = (ImageButton) findViewById(R.id.btn_farminfo);
        btnDeleteMarker = (ImageButton) findViewById(R.id.btn_deleteMarker);
        btnBurgermenu = (ImageButton) findViewById(R.id.btnBurgermenu);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActionToggleDrawerListner();
        drawerListener.syncState();

        PD = new ProgressDialog(this);
        PD.setMessage("Getting data from server.\nPlease wait....");
        PD.setCancelable(false);

    }



    private void initMarkers() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {  //if extras is not nul
            if (extras.getString("fromActivity") != null){
                String from = extras.getString("fromActivity");

                if (from.equalsIgnoreCase("viewCustinfo")) { //if from Activity View Customer Info
                    if (extras.getString("lat")!=null && extras.get("long")!= null) {//if lat & long is not null// passed from intent
                        LatLng latLng = new LatLng(
                                Double.parseDouble(extras.getString("lat")),
                                Double.parseDouble(extras.getString("long"))  );

                        if(((Var) this.getApplication()).getGoogleMap() != null){ //if google maps is ready
                            moveCameraAnimate(((Var) this.getApplication()).getGoogleMap(), latLng, 15);
                            maps.setInfoWindowAdapter(new FarmInfoWindow());
                            maps.clear();

                            Helper.map.map_addMarker(((Var) this.getApplication()).getGoogleMap(), latLng, R.drawable.ic_place_red_24dp,
                                    extras.getString("contactName"), extras.getString("address"), extras.getInt("id")+"",null, null);
                        PD.dismiss();
                        }
                        else{//google map not ready
                            Helper.toast.short_(activity, "Can't find current location. Please try again later.");
                        }
                    }
                }else{//if not from view Customer Info
                    showAllRelatedMarkers();
                }
            }
            else {//if opened from login screen
                    showAllRelatedMarkers();  }
        }
        else{//if opened from login screen
            showAllRelatedMarkers();
        }
    }

    //when map is read
    @Override
    public void onMapReady(final GoogleMap map) {
        map.getUiSettings().setMapToolbarEnabled(false);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.setMyLocationEnabled(true);
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        maps = map;

        getunsynchedData();

        txtViewTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog d = Helper.dialog.themedYesNo(activity, "Sync data to our server?", "Sync", "NO", "SYNC NOW", R.color.skyblue_500);
                final Button btnNo = (Button) d.findViewById(R.id.btn_dialog_yesno_opt1);
                Button btnSync = (Button) d.findViewById(R.id.btn_dialog_yesno_opt2);

                btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.hide();
                    }
                });
                btnSync.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.hide();
                        startSynchingDB_FARMINFO();
//                        startSynchingDB_HarvestInfo();
                    }
                });
            }
        });

        ((Var) this.getApplication()).setGoogleMap(map);

        txtusername.setText(Helper.variables.getGlobalVar_currentUserFirstname(activity) + " " + Helper.variables.getGlobalVar_currentUserLastname(activity));
        map.setInfoWindowAdapter(new FarmInfoWindow());
        initListeners(map);
        fusedLocation.connectToApiClient();

        params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        layout.setOrientation(LinearLayout.VERTICAL);
        btn_cancelAddmarker.setVisibility(View.GONE);

        tvBottomPopUp.setBackgroundColor(ContextCompat.getColor(context, R.color.white_200));
        tvBottomPopUp.setText("Owner Location");
        tvBottomPopUp.setPadding(5, 5, 5, 5);

        layout.addView(tvBottomPopUp, params);
        layout.setBackgroundColor(ContextCompat.getColor(context, R.color.white_200));


        btnOwnerLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOwnerLocation();
                isMarkerSelected = false;
                animateBottom();
            }
        });

        btnBurgermenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDrawerOpen){
                    closeDrawer();
                }else{
                    openDrawer();
                }
            }
        });

        txtfishbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isPackageInstalled("com.santeh.rjhonsl.fishbook", context)){
                  Context ctx= context; // or you can replace **'this'** with your **ActivityName.this**
                  Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.santeh.rjhonsl.fishbook");
                  ctx.startActivity(i);
                }else{
                    Helper.toast.indefinite(activity, "Fishbook is not yet installed.");
                }
                closeDrawer();


            }
        });


        btnCasePond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoCaseAndPond(clickedMarker.getPosition(), markerdetails);
                animateBottom();
            }
        });

        btnChangeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickedMarker != null) {
                    fusedLocation.disconnectFromApiClient();
                    fusedLocation.connectToApiClient();
                    final LatLng originalPosition = clickedMarker.getPosition();

                    final LatLng center = fusedLocation.getLastKnowLocation();
                    moveCameraAnimate(maps, center, 14);

                    Helper.dialog.themedOkOnly(activity, "Change Location", "Long press marker until InfoWindow is gone then drag marker to desired location. \n\nNOTE: You should not exceed 1000m from your current location.", "OK");
                    hiddenPanel.setVisibility(View.GONE);
                    if (mapcircle == null || !mapcircle.isVisible()) {
                        circleOptions_addLocation = Helper.map.addCircle(activity, center, 1, R.color.skyblue_20,
                                R.color.skyblue_20, 1000);
                        mapcircle = map.addCircle(circleOptions_addLocation);
                    }

                    clickedMarker.setDraggable(true);
                    map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                        @Override
                        public void onMarkerDragStart(Marker marker) {
                            hiddenPanel.setVisibility(View.GONE);
                            marker.hideInfoWindow();
                        }

                        @Override
                        public void onMarkerDrag(Marker marker) {
                            hiddenPanel.setVisibility(View.GONE);
                            marker.hideInfoWindow();
                        }

                        @Override
                        public void onMarkerDragEnd(final Marker marker) {
                            closeAddingMarker();
                            if (Helper.map.getDifference(center, marker.getPosition()) > 1000) {
                                Helper.dialog.themedOkOnly(activity, "Warning", "You can't place marker 1000m away from your current location", "OK");
                                marker.setPosition(originalPosition);
                                marker.showInfoWindow();
                            }else{
                                final Dialog d = Helper.dialog.themedYesNo(activity, "Move location of client's marker here?", "Change Location", "NO", "YES", R.color.red_material_600);
                                Button yes = (Button) d.findViewById(R.id.btn_dialog_yesno_opt2);
                                Button no = (Button) d.findViewById(R.id.btn_dialog_yesno_opt1);
                                d.setCancelable(false);
                                yes.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        d.hide();
                                        marker.setDraggable(false);
                                        clickedMarker.setDraggable(false);

                                        hiddenPanel.setVisibility(View.VISIBLE);
                                        marker.showInfoWindow();
                                        db.updateOneRow(
                                                GPSHelper.TBLFARMiNFO,     //table name
                                                GPSHelper.CL_FARMINFO_LAT,  //column name
                                                marker.getPosition().latitude + "",       //value
                                                GPSHelper.CL_FarmInfo_ID + " = " + markerdetails[0] //condition;
                                        );
                                        db.updateOneRow(
                                                GPSHelper.TBLFARMiNFO,     //table name
                                                GPSHelper.CL_FARMINFO_LNG,  //column name
                                                marker.getPosition().longitude+"",       //value
                                                GPSHelper.CL_FarmInfo_ID +" = "+ markerdetails[0] //condition;
                                        );
                                    }
                                });

                                no.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        marker.setPosition(originalPosition);
                                        d.hide();
                                        marker.showInfoWindow();
                                        isMarkerSelected= true;
                                        animateBottom();

                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

        btnFarmDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoFarmDetails(clickedMarker.getPosition(), markerdetails, clickedMarker);
                animateBottom();
            }
        });

        tvBottomPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOwnerLocation();
            }
        });


        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                if (isMarkerSelected) {
                    isMarkerSelected = false;
                }
                animateBottom();
                closeAddingMarker();

            }
        });


        btnDeleteMarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteRow_FarmInfo(markerdetails[0]);
                isMarkerSelected = false;
                animateBottom();
                closeAddingMarker();
                clickedMarker.remove();
                getunsynchedData();

            }
        });

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                clickedMarker = marker;
                isMarkerSelected = true;

                final String[] details = marker.getTitle().split("#-#");
                markerdetails = details;

                if (activeSelection.equalsIgnoreCase("farm")) {
                    animateBottom();
                }


                if (!markerdetails[0].equalsIgnoreCase("")){
                    if (db.isFarmnamePost(markerdetails[0])) {
                        btnChangeLocation.setEnabled(false);
                        btnDeleteMarker.setEnabled(false);
                    } else {
                        btnChangeLocation.setEnabled(true);
                        btnDeleteMarker.setEnabled(true);
                    }
                }


                return false;
            }
        });


        if (Helper.variables.getGlobalVar_currentLevel(activity) > 1){
            nav_usermonitoring.setVisibility(View.GONE);
        }else{
            nav_usermonitoring.setVisibility(View.VISIBLE);
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
            try {
                //centered on sibuyan sea
                fusedLocation.disconnectFromApiClient();
                fusedLocation.connectToApiClient();

                if (checkIfLocationAvailable()) {

                    if (fusedLocation.getLastKnowLocation().latitude == 0 && fusedLocation.getLastKnowLocation().longitude == 0.0) {

                        LatLng latLng = new LatLng(14.651347, 121.029381); //santeh feeds west office.
                        moveCameraAnimate(map, latLng, 9);
                        insertloginlocation();
                        initMarkers();
                    }else{
                        moveCameraAnimate(map, fusedLocation.getLastKnowLocation(), 8);
                        insertloginlocation();
                        initMarkers();
                    }


                } else {
                    PD.hide();
                    curlat = 11.867145;
                    curLong = 122.756693;
                    zoom = 5;
                }
            }catch(Exception e){
                    Helper.toast.short_(activity, "Location is not available: "+e);
                    Log.e("Error", e.toString());
                }
            }
        }, 500);
    }


    private boolean isPackageInstalled(String packagename, Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
    private void closeAddingMarker() {
        if(mapcircle!=null){
            mapcircle.remove();
            mapcircle = null;
        }

        btn_cancelAddmarker.setVisibility(View.GONE);
        btn_add_marker.setEnabled(true);
        maps.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

            }
        });
    }

    private void getOwnerLocation() {
        double lat = 0, lng = 0;
        String farmidd;
        String[] splitted = clickedMarker.getTitle().split("#-#");
        if (!splitted[4].equalsIgnoreCase("") && !splitted[4].equalsIgnoreCase("null")) {
            lat = Double.parseDouble(splitted[4]);
        }

        if (!splitted[5].equalsIgnoreCase("") && !splitted[5].equalsIgnoreCase("null")) {
            lng = Double.parseDouble(splitted[5]);
        }

        farmidd = splitted[6];

        if (lat != 0 && lng != 0) {
            Helper.map.moveCameraAnimate(maps, new LatLng(lat, lng), 15);
            maps.clear();

            showAllCustomerLocation();
        } else {
            Helper.dialog.themedOkOnly(activity, "Oops", "Address of farm owner is currently not available. \n\nFarm ID: " + farmidd
                    , "OK");
        }
    }

    private void animateBottom(){
        Animation animatelayout;
        int visibility;

        if (isMarkerSelected){
            animatelayout  = AnimationUtils.loadAnimation(context, R.anim.bottom_up);
            animatelayout.setDuration(300);
            visibility = View.VISIBLE;

            btn_add_marker.setVisibility(View.GONE);

            hiddenPanel.startAnimation(animatelayout);
            hiddenPanel.setVisibility(visibility);

        }else{
            animatelayout  = AnimationUtils.loadAnimation(context, R.anim.bottom_down);
            animatelayout.setDuration(300);

            visibility = View.GONE;
            btn_add_marker.setVisibility(View.VISIBLE);

            if (hiddenPanel.getVisibility() == View.VISIBLE){
                hiddenPanel.startAnimation(animatelayout);
                hiddenPanel.setVisibility(visibility);
            }

        }


    }

    private void getunsynchedData() {
        if(Helper.variables.getGlobalVar_currentLevel(activity) == 4){
            int farmIsPostedCount =  db.getFarmInfo_notPosted_Count(activity),
                    custPostedCount = db.getCustInfo_notPosted_Count(activity),
                    pondPostedCount = db.getPond_notPosted_Count(activity),
                    weeklyPostedCount = db.getWeeklyPosted_notPosted_Count(activity),
                    harvestInfoCount = db.getHarvestPosted_notPosted_Count(activity),
                    sum = farmIsPostedCount + custPostedCount + pondPostedCount + weeklyPostedCount + harvestInfoCount;
            if (farmIsPostedCount > 0  || custPostedCount > 0 || pondPostedCount > 0  || weeklyPostedCount > 0 || harvestInfoCount > 0){
                txtViewTop.setText("You have (" + sum + ") unsynced data!");
                txtViewTop.setVisibility(View.VISIBLE);
                Animation anim = new AlphaAnimation(0.7f, 1.0f);
                anim.setDuration(800); //You can manage the blinking time with this parameter
                anim.setStartOffset(20);
                anim.setRepeatMode(Animation.REVERSE);
                anim.setRepeatCount(Animation.INFINITE);
                txtViewTop.startAnimation(anim);
            }else{
                txtViewTop.setVisibility(View.GONE);
            }
        }else{
            txtViewTop.setVisibility(View.GONE);
        }
    }


    private void initListeners(final GoogleMap map) {

        nav_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });


        nav_displayAllMarkers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapClear(map);
                showAllRelatedMarkers();
                closeDrawer();
                activeSelection = "farm";
//                if (popUp.isShowing()) {
//                    popUp.dismiss();
//                }
            }
        });


        nav_fingerlings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapClear(map);
                closeDrawer();

            }
        });

        nav_customerAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapClear(map);
                closeDrawer();
                activeSelection = "customer";
                showAllCustomerLocation();
//                if (popUp.isShowing()) {
//                    popUp.dismiss();
//                }
            }
        });

        nav_sperms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapClear(map);
                closeDrawer();
            }
        });

        nav_usermonitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(MapsActivity.this, Activity_UserMonitoring_ViewByUser.class);
                closeDrawer();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    }
                }, 280);
            }
        });


        nav_maptype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                closeDrawer();

                String[] maptypes = {"Normal", "Satellite", "Terrain", "Hybrid"};
                final Dialog dd = Helper.dialog.themedList(activity, maptypes, "Map Types", R.color.green_400);
                ListView lstMapType = (ListView) dd.findViewById(R.id.dialog_list_listview);
                dd.show();

                lstMapType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                            dd.hide();
                        } else if (position == 1) {
                            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                            dd.hide();
                        } else if (position == 2) {
                            map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                            dd.hide();
                        } else if (position == 3) {
                            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                            dd.hide();
                        }
                    }
                });
            }
        });
        btn_cancelAddmarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelMarkerAdding();
            }
        });


        btn_add_marker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setUpMap();
                fusedLocation.disconnectFromApiClient();
                if (Helper.map.isLocationEnabled(context)) {

                    fusedLocation.connectToApiClient();


                    if (fusedLocation.getLastKnowLocation().latitude == 0 && fusedLocation.getLastKnowLocation().longitude == 0.0) {

                        LatLng latLng = new LatLng(14.651347, 121.029381); //santeh feeds west office.
                        moveCameraAnimate(map, latLng, 9);
                        Helper.toast.short_(activity, "Current location is not available. Please try again later.");
                    } else {

                        final Handler handler = new Handler();
                        final Handler handler1 = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                curLatlng = fusedLocation.getLastKnowLocation();

                                moveCameraAnimate(map, new LatLng(curLatlng.latitude, curLatlng.longitude), 17);
                                handler1.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (mapcircle == null || !mapcircle.isVisible()) {
                                            circleOptions_addLocation = Helper.map.addCircle(activity, curLatlng, 1, R.color.skyblue_20,
                                                    R.color.skyblue_20, 1000);
                                            mapcircle = maps.addCircle(circleOptions_addLocation);
                                        }
                                        btn_cancelAddmarker.setVisibility(View.VISIBLE);
                                        Helper.dialog.themedOkOnly(activity, "Add Marker", "Long press desired location within the blue circle.", "OK");
                                    }
                                }, 1200);

                            }
                        }, 280);


                        if (btn_add_marker.isEnabled()) {
                            btn_add_marker.setEnabled(false);
                        }


                        maps.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                            @Override
                            public void onMapLongClick(LatLng latLng) {

                                final LatLng touchLocation = latLng;
                                LatLng center = fusedLocation.getLastKnowLocation();

                                float[] results = new float[1];
                                Location.distanceBetween(center.latitude, center.longitude,
                                        touchLocation.latitude, touchLocation.longitude, results);
//                        Helper.toastLong(activity, results[0]+"");

                                if (results[0] > 1000) {
                                    final Dialog d = Helper.dialog.themedOkOnly(activity, "Out of range", "Selection is out of 1km range", "OK");
                                    d.show();

                                    Button ok = (Button) d.findViewById(R.id.btn_dialog_okonly_OK);
                                    ok.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            d.hide();
                                        }
                                    });
                                } else {

                                    String[] options = {"Farm Information", "Customer Information"};
                                    final Dialog d1 = Helper.dialog.list_withPrompt(activity, options, "Add Marker",
                                            "Select the type of marker you want to add.", R.color.blue);
                                    ListView lvoptions = (ListView) d1.findViewById(R.id.dialog_list_listview);
                                    lvoptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            if (position == 0) {
                                                cancelMarkerAdding();
                                                d1.hide();
                                                final Intent intent = new Intent(MapsActivity.this, Activity_Add_FarmInformation.class);
                                                intent.putExtra("latitude", touchLocation.latitude);
                                                intent.putExtra("longtitude", touchLocation.longitude);
                                                startActivity(intent);
                                                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                            }

                                            if (position == 1) {
                                                d1.hide();
                                                cancelMarkerAdding();
                                                final Intent intent = new Intent(MapsActivity.this, Activity_Add_CustomerInformation_Basic.class);
                                                intent.putExtra("latitude", touchLocation.latitude);
                                                intent.putExtra("longtitude", touchLocation.longitude);
                                                startActivity(intent);
                                                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                            }
                                        }
                                    });

                                }
                            }
                        });

                    }


                } else {
                    Helper.map.isLocationAvailablePrompt(context, activity);
                }

            }
        });

        nav_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, Activity_Settings.class);
                closeDrawer();
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });


        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(final Marker marker) {

                clickedMarker = marker;
                final String[] details = marker.getTitle().split("#-#");
                markerdetails = details;

                if (activeSelection.equalsIgnoreCase("farm")) {
//
//                    String[] options = new String[]{"Case/Ponds", "Farm Details"};
//                    final Dialog d = Helper.themedList(activity, options, "Options", R.color.blue);
//                    ListView lv = (ListView) d.findViewById(R.id.dialog_list_listview);
//                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            LatLng location = marker.getPosition();
//                            if (position == 0) {
//
//                                d.hide();
////                                Intent intent = new Intent(MapsActivity.this, Activity_ManagePonds.class);
//                                gotoCaseAndPond(location, details);
//
//                            } else if (position == 1) {
//
//                                d.hide();
//                                gotoFarmDetails(location, details, marker);
//
//                            }
//                        }
//                    });
//
                } else if (activeSelection.equalsIgnoreCase("customer")) {

                    String[] options = new String[]{"Customer Details", "Owned Farms"};
                    final Dialog d = Helper.dialog.themedList(activity, options, "Options", R.color.blue);
                    ListView lv = (ListView) d.findViewById(R.id.dialog_list_listview);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 0) {
                                final Intent intent = new Intent(MapsActivity.this, Activity_CustomerDetails.class);
                                intent.putExtra("id", details[2]);
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivity(intent); overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                    }}, 100);
                                d.hide();
                            } else if (position == 1) {
                                getListOfFarms(details[0]);
                                d.hide();
                            }
                        }
                    });
                }

            }
        });

        nav_growout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Intent intent = new Intent(MapsActivity.this, Activity_WeeklyReports_Growout_FeedDemands.class);
                closeDrawer();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    }
                }, 280);

            }
        });

    }

    private void gotoFarmDetails(LatLng location, String[] details, Marker marker) {
        Intent intent = new Intent(MapsActivity.this, Activity_FarmInfo_Edit.class);
        intent.putExtra("lat", location.latitude + "");
        intent.putExtra("userid", Integer.parseInt(details[0]));
        intent.putExtra("long", location.longitude + "");
        intent.putExtra("contactName", details[7]);
        intent.putExtra("address", marker.getSnippet());
        intent.putExtra("farmname", details[1]);
        intent.putExtra("farmID", details[6]);
        intent.putExtra("contactnumber", details[8]);
        intent.putExtra("culturesystem", details[9]);
        intent.putExtra("levelofculture", details[10]);
        intent.putExtra("watertype", details[11]);
        intent.putExtra("isposted", Integer.parseInt(details[12]));

        intent.putExtra("fromActivity", "viewCustinfo");
        startActivity(intent);
    }

    private void gotoCaseAndPond(LatLng location, String[] details) {
        Intent intent = new Intent(MapsActivity.this, Activity_FarmViewOptions.class);
        intent.putExtra("id", Integer.parseInt(details[0]));
        intent.putExtra("farmname", "" + details[1]);
        intent.putExtra("latitude", location.latitude + "");
        intent.putExtra("longitude", location.longitude + "");
        startActivity(intent);
    }

    private void cancelMarkerAdding() {
        removeCircle();
        btn_cancelAddmarker.setVisibility(View.GONE);
        btn_add_marker.setEnabled(true);
        maps.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

            }
        });
    }


    private boolean checkIfLocationAvailable() {
        GPSTracker gpstracker = new GPSTracker(this);
        return gpstracker.getIsGPSTrackingEnabled();
    }

    private void moveCameraAnimate(final GoogleMap map, final LatLng latlng, final int zoom) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, zoom));
    }


    private void closeDrawer() {
        drawerLayout.closeDrawer(Gravity.LEFT);
    }

    private void openDrawer() {
        drawerLayout.openDrawer(Gravity.LEFT);
    }



    private void mapClear(GoogleMap map) {
        map.clear();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerListener.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerListener.onOptionsItemSelected(item)) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
         @Override
         public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);

    }

    private void ActionToggleDrawerListner() {
        drawerListener = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_menu_white_24dp,
                R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                isDrawerOpen = false;
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                isDrawerOpen = true;
            }
        };
    }


    @Override
    public void onLocationChanged(Location location) {
//        txtViewTop.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
    }


    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
//            txtViewTop.setText(mLastLocation.getLatitude() + " " + mLastLocation.getLongitude());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public void showAllRelatedMarkers() {
        PD.setMessage("Please wait...");
        PD.show();
        String url;
        if (passedintent != null){
            if (passedintent.hasExtra("fromActivity")){
                if (passedintent.getStringExtra("fromActivity").equalsIgnoreCase("login")
                        || passedintent.getStringExtra("fromActivity").equalsIgnoreCase("addfarminfo") ){
                    url  = Helper.variables.URL_SELECT_ALL_CUSTINFO_LEFTJOIN_PONDINFO;
                    activeSelection = "farm";
                    Log.d("URL", "login and farminfo");
                }else if (passedintent.getStringExtra("fromActivity").equalsIgnoreCase("addcustomerinfo")){
                    url  = Helper.variables.URL_SELECT_ALL_CUSTINFO_LEFTJOIN_PONDINFO;
                    Log.d("URL", "addcustomerinfo");
                    activeSelection = "customer";
                }else{
                    url  = Helper.variables.URL_SELECT_ALL_CUSTINFO_LEFTJOIN_PONDINFO;
                    Log.d("URL", "default");
                    activeSelection = "farm";
                }
            }else{
                    url  = Helper.variables.URL_SELECT_ALL_CUSTINFO_LEFTJOIN_PONDINFO;
                Log.d("URL", "fromActivity null");
            }
        }else{
                    url  = Helper.variables.URL_SELECT_ALL_CUSTINFO_LEFTJOIN_PONDINFO;
            Log.d("URL", "passed intent null");
        }


        //get current userlvl
        if (userlvl == 1 || userlvl == 2 || userlvl == 3 || userlvl == 0 ){ //if user is not TSR/Technician
            if (Helper.random.isNetworkAvailable(context)){ //if internet is availble
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(final String response) {
                                if (response.substring(1, 2).equalsIgnoreCase("0")) {
                                    PD.dismiss();
                                    updateDisplay();
                                } else {
                                    PD.dismiss();
                                    custInfoObjectList = CustAndPondParser.parseFeed(response);
                                    if (custInfoObjectList != null) {
                                        if (custInfoObjectList.size() > 0) {
                                            if (passedintent != null) {
                                                if (passedintent.hasExtra("fromActivity")) {
                                                    if (passedintent.getStringExtra("fromActivity").equalsIgnoreCase("login")) {
                                                        userid = extrass.getInt("userid");
                                                        userlevel = extrass.getInt("userlevel");
                                                        username = extrass.getString("username");
                                                        firstname = extrass.getString("firstname");
                                                        lastname = extrass.getString("lastname");
                                                        userdescription = extrass.getString("userdescription");
//                                                insertloginlocation();
                                                    }
                                                    activeSelection = "farm";
                                                    updateDisplay();
                                                } else { updateDisplay(); }
                                            } else { updateDisplay(); }
                                        } else {updateDisplay(); }
                                    } else {updateDisplay();}
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                PD.dismiss();
                                Helper.toast.short_(MapsActivity.this, "Something happened. Please try again later");
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("username", Helper.variables.getGlobalVar_currentUserName(activity));
                        params.put("password", Helper.variables.getGlobalVar_currentUserPassword(activity));
                        params.put("deviceid", Helper.deviceInfo.getMacAddress(context));
                        params.put("userid", Helper.variables.getGlobalVar_currentUserID(activity)+"");
                        params.put("userlvl", Helper.variables.getGlobalVar_currentLevel(activity)+"");

                        return params;
                    }
                };

                MyVolleyAPI api = new MyVolleyAPI();
                api.addToReqQueue(postRequest, MapsActivity.this);
            }else{
                Helper.toast.short_(MapsActivity.this, "No internet connection available. Please try again later.");
            }

        }else if(userlvl == 4) {//if user is tsr/technician... then query local database
            Log.d("DEBUG", "Before get Cursor from db");
            activeSelection = "farm";
            PD.dismiss();
            Cursor cur = db.getAll_FARMINFO_LEFTJOIN_PONDINFO_LEFTJOIN_CUSTOMERINFO(Helper.variables.getGlobalVar_currentUserID(activity) + "");
            getFarmPondCustFromDB(cur);
        }
    }

    private void getFarmPondCustFromDB(Cursor cur) {
        Log.d("DEBUG", "before cur not null");
        if(cur != null) {
            if(cur.getCount() > 0) {
                custInfoObjectList = new ArrayList<>();
                Log.d("DEBUG", "after new array list");
                while (cur.moveToNext()) {
                    CustInfoObject custInfoObject = new CustInfoObject();
                    /** FARM INFO **/
                    custInfoObject.setCi_id(cur.getInt(cur.getColumnIndex(GPSHelper.CL_FarmInfo_ID)));
                    custInfoObject.setLatitude(cur.getString(cur.getColumnIndex(GPSHelper.CL_FARMINFO_LAT)));
                    custInfoObject.setLongtitude(cur.getString(cur.getColumnIndex(GPSHelper.CL_FARMINFO_LNG)));
                    custInfoObject.setContact_name(cur.getString(cur.getColumnIndex(GPSHelper.CL_FARMINFO_CONTACT_NAME)));
                    custInfoObject.setCompany(cur.getString(cur.getColumnIndex(GPSHelper.CL_FARMINFO_COMPANY)));
                    custInfoObject.setAddress(cur.getString(cur.getColumnIndex(GPSHelper.CL_FARMINFO_FARM_ADDRESS)));
                    custInfoObject.setFarmname(cur.getString(cur.getColumnIndex(GPSHelper.CL_FARMINFO_FARM_NAME)));
                    custInfoObject.setCounter(0);
                    custInfoObject.setFarmID(cur.getString(cur.getColumnIndex(GPSHelper.CL_FARMINFO_FARM_ID)));
                    custInfoObject.setContact_number(cur.getString(cur.getColumnIndex(GPSHelper.CL_FARMINFO_C_NUMBER)));
                    custInfoObject.setCultureType(cur.getString(cur.getColumnIndex(GPSHelper.CL_FARMINFO_CULTYPE)));
                    custInfoObject.setCulturelevel(cur.getString(cur.getColumnIndex(GPSHelper.CL_FARMINFO_CULTlVL)));
                    custInfoObject.setWaterType(cur.getString(cur.getColumnIndex(GPSHelper.CL_FARMINFO_WATTYPE)));
                    custInfoObject.setDateAddedToDB(cur.getString(cur.getColumnIndex(GPSHelper.CL_FARMINFO_dateAdded)));
                    custInfoObject.setIsPosted_farm(cur.getInt(cur.getColumnIndex(GPSHelper.CL_FARMINFO_IsPosted)));
                    custInfoObject.setAllSpecie(cur.getString(cur.getColumnIndex("allSpecie"))); //(obj.getString("allSpecie"));

                    /** POND INFO **/
                    custInfoObject.setId(cur.getInt(cur.getColumnIndex(GPSHelper.CL_POND_INDEX)));
                    custInfoObject.setTotalStockOfFarm(cur.getInt(cur.getColumnIndex("Totalquantity")));//(obj.getInt("Totalquantity"));
                    custInfoObject.setSizeofStock(cur.getInt(cur.getColumnIndex(GPSHelper.CL_POND_sizeofStock)));
                    custInfoObject.setPondID(cur.getInt(cur.getColumnIndex(GPSHelper.CL_POND_PID)));
                    custInfoObject.setQuantity(cur.getInt(cur.getColumnIndex(GPSHelper.CL_POND_quantity)));
                    custInfoObject.setArea(cur.getInt(cur.getColumnIndex(GPSHelper.CL_POND_area)));
                    custInfoObject.setSpecie(cur.getString(cur.getColumnIndex(GPSHelper.CL_POND_specie)));
                    custInfoObject.setDateStocked(cur.getString(cur.getColumnIndex(GPSHelper.CL_POND_dateStocked)));
                    custInfoObject.setCulturesystem(cur.getString(cur.getColumnIndex(GPSHelper.CL_POND_culturesystem)));
                    custInfoObject.setRemarks(cur.getString(cur.getColumnIndex(GPSHelper.CL_POND_remarks)));
                    custInfoObject.setCustomerID(cur.getString(cur.getColumnIndex(GPSHelper.CL_POND_customerId)));
                    custInfoObject.setSurvivalrate_per_pond(cur.getString(cur.getColumnIndex(GPSHelper.CL_POND_survivalrate)));

                    /** ADDRESS **/
                    custInfoObject.setMainCustomerId(cur.getInt(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_ID))+"" );
                    custInfoObject.setLastname(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_LastName)));
                    custInfoObject.setFirstname(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_FirstName)));
                    custInfoObject.setMiddleName(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_MiddleName)));
                    custInfoObject.setCustfarmID(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_FarmId)));
                    custInfoObject.setStreet(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_Street)));
                    custInfoObject.setHouseNumber(cur.getInt(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_HouseNumber)));
                    custInfoObject.setSubdivision(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_Subdivision)));
                    custInfoObject.setBarangay(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_Barangay)));
                    custInfoObject.setCity(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_City)));
                    custInfoObject.setProvince(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_Province)));
                    custInfoObject.setBirthday(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_CBirthday)));
                    custInfoObject.setBirthPlace(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_CBirthPlace)));
                    custInfoObject.setTelephone(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_Telephone)));
                    custInfoObject.setCellphone(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_Cellphone)));
                    custInfoObject.setCivilStatus(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_CivilStatus)));
                    custInfoObject.setSpouse_lname(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_S_LastName)));
                    custInfoObject.setSpouse_fname(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_S_FirstName)));
                    custInfoObject.setSpouse_mname(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_S_MiddleName)));
                    custInfoObject.setSpouse_birthday(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_S_BirthDay)));
                    custInfoObject.setHouseStatus(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_HouseStatus)));
                    custInfoObject.setCust_longtitude(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_Longitude)));
                    custInfoObject.setCust_latitude(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_Latitude)));
                    custInfoObject.setDateAddedToDB(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_DateAdded)));
                    custInfoObject.setAddedBy(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_AddedBy)));

                    custInfoObjectList.add(custInfoObject);
                }

                Log.d("DEBUG", "before active selection"+ activeSelection);
                if (activeSelection.equalsIgnoreCase("farm")){
                    updateDisplay();
                }
                PD.dismiss();
            }else{
                PD.dismiss();
                prompt_noFarm();
            }
        }else{
            PD.dismiss();
            prompt_noFarm();
        }
    }


    public void showAllCustomerFarmByID(final String farmid) {


        if (userlvl == 1 || userlvl == 2 || userlvl == 3 || userlvl == 0){
            PD.setMessage("Please wait...");
            PD.show();
            StringRequest postRequest = new StringRequest(Request.Method.POST, Helper.variables.URL_SELECT_FARMINFO_LF_POND_AND_MCI_BY_FARMID,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String response) {

                            if (response.substring(1, 2).equalsIgnoreCase("0")) {
                                PD.dismiss();
                                updateDisplay();
                            } else {
                                PD.dismiss();
                                custInfoObjectList = CustAndPondParser.parseFeed(response);
                                if (custInfoObjectList != null) {
                                    if (custInfoObjectList.size() > 0) {

                                        maps.clear();
                                        activeSelection = "farm";
                                        maps.setInfoWindowAdapter(new FarmInfoWindow());

                                        for (int i = 0; i < custInfoObjectList.size(); i++) {
                                            final CustInfoObject ci;
                                            ci = custInfoObjectList.get(i);
                                            Log.d("MARKER", "ADDING FARM MARKER"+1);
                                            LatLng custLatlng = new LatLng(Double.parseDouble(ci.getLatitude() + ""), Double.parseDouble(ci.getLongtitude() + ""));
                                            Helper.map.map_addMarker(maps, custLatlng,
                                                    R.drawable.ic_place_red_24dp, ci.getFarmname(), ci.getAddress(), ci.getCi_id() + "", ci.getTotalStockOfFarm() + "",
                                                    ci.getAllSpecie() + "#-#" + ci.getCust_latitude() + "#-#" + ci.getCust_longtitude() + "#-#" + ci.getMainCustomerId());
                                        }
                                    }else {maps.clear();}
                                }else{maps.clear();}
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            PD.dismiss();
                            Helper.toast.short_(MapsActivity.this, "Something happened. Please try again later");
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", Helper.variables.getGlobalVar_currentUserName(activity));
                    params.put("password", Helper.variables.getGlobalVar_currentUserPassword(activity));
                    params.put("deviceid", Helper.deviceInfo.getMacAddress(context));
                    params.put("userid", Helper.variables.getGlobalVar_currentUserID(activity)+"");
                    params.put("userlvl", Helper.variables.getGlobalVar_currentLevel(activity)+"");
                    params.put("farmid", farmid+"");

//
                    return params;
                }
            };

            MyVolleyAPI api = new MyVolleyAPI();
            api.addToReqQueue(postRequest, MapsActivity.this);
        }else if( userlvl == 4) {
            PD.dismiss();
            updateDisplay();
        }

    }

    public void showAllCustomerLocation(){
        PD.setMessage("Please wait...");
        PD.show();
        String url = Helper.variables.URL_SELECT_CUST_LOCATION_BY_ASSIGNED_AREA;

        if (userlvl == 1 || userlvl == 2 || userlvl == 3 || userlvl == 0){
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String response) {

                            PD.dismiss();

                            if (!response.substring(1, 2).equalsIgnoreCase("0")) {
                                custInfoObjectList = CustAndPondParser.parseFeed(response);
                                showCustomerLocation();
                            } else {
                                prompt_noCustomerLocation();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            PD.dismiss();
                            Helper.toast.short_(MapsActivity.this,"Something happened. Please try again later");
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", Helper.variables.getGlobalVar_currentUserName(activity));
                    params.put("password", Helper.variables.getGlobalVar_currentUserPassword(activity));
                    params.put("deviceid", Helper.deviceInfo.getMacAddress(context));
                    params.put("userid", Helper.variables.getGlobalVar_currentUserID(activity)+"");
                    params.put("userlvl", Helper.variables.getGlobalVar_currentLevel(activity)+"");
                    return params;
                }
            };
            MyVolleyAPI api = new MyVolleyAPI();
            api.addToReqQueue(postRequest, MapsActivity.this);

        }else if(userlvl == 4) {
            Cursor cur = db.getCUST_LOCATION_BY_ASSIGNED_AREA(Helper.variables.getGlobalVar_currentUserID(activity)+"");
            if(cur != null) {
                if(cur.getCount() > 0) {
                    custInfoObjectList = new ArrayList<>();
                    while (cur.moveToNext()) {
                        CustInfoObject custInfoObject = new CustInfoObject();

                        custInfoObject.setMainCustomerId(cur.getInt(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_ID))+"" );
                        custInfoObject.setLastname(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_LastName)));
                        custInfoObject.setFirstname(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_FirstName)));
                        custInfoObject.setMiddleName(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_MiddleName)));
                        custInfoObject.setFarmID(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_FarmId)));
                        custInfoObject.setStreet(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_Street)));
                        custInfoObject.setHouseNumber(cur.getInt(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_HouseNumber)));
                        custInfoObject.setSubdivision(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_Subdivision)));
                        custInfoObject.setBarangay(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_Barangay)));
                        custInfoObject.setCity(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_City)));
                        custInfoObject.setProvince(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_Province)));
                        custInfoObject.setBirthday(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_CBirthday)));
                        custInfoObject.setBirthPlace(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_CBirthPlace)));
                        custInfoObject.setTelephone(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_Telephone)));
                        custInfoObject.setCellphone(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_Cellphone)));
                        custInfoObject.setCivilStatus(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_CivilStatus)));
                        custInfoObject.setSpouse_lname(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_S_LastName)));
                        custInfoObject.setSpouse_fname(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_S_FirstName)));
                        custInfoObject.setSpouse_mname(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_S_MiddleName)));
                        custInfoObject.setSpouse_birthday(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_S_BirthDay)));
                        custInfoObject.setHouseStatus(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_HouseStatus)));
                        custInfoObject.setCust_longtitude(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_Longitude)));
                        custInfoObject.setCust_latitude(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_Latitude)));
                        custInfoObject.setDateAddedToDB(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_DateAdded)));
                        custInfoObject.setAddedBy(cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_AddedBy)));
                        custInfoObject.setIsPosted_cust(cur.getInt(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_isposted)));

                        custInfoObjectList.add(custInfoObject);
                    }
                    showCustomerLocation();
                    PD.dismiss();
                }else{
                    PD.dismiss();
                    prompt_noCustomerLocation();
                }
            }else{
                PD.dismiss();
                prompt_noCustomerLocation();
            }
        }

    }

    private void showCustomerLocation() {
        if (custInfoObjectList!=null){
            if (custInfoObjectList.size() > 0){
                for (int i = 0; i < custInfoObjectList.size(); i++) {

                    String address = custInfoObjectList.get(i).getHouseNumber()+"";

                    if(!custInfoObjectList.get(i).getStreet().equalsIgnoreCase(" --- ")){
                        address = address + " " + custInfoObjectList.get(i).getStreet();
                    }
                    if(!custInfoObjectList.get(i).getSubdivision().equalsIgnoreCase(" --- ")){
                        address = address + ", " + custInfoObjectList.get(i).getSubdivision();
                    }
                    address = address + "" + ", " + custInfoObjectList.get(i).getBarangay() + ", " + custInfoObjectList.get(i).getCity() + ", " + custInfoObjectList.get(i).getProvince();

                    maps.setInfoWindowAdapter(new CustomerInfoWindow());
                    Log.d("DEBUG", "SHOW all custlocation - Active selection customer");
                    activeSelection = "customer";
                    Helper.map.map_addMarker(maps, new LatLng(Double.parseDouble(custInfoObjectList.get(i).getCust_latitude()), Double.parseDouble(custInfoObjectList.get(i).getCust_longtitude())),
                            R.drawable.ic_housemarker_24dp,
                            custInfoObjectList.get(i).getFirstname() + " " + custInfoObjectList.get(i).getLastname(), //Firstname and LastName
                            address, custInfoObjectList.get(i).getFarmID(), custInfoObjectList.get(i).getMainCustomerId(), "0");
                }
            }else{ prompt_noCustomerLocation();}
        }else{ prompt_noCustomerLocation();}
    }

    private void prompt_noCustomerLocation() {
        Helper.dialog.themedOkOnly(activity, "Warning", "You have not added any customer address", "OK");
    }

    public void getListOfFarms(final String farmid){
        PD.setMessage("Please wait...");
        PD.show();
        String url = Helper.variables.URL_SELECT_FARM_BY_FARMID;
        if (userlvl == 1 || userlvl == 2 || userlvl == 3 || userlvl == 0){
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String response) {
                            PD.dismiss();
                            if (!response.substring(1, 2).equalsIgnoreCase("0")) {
                                custInfoObjectList = CustAndPondParser.parseFeed(response);
                                showAllCustomerFarmByFarmID();
                            } else {
                                Helper.dialog.themedOkOnly(activity, "Warning", "No farm related to selected customer. Please check Farm ID", "OK");}
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            PD.dismiss();
                            Helper.toast.short_(MapsActivity.this,"Something happened. Please try again later");
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", Helper.variables.getGlobalVar_currentUserName(activity));
                    params.put("password", Helper.variables.getGlobalVar_currentUserPassword(activity));
                    params.put("deviceid", Helper.deviceInfo.getMacAddress(context));
                    params.put("userid", Helper.variables.getGlobalVar_currentUserID(activity)+"");
                    params.put("userlvl", Helper.variables.getGlobalVar_currentLevel(activity)+"");
                    params.put("farmid", farmid+"");
//
                    return params;
                }
            };

            MyVolleyAPI api = new MyVolleyAPI();
            api.addToReqQueue(postRequest, MapsActivity.this);
        }else if( userlvl == 4) {
            Cursor cur = db.getFARM_POND_CUSTOMER_BY_FARMID(Helper.variables.getGlobalVar_currentUserID(activity)+"", farmid);
            activeSelection = "customer";
            if (cur.getCount() > 0){
                PD.dismiss();
                getFarmPondCustFromDB(cur);
                Log.d("SHOW MARKER", "showAllCustomerFarmByFarmID");
                showAllCustomerFarmByFarmID();
            }else{
                PD.dismiss();
                prompt_noFarm();
            }

        }

    }

    private void showAllCustomerFarmByFarmID() {
        if (custInfoObjectList!=null){
            if (custInfoObjectList.size() > 0) {
                Log.d("DEBUG", "Show all custinfo by farmID - active selection customer");
                activeSelection = "customer";
                String farmnames[] = new String[custInfoObjectList.size()];
                for (int i = 0; i < custInfoObjectList.size(); i++) {
                    farmnames[i] = custInfoObjectList.get(i).getFarmname();
                }

                final Dialog d = Helper.dialog.themedList(activity, farmnames, "Farm(s)", R.color.darkgreen_800);
                d.show();
                ListView lv = (ListView) d.findViewById(R.id.dialog_list_listview);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        maps.setInfoWindowAdapter(new FarmInfoWindow());
                        double lat = Double.parseDouble(custInfoObjectList.get(position).getLatitude() + "");
                        double lng = Double.parseDouble(custInfoObjectList.get(position).getLongtitude() + "");
                        LatLng latLng = new LatLng(lat, lng);
//                                            Helper.toastShort(activity, custInfoObjectList.get(position).getFarmID() + " " + custInfoObjectList.get(position).getLongtitude() + " " + custInfoObjectList.get(position).getLatitude());
                        Helper.map.moveCameraAnimate(maps, latLng, 15);
                        maps.clear();
                        activeSelection = "farm";
                        showAllCustomerFarmByID(custInfoObjectList.get(position).getFarmID());
                        d.hide();
                    }
                });
            }else{
                Helper.dialog.themedOkOnly(activity, "Warning", "No farm related to selected customer. Please check Farm ID", "OK");}
        }else{ Helper.dialog.themedOkOnly(activity, "Warning", "No farm related to selected customer. Please check Farm ID", "OK");}
    }


    private void insertloginlocation(){
        if (Helper.isnull.isIntentKeywordNotNull("fromActivity", passedintent)){
          if (extrass.getString("fromActivity").equalsIgnoreCase("login")) {
              Log.d("EXTRAS", "fromactivity = login");

              userid = extrass.getInt("userid");
              userlevel = extrass.getInt("userlevel");
              username = extrass.getString("username");
              firstname = extrass.getString("firstname");
              lastname = extrass.getString("lastname");
              userdescription = extrass.getString("userdescription");

              if (Logging.logUserAction(activity, context, Helper.userActions.TSR.USER_LOGIN, Helper.variables.ACTIVITY_LOG_TYPE_TSR_MONITORING)){
                  Helper.toast.short_(activity, "Location found :) ");
                  passedintent = null;
              }
          }

        }

    }



    protected void updateDisplay() {
        if (custInfoObjectList != null) {
            if (custInfoObjectList.size() > 0) {
                for (int i = 0; i < custInfoObjectList.size(); i++) {
                    final CustInfoObject ci;
                    ci = custInfoObjectList.get(i);
                    maps.setInfoWindowAdapter(new FarmInfoWindow());
                    LatLng custLatlng = new LatLng(Double.parseDouble(ci.getLatitude() + ""), Double.parseDouble(ci.getLongtitude() + ""));
                    Helper.map.map_addMarker(maps, custLatlng,
                            R.drawable.ic_place_red_24dp, ci.getFarmname(), ci.getAddress(), ci.getCi_id() + "", ci.getTotalStockOfFarm() + "",
                            ci.getAllSpecie() + "#-#" + ci.getCust_latitude() + "#-#" + ci.getCust_longtitude() + "#-#" + ci.getFarmID() + "#-#"
                                    + ci.getContact_name() + "#-#" + ci.getContact_number() + "#-#" + ci.getCultureType() + "#-#" + ci.getCulturelevel() + "#-#" + ci.getWaterType() + "#-#" + ci.getIsPosted_farm());
                }
            } else {prompt_noFarm();}
        } else {prompt_noFarm(); }
    }

    private void prompt_noFarm() {
        final Dialog d = Helper.dialog.themedOkOnly(activity, "Santeh CRM", "You have not added a farm/marker yet. \nStart by pressing the  plus '+' on the upper right side of the screen.", "OK");
        Button ok = (Button) d.findViewById(R.id.btn_dialog_okonly_OK);
        d.show();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.hide();
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        db.close();
        Log.d("PROCESS", "Onpause");
    }


    //class for infoWindow
    class FarmInfoWindow implements GoogleMap.InfoWindowAdapter {
        private final View myContentsView;
        FarmInfoWindow() {
            myContentsView = getLayoutInflater().inflate(R.layout.infowindow_farminfo, null);
        }
        @Override
        public View getInfoWindow(Marker marker) {
            TextView tvTitle = ((TextView) myContentsView.findViewById(R.id.title));
            TextView tvSnippet = ((TextView) myContentsView.findViewById(R.id.addres));
            TextView txtStock = ((TextView) myContentsView.findViewById(R.id.stocks));
            TextView txtSpecie = ((TextView) myContentsView.findViewById(R.id.specie));
            String[] details = marker.getTitle().split("#-#");

            tvTitle.setText(details[1]);

            if (details[2].equalsIgnoreCase("") || details[2].equalsIgnoreCase("null")){
                txtStock.setText("n/a");
            } else{txtStock.setText("" + details[2]);}

            if (details[3].equalsIgnoreCase("") || details[3].equalsIgnoreCase("null")){
                txtSpecie.setText("n/a"); } else{ txtSpecie.setText("" + details[3]); }
            tvSnippet.setText(marker.getSnippet());

            return myContentsView;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }
    }


    class CustomerInfoWindow implements GoogleMap.InfoWindowAdapter {
        private final View myContentsView;
        CustomerInfoWindow() {
            myContentsView = getLayoutInflater().inflate(R.layout.infowindow_customer_address, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {
            TextView tvSnippet = ((TextView) myContentsView.findViewById(R.id.address));
            TextView tvTitle = ((TextView) myContentsView.findViewById(R.id.title));
            String[] details = marker.getTitle().split("#-#");

            tvSnippet.setText(marker.getSnippet());
            tvTitle.setText(details[1]+"");
            return myContentsView;
        }


        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        fusedLocation.disconnectFromApiClient();
        fusedLocation.connectToApiClient();
        db.open();
        if(activeFilter==0){
            activeFilter = 0;
        }

        if (Helper.isnull.isGlobalUserIDNull(activity)) {
            Dialog d = Helper.dialog.themedOkOnly(activity, "Session Expired",
                    "It seems that you have been inactive for too long. Please log in again", "OK");
            d.setCancelable(false);
            Button ok = (Button) d.findViewById(R.id.btn_dialog_okonly_OK);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Helper.activityActions.startActivityClearStack(activity, Activity_LoginScreen.class);
                }
            });
        }

        getunsynchedData();

    }

    @Override
    public void onBackPressed() {
        exitApp();
    }

    private void exitApp() {
        final Dialog d = Helper.dialog.themedYesNo(activity, "Close the app? You will have to login next time.", "Close", "YES", "NO", R.color.blue);
        d.show();
        Button yes = (Button) d.findViewById(R.id.btn_dialog_yesno_opt1);
        Button no = (Button) d.findViewById(R.id.btn_dialog_yesno_opt2);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.hide();
                finishAffinity();
                Intent setIntent = new Intent(Intent.ACTION_MAIN);
                setIntent.addCategory(Intent.CATEGORY_HOME);
                setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(setIntent);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.hide();
            }
        });
    }


    private void logout() {
        final Dialog d = Helper.dialog.themedYesNo(activity, "Logout this account?", "Log Out", "YES", "NO", R.color.blue);
        d.show();
        Button yes = (Button) d.findViewById(R.id.btn_dialog_yesno_opt1);
        Button no = (Button) d.findViewById(R.id.btn_dialog_yesno_opt2);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.hide();
                closeDrawer();
                final Dialog d2 = Helper.dialog.initProgressDialog(activity);
                d2.show();

                TextView message = (TextView) d2.findViewById(R.id.progress_message);
                message.setText("Logging out...");

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        d2.hide();
                        logoff();
                    }
                }, 1500);


            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.hide();
            }
        });
    }

    public void logoff(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                finish();
            }
        }, 300);
    }

    private void removeCircle(){
        if(mapcircle!=null){
            mapcircle.remove();
            mapcircle = null;
        }

    }



    private void startSynchingDB_FARMINFO() {
        if (db.getFarmInfo_notPosted_Count(activity) > 0) {
            StringRequest postRequest = new StringRequest(Request.Method.POST, Helper.variables.URL_PHP_RAW_QUERY_POST_INSERT,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String response) {
                            if (!response.substring(1, 2).equalsIgnoreCase("0")) {
                                db.updateUnPostedToPosted_FARM();
                                startSynchingDB_CUSTINFO();
                                Log.d("SYNC", "SYNC OK - FARM.");
                            } else {
                                Log.d("SYNC", "SYNC FAILED - FARM."+ response);
                                strikes = strikes + 1;
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Helper.dialog.themedOkOnly(activity, "Error", error.toString(), "OK");
                            getunsynchedData();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", Helper.variables.getGlobalVar_currentUserName(activity));
                    params.put("password", Helper.variables.getGlobalVar_currentUserPassword(activity));
                    params.put("deviceid", Helper.deviceInfo.getMacAddress(context));
                    params.put("userid", Helper.variables.getGlobalVar_currentUserID(activity) + "");
                    params.put("userlvl", Helper.variables.getGlobalVar_currentLevel(activity) + "");


                    params.put("sql", db.getSQLStringForInsert_UNPOSTED_FARMINFO(activity) + "");
                    return params;
                }
            };

            MyVolleyAPI api = new MyVolleyAPI();
            api.addToReqQueue(postRequest, context);
        }else{
            startSynchingDB_CUSTINFO();
        }


    }


    private void startSynchingDB_CUSTINFO() {

        if (db.getCustInfo_notPosted_Count(activity) > 0) {
            StringRequest postRequest = new StringRequest(Request.Method.POST, Helper.variables.URL_PHP_RAW_QUERY_POST_INSERT,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String response) {
                            if (!response.substring(1, 2).equalsIgnoreCase("0")) {
                                startSynchingDB_PondInfo();
                                db.updateUnPostedToPosted_Cust();
                                Log.d("SYNC", "SYNC OK - CUST.");
                            } else {
                                Log.d("SYNC", "SYNC FAILED - CUST."+ response);
                                strikes = strikes + 1;
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Helper.dialog.themedOkOnly(activity, "Error", error.toString(), "OK");
                            getunsynchedData();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", Helper.variables.getGlobalVar_currentUserName(activity));
                    params.put("password", Helper.variables.getGlobalVar_currentUserPassword(activity));
                    params.put("deviceid", Helper.deviceInfo.getMacAddress(context));
                    params.put("userid", Helper.variables.getGlobalVar_currentUserID(activity) + "");
                    params.put("userlvl", Helper.variables.getGlobalVar_currentLevel(activity) + "");

                    params.put("sql", db.getSQLStringForInsert_UNPOSTED_CustomerINFO(activity) + "");

                    return params;
                }
            };

            MyVolleyAPI api = new MyVolleyAPI();
            api.addToReqQueue(postRequest, context);

        }else {
            startSynchingDB_PondInfo();
        }

    }

    private void startSynchingDB_PondInfo() {

        if (db.getPond_notPosted_Count(activity) > 0) {
            StringRequest postRequest = new StringRequest(Request.Method.POST, Helper.variables.URL_PHP_RAW_QUERY_POST_INSERT,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String response) {
                            if (!response.substring(1, 2).equalsIgnoreCase("0")) {
                                db.updateUnPostedToPosted_POND();
                                startSynchingDB_weeklyPondReport();
                                Log.d("SYNC", "SYNC OK - POND.");
                            } else {
                                Log.d("SYNC", "SYNC FAILED - POND."+ response);
                                strikes = strikes + 1;
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Helper.dialog.themedOkOnly(activity, "Error", error.toString(), "OK");
                            getunsynchedData();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", Helper.variables.getGlobalVar_currentUserName(activity));
                    params.put("password", Helper.variables.getGlobalVar_currentUserPassword(activity));
                    params.put("deviceid", Helper.deviceInfo.getMacAddress(context));
                    params.put("userid", Helper.variables.getGlobalVar_currentUserID(activity) + "");
                    params.put("userlvl", Helper.variables.getGlobalVar_currentLevel(activity) + "");
                    params.put("sql", db.getSQLStringForInsert_UNPOSTED_POND(activity) + "");

                    Log.d("SQL_STRING", db.getSQLStringForInsert_UNPOSTED_POND(activity));

                    return params;
                }
            };

            MyVolleyAPI api = new MyVolleyAPI();
            api.addToReqQueue(postRequest, context);
        }else{
            startSynchingDB_weeklyPondReport();
        }
    }


    private void startSynchingDB_weeklyPondReport() {

        if (db.getWeeklyPosted_notPosted_Count(activity) > 0) {
            StringRequest postRequest = new StringRequest(Request.Method.POST, Helper.variables.URL_PHP_RAW_QUERY_POST_INSERT,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String response) {
                            if (!response.substring(1, 2).equalsIgnoreCase("0")) {
                                db.updateUnPostedToPosted_WEEKLY();
                                startSynchingDB_HarvestInfo();
                            } else {
                                strikes = strikes + 1;
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Helper.dialog.themedOkOnly(activity, "Error", error.toString(), "OK");
                            getunsynchedData();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", Helper.variables.getGlobalVar_currentUserName(activity));
                    params.put("password", Helper.variables.getGlobalVar_currentUserPassword(activity));
                    params.put("deviceid", Helper.deviceInfo.getMacAddress(context));
                    params.put("userid", Helper.variables.getGlobalVar_currentUserID(activity) + "");
                    params.put("userlvl", Helper.variables.getGlobalVar_currentLevel(activity) + "");
                    params.put("sql", db.getSQLStringForInsert_UNPOSTED_WEEKLY() + "");

                    Log.d("SQL_STRING", db.getSQLStringForInsert_UNPOSTED_WEEKLY());

                    return params;
                }
            };

            MyVolleyAPI api = new MyVolleyAPI();
            api.addToReqQueue(postRequest, context);
        }else{
           startSynchingDB_HarvestInfo();
        }


    }

    private void finishSync() {
        txtViewTop.setVisibility(View.GONE);
        txtViewTop.clearAnimation();
        Log.d("SYNC", "SYNC OK - WEEK.");
        sync_userActivities1();
        Helper.toast.short_(activity, "SYNC FINISHED.");
    }


    private void startSynchingDB_HarvestInfo() {

        if (db.getHarvestInfo_notPosted_Count(activity) > 0) {
            StringRequest postRequest = new StringRequest(Request.Method.POST, Helper.variables.URL_PHP_RAW_QUERY_POST_INSERT,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String response) {
                            if (!response.substring(1, 2).equalsIgnoreCase("0")) {
                                db.updateUnPostedToPosted_HarvestInfo();
                                finishSync();
                            } else {
                                strikes = strikes + 1;
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Helper.dialog.themedOkOnly(activity, "Error", error.toString(), "OK");
                            getunsynchedData();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", Helper.variables.getGlobalVar_currentUserName(activity));
                    params.put("password", Helper.variables.getGlobalVar_currentUserPassword(activity));
                    params.put("deviceid", Helper.deviceInfo.getMacAddress(context));
                    params.put("userid", Helper.variables.getGlobalVar_currentUserID(activity) + "");
                    params.put("userlvl", Helper.variables.getGlobalVar_currentLevel(activity) + "");
                    params.put("sql", db.getSQLStringForInsert_UNPOSTED_HARVESTINFO() + "");

                    Log.d("SQL_STRING", db.getSQLStringForInsert_UNPOSTED_HARVESTINFO() + "");

                    return params;
                }
            };

            MyVolleyAPI api = new MyVolleyAPI();
            api.addToReqQueue(postRequest, context);

        }else{
            finish();
        }


    }

    private void sync_userActivities1() {
        //        Helper.themedOkOnly_scrolling(activity, "SQL STRING", db.getSQLStringForInsert_UNPOSTED_USERACTIVITY(), "OK", R.color.red);
        if (db.getUserActivity_notPosted_Count(activity) > 0) {
            StringRequest postRequest = new StringRequest(Request.Method.POST, Helper.variables.URL_PHP_RAW_QUERY_POST_INSERT,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String response) {
                            if (!response.substring(1, 2).equalsIgnoreCase("0")) {
                                db.updateUnPostedToPosted_USERACTIVITY();
                            } else {
                                Log.d("SYNC", "SYNC INTERRUPTED. Please try syncing again. USERS");
                            }
                            Log.d("SYNC", ""+response);

                            if (strikes > 0){
                                Helper.toast.short_(activity, "SYNC FAILED. Please try syncing again.");
                                strikes = 0;
                            }else{
                                strikes = 0;
                                Helper.toast.short_(activity, "SYNC FINISHED.");
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Helper.dialog.themedOkOnly(activity, "Error", error.toString(), "OK");
                            getunsynchedData();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", Helper.variables.getGlobalVar_currentUserName(activity)+"");
                    params.put("password", Helper.variables.getGlobalVar_currentUserPassword(activity)+"");
                    params.put("deviceid", Helper.deviceInfo.getMacAddress(context)+"");
                    params.put("userid", Helper.variables.getGlobalVar_currentUserID(activity) + "");
                    params.put("userlvl", Helper.variables.getGlobalVar_currentLevel(activity) + "");
                    params.put("sql", db.getSQLStringForInsert_UNPOSTED_USERACTIVITY() + "");

                    Log.d("SYNC SQL_STRING", db.getSQLStringForInsert_UNPOSTED_USERACTIVITY());
                    return params;
                }
            };

            MyVolleyAPI api = new MyVolleyAPI();
            api.addToReqQueue(postRequest, context);
        }else{
//                        Helper.toastShort(activity, "SYNC FINISHED. USERS ");
        }
    }

}