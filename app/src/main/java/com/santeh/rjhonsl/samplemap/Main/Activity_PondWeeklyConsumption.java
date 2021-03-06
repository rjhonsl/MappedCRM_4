package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.model.LatLng;
import com.santeh.rjhonsl.samplemap.APIs.MyVolleyAPI;
import com.santeh.rjhonsl.samplemap.Adapter.Adapter_Growouts_PondWeekLyConsumption;
import com.santeh.rjhonsl.samplemap.DBase.GPSHelper;
import com.santeh.rjhonsl.samplemap.DBase.GPSQuery;
import com.santeh.rjhonsl.samplemap.Obj.CustInfoObject;
import com.santeh.rjhonsl.samplemap.Parsers.PondWeeklyUpdateParser;
import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.FusedLocation;
import com.santeh.rjhonsl.samplemap.Utils.Helper;
import com.santeh.rjhonsl.samplemap.Utils.Logging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rjhonsl on 8/25/2015.
 */
public class Activity_PondWeeklyConsumption extends Activity {

    Adapter_Growouts_PondWeekLyConsumption adapterPondWeeklyReport;

    String farmName;
    int id;

    static Activity activity;
    Context context;

    ProgressDialog PD;

    TextView txtheadr, txtdateStocked, txtQuantity;

    List<CustInfoObject> pondweeklyList;
    List<CustInfoObject> finalWeekList;

    ImageButton btn_details, btn_addreport;

    String strrecommended = "", strRemarks = "", strFeedtype="";
    int strweeknum, strabw, strSurvivalRate;

    ListView lvPonds;
    String [] pondListArray;
    private int pondid;
    private int abw;
    private String survivalrate;
    private int area;
    private int quantity;
    private String specie;
    private String datestocked;
    private String culturesystem;
    private String remarks;
    int startWeek, currentweek;

    GPSQuery db;
    private String latitude;
    private String longitude;
    private FusedLocation fusedLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_growout_weeklyreport);
        activity = this;
        context = Activity_PondWeeklyConsumption.this;
        db = new GPSQuery(this);
        db.open();

        PD = new ProgressDialog(this);
        PD.setMessage("Updating database. Please wait....");
        PD.setCancelable(false);

        initViewsFromXML();
        fusedLocation = new FusedLocation(context, activity);
        fusedLocation.connectToApiClient();


        ImageButton btn_title_back = (ImageButton) findViewById(R.id.title_back);
        btn_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (getIntent() != null){
            if (getIntent().hasExtra("pondid")){ pondid = getIntent().getIntExtra("pondid",0); }
            if (getIntent().hasExtra("id")){id = getIntent().getIntExtra("id",0);}
            if (getIntent().hasExtra("abw")){ abw = getIntent().getIntExtra("abw",0);}
            if (getIntent().hasExtra("survivalrate")){survivalrate = getIntent().getStringExtra("survivalrate");}
            if (getIntent().hasExtra("area")){ area = getIntent().getIntExtra("area", 0);}
            if (getIntent().hasExtra("quantity")){ quantity = getIntent().getIntExtra("quantity", 0);}
            if (getIntent().hasExtra("specie")){ specie = getIntent().getStringExtra("specie");}
            if (getIntent().hasExtra("datestocked")){ datestocked = getIntent().getStringExtra("datestocked");}
            if (getIntent().hasExtra("culturesystem")){culturesystem = getIntent().getStringExtra("culturesystem");}
            if (getIntent().hasExtra("remarks")){ remarks = getIntent().getStringExtra("remarks");}
            if (getIntent().hasExtra("remarks")){ latitude = getIntent().getStringExtra("latitude");}
            if (getIntent().hasExtra("remarks")){ longitude = getIntent().getStringExtra("longitude");}

            getpondData(id, Helper.variables.URL_SELECT_POND_WEEKLY_UPDATES_BY_ID);
        }


        Bundle extras = getIntent().getExtras();
        if (extras !=null){
            farmName = extras.getString("farmname");
        }

        txtheadr.setText("Pond " + pondid + " - " + specie);
        txtQuantity.setText("Quantity: " + quantity);
        txtdateStocked.setText("Date Stocked: " + datestocked);

        btn_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog d = new Dialog(activity);//
                d.requestWindowFeature(Window.FEATURE_NO_TITLE); //notitle
                d.setContentView(R.layout.dialog_material_themed_ponddetails);//Set the xml view of the dialog
                Button btnOK1 = (Button) d.findViewById(R.id.btnOK);
                TextView txtpondNum = (TextView) d.findViewById(R.id.txtPondNum);
                TextView txtSpecie = (TextView) d.findViewById(R.id.txtSpecies);
                TextView txtqty = (TextView) d.findViewById(R.id.txtQuantity);
                TextView txtabw = (TextView) d.findViewById(R.id.txtABW);
                TextView txtSurvivalRate = (TextView) d.findViewById(R.id.txtSurvivalRate);
                TextView txtDateStocked = (TextView) d.findViewById(R.id.txtDateStocked);
                TextView txtArea = (TextView) d.findViewById(R.id.txtArea);
                TextView txtCultureSystem = (TextView) d.findViewById(R.id.txtCultureSystem);

                txtpondNum.setText(pondid + "");
                txtSpecie.setText(specie + "");
                txtqty.setText(quantity + "");
                txtabw.setText(abw + "g");
                txtSurvivalRate.setText(strSurvivalRate + "%");
                txtDateStocked.setText(datestocked + "");
                txtArea.setText(area + "m²");
                txtCultureSystem.setText(culturesystem + "");

                btnOK1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.hide();
                    }
                });
                btnOK1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.hide();
                    }
                });
                btnOK1.setText("OK");
                d.show();
            }
        });

        btn_addreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LatLng currentloc = fusedLocation.getLastKnowLocation();
                final LatLng farmlocat = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        float[] results = new float[1];
                        Location.distanceBetween(farmlocat.latitude, farmlocat.longitude,
                                currentloc.latitude, currentloc.longitude, results);
