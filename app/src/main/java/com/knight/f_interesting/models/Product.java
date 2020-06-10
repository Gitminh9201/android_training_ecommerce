package com.knight.f_interesting.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("image_source")
    private String image;

    @SerializedName("content")
    private String content;

    @SerializedName("price")
    private int price;

    @SerializedName("price_compare")
    private int priceCompare;

    @SerializedName("category")
    private Category category;

    @SerializedName("brand")
    private Brand brand;

    public List<Product> getRelated() {
        return related;
    }

    public void setRelated(List<Product> related) {
        this.related = related;
    }

    @SerializedName("related")
    private List<Product> related;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @SerializedName("group")
    private Group group;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    @SerializedName("gallery")
    private List<Gallery> gallery;

    public Product(){}

    public Product(int id, String title, String image, String content, int price, Group group,
                   int priceCompare, List<Gallery> gallery, Brand brand, Category category, List<Product> related) {
        this.category = category;
        this.brand = brand;
        this.group = group;
        this.related = related;
        this.id = id;
        this.title = title;
        this.image = image;
        this.content = content;
        this.price = price;
        this.priceCompare = priceCompare;
        this.gallery = gallery;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPriceCompare() {
        return priceCompare;
    }

    public void setPriceCompare(int priceCompare) {
        this.priceCompare = priceCompare;
    }

    public List<Gallery> getGallery() {
        return gallery;
    }

    public void setGallery(List<Gallery> gallery) {
        this.gallery = gallery;
    }
}
