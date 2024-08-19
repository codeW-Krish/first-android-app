package com.example.loginsignupapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.security.Signature;

public class MainActivity extends AppCompatActivity {

    EditText et_username,et_password;
    Button btn_login;
    TextView goto_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        53CBF1 E9E6F4F4

        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        goto_signup = (TextView) findViewById(R.id.goto_signup);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et_username.getText().toString();
                String pass = et_password.getText().toString();

                if(name.isEmpty() || pass.isEmpty()){
                    Toast toast = Toast.makeText(getApplicationContext(),"Empty field...",Toast.LENGTH_LONG);
                    toast.show();
                    return;

                }

                Intent intent = new Intent(getApplicationContext(), IndexPage.class);
                intent.putExtra("key_name",name);
                startActivity(intent);
            }
        });

        goto_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        // Lamda Function
        //btn_login.setOnClickListener(view -> {
        //  ....
        //});
    }
}