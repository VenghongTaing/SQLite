package com.example.quizwithfab;

import androidx.annotation.Nullable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

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

    //-------------User table

    private static final String TABLE_USER = "tblUser";
    private static final String COLUMN_USER_ID = "_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_PASS = "user_password";
    private static final String COLUMN_USER_TYPE = "user_type";


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

        //-------------user
        String query_user = "CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_NAME + " TEXT, " +
                COLUMN_USER_PASS + " TEXT, " +
                COLUMN_USER_TYPE + " TEXT);";
        db.execSQL(query_user);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    //define method from SQLOpenHelper exten Class to add course
    void addCourse(String courseName, String courseCredit, String courseFee, String courseDescription) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_COURSE_NAME, courseName);
        contentValues.put(COLUMN_COURSE_CREDIT, courseCredit);
        contentValues.put(COLUMN_COURSE_FEE, courseFee);
        contentValues.put(COLUMN_COURSE_DESCRIPTION, courseDescription);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    //define method from SQLOpenHelper exten Class to update course
    void updateCourse(String row_id, String courseName, String courseCredit, String courseFee, String courseDescription) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_COURSE_NAME, courseName);
        contentValues.put(COLUMN_COURSE_CREDIT, courseCredit);
        contentValues.put(COLUMN_COURSE_FEE, courseFee);
        contentValues.put(COLUMN_COURSE_DESCRIPTION, courseDescription);
        long result = db.update(TABLE_NAME, contentValues, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    //define method from SQLOpenHelper exten Class to delete course
    void deleteCourse(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Deleted Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void RegisterUserLogin(String username, String password, String usertype) {
        SQLiteDatabase userDB = this.getWritableDatabase();
        ContentValues con = new ContentValues();
        con.put(COLUMN_USER_NAME, username);
        con.put(COLUMN_USER_PASS, password);
        con.put(COLUMN_USER_TYPE, usertype);
        long result = userDB.insert(TABLE_USER, null, con);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
            userDB.close();
        }
    }

    public Boolean checkusername(String username) {
        SQLiteDatabase userDB = this.getWritableDatabase();
        Cursor cursor = userDB.rawQuery("select * from tblUser where user_name = ?", new String[]{username});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkusernameandpassword(String username, String password) {
        SQLiteDatabase userDB = this.getWritableDatabase();
        Cursor cursor =
                userDB.rawQuery("select * from tblUser where user_name = ? and user_password = ?", new String[]{username, password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    //check user type
    public String checkusertype(String username) {
        SQLiteDatabase userDB = this.getWritableDatabase();
        Cursor cursor =
                userDB.rawQuery("select user_type from tblUser where user_name = ?", new String[]{username});
        String usertype = "";
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                usertype = cursor.getString(0);
            }
        }
        return usertype;
    }

    public ArrayList<User> loginUser(String userName, String userPass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<User> userArrayList = new ArrayList<User>();// create list users
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM tblUser WHERE user_name = '" + userName + "' and user_password = '" + userPass + "'", null);
            //Toast.makeText(context,cursor.toString(), Toast.LENGTH_SHORT).show();
            if (cursor.moveToFirst()) {
                User user = new User();
                user.setUserName(cursor.getString(0));
                Toast.makeText(context, cursor.getString(0), Toast.LENGTH_SHORT).show();
                user.setUserPass(cursor.getString(1));
                userArrayList.add(user); // add infor user to array list

            }
            while (cursor.moveToNext()) ;

        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return userArrayList;
    }


    public void updatepassword(String userNameFromForgetPasswordForm, String newPass) {
        SQLiteDatabase userdb = this.getWritableDatabase();
        String strSQL = "UPDATE tblUser SET user_password = '" + newPass + "' WHERE user_name = '" + userNameFromForgetPasswordForm + "'";
        userdb.execSQL(strSQL);

    }

    public Cursor searchItem(String toString) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_COURSE_NAME + " LIKE '%" + toString + "%'";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
}
