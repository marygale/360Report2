package com.marygalejabagat.it350.adapter;


import com.android.volley.toolbox.ImageLoader;
import com.marygalejabagat.it350.R;
import com.marygalejabagat.it350.app.AppController;
import com.marygalejabagat.it350.model.Surveys;
import com.marygalejabagat.it350.model.Surveys;


import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SurveyListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Surveys> surveyItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public SurveyListAdapter(Activity activity, List<Surveys> surveyItems) {
        this.activity = activity;
        this.surveyItems = surveyItems;
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

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        TextView name = (TextView) convertView.findViewById(R.id.survey_name);
        TextView desc = (TextView) convertView.findViewById(R.id.survey_description);
        /*TextView genre = (TextView) convertView.findViewById(R.id.genre);
        TextView year = (TextView) convertView.findViewById(R.id.releaseYear);*/

        // getting user data for the row
        Surveys m = surveyItems.get(position);

        // thumbnail image
        //thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        // name
        name.setText(m.getName());

        // description
        if(desc.length() > 0){
            desc.setText(m.getDescription());
        }

        return convertView;
    }

}