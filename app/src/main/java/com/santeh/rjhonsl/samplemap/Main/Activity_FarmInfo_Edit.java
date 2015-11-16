package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.santeh.rjhonsl.samplemap.APIs.MyVolleyAPI;
import com.santeh.rjhonsl.samplemap.DBase.GpsDB_Query;
import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.Helper;
import com.santeh.rjhonsl.samplemap.Utils.Logging;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rjhonsl on 8/11/2015.
 */
public class Activity_FarmInfo_Edit extends Activity{

    Intent intentExtras;

    LinearLayout ll;
    TextView txtactivityHeader, txtlat, txtlong;
    EditText edtContactname, edtCompany, edtAddress, edtFarmName, edtFarmId, edtContactNumber, edtCultureSystem, edtLevelOfCulture, edtWaterType;

    private String latitude, longitude;
    private int farmIndexId;
    private String contactName, address, farmname, farmID, contactnumber, culturesystem, levelofculture, watertype;

    Button btnSaveChanges, btnCancel, btnDelete;
    ProgressDialog PD;

    Activity activity;
    Context context;

    ImageButton btnback;

    GpsDB_Query db;
    private int isposted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = this;
        context = Activity_FarmInfo_Edit.this;
        PD = new ProgressDialog(this);
        PD.setCancelable(false);
        db = new GpsDB_Query(this);
        db.open();

        setContentView(R.layout.activity_add_farminformation);
        intentExtras = getIntent();
        getextras(intentExtras);
        initXmlViews();

        if (latitude.length() >= 10){
            txtlat.setText(latitude.substring(0, 9));
        }else{
            txtlat.setText(latitude);
        }

        if (longitude.length() >= 10){
            txtlong.setText(longitude.substring(0, 9));
        }else{
            txtlong.setText(longitude);
        }

        edtContactname.setText(contactName);
        edtAddress.setText(address);
        edtFarmName.setText(farmname);
        edtFarmId.setText(farmID);
        edtContactNumber.setText(contactnumber);
        edtCultureSystem.setText(culturesystem);
        edtLevelOfCulture.setText(levelofculture);
        edtWaterType.setText(watertype);

        if (Helper.variables.getGlobalVar_currentLevel(activity) == 4){
            txtactivityHeader.setText("Edit Farm Information");
        }else{
            txtactivityHeader.setText("Farm Information");
        }


        initListeners();
    }

    private void initListeners() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isposted == 1) {
                    Helper.createCustomThemedDialogOKOnly(activity, "Oops", "Item is alrady posted on our servers. Please contact admin for further changes.", "OK", R.color.red);
                }else{
                    final Dialog d = Helper.createCustomDialogThemedYesNO(activity, "Are you sure you want to delete this record? ", "Delete", "NO", "YES", R.color.red);
                    Button no = (Button) d.findViewById(R.id.btn_dialog_yesno_opt1);
                    Button yes = (Button) d.findViewById(R.id.btn_dialog_yesno_opt2);
                    d.show();
                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            d.hide();
                        }
                    });
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            boolean isDeleted = db.deleteRow_FarmInfo(farmIndexId+"");
                            if (isDeleted) {
                                Helper.toastShort(activity, "Record has been deleted.");
                                Intent intent = new Intent(activity, MapsActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("fromActivity", "addfarminfo");

                                Logging.logUserAction(activity, activity.getBaseContext(),
                                        Helper.userActions.TSR.DELETE_FARM + ":" + Helper.variables.getGlobalVar_currentUserID(activity) + "-" + farmIndexId + "-" + edtFarmName.getText().toString(),
                                        Helper.variables.ACTIVITY_LOG_TYPE_TSR_MONITORING);

                                startActivity(intent);
                                finish(); // call this to finish the current activity
                            }
                        }
                    });
                }
            }
        });

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCustomerInformation();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        edtCultureSystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = new Dialog(Activity_FarmInfo_Edit.this);//
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.dialog_listculturetype);
                d.show();

                ListView lstCultureType = (ListView) d.findViewById(R.id.lstDialog_culturetype);
                lstCultureType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            edtCultureSystem.setText("Cage");
                            d.hide();
                        } else if (position == 1) {
                            edtCultureSystem.setText("Pen");
                            d.hide();
                        } else if (position == 2) {
                            edtCultureSystem.setText("Pond");
                            d.hide();
                        }
                    }
                });
            }
        });

        edtLevelOfCulture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = new Dialog(Activity_FarmInfo_Edit.this);//
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.dialog_listculturelevel);
                d.show();

                ListView lstCultureLevel = (ListView) d.findViewById(R.id.lstDialog_culturelevel);
                lstCultureLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            edtLevelOfCulture.setText("Extensive");
                            d.hide();
                        } else if (position == 1) {
                            edtLevelOfCulture.setText("Intensive");
                            d.hide();
                        }else if (position == 2) {
                            edtLevelOfCulture.setText("Semi-Intensive");
                            d.hide();
                        }else if (position == 3) {
                            edtLevelOfCulture.setText("Mono-Culture");
                            d.hide();
                        }else if (position == 4) {
                            edtLevelOfCulture.setText("Poly-Culture");
                            d.hide();
                        }
                    }
                });
            }
        });

        edtWaterType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = new Dialog(Activity_FarmInfo_Edit.this);//
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.dialog_listwatertype);
                d.show();

                ListView lstCultureLevel = (ListView) d.findViewById(R.id.lstDialog_watertype);
                lstCultureLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            edtWaterType.setText("Fresh Water");
                            d.hide();
                        } else if (position == 1) {
                            edtWaterType.setText("Marine Water");
                            d.hide();
                        } else if (position == 2) {
                            edtWaterType.setText("Brackish Water");
                            d.hide();
                        }
                    }
                });
            }
        });

        edtCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = new Dialog(Activity_FarmInfo_Edit.this);//
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.dialog_listcompany);
                d.show();

                ListView lstCultureLevel = (ListView) d.findViewById(R.id.lstDialog_company);
                lstCultureLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            edtCompany.setText("PetOne");
                            d.hide();
                        } else if (position == 1) {
                            edtCompany.setText("Pronatural");
                            d.hide();
                        }
                        else if (position == 2) {
                            edtCompany.setText("Santeh");
                            d.hide();
                        }
                        else if (position == 3) {
                            edtCompany.setText("Tateh");
                            d.hide();
                        }
                    }
                });
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initXmlViews() {
        txtactivityHeader = (TextView) findViewById(R.id.titleHeader);
        txtlat = (TextView) findViewById(R.id.addMarker_lat);
        txtlong= (TextView) findViewById(R.id.addMarker_long);

        btnCancel = (Button) findViewById(R.id.btn_markerdetail_CANCEL);
        btnSaveChanges = (Button) findViewById(R.id.btn_markerdetail_OK);
        btnDelete = (Button) findViewById(R.id.btn_markerdetail_Delete);
        ll = (LinearLayout) findViewById(R.id.ll_buttonHolder);

        btnback = (ImageButton) findViewById(R.id.title);

        edtContactname = (EditText) findViewById(R.id.txt_markerdetails_contactName);
        edtCompany = (EditText) findViewById(R.id.txt_markerdetails_company);
        edtAddress = (EditText) findViewById(R.id.txt_markerdetails_address);
        edtFarmName = (EditText) findViewById(R.id.txt_markerdetails_farmname);
        edtFarmId = (EditText) findViewById(R.id.txt_markerdetails_farmID);
        edtContactNumber = (EditText) findViewById(R.id.txt_markerdetails_contactNumber);
        edtCultureSystem = (EditText) findViewById(R.id.txt_markerdetails_cultureType);
        edtLevelOfCulture = (EditText) findViewById(R.id.txt_markerdetails_levelofCulture);
        edtWaterType = (EditText) findViewById(R.id.txt_markerdetails_waterType);

        if (Helper.variables.getGlobalVar_currentLevel(activity) != 4){
            ll.setVisibility(View.GONE);
        }else{
            ll.setVisibility(View.VISIBLE);
        }

        Helper.hideKeyboardOnLoad(activity);

    }

    private void getextras(Intent intent) {

        if (intent != null){
            if (intent.hasExtra("lat")){latitude = intent.getStringExtra("lat");}
            if (intent.hasExtra("long")){longitude = intent.getStringExtra("long"); }

            if (intent.hasExtra("userid")){farmIndexId = intent.getIntExtra("userid", 0);}

            if (intent.hasExtra("contactName")){contactName = intent.getStringExtra("contactName");}
            if (intent.hasExtra("address")){address = intent.getStringExtra("address");}
            if (intent.hasExtra("farmname")){farmname = intent.getStringExtra("farmname");}
            if (intent.hasExtra("farmID")){farmID = intent.getStringExtra("farmID");}
            if (intent.hasExtra("contactnumber")){contactnumber = intent.getStringExtra("contactnumber");}
            if (intent.hasExtra("culturesystem")){culturesystem = intent.getStringExtra("culturesystem");}
            if (intent.hasExtra("levelofculture")){levelofculture = intent.getStringExtra("levelofculture");}
            if (intent.hasExtra("watertype")){watertype = intent.getStringExtra("watertype");}
            if (intent.hasExtra("isposted")){isposted = intent.getIntExtra("isposted", 3);}

            Helper.toastShort(activity, isposted+"");
        }
    }


    public void updateCustomerInformation() {
        if (isposted == 1) {
            Helper.createCustomThemedDialogOKOnly(activity, "Oops", "Item is alrady posted on our servers. Please contact admin for further changes.", "OK", R.color.red);
        }else{
            latitude = txtlat.getText().toString();
            longitude = txtlong.getText().toString();
            if (    latitude.equalsIgnoreCase("") || longitude.equalsIgnoreCase("") ||
                    edtContactname.getText().toString().equalsIgnoreCase("") ||
                    edtCompany.getText().toString().equalsIgnoreCase("") ||
                    edtAddress.getText().toString().equalsIgnoreCase("") ||
                    edtFarmName.getText().toString().equalsIgnoreCase("") ||
                    edtFarmId.getText().toString().equalsIgnoreCase("") ||
                    edtContactNumber.getText().toString().equalsIgnoreCase("") ||
                    edtCultureSystem.getText().toString().equalsIgnoreCase("") ||
                    edtLevelOfCulture.getText().toString().equalsIgnoreCase("") ||
                    edtWaterType.getText().toString().equalsIgnoreCase(""))
            {
                Helper.toastShort(activity,   "You must complete all fields to continue.");

            } else {
                PD.show();
                PD.setMessage("Saving changes...");

                if (Helper.variables.getGlobalVar_currentLevel(activity) != 4){
                    StringRequest postRequest = new StringRequest(Request.Method.POST, Helper.variables.URL_UPDATE_CUSTOMERINFORMATION_BY_ID,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    if (!Helper.extractResponseCodeBySplit(response).equalsIgnoreCase("0")) {
                                        PD.dismiss();
                                        Helper.toastShort(activity, "Update successful.");
                                        Logging.logUserAction(activity, context, Helper.userActions.TSR.Edit_FARM + ": index " + farmIndexId, Helper.variables.ACTIVITY_LOG_TYPE_TSR_MONITORING);
                                    } else {
                                        PD.dismiss();
                                        Helper.toastShort(activity, getResources().getString(R.string.VolleyUnexpectedError));
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            PD.dismiss();
                            Helper.toastShort(activity, "Failed to connect to server.");
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("id", farmIndexId+"");
                            params.put("latitude", String.valueOf(txtlat.getText()));
                            params.put("longitude", String.valueOf(txtlong.getText()));
                            params.put("contactName", edtContactname.getText().toString());
                            params.put("company", edtCompany.getText().toString());
                            params.put("address", edtAddress.getText().toString());
                            params.put("farmName", edtFarmName.getText().toString());
                            params.put("farmID", edtFarmId.getText().toString());
                            params.put("contactNumber", edtContactNumber.getText().toString());
                            params.put("cultureType", edtCultureSystem.getText().toString());
                            params.put("cultureLevel", edtLevelOfCulture.getText().toString());
                            params.put("waterType", edtWaterType.getText().toString());
                            params.put("username", Helper.variables.getGlobalVar_currentUserName(activity));
                            params.put("password", Helper.variables.getGlobalVar_currentUserPassword(activity));
                            params.put("deviceid", Helper.getMacAddress(activity));
                            return params;
                        }
                    };
                    // Adding request to request queue
                    MyVolleyAPI api = new MyVolleyAPI();
                    api.addToReqQueue(postRequest, Activity_FarmInfo_Edit.this);
                }else{

                    db.open();
                    int rowsAffectedCount = db.updateRowFarmInfo(
                            farmIndexId+"",
                            edtContactname.getText()+"",
                            edtCompany.getText()+"",
                            edtAddress.getText()+"",
                            edtFarmName.getText()+"",
                            edtFarmId.getText()+"",
                            edtContactNumber.getText()+"",
                            edtCultureSystem.getText()+"",
                            edtLevelOfCulture.getText()+"",
                            edtWaterType.getText()+""
                    );

                    if (rowsAffectedCount > 0) {
                        PD.dismiss();
                        Helper.createCustomThemedDialogOKOnly(activity, "Success", "Changes was successfully saved.", "OK", R.color.blue);
                        Intent intent = new Intent(activity, MapsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("fromActivity", "addfarminfo");

                        Logging.logUserAction(activity, activity.getBaseContext(),
                                Helper.userActions.TSR.Edit_FARM + ":" + Helper.variables.getGlobalVar_currentUserID(activity) + "-" + farmIndexId + "-" + edtFarmName.getText().toString(),
                                Helper.variables.ACTIVITY_LOG_TYPE_TSR_MONITORING);

                        startActivity(intent);
                        finish(); // call this to finish the current activity
                    }else{
                        Helper.createCustomThemedDialogOKOnly(activity, "Error", "Something happened. Please try again.", "OK", R.color.red);
                    }
                }
            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        db.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        db.open();
    }

}
