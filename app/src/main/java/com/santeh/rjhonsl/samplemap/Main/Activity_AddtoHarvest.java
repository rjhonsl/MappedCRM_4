package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.Helper;

import java.util.Calendar;

/**
 * Created by rjhonsl on 2/24/2016.
 */
public class Activity_AddtoHarvest extends FragmentActivity implements  DatePickerDialog.OnDateSetListener{

    Activity activity;
    Context context;

    public static final String DATEPICKER_TAG = "datepicker";

    Intent intent;
    int y,m,d;

    int casenum = 0, id = 0;
    String species = "", datestocked = "";

    DatePickerDialog datePickerDialog;
    EditText edtFinalAbw, edtTotalFeedinKilos, edtCaseNumber, edtSpecies, edtDateofHarvest, edtDaysOfCulture, edtDateOfStocking, edtFCR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtoharvest);

        activity = this;
        context = Activity_AddtoHarvest.this;

        intent = getIntent();
        Helper.hideKeyboardOnLoad(activity);


        if (intent.hasExtra("id")) {
            id = intent.getIntExtra("id",0);
        }

        if (intent.hasExtra("casenum")){
            casenum = intent.getIntExtra("casenum",0);
        }

        if (intent.hasExtra("species")){
            species = intent.getStringExtra("species");
        }

        if (intent.hasExtra("datestocked")){
            datestocked = intent.getStringExtra("datestocked");
        }

        final Calendar calendar = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        Helper.convertDateTimeStringToMilis_DB_Format(datestocked);


        edtFinalAbw = (EditText) findViewById(R.id.edt_finalabw);
        edtCaseNumber = (EditText) findViewById(R.id.edt_casenumber);
        edtSpecies = (EditText) findViewById(R.id.edt_specie);
        edtTotalFeedinKilos = (EditText) findViewById(R.id.edt_totalKiloOfFeedsConsumed);
        edtDateofHarvest = (EditText) findViewById(R.id.edt_dateOfHarvest);
        edtDaysOfCulture = (EditText) findViewById(R.id.edt_daysofculture);
        edtDateOfStocking = (EditText) findViewById(R.id.edt_dateOfStocking);
        edtFCR = (EditText) findViewById(R.id.edt_fcr);

        edtCaseNumber.setText(casenum+"");
        edtSpecies.setText(species+"");
        edtDateOfStocking.setText(datestocked);


        edtFCR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strwhole, strdeci;
                if (edtFCR.getText().toString().equalsIgnoreCase("")){
                    strwhole = "1";
                    strdeci = "0";
                }else {
                    strwhole = Helper.splitter(edtFCR.getText().toString(), ".")[0];
                    strdeci = Helper.splitter(edtFCR.getText().toString(), ".")[1];
                }


                final Dialog d = Helper.createDecimaldDialog(activity, "FCR", 1, 999);
                d.show();
                Button set = (Button) d.findViewById(R.id.btn_decimalpicker_set);
                final NumberPicker nbpwhole = (NumberPicker) d.findViewById(R.id.dialog_decipicker_whole);
                final NumberPicker nbpdecimal= (NumberPicker) d.findViewById(R.id.dialog_decipicker_deci);

                nbpdecimal.setValue(Integer.parseInt(strdeci));
                nbpwhole.setValue(Integer.parseInt(strwhole));

                set.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.hide();
                        edtFCR.setText(nbpwhole.getValue()+"."+nbpdecimal.getValue());
                    }
                });


            }
        });

        edtDateofHarvest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.setYearRange(1920, 2037);
                datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
            }
        });


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

                final Dialog d = Helper.createNumberPickerdDialog(activity,"Final ABW", 1, 9999);
                Button set = (Button) d.findViewById(R.id.btn_numberpicker_set);
                final NumberPicker nbp = (NumberPicker) d.findViewById(R.id.dialog_numberpicker);
                nbp.setValue(initialValue);

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


        edtTotalFeedinKilos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int kilos = Helper.removeUnits(edtTotalFeedinKilos.getText().toString(), "kg");

                final Dialog d = Helper.createNumberPickerdDialog(activity, "Total Feed Consumed(kg)", 1, 999999);
                Button set = (Button) d.findViewById(R.id.btn_numberpicker_set);
                final NumberPicker nbp = (NumberPicker) d.findViewById(R.id.dialog_numberpicker);
                nbp.setValue(kilos);

                set.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nbp.clearFocus();
                        edtTotalFeedinKilos.setText(nbp.getValue()+"kg");
                        d.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        edtDateofHarvest.setText(year + "-" + (month + 1) + "-" + day);

        long dateSelected = Helper.convertDateToLong(day, month + 1, year);
        long dateStock = Helper.convertDateToMilis_DB_Format(datestocked);

        long difference = Helper.getDateDifference(dateSelected, dateStock);

        if (difference > 1){
            edtDaysOfCulture.setText(String.valueOf(difference)+" days");
        }else{
            edtDaysOfCulture.setText(String.valueOf(difference)+" day");
        }


        y = year;
        m = month + 1;
        d = day;
    }

}


