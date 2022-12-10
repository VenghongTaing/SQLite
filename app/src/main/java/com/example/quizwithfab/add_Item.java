package com.example.quizwithfab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class add_Item extends AppCompatActivity {

    EditText courseName, courseCredit, courseFee, courseDescription;
    Button addBtn;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        db = new DBHelper(this);

        courseName = findViewById(R.id.nameTxt);
        courseCredit = findViewById(R.id.creditTxt);
        courseFee = findViewById(R.id.courseFeeTxt);
        courseDescription = findViewById(R.id.descriptionTxt);

        addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = courseName.getText().toString();
                String credit = courseCredit.getText().toString();
                String fee = courseFee.getText().toString();
                String description = courseDescription.getText().toString();

                db.addCourse(name, credit, fee, description);

                //Clear all text in edittext and back to main activity
                courseName.setText("");
                courseCredit.setText("");
                courseFee.setText("");
                courseDescription.setText("");
                finish();
            }
        });

        //Set the title of the activity
        setTitle(file_Read_String(this, "CourseName"));

        //Get the value from row clicked
        courseName.setText(file_Read_String(this, "CourseName"));
        courseCredit.setText(file_Read_String(this, "CourseCredit"));
        courseFee.setText(file_Read_String(this, "CourseFee"));
        courseDescription.setText(file_Read_String(this, "CourseDescription"));
    }

    public String file_Read_String(Context context, String FileName_HadSave){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String appcode = sharedPreferences.getString(FileName_HadSave, "");
        String data = appcode;
        return  data;
    }
}