package com.santeh.rjhonsl.samplemap.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.santeh.rjhonsl.samplemap.R;

/**
 * Created by rjhonsl on 4/12/2016.
 */
public class Activity_PondQuanityTransfer extends FragmentActivity {

    Intent passedIntent;
    String quantity;
    int pondid, id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quantitytransfer);
        TextView txttitle = (TextView) findViewById(R.id.title);
        txttitle.setText("Quantity Transfer");


        passedIntent = getIntent();
        if (passedIntent != null) {
            if (passedIntent.hasExtra("quantity")){
                quantity = passedIntent.getStringExtra("quantity");
            }
            if (passedIntent.hasExtra("pondid")){
                pondid = passedIntent.getIntExtra("pondid",0);
            }
            if (passedIntent.hasExtra("id")){
                id = passedIntent.getIntExtra("id",0);
            }
        }


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
