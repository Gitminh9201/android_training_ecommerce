package com.knight.f_interesting.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseList<T> {
    @SerializedName("count")
    private int count;
    @SerializedName("status")
    private int status;
    @SerializedName("data")
    private List<T> data;

    public ResponseList(int count, int status, List<T> data) {
        this.count = count;
        this.status = status;
        this.data = data;
    }

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

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
