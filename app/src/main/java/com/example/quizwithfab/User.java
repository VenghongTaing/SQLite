package com.example.quizwithfab;

public class User {
    private String userName;
    private String userPass;

    //Create empty constructor
    public User() {
    }

    //Create constructor for assign value to this variables
    public User(String userName, String userPass) {
        this.userName = userName;
        this.userPass = userPass;
    }

    //Getting and Setting
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }
}
