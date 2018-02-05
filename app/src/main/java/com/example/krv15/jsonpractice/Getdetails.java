package com.example.krv15.jsonpractice;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;


public class Getdetails extends Activity {
    TextView tvname,tvsurname,tvage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getdetails);
        tvname    = findViewById(R.id.name);
        tvsurname = findViewById(R.id.surname);
        tvage     = findViewById(R.id.age);
    }
}
