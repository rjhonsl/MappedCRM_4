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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posttext);
        activity = this;
        context = Activity_PostText.this;
        Helper.hideKeyboardOnLoad(this);

        edtPostContent = (EditText) findViewById(R.id.edtPostSomethingContent);

        LinearLayout llpostNow = (LinearLayout) findViewById(R.id.ll_postnow);
        LinearLayout lltop = (LinearLayout) findViewById(R.id.ll_top_postsomething);

        llpostNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.toastShort(activity, edtPostContent.getText().toString());
            }
        });

        lltop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }



    private void startPostSomethingToWeb(final String sqlString) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, Helper.variables.URL_PHP_RAW_QUERY_POST_INSERT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        if (!response.substring(1, 2).equalsIgnoreCase("0")) {

                        } else {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Helper.toastLong(activity, error.toString());
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

                params.put("sql", sqlString);


                return params;
            }
        };

        MyVolleyAPI api = new MyVolleyAPI();
        api.addToReqQueue(postRequest, context);
    }
}
