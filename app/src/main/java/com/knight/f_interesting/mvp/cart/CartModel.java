package com.knight.f_interesting.mvp.cart;

import android.content.Context;
import android.util.Log;

import com.knight.f_interesting.api.APIInterface;
import com.knight.f_interesting.api.AppClient;
import com.knight.f_interesting.models.Cart;
import com.knight.f_interesting.models.Invoice;
import com.knight.f_interesting.models.Product;
import com.knight.f_interesting.models.ResponseList;
import com.knight.f_interesting.models.ResponseObject;
import com.knight.f_interesting.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartModel implements CartContract.Model {

    Context context;

    CartModel(Context context) {
        this.context = context;
    }

    @Override
    public void getData(final OnFinishedListener onFinishedListener, final List<Cart> carts) {
        String ids = "";
        for (Cart cart : carts) {
            ids += String.valueOf(cart.getProductId()) + ",";
        }
        Log.e("ids", ids);
        APIInterface api = AppClient.client().create(APIInterface.class);
        Call<ResponseList<Product>> call = api.productCart(ids);
        call.enqueue(new Callback<ResponseList<Product>>() {
            @Override
            public void onResponse(Call<ResponseList<Product>> call, Response<ResponseList<Product>> response) {
                if (response.body().getStatus() == 1)
                    onFinishedListener.onFinishedGetData(response.body().getData(), carts);
                else onFinishedListener.onFinishedGetData(new ArrayList<Product>(), carts);

            }

            @Override
            public void onFailure(Call<ResponseList<Product>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }

    @Override
    public void createOrder(final OnFinishedListener onFinishedListener, Invoice invoice, String code) {
        String productIds = "";
        String quantities = "";
        List<Cart> carts = AppUtils.db.getCart();
        for (Cart cart : carts) {
            productIds += String.valueOf(cart.getProductId()) + ",";
            quantities += String.valueOf(cart.getQuantity()) + ",";
        }
        productIds = productIds.substring(0, productIds.length() - 1);
        quantities = quantities.substring(0, quantities.length() - 1);
        APIInterface api = AppClient.client().create(APIInterface.class);
        Call<ResponseObject<Invoice>> call = api.createOrder(AppClient.headers(), productIds, quantities, invoice.getAddress(), invoice.getNote(), invoice.getPhone(), code);
        call.enqueue(new Callback<ResponseObject<Invoice>>() {
            @Override
            public void onResponse(Call<ResponseObject<Invoice>> call, Response<ResponseObject<Invoice>> response) {
                if (response.body() != null && response.body().getStatus() == 1){
                    onFinishedListener.onFinishedCreateOrder(response.body().getData());}
                else{
                    onFinishedListener.onFinishedCreateOrder(new Invoice());
                }
            }

            @Override
            public void onFailure(Call<ResponseObject<Invoice>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
