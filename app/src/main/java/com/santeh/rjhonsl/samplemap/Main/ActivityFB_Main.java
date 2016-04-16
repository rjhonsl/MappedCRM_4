package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.Helper;

/**
 * Created by rjhonsl on 4/14/2016.
 */
public class ActivityFB_Main extends AppCompatActivity {

    Activity activity;
    Context context;
    ImageView imageview;
    ImageButton btnPopupOptions;
    String finalHashtag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityfb_main);
        activity = ActivityFB_Main.this;
        context = ActivityFB_Main.this;
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar2);
        myToolbar.setBackgroundColor(getResources().getColor(R.color.blue_400));

        setSupportActionBar(myToolbar);
        myToolbar.inflateMenu(R.menu.menu_search);

        ActionBar ab = getSupportActionBar();



        CardView cardview1 = (CardView) findViewById(R.id.post1);
        TextView txtLinkToComments1 = (TextView) cardview1.findViewById(R.id.txtlinkcomments);
        TextView txtlinktoShare1 = (TextView) cardview1.findViewById(R.id.txtlinkshare);
        final ImageButton btn1 = (ImageButton) cardview1.findViewById(R.id.btnPostOptions);

        txtLinkToComments1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, Activity_Comments.class);
                startActivity(intent);
                
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(activity, btn1);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId()== R.id.reportPostAsSpam){
                            Helper.toastShort(activity,"Reported as spam");
                        }else if (item.getItemId()== R.id.copyPostText){
                            Helper.toastShort(activity,"Post Copied");
                        }
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });

        CardView cardview2 = (CardView) findViewById(R.id.post2);
        CardView cardview5 = (CardView) findViewById(R.id.post5);
        final ImageButton btn2 = (ImageButton) cardview2.findViewById(R.id.btnPostOptions);
        ImageView justimage = (ImageView) cardview2.findViewById(R.id.justimage);
        ImageView justimage5 = (ImageView) cardview5.findViewById(R.id.justimage);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.wall4, options);
        Bitmap largeIcon5 = BitmapFactory.decodeResource(getResources(), R.drawable.wall, options);

        justimage.setImageBitmap(largeIcon);
        justimage5.setImageBitmap(largeIcon5);


        SpannableString ss = new SpannableString("Here @ Malangaan Rock formation! #natureTrip");

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
//                startActivity(new Intent(MyActivity.this, NextActivity.class));
                Helper.toastShort(activity, "hashtag: " + finalHashtag);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };

        ForegroundColorSpan fcs = new ForegroundColorSpan(Color.BLUE);

        int startint = 0 , endint=0, isspace = 0;
        finalHashtag = "";
        for (int i = 0; i < ss.length(); i++) {

            if (ss.charAt(i) == '#') {
                startint = i;
            }

            if (startint!=0 && (endint == 0)){
                finalHashtag = finalHashtag + ss.charAt(i);
            }

            if (startint != 0 || ss.charAt(i) == ' ' ) {
                    endint =  i;
            }

        }
        ss.setSpan(clickableSpan, startint, endint, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(fcs, startint, endint, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        final TextView txtpost = (TextView) cardview2.findViewById(R.id.txtpost);
        txtpost.setText(ss);
        txtpost.setMovementMethod(LinkMovementMethod.getInstance());
        txtpost.setHighlightColor(Color.TRANSPARENT);




        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(activity, btn2);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId()== R.id.reportPostAsSpam){
                            Helper.toastShort(activity,"Reported as spam");
                        }else if (item.getItemId()== R.id.copyPostText){
                            Helper.toastShort(activity,"Post Copied");
                        }

                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });


        CardView cardview3 = (CardView) findViewById(R.id.post3);
        final ImageButton btn3 = (ImageButton) cardview3.findViewById(R.id.btnPostOptions);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(activity, btn3);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId()== R.id.reportPostAsSpam){
                            Helper.toastShort(activity,"Reported as spam");
                        }else if (item.getItemId()== R.id.copyPostText){
                            Helper.toastShort(activity,"Post Copied");
                        }
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });



        imageview = (ImageView) findViewById(R.id.justimage);

        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, Activity_ViewImage.class);
                startActivity(intent);
            }
        });

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setCustomView(R.menu.menu_search);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        SearchView searchView =
//                (SearchView) MenuItemCompat.getActionView(searchItem);

        // Define the listener
        MenuItemCompat.OnActionExpandListener expandListener = new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when action item collapses
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true;  // Return true to expand action view
            }
        };

        // Get the MenuItem for the action item
        MenuItem actionMenuItem = menu.findItem(R.id.action_search);

        // Assign the listener to that action item
        MenuItemCompat.setOnActionExpandListener(actionMenuItem, expandListener);

        return true;
    }
}
