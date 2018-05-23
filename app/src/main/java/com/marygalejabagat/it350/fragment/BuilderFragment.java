package com.marygalejabagat.it350.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.marygalejabagat.it350.R;
import com.marygalejabagat.it350.adapter.SurveyListAdapter;
import com.marygalejabagat.it350.app.AppController;
import com.marygalejabagat.it350.model.Questions;
import com.marygalejabagat.it350.model.Surveys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


public class BuilderFragment extends Fragment {

    private static final String TAG = "Survey Fragment";
    private static final String posttUrl = "https://mgsurvey.herokuapp.com/api/getAllQuestionsWithDimension";
    private List<Questions> question_list = new ArrayList<Questions>();
    private CheckBox checkBox;
    private TextView TxtWait;
    private SurveyListAdapter adapter;

    View view;


    public BuilderFragment() {
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
        view = inflater.inflate(R.layout.builder_fragment2, container, false);
        /*String dim = getActivity().getIntent().getExtras().getString("dimension");
        Log.e("DIMENSION", dim);*/
        /*JsonArrayRequest userReq = new JsonArrayRequest(posttUrl,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("GGGGG", response.toString());
                        *//*showProgress();*//*

                        // Parsing json
                        if(response.length() > 0){
                            for (int i = 0; i < response.length(); i++) {
                                try {

                                    JSONObject obj = response.getJSONObject(i);
                                    Questions q = new Questions();
                                    q.setId(obj.getInt("id"));
                                    q.setName(obj.getString("name"));
                                    q.setDimension(obj.getInt("dimension"));
                                    q.setWith_options(obj.getInt("with_options"));
                                    q.setIs_calculating(obj.getInt("is_calculating"));
                                    q.setIs_deleted(obj.getInt("is_deleted"));
                                    question_list.add(q);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            adapter.notifyDataSetChanged();
                        }else{
                            TxtWait.setVisibility(View.VISIBLE);
                            TxtWait.setText("No data to display");
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("engz", error.getMessage());
                *//*  showProgress();*//*

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(userReq);*/

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.drawer_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }




}