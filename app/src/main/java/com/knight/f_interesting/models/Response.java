package com.knight.f_interesting.models;

import com.google.gson.annotations.SerializedName;

public class Response<T> {
    @SerializedName("status")
    private int status;
    @SerializedName("data")
    private T data;

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

    public Response(int status, T data) {
        this.status = status;
        this.data = data;
    }
}