//                        Helper.toastLong(activity, results[0]+"");

                        if (results[0] > 1000) {
                            final Dialog d = Helper.dialog.themedOkOnly(activity, "Out of range", "You must be near the farm to Add a new pond.", "OK");
                        } else {
                            final Dialog d = new Dialog(activity);//
                            d.requestWindowFeature(Window.FEATURE_NO_TITLE); //notitle
                            d.setContentView(R.layout.dialog_material_themed_addpondreport);//Set the xml view of the dialog
                            Button add = (Button) d.findViewById(R.id.btnAdd);
                            Button cancel = (Button) d.findViewById(R.id.btnCancel);
                            final EditText edtAbw = (EditText) d.findViewById(R.id.edtAbw);
                            final EditText edtSurvivalRate = (EditText) d.findViewById(R.id.edtSurvivalRate);
                            final EditText edtRemarks = (EditText) d.findViewById(R.id.edtRemarks);
                            if (strabw == 0) {
                                edtAbw.setText("" + abw);
                            } else {
                                edtAbw.setText("" + strabw);
                            }

                            if (strSurvivalRate == 0) {
                                edtSurvivalRate.setText("" + survivalrate);
                            } else {
                                edtSurvivalRate.setText("" + strSurvivalRate);
                            }

                            d.show();
                            add.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (!edtAbw.getText().toString().equalsIgnoreCase("") || !edtRemarks.getText().toString().equalsIgnoreCase("")) {
                                        d.hide();
                                        AddReport(edtAbw.getText().toString(), Helper.variables.URL_INSERT_POND_REPORT, edtRemarks.getText().toString(), edtSurvivalRate.getText().toString());
                                    } else {
                                        Helper.toast.short_(activity, "You have to complete all fields to continue");
                                    }

                                }
                            });

                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    d.hide();
                                }
                            });
                        }

                    }
                }, 280);

            }
        });



        lvPonds.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                pondweeklyList
                Helper.dialog.themedOkOnly(activity, "Details",
                        "ABW: "+pondweeklyList.get(position).getSizeofStock()+"g\n\n" +
                                "Survival Rate: " + pondweeklyList.get(position).getSurvivalrate_per_pond()+"%\n\n"+
                                "Date reported: \n" + pondweeklyList.get(position).getDateAddedToDB()+""
                        , "OK");
            }
        });




        lvPonds.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                if (Helper.variables.getGlobalVar_currentLevel(activity) == 4){
                    if (position==pondweeklyList.size()-1) {
                        Helper.dialog.themedOkOnly(activity, "Warning", "You cannot Edit or Delete Initial Stocking Data", "OK");
                    }else {
                       if (pondweeklyList.get(position).getIsPosted_weekly() == 1) {
                           Helper.dialog.themedOkOnly(activity, "Oops", "Item is already posted on server. Please contact admin for further changes.", "OK");
                       }else{
                           String[] options = {"Edit ABW and Remarks", "Delete"};
                           final Dialog d = Helper.dialog.themedList(activity, options, "Options ", R.color.deepteal_400);
                           d.show();

                           ListView lvOptions = (ListView) d.findViewById(R.id.dialog_list_listview);
                           lvOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                               @Override
                               public void onItemClick(AdapterView<?> parent, View view, int position1, long id) {
                                   if (position1 == 0 ){

                                       d.hide();

                                       final Dialog d2 = new Dialog(activity);//
                                       d2.requestWindowFeature(Window.FEATURE_NO_TITLE); //notitle
                                       d2.setContentView(R.layout.dialog_material_themed_addpondreport);//Set the xml view of the dialog
                                       Button add = (Button) d2.findViewById(R.id.btnAdd);
                                       TextView txttitle = (TextView) d2.findViewById(R.id.txtTitle);
                                       txttitle.setText("Edit Week Details ");
                                       add.setText("UPDATE");

                                       Button cancel = (Button) d2.findViewById(R.id.btnCancel);
                                       final EditText edtAbw = (EditText) d2.findViewById(R.id.edtAbw);
                                       final EditText edtRemarks = (EditText) d2.findViewById(R.id.edtRemarks);
                                       final EditText editSurvivalRate = (EditText) d2.findViewById(R.id.edtSurvivalRate);

                                       edtAbw.setText(""+ pondweeklyList.get(position).getSizeofStock());
                                       edtRemarks.setText(""+ pondweeklyList.get(position).getRemarks());
                                       editSurvivalRate.setText(""+ pondweeklyList.get(position).getSurvivalrate_per_pond());

                                       d2.show();
                                       add.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               d2.hide();
                                               modifyWeeklyDetail(pondweeklyList.get(position).getId() + "", edtRemarks.getText().toString(), edtAbw.getText().toString(),
                                                       Helper.variables.URL_UPDATE_POND_WEEKLY_DETAIL_BY_ID);
                                           }
                                       });

                                       cancel.setOnClickListener(new View.OnClickListener() {
                                           @Override public void onClick(View v) {
                                               d2.hide();
                                           }
                                       });

                                   }else if (position1 == 1){
                                       d.hide();
                                       final Dialog del = Helper.dialog.themedYesNo(activity, "Delete selected visit?", "Delete", "NO", "DELETE", R.color.red);
                                       del.show();

                                       Button cancel = (Button) del.findViewById(R.id.btn_dialog_yesno_opt1);
                                       Button delete = (Button) del.findViewById(R.id.btn_dialog_yesno_opt2);
                                       delete.setTextColor(getResources().getColor(R.color.red));

                                       cancel.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               del.hide();
                                           }
                                       });

                                       delete.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               del.hide();
                                               if (db.deleteRow_Weekly(pondweeklyList.get(position).getId() + "")) {
                                                   Helper.toast.short_(activity, "Deleted successfully");
                                                   finish();
                                               } else {
                                                   Helper.toast.short_(activity, "Delete failed. Try again.");
                                                   finish();
                                               }
                                           }
                                       });
                                   }
                               }
                           });
                       }
                    }
                }
                return true;
            }
        });
    }


    private void scrollMyListViewToBottom(final ListView myListView, final Adapter_Growouts_PondWeekLyConsumption myListAdapter, final int position ) {
        myListView.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                myListView.setSelection(position - 1);
            }
        });
    }


    private void initViewsFromXML() {
        txtheadr = (TextView) findViewById(R.id.lbl_weeklyreports_farmName);
        txtdateStocked = (TextView) findViewById(R.id.txt_weeklyreports_DateStocked);
        txtQuantity = (TextView) findViewById(R.id.txt_weeklyreports_quantity);
        lvPonds = (ListView) findViewById(R.id.lv_pond_weeklyReports);
        btn_details = (ImageButton) findViewById(R.id.btn_details);
        btn_addreport = (ImageButton) findViewById(R.id.btn_addreport);
        if (Helper.variables.getGlobalVar_currentLevel(activity) == 4) {
            btn_addreport.setVisibility(View.VISIBLE);
        }else{
            btn_addreport.setVisibility(View.GONE);
        }
    }



    public void getpondData( final int pondid, String url) {
        if (Helper.variables.getGlobalVar_currentLevel(activity) == 4) {

            Cursor cur = db.getLocal_PondWeeklyUpdates(id+"");
            if (cur != null){
                if (cur.getCount() > 0) {
                    pondweeklyList = new ArrayList<>();
                    finalWeekList = new ArrayList<>();
                    Log.d("DB", "get count" +id + " " + cur.getCount());
                    while (cur.moveToNext()) {
                        CustInfoObject custInfoObject = new CustInfoObject();
                        custInfoObject.setId(cur.getInt(cur.getColumnIndex(GPSHelper.CL_WEEKLY_UPDATES_ID)));
                        custInfoObject.setSizeofStock(cur.getInt(cur.getColumnIndex(GPSHelper.CL_WEEKLY_UPDATES_CURRENT_ABW)));
                        custInfoObject.setRemarks(cur.getString(cur.getColumnIndex(GPSHelper.CL_WEEKLY_UPDATES_REMARKS)));
                        custInfoObject.setPondID(cur.getInt(cur.getColumnIndex(GPSHelper.CL_WEEKLY_UPDATES_PONDID)));
                        custInfoObject.setDateAddedToDB(cur.getString(cur.getColumnIndex(GPSHelper.CL_WEEKLY_UPDATES_DATEADDED)));
                        custInfoObject.setSurvivalrate_per_pond(cur.getString(cur.getColumnIndex(GPSHelper.CL_WEEKLY_UPDATES_CURRENT_SURVIVALRATE)));
                        custInfoObject.setIsPosted_weekly(cur.getInt(cur.getColumnIndex(GPSHelper.CL_WEEKLY_UPDATES_isposted)));
                        pondweeklyList.add(custInfoObject);
                    }

                    populatePondUpdatesListView();
                    adapterPondWeeklyReport = new Adapter_Growouts_PondWeekLyConsumption(Activity_PondWeeklyConsumption.this,
                            R.layout.item_lv_weeklyreport_allfeeddemands, finalWeekList);
                    lvPonds.setAdapter(adapterPondWeeklyReport);
                    scrollMyListViewToBottom(lvPonds, adapterPondWeeklyReport, pondweeklyList.size());
                }
            }

        }else{
            PD.setMessage("Retrieving Data. Please wait... ");
            PD.show();
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pondweeklyList = new ArrayList<>();
                            finalWeekList = new ArrayList<>();
                            PD.dismiss();

                            if (!response.substring(1,2).equalsIgnoreCase("0")) {
                                pondweeklyList = PondWeeklyUpdateParser.parseFeed(response);
                                populatePondUpdatesListView();
                            }else {
//                            Helper.toastShort(activity, "No records");
                            }
                            adapterPondWeeklyReport = new Adapter_Growouts_PondWeekLyConsumption(Activity_PondWeeklyConsumption.this,
                                    R.layout.item_lv_weeklyreport_allfeeddemands, finalWeekList);
                            lvPonds.setAdapter(adapterPondWeeklyReport);

                            scrollMyListViewToBottom(lvPonds, adapterPondWeeklyReport, pondweeklyList.size());
                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    PD.dismiss();
                    Dialog d = Helper.dialog.themedOkOnly(activity, "Error", "Something unexpected happened: " + error.toString(), "OK");
                    d.show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", Helper.variables.getGlobalVar_currentUserName(activity));
                    params.put("password", Helper.variables.getGlobalVar_currentUserPassword(activity));
                    params.put("deviceid", Helper.deviceInfo.getMacAddress(context));
                    params.put("pondindex", id+"");
                    return params;
                }
            };

            MyVolleyAPI api = new MyVolleyAPI();
            api.addToReqQueue(postRequest, context);
        }
    }

    private void populatePondUpdatesListView() {
        if (pondweeklyList!=null) {
            if (pondweeklyList.size() > 0){
                Log.d("null", "before for");

                for (int i = 0; i < pondweeklyList.size(); i++) {

                    CustInfoObject weekinfo1 = new CustInfoObject();

                    strRemarks = pondweeklyList.get(i).getRemarks();
                    strabw = pondweeklyList.get(i).getSizeofStock();
                    strSurvivalRate = Integer.parseInt(pondweeklyList.get(i).getSurvivalrate_per_pond());

                    if (specie.equalsIgnoreCase("tilapia")){
                        strweeknum = Helper.forFishComputation.get_Tilapia_WeekNum_byABW(strabw);
                    }else if (specie.equalsIgnoreCase("bangus")){
                        strweeknum = Helper.forFishComputation.get_Bangus_WeekNum_byABW(strabw);
                    }else if (specie.equalsIgnoreCase("vannamei")){
                        strweeknum = Helper.forFishComputation.get_Bangus_WeekNum_byABW(strabw);
                    }

                    if(specie.equalsIgnoreCase("tilapia")){
                        strrecommended = (Double.parseDouble(Helper.forFishComputation.computeWeeklyFeedConsumption(Double.parseDouble(strabw + ""), quantity,
                                Helper.forFishComputation.get_TilapiaFeedingRate_by_WeekNum(strweeknum),
                                (Double.parseDouble(strSurvivalRate+"") / 100)))/1000)+"";
                    }else if (specie.equalsIgnoreCase("bangus")){
                        strrecommended = (Double.parseDouble(Helper.forFishComputation.computeWeeklyFeedConsumption(Double.parseDouble(strabw + ""), quantity,
                                Helper.forFishComputation.get_BangusFeedingRate_by_WeekNum(strweeknum),
                                (Double.parseDouble(strSurvivalRate+"") / 100)))/1000)+"";
                    }

                    if (specie.equalsIgnoreCase("tilapia")){
                        strFeedtype = Helper.forFishComputation.getTilapiaFeedTypeByNumberOfWeeks(Helper.forFishComputation.get_Tilapia_WeekNum_byABW(strabw));
                    }else if (specie.equalsIgnoreCase("bangus")) {
                        strFeedtype = Helper.forFishComputation.getBangusFeedtypeByABW(strabw);
                    }

                    weekinfo1.setId(pondweeklyList.get(i).getId());
                    weekinfo1.setRemarks(strRemarks);
                    weekinfo1.setSizeofStock(strabw);
                    weekinfo1.setSurvivalrate_per_pond(strSurvivalRate+"");
                    weekinfo1.setWeek(strweeknum);
                    weekinfo1.setRecommendedConsumption(strrecommended);
                    weekinfo1.setCurrentFeedType(strFeedtype);
//                                        Log.d("null", strRemarks + " x " + strabw + " x " + strweeknum + " x " + strrecommended + " x " + strFeedtype);
                    finalWeekList.add(weekinfo1);
                    Log.d("null", "end of loop");
                }//end of loop
            }
        }
    }


    private void AddReport(final String abw2, String url, final String remarks2, String survivalRate){
        PD.setMessage("Saving Report. Please wait... ");
        PD.show();

        final long result = db.insertWeeklyUpdates(abw2, remarks2, id+"", Helper.convert.getDateDBformat(), survivalRate);

        if (result != -1){
            PD.dismiss();
            final Dialog d = Helper.dialog.themedOkOnly(Activity_PondWeeklyConsumption.this,
                    "Success", "Saving successful", "OK");
            TextView ok = (TextView) d.findViewById(R.id.btn_dialog_okonly_OK);
            d.show();
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.hide();

                    Logging.logUserAction(activity, activity.getBaseContext(),
                            Helper.userActions.TSR.ADD_WEEKLYREPORT + ":" + result + "-" + Helper.variables.getGlobalVar_currentUserID(activity) + "-" + abw2 + "-" + remarks2,
                            Helper.variables.ACTIVITY_LOG_TYPE_TSR_MONITORING);

                    finish();

                }
            });
        }else{
            PD.dismiss();
            final Dialog d = Helper.dialog.themedOkOnly(Activity_PondWeeklyConsumption.this,
                    "Error", "Reporting failed. Please Try Again. ", "OK");
            TextView ok = (TextView) d.findViewById(R.id.btn_dialog_okonly_OK);
            d.show();
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.hide();
                    PD.dismiss();
                }
            });
        }
//        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        pondweeklyList = new ArrayList<>();
//                        PD.dismiss();
//
////                        Helper.themedOkOnly(activity, "Responze", response, "OK", R.color.red);
//                        if (!response.substring(1,2).equalsIgnoreCase("0")) {
//                            Helper.toastShort(activity, "Report Added Successfully!");
//                            getpondData(id, Helper.variables.URL_SELECT_POND_WEEKLY_UPDATES_BY_ID);
//                        }else { Helper.toastShort(activity, "No records"); }
//                    }
//
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                PD.dismiss();
//                Dialog d = Helper.themedOkOnly(activity, "Error", "Something unexpected happened: " + error.toString(), "OK", R.color.red);
//                d.show();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//
//                params.put("username", Helper.variables.getGlobalVar_currentUserName(activity));
//                params.put("password", Helper.variables.getGlobalVar_currentUserPassword(activity));
//                params.put("deviceid", Helper.getMacAddress(context));
//                params.put("abw", abw2);
//                params.put("remarks", remarks2);
//                params.put("pondindex", id+"");
//                return params;
//            }
//        };
//
//        MyVolleyAPI api = new MyVolleyAPI();
//        api.addToReqQueue(postRequest, context);
    }



    private void modifyWeeklyDetail(final String idToBeDeleted, final String remarks1, final String abw1, String url2){
        PD.setMessage("Updating database. Please wait... ");
        PD.show();

        int res = db.updateRowWeeklyUpdates(idToBeDeleted, abw1, remarks1);
        if (res > 0) {
            PD.dismiss();
            Helper.dialog.themedOkOnly(activity, "Success", "You have successfully updated the record", "OK");
            getpondData(id, "");
        }else{
            PD.dismiss();
            Helper.dialog.themedOkOnly(activity, "Error", "Updating failed. Please try again.", "OK");
        }

//        StringRequest postRequest = new StringRequest(Request.Method.POST, url2,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        pondweeklyList = new ArrayList<>();
//                        PD.dismiss();
//                        if (!response.substring(1,2).equalsIgnoreCase("0")) {
//                            Helper.toastShort(activity, "Success");
//                            getpondData(id, Helper.variables.URL_SELECT_POND_WEEKLY_UPDATES_BY_ID);
//                        }else { Helper.toastShort(activity, "Something went wrong. Please try again later"); }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                PD.dismiss();
//                Dialog d = Helper.themedOkOnly(activity, "Error", "Something unexpected happened: " + error.toString(), "OK", R.color.red);
//                d.show();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//
//                params.put("username", Helper.variables.getGlobalVar_currentUserName(activity));
//                params.put("password", Helper.variables.getGlobalVar_currentUserPassword(activity));
//                params.put("deviceid", Helper.getMacAddress(context));
//                params.put("pondindex", idToBeDeleted+"");
//                params.put("abw",       abw1+"");
//                params.put("remarks",   remarks1+"");
//                return params;
//            }
//        };
//
//        MyVolleyAPI api = new MyVolleyAPI();
//        api.addToReqQueue(postRequest, context);
    }

    @Override
    protected void onResume() {
        super.onResume();
        db.open();
    }


    @Override
    protected void onPause() {
        super.onPause();
        db.close();
    }



}
