package com.marygalejabagat.it350.fragment;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.marygalejabagat.it350.R;
import com.marygalejabagat.it350.adapter.QuestionAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ViewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView _txtName;
    private TextView _txtDesc;
    private TextView _txtError;
    private Button _btnNxt;
    private Button _btnNxt2;
    private EditText _inPass;
    private LinearLayout _layout;
    private LinearLayout _layout2;
    private LinearLayout _layoutCheckBox;
    public static String dbPassword;
    public static int currentSurvey;
    public static ProgressDialog pd;
    public static Map<String, String> answer = new HashMap<String, String>();
    private QuestionAdapter adapter;
    public static Bundle args;

    View view;

    private OnFragmentInteractionListener mListener;

    public ViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view, container, false);
        _txtName = view.findViewById(R.id.surveyName);
        _txtDesc = view.findViewById(R.id.description);
        _btnNxt = view.findViewById(R.id.btn_next);
        _btnNxt2 = view.findViewById(R.id.btn_next2);
        _inPass = view.findViewById(R.id.input_password);
        _txtError = view.findViewById(R.id.txtError);
        _layout = view.findViewById(R.id.step1);
        _layout2 = view.findViewById(R.id.step2);
        _layoutCheckBox = view.findViewById(R.id.chkboxlyt);
        args = getArguments();
        String s= args.getString("survey");


        try {
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++) {
                Log.e("JSONOBJECT", jsonArray.get(i).toString());
                JSONObject obj = new JSONObject(jsonArray.get(i).toString());
                String name = obj.getString("name");
                String desc = obj.getString("description");
                currentSurvey = obj.getInt("id");
                dbPassword = obj.getString("password");
                _txtName.setText(name);
                _txtDesc.setText(desc);
                args.putString("survey_id", String.valueOf(currentSurvey));
                Log.e("JSONOBJECT", name);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        _btnNxt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                checkPass();
            }
        });
        _btnNxt2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                Class fragmentClass = SurveyQuestionFragment.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                fragment.setArguments(args);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
            }
        });

        Log.e("VIEWFRAGMENT:ONCREATE", s);
        return view;
    }

    private void getSurveyGroup() {
        //currentSurvey
        Log.e("CURRENTSURVEY", String.valueOf(currentSurvey));
        showProgressBar("Fixing survey group...");

        String url = "https://mgsurvey.herokuapp.com/api/getSurveyGroup";
        RequestQueue StatusQue = Volley.newRequestQueue(view.getContext());
        StringRequest Status = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("RESPONSE::SURVEYGROUP", response.toString());
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    CheckBox[] dynamicCheckBoxes = new CheckBox[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        final CheckBox cb = new CheckBox(getContext());
                        String name = obj.getString("name");
                        cb.setText(name);
                        cb.setTag(obj.getInt("id"));
                        dynamicCheckBoxes[i]=cb;
                        _layoutCheckBox.addView(cb);
                        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if(isChecked){
                                    String grp = cb.getTag().toString();
                                    answer.put("group", grp);
                                    args.putString("group", grp);
                                    Log.e("SELECTED GROUP", grp);
                                }
                            }
                        });
                    }

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
                n.put("survey_id", String.valueOf(currentSurvey));
                Log.e("PARAM:UPDATESURVEY", n.toString());
                return n;
            }
        };
        StatusQue.add(Status);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public void checkPass(){
        String enteredPass = _inPass.getText().toString();
        Log.e("ENTEREDPASS", enteredPass);
        Log.e("DBPASS", dbPassword);
        if(enteredPass.equals(dbPassword)){Log.e("SAME", dbPassword);
            getSurveyGroup();
            _txtError.setVisibility(View.GONE);
            _layout.setVisibility(View.GONE);
            _layout2.setVisibility(View.VISIBLE);
        }else{
            _txtError.setVisibility(View.VISIBLE);
        }

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
