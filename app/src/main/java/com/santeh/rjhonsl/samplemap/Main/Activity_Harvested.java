package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.santeh.rjhonsl.samplemap.Adapter.AdapterHarvest;
import com.santeh.rjhonsl.samplemap.DBase.GpsDB_Query;
import com.santeh.rjhonsl.samplemap.DBase.GpsSQLiteHelper;
import com.santeh.rjhonsl.samplemap.Obj.CustInfoObject;
import com.santeh.rjhonsl.samplemap.R;
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

    GpsDB_Query db;
    ListView lvHarvestInfo;
    LinearLayout llnopond;
    AdapterHarvest adapterHarvested;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harvested);

        db = new GpsDB_Query(this);
        db.open();

        activity = this;
        context = Activity_Harvested.this;

        intent = getIntent();

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

                final String[] options = new String[]{"Edit", "Delete", "Update History"};
                final Dialog d = Helper.createCustomThemedListDialog(activity, options, "Options", R.color.skyblue_500);
                d.show();

                final ListView lvoptions = (ListView) d.findViewById(R.id.dialog_list_listview);
                lvoptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position2, long id) {
                        if (position2 == 0){
                            d.hide();
                            if (harvestinfoList.get(position).getHrv_isPosted().equalsIgnoreCase("1")) {
                                Helper.createCustomDialogOKOnly(activity, "Warning", "This harvest information was already uploaded.", "OK");
                            }else{

                            }
                        }else if(position2 == 1){
                            d.hide();
                            if (harvestinfoList.get(position).getHrv_isPosted().equalsIgnoreCase("1")) {
                                Helper.createCustomDialogOKOnly(activity, "Warning", "This harvest information was already uploaded.", "OK");
                                d.hide();
                            }else{
                                final Dialog d1 =  Helper.createCustomDialogThemedYesNO(activity, "Delete this harvest info?", "Delete", "NO", "YES", R.color.red);
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
                                        Helper.toastShort(activity, "Harvest Information Deleted");
                                        recreate();
                                    }
                                });
                            }

                        }else if(position2 == 2){
                            d.hide();
                        }
                    }
                });

                return false;
            }
        });
        getHarvertInfo();

    }

    private void getHarvertInfo() {
        Cursor cur = db.getAllHarvestInfo();
        if (cur!= null){
            if (cur.getCount() > 0) {
                harvestinfoList = new ArrayList<>();

                while (cur.moveToNext()) {
                    CustInfoObject custInfoObject = new CustInfoObject();
                    custInfoObject.setHrv_id(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_HRV_ID)));
                    custInfoObject.setHrv_pondid(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_HRV_PONDID)));
                    custInfoObject.setHrv_casenum(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_HRV_CASENUM)));
                    custInfoObject.setHrv_specie(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_HRV_SPECIES)));
                    custInfoObject.setHrv_dateOfHarvest(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_HRV_DATEOFHARVESTED)));
                    custInfoObject.setHrv_finalABW(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_HRV_FINALABW)));
                    custInfoObject.setHrv_totalConsumption(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_HRV_TOTAL_CONSUMPTION)));
                    custInfoObject.setHrv_fcr(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_HRV_FCR)));
                    custInfoObject.setHrv_pricePerKilo(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_HRV_PRICEPERKILO)));
                    custInfoObject.setHrv_totalHarvested(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_HRV_TOTALHARVEST)));
                    custInfoObject.setHrv_isPosted(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_HRV_ISPOSTED)));
                    custInfoObject.setHrv_dateRecorded(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_HRV_DATE_INSERTED)));

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
    }

    @Override
    protected void onPause() {
        super.onPause();
        db.close();
    }
}
