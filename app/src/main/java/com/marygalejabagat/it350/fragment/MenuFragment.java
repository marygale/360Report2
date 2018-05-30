package com.marygalejabagat.it350.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
/*import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;*/


import com.marygalejabagat.it350.R;

import java.util.List;
/*import com.marygalejabagat.it350.fragment.SurveyFragment;*/

public class MenuFragment extends DialogFragment {

    View view;
    private Button btnStart;
    private Button btnStop;
    private Button btnEdit;
    private Button btnDelete;
    public SurveyFragment Surveyfragment;
    private MenuFragment frag;

    public MenuFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

   /* public MenuFragment newInstance(String title, SurveyFragment fragment) {
        MenuFragment frag = new MenuFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        this.Surveyfragment = fragment;
        frag.setArguments(args);
        return frag;
    }
*/

    public static MenuFragment newInstance(String title) {
        MenuFragment frag = new MenuFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.pop_menu, container);
        btnStart = (Button) view.findViewById(R.id.btnStart);
        btnStop = (Button) view.findViewById(R.id.btnStop);
        btnEdit = (Button) view.findViewById(R.id.btnEdit);
        btnDelete = (Button) view.findViewById(R.id.btnDelete);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("START SURVEY", "THIS");
                Fragment fragment = null;
                Class fragmentClass;

                 Bundle arguments = new Bundle();
                arguments.putString("survey", "test");

                fragmentClass = StartFragment.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                fragment.setArguments(arguments);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.flContent, fragment).commit();
              /*  getActivity().getFragmentManager().beginTransaction().addToBackStack(null);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.flContent, fragment).commit();*/

                /*FragmentManager fragmentManager = getActivity().getSupportFragmentManager();;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


                fragmentTransaction.replace(R.id.flContent, fragment).commit();
                fragmentTransaction.remove(fragment);*/

                /*getActivity().getFragmentManager().beginTransaction().remove().commit();*/



               /* FragmentManager fm1 = getFragmentManager();
                SurveyFragment fragm = (SurveyFragment)fm1.findFragmentById(R.id.popMenu);
                fragm.closeDialog();*/


                /*SurveyFragment sf = (SurveyFragment) fm.findFragmentById(R.id.fragmentSurvey);
                sf.closeDialog();*/
            }

        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("STOP SURVEY", "THIS");
            }

        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("EDIT SURVEY", "THIS");
            }

        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("DELETE SURVEY", "THIS");
            }

        });
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    /*public void redirect(String f){
        Fragment fragment = null;
        Class fragmentClass;

        Bundle arguments = new Bundle();
        *//*arguments.putString("survey", survey);*//*

        fragmentClass = BuilderFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fragment.setArguments(arguments);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
    }*/


}