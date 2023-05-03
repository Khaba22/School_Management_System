package com.example.lmsshine;

public class User {

    public String fullName, schoolName, email, types;

    public User(String fullName, String schoolName, String email, String password, String types) {

    }

    public User(String fullName, String schoolName, String email, String types) {
        this.fullName = fullName;
        this.schoolName = schoolName;
        this.email = email;
        this.types = types;
    }

    public String getFullName() {
        return fullName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getEmail() {
        return email;
    }
}
