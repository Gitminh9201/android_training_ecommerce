package com.knight.f_interesting.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryResponse {
    @SerializedName("count")
    private int count;

    @SerializedName("status")
    private int status;

    @SerializedName("categories")
    private List<Category> categories;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public CategoryResponse(int count, int status, List<Category> categories) {
        this.count = count;
        this.status = status;
        this.categories = categories;
    }
}
