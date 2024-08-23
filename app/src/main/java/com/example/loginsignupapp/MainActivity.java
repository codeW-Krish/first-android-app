package com.example.loginsignupapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText et_email,et_password;
    Button btn_login;
    TextView goto_signup,goto_forgot_pass;
    private ActivityResultLauncher<Intent> resetPasswordLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        53CBF1 E9E6F4F4
        DBhelper DB;

        et_email = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        goto_forgot_pass = (TextView) findViewById(R.id.goto_forgot_pass);
        goto_signup = (TextView) findViewById(R.id.goto_signup);
        DB = new DBhelper(this);


        // Set up ActivityResultLauncher
        resetPasswordLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    String email = data.getStringExtra("RESULT_EMAIL");
                    et_email.setText(email);
                    et_password.setText("");
                    //btn_login.performClick();
                }
            }
        });

        // Above Code breakdown
        /*
            ActivityResultContracts.StartActivityForResult(): This specifies that you want to start an activity and expect a result back.
            Callback (result -> { ... }): This is a lambda function that handles the result when the activity finishes.
                                          This is the function that gets called when the result from the started activity is returned.

            result.getResultCode(): Checks the result code of the returned activity. Typically, RESULT_OK indicates success.
         */

        /* Alternative way of above resetPasswordLauncher
        resetPasswordLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        String email = data.getStringExtra("RESULT_EMAIL");
                        et_email.setText(email);
                        et_password.setText("");
                        showToast("Password reset successfully");
                        //btn_login.performClick();
                    }
                }
            }
        });
         */

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et_email.getText().toString();
                String pass = et_password.getText().toString();

                if(email.isEmpty() || pass.isEmpty()){
                    Toast toast = Toast.makeText(getApplicationContext(),"Empty field...",Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }

                if(DB.check_user_exists(email)){
                    if(DB.check_username_and_password(email,pass)){
                        Intent intent = new Intent(getApplicationContext(), IndexPage.class);
                        String username = DB.get_username(email);
                        intent.putExtra("key_username",username);
                        intent.putExtra("key_email",email);
                        startActivity(intent);
                    }else{
                        showToast("Wrong Password.. Try again");
                    }
                }else{
                    showToast("Invalid credentials");
                }
            }
        });

        goto_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        goto_forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et_email.getText().toString();
                Intent intent = new Intent(getApplicationContext(), ResetPasswordActivity.class);
                intent.putExtra("key_email",email);
                resetPasswordLauncher.launch(intent);
//                startActivityForResult(intent,1);
            }
        });

        // Lamda Function
        //btn_login.setOnClickListener(view -> {
        //  ....
        //});
    }
    public void showToast(String text){
        Toast toast = Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT);
        toast.show();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
//            String email = data.getStringExtra("RESULT_EMAIL");
//            et_email.setText(email);
//            et_password.setText("");
//            showToast("Password reset successfully");
////            btn_login.performClick();
//        }
//    }
}