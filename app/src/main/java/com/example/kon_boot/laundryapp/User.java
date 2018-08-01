package com.example.kon_boot.laundryapp;

public class User {
    String name,phn,email,password;
    public User()
    {

    }

    public User(String name, String phn, String email, String password) {
        this.name = name;
        this.phn = phn;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhn() {
        return phn;
    }

    public void setPhn(String phn) {
        this.phn = phn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
