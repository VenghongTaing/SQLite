package com.example.quizwithfab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class RegisterUserActivity extends AppCompatActivity {
    TextInputLayout userName,userPass,userConfirmPass;
    Button btnRegister;
    ProgressBar registerPB;
    //--Declaring database object
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //--open database---
        dbHelper = new DBHelper(RegisterUserActivity.this);


        //----------EditText------
        userName = (TextInputLayout) findViewById(R.id.userNameEdtR);
        userPass = (TextInputLayout) findViewById(R.id.passEdtR);
        userConfirmPass = (TextInputLayout) findViewById(R.id.confirmPassEdt);
        registerPB = (ProgressBar) findViewById(R.id.registerPB);
        //---Button
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerPB.setVisibility(View.VISIBLE);
                String user_name = userName.getEditText().getText().toString();
                String user_pass = userPass.getEditText().getText().toString();
                String user_confirm_pass = userConfirmPass.getEditText().getText().toString();

                if(user_name.isEmpty()){
                    userName.getEditText().setError("Empty");
                    registerPB.setVisibility(View.GONE);
                }else if(user_pass.isEmpty()){
                    userPass.getEditText().setError("Empty");
                    registerPB.setVisibility(View.GONE);
                }else if(user_confirm_pass.isEmpty()){
                    userConfirmPass.getEditText().setError("Empty");
                    registerPB.setVisibility(View.GONE);
                }else if(!user_confirm_pass.equals(user_pass)){
                    userConfirmPass.getEditText().setError("Not match");
                    registerPB.setVisibility(View.GONE);
                }else{
                    registerPB.setVisibility(View.GONE);
                   dbHelper.RegisterUserLogin(user_name,user_pass);



                }
            }
        });

    }
    public void goToLogin(View view) {
        startActivity(new Intent(RegisterUserActivity.this, LoginUserActivity.class));
        finish();
    }
}