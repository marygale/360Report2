package com.marygalejabagat.it350.fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.marygalejabagat.it350.LoginActivity;
import com.marygalejabagat.it350.R;
import com.marygalejabagat.it350.adapter.CustomListAdapter;
import com.marygalejabagat.it350.app.AppController;
import com.marygalejabagat.it350.model.User;

import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class UserFragment extends Fragment {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final String url = "https://mgsurvey.herokuapp.com/api/getUsers";
    private List<User> userList = new ArrayList<User>();
    private ListView listView;
    private CustomListAdapter adapter;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    public static ProgressDialog pd;
    private LinearLayout rl;

    View view;

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user, container, false);

        listView = (ListView) view.findViewById(R.id.ListUser);
        adapter = new CustomListAdapter(getActivity(), userList);
        listView.setAdapter(adapter);
        loadUser();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.drawer_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void loadUser(){
        final LinearLayout rl = (LinearLayout) view.findViewById(R.id.main_layout);
        showProgressBar("Loading user list.....");
        JsonArrayRequest userReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        pd.dismiss();
                        Log.e(TAG, String.valueOf(response.length()));
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
                pd.setMessage("Error while loading user list");
                pd.dismiss();
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                /*  showProgress();*/

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(userReq);
    }

    public void showProgressBar(String message){
        pd = new ProgressDialog(view.getContext());
        pd.setIndeterminate(false);
        pd.setMessage(message);
        pd.setProgressStyle(android.app.ProgressDialog.STYLE_HORIZONTAL);
        pd.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        pd.setCancelable(true);
        pd.setMax(100);
        pd.show();

    }
}