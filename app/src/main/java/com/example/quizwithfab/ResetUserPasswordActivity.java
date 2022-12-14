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

public class ResetUserPasswordActivity extends AppCompatActivity {
    TextInputLayout userNewPass, userConfirmPass;
    Button btnResetPassword;
    ProgressBar reset_psswordPB;
    String userNameFromForgetPasswordForm;

    //--Declaring database object
    private DBHelper myDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_user_password);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Open database
        myDB = new DBHelper(this);
        userNewPass = (TextInputLayout) findViewById(R.id.userNewPass);
        userConfirmPass = (TextInputLayout) findViewById(R.id.userConfirmPass);
        reset_psswordPB = (ProgressBar) findViewById(R.id.resetpasswordPB);
        userNameFromForgetPasswordForm = getIntent().getExtras().getString("username");
        btnResetPassword = (Button) findViewById(R.id.btnReset);
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset_psswordPB.setVisibility(View.VISIBLE);
                String newPass = userNewPass.getEditText().getText().toString();
                String confirmPass = userConfirmPass.getEditText().getText().toString();
                if (newPass.isEmpty()) {
                    userNewPass.getEditText().setError("Empty");
                    reset_psswordPB.setVisibility(View.GONE);
                } else if (confirmPass.isEmpty()) {
                    userConfirmPass.getEditText().setError("Empty");
                    reset_psswordPB.setVisibility(View.GONE);
                }else if(!confirmPass.equals(newPass)) {
                    userConfirmPass.getEditText().setError("Not match");
                    reset_psswordPB.setVisibility(View.GONE);
                }else{
                    Toast.makeText(getApplicationContext(),newPass, Toast.LENGTH_SHORT).show();
                    try{
                        reset_psswordPB.setVisibility(View.GONE);
                        myDB.updatepassword(userNameFromForgetPasswordForm,newPass);
                        Toast.makeText(ResetUserPasswordActivity.this,"Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ResetUserPasswordActivity.this,LoginUserActivity.class));
                        finish();

                    }catch(Exception e){
                        reset_psswordPB.setVisibility(View.GONE);
                        Toast.makeText(ResetUserPasswordActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }

    public void goToLogin(View view) {
        startActivity(new Intent(ResetUserPasswordActivity.this, LoginUserActivity.class));
        finish();
    }
}