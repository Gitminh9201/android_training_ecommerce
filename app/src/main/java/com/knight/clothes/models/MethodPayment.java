package com.knight.clothes.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MethodPayment implements Serializable {

    @SerializedName("id")
    int id;
    @SerializedName("title")
    String title;
    @SerializedName("information")
    String information;
    @SerializedName("icon_source")
    String icon_source;

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public MethodPayment(int id, String title, String information, String icon_source) {
        this.id = id;
        this.title = title;
        this.information = information;
        this.icon_source = icon_source;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon_source() {
        return icon_source;
    }

    public void setIcon_source(String icon_source) {
        this.icon_source = icon_source;
    }

    public MethodPayment(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public MethodPayment(int id, String title, String information) {
        this.id = id;
        this.title = title;
        this.information = information;
    }
}
