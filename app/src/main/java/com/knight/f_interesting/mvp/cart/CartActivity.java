package com.knight.f_interesting.mvp.cart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.knight.f_interesting.R;
import com.knight.f_interesting.models.Product;

import java.util.List;

public class CartActivity extends AppCompatActivity implements CartContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setDataToView(List<Product> products) {

    }

    @Override
    public void onResponseFailure(Throwable throwable) {

    }
}