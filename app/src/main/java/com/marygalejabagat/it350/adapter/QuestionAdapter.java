package com.marygalejabagat.it350.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.marygalejabagat.it350.R;
import com.marygalejabagat.it350.model.Questions;

import java.util.List;

public class QuestionAdapter  extends ArrayAdapter<Questions> {

    private final List<Questions> list;
    private LayoutInflater inflater;
    private Activity activity;



    public QuestionAdapter(Context context, int resource, List<Questions> list) {
        super(context, resource, list);
        this.list = list;
    }

    static class ViewHolder {
        protected TextView categoryName;
        protected CheckBox categoryCheckBox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflator = LayoutInflater.from(getContext());
            convertView = inflator.inflate(R.layout.questons_list, null);
            viewHolder = new ViewHolder();
            viewHolder.categoryName = (TextView) convertView.findViewById(R.id.dimensions);
            viewHolder.categoryCheckBox = (CheckBox) convertView.findViewById(R.id.checkQuestion);
            convertView.setTag(viewHolder);
            convertView.setTag(R.id.dimensions, viewHolder.categoryName);
            convertView.setTag(R.id.checkQuestion, viewHolder.categoryCheckBox);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.categoryCheckBox.setTag(position);
        viewHolder.categoryName.setText(list.get(position).getDimensionName());
        viewHolder.categoryCheckBox.setText(list.get(position).getName());

        return convertView;

    }


    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    /*@Override
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
    }*/
}
