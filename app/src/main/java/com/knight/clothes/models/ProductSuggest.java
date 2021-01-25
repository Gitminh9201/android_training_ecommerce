package com.knight.clothes.models;

import com.google.gson.annotations.SerializedName;

public class ProductSuggest {
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("image_source")
    private String image;

    public ProductSuggest(int id, String title, String image) {
        this.id = id;
        this.title = title;
        this.image = image;
    }

    public ProductSuggest(String title) {
        this.title = title;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
