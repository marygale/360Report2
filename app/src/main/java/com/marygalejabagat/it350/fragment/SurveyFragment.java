package com.marygalejabagat.it350.fragment;

import android.app.ProgressDialog;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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


    View view;

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

        listView = (ListView) view.findViewById(R.id.ListSurvey);
        adapter = new SurveyListAdapter(getActivity(), survey_list);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "Long CLICK", Toast.LENGTH_SHORT).show();
                PopupMenu popup = new PopupMenu (view.getContext(),view, Gravity.CENTER);
                popup.inflate(R.menu.action_survey_menu);
                popup.show();
               /* if (currentActionMode != null) { return false; }
                currentListItemIndex = position;
                currentActionMode = startActionMode(modeCallBack);
                currentActionMode= getActivity().startActionMode(modeCallBack);
                view.setSelected(true);*/
                return true;
            }
        });

        Log.e(TAG, "LOGS here");
        loadSurvey();
        /*listView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                *//*displayPopupWindow();*//*
                PopupMenu popup = new PopupMenu (view.getContext(),listView);
                popup.inflate(R.menu.action_survey_menu);
                popup.show();
                *//*popup.getMenuInflater()*//*

            }
        });*/

        /*listView.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Log.e("CLICK ON", "LISTVIEW");
                popMenu();
            }

        });
        listView.setOnClickListener(View.OnClickListener);
        listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(view.getContext(), v);
                popup.setOnMenuItemClickListener(PopupMenu popup);
                popup.inflate(R.menu.action_survey_menu);
                popup.show();
            }
        });
        listView.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                ItemClicked item = adapter.getItemAtPosition(position);
                Log.e("CLICK ON", "LISTVIEW");
                PopupMenu popup = new PopupMenu(view.getContext(), view);
                popup.setOnMenuItemClickListener(view.getDisplay());
                popup.inflate(R.menu.action_survey_menu);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.action_survey_menu, popup.getMenu());
                popup.show();
                popMenu();
                displayPopupWindow(view);
            }

        });*/

        /*listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentActionMode != null) { return false; }
                currentListItemIndex = position;
                currentActionMode = startActionMode(modeCallBack);
                currentActionMode = SurveyFragment.this.getActivity().startActionMode(modeCallBack);
                view.setSelected(true);
                return true;
            }
        });*/
        return view;
    }

    View.OnClickListener viewClickListener
            = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            // TODO Auto-generated method stub
            showPopupMenu(view);
        }

    };

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

    /*private ActionMode.Callback modeCallBack = new ActionMode.Callback() {
        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.setTitle("Actions");
            mode.getMenuInflater().inflate(R.menu.action_survey_menu, menu);
            return true;
        }

        // Called each time the action mode is shown.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_start:
                    Toast.makeText(getContext(), "Start survey!", Toast.LENGTH_SHORT).show();
                    mode.finish(); // Action picked, so close the contextual menu
                    return true;
                case R.id.menu_stop:
                    Toast.makeText(getContext(), "Stop survey!", Toast.LENGTH_SHORT).show();
                    mode.finish(); // Action picked, so close the contextual menu
                    return true;
                case R.id.menu_edit:
                    Toast.makeText(getContext(), "Editing!", Toast.LENGTH_SHORT).show();
                    mode.finish(); // Action picked, so close the contextual menu
                    return true;
                case R.id.menu_delete:
                    // Trigger the deletion here
                    mode.finish(); // Action picked, so close the contextual menu
                    return true;
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            currentActionMode = null; // Clear current action mode
        }
    };*/

    public void popMenu(){
        pd = new ProgressDialog(view.getContext());
        pd.setContentView(R.layout.activity_login);
        pd.setProgressStyle(android.app.ProgressDialog.STYLE_HORIZONTAL);
        pd.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(true);
        pd.show();
    }
   private void displayPopupWindow(View anchorView) {
        PopupWindow popup = new PopupWindow(view.getContext());

        /*View layout = getLayoutInflater().inflate(R.layout.pop_menu, null);
        popup.setContentView(layout);
        // Set content width and height
        popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        // Closes the popup window when touch outside of it - when looses focus
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);
        // Show anchored to button
        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.showAsDropDown(anchorView);*/
    }


   /*  public boolean onMenuItemClick(MenuItem item) {
        Toast.makeText(getContext(), "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.menu_start:
                Toast.makeText(getContext(), "Start survey!", Toast.LENGTH_SHORT).show();
                 // Action picked, so close the contextual menu
                return true;
            case R.id.menu_stop:
                Toast.makeText(getContext(), "Stop survey!", Toast.LENGTH_SHORT).show();
                 // Action picked, so close the contextual menu
                return true;
            case R.id.menu_edit:
                Toast.makeText(getContext(), "Editing!", Toast.LENGTH_SHORT).show();
                 // Action picked, so close the contextual menu
                return true;
            case R.id.menu_delete:
                // Trigger the deletion here
                 // Action picked, so close the contextual menu
                return true;
            default:
                return false;
        }
    }*/

    private void showPopupMenu(View v){
        PopupMenu popupMenu = new PopupMenu(view.getContext(), v);
        popupMenu.getMenuInflater().inflate(R.menu.action_survey_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(view.getContext(),
                        item.toString(),
                        Toast.LENGTH_LONG).show();
                return true;
            }
        });

        popupMenu.show();
    }

    private ActionMode.Callback modeCallBack = new ActionMode.Callback() {
        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.setTitle("Actions");
            mode.getMenuInflater().inflate(R.menu.action_survey_menu, menu);
            return true;
        }

        // Called each time the action mode is shown.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_start:
                    Toast.makeText(getContext(), "Start survey!", Toast.LENGTH_SHORT).show();
                    // Action picked, so close the contextual menu
                    return true;
                case R.id.menu_stop:
                    Toast.makeText(getContext(), "Stop survey!", Toast.LENGTH_SHORT).show();
                    // Action picked, so close the contextual menu
                    return true;
                case R.id.menu_edit:
                    Toast.makeText(getContext(), "Editing!", Toast.LENGTH_SHORT).show();
                    // Action picked, so close the contextual menu
                    return true;
                case R.id.menu_delete:
                    // Trigger the deletion here
                    // Action picked, so close the contextual menu
                    return true;
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            currentActionMode = null; // Clear current action mode
        }
    };
}