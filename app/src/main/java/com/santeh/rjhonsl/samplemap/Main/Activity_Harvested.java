package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.santeh.rjhonsl.samplemap.Adapter.AdapterPonds;
import com.santeh.rjhonsl.samplemap.DBase.GpsDB_Query;
import com.santeh.rjhonsl.samplemap.DBase.GpsSQLiteHelper;
import com.santeh.rjhonsl.samplemap.Obj.CustInfoObject;
import com.santeh.rjhonsl.samplemap.R;

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
    AdapterPonds adapterHarvested;

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


        getHarvertInfo();

    }

    private void getHarvertInfo() {
        Log.d("DB", "");
        Cursor cur = db.getLocal_NotPondsByFarmIndex(intent.getStringExtra("id")+"");
        if (cur!= null){
            if (cur.getCount() > 0) {
                harvestinfoList = new ArrayList<>();

                while (cur.moveToNext()) {
                    CustInfoObject custInfoObject = new CustInfoObject();
                    custInfoObject.setId(cur.getInt(cur.getColumnIndex(GpsSQLiteHelper.CL_POND_INDEX)));
                    custInfoObject.setPondID(cur.getInt(cur.getColumnIndex(GpsSQLiteHelper.CL_POND_PID)));
                    custInfoObject.setSpecie(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_POND_specie)));
                    custInfoObject.setSizeofStock(cur.getInt(cur.getColumnIndex(GpsSQLiteHelper.CL_POND_sizeofStock)));
                    custInfoObject.setSurvivalrate_per_pond(cur.getInt(cur.getColumnIndex(GpsSQLiteHelper.CL_POND_survivalrate)) + "");
                    custInfoObject.setDateStocked(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_POND_dateStocked)));
                    custInfoObject.setQuantity(cur.getInt(cur.getColumnIndex(GpsSQLiteHelper.CL_POND_quantity)));
                    custInfoObject.setArea(cur.getInt(cur.getColumnIndex(GpsSQLiteHelper.CL_POND_area)));
                    custInfoObject.setCulturesystem(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_POND_culturesystem)));
                    custInfoObject.setRemarks(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_POND_remarks)));
                    custInfoObject.setCustomerID(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_POND_customerId)));
                    custInfoObject.setIsPosted(cur.getInt(cur.getColumnIndex(GpsSQLiteHelper.CL_POND_isPosted)));
                    custInfoObject.setCurrentABW(cur.getInt(cur.getColumnIndex(GpsSQLiteHelper.CL_WEEKLY_UPDATES_CURRENT_ABW)));
                    harvestinfoList.add(custInfoObject);
                }
                populateListViewAdapter();
            }
        }
    }


    private void populateListViewAdapter() {
        if (harvestinfoList != null){
            adapterHarvested = new AdapterPonds(context, R.layout.item_lv_manageponds, harvestinfoList);
            lvHarvestInfo.setAdapter(adapterHarvested);
            lvHarvestInfo.setVisibility(View.VISIBLE);
            llnopond.setVisibility(View.GONE);
        }
        else{
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
