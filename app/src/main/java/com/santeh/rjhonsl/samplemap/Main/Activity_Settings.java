package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.santeh.rjhonsl.samplemap.APIs.MyVolleyAPI;
import com.santeh.rjhonsl.samplemap.DBase.GpsDB_Query;
import com.santeh.rjhonsl.samplemap.DBase.GpsSQLiteHelper;
import com.santeh.rjhonsl.samplemap.Obj.CustInfoObject;
import com.santeh.rjhonsl.samplemap.Parsers.CustAndPondParser;
import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.Helper;
import com.santeh.rjhonsl.samplemap.Utils.SimpleFileDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rjhonsl on 7/31/2015.
 */
public class Activity_Settings extends Activity{

    TextView txtTitle, txtSettings_custinfo, txtAbout, txtChangeLog, txtRestoreFromServer, txtRestoreLocal, txtBackupLocal;
    Context context; Activity activity;

    List<CustInfoObject> custInfoObjectList;
    ProgressDialog PD;

    GpsDB_Query db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        context = Activity_Settings.this;
        activity = this;
        db = new GpsDB_Query(this);
        PD = new ProgressDialog(this);
        PD.setCancelable(false);
        PD.setIndeterminate(true);

        custInfoObjectList = new ArrayList<>();

        db.open();

        txtTitle = (TextView) findViewById(R.id.txt_settings_title);
        txtAbout = (TextView) findViewById(R.id.txt_settings_about);
        txtRestoreLocal = (TextView) findViewById(R.id.txt_settings_restore_local);
        txtBackupLocal = (TextView) findViewById(R.id.txt_settings_backup_local);
        txtRestoreFromServer = (TextView) findViewById(R.id.txt_settings_restore);
        txtSettings_custinfo = (TextView) findViewById(R.id.txt_settings_farmInfo);
        txtChangeLog = (TextView) findViewById(R.id.txt_settings_changelog);

        txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtSettings_custinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Settings.this, Activity_ViewCustomerInfo.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        txtAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        txtChangeLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


        txtBackupLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backupLocal();
            }
        });

        txtRestoreLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restoreLocal();
            }
        });

        txtRestoreFromServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startRestore();
                startRestore_customerInfo();
                PD.setMessage("Restoring... ");
                PD.show();

//                String query_custinfo = "SELECT * FROM `tblmaincustomerinfo` where mci_addedby = 9";
//                String query_pondInfo = "SELECT * FROM `tblPond` WHERE tblPond.customerId = '9-1' OR XX = XX";
//                String query_weekly = "SELECT * FROM `tblpond_weeklyupdates` WHERE wu_pondid = '9-12' OR XX = XX";
            }
        });
    }



    private void startRestore(){

        final String query_farminfo   = "SELECT * FROM `tblCustomerInfo` WHERE `tblCustomerInfo`.`addedby` = "+Helper.variables.getGlobalVar_currentUserID(activity);
        StringRequest postRequest = new StringRequest(Request.Method.POST, Helper.variables.URL_PHP_RAW_QUERY_POST_SELECT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        PD.dismiss();
                        if (!response.substring(1, 2).equalsIgnoreCase("0")) {
                            custInfoObjectList =  CustAndPondParser.parseFeed(response);
                            if (custInfoObjectList != null) {
                                if (custInfoObjectList.size() > 0) {
                                    db.delete_ALL_ITEM_ON_TABLE(GpsSQLiteHelper.TBLFARMiNFO);
                                    Log.d("RESTORE", "TABLE CLEARED");
                                    for (int i = 0; i < custInfoObjectList.size() ; i++) {
                                        db.insertFarmInformation_RESTORE(
                                                custInfoObjectList.get(i).getLatitude(),
                                                custInfoObjectList.get(i).getLongtitude(),
                                                custInfoObjectList.get(i).getContact_name(),
                                                custInfoObjectList.get(i).getCompany(),
                                                custInfoObjectList.get(i).getAddress(),
                                                custInfoObjectList.get(i).getFarmname(),
                                                custInfoObjectList.get(i).getFarmID(),
                                                custInfoObjectList.get(i).getContact_number(),
                                                custInfoObjectList.get(i).getCultureType(),
                                                custInfoObjectList.get(i).getCulturelevel(),
                                                custInfoObjectList.get(i).getWaterType(),
                                                custInfoObjectList.get(i).getDateAddedToDB(),
                                                custInfoObjectList.get(i).getFarm_addedBy(),
                                                custInfoObjectList.get(i).getFarmLocalID()
                                        );
                                    }
                                    Helper.toastShort(activity, "Restore Successful.");
                                    Log.d("RESTORE", "RESTORE SUCCESSFUL - farminfo");
                                }
                            }
                        } else {
                            Log.d("RESTORE", "RESTORE failed - farminfo");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        PD.dismiss();
                        Helper.toastShort(activity, "RESTORE FAILED. Please try syncing again.");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", Helper.variables.getGlobalVar_currentUserName(activity));
                params.put("password", Helper.variables.getGlobalVar_currentUserPassword(activity));
                params.put("deviceid", Helper.getMacAddress(context));
                params.put("userid", Helper.variables.getGlobalVar_currentUserID(activity)+"");
                params.put("userlvl", Helper.variables.getGlobalVar_currentLevel(activity)+"");
                params.put("sql", query_farminfo + "");

                Log.d("SQL_RESTORE", query_farminfo);

//
                return params;
            }
        };

        MyVolleyAPI api = new MyVolleyAPI();
        api.addToReqQueue(postRequest, this);
    }


    private void startRestore_customerInfo(){

        final String query_customerInfo   = "SELECT * FROM `tblmaincustomerinfo` WHERE `tblmaincustomerinfo`.`mci_addedby` = "+Helper.variables.getGlobalVar_currentUserID(activity);
        StringRequest postRequest = new StringRequest(Request.Method.POST, Helper.variables.URL_PHP_RAW_QUERY_POST_SELECT_CUSTOMER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        PD.dismiss();
                        if (!response.substring(1, 2).equalsIgnoreCase("0")) {
                            custInfoObjectList =  CustAndPondParser.parseFeed(response);
                            if (custInfoObjectList != null) {
                                if (custInfoObjectList.size() > 0) {


                                    db.delete_ALL_ITEM_ON_TABLE(GpsSQLiteHelper.TBLMAINCUSTOMERINFO);
                                    Log.d("RESTORE", "TABLE CLEARED");
                                    for (int i = 0; i < custInfoObjectList.size() ; i++) {
                                        db.insertMainCustomerInformation_RESTORE(
                                                Helper.variables.getGlobalVar_currentUserID(activity),
                                                custInfoObjectList.get(i).getLastname(),
                                                custInfoObjectList.get(i).getMiddleName(),
                                                custInfoObjectList.get(i).getFirstname(),
                                                custInfoObjectList.get(i).getFarmID(),
                                                custInfoObjectList.get(i).getHouseNumber()+"",
                                                custInfoObjectList.get(i).getStreet(),
                                                custInfoObjectList.get(i).getSubdivision(),
                                                custInfoObjectList.get(i).getBarangay(),
                                                custInfoObjectList.get(i).getCity(),
                                                custInfoObjectList.get(i).getProvince(),
                                                custInfoObjectList.get(i).getBirthday(),
                                                custInfoObjectList.get(i).getBirthPlace(),
                                                custInfoObjectList.get(i).getTelephone(),
                                                custInfoObjectList.get(i).getCellphone(),
                                                custInfoObjectList.get(i).getCivilStatus(),
                                                custInfoObjectList.get(i).getSpouse_fname(),
                                                custInfoObjectList.get(i).getSpouse_lname(),
                                                custInfoObjectList.get(i).getSpouse_mname(),
                                                custInfoObjectList.get(i).getSpouse_birthday(),
                                                custInfoObjectList.get(i).getHouseStatus(),
                                                custInfoObjectList.get(i).getCust_latitude(),
                                                custInfoObjectList.get(i).getCust_longtitude(),
                                                custInfoObjectList.get(i).getCustomerType(),
                                                Integer.parseInt(custInfoObjectList.get(i).getMainCustomerId())
                                        );
                                    }
                                    Helper.toastShort(activity, "Restore Successful.");

                                }
                            }
                        } else {
                            Log.d("RESTORE", "RESTORE failed - farminfo");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        PD.dismiss();
                        Helper.toastShort(activity, "RESTORE FAILED. Please try syncing again.");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", Helper.variables.getGlobalVar_currentUserName(activity));
                params.put("password", Helper.variables.getGlobalVar_currentUserPassword(activity));
                params.put("deviceid", Helper.getMacAddress(context));
                params.put("userid", Helper.variables.getGlobalVar_currentUserID(activity)+"");
                params.put("userlvl", Helper.variables.getGlobalVar_currentLevel(activity)+"");
                params.put("sql", query_customerInfo + "");

                return params;
            }
        };

        MyVolleyAPI api = new MyVolleyAPI();
        api.addToReqQueue(postRequest, this);
    }



    private void restoreLocal(){
        final Dialog d = Helper.createCustomDialogThemedYesNO(activity,
                "All data will be restored to the state of when the backup was done. All existing data after the date will not be restored.\n\nAre you sure you want to retore db? ",
                "Restore", "NO", "YES", R.color.red_material_600);
        Button btnyes = (Button) d.findViewById(R.id.btn_dialog_yesno_opt2);
        Button btnno  = (Button) d.findViewById(R.id.btn_dialog_yesno_opt1);

        btnno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.hide();
            }
        });

        btnyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.hide();
                if (Helper.random.checkSD(activity)) {
                    /////////////////////////////////////////////////////////////////////////////////////////////////
                    //Create FileOpenDialog and register a callback
                    /////////////////////////////////////////////////////////////////////////////////////////////////
                    SimpleFileDialog FileOpenDialog = new SimpleFileDialog(activity, ".db",
                            new SimpleFileDialog.SimpleFileDialogListener() {
                                String m_chosen;

                                @Override
                                public void onChosenDir(String chosenDir) {
                                    // The code in this function will be executed when the dialog OK button is pushed
                                    m_chosen = chosenDir;
                                    try {
                                        File sd = Environment.getExternalStorageDirectory();//gets external Directory/address
                                        if (sd.canWrite()) {
                                            String backupDBPath = "/data/data/com.santeh.rjhonsl.samplemap/databases/local.db";//database internal storage path
                                            File currentDB = new File(backupDBPath);
                                            File backupDB = new File(m_chosen);

                                            if (currentDB.exists()) {
                                                FileChannel src = new FileInputStream(backupDB).getChannel();
                                                FileChannel dst = new FileOutputStream(currentDB).getChannel();
                                                dst.transferFrom(src, 0, src.size());
                                                src.close();
                                                dst.close();
                                                Toast.makeText(getApplicationContext(), "Restore was successful", Toast.LENGTH_LONG).show();
                                                Intent i = getBaseContext().getPackageManager()
                                                        .getLaunchIntentForPackage(getBaseContext().getPackageName());
                                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(i);
                                            }
                                        }
                                    } catch (Exception e) {
                                        Helper.toastLong(activity, "Failed to Restore: " + String.valueOf(e));
                                    }

                                }
                            });

                    //You can change the default filename using the public variable "Default_File_Name"
                    FileOpenDialog.Default_File_Name = "";
                    FileOpenDialog.chooseFile_or_Dir();
                    /////////////////////////////////////////////////////////////////////////////////////////////////
                } else {
                    Helper.toastLong(activity, "External storage not available");
                }
            }
        });

    }

    private void backupLocal(){
        final Dialog d = Helper.createCustomDialogThemedYesNO(activity,
                "Create local backup? ",
                "Backup", "NO", "YES", R.color.red_material_600);
        Button btnyes = (Button) d.findViewById(R.id.btn_dialog_yesno_opt2);
        Button btnno  = (Button) d.findViewById(R.id.btn_dialog_yesno_opt1);

        btnno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.hide();
            }
        });

        btnyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.hide();

                if(Helper.random.checkSD(activity)){
                    final String inFileName = "/data/data/com.santeh.rjhonsl.samplemap/databases/local.db";//current database to be exported
                    File dbFile = new File(inFileName);
                    FileInputStream fis = null;
                    try {
                        fis = new FileInputStream(dbFile);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    //gets time for naming sequence
                    Date d = new Date();
                    CharSequence s  = DateFormat.format("MMM-dd-yyyy hhmmAA", d.getTime());
                    String curDate = String.valueOf(s);

                    String outFileName = Environment.getExternalStorageDirectory()+"/.aq/local/" + curDate+".db";//output file name

                    // Open the empty db as the output stream
                    OutputStream output = null;
                    try {
                        output = new FileOutputStream(outFileName);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    // Transfer bytes from the inputfile to the outputfile
                    byte[] buffer = new byte[1024];
                    int length;
                    try {
                        while ((length = fis.read(buffer))>0){
                            output.write(buffer, 0, length);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Close the streams
                    try {
                        output.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Helper.toastLong(activity, "Back up Successfull: \n" + curDate);
                }
                else{
                    Helper.toastLong(activity, "External Storage not available!");
                }

            }
        });
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
