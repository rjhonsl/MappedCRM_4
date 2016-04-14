package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.google.android.gms.maps.model.LatLng;
import com.santeh.rjhonsl.samplemap.DBase.GpsDB_Query;
import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.FusedLocation;
import com.santeh.rjhonsl.samplemap.Utils.Helper;

import java.util.Calendar;

/**
 * Created by rjhonsl on 9/28/2015.
 */
public class Activity_EditPonds extends FragmentActivity  implements DatePickerDialog.OnDateSetListener{

    private int pondid, id, abw, quantity, area;
    private String specie, datestocked, culturesystem, remarks, survivalrate, latitude, longitude;

    private EditText edtPondNumber, edtSpecie, edtABW, edtSurvivalRate, edtDateStocked, edtQuantity, edtArea, edtCultureSystem, edtRemarks;

    Activity activity;
    Context context;
    ImageButton btn_back;
    Button btnQuantityTransfer;

    FusedLocation fusedLocation;
    public static final String DATEPICKER_TAG = "datepicker";

    DatePickerDialog datePickerDialog;
    int y, m, d;

    GpsDB_Query db;
    private int isposted;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpond);
        ProgressDialog PD = new ProgressDialog(this);
        PD.setCancelable(false);
        activity = this;
        context = Activity_EditPonds.this;
        db = new GpsDB_Query(this);
        db.open();

        fusedLocation = new FusedLocation(context, activity);
        fusedLocation.connectToApiClient();
        Helper.hideKeyboardOnLoad(activity);

        if (getIntent() != null){
            if (getIntent().hasExtra("pondid")){ pondid = getIntent().getIntExtra("pondid",0); }
            if (getIntent().hasExtra("id")){id = getIntent().getIntExtra("id", 0);}
            if (getIntent().hasExtra("isposted")){isposted = getIntent().getIntExtra("isposted", 0);}
            if (getIntent().hasExtra("abw")){ abw = getIntent().getIntExtra("abw", 0);}
            if (getIntent().hasExtra("survivalrate")){
                survivalrate = getIntent().getStringExtra("survivalrate");
            }
            if (getIntent().hasExtra("area")){ area = getIntent().getIntExtra("area", 0);}
            if (getIntent().hasExtra("quantity")){ quantity = getIntent().getIntExtra("quantity", 0);}
            if (getIntent().hasExtra("specie")){ specie = getIntent().getStringExtra("specie");}
            if (getIntent().hasExtra("datestocked")){ datestocked = getIntent().getStringExtra("datestocked");}
            if (getIntent().hasExtra("culturesystem")){culturesystem = getIntent().getStringExtra("culturesystem");}
            if (getIntent().hasExtra("remarks")){ remarks = getIntent().getStringExtra("remarks");}
            if (getIntent().hasExtra("latitude")){ latitude = getIntent().getStringExtra("latitude");}
            if (getIntent().hasExtra("longitude")){ longitude = getIntent().getStringExtra("longitude");}
        }


        edtSpecie = (EditText) findViewById(R.id.edtSpecie);
        edtPondNumber = (EditText) findViewById(R.id.edtpondnumber);
        edtABW = (EditText) findViewById(R.id.edtAbw);
        edtSurvivalRate = (EditText) findViewById(R.id.edtSurvivalRate);
        edtDateStocked = (EditText) findViewById(R.id.edtDateStocked);
        edtQuantity = (EditText) findViewById(R.id.edtQuantity);
        edtArea = (EditText) findViewById(R.id.edtArea);
        edtCultureSystem = (EditText) findViewById(R.id.edtCultureSystem);
        edtRemarks = (EditText) findViewById(R.id.edtRemarks);
        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnQuantityTransfer = (Button) findViewById(R.id.btnPondQuantityTransfer);

        if (isposted == 1) {
            edtSpecie.setEnabled(false);
            edtPondNumber.setEnabled(false);
            edtABW.setEnabled(false);
            edtSurvivalRate.setEnabled(false);
            edtDateStocked.setEnabled(false);
            edtQuantity.setEnabled(false);
            edtArea.setEnabled(false);
            edtCultureSystem.setEnabled(false);
            edtRemarks.setEnabled(false);

            btnSave.setEnabled(false);
        }else{
            edtSpecie.setEnabled(true);
            edtPondNumber.setEnabled(true);
            edtABW.setEnabled(true);
            edtSurvivalRate.setEnabled(true);
            edtDateStocked.setEnabled(true);
            edtQuantity.setEnabled(true);
            edtArea.setEnabled(true);
            edtCultureSystem.setEnabled(true);
            edtRemarks.setEnabled(true);

            btnSave.setEnabled(true);
        }


        btn_back = (ImageButton) findViewById(R.id.btn_title_left);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnQuantityTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, Activity_PondQuanityTransfer.class);
                intent.putExtra("quantity", quantity+"");
                intent.putExtra("pondid", pondid);
                intent.putExtra("id", id);
                intent.putExtra("species", specie);
                startActivity(intent);
            }
        });

        if (Helper.variables.getGlobalVar_currentLevel(activity) == 4){
            btnSave.setVisibility(View.VISIBLE);
        }else{
            btnSave.setVisibility(View.GONE);
        }


        edtSpecie.setText(specie);
        edtPondNumber.setText(pondid+"");
        edtABW.setText(abw+"");
        edtSurvivalRate.setText(survivalrate+"");
        edtDateStocked.setText(datestocked+"");
        edtQuantity.setText(quantity+"");
        edtArea.setText(area+"");
        edtCultureSystem.setText(culturesystem);
        edtRemarks.setText(remarks);


        final Calendar calendar = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));


        edtSpecie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] options = Helper.variables.ARRAY_SPECIES;
                final Dialog d = Helper.createCustomThemedListDialog(activity, options, "Species", R.color.deepteal_500);
                d.show();

                ListView lv = (ListView) d.findViewById(R.id.dialog_list_listview);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        edtSpecie.setText(Helper.variables.ARRAY_SPECIES[position]);
                        d.hide();
                    }
                });
            }
        });

        edtDateStocked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.setYearRange(1980, 2030);
                datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
            }
        });

        edtCultureSystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] options = Helper.variables.ARRAY_CULTURE_SYSTEM;
                final Dialog d = Helper.createCustomThemedListDialog(activity, options, "Systems", R.color.deepteal_500);
                d.show();

                ListView lv = (ListView) d.findViewById(R.id.dialog_list_listview);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        edtCultureSystem.setText(Helper.variables.ARRAY_CULTURE_SYSTEM[position]);
                        d.hide();
                    }
                });
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fusedLocation.disconnectFromApiClient();
                if (isposted == 1) {
                    Helper.createCustomThemedDialogOKOnly(activity, "Oops", "Record already finalized/posted. Please contact admin for further changes", "OK");
                } else {
                    fusedLocation.connectToApiClient();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            LatLng currentloc = fusedLocation.getLastKnowLocation();
                            LatLng farmlocat = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

                            float[] results = new float[1];
                            Location.distanceBetween(farmlocat.latitude, farmlocat.longitude,
                                    currentloc.latitude, currentloc.longitude, results);
//                        Helper.toastLong(activity, results[0]+"");

                            if (results[0] > 1000) {
                                final Dialog d = Helper.createCustomThemedDialogOKOnly(activity, "Out of Range", "You must be near the farm to EDIT.", "OK");
                                d.show();

                                Button ok = (Button) d.findViewById(R.id.btn_dialog_okonly_OK);
                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        d.hide();
                                    }
                                });
                            } else {

                                if (edtPondNumber.getText().toString().equalsIgnoreCase("")
                                        || edtSpecie.getText().toString().equalsIgnoreCase("")
                                        || edtABW.getText().toString().equalsIgnoreCase("")
                                        || edtSurvivalRate.getText().toString().equalsIgnoreCase("")
                                        || edtDateStocked.getText().toString().equalsIgnoreCase("")
                                        || edtQuantity.getText().toString().equalsIgnoreCase("")
                                        || edtCultureSystem.getText().toString().equalsIgnoreCase("")
                                        || edtRemarks.getText().toString().equalsIgnoreCase("")
                                        ) {
                                    final Dialog d = Helper.createCustomThemedDialogOKOnly(activity, "Message", "Complete all the following fields to continue.", "OK");
                                    d.show();
                                    Button ok = (Button) d.findViewById(R.id.btn_dialog_okonly_OK);
                                    ok.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            d.hide();

                                            if (edtPondNumber.getText().toString().equalsIgnoreCase("")) {
                                                edtPondNumber.requestFocus();
                                            } else if (edtSpecie.getText().toString().equalsIgnoreCase("")) {
                                                edtSpecie.requestFocus();
                                            } else if (edtABW.getText().toString().equalsIgnoreCase("")) {
                                                edtABW.requestFocus();
                                            } else if (edtSurvivalRate.getText().toString().equalsIgnoreCase("")) {
                                                edtSurvivalRate.requestFocus();
                                            } else if (edtDateStocked.getText().toString().equalsIgnoreCase("")) {
                                                edtDateStocked.requestFocus();
                                            } else if (edtQuantity.getText().toString().equalsIgnoreCase("")) {
                                                edtQuantity.requestFocus();
                                            } else if (edtCultureSystem.getText().toString().equalsIgnoreCase("")) {
                                                edtCultureSystem.requestFocus();
                                            } else if (edtRemarks.getText().toString().equalsIgnoreCase("")) {
                                                edtRemarks.requestFocus();
                                            }
                                        }

                                    });
                                } else {

                                    final Dialog x = Helper.createCustomDialogThemedYesNO(activity, "Save all information?", "Save", "NO", "YES",
                                            R.color.green_400);
                                    x.show();
                                    Button no = (Button) x.findViewById(R.id.btn_dialog_yesno_opt1);
                                    Button yes = (Button) x.findViewById(R.id.btn_dialog_yesno_opt2);
                                    no.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            x.hide();
                                        }
                                    });

                                    yes.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            x.hide();
                                            pondInfoDB();
                                        }
                                    });
                                }
                            }
                        }
                    }, 280);
                }
            }
        });
    }

    public void pondInfoDB() {
        Log.d("DB", "before query "+id);
        int res = db.updatePondInfo(id+"", edtPondNumber.getText().toString(), edtSpecie.getText().toString(), edtABW.getText().toString(),
                edtSurvivalRate.getText().toString(), edtDateStocked.getText().toString(), edtQuantity.getEditableText().toString(), edtArea.getText().toString(),
                edtCultureSystem.getText().toString(), edtRemarks.getText().toString());

        Log.d("DB", "row "+res);
        if (res > 0) {
            Dialog d = Helper.createCustomThemedDialogOKOnly(activity, "Success", "You have successfully updated the record", "OK");
            Button ok = (Button) d.findViewById(R.id.btn_dialog_okonly_OK);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }else {
            Helper.createCustomThemedDialogOKOnly(activity, "Error", "Something went wrong. Please try saving again", "OK");
        }

//        PD.setMessage("Saving Changes... ");
//        PD.show();
//
//        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        String responseCode = Helper.extractResponseCode(response);
//                        String title, prompt;
//
//
//                        if (responseCode.equalsIgnoreCase("0")){
//                            oopsprompt();
//                        }else if (responseCode.equalsIgnoreCase("1")) {
//
//                            Logging.logUserAction(activity, activity.getBaseContext(), Helper.userActions.TSR.Edit_POND, Helper.variables.ACTIVITY_LOG_TYPE_TSR_MONITORING);
//                            title = "Update";
//                            prompt = "You have successfully updated database.";
//                            PD.dismiss();
//
//                            final Dialog d = Helper.createCustomThemedDialogOKOnly(activity, title,
//                                    prompt, "OK", R.color.skyblue_500);
//                            TextView ok = (TextView) d.findViewById(R.id.btn_dialog_okonly_OK);
//                            d.show();
//                            ok.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    d.hide();
//                                    finish();
//                                }
//                            });
//                        }
//                        else {
//                            oopsprompt();
//                        }
//
//                    }
//
//                    private void oopsprompt() {
//                        String title="OOPS";
//                        String prompt = "Something went wrong. Please try again later.";
//                        PD.dismiss();
//
//                        final Dialog d = Helper.createCustomDialogOKOnly(activity, title,
//                                prompt, "OK");
//                        TextView ok = (TextView) d.findViewById(R.id.btn_dialog_okonly_OK);
//                        d.setCancelable(false);
//                        d.show();
//                        ok.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                d.hide();
//                            }
//                        });
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                PD.dismiss();
//
//                final Dialog d = Helper.createCustomDialogOKOnly(activity, "OOPS",
//                        "Something went wrong. error( "+ error +" )", "OK");
//                TextView ok = (TextView) d.findViewById(R.id.btn_dialog_okonly_OK);
//                d.setCancelable(false);
//                d.show();
//                ok.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
////                                    finish();
//                        d.hide();
//                    }
//                });
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//
//                params.put("specie", String.valueOf(edtSpecie.getText()));
//                params.put("pondid", String.valueOf(edtPondNumber.getText()));
//                params.put("dateStocked", String.valueOf(edtDateStocked.getText()));
//                params.put("quantity", String.valueOf(edtQuantity.getText()));
//                params.put("area", String.valueOf(edtArea.getText()));
//                params.put("culturesystem", String.valueOf(edtCultureSystem.getText()));
//                params.put("remarks", String.valueOf(edtRemarks.getText()));
//                params.put("id", String.valueOf(id2));
//                params.put("sizeofStock", String.valueOf(edtABW.getText()));
//                params.put("survivalrate", String.valueOf(edtSurvivalRate.getText()));
//
//                params.put("username", Helper.variables.getGlobalVar_currentUserName(activity));
//                params.put("password", Helper.variables.getGlobalVar_currentUserPassword(activity));
//                params.put("deviceid", Helper.getMacAddress(activity));
//                return params;
//            }
//        };
//
//        // Adding request to request queue
//        MyVolleyAPI api = new MyVolleyAPI();
//        api.addToReqQueue(postRequest, context);
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        edtDateStocked.setText( year + "-" + (month + 1) + "-"+day );
        y = year;
        m = month + 1;
        d = day;
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
