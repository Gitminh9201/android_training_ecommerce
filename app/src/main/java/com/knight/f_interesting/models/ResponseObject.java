package com.knight.f_interesting.models;

import com.google.gson.annotations.SerializedName;

public class ResponseObject<T> {
    @SerializedName("status")
    private int status;
    @SerializedName("msg")
    private String msg;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResponseObject(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
}
