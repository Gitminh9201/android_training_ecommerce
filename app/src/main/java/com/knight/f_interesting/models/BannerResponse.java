package com.knight.f_interesting.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BannerResponse {

    @SerializedName("status")
    private int status;

    @SerializedName("count")
    private int count;

    @SerializedName("banners")
    private List<Banner> banners;

    public BannerResponse(int status, int count, List<Banner> banners) {
        this.status = status;
        this.count = count;
        this.banners = banners;
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

    public List<Banner> getBanners() {
        return banners;
    }

    public void setBanners(List<Banner> banners) {
        this.banners = banners;
    }
}
