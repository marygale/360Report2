package com.marygalejabagat.it350.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
/*import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;*/


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.marygalejabagat.it350.R;
import com.marygalejabagat.it350.app.AppController;
import com.marygalejabagat.it350.model.Surveys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*import com.marygalejabagat.it350.fragment.SurveyFragment;*/

public class MenuFragment extends DialogFragment {

    View view;
    private Button btnStart;
    private Button btnStop;
    private Button btnEdit;
    private Button btnDelete;
    private Button btnView;
    public SurveyFragment Surveyfragment;
    private MenuFragment frag;
    private Bundle arguments;
    /*private static String qId;*/

    public MenuFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }


    public static MenuFragment newInstance(String qId, int status) {
        MenuFragment frag = new MenuFragment();
        Bundle args = new Bundle();
        args.putString("id", qId);
        args.putInt("status", status);
        frag.setArguments(args);

        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.pop_menu, container);
        btnStart = (Button) view.findViewById(R.id.btnStart);
        btnStop = (Button) view.findViewById(R.id.btnStop);
        btnEdit = (Button) view.findViewById(R.id.btnEdit);
        btnDelete = (Button) view.findViewById(R.id.btnDelete);
        btnView = (Button) view.findViewById(R.id.btnView);
        int status = getArguments().getInt("status");
        if(status == 0){ //status is stop
            Log.e("STATUS ", "status is stop");
            btnStart.setVisibility(View.VISIBLE);
        }else{ //status is start
            Log.e("STATUS ", "status is start");
            btnStart.setVisibility(View.GONE);
            btnView.setVisibility(View.VISIBLE);
        }
        final MenuFragment mf = this;
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                Class fragmentClass;

                String tagName = getArguments().getString("id");
                Log.e("START SURVEY", tagName);

                arguments = new Bundle();
                arguments.putString("survey_id", tagName);
                arguments.putInt("status", getArguments().getInt("status"));
                start_stop(tagName);

                /*fragmentClass = StartFragment.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                fragment.setArguments(arguments);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();

                ft.remove(mf);
                ft.replace(R.id.flContent, fragment);
                ft.commit();*/
            }

        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("STOP SURVEY", "THIS");
            }

        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("EDIT SURVEY", "THIS");
            }

        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("DELETE SURVEY", "THIS");
            }

        });
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    /*public void redirect(String f){
        Fragment fragment = null;
        Class fragmentClass;

        Bundle arguments = new Bundle();
        *//*arguments.putString("survey", survey);*//*

        fragmentClass = BuilderFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fragment.setArguments(arguments);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
    }*/

    public void start_stop(final String TagName){
        //getSurveyById
        String url = "https://mgsurvey.herokuapp.com/api/updateSurveyStatus";
        final JsonArrayRequest userReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("geSurveyById RESULT", response.toString());

                        // Parsing json
                        if(response.length() > 0){
                            for (int i = 0; i < response.length(); i++) {
                                try {

                                    JSONObject obj = response.getJSONObject(i);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e("geSurveyById", error.getMessage());

                    }
                }){
                    protected Map<String, String> getParams() {
                        Map<String, String> Survey = new HashMap<String, String>();
                        Survey.put("survey_id", TagName);
                        return Survey;
                    }
                };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(userReq);

    }
}