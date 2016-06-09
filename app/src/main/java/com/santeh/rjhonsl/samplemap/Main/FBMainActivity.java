package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.santeh.rjhonsl.samplemap.APIs.MyVolleyAPI;
import com.santeh.rjhonsl.samplemap.Adapter.RecyclerViewAdapter;
import com.santeh.rjhonsl.samplemap.Obj.VarFishbook;
import com.santeh.rjhonsl.samplemap.Parsers.NewsFeedsParser;
import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.FusedLocation;
import com.santeh.rjhonsl.samplemap.Utils.Helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FBMainActivity extends AppCompatActivity {

    Activity activity;
    Context context;
    RecyclerView recyclerView;
    RecyclerViewAdapter rcAdapter;

    private static final int SELECT_PICTURE = 1;
    private static final int SELECT_FILE = 2;

    ProgressDialog loading;
    FloatingActionButton fabAddPost;
    LinearLayout llbottomwrapper;

    FusedLocation fusedLocation;
    Boolean isBottomAnimating = false;
    Boolean isFabSelected = false;
    String currentUploadID = null;

    List<VarFishbook> newsFeedList;
    public static int CONTENT_TEXT = 0;
    public static int CONTENT_IMAGE = 1;
    public static int CONTENT_FILE = 2;
    public static int CONTENT_EVENT = 3;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        activity = this;
        context = FBMainActivity.this;

        loading = new ProgressDialog(context);
        loading.setIndeterminate(true);
        loading.setCancelable(false);
        loading.setMessage("Uploading");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar2);
        assert myToolbar != null;
        myToolbar.setBackgroundColor(getResources().getColor(R.color.blue_400));
        setSupportActionBar(myToolbar);
        myToolbar.inflateMenu(R.menu.menu_search);

        ActionBar ab = getSupportActionBar();

        fusedLocation = new FusedLocation(context, activity);
        fusedLocation.connectToApiClient();



        fabAddPost = (FloatingActionButton) findViewById(R.id.fab_addpost);
        llbottomwrapper = (LinearLayout) findViewById(R.id.ll_wrapper_bottom);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        registerForContextMenu(recyclerView);

        //add ItemDecoration
        int VERTICAL_ITEM_SPACE = 30;
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
//        //or
//        recyclerView.addItemDecoration(new DividerItemDecoration(activity));
//        //or
//        recyclerView.addItemDecoration(new DividerItemDecoration(activity, R.drawable.line_divider));

        LinearLayout llpostSomething = (LinearLayout) findViewById(R.id.ll_postSomething);
        LinearLayout lluploadPhoto = (LinearLayout) findViewById(R.id.ll_uploadPhoto);
        LinearLayout lluploadFile = (LinearLayout) findViewById(R.id.ll_uploadfile);


        fabAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFabSelected = true;
                animateBottom();
            }
        });

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
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

        assert lluploadPhoto != null;
        lluploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(activity, Activity_PostImage.class));

            }
        });


        assert llpostSomething != null;
        llpostSomething.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(activity, Activity_PostText.class);
//                startActivity(intent);
            }
        });

        assert lluploadFile != null;
        lluploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
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
                        "Select a file"), SELECT_FILE);

            }
        });



        requestDataFeedData();
    }


    private void requestDataFeedData() {
        StringRequest postRequest = new StringRequest(Request.Method.POST, Helper.variables.sourceAddress_goDaddy+"FBselectNewsFeeds.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        if (response.substring(1, 2).equalsIgnoreCase("0")) {
                            Log.d("ResponseAdapter", "0 Failed");

                        } else {
                            Log.d("ResponseAdapter", "1 Success: "+ response);
//                            Helper.dialogBox.okOnly_Scrolling(activity, "Response", response, "OK", R.color.blue_400);
                            newsFeedList = NewsFeedsParser.parseFeed(response, context);
                            rcAdapter = new RecyclerViewAdapter(newsFeedList, context);

                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(rcAdapter);
                            rcAdapter.notifyDataSetChanged();

//                            newsFeedList = new ArrayList<>();
//
//                            ImageAdapter adapter = new ImageAdapter(context, R.layout.cardview_postimage, newsFeedList);

//                            lvNewsFeeds.setAdapter(adapter);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Helper.dialog.themedOkOnly_scrolling(activity,"Err" ,""+error, "OK", R.color.red);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", "tsraqua");
                params.put("password", "tsraqua");
                params.put("deviceid", Helper.deviceInfo.getMacAddress(context));
                params.put("userid",  "11");
                params.put("userlvl", "4");

                return params;
            }
        };

        MyVolleyAPI api = new MyVolleyAPI();
        api.addToReqQueue(postRequest, context);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_refresh) {
            Helper.toast.long_(activity, "Refreshing Contents");
            requestDataFeedData();
        }
        return super.onOptionsItemSelected(item);

    }



    public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

        private final int mVerticalSpaceHeight;

        public VerticalSpaceItemDecoration(int mVerticalSpaceHeight) {
            this.mVerticalSpaceHeight = mVerticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.bottom = mVerticalSpaceHeight;
        }
    }

}
