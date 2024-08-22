package com.example.loginsignupapp;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class IndexPage extends AppCompatActivity {

    TextView tv_userinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_index_page);

        tv_userinfo = (TextView) findViewById(R.id.tv_userinfo);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("key_username")) {
            String username = intent.getStringExtra("key_username");
            tv_userinfo.setText("Hello "+username);
        }
    }
}