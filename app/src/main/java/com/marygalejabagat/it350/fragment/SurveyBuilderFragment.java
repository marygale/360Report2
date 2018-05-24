package com.marygalejabagat.it350.fragment;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.marygalejabagat.it350.BuilderActivity;
import com.marygalejabagat.it350.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class SurveyBuilderFragment extends Fragment {
    private static final String TAG = "survey Builder fragment";

   View view;

   private Button _nxtButton;
   private TextView _surveyName;
   private TextView _descText;
   private RadioButton _emailOn;
   private RadioButton _emailOff;
   private CheckBox _chckTeacher;
   private CheckBox _chckStaff;
   private CheckBox _chckAdmin;
   private CheckBox _chckParent;
   private CheckBox _chckLeadership;
   private CheckBox _chckRelationship;
   private CheckBox _chckMgt;
   private CheckBox _chckVision;
   private CheckBox _chckKnowledge;
   private TextView _txtGrp;
   private TextView _txtDimen;
   private TextView _txtPass;
   private ArrayList<String> selectedDim = new ArrayList<String>();
   private ArrayList<String> selectedGroup = new ArrayList<String>();






    public SurveyBuilderFragment() {
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
        view = inflater.inflate(R.layout.builder_fragment, container, false);
        _nxtButton = (Button) view.findViewById(R.id.btn_next);
        _nxtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fnNext();
            }
        });


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.drawer_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }


    public void fnNext(){
        Toast.makeText(view.getContext(), "Next ",   Toast.LENGTH_LONG).show();
        if (!validate()) {
            return;
        }else{
            saveQuestion();

        }
    }

    public boolean validate(){
        boolean valid = true;

        _emailOn = (RadioButton) view.findViewById(R.id.emailOn);
        _emailOff = (RadioButton) view.findViewById(R.id.emailOff);

        _surveyName = (TextView) view.findViewById(R.id.survey_name);
        _descText = (TextView) view.findViewById(R.id.description);
        String name = _surveyName.getText().toString();
        String desc = _descText.getText().toString();
        if(name.isEmpty()){
            _surveyName.setError("name must not be empty");
            valid = false;
        } else {
            _surveyName.setError(null);
        }

        if(desc.isEmpty()){
            _descText.setError("description must not be empty");
            valid = false;
        } else {
            _descText.setError(null);
        }
        _txtPass = (TextView) view.findViewById(R.id.input_password);
        String password = _txtPass.getText().toString();

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _txtPass.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _txtPass.setError(null);
        }


        _chckTeacher = (CheckBox) view.findViewById(R.id.chckTeacher);
        _chckStaff = (CheckBox) view.findViewById(R.id.chckStaff);
        _chckAdmin = (CheckBox) view.findViewById(R.id.chckAdmin);
        _chckParent = (CheckBox) view.findViewById(R.id.chckParent);
        _txtGrp = (TextView) view.findViewById(R.id.txt_grp);
        /** at least one group is check **/
        if(!_chckTeacher.isChecked() && !_chckStaff.isChecked() && !_chckAdmin.isChecked() && !_chckParent.isChecked()){
            _txtGrp.setError("select at least one group");
            valid = false;
        }else{
            _txtGrp.setError(null);
        }


        /** at least one dimension is check **/

        _chckLeadership = (CheckBox) view.findViewById(R.id.leadership);
        _chckRelationship = (CheckBox) view.findViewById(R.id.relationship);
        _chckMgt = (CheckBox) view.findViewById(R.id.mgt);
        _chckVision = (CheckBox) view.findViewById(R.id.vision);
        _chckKnowledge= (CheckBox) view.findViewById(R.id.knowledge);
        _txtDimen = (TextView) view.findViewById(R.id.dimensions);
        if(!_chckLeadership.isChecked() && !_chckRelationship.isChecked() && !_chckMgt.isChecked() && !_chckVision.isChecked() && !_chckKnowledge.isChecked()){
            _txtDimen.setError("select at least one dimension");
            valid = false;
        }else{
            _txtDimen.setError(null);
        }
        return valid;
    }

    public void saveQuestion(){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(view.getContext());
        String url = "https://mgsurvey.herokuapp.com/api/postSurvey";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("RESPONSE", response);

                if(response != "0"){

                    Log.e("SURVEY_ID ", response);
                    processNext(response);
                }



            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "error on post survey");
            }
        }) {
            protected Map<String, String> getParams() {
                if(_chckLeadership.isChecked()){
                    selectedDim.add(_chckLeadership.getText().toString());
                }
                if(_chckRelationship.isChecked()){
                    selectedDim.add(_chckRelationship.getText().toString());
                }
                if(_chckMgt.isChecked()){
                    selectedDim.add(_chckMgt.getText().toString());
                }
                if(_chckVision.isChecked()){
                    selectedDim.add(_chckVision.getText().toString());
                }
                if(_chckKnowledge.isChecked()){
                    selectedDim.add(_chckKnowledge.getText().toString());
                }

                Map<String, String> Survey = new HashMap<String, String>();
                Survey.put("name", _surveyName.getText().toString());
                Survey.put("description", _descText.getText().toString());
                Survey.put("password", _txtPass.getText().toString());
                Survey.put("emailOn", _emailOn.getText().toString());
                Survey.put("emailOff", _emailOff.getText().toString());
                int s = selectedDim.size();Log.e("SIIIIZZE ", "gale"+s);
                for(int i = 0; i<selectedDim.size(); i++){
                    Survey.put("dimensions["+i+"]", selectedDim.get(i));
                    Log.e("dimensions["+i+"]", selectedDim.get(i));
                }


                if(_chckTeacher.isChecked()){
                    selectedGroup.add(_chckTeacher.getText().toString());
                }
                if(_chckStaff.isChecked()){
                    selectedGroup.add(_chckStaff.getText().toString());
                }
                if(_chckAdmin.isChecked()){
                    selectedGroup.add(_chckAdmin.getText().toString());
                }
                if(_chckParent.isChecked()){
                    selectedGroup.add(_chckParent.getText().toString());
                }
                for(int i = 0; i<selectedGroup.size(); i++){
                    Survey.put("groups["+i+"]", selectedGroup.get(i));
                }
                Log.e("survey To String ", Survey.toString());
                return Survey;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }

    public void processNext(String survey){
            Fragment fragment = null;
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
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        /*if(_chckLeadership.isChecked()){
                selectedDim.add("Leadership");
            }else if(_chckRelationship.isChecked()){
                selectedDim.add("Relationship");
            }else if(_chckMgt.isChecked()){
                selectedDim.add("Management");
            }else if(_chckVision.isChecked()){
                selectedDim.add("Vision");
            }else if(_chckKnowledge.isChecked()){
                selectedDim.add("Knowledge");
            }
            *//*i.putExtra("dimension", selectedDim);
            startActivity(i);*//*
            Fragment fragment = null;
            Class fragmentClass;

            Bundle arguments = new Bundle();
            arguments.putStringArrayList("dimension", selectedDim);

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
}