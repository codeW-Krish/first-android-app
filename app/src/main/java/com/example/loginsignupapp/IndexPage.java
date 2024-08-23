package com.example.loginsignupapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class IndexPage extends AppCompatActivity {

    TextView tv_userinfo;
    Button btn_edit_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_index_page);

        tv_userinfo = (TextView) findViewById(R.id.tv_userinfo);
        btn_edit_profile = (Button) findViewById(R.id.btn_edit_profile);

        Intent intent = getIntent();
        String email = intent.getStringExtra("key_email");;
        if (intent != null && intent.hasExtra("key_username")) {
            String username = intent.getStringExtra("key_username");
            tv_userinfo.setText("Hello "+username);
        }

        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Editprofile.class);
                intent.putExtra("ep_key_email",email);
                startActivity(intent);
            }
        });

    }
}