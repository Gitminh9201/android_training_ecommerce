package com.knight.clothes.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Group {
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("products")
    private List<Product> products;

    @SerializedName("index")
    private int index;

    public Group(int id, String title, List<Product> products, int index) {
        this.id = id;
        this.title = title;
        this.products = products;
        this.index = index;
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
