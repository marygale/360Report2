package com.marygalejabagat.it350.fragment;

import android.app.ProgressDialog;
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


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.marygalejabagat.it350.R;
import com.marygalejabagat.it350.model.Surveys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    public static ProgressDialog pd;
    public static int tagId;
    public static int surveyStatus;
    private List<Surveys> surveyEntry = new ArrayList<Surveys>();
    private static final List<Surveys> surveyView = new ArrayList<Surveys>();
    public static ArrayList<HashMap<String, String>> selectedSurvey = new ArrayList<HashMap<String, String>>();


    public MenuFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }


    public static MenuFragment newInstance(int qId, int status, ArrayList<HashMap<String, String>> adapterMap) {
        MenuFragment frag = new MenuFragment();
        Bundle args = new Bundle();
        args.putInt("id", qId);
        args.putInt("status", status);
        frag.setArguments(args);
        selectedSurvey = adapterMap;
        tagId = qId;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.pop_menu, container);
        btnStart = view.findViewById(R.id.btnStart);
        btnStop = view.findViewById(R.id.btnStop);
        btnEdit = view.findViewById(R.id.btnEdit);
        btnDelete = view.findViewById(R.id.btnDelete);
        btnView = view.findViewById(R.id.btnView);
        surveyStatus = getArguments().getInt("status");
        if(surveyStatus == 0){ //status is stop
            Log.e("STATUS ", "status is stop");
            btnStart.setVisibility(View.VISIBLE);
        }else{ //status is start
            Log.e("STATUS ", "status is start");
            btnStart.setVisibility(View.GONE);
            btnView.setVisibility(View.VISIBLE);
        }
        tagId = getArguments().getInt("id");
        final MenuFragment mf = this;

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*String tagName = getArguments().getString("id");*/
                Log.e("START SURVEY", String.valueOf(tagId));
                start_stop();
                /*start_stop(tagId, surveyStatus);*/

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

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("VIEW SURVEY", String.valueOf(tagId));
                getSurveyById(tagId);
                /*Log.e("VIEWSURVEYENTRY", selectedSurvey.toString());

                String tagName = getArguments().getString("id");
                Log.e("VIEW SURVEY", tagName);
                getSurveyById();*/
                /*arguments = new Bundle();
                arguments.putString("survey_id", tagName);
                arguments.putInt("status", getArguments().getInt("status"));
                arguments.putString("selected", surveyEntry.toString());*/

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
                start_stop();
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

    public void redirect(Class fragmentClass){
        arguments = new Bundle();
        arguments.putInt("survey_id", tagId);
        arguments.putInt("status", surveyStatus);

        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fragment.setArguments(arguments);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.remove(this);
        ft.replace(R.id.flContent, fragment);
        ft.commit();
        /*Fragment fragment = null;
        Class fragmentClass;

        Bundle arguments = new Bundle();
        arguments.putString("survey", survey);

        fragmentClass = BuilderFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fragment.setArguments(arguments);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();*/
    }

    public void start_stop(){
        /*if(surveyStatus == 0){
            showProgressBar("Starting a survey....");
        }else{
            showProgressBar("Stopping a survey....");
        }*/
        showProgressBar("Please wait...");

        Log.e("START:STOP",  String.valueOf(surveyStatus));
        String url = "https://mgsurvey.herokuapp.com/api/updateSurveyStatus";
        RequestQueue StatusQue = Volley.newRequestQueue(view.getContext());
        StringRequest Status = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("RESPONSE::UPDATESTAT", response);
                try{
                    JSONObject obj = new JSONObject(response);
                    Surveys s = new Surveys();
                    s.setId(obj.getInt("id"));
                    s.setName(obj.getString("name"));
                    s.setDescription(obj.getString("description"));
                    s.setStatus(obj.getInt("status"));
                    s.setEmail_verification_on(obj.getInt("email_verification_on"));
                    s.setUser_id(obj.getInt("user_id"));
                    s.setOpen(obj.getInt("open"));
                    s.setModified(obj.getString("modified"));
                    s.setCreated(obj.getString("created"));
                    surveyEntry.add(s);
                    Log.e("SURVEYENTRY", surveyEntry.toString());
                }catch (JSONException e){
                    e.printStackTrace();
                }

                pd.dismiss();
                redirect(StartFragment.class);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("UPDATESTAT", error.getMessage());
                pd.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> n = new HashMap<String, String>();
                n.put("survey_id", String.valueOf(tagId));
                Log.e("PARAM:UPDATESURVEY", n.toString());
                return n;
            }
        };
        StatusQue.add(Status);
        pd.dismiss();


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

    public void getSurveyById(final int id){
        String url = "https://mgsurvey.herokuapp.com/api/getSurveyById";
        RequestQueue SurveyQue = Volley.newRequestQueue(view.getContext());
        StringRequest getSurvey = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("RES::GETSURVEYBYID", response.toString());
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    viewRedirect(ViewFragment.class, jsonArray);

                }catch (JSONException e){
                    e.printStackTrace();
                }
                Log.e("VIEW getSurveyById", surveyView.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("UPDATESTAT", error.getMessage());
                pd.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> n = new HashMap<String, String>();
                n.put("survey_id", String.valueOf(id));
                Log.e("PARAM:UPDATESURVEY", n.toString());
                return n;
            }
        };
        SurveyQue.add(getSurvey);
    }

    public void viewRedirect(Class fragmentClass, JSONArray res){
        Log.e("VIEWREDIRECT", res.toString());
        arguments = new Bundle();
        arguments.putString("survey", res.toString());

        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fragment.setArguments(arguments);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.remove(this);
        ft.replace(R.id.flContent, fragment);
        ft.commit();
    }

}