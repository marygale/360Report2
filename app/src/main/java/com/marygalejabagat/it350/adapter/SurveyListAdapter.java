package com.marygalejabagat.it350.adapter;


import com.android.volley.toolbox.ImageLoader;
import com.marygalejabagat.it350.R;
import com.marygalejabagat.it350.app.AppController;
import com.marygalejabagat.it350.fragment.SurveyFragment;
import com.marygalejabagat.it350.model.Surveys;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.app.ProgressDialog;

public class SurveyListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Surveys> surveyItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    public static ProgressDialog pd;
    private final SurveyFragment fragment;
    private ArrayList<HashMap<String, String>> selectedSurvey = new ArrayList<HashMap<String, String>>();

    public SurveyListAdapter(Activity activity, List<Surveys> surveyItems, SurveyFragment fragment) {
        this.activity = activity;
        this.surveyItems = surveyItems;
        this.fragment = fragment;
    }

    @Override
    public int getCount() {
        return surveyItems.size();
    }

    @Override
    public Object getItem(int location) {
        return surveyItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.survey_list, null);


        TextView name = convertView.findViewById(R.id.survey_name);
        TextView desc = convertView.findViewById(R.id.survey_description);
        TextView created = convertView.findViewById(R.id.created);

        Surveys m = surveyItems.get(position);

        name.setText(m.getName());
        name.setTag(m.getId());
        desc.setTag(m.getStatus());
        created.setText(m.getCreated());

        Log.e("SURVEYLISTADAPTER", String.valueOf(m.getStatus()));
        Log.e("SURVEYLISTADAPTER", String.valueOf(m.getStatus()));


        // description
        if(desc.length() > 0){
            desc.setText(m.getDescription());
        }
        Object surveyId = name.getTag();
        Object status = desc.getTag();

        HashMap<String, String> en = new HashMap<String, String>();
        en.put("status", status.toString());
        en.put("surveyId", surveyId.toString());
        selectedSurvey.add(en);
        fragment.getSelectedSurvey(selectedSurvey);
        return convertView;
    }

    

}