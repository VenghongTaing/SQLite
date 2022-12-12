package com.example.quizwithfab;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserConnector extends SQLiteOpenHelper{

    public static final String DATABASE_TABLE = "tblUser";

    public UserConnector(Context context){
        super(context, "AddCourse.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + DATABASE_TABLE + " (user_ID INTEGER PRIMARY KEY AUTOINCREMENT, userName TEXT, userPass TEXT,createDate TEXT) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
    }
}
