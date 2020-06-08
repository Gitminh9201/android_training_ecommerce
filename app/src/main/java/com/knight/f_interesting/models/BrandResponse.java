package com.knight.f_interesting.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BrandResponse {

    @SerializedName("status")
    private int status;

    @SerializedName("count")
    private int count;

    @SerializedName("brands")
    private List<Brand> brands;

    public BrandResponse(int count, List<Brand> brands) {
        this.count = count;
        this.brands = brands;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }
}
