package com.knight.clothes.models;

import com.google.gson.annotations.SerializedName;

public class Gallery {
    @SerializedName("id")
    private int id;

    @SerializedName("image_source")
    private String image;

    @SerializedName("index")
    private int index;

    @SerializedName("product_id")
    private int productId;

    public Gallery(int id, String image, int index, int productId) {
        this.id = id;
        this.image = image;
        this.index = index;
        this.productId = productId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
