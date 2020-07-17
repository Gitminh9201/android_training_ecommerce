package com.knight.f_interesting.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Invoice {
    @SerializedName("id")
    private int id;

    @SerializedName("phone")
    private String phone;

    @SerializedName("email")
    private String email;

    @SerializedName("ship_id")
    private int shipID;

    @SerializedName("payment_id")
    private int paymentID;

    @SerializedName("address")
    private String address;

    @SerializedName("note")
    private String note;

    @SerializedName("total")
    private int total;

    @SerializedName("discount")
    private int discount;

    @SerializedName("status")
    private int status;

    @SerializedName("user_id")
    private int userID;

    @SerializedName("detail")
    private List<OrderDetail> detail;

    @SerializedName("payment_method")
    private MethodPayment payment;

    public Invoice(int id, String phone, String email, int shipID, int paymentID, String address,
                   String note, int total, int discount, int status, int userID, List<OrderDetail> detail,
                   MethodPayment payment, String created) {
        this.id = id;
        this.phone = phone;
        this.email = email;
        this.shipID = shipID;
        this.paymentID = paymentID;
        this.address = address;
        this.note = note;
        this.total = total;
        this.discount = discount;
        this.status = status;
        this.userID = userID;
        this.detail = detail;
        this.payment = payment;
        this.created = created;
    }

    public MethodPayment getPayment() {
        return payment;
    }

    public void setPayment(MethodPayment payment) {
        this.payment = payment;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @SerializedName("created_at")
    private String created;

    public Invoice() {
    }

    public Invoice(int id, String phone, String email, int shipID, int paymentID, String address,
                   String note, int total, int discount, int status, int userID,
                   List<OrderDetail> detail, String created) {
        this.id = id;
        this.phone = phone;
        this.email = email;
        this.shipID = shipID;
        this.paymentID = paymentID;
        this.address = address;
        this.note = note;
        this.total = total;
        this.discount = discount;
        this.status = status;
        this.userID = userID;
        this.detail = detail;
        this.created = created;
    }

    public List<OrderDetail> getDetail() {
        return detail;
    }

    public void setDetail(List<OrderDetail> detail) {
        this.detail = detail;
    }

    public Invoice(String phone, int shipID, int paymentID,
                   String address, String note) {
        this.phone = phone;
        this.shipID = shipID;
        this.paymentID = paymentID;
        this.address = address;
        this.note = note;
    }

    public Invoice(int id, String phone, String email, int shipID, int paymentID, String address,
                   String note, int total, int discount, int status, int userID) {
        this.id = id;
        this.phone = phone;
        this.email = email;
        this.shipID = shipID;
        this.paymentID = paymentID;
        this.address = address;
        this.note = note;
        this.total = total;
        this.discount = discount;
        this.status = status;
        this.userID = userID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getShipID() {
        return shipID;
    }

    public void setShipID(int shipID) {
        this.shipID = shipID;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
