package com.example.quizwithfab;

public class CourseList {

    private String course_id, course_name, course_credit, course_fee, course_description;

    public CourseList (){

    }

    public CourseList(String course_id, String course_name, String course_credit, String course_fee, String course_description) {
        this.course_id = course_id;
        this.course_name = course_name;
        this.course_credit = course_credit;
        this.course_fee = course_fee;
        this.course_description = course_description;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_credit() {
        return course_credit;
    }

    public void setCourse_credit(String course_credit) {
        this.course_credit = course_credit;
    }

    public String getCourse_fee() {
        return course_fee;
    }

    public void setCourse_fee(String course_fee) {
        this.course_fee = course_fee;
    }

    public String getCourse_description() {
        return course_description;
    }

    public void setCourse_description(String course_description) {
        this.course_description = course_description;
    }
}
