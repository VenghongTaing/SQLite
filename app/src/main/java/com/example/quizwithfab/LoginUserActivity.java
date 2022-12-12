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
        myDB = new DBHelper(LoginUserActivity.this);
        userName = (TextInputLayout) findViewById(R.id.userNameEdt);
        userPass = (TextInputLayout) findViewById(R.id.userNameEdt);
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
                    ArrayList<User> userDetails = myDB.loginUser(username, password);

                    if (userDetails.size() != 0) {
                        User user = userDetails.get(0);
                        Toast.makeText(getApplicationContext(), "Valid user", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginUserActivity.this, MainActivity.class));
                        finish();
                    } else {
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
}