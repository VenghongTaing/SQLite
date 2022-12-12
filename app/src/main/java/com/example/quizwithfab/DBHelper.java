package com.example.quizwithfab;
import androidx.annotation.Nullable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

class DBHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "AddCourse.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "tblCourse";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_COURSE_NAME = "course_name";
    private static final String COLUMN_COURSE_CREDIT = "course_credit";
    private static final String COLUMN_COURSE_FEE = "course_fee";
    private static final String COLUMN_COURSE_DESCRIPTION = "course_description";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //sql schema
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_COURSE_NAME + " TEXT, " +
                COLUMN_COURSE_CREDIT + " TEXT, " +
                COLUMN_COURSE_FEE + " TEXT, " +
                COLUMN_COURSE_DESCRIPTION + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //define method from SQLOpenHelper exten Class to add course
    void addCourse(String courseName, String courseCredit, String courseFee, String courseDescription){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_COURSE_NAME, courseName);
        contentValues.put(COLUMN_COURSE_CREDIT, courseCredit);
        contentValues.put(COLUMN_COURSE_FEE, courseFee);
        contentValues.put(COLUMN_COURSE_DESCRIPTION, courseDescription);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}