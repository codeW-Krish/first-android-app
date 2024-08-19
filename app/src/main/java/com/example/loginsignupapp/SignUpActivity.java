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


public class SignUpActivity extends AppCompatActivity {

    EditText et_signin_name,et_signin_email,et_signin_phone,et_signin_pass,et_signin_cof_pass;
    Button btn_signin;
    TextView link_to_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        et_signin_name = (EditText) findViewById(R.id.et_signin_name);
        et_signin_email = (EditText) findViewById(R.id.et_signin_email);
        et_signin_phone = (EditText) findViewById(R.id.et_signin_phone);
        et_signin_pass = (EditText) findViewById(R.id.et_signin_pass);
        et_signin_cof_pass = (EditText) findViewById(R.id.et_signin_cof_pass);
        btn_signin = (Button) findViewById(R.id.btn_signin);
        link_to_login = (TextView) findViewById(R.id.link_to_login);

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et_signin_name.getText().toString();
                String email = et_signin_email.getText().toString();
                String phone = et_signin_phone.getText().toString();
                String pass = et_signin_pass.getText().toString();
                String cof_pass = et_signin_cof_pass.getText().toString();

                if(name.isEmpty() || email.isEmpty() || phone.isEmpty() || pass.isEmpty() || cof_pass.isEmpty()){
//                    Toast toast = Toast.makeText(getApplicationContext(),"All fields are compulsory to fill...",Toast.LENGTH_LONG);
//                    toast.show();
                    showToast("All fields are compulsory to fill...");
                    return;
                }

                if(!email.contains("@")){
                    showToast("Invalid Email Address...");
                    et_signin_email.setText("");
                    return;
                }

                if(phone.length()!=10){
                    showToast("Not a valid Phone Number...");
                    et_signin_phone.setText("");
                    return;
                }

                if(!pass.equals(cof_pass)){
                    showToast("Password Not Matching...");
                    return;
                }
//                Bundle b = new Bundle();
//                b.putString("key_name",name);
//                b.putString("key_email",email);
//                b.putString("key_phone",phone);
//                b.putString("key_pass",pass);
//                b.putString("key_cof_pass",cof_pass);

                Intent intent = new Intent(getApplicationContext(), IndexPage.class);
                intent.putExtra("key_name",name);
                startActivity(intent);

            }
        });
        link_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void showToast(String text){
        Toast toast = Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG);
        toast.show();
    }
}