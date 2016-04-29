package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.FusedLocation;
import com.santeh.rjhonsl.samplemap.Utils.Helper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by rjhonsl on 4/14/2016.
 */
public class ActivityFB_Main extends AppCompatActivity {

    Activity activity;
    Context context;
    ImageView imageview;
    ImageButton btnPopupOptions;
    String encodedImage;
    ScrollView scrollView;
    ListView lvFeeds;
    Bitmap bitmap;
    private static final int SELECT_PICTURE = 1;
    private static final int SELECT_FILE = 2;
    private String selectedFilePath;

    ProgressDialog loading;
    FloatingActionButton fabAddPost;
    LinearLayout llbottomwrapper;

    FusedLocation fusedLocation;
    RequestParams params;
    Boolean isBottomAnimating = false;
    Boolean isFabSelected = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityfb_main);
        activity = ActivityFB_Main.this;
        context = ActivityFB_Main.this;

        params = new RequestParams();
        loading = new ProgressDialog(context);
        loading.setIndeterminate(true);
        loading.setCancelable(false);
        loading.setMessage("Uploading");

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
                isFabSelected = true;
                animateBottom();
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
            if (requestCode == SELECT_PICTURE) { //
                Uri selectedFileUri = data.getData();
                Uri filePath = data.getData();

                File file = getPath(selectedFileUri);
                selectedFilePath = file.getAbsoluteFile().getName().toString();

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), selectedFileUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String imageString = getStringImageBase64(bitmap);
                encodeImagetoString(bitmap);


            }else if(requestCode == SELECT_FILE){
                Uri selectedFileUri = data.getData();
                Uri filePath = data.getData();

                File file = getPath(selectedFileUri);
                selectedFilePath = file.getAbsoluteFile().getName();
                String fullpath = file.getPath();

                Helper.toastLong(activity, selectedFilePath+ " size:" + getFileSize(file));

            }
        }


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
//                params.put("imagearray",  getStringImageBase64(bitmap)+"");
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


    public String getStringImageBase64(Bitmap bmp){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte[] imageBytes = stream.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public void encodeImagetoString(final Bitmap bmp ) {
        new AsyncTask<Void, Void, String>() {

            protected void onPreExecute() {
              loading.show();
            }

            @Override
            protected String doInBackground(Void... params) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                byte[] imageBytes = stream.toByteArray();
                encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                return "";
            }

            @Override
            protected void onPostExecute(String msg) {
                loading.setMessage("Uploading...");
                params.put("image", encodedImage);
                params.put("imagename", selectedFilePath);
                params.put("username", "tsraqua");
                params.put("password", "tsraqua");
                params.put("deviceid", Helper.getMacAddress(context));
                params.put("userid",  "11");
                params.put("userlvl", "4");
                // Trigger Image upload
                makeHTTPCall();
            }
        }.execute(null, null, null);
    }


    public void makeHTTPCall() {
        loading.setMessage("Invoking Php");
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(Helper.variables.URL_PHP_INSERT_FEEDPOST_PHOTO,
                params, new AsyncHttpResponseHandler() {
                    // When the response returned by REST has Http
                    // response code '200'


                    @Override
                    public void onSuccess(String content) {
                        super.onSuccess(content);
                    }

                    @Override
                    public void onSuccess(int statusCode, String content) {
                        super.onSuccess(statusCode, content);
                        loading.hide();
                        Toast.makeText(getApplicationContext(), " " + statusCode + " " + content,
                                Toast.LENGTH_LONG).show();
                    }

                    // When the response returned by REST has Http response code other than '200' such as '404', '500' or '403' etc
                    @Override
                    public void onFailure(int statusCode, Throwable error,
                                          String content) {
                        // Hide Progress Dialog
                        loading.hide();
                        // When Http response code is '404'
                        if (statusCode == 404) {
                            Toast.makeText(getApplicationContext(),
                                    "Requested resource not found",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code is '500'
                        else if (statusCode == 500) {
                            Toast.makeText(getApplicationContext(),
                                    "Something went wrong at server end",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code other than 404, 500
                        else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Error Occured: Most Common Error: \n1. Device not connected to Internet\n2. Web App is not deployed in App server\n3. App server is not runningn HTTP Status code : "
                                            + "\n"+ statusCode + "\n" +error.toString(), Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
    }


    public File getPath(Uri uri) {
        return new File(uri.getPath());
    }

    private double getFileSize(File file){
        double filesize = file.length() / 1024;
        return  filesize;
    }
}

