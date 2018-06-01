package com.marygalejabagat.it350.fragment;

import android.content.Context;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.marygalejabagat.it350.R;
import com.marygalejabagat.it350.model.Surveys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


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
    public static String dbPassword;

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
        Bundle args = getArguments();
        String s= args.getString("survey");


        try {
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++) {
                Log.e("JSONOBJECT", jsonArray.get(i).toString());
                JSONObject obj = new JSONObject(jsonArray.get(i).toString());
                String name = obj.getString("name");
                String desc = obj.getString("description");
                dbPassword = obj.getString("password");
                _txtName.setText(name);
                _txtDesc.setText(desc);
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

            }
        });

        Log.e("VIEWFRAGMENT:ONCREATE", s);
        return view;
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
            _txtError.setVisibility(View.GONE);
            _layout.setVisibility(View.GONE);
            _layout2.setVisibility(View.VISIBLE);
        }else{
            _txtError.setVisibility(View.VISIBLE);
        }

    }
}
