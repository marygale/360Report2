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
import java.util.ArrayList;
import android.widget.Toast;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;

public class QuestionAdapter  extends ArrayAdapter<Questions> {

    private final List<Questions> list;
    private LayoutInflater inflater;
    private Activity activity;
    private ArrayList<String> selectedQuestion = new ArrayList<String>();



    public QuestionAdapter(Context context, int resource, List<Questions> list) {
        super(context, resource, list);
        this.list = list;
    }

     static HashMap<String, String> map = new HashMap<>();

    static class ViewHolder {
        protected TextView txtId;
        protected TextView txtSurveyId;
        protected TextView categoryName;
        protected CheckBox categoryCheckBox;

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

            /** invisiblefield **/
           /* viewHolder.txtId = (TextView) convertView.findViewById(R.id.questionId);
            viewHolder.txtSurveyId = (TextView) convertView.findViewById(R.id.surveyId);*/

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

        /*convertView.setTag(q.getSurveyId());
        Log.e("convertView :: ", convertView.getTag().toString());
        viewHolder.txtId.setText(q.getId());
        viewHolder.txtSurveyId.setText(q.getSurveyId());*/

        viewHolder.categoryCheckBox.setChecked(q.getSelected());
        viewHolder.categoryCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Integer pos = (Integer)  viewHolder.categoryCheckBox.getTag();*/
                String name = (String) viewHolder.categoryCheckBox.getText();
                Object id = (Object) viewHolder.categoryCheckBox.getTag();
                Object surveyId = (Object) viewHolder.categoryName.getTag();

               /* String id = (String) viewHolder.txtId.getTag();
                String survey = (String) viewHolder.txtSurveyId.getTag();
                String survey = (String) viewHolder.txtSurveyId.getTag();*/

               Map<String, String> en = new HashMap<String, String>();
               en.put("name", name);
               en.put("id", id.toString());
               en.put("surveyId", surveyId.toString());


               /* selectedQuestion.add(name);
                selectedQuestion.add(surveyId);*/
                 Log.e("QUESTIONADAPTER :: ", en.toString());

                /*Integer idNumber = list.get(pos).getId();
                String id = new Integer(idNumber).toString();

                Map<String, String> Question = new HashMap<String, String>();
                Question.put("name", name);
                Question.put("id", id);*/

                /*String tag = (String) viewHolder.categoryCheckBox.getTag();
                Log.e("TAG ::: ", tag);*/
                Toast.makeText(getContext(), "Checkbox "+name+" clicked!", Toast.LENGTH_SHORT).show();


                /*Toast.makeText(getContext(), q.toString(), Toast.LENGTH_SHORT).show();
                if(list.get(pos).getSelected()){
                    list.get(pos).setSelected(false);
                }else{
                    list.get(pos).setSelected(true);
                    list.get(pos).setName(name);
                    list.get(pos).setName(name);
                    selectedQuestion.add(name);
                    Integer idNumber = list.get(pos).getId();
                    String id = new Integer(idNumber).toString();
                    selectedQuestion.add(id);
                    Toast.makeText(getContext(), "QUESTION ID "+id+" clicked!", Toast.LENGTH_SHORT).show();
                }
                Log.e("QUESTIONADAPTER :: ", selectedQuestion.toString());*/
                /*Map<String, String> Survey = new HashMap<String, String>();*/


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

    /*@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.questons_list, null);

        TextView name = (TextView) convertView.findViewById(R.id.dimensions);
        CheckBox checkQuestion = (CheckBox) convertView.findViewById(R.id.checkQuestion);

        Questions q = q;
        name.setText(q.getDimensionName());
        checkQuestion.setText(q.getName());
        return convertView;
    }*/
}
