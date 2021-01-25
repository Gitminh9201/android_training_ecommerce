package com.knight.clothes.models;

import com.google.gson.annotations.SerializedName;

public class Banner {

    @SerializedName("id")
    private int id;

    @SerializedName("index")
    private int index;

    @SerializedName("image_source")
    private String image;

    @SerializedName("slogan")
    private String slogan;

    public Banner(int id, int index, String image, String slogan) {
        this.id = id;
        this.index = index;
        this.image = image;
        this.slogan = slogan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }
}
