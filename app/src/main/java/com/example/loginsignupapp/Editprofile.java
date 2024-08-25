package com.example.loginsignupapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class Editprofile extends AppCompatActivity {

    EditText ep_username,ep_email,ep_phone;
    Button btn_save_changes,btn_cancel,btn_change_picture;
    TextView display_email;
    DBhelper DB;
    String currentEmail;
    String currentUsername;
    ImageView iv_profile_picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editprofile);

        ep_username = (EditText) findViewById(R.id.ep_username);
        ep_email = (EditText) findViewById(R.id.ep_email);
        ep_phone = (EditText) findViewById(R.id.ep_phone);
        btn_save_changes = (Button) findViewById(R.id.btn_save_changes);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_change_picture = (Button) findViewById(R.id.btn_change_picture);
        iv_profile_picture = (ImageView) findViewById(R.id.iv_profile_picture);
        display_email = (TextView) findViewById(R.id.tv_email);
        DB = new DBhelper(this);

        Intent intent = getIntent();
        currentEmail = intent.getStringExtra("ep_key_email");
        String email = currentEmail;
        currentUsername = DB.get_username(currentEmail);

        //String tv_username = currentUsername;
        String tv_email = currentEmail;
        //String tv_phone = DB.get_phone_no(currentEmail);

        ep_username.setText(currentUsername);
        display_email.setText("Email: "+tv_email);
        ep_phone.setText(currentEmail);

        btn_save_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newUsername = ep_username.getText().toString();
                String up_email = ep_email.getText().toString();
                String phone = ep_phone.getText().toString();

                if(newUsername.isEmpty() && up_email.isEmpty() && phone.isEmpty()){
                    showToast("All fields are empty... Nothing to Update");
                    return;
                }

                boolean is_anythingUpdated = false;

                if(!newUsername.isEmpty() && !newUsername.equals(currentUsername)){
                    if(DB.update_username(email,newUsername)){
                        currentUsername = newUsername;
                        is_anythingUpdated = true;
                    }else{
                        showToast("Failed to update the username");
                    }
                }
                boolean emailUpdatedSuccessfully = false;
                if(!up_email.isEmpty()){
                    if(!DB.check_user_exists(up_email)){ // This condition check that the new email provided by the user doesn't exists already in th DB
                        if(DB.update_email(currentEmail,up_email)){
                            currentEmail = up_email; // Update the current email if successful
                            is_anythingUpdated = true;
                            emailUpdatedSuccessfully = true;
                        }else{
                            showToast("Failed to update the email");
                        }
                    }else{
                        showToast("User already exists with this email");
                        return;
                    }
                }

                if(!phone.isEmpty()){
                    if(DB.update_phone_no(currentEmail,phone)){
                        is_anythingUpdated = true;
                    }else{
                        showToast("Failed to update phone number");
                    }
                }

                if(is_anythingUpdated){
                    if (emailUpdatedSuccessfully) {
                        showToast("Email updated successfully");
                    }
                    showToast("Profile updated successfully");
                    Intent resultIntent = new Intent(); // It doesn’t need to be initialized with any specific component or action because it’s used solely to carry data back to the parent activity.
                    resultIntent.putExtra("key_username", currentUsername);
                    resultIntent.putExtra("key_email", currentEmail);
                    setResult(RESULT_OK, resultIntent); //  indicates that the activity was successful and attaches the Intent with the result data.
                    finish();
                }else{
                    showToast("No changes were made");
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
    public void showToast(String text){
        Toast toast = Toast.makeText(this,text,Toast.LENGTH_SHORT);
        toast.show();
    }
}