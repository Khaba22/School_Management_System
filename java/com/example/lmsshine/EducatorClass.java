package com.example.lmsshine;

public class EducatorClass {

    String name, username, email, sName, password;

    public EducatorClass() {
        // empty constructor
    }


    public EducatorClass(String name, String username, String email, String sName, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.sName = sName;
        this.password = password;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
