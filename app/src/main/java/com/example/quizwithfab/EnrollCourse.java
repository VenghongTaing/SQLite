package com.example.quizwithfab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;


public class EnrollCourse extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText searchView;
    DBHelper dbcart;
    Button searchBtn;
    //create array list to store data
    ArrayList<String> course_id, course_name, course_credit, course_fee, course_description;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_course);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbcart = new DBHelper(EnrollCourse.this);
        course_id = new ArrayList<>();
        course_name = new ArrayList<>();
        course_credit = new ArrayList<>();
        course_fee = new ArrayList<>();
        course_description = new ArrayList<>();

        storeDataIntArrays();

        recyclerView.setLayoutManager(new LinearLayoutManager(EnrollCourse.this));
        recyclerView.setAdapter(customAdapter);

        searchView = findViewById(R.id.edittxtsearch);
        //search data from sqlite with text change
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    //clear data in row
                    list.clear();

                    //if search text is empty
                    //clear search
                    storeDataIntArrays();
                } else {
                    list.clear();

                    //if text is not empty
                    //search text from sqlite
                    searchItem(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void searchItem(String toString) {
        Cursor cursor = dbcart.searchItem(toString);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                list.add(new CourseList(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)));
            }
            customAdapter = new CustomAdapter(EnrollCourse.this, list);
            recyclerView.setAdapter(customAdapter);
        }
    }


    ArrayList<CourseList> list = new ArrayList<>();

    void storeDataIntArrays(){
        Cursor cursor = dbcart.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        else{
             Toast.makeText(this, "Data found", Toast.LENGTH_SHORT).show();
            while (cursor.moveToNext()){
//                Log.d("123", cursor.getString(0));
                list.add(new CourseList(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)));
            }
            customAdapter = new CustomAdapter(EnrollCourse.this, list);
            recyclerView.setAdapter(customAdapter);
        }
    }
}