package com.example.loginsignupapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class ResetPasswordActivity extends AppCompatActivity {

    EditText et_rp_phone,et_rp_pass,et_rp_cof_pass;
    Button btn_reset_pass;
    DBhelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_password);

        DB = new DBhelper(this);
        et_rp_phone = (EditText) findViewById(R.id.et_rp_phone);
        et_rp_pass = (EditText) findViewById(R.id.et_rp_pass);
        et_rp_cof_pass = (EditText) findViewById(R.id.et_rp_cof_pass);
        btn_reset_pass = (Button) findViewById(R.id.btn_reset_pass);

        btn_reset_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = et_rp_phone.getText().toString();
                String newpass = et_rp_pass.getText().toString();
                String newcofpass = et_rp_cof_pass.getText().toString();

                if(phone.isEmpty() || newpass.isEmpty() || newcofpass.isEmpty()){
                    showToast("Empty fields... All fields are compulsory to fill.");
                    return;
                }
                Intent i = getIntent();
                String email = i.getStringExtra("key_email");

                if(newpass.equals(newcofpass)){
                    if(DB.update_password(phone,email,newpass)){
                        showToast("Password reset successfully");
                        Intent resultIntent = new Intent(); // Used for pass data between activities without starting a new one.
                        resultIntent.putExtra("RESULT_EMAIL",email);
                        setResult(RESULT_OK,resultIntent); // we're not starting activity we're just returning the data to MainActivity
                        //startActivity(resultIntent); // This will start Activity that's why we don't use startActivity();
                        finish(); // This will finish/close the ResetPasswordActivity, No need to press back button
                    }else{
                        showToast("Invalid details or the user not exists with this email: "+email);
                    }
                }else{
                    showToast("Password not matching try again...");
                }
            }
        });
    }

    public void showToast(String text){
        Toast toast = Toast.makeText(this,text,Toast.LENGTH_SHORT);
        toast.show();
    }
}