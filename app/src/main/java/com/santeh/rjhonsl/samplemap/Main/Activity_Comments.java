package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.Helper;

/**
 * Created by rjhonsl on 4/16/2016.
 */
public class Activity_Comments extends AppCompatActivity {

    Activity activity;
    Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        activity = this;
        context = Activity_Comments.this;

        LinearLayout llcomment = (LinearLayout) findViewById(R.id.ll_top_comments);
        llcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final ImageButton btnAddComment = (ImageButton) findViewById(R.id.btnAddcomment);
        final EditText edtAddComment = (EditText) findViewById(R.id.edtAddComment);

        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.toastShort(activity, edtAddComment.getText().toString()+"");
            }
        });

        edtAddComment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    btnAddComment.setVisibility(View.VISIBLE);
                }else {
                    btnAddComment.setVisibility(View.GONE);
                }
            }
        });

    }
}
