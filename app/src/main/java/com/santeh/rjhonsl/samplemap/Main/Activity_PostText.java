package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.santeh.rjhonsl.samplemap.APIs.MyVolleyAPI;
import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.FusedLocation;
import com.santeh.rjhonsl.samplemap.Utils.Helper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rjhonsl on 4/22/2016.
 */
public class Activity_PostText extends AppCompatActivity {

    Activity activity;
    Context context;
    EditText edtPostContent;
    FusedLocation fusedLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posttext);
        activity = this;
        context = Activity_PostText.this;
        Helper.hideKeyboardOnLoad(this);

        fusedLocation = new FusedLocation(context, activity);
        fusedLocation.connectToApiClient();

        edtPostContent = (EditText) findViewById(R.id.edtPostSomethingContent);

        LinearLayout llpostNow = (LinearLayout) findViewById(R.id.ll_postnow);
        LinearLayout lltop = (LinearLayout) findViewById(R.id.ll_top_postsomething);

        llpostNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.toastShort(activity, edtPostContent.getText().toString());
                startPostSomethingToWeb();
            }
        });

        lltop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void startPostSomethingToWeb() {
        StringRequest postRequest = new StringRequest(Request.Method.POST, Helper.variables.URL_PHP_INSERT_FEEDPOST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        if (!response.substring(1, 2).equalsIgnoreCase("0")) {
                            Helper.toastShort(activity, "Error"+response);
                        } else {
                            Helper.toastShort(activity, "Posted");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Helper.toastLong(activity, "error: "+error.toString());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", Helper.variables.getGlobalVar_currentUserName(activity));
                params.put("password", Helper.variables.getGlobalVar_currentUserPassword(activity));
                params.put("deviceid", Helper.getMacAddress(context));
                params.put("userid", Helper.variables.getGlobalVar_currentUserID(activity) + "");
                params.put("userlvl", Helper.variables.getGlobalVar_currentLevel(activity) + "");

                params.put("content_type", 0 + "");
                params.put("content_desc", edtPostContent.getText().toString() + "");
                params.put("content_imgurl", "");
                params.put("content_eventid", "");
                params.put("content_fileurl",  "");
                params.put("content_fetchAt", System.currentTimeMillis() + "");


                String sqlString = "INSERT INTO `feed_main_` " +
                        "(`feed_main_id`, `feed_main_uid`, `feed_main_date`, `feed_main_loclat`, `feed_main_loclong`, `feed_main_fetch_at`, `feed_main_seen_state`) " +
                        "VALUES " +
                        "(NULL, '"+Helper.variables.getGlobalVar_currentUserID(activity)+"', " +
                        "'"+System.currentTimeMillis()+"', " +
                        "'"+fusedLocation.getLastKnowLocation().latitude+"', " +
                        "'"+fusedLocation.getLastKnowLocation().latitude+"', '0', '0');";


                params.put("sql", sqlString);


                return params;
            }
        };


        MyVolleyAPI api = new MyVolleyAPI();
        api.addToReqQueue(postRequest, context);
    }

}
