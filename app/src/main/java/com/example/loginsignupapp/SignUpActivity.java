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

    EditText et_signup_name, et_signup_email, et_signup_phone, et_signup_pass, et_signup_cof_pass;
    Button btn_signin;
    TextView link_to_login;
    DBhelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        et_signup_name = (EditText) findViewById(R.id.et_signup_name);
        et_signup_email = (EditText) findViewById(R.id.et_signup_email);
        et_signup_phone = (EditText) findViewById(R.id.et_signup_phone);
        et_signup_pass = (EditText) findViewById(R.id.et_signup_pass);
        et_signup_cof_pass = (EditText) findViewById(R.id.et_signup_cof_pass);
        btn_signin = (Button) findViewById(R.id.btn_signin);
        link_to_login = (TextView) findViewById(R.id.link_to_login);
        DB = new DBhelper(this);

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    String name = et_signup_name.getText().toString();
                    String email = et_signup_email.getText().toString();
                    String phone = et_signup_phone.getText().toString();
                    String pass = et_signup_pass.getText().toString();
                    String cof_pass = et_signup_cof_pass.getText().toString();

                    if(name.isEmpty() || email.isEmpty() || phone.isEmpty() || pass.isEmpty() || cof_pass.isEmpty()){
                        showToast("All fields are compulsory to fill...");
                        return;
                    }

                    if(!email.contains("@")){
                        showToast("Invalid Email Address...");
                        et_signup_email.setText("");
                        return;
                    }

                    if(phone.length()!=10){
                        showToast("Not a valid Phone Number...");
                        et_signup_phone.setText("");
                        return;
                    }

                    if(!pass.equals(cof_pass)){
                        showToast("Password Not Matching...");
                        return;
                    }

                    if(DB.check_user_exists(email)){
                        showToast("User already exists with this email address");
                        return;
                    }

                    boolean successfully_signup = DB.insert_user_to_db(name,email,phone,pass,cof_pass);

//                Bundle b = new Bundle();
//                b.putString("key_name",name);
//                b.putString("key_email",email);
//                b.putString("key_phone",phone);
//                b.putString("key_pass",pass);
//                b.putString("key_cof_pass",cof_pass);
                    if(successfully_signup){
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                        intent.putExtra("key_name",name);
                        startActivity(intent);
                    }else{
                        showToast("Signup failed. Please try again");
                    }
                }catch(Exception e){
                    showToast("An error occurred: "+e.getMessage());
                    e.printStackTrace(); //Log the exception details
                }

            }
        });
        link_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }catch(Exception e){
                    showToast("An error occurred while starting the activity: "+e.getMessage());
                    e.printStackTrace(); // Log the exception details
                }
            }
        });
    }
    public void showToast(String text){
        Toast toast = Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG);
        toast.show();
    }
}