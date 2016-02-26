package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.Helper;

/**
 * Created by rjhonsl on 2/24/2016.
 */
public class Activity_AddtoHarvest extends FragmentActivity{

    Activity activity;
    Context context;


    EditText edtFinalAbw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtoharvest);

        activity = this;
        context = Activity_AddtoHarvest.this;

        Helper.hideKeyboardOnLoad(activity);


        edtFinalAbw = (EditText) findViewById(R.id.edt_finalabw);
        edtFinalAbw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int initialValue;

                if (edtFinalAbw.getText().toString().equalsIgnoreCase("") || edtFinalAbw.getText().toString().equalsIgnoreCase(null)){
                    initialValue = 1;
                }else {
                    if (edtFinalAbw.getText().toString().substring(edtFinalAbw.length()-1,edtFinalAbw.length()).equalsIgnoreCase("g")){
                        initialValue = Integer.parseInt(edtFinalAbw.getText().toString().substring(0, edtFinalAbw.length()-1 ));
                    }else{
                        initialValue = Integer.parseInt(edtFinalAbw.getText().toString());
                    }
                }

                final Dialog d = Helper.createNumberPickerdDialog(activity, initialValue);
                Button set = (Button) d.findViewById(R.id.btn_numberpicker_set);
                final NumberPicker nbp = (NumberPicker) d.findViewById(R.id.dialog_numberpicker);

                set.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nbp.clearFocus();
                        edtFinalAbw.setText(nbp.getValue()+"g");
                        d.dismiss();
                    }
                });
            }
        });
    }
}


