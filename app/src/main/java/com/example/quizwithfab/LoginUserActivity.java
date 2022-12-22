package com.example.quizwithfab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class LoginUserActivity extends AppCompatActivity {
    TextInputLayout userName, userPass;
    Button btnLogin;
    ProgressBar loginPB;
    //--Declaring database object
    private DBHelper myDB;
    String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Open database
        myDB = new DBHelper(this);
        userName = (TextInputLayout) findViewById(R.id.userNameEdt);
        userPass = (TextInputLayout) findViewById(R.id.passEdt);
        loginPB = (ProgressBar) findViewById(R.id.loginPB);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginPB.setVisibility(View.VISIBLE);
                string = userName.getEditText().getText().toString(); // Capture username for displaying in another form
                String username = userName.getEditText().getText().toString();
                String password = userPass.getEditText().getText().toString();
                if (username.isEmpty()) {
                    userName.getEditText().setError("Empty");
                    loginPB.setVisibility(View.GONE);
                } else if (password.isEmpty()) {
                    userPass.getEditText().setError("Empty");
                    loginPB.setVisibility(View.GONE);
                } else {
                    Boolean checkUsernameAndPassword = myDB.checkusernameandpassword(username, password);
                    String userType = myDB.checkusertype(username);
                    if (checkUsernameAndPassword == true ) {
                        if (userType.equals("user")){
                            loginPB.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Login successful and User Type USER", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginUserActivity.this,MainActivity.class));
                        }else if (userType.equals("teacher")){
                            loginPB.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Login successful and User Type TEACHER", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginUserActivity.this,MainActivity.class));
                        }
                    } else {
                        loginPB.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Invalid user", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }

    public void goToRegister(View view) {
        startActivity(new Intent(LoginUserActivity.this, RegisterUserActivity.class));
        finish();
    }

    public void gotoForgetPassword(View view) {
        myDB = new DBHelper(this);
        loginPB.setVisibility(View.VISIBLE);
        string = userName.getEditText().getText().toString();
        String userNameForForgetPassword = userName.getEditText().getText().toString();
        if(userNameForForgetPassword.isEmpty()) {
            userName.getEditText().setError("Empty");
            loginPB.setVisibility(View.GONE);
        }else{

            Boolean check_userName = myDB.checkusername(userNameForForgetPassword);
            if(check_userName == true) {
                loginPB.setVisibility(View.GONE);
                Intent forgetPassword_intent = new Intent(LoginUserActivity.this,ResetUserPasswordActivity.class);
                forgetPassword_intent.putExtra("username",string);
                startActivity(forgetPassword_intent);
                finish();
            }else{
                loginPB.setVisibility(View.GONE);
                userName.getEditText().setError("Empty or not exist user");
                Toast.makeText(getApplicationContext(),"Please fill username or not exist user",Toast.LENGTH_SHORT).show();

            }
        }

    }
}