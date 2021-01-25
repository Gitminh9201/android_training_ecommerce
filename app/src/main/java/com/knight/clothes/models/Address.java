package com.knight.clothes.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Address implements Serializable {
    String phone;
    String province;
    String district;
    String ward;
    String address;

    public static Address fromJSON(JSONObject json) {
        try {
            return new Address(json.getString("phone"), json.getString("province"),
                    json.getString("district"), json.getString("ward"),
                    json.getString("address"));
        } catch (JSONException err) {
            return null;
        }
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Address(String phone, String province, String district, String ward, String address) {
        this.phone = phone;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.address = address;
    }
}
