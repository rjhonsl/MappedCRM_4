package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.FusedLocation;
import com.santeh.rjhonsl.samplemap.Utils.Helper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

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
    ListView lvFeeds;
    private static final int SELECT_PICTURE = 1;
    private static final int SELECT_FILE = 2;
    private String selectedFilePath;

    FloatingActionButton fabAddPost;
    LinearLayout llbottomwrapper;

    FusedLocation fusedLocation;

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
        ab.setDisplayHomeAsUpEnabled(true);

        fusedLocation = new FusedLocation(context, activity);
        fusedLocation.connectToApiClient();



        fabAddPost = (FloatingActionButton) findViewById(R.id.fab_addpost);
//        scrollView = (ScrollView) findViewById(R.id.scrollView_fbmain);
        llbottomwrapper = (LinearLayout) findViewById(R.id.ll_wrapper_bottom);
        lvFeeds = (ListView) findViewById(R.id.lv_feeds);
//        CardView cardview1 = (CardView) findViewById(R.id.post1);
//        TextView txtLinkToComments1 = (TextView) cardview1.findViewById(R.id.txtlinkcomments);
//        final ImageButton btn1 = (ImageButton) cardview1.findViewById(R.id.btnPostOptions);
//
        LinearLayout llpostSomething = (LinearLayout) findViewById(R.id.ll_postSomething);
        LinearLayout lluploadPhoto = (LinearLayout) findViewById(R.id.ll_uploadPhoto);
        LinearLayout lluploadFile = (LinearLayout) findViewById(R.id.ll_uploadfile);
//        LinearLayout llshare = (LinearLayout) cardview1.findViewById(R.id.ll_share);
//
//        llshare.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String message = "Text I want to share.";
//                Intent share = new Intent(Intent.ACTION_SEND);
//                share.setType("text/plain");
//                share.putExtra(Intent.EXTRA_TEXT, message);
//
//                startActivity(Intent.createChooser(share, "Share to:"));
//            }
//        });
//

//
//
//        txtLinkToComments1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(activity, Activity_Comments.class);
//                startActivity(intent);
//
//            }
//        });
//
//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PopupMenu popup = new PopupMenu(activity, btn1);
//                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
//
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    public boolean onMenuItemClick(MenuItem item) {
//                        if (item.getItemId()== R.id.reportPostAsSpam){
//                            Helper.toastShort(activity,"Reported as spam");
//                        }else if (item.getItemId()== R.id.copyPostText){
//                            Helper.toastShort(activity,"Post Copied");
//                        }
//                        return true;
//                    }
//                });
//
//                popup.show();//showing popup menu
//            }
//        });
//
//        CardView cardview2 = (CardView) findViewById(R.id.post2);
//        CardView cardview5 = (CardView) findViewById(R.id.post5);
//        final ImageButton btn2 = (ImageButton) cardview2.findViewById(R.id.btnPostOptions);
//        ImageView justimage = (ImageView) cardview2.findViewById(R.id.justimage);
//        ImageView justimage5 = (ImageView) cardview5.findViewById(R.id.justimage);
//
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = 4;
//        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.wall4, options);
//        Bitmap largeIcon5 = BitmapFactory.decodeResource(getResources(), R.drawable.wall, options);
//
//        justimage.setImageBitmap(largeIcon);
//        justimage5.setImageBitmap(largeIcon5);
//
//
//        SpannableString ss = new SpannableString("Here @ Malangaan Rock formation! #natureTrip");
//
//        ClickableSpan clickableSpan = new ClickableSpan() {
//            @Override
//            public void onClick(View textView) {
////                startActivity(new Intent(MyActivity.this, NextActivity.class));
//                Helper.toastShort(activity, "hashtag: " + finalHashtag);
//            }
//            @Override
//            public void updateDrawState(TextPaint ds) {
//                super.updateDrawState(ds);
//                ds.setUnderlineText(false);
//            }
//        };
//
//        ForegroundColorSpan fcs = new ForegroundColorSpan(Color.BLUE);
//
//        int startint = 0 , endint=0, isspace = 0;
//        finalHashtag = "";
//        for (int i = 0; i < ss.length(); i++) {
//
//            if (ss.charAt(i) == '#') {
//                startint = i;
//            }
//
//            if (startint!=0 && (endint == 0)){
//                finalHashtag = finalHashtag + ss.charAt(i);
//            }
//
//            if (startint != 0 || ss.charAt(i) == ' ' ) {
//                    endint =  i;
//            }
//
//        }
//        ss.setSpan(clickableSpan, startint, endint, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        ss.setSpan(fcs, startint, endint, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//
//        final TextView txtpost = (TextView) cardview2.findViewById(R.id.txtpost);
//        txtpost.setText(ss);
//        txtpost.setMovementMethod(LinkMovementMethod.getInstance());
//        txtpost.setHighlightColor(Color.TRANSPARENT);
//
//
//        btn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PopupMenu popup = new PopupMenu(activity, btn2);
//                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
//
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    public boolean onMenuItemClick(MenuItem item) {
//                        if (item.getItemId()== R.id.reportPostAsSpam){
//                            Helper.toastShort(activity,"Reported as spam");
//                        }else if (item.getItemId()== R.id.copyPostText){
//                            Helper.toastShort(activity,"Post Copied");
//                        }
//
//                        return true;
//                    }
//                });
//
//                popup.show();//showing popup menu
//            }
//        });


//        CardView cardview3 = (CardView) findViewById(R.id.post3);
//        final ImageButton btn3 = (ImageButton) cardview3.findViewById(R.id.btnPostOptions);
//        btn3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PopupMenu popup = new PopupMenu(activity, btn3);
//                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
//
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    public boolean onMenuItemClick(MenuItem item) {
//                        if (item.getItemId()== R.id.reportPostAsSpam){
//                            Helper.toastShort(activity,"Reported as spam");
//                        }else if (item.getItemId()== R.id.copyPostText){
//                            Helper.toastShort(activity,"Post Copied");
//                        }
//                        return true;
//                    }
//                });
//
//                popup.show();//showing popup menu
//            }
//        });



//        imageview = (ImageView) findViewById(R.id.justimage);
//
//        imageview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(activity, Activity_ViewImage.class);
//                startActivity(intent);
//            }
//        });






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

        lvFeeds.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (llbottomwrapper.getVisibility() == View.VISIBLE) {
                    isFabSelected = false;
                    if (!isBottomAnimating){
                        animateBottom();
                    }
                }
                return false;
            }
        });

//        lvFeeds.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//            @Override
//            public void onScrollChanged() {
//                if (llbottomwrapper.getVisibility() == View.VISIBLE) {
//                    isFabSelected = false;
//                    if (!isBottomAnimating){
//                        animateBottom();
//                    }
//                }
//            }
//        });

//        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//            @Override
//            public void onScrollChanged() {
//                if (llbottomwrapper.getVisibility() == View.VISIBLE) {
////                    llbottomwrapper.setVisibility(View.GONE);
////                    fabAddPost.setVisibility(View.VISIBLE);
//                    isFabSelected = false;
//                    if (!isBottomAnimating){
//                        animateBottom();
//                    }
//                }
//            }
//        });

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


        llpostSomething.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, Activity_PostText.class);
                startActivity(intent);
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

                uploadImage(selectedFileUri);
//
//                startPostSomethingToWeb(file, selectedFilePath, selectedFileUri);
//                Bitmap bitmap = null;
//                try {
//                    bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), selectedFileUri);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Helper.toastLong(activity, selectedFilePath);

                try {
                    FileInputStream is = new FileInputStream(file);
                    FileOutputStream os = new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }


    }






    private void uploadImage(final Uri uriFilepath){
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Helper.variables.URL_PHP_INSERT_FEEDPOST_PHOTO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(activity, s , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(activity, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uriFilepath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String image = getStringImage(bitmap);

                //Getting Image Name
                String name = "name";

                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put("keyimage", image);
                params.put("keyname", name);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

//    private void startPostSomethingToWeb(final File file, final String filepath, final Uri uriFilepath) {
//        StringRequest postRequest = new StringRequest(Request.Method.POST, Helper.variables.URL_PHP_INSERT_FEEDPOST_PHOTO,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(final String response) {
//                        if (!response.substring(1, 2).equalsIgnoreCase("0")) {
//                            Helper.createCustomThemedDialogOKOnly_scrolling(activity,"TITLE" ,response+"", "OK", R.color.red);
////                            Helper.toastShort(activity, "Response: "+response);
//                        } else {
//                            Helper.toastShort(activity, "Error: " + response);
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Helper.toastLong(activity, "error: "+error.toString());
//                    }
//                })
//        {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                params.put("username", Helper.variables.getGlobalVar_currentUserName(activity));
//                params.put("password", Helper.variables.getGlobalVar_currentUserPassword(activity));
//                params.put("deviceid", Helper.getMacAddress(context));
//                params.put("userid", Helper.variables.getGlobalVar_currentUserID(activity) + "");
//                params.put("userlvl", Helper.variables.getGlobalVar_currentLevel(activity) + "");
//                Bitmap bitmap = null;
//                try {
//                    bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uriFilepath);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                params.put("content_type", 0 + "");
//                params.put("content_desc", filepath + "");
//                params.put("content_imgurl", "");
//                params.put("content_eventid", "");
//                params.put("content_fileurl",  "");
//                params.put("imagearray",  getStringImage(bitmap)+"");
//                params.put("content_fetchAt", System.currentTimeMillis() + "");
//
//
//                String sqlString = "INSERT INTO `feed_main_` " +
//                        "(`feed_main_id`, `feed_main_uid`, `feed_main_date`, `feed_main_loclat`, `feed_main_loclong`, `feed_main_fetch_at`, `feed_main_seen_state`) " +
//                        "VALUES " +
//                        "(NULL, '"+Helper.variables.getGlobalVar_currentUserID(activity)+"', " +
//                        "'"+System.currentTimeMillis()+"', " +
//                        "'"+fusedLocation.getLastKnowLocation().latitude+"', " +
//                        "'"+fusedLocation.getLastKnowLocation().latitude+"', '0', '0');";
//
//
//                params.put("sql", sqlString);
//
//
//                return params;
//            }
//        };
//
//
//        MyVolleyAPI api = new MyVolleyAPI();
//        api.addToReqQueue(postRequest, context);
//    }


    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public File getPath(Uri uri) {
        return new File(uri.getPath());
    }
}
