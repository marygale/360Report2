package com.marygalejabagat.it350.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import com.marygalejabagat.it350.R;
import com.marygalejabagat.it350.adapter.SurveyListAdapter;
import com.marygalejabagat.it350.app.AppController;
import com.marygalejabagat.it350.model.Surveys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SurveyFragment extends Fragment {

    private static final String TAG = "Survey Fragment";
    private static final String url = "https://mgsurvey.herokuapp.com/api/getSurveyList";
    private List<Surveys> survey_list = new ArrayList<Surveys>();
    private ListView listView;
    private TextView TxtWait;
    private SurveyListAdapter adapter;

    View view;

    public SurveyFragment() {
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
        view = inflater.inflate(R.layout.fragment_survey, container, false);

        listView = (ListView) view.findViewById(R.id.ListSurvey);
        adapter = new SurveyListAdapter(getActivity(), survey_list);
        listView.setAdapter(adapter);
        Log.e(TAG, "LOGS here");
        TxtWait = (TextView) view.findViewById(R.id.txtWait);

        JsonArrayRequest userReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("GGGGG", response.toString());
                        /*showProgress();*/

                        // Parsing json
                        if(response.length() > 0){
                            for (int i = 0; i < response.length(); i++) {
                                try {

                                    JSONObject obj = response.getJSONObject(i);
                                    Surveys survey = new Surveys();
                                    survey.setName(obj.getString("name"));
                                    survey.setDescription(obj.getString("description"));
                                /*String dateStr = obj.getString("created");
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Date created = sdf.parse(dateStr);
                                survey.setCreated(created);*/
                                    survey_list.add(survey);


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
                /*  showProgress();*/

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(userReq);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.drawer_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}