package com.knight.clothes.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Brand {
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("image_source")
    private String image;

    @SerializedName("description")
    private String description;

    @SerializedName("country")
    private String country;

    @SerializedName("products")
    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Brand(int id, String title, String image, String description, String country, List<Product> products) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.description = description;
        this.country = country;
        this.products = products;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
