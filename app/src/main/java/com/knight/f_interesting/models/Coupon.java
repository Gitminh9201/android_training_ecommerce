package com.knight.f_interesting.models;

import com.google.gson.annotations.SerializedName;

public class Coupon {

    @SerializedName("id")
    private int id;

    @SerializedName("code")
    private String code;

    @SerializedName("title")
    private String title;

    @SerializedName("image_source")
    private String image;

    @SerializedName("description")
    private String description;

    @SerializedName("type")
    private int type;

    @SerializedName("discount")
    private int discount;

    @SerializedName("max")
    private int max;

    @SerializedName("count")
    private int count;

    @SerializedName("condition")
    private int condition;

    @SerializedName("expired")
    private String expired;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }

    public Coupon(int id, String code, String title, String image, String description,
                  int type, int discount, int max, int count, int condition, String expired) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.image = image;
        this.description = description;
        this.type = type;
        this.discount = discount;
        this.max = max;
        this.count = count;
        this.condition = condition;
        this.expired = expired;
    }
}
