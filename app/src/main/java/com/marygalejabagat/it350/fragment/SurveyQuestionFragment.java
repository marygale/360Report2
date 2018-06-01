package com.marygalejabagat.it350.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
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
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.marygalejabagat.it350.R;
import com.marygalejabagat.it350.adapter.SurveyListAdapter;
import com.marygalejabagat.it350.adapter.SurveyQuestionAdapter;
import com.marygalejabagat.it350.model.Questions;
import com.marygalejabagat.it350.model.Questions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SurveyQuestionFragment extends Fragment {

    View view;
    private ListView listView;
    public static Bundle args;
    public static ProgressDialog pd;
    private SurveyQuestionAdapter adapter;
    public static ArrayList<Questions> questionList = new ArrayList<>();
    private static final String TAG = "SurveyQuestionFragment";
    LayoutInflater lf;

    public SurveyQuestionFragment() {
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
        view = inflater.inflate(R.layout.fragment_survey_question, container, false);
        args = getArguments();
        questionList = new ArrayList<>();
        listView = view.findViewById(R.id.listQuestion);
        adapter = new SurveyQuestionAdapter(view.getContext(), R.layout.question_choice_list, questionList);
        lf = getLayoutInflater();
        ViewGroup footer = (ViewGroup)lf.inflate(R.layout.question_footer,listView,false);
        listView.addFooterView(footer);
        listView.setAdapter(adapter);
        loadQuestions();
        Log.e("SURVEYQUESTONFRAG", args.toString());
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.drawer_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void loadQuestions() {
        Log.e("LOADQUESTION", args.getString("survey_id"));
        showProgressBar("Loading questions...");
        String url = "https://mgsurvey.herokuapp.com/api/getQuestionBySurveyId";
        RequestQueue QuestionQue = Volley.newRequestQueue(view.getContext());
        StringRequest QuestionReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("RESPONSE::SURVEYGROUP", response.toString());
                try {
                    pd.dismiss();
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        Questions q = new Questions();
                        q.setName(obj.getString("name"));
                        q.setId(obj.getInt("id"));
                        q.setDimensionName(obj.getString("dimension_name"));
                        q.setSurveyId(obj.getInt("survey_id"));
                        q.setModified(args.getString("group"));
                        questionList.add(q);
                        Log.e("NAMENNNN::", obj.getString("name"));
                    }
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                pd.dismiss();

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
                n.put("survey_id", args.getString("survey_id"));
                n.put("group", args.getString("group"));
                Log.e("PARAM:UPDATESURVEY", n.toString());
                return n;
            }
        };
        QuestionQue.add(QuestionReq);
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
