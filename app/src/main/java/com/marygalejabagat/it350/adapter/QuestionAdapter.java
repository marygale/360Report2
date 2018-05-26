package com.marygalejabagat.it350.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.marygalejabagat.it350.R;
import com.marygalejabagat.it350.model.Questions;

import java.util.List;

public class QuestionAdapter extends BaseAdapter {

    private final List<Questions> list;
    private LayoutInflater inflater;
    private Activity activity;

    public QuestionAdapter(Activity activity, List<Questions> list){
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int location) {
        return list.get(location);
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
            convertView = inflater.inflate(R.layout.questons_list, null);

        TextView name = (TextView) convertView.findViewById(R.id.dimensions);
        CheckBox checkQuestion = (CheckBox) convertView.findViewById(R.id.checkQuestion);

        Questions q = list.get(position);
        name.setText(q.getDimensionName());
        checkQuestion.setText(q.getName());
        return convertView;
    }
}
