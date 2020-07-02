package com.knight.f_interesting.models;

import com.google.gson.annotations.SerializedName;

public class OrderDetail {

    @SerializedName("id")
    private int id;

    @SerializedName("order_id")
    private int orderID;

    @SerializedName("product")
    private Product product;

    @SerializedName("quantity")
    private int quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrderDetail(int id, int orderID, Product product, int quantity) {
        this.id = id;
        this.orderID = orderID;
        this.product = product;
        this.quantity = quantity;
    }
}
