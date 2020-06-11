package com.knight.f_interesting.mvp.cart;

import android.util.Log;

import com.knight.f_interesting.api.APIInterface;
import com.knight.f_interesting.api.Client;
import com.knight.f_interesting.models.Cart;
import com.knight.f_interesting.models.Product;
import com.knight.f_interesting.models.ResponseList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartModel implements CartContract.Model {

    List<Product> products;

    @Override
    public void getData(final OnFinishedListener onFinishedListener, final List<Cart> carts) {
        String ids = "";
        for (Cart cart : carts) {
            ids += String.valueOf(cart.getProductId()) + ", ";
        }
        Log.e("ids", ids);
        APIInterface api = Client.client().create(APIInterface.class);
        Call<ResponseList<Product>> call = api.productCart(ids);
        call.enqueue(new Callback<ResponseList<Product>>() {
            @Override
            public void onResponse(Call<ResponseList<Product>> call, Response<ResponseList<Product>> response) {
                if (response.body().getStatus() == 1)
                    products = response.body().getData();
                else
                    products = new ArrayList<>();
                onFinishedListener.onFinished(products, carts);
            }

            @Override
            public void onFailure(Call<ResponseList<Product>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
