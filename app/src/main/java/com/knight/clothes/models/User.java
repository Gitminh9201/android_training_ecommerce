package com.knight.clothes.models;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("birthday")
    private String birthday;

    public User(String name, String email, String birthday,
                String numberPhone) {
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.numberPhone = numberPhone;
    }

    public User(String name, String email,
                String numberPhone) {
        this.name = name;
        this.email = email;
        this.numberPhone = numberPhone;
    }

    public User(int id, String name, String email, String avatar, String birthday,
                String numberPhone, int gender, String token) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.birthday = birthday;
        this.numberPhone = numberPhone;
        this.gender = gender;
        this.token = token;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    @SerializedName("numberphone")
    private String numberPhone;

    @SerializedName("gender")
    private int gender;

    @SerializedName("token")
    private String token;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User() {
    }

    public User(int id, String name, String email, String avatar, String token) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
