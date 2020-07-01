package com.knight.f_interesting.models;

import java.io.Serializable;

public class MethodPayment implements Serializable {
    int id;
    String title;
    String icon_source;

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

    public MethodPayment(int id, String title, String icon_source) {
        this.id = id;
        this.title = title;
        this.icon_source = icon_source;
    }
}
