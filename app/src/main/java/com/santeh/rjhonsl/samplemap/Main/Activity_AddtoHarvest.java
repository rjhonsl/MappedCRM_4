package com.santeh.rjhonsl.samplemap.Main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.santeh.rjhonsl.samplemap.DBase.GPSQuery;
import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.Helper;

import java.util.Calendar;

/**
 * Created by rjhonsl on 2/24/2016.
 */
public class Activity_AddtoHarvest extends FragmentActivity implements  DatePickerDialog.OnDateSetListener{

    Activity activity;
    Context context;

    GPSQuery db;

    public static final String DATEPICKER_TAG = "datepicker";

    Intent intent;
    int y,m,d;

    int casenum = 0, id = 0;
    String species = "", datestocked = "";

    DatePickerDialog datePickerDialog;
    EditText edtFinalAbw, edtTotalFeedinKilos, edtCaseNumber, edtSpecies, edtDateofHarvest, edtDaysOfCulture, edtDateOfStocking, edtFCR, edtPricePerkilo, edtTotalWeightHarvested;
    LinearLayout llHarvestInfo, llbottomButton, llNoHarvestInfo;
    CheckBox chkNoHarvestInfo;
    ImageButton btnAddToHarvest, btnTitleLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtoharvest);

        db = new GPSQuery(this);
        db.open();

        activity = this;
        context = Activity_AddtoHarvest.this;

        intent = getIntent();
        Helper.random.hideKeyboardOnLoad(activity);



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

        Helper.convert.convertDateTimeStringToMilis_DB_Format(datestocked);


        edtFinalAbw = (EditText) findViewById(R.id.edt_finalabw);
        edtCaseNumber = (EditText) findViewById(R.id.edt_casenumber);
        edtSpecies = (EditText) findViewById(R.id.edt_specie);
        edtTotalFeedinKilos = (EditText) findViewById(R.id.edt_totalKiloOfFeedsConsumed);
        edtDateofHarvest = (EditText) findViewById(R.id.edt_dateOfHarvest);
        edtDaysOfCulture = (EditText) findViewById(R.id.edt_daysofculture);
        edtDateOfStocking = (EditText) findViewById(R.id.edt_dateOfStocking);
        edtFCR = (EditText) findViewById(R.id.edt_fcr);
        edtPricePerkilo = (EditText) findViewById(R.id.edt_priceperkilo);
        edtTotalWeightHarvested = (EditText) findViewById(R.id.edt_totalWeightOnPond);
        llHarvestInfo = (LinearLayout) findViewById(R.id.ll_harvestInfo);
        chkNoHarvestInfo = (CheckBox) findViewById(R.id.chk_noHarvestInfo);
        llbottomButton = (LinearLayout) findViewById(R.id.ll_bottombutton);
        btnAddToHarvest = (ImageButton) findViewById(R.id.btn_ok_addtoharvest);
        llNoHarvestInfo = (LinearLayout) findViewById(R.id.ll_noharvestinfo);
        btnTitleLeft = (ImageButton) findViewById(R.id.btn_title_left);

        edtCaseNumber.setText(casenum+"");
        edtSpecies.setText(species+"");
        edtDateOfStocking.setText(datestocked);


        chkNoHarvestInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (chkNoHarvestInfo.isChecked()) {
                    Helper.animate.slideToTop(llHarvestInfo, llHarvestInfo.getHeight(), 0.0f, View.GONE);
                } else {

                    TranslateAnimation animate = new TranslateAnimation(0,0,-llHarvestInfo.getHeight(),0);
                    animate.setDuration(300);
                    animate.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            llHarvestInfo.setVisibility(View.VISIBLE);
                            llHarvestInfo.animate()
                                    .alpha(0.2f)
                                    .setStartDelay(100)
                                    .setDuration(200)
                                    .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            super.onAnimationEnd(animation);
                                        }
                                    });
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            llHarvestInfo.setAlpha(1.0f);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    llHarvestInfo.startAnimation(animate);
                }
            }
        });

        btnTitleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        edtPricePerkilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strwhole = null, strdeci = null;
                if (edtPricePerkilo.getText().toString().equalsIgnoreCase("")) {
                    strwhole = "1";
                    strdeci = "0";
                } else {

                    String[] holder = Helper.random.splitter(edtPricePerkilo.getText().toString(), "\\.");

                    Log.d("Float", "before: whole");
                    strwhole = holder[0];
                    Log.d("Float", "before decimal");
                    strdeci = holder[1];
                }


                final Dialog d = Helper.dialog.decimalPicker(activity, "Price", 1, 999);
                d.show();
                Button set = (Button) d.findViewById(R.id.btn_decimalpicker_set);
                final NumberPicker nbpwhole = (NumberPicker) d.findViewById(R.id.dialog_decipicker_whole);
                final NumberPicker nbpdecimal = (NumberPicker) d.findViewById(R.id.dialog_decipicker_deci);

                nbpdecimal.setValue(Integer.parseInt(strdeci));
                nbpwhole.setValue(Integer.parseInt(strwhole));

                set.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.hide();
                        nbpwhole.clearFocus();
                        nbpdecimal.clearFocus();
                        edtPricePerkilo.setText(nbpwhole.getValue() + "." + nbpdecimal.getValue());
                    }
                });

            }
        });

        btnAddToHarvest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkNoHarvestInfo.isChecked()) {
                    if (edtDateofHarvest.getText().toString().equalsIgnoreCase("")) {
                        Helper.toast.short_(activity, "Incomplete fields");
                    }else{
                        saveHarvestinfo();
                    }
                }else {
                    if (edtFinalAbw.getText().toString().equalsIgnoreCase("") || edtTotalFeedinKilos.getText().toString().equalsIgnoreCase("")||
                            edtFCR.getText().toString().equalsIgnoreCase("") || edtPricePerkilo.getText().toString().equalsIgnoreCase("") || edtTotalWeightHarvested.getText().toString().equalsIgnoreCase("")) {
                        Helper.toast.short_(activity, "Incomplete fields");
                    }else{
                        saveHarvestinfo();
                    }
                }
            }
        });

        edtFCR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strwhole = null, strdeci = null;
                if (edtFCR.getText().toString().equalsIgnoreCase("")){
                    strwhole = "1";
                    strdeci = "0";
                }else {

                    String[] holder = Helper.random.splitter(edtFCR.getText().toString(), "\\.");

                    Log.d("Float", "before: whole");
                    strwhole = holder[0];
                    Log.d("Float", "before decimal");
                    strdeci = holder[1];
                }


                final Dialog d = Helper.dialog.decimalPicker(activity, "FCR", 1, 999);
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
                        nbpwhole.clearFocus();
                        nbpdecimal.clearFocus();
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

                final Dialog d = Helper.dialog.numberPicker(activity,"Final ABW", 1, 9999);
                Button set = (Button) d.findViewById(R.id.btn_numberpicker_set);
                final NumberPicker nbp = (NumberPicker) d.findViewById(R.id.dialog_numberpicker);
                nbp.setValue(initialValue);

                set.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nbp.clearFocus();
                        edtFinalAbw.setText(nbp.getValue() + "g");
                        d.dismiss();
                    }
                });
            }
        });


        edtTotalFeedinKilos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int kilos = Helper.random.removeSuffix(edtTotalFeedinKilos.getText().toString(), "kg");

                final Dialog d = Helper.dialog.numberPicker(activity, "Total Feed Consumed(kg)", 1, 999999);
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

        edtTotalWeightHarvested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int kilos = Helper.random.removeSuffix(edtTotalWeightHarvested.getText().toString(), "kg");

                final Dialog d = Helper.dialog.numberPicker(activity, "Weight Harvested(kg)", 1, 999999);
                Button set = (Button) d.findViewById(R.id.btn_numberpicker_set);
                final NumberPicker nbp = (NumberPicker) d.findViewById(R.id.dialog_numberpicker);
                nbp.setValue(kilos);

                set.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nbp.clearFocus();
                        edtTotalWeightHarvested.setText(nbp.getValue()+"kg");
                        d.dismiss();
                    }
                });
            }
        });
    }

    private void saveHarvestinfo() {
        final boolean isNoHarvestInfoChecked = chkNoHarvestInfo.isChecked();
        final Dialog d = Helper.dialog.themedYesNo(activity, "Mark Case# " + casenum + " as harvested?", "Harvest", "NO", "YES", R.color.red);
        Button yes = (Button) d.findViewById(R.id.btn_dialog_yesno_opt2);
        Button no = (Button) d.findViewById(R.id.btn_dialog_yesno_opt1);


        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nullval = "null"; long result;
                if (isNoHarvestInfoChecked) {
                    result = db.insertHarvestInfo(id + "", edtCaseNumber.getText().toString(), edtSpecies.getText().toString(), edtDateofHarvest.getText().toString(),
                            nullval, nullval, nullval, nullval, nullval);

                } else {
                    result = db.insertHarvestInfo(
                            id + "",
                            edtCaseNumber.getText().toString(),
                            edtSpecies.getText().toString(),
                            edtDateofHarvest.getText().toString(),
                            Helper.random.removeSuffix(edtFinalAbw.getText().toString(), "g")+"",
                            Helper.random.removeSuffix(edtTotalFeedinKilos.getText().toString(), "kg") + "",
                            edtFCR.getText().toString(),
                            edtPricePerkilo.getText().toString(),
                            Helper.random.removeSuffix(edtTotalWeightHarvested.getText().toString(), "kg") + ""
                    );
                }

                if (result > 0){
                    db.updatePondAsHarvested(id + "");

                    Helper.toast.short_(activity, "Pond has been moved to Harvested");
                    finish();

                }else{
                    Helper.toast.short_(activity, "Saving Failed. Please try again");
                }

                d.hide();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.hide();
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        edtDateofHarvest.setText(year + "-" + (month + 1) + "-" + day);

        long dateSelected = Helper.convert.convertDateToLong(day, month + 1, year);
        long dateStock = Helper.convert.convertDateToMilis_DB_Format(datestocked);

        long difference = Helper.convert.getDateDifference(dateSelected, dateStock);

        if (difference > 1){
            edtDaysOfCulture.setText(String.valueOf(difference)+" days");
        }else{
            edtDaysOfCulture.setText(String.valueOf(difference)+" day");
        }

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


