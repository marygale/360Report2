package com.marygalejabagat.it350;

import com.marygalejabagat.it350.adapter.CustomListAdapter;
import com.marygalejabagat.it350.app.AppController;
import com.marygalejabagat.it350.model.User;

/*import android.app.ProgressDialog;*/
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;



public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final String url = "https://mgsurvey.herokuapp.com/api/getUsers";
    /*private ProgressDialog pDialog;*/
    private List<User> userList = new ArrayList<User>();
    private ListView listView;
    private CustomListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        listView = (ListView) findViewById(R.id.ListUser);
        adapter = new CustomListAdapter(this, userList);
        listView.setAdapter(adapter);
        Log.e(TAG, "LOGS here");

      /*  pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();*/

        /*getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1b1b1b")));*/

        /*Create volley request obj*/
        JsonArrayRequest userReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e(TAG, response.toString());
                        hidePDialog();

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

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        hidePDialog();

                    }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(userReq);

    }

    private void hidePDialog(){
       /* if(pDialog != null){
            pDialog.dismiss();
            pDialog = null;
        }*/
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        hidePDialog();
    }


}
