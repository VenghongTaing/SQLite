package com.example.quizwithfab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    Context context;
    ArrayList<CourseList> list;

    public CustomAdapter(Context context, ArrayList<CourseList> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.activity_my_rows, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        CourseList courseList = list.get(position);

        holder.course_id_txt.setText(courseList.getCourse_id());
        holder.course_name_txt.setText(courseList.getCourse_name());
        holder.course_credit_txt.setText(courseList.getCourse_credit());
        holder.course_fee_txt.setText(courseList.getCourse_fee());
        holder.course_description_txt.setText(courseList.getCourse_description());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                file_Write(context, "CourseID", courseList.getCourse_id());
                file_Write(context, "CourseName", courseList.getCourse_name());
                file_Write(context, "CourseCredit", courseList.getCourse_credit());
                file_Write(context, "CourseFee", courseList.getCourse_fee());
                file_Write(context, "CourseDescription", courseList.getCourse_description());
                Intent intent = new Intent(context, add_Item.class);
                context.startActivity(intent);
            }
        });
    }

    private void file_Write(Context context, String FileName_WillSave, String value_WillSave){

        String data = value_WillSave;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(FileName_WillSave, data);
        editor.apply();
    }

    public String file_Read_String(Context context, String FileName_HadSave){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String appcode = sharedPreferences.getString(FileName_HadSave, "");
        String data = appcode;
        return  data;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView course_id_txt, course_name_txt, course_credit_txt, course_fee_txt, course_description_txt;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            course_id_txt = itemView.findViewById(R.id.course_id);
            course_name_txt = itemView.findViewById(R.id.course_name);
            course_credit_txt = itemView.findViewById(R.id.course_credit);
            course_fee_txt = itemView.findViewById(R.id.course_fee);
            course_description_txt = itemView.findViewById(R.id.course_description);
        }
    }
}
