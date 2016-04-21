package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.Helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by rjhonsl on 4/14/2016.
 */
public class ActivityFB_Main extends AppCompatActivity {

    Activity activity;
    Context context;
    ImageView imageview;
    ImageButton btnPopupOptions;
    String finalHashtag;
    ScrollView scrollView;
    private static final int SELECT_PICTURE = 1;
    private static final int SELECT_FILE = 2;
    private String selectedFilePath;

    FloatingActionButton fabAddPost;
    LinearLayout llbottomwrapper;

    Boolean isBottomAnimating = false;
    Boolean isFabSelected = false;

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


        fabAddPost = (FloatingActionButton) findViewById(R.id.fab_addpost);
        scrollView = (ScrollView) findViewById(R.id.scrollView_fbmain);
        llbottomwrapper = (LinearLayout) findViewById(R.id.ll_wrapper_bottom);
        CardView cardview1 = (CardView) findViewById(R.id.post1);
        TextView txtLinkToComments1 = (TextView) cardview1.findViewById(R.id.txtlinkcomments);
        final ImageButton btn1 = (ImageButton) cardview1.findViewById(R.id.btnPostOptions);

        LinearLayout llpostSomething = (LinearLayout) findViewById(R.id.ll_postSomething);
        LinearLayout lluploadPhoto = (LinearLayout) findViewById(R.id.ll_uploadPhoto);
        LinearLayout lluploadFile = (LinearLayout) findViewById(R.id.ll_uploadfile);
        LinearLayout llshare = (LinearLayout) cardview1.findViewById(R.id.ll_share);

        llshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "Text I want to share.";
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);

                startActivity(Intent.createChooser(share, "Share to:"));
            }
        });

        llpostSomething.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        lluploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        lluploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



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


        ab.setDisplayHomeAsUpEnabled(true);



//        scrollView.setOnGenericMotionListener(new View.OnGenericMotionListener() {
//            @Override
//            public boolean onGenericMotion(View v, MotionEvent event) {
//                if (llbottomwrapper.getVisibility() == View.VISIBLE) {
////                    llbottomwrapper.setVisibility(View.GONE);
////                    fabAddPost.setVisibility(View.VISIBLE);
//                    isFabSelected = false;
//                    animateBottom();
//                }
//                return false;
//            }
//        });

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (llbottomwrapper.getVisibility() == View.VISIBLE) {
//                    llbottomwrapper.setVisibility(View.GONE);
//                    fabAddPost.setVisibility(View.VISIBLE);
                    isFabSelected = false;
                    if (!isBottomAnimating){
                        animateBottom();
                    }

                }
            }
        });

        fabAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (llbottomwrapper.getVisibility() == View.VISIBLE) {
////                    llbottomwrapper.setVisibility(View.GONE);
////                    fabAddPost.setVisibility(View.VISIBLE);
                isFabSelected = true;

                animateBottom();
//                } else {
////                    llbottomwrapper.setVisibility(View.VISIBLE);
////                    fabAddPost.setVisibility(View.GONE);
//                    isFabSelected = false;
//                    animateBottom();
//                }
            }
        });

        lluploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), SELECT_PICTURE);
            }
        });



        lluploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                String[] mimetypes = {
                        "text/*",
                        "application/msword",
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                        "application/vnd.ms-excel",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                        "application/vnd.ms-powerpoint",
                        "application/vnd.openxmlformats-officedocument.presentationml.presentation",
                        "application/x-rar-compressed",
                        "application/zip"
                };
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), SELECT_FILE);
            }
        });


    }

    private void animateBottom(){
        Animation animatelayout;
        int visibility;

        if (isFabSelected){
            animatelayout  = AnimationUtils.loadAnimation(context, R.anim.bottom_up);
            animatelayout.setDuration(300);
            animatelayout.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    llbottomwrapper.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            visibility = View.VISIBLE;

            fabAddPost.setVisibility(View.GONE);
            llbottomwrapper.startAnimation(animatelayout);

        }
        else{
            animatelayout  = AnimationUtils.loadAnimation(context, R.anim.bottom_down);
            animatelayout.setDuration(300);
            animatelayout.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    isBottomAnimating = true;
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    isBottomAnimating = false;
                    llbottomwrapper.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            fabAddPost.setVisibility(View.VISIBLE);
            llbottomwrapper.startAnimation(animatelayout);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

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





    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE || requestCode == SELECT_FILE) {
                Uri selectedFileUri = data.getData();

                File file = getPath(selectedFileUri);
                selectedFilePath = file.getAbsolutePath();

                Helper.toastLong(activity, selectedFilePath);
                try {
                    FileInputStream is = new FileInputStream(file);
                    FileOutputStream os = new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public File getPath(Uri uri) {
        return new File(uri.getPath());
    }
}
