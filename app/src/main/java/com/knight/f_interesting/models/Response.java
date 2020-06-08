package com.knight.f_interesting.models;

import com.google.gson.annotations.SerializedName;

public class Response<T> {
    @SerializedName("count")
    private int count;
    @SerializedName("status")
    private int status;
    @SerializedName("data")
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Response(int count, int status, T data) {
        this.count = count;
        this.status = status;
        this.data = data;
    }
}
