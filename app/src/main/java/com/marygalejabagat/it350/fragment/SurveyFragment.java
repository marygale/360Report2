package com.marygalejabagat.it350.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SurveyFragment extends Fragment{

    private static final String TAG = "Survey Fragment";
    private static final String url = "https://mgsurvey.herokuapp.com/api/getSurveyList";
    private List<Surveys> survey_list = new ArrayList<Surveys>();
    private ListView listView;
    private SurveyListAdapter adapter;
    public static ProgressDialog pd;
    public static MenuFragment pf;
    public static ArrayList<HashMap<String, String>> selectedSurvey = new ArrayList<HashMap<String, String>>();
    View view;
    View menuView;

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
        view = inflater.inflate(R.layout.fragment_survey, container, false);
        menuView = inflater.inflate(R.layout.pop_menu, container, false);

        listView = (ListView) view.findViewById(R.id.ListSurvey);
        adapter = new SurveyListAdapter(getActivity(), survey_list, this);
        listView.setAdapter(adapter);
        Log.e(TAG, "LOGS here");
        loadSurvey();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView name = view.findViewById(R.id.survey_name);
                TextView desc = view.findViewById(R.id.survey_description);
                int TagName = (int) name.getTag();
                int status = (int) desc.getTag();
                showEditDialog(TagName, status); Log.e("SURVEY ID SELECTED", String.valueOf(TagName));
                return true;
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.drawer_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    public void loadSurvey(){
        final LinearLayout rl = (LinearLayout) view.findViewById(R.id.main_layout);
        showProgressBar("Loading survey list.....");

        JsonArrayRequest userReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("GGGGG", response.toString());
                        pd.dismiss();

                        // Parsing json
                        if(response.length() > 0){
                            for (int i = 0; i < response.length(); i++) {
                                try {

                                    JSONObject obj = response.getJSONObject(i);
                                    Surveys survey = new Surveys();
                                    survey.setName(obj.getString("name"));
                                    survey.setDescription(obj.getString("description"));
                                    survey.setCreated(obj.getString("created"));
                                    survey.setId(obj.getInt("id"));
                                    survey.setStatus(obj.getInt("status"));
                                    survey.setUser_id(obj.getInt("user_id"));
                                    survey_list.add(survey);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            adapter.notifyDataSetChanged();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("engz", error.getMessage());
                pd.dismiss();

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



    private void showEditDialog(int TagName, int status) {
        /*FragmentManager fm = getChildFragmentManager();*/
        /*MenuFragment pf = MenuFragment.newInstance("Some Title");*/
        /*pf = MenuFragment.newInstance("Some Title");*/
        pf = MenuFragment.newInstance(TagName, status, selectedSurvey);
        pf.show(getActivity().getSupportFragmentManager(), "po_menu");

        /*pf.show(getActivity().getFragmentManager(), "po_menu");*/
    }

    public static void getSelectedSurvey(ArrayList<HashMap<String, String>> adapterMap){
        selectedSurvey = adapterMap;
    }

}