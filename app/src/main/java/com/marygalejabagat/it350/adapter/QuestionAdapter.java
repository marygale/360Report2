package com.marygalejabagat.it350.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.marygalejabagat.it350.R;
import com.marygalejabagat.it350.fragment.BuilderFragment;
import com.marygalejabagat.it350.model.Questions;

import java.util.List;
import java.util.ArrayList;
import android.widget.Toast;
import android.util.Log;
import java.util.HashMap;

import butterknife.BindView;

public class QuestionAdapter  extends ArrayAdapter<Questions> {

    private final List<Questions> list;
    private final BuilderFragment fragment;
    private LayoutInflater inflater;
    private Activity activity;
    private ArrayList<HashMap<String, String>> selectedQuestion = new ArrayList<HashMap<String, String>>();
    private Button btnSubmit;



    public QuestionAdapter(Context context, int resource, List<Questions> list, BuilderFragment fragment) {
        super(context, resource, list);
        this.list = list;
        this.fragment = fragment;
    }

     static HashMap<String, String> map = new HashMap<>();

    static class ViewHolder {
        protected TextView categoryName;
        protected CheckBox categoryCheckBox;
        protected CheckBox posCheckBox;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflator = LayoutInflater.from(getContext());
            convertView = inflator.inflate(R.layout.questons_list, null);

            viewHolder.categoryName = (TextView) convertView.findViewById(R.id.dimensions);
            viewHolder.categoryCheckBox = (CheckBox) convertView.findViewById(R.id.checkQuestion);

            convertView.setTag(viewHolder);
            convertView.setTag(R.id.dimensions, viewHolder.categoryName);
            convertView.setTag(R.id.checkQuestion, viewHolder.categoryCheckBox);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Questions q = list.get(position);
        viewHolder.categoryCheckBox.setTag(position);
        viewHolder.categoryCheckBox.setTag(q.getId());
        viewHolder.categoryName.setTag(q.getSurveyId());

        viewHolder.categoryName.setText(q.getDimensionName());
        viewHolder.categoryCheckBox.setText(q.getName());


        viewHolder.categoryCheckBox.setChecked(q.getSelected());
        viewHolder.categoryCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer pos = (Integer)  viewHolder.categoryCheckBox.getTag();
                Log.e("POS", pos.toString());
                String name = (String) viewHolder.categoryCheckBox.getText();
                Object id = (Object) viewHolder.categoryCheckBox.getTag();
                Object surveyId = (Object) viewHolder.categoryName.getTag();

                HashMap<String, String> en = new HashMap<String, String>();
                en.put("name", name);
                en.put("id", id.toString());
                en.put("surveyId", surveyId.toString());
                selectedQuestion.add(en);
                fragment.saveData(selectedQuestion);
                Log.e("QUESTIONADAPTER :: ", selectedQuestion.toString());



            }

        });

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



    public void processQuestion(){

    }
}
