package com.knight.f_interesting.mvp.cart;

import com.knight.f_interesting.models.Product;

import java.util.Arrays;
import java.util.List;

public class CartModel implements CartContract.Model {

    List<Product> products;

    @Override
    public void getData(OnFinishedListener onFinishedListener, Arrays ids) {

    }
}
