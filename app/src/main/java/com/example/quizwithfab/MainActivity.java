package com.example.quizwithfab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addBtn;
    View view;

    DBHelper myDB;

    //create array list to store data
    ArrayList<String> course_id, course_name, course_credit, course_fee, course_description;

    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        recyclerView = findViewById(R.id.recyclerView);

        addBtn = findViewById(R.id.fab);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //hide button
                addBtn.hide();

                //Clear data in viewholder
                file_Write(MainActivity.this, "CourseID", "");
                file_Write(MainActivity.this, "CourseName", "");
                file_Write(MainActivity.this, "CourseCredit", "");
                file_Write(MainActivity.this, "CourseFee", "");
                file_Write(MainActivity.this, "CourseDescription", "");

                Intent intent = new Intent(MainActivity.this, add_Item.class);
                startActivity(intent);
            }
        });
        view = findViewById(R.id.enrollview);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EnrollCourse.class);
                startActivity(intent);
            }
        });

        //Connect to database and create table
        myDB = new DBHelper(MainActivity.this);

        course_id = new ArrayList<>();
        course_name = new ArrayList<>();
        course_credit = new ArrayList<>();
        course_fee = new ArrayList<>();
        course_description = new ArrayList<>();

        storeDataIntArrays();

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(customAdapter);

    }

    //Checking if main activity is on restart
    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }

    ArrayList<CourseList> list = new ArrayList<>();

    void storeDataIntArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        else{
//            Toast.makeText(this, "Data found", Toast.LENGTH_SHORT).show();
            while (cursor.moveToNext()){
//                Log.d("123", cursor.getString(0));
                list.add(new CourseList(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)));
            }
            customAdapter = new CustomAdapter(MainActivity.this, list);
            recyclerView.setAdapter(customAdapter);
        }
    }

    private void file_Write(Context context, String FileName_WillSave, String value_WillSave){

        String data = value_WillSave;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(FileName_WillSave, data);
        editor.apply();
    }
}