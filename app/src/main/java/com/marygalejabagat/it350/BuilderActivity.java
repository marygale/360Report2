package com.marygalejabagat.it350;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.marygalejabagat.it350.model.Questions;

import java.util.ArrayList;
import java.util.List;

public class BuilderActivity extends AppCompatActivity {

    private List<Questions> question_list = new ArrayList<Questions>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.builder_fragment2);
        String s = getIntent().getStringExtra("dimension");
        Log.e("DIMENSION", s);
    }
}
