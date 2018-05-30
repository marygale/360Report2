package com.marygalejabagat.it350.fragment;


import com.marygalejabagat.it350.adapter.QuestionAdapter;
import com.marygalejabagat.it350.model.Questions;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.marygalejabagat.it350.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.widget.Toast;
import android.widget.Button;
import android.app.ProgressDialog;

public class BuilderFragment extends Fragment {

    private static final String TAG = "Survey Fragment";
    private static final String posttUrl = "https://mgsurvey.herokuapp.com/api/getAllQuestionsWithDimension";
    private QuestionAdapter adapter;
    private ListView listView;
    public static ArrayList<Questions> QuestionList = new ArrayList<>();
    Context context;

    View view;
    private CheckBox _chckName;
    private TextView _txtName;
    private Button btnSubmit;
    LayoutInflater lf;

    public static ArrayList<HashMap<String, String>> checkQuestion = new ArrayList<HashMap<String, String>>();
    public static ProgressDialog pd;

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
        _txtName = (TextView) view.findViewById(R.id.dimensions);
        adapter = new QuestionAdapter(view.getContext(), R.layout.questons_list, QuestionList, this);
        lf = getLayoutInflater();
        ViewGroup footer = (ViewGroup)lf.inflate(R.layout.question_footer,listView,false);
        listView.addFooterView(footer);
        listView.setAdapter(adapter);
        Log.e("Builder2", dim);
        loadData(dim);

        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                save();
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.drawer_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    public void loadData(final String survey){
        showProgressBar("Loading survey questions.....");
        String url = "https://mgsurvey.herokuapp.com/api/getQuestionsBySurvey";
        RequestQueue MyRequestQueue = Volley.newRequestQueue(view.getContext());
        StringRequest QuestionReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("RESPONSE:::::::", response.toString());
                try {
                    pd.dismiss();
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0; i < jsonArray.length(); i++) {
                        Questions q = new Questions();
                        JSONObject obj = jsonArray.getJSONObject(i);
                        q.setDimensionName(obj.getString("dimension_name"));
                        q.setName(obj.getString("name"));
                        q.setSurveyId(obj.getInt("survey_id"));
                        q.setId(obj.getInt("id"));
                        QuestionList.add(q);
                    }
                    adapter.notifyDataSetChanged();
                    Log.e("QUESTIONLIST:::::::", QuestionList.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("ERROR", " ON GETQUESTIONSBYSURVYE");
                    pd.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "error on post survey");
                pd.dismiss();
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


    }

    public static void saveData(ArrayList<HashMap<String, String>> adapterMap){
        /*final ArrayList<HashMap<String, String>> checkQuestion = adapterMap;*/
        checkQuestion = adapterMap;
    }
    public void save(){
        final LinearLayout rl = (LinearLayout) view.findViewById(R.id.login_layout);
        /*final android.app.ProgressDialog pd = new android.app.ProgressDialog(view.getContext());*/
        showProgressBar("Saving survey questions.....");

        String qURL = "https://mgsurvey.herokuapp.com/api/postSurveyQuestions";
        RequestQueue QuestionQue = Volley.newRequestQueue(view.getContext());
        StringRequest QuestionRequest = new StringRequest(Request.Method.POST, qURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("RESPONSE::SAVEQUESTIONS", response);
                processNext();
                pd.dismiss();
                /*if(response == "true"){}*/

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "error on postSurveyQuestions");
                pd.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> n = new HashMap<String, String>();
                for(int i = 0; i<checkQuestion.size(); i++){

                    n.put("questions["+i+"]", checkQuestion.get(i).toString());
                    Log.e("OTHERS", checkQuestion.get(i).toString());
                }

                return n;
            }
        };
        QuestionQue.add(QuestionRequest);
        pd.dismiss();
        Log.e("BUILDER::FRAGMENT",checkQuestion.toString());
    }

    public void processNext(){
        adapter = new QuestionAdapter(view.getContext(), R.layout.questons_list, QuestionList, this);
        QuestionList = new ArrayList<>();
        Fragment fragment = null;
        Class fragmentClass;

        fragmentClass = SurveyFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

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

    /*@Override
    public void onResume() {
        super.onResume();
        adapter = new QuestionAdapter(view.getContext(), R.layout.questons_list, QuestionList, this);
        QuestionList = new ArrayList<>();

    }*/

}