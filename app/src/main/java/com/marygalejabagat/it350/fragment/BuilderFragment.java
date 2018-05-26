package com.marygalejabagat.it350.fragment;


import com.marygalejabagat.it350.adapter.QuestionAdapter;
import com.marygalejabagat.it350.model.Questions;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.marygalejabagat.it350.R;
import com.marygalejabagat.it350.app.AppController;
import com.marygalejabagat.it350.model.Questions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class BuilderFragment extends Fragment {

    private static final String TAG = "Survey Fragment";
    private static final String posttUrl = "https://mgsurvey.herokuapp.com/api/getAllQuestionsWithDimension";
    private TextView TxtWait;
    private QuestionAdapter adapter;
    private ListView listView;
    public static ArrayList<Questions> QuestionList = new ArrayList<>();
    Context context;

    View view;
    private CheckBox _chckName;
    private TextView _txtName;


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
        view = inflater.inflate(R.layout.builder_fragment2, container, false);

        Bundle arguments = getArguments();
        String dim = arguments.getString("survey");
        listView = (ListView) view.findViewById(R.id.listQuestions);
        _chckName = (CheckBox) view.findViewById(R.id.checkQuestion);
        _txtName = (TextView) view.findViewById(R.id.dimensions);
        adapter = new QuestionAdapter(view.getContext(), R.layout.questons_list, QuestionList);
        listView.setAdapter(adapter);
        Log.e("Builder2", dim);



        loadData(dim);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.drawer_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    public void loadData(final String survey){
        String url = "https://mgsurvey.herokuapp.com/api/getQuestionsBySurvey";
        RequestQueue MyRequestQueue = Volley.newRequestQueue(view.getContext());
        StringRequest QuestionReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    /*JSONObject obj = new JSONObject(response);*/
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0; i < jsonArray.length(); i++) {
                        Questions q = new Questions();
                        JSONObject obj = jsonArray.getJSONObject(i);
                        q.setDimensionName(obj.getString("dimension_name"));
                        q.setName(obj.getString("name"));
                        QuestionList.add(q);
                        Log.e("RESULT:::::::", obj.getString("name"));
                    }
                    adapter.notifyDataSetChanged();
                    Log.e("QUESTIONLIST:::::::", QuestionList.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("ERROR", " ON GETQUESTIONSBYSURVYE");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "error on post survey");
            }
        }) {
            protected Map<String, String> getParams() {

                Map<String, String> Param = new HashMap<String, String>();
                Param.put("survey_id", survey);
                Log.e("pass header ", Param.toString());
                return Param;
            }
        };
        MyRequestQueue.add(QuestionReq);
        AppController.getInstance().addToRequestQueue(QuestionReq);

    }

}