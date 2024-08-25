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
    DBhelper DB;
    private static final int REQUEST_CODE_EDIT_PROFILE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_index_page);

        tv_userinfo = (TextView) findViewById(R.id.tv_userinfo);
        btn_edit_profile = (Button) findViewById(R.id.btn_edit_profile);
        DB = new DBhelper(this);

        Intent intent = getIntent();
        String username = intent.getStringExtra("key_username");
        String email = intent.getStringExtra("key_email");

//        if(intent != null){
//            if(intent.hasExtra("key_username")){
//                username = intent.getStringExtra("key_username");
//            }
//            if(intent.hasExtra("key_email")){
//                email = intent.getStringExtra("key_email");
//            }
//        }

        // Fetch username from the database if it is not passed through the intent
        if(email != null && username == null){
            username = DB.get_username(email);
        }

        // Set the user info or default message
        if(username != null){
            tv_userinfo.setText("Hello "+username);
        }else{
            tv_userinfo.setText("Hello User"); // Default text
        }

        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Editprofile.class);
                intent.putExtra("ep_key_email", email);
                startActivityForResult(intent, REQUEST_CODE_EDIT_PROFILE);
            }
        });
    }
    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_EDIT_PROFILE && resultCode == RESULT_OK) {
            String newUsername = data.getStringExtra("key_username");
            String email = data.getStringExtra("key_email");

            if (newUsername != null) {
                tv_userinfo.setText("Hello " + newUsername);
            } else if (email != null) {
                String username = DB.get_username(email); // Fetch username from DB
                tv_userinfo.setText("Hello " + username);
            }
        }
    }

}