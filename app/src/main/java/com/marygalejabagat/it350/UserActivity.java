package com.marygalejabagat.it350;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.marygalejabagat.it350.adapter.CustomListAdapter;
import com.marygalejabagat.it350.app.AppController;
import com.marygalejabagat.it350.fragment.UserFragment;
import com.marygalejabagat.it350.model.User;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;


public class UserActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final String url = "https://mgsurvey.herokuapp.com/api/getUsers";
    private List<User> userList = new ArrayList<User>();
    private ListView listView;
    private CustomListAdapter adapter;
    private ProgressBar progressBar;
    private TextView txtProgress;
    private int progressStatus = 0;
    private Handler handler = new Handler();

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view

        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nvView);
        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);
        ImageView ivHeaderPhoto = headerLayout.findViewById(R.id.imageView);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        mDrawer.addDrawerListener(drawerToggle);


        listView = (ListView) findViewById(R.id.ListUser);
        adapter = new CustomListAdapter(this, userList);
        listView.setAdapter(adapter);
        Log.e(TAG, "LOGS here");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*Create volley request obj*/
        JsonArrayRequest userReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("GGAAAAAAAAAAA", response.toString());
                        /*showProgress();*/

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                User user = new User();
                                user.setFirstName(obj.getString("first_name"));
                                user.setLastName(obj.getString("last_name"));
                                user.setEmail(obj.getString("email_address"));
                                user.setRoleID(obj.getInt("role_id"));
                                user.setUserID(obj.getInt("user_id"));
                                userList.add(user);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
              /*  showProgress();*/

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(userReq);
    }

    /*public void showProgress(){
        txtProgress = (TextView) findViewById(R.id.txtProgress);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        progressStatus = 0;


        progressBar.setVisibility(View.VISIBLE);
        txtProgress.setVisibility(View.VISIBLE);
        txtProgress.setText("Authenticating...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(progressStatus < 100){
                    progressStatus +=1;
                    try{
                        Thread.sleep(20);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    // Update the progress bar
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progressStatus);
                            // Show the progress on TextView
                            txtProgress.setText(progressStatus+"");
                            // If task execution completed
                            if(progressStatus == 100){
                                // Hide the progress bar from layout after finishing task
                                progressBar.setVisibility(View.GONE);
                                // Set a message of completion
                                txtProgress.setText("Operation completed...");
                                //onLoginSuccess();
                            }
                        }
                    });
                }
            }
        }).start();

    }*/

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.builder:
                fragmentClass = UserFragment.class;
                Toast.makeText(getApplicationContext(), "Builder ",   Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_component:
                Intent usericon=new Intent(this,UserActivity.class);
                startActivity(usericon);
                finish();
            case R.id.user_menu:
                Toast.makeText(getApplicationContext(), "User List ",   Toast.LENGTH_LONG).show();
                fragmentClass = UserFragment.class;
                break;
            default:
                fragmentClass = UserFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.navigation_drawer_open,  R.string.navigation_drawer_close);
    }
}
