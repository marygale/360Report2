package com.marygalejabagat.it350.fragment;

import android.app.ProgressDialog;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.marygalejabagat.it350.MainActivity;
import com.marygalejabagat.it350.R;
import com.marygalejabagat.it350.adapter.SurveyListAdapter;
import com.marygalejabagat.it350.app.AppController;
import com.marygalejabagat.it350.model.Surveys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SurveyFragment extends Fragment{

    private static final String TAG = "Survey Fragment";
    private static final String url = "https://mgsurvey.herokuapp.com/api/getSurveyList";
    private List<Surveys> survey_list = new ArrayList<Surveys>();
    private ListView listView;
    private SurveyListAdapter adapter;
    public static ProgressDialog pd;
    private ActionMode currentActionMode;
    private int currentListItemIndex;
    private Button btnStart;
    public static MenuFragment pf;

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
        adapter = new SurveyListAdapter(getActivity(), survey_list);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "Long CLICK", Toast.LENGTH_SHORT).show();
                showEditDialog();
                return true;
            }
        });

        Log.e(TAG, "LOGS here");
        loadSurvey();
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



    private void showEditDialog() {
        /*FragmentManager fm = getChildFragmentManager();*/
        /*MenuFragment pf = MenuFragment.newInstance("Some Title");*/
        /*pf = MenuFragment.newInstance("Some Title");*/
        pf = MenuFragment.newInstance("Some Title");
        pf.show(getActivity().getSupportFragmentManager(), "po_menu");

        /*pf.show(getActivity().getFragmentManager(), "po_menu");*/
    }

}