package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.santeh.rjhonsl.samplemap.Adapter.AdapterHarvest;
import com.santeh.rjhonsl.samplemap.DBase.GPSHelper;
import com.santeh.rjhonsl.samplemap.DBase.GPSQuery;
import com.santeh.rjhonsl.samplemap.Obj.CustInfoObject;
import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.FusedLocation;
import com.santeh.rjhonsl.samplemap.Utils.Helper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rjhonsl on 3/9/2016.
 */
public class Activity_Harvested extends FragmentActivity {


    TextView txtfarmname;
    ImageButton btnTitleLeft;
    Intent intent;

    Activity activity;
    Context context;
    List<CustInfoObject> harvestinfoList;

    GPSQuery db;
    ListView lvHarvestInfo;
    LinearLayout llnopond;
    AdapterHarvest adapterHarvested;
    String p_index, farmname;
    FusedLocation fusedlocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harvested);

        db = new GPSQuery(this);
        db.open();

        activity = this;
        context = Activity_Harvested.this;

        intent = getIntent();
        fusedlocation = new FusedLocation(context, activity);
        fusedlocation.connectToApiClient();

        btnTitleLeft = (ImageButton) findViewById(R.id.btn_title_left);
        txtfarmname = (TextView) findViewById(R.id.txt_farmname);
        lvHarvestInfo = (ListView) findViewById(R.id.lst_harvesthistory);
        llnopond = (LinearLayout) findViewById(R.id.ll_nopond);


        txtfarmname.setText(intent.getStringExtra("farmname"));
        btnTitleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        lvHarvestInfo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                fusedlocation.disconnectFromApiClient();
                fusedlocation.connectToApiClient();

                final String[] options = new String[]{"Edit", "Delete", "Update History"};
                final Dialog d = Helper.dialog.themedList(activity, options, "Options", R.color.skyblue_500);
                d.show();

                final ListView lvoptions = (ListView) d.findViewById(R.id.dialog_list_listview);
                lvoptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position2, long id) {
                        if (position2 == 0) {
                            d.hide();
                            Log.d("Harvested", "position 2 - before if");

                            if (harvestinfoList.get(position).getHrv_isPosted().equalsIgnoreCase("1")) {
                                Helper.dialog.themedOkOnly(activity, "Warning", "This harvest information was already uploaded.", "OK");
                            } else {

                                Intent intent = new Intent(Activity_Harvested.this, Activity_EditHarvest.class);
                                intent.putExtra("hrv_id", harvestinfoList.get(position).getHrv_id());
                                intent.putExtra("hrv_pondid", harvestinfoList.get(position).getHrv_pondid());
                                intent.putExtra("hrv_casenum", harvestinfoList.get(position).getHrv_casenum());
                                intent.putExtra("hrv_species", harvestinfoList.get(position).getHrv_specie());
                                intent.putExtra("hrv_dateofharvest", harvestinfoList.get(position).getHrv_dateOfHarvest());
                                intent.putExtra("hrv_finalabw", harvestinfoList.get(position).getHrv_finalABW());
                                intent.putExtra("hrv_totalconsumption", harvestinfoList.get(position).getHrv_totalConsumption());
                                intent.putExtra("hrv_fcr", harvestinfoList.get(position).getHrv_fcr());
                                intent.putExtra("hrv_priceperkilo", harvestinfoList.get(position).getHrv_pricePerKilo());
                                intent.putExtra("hrv_totalHarvest", harvestinfoList.get(position).getHrv_totalHarvested());
                                intent.putExtra("hrv_isposted", harvestinfoList.get(position).getHrv_isPosted());
                                intent.putExtra("hrv_dateinserted", harvestinfoList.get(position).getHrv_dateRecorded());
                                intent.putExtra("datestocked", harvestinfoList.get(position).getDateStocked());
                                startActivity(intent);
                            }
                        } else if (position2 == 1) {
                            d.hide();
                            if (harvestinfoList.get(position).getHrv_isPosted().equalsIgnoreCase("1")) {
                                Log.d("Harvested", "position 2 - is posted");
                                Helper.dialog.themedOkOnly(activity, "Warning", "This harvest information was already uploaded.", "OK");
                                d.hide();
                            } else {
                                final Dialog d1 = Helper.dialog.themedYesNo(activity, "Delete this harvest info?", "Delete", "NO", "YES", R.color.red);
                                Button no = (Button) d1.findViewById(R.id.btn_dialog_yesno_opt1);
                                Button yes = (Button) d1.findViewById(R.id.btn_dialog_yesno_opt2);

                                no.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        d1.hide();
                                    }
                                });

                                yes.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        d1.hide();
                                        db.deleteRow_HarvestInfo(harvestinfoList.get(position).getHrv_id());
                                        Helper.toast.short_(activity, "Harvest Information Deleted");
                                        getHarvertInfo();

                                    }
                                });
                            }

                        } else if (position2 == 2) {
                            d.hide();
                            startPondUpdatesActivity(position);
                        }
                    }
                });

                return false;
            }
        });
        getHarvertInfo();

    }

    private void startPondUpdatesActivity(int position) {
        Intent intent = new Intent(activity, Activity_PondWeeklyConsumption.class);
        intent.putExtra("farmname", farmname);
        intent.putExtra("pondid", harvestinfoList.get(position).getPondID());
        intent.putExtra("id", harvestinfoList.get(position).getId());
        intent.putExtra("specie", harvestinfoList.get(position).getSpecie());
        intent.putExtra("abw", harvestinfoList.get(position).getSizeofStock());
        intent.putExtra("survivalrate", harvestinfoList.get(position).getSurvivalrate_per_pond());
        intent.putExtra("datestocked", harvestinfoList.get(position).getDateStocked());
        intent.putExtra("quantity", harvestinfoList.get(position).getQuantity());
        intent.putExtra("area", harvestinfoList.get(position).getArea());
        intent.putExtra("culturesystem", harvestinfoList.get(position).getCulturesystem());
        intent.putExtra("remarks", harvestinfoList.get(position).getRemarks());

        intent.putExtra("latitude", fusedlocation.getLastKnowLocation().latitude);
        intent.putExtra("longitude", fusedlocation.getLastKnowLocation().longitude);
        startActivity(intent);
    }

    private void getHarvertInfo() {
        Cursor cur = db.getAllHarvestInfo();
        if (cur!= null){
            if (cur.getCount() > 0) {
                harvestinfoList = new ArrayList<>();

                while (cur.moveToNext()) {
                    CustInfoObject custInfoObject = new CustInfoObject();
                    custInfoObject.setHrv_id(cur.getString(cur.getColumnIndex(GPSHelper.CL_HRV_ID)));
                    custInfoObject.setHrv_pondid(cur.getString(cur.getColumnIndex(GPSHelper.CL_HRV_PONDID)));
                    custInfoObject.setHrv_casenum(cur.getString(cur.getColumnIndex(GPSHelper.CL_HRV_CASENUM)));
                    custInfoObject.setHrv_specie(cur.getString(cur.getColumnIndex(GPSHelper.CL_HRV_SPECIES)));
                    custInfoObject.setHrv_dateOfHarvest(cur.getString(cur.getColumnIndex(GPSHelper.CL_HRV_DATEOFHARVEST)));
                    custInfoObject.setHrv_finalABW(cur.getString(cur.getColumnIndex(GPSHelper.CL_HRV_FINALABW)));
                    custInfoObject.setHrv_totalConsumption(cur.getString(cur.getColumnIndex(GPSHelper.CL_HRV_TOTAL_CONSUMPTION)));
                    custInfoObject.setHrv_fcr(cur.getString(cur.getColumnIndex(GPSHelper.CL_HRV_FCR)));
                    custInfoObject.setHrv_pricePerKilo(cur.getString(cur.getColumnIndex(GPSHelper.CL_HRV_PRICEPERKILO)));
                    custInfoObject.setHrv_totalHarvested(cur.getString(cur.getColumnIndex(GPSHelper.CL_HRV_TOTALHARVEST)));
                    custInfoObject.setHrv_isPosted(cur.getString(cur.getColumnIndex(GPSHelper.CL_HRV_ISPOSTED)));
                    Log.d("Harvest info", ""+cur.getString(cur.getColumnIndex(GPSHelper.CL_HRV_ISPOSTED)));
                    custInfoObject.setHrv_dateRecorded(cur.getString(cur.getColumnIndex(GPSHelper.CL_HRV_DATE_INSERTED)));

                    custInfoObject.setDateStocked(  cur.getString(cur.getColumnIndex(GPSHelper.CL_POND_dateStocked)));
                    custInfoObject.setPondID(       cur.getInt(cur.getColumnIndex(GPSHelper.CL_POND_PID)));
                    custInfoObject.setId(           cur.getInt(cur.getColumnIndex(GPSHelper.CL_POND_INDEX)));
                    custInfoObject.setSpecie(       cur.getString(cur.getColumnIndex(GPSHelper.CL_POND_specie)));
                    custInfoObject.setSizeofStock(  cur.getInt(cur.getColumnIndex(GPSHelper.CL_POND_sizeofStock)));
                    custInfoObject.setSurvivalrate_per_pond(cur.getString(cur.getColumnIndex(GPSHelper.CL_POND_survivalrate)));
                    custInfoObject.setDateStocked(  cur.getString(cur.getColumnIndex(GPSHelper.CL_POND_dateStocked)));
                    custInfoObject.setQuantity(     cur.getInt(cur.getColumnIndex(GPSHelper.CL_POND_quantity)));
                    custInfoObject.setArea(         cur.getInt(cur.getColumnIndex(GPSHelper.CL_POND_area)));
                    custInfoObject.setCulturesystem(cur.getString(cur.getColumnIndex(GPSHelper.CL_POND_culturesystem)));
                    custInfoObject.setRemarks(      cur.getString(cur.getColumnIndex(GPSHelper.CL_POND_remarks)));
                    custInfoObject.setLatitude(     fusedlocation.getLastKnowLocation().latitude+"");
                    custInfoObject.setLongtitude(   fusedlocation.getLastKnowLocation().longitude+"");


                    farmname = "Farm";
                    p_index = cur.getString(cur.getColumnIndex(GPSHelper.CL_POND_INDEX));

                    harvestinfoList.add(custInfoObject);

                }
                populateListViewAdapter();
            }
        }
    }


    private void populateListViewAdapter() {
        if (harvestinfoList != null){
            adapterHarvested = new AdapterHarvest(context, R.layout.item_lv_harvested, harvestinfoList);
            lvHarvestInfo.setAdapter(adapterHarvested);
            lvHarvestInfo.setVisibility(View.VISIBLE);
            llnopond.setVisibility(View.GONE);
        } else{
            lvHarvestInfo.setVisibility(View.GONE);
            llnopond.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        db.open();
        getHarvertInfo();
    }

    @Override
    protected void onPause() {
        super.onPause();
        db.close();
    }
}
