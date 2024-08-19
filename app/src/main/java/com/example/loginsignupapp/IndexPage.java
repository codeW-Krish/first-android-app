package com.example.loginsignupapp;


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

        Bundle b = getIntent().getExtras();
        if(b!=null){
            String tv_name = b.getString("key_name");
            tv_userinfo.setText("Hello, "+tv_name);
        }
    }
}