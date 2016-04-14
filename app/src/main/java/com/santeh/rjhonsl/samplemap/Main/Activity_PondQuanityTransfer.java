package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.santeh.rjhonsl.samplemap.Adapter.Adapter_QuantityTransfer;
import com.santeh.rjhonsl.samplemap.DBase.GpsDB_Query;
import com.santeh.rjhonsl.samplemap.DBase.GpsSQLiteHelper;
import com.santeh.rjhonsl.samplemap.Obj.CustInfoObject;
import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.Helper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rjhonsl on 4/12/2016.
 */
public class Activity_PondQuanityTransfer extends FragmentActivity {

    Intent passedIntent;
    String quantity, specie, To="n/a";
    int pondid, id;

    Activity activity;
    Context context;

    List<CustInfoObject> pondList  = new ArrayList<>();
    ListView lvPonds;

    ImageButton btnBack;
    Button btnChangeqty;

    Adapter_QuantityTransfer adapterQuantityTransfer;

    GpsDB_Query db;
    Cursor cur;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quantitytransfer);
        TextView txttitle = (TextView) findViewById(R.id.title);
        txttitle.setText("Quantity Transfer");

        activity = this;
        context = Activity_PondQuanityTransfer.this;
        db = new GpsDB_Query(this);



        passedIntent = getIntent();
        if (passedIntent != null) {
            if (passedIntent.hasExtra("quantity")){
                quantity = passedIntent.getStringExtra("quantity");
            }
            if (passedIntent.hasExtra("pondid")){
                pondid = passedIntent.getIntExtra("pondid", 0);
            }
            if (passedIntent.hasExtra("id")){
                id = passedIntent.getIntExtra("id", 0);
            }
            if (passedIntent.hasExtra("species")){
                specie = passedIntent.getStringExtra("species");
            }
        }

        btnChangeqty = (Button) findViewById(R.id.btnchangeqty);
        btnBack = (ImageButton) findViewById(R.id.btn_title_left);

        final TextView txtHeader = (TextView) findViewById(R.id.txtHeader_QuantityTransfer);
        lvPonds = (ListView) findViewById(R.id.lv_ponds);
        lvPonds.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String[] value =   getResources().getStringArray(R.array.province);

                txtHeader.setText(setCaseTo(pondList.get(position).getPondID()+"", quantity));
            }
        });

        txtHeader.setText(setCaseTo(To, quantity));
        populatelistview();

        btnChangeqty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toasting(cur);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void populatelistview() {
//        pondList
        db.open();
        cur = db.getPondOfFarm(id + "");

        if (cur != null) {
//                Helper.toastShort(activity, cursor.getCount()+"");
            if (cur.getCount() > 0){
                pondList = new ArrayList<>();
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
                    pondList.add(custInfoObject);
                }
            }
        }


        if (pondList.size() > 0){
            adapterQuantityTransfer = new Adapter_QuantityTransfer(context, R.layout.item_lv_pondinfo, pondList);
            lvPonds.setAdapter(adapterQuantityTransfer);
        }
    }

    private void toasting(Cursor cur) {
        Helper.toastShort(activity, "pid " + id + " xpondsize: " + pondList.size() + " xcursor:" + cur.getColumnCount() + " " + cur.getCount());
    }

    private Spanned setCaseTo(String quantityTo, String quantity) {
        if (quantityTo.equalsIgnoreCase("n/a")){
            quantityTo = "";
        }else{
            quantityTo = "Case# "+quantityTo;
        }
        return Html.fromHtml(
                "<big>"+
                "Transfer <strong><u>" + quantity + "pcs</u></strong> " + "of <b>"+specie+"</b> " +
                "from <b>Case# " + pondid + "</b>" + " to " +
                "<b><u>" + quantityTo + "</u></b>."+"</big>"
        );
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
