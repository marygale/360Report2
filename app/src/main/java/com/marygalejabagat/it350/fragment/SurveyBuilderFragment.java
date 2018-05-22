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


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import com.marygalejabagat.it350.BuilderActivity;
import com.marygalejabagat.it350.LoginActivity;
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

import butterknife.BindView;


public class SurveyBuilderFragment extends Fragment {

    private static final String TAG = "Survey Fragment";
    private static final String url = "https://mgsurvey.herokuapp.com/api/getSurveyList";
    private List<Surveys> survey_list = new ArrayList<Surveys>();
    private ListView listView;
    private TextView TxtWait;
    private SurveyListAdapter adapter;

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
            Intent i = new Intent(view.getContext(), BuilderActivity.class);
            if(_chckLeadership.isChecked()){
                selectedDim.add("Leadership");
               /*i.putExtra("dimension", "Leadership");*/
            }else if(_chckRelationship.isChecked()){
                selectedDim.add("Relationship");
                /*i.putExtra("dimension", "Relationship");*/
            }else if(_chckMgt.isChecked()){
                selectedDim.add("Management");
                /*i.putExtra("dimension", "Management");*/
            }else if(_chckVision.isChecked()){
                selectedDim.add("Vision");
                /*i.putExtra("dimension", "Vision");*/
            }else if(_chckKnowledge.isChecked()){
                selectedDim.add("Knowledge");
                /*i.putExtra("dimension", "Knowledge");*/
            }
            i.putExtra("dimension", selectedDim);
            startActivity(i);
        }
    }

    public boolean validate(){
        boolean valid = true;

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
}