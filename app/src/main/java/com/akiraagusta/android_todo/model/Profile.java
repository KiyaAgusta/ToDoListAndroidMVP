package com.akiraagusta.android_todo.model;

public class Profile {
    private int user_id;
    private int account_id;
    private String full_name;
    private String gender;
    private String birth_date;
    private String email;
    private String profile_picture;
    private String phone_number;

    public Profile(int user_id, int account_id, String full_name, String gender, String birth_date, String email, String profile_picture, String phone_number) {
        this.user_id = user_id;
        this.account_id = account_id;
        this.full_name = full_name;
        this.gender = gender;
        this.birth_date = birth_date;
        this.email = email;
        this.profile_picture = profile_picture;
        this.phone_number = phone_number;
    }

    public Profile(String full_name, String gender, String birth_date, String email, String phone_number) {
        this.full_name = full_name;
        this.gender = gender;
        this.birth_date = birth_date;
        this.email = email;
        this.phone_number = phone_number;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
