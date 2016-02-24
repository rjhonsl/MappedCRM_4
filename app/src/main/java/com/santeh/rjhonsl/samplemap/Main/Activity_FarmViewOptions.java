package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.santeh.rjhonsl.samplemap.R;

/**
 * Created by rjhonsl on 8/24/2015.
 */
public class Activity_FarmViewOptions extends Activity{

    String customerID="", farmname="";
    Activity activity;

    TextView txtManagePonds, txtWeeklyReports;
    String farmName, lat, lng;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infowindowselect_option);
        activity = this;

        Intent intentt = getIntent();
        Bundle extras = getIntent().getExtras();


//        intent.putExtra("id", Integer.parseInt(details[0]));
//        intent.putExtra("farmname", "" + details[1]);
//        intent.putExtra("latitude", location.latitude + "");
//        intent.putExtra("longitude", location.longitude + "");
        if (intentt.hasExtra("id")) {
            id = intentt.getIntExtra("id", 0);
        }
        if (intentt.hasExtra("latitude")) {
            lat = intentt.getStringExtra("latitude");
        }
        if (intentt.hasExtra("longitude")) {
            lng = intentt.getStringExtra("longitude");
        }
        if (intentt.hasExtra("farmname")) {
            farmName = intentt.getStringExtra("farmname");
        }

        if (extras !=null){
            customerID = extras.getString("customerID");
            farmname = extras.getString("farmname");

        }else{
            customerID = "null";
        }

//        Helper.toastLong(activity, "id"+customerID);
        txtManagePonds = (TextView) findViewById(R.id.txt_farmoptions_managePonds);
        txtWeeklyReports = (TextView) findViewById(R.id.txt_farmoptions_harvest_history);


        txtManagePonds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//            int converted = Integer.parseInt(customerID.trim());
            Intent intent = new Intent(Activity_FarmViewOptions.this, Activity_ManagePonds.class);
            intent.putExtra("id", id);
            intent.putExtra("farmname", "" + farmName);
            intent.putExtra("latitude", lat);
            intent.putExtra("longitude", lng );
            startActivity(intent);

            }
        });

        txtWeeklyReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                int converted = Integer.parseInt(customerID.trim());
//                Intent intent = new Intent(Activity_FarmViewOptions.this, Activity_PondWeeklyConsumption.class);
//                intent.putExtra("id", converted );
//                intent.putExtra("farmname", farmname);
//                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
