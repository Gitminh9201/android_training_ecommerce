package com.knight.f_interesting.mvp.detail_product;

import android.content.Context;

import com.knight.f_interesting.api.APIInterface;
import com.knight.f_interesting.api.AppClient;
import com.knight.f_interesting.models.Product;
import com.knight.f_interesting.models.ResponseObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProductModel implements DetailProductContract.Model {

    Product product;
    APIInterface api = AppClient.client().create(APIInterface.class);
    Context context;

    DetailProductModel(Context context) {
        this.context = context;
    }

    @Override
    public void getData(final OnFinishedListener onFinishedListener, int id) {
        Call<ResponseObject<Product>> call = api.detail(id);
        call.enqueue(new Callback<ResponseObject<Product>>() {
            @Override
            public void onResponse(Call<ResponseObject<Product>> call, retrofit2.Response<ResponseObject<Product>> response) {
                if (response.body() != null && response.body().getStatus() == 1)
                    product = response.body().getData();
                else product = new Product();
                onFinishedListener.onFinished(product);
            }

            @Override
            public void onFailure(Call<ResponseObject<Product>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }

    @Override
    public void checkBookmark(final OnFinishedListener onFinishedListener, int id) {
        Call<ResponseObject> call = api.checkCollection(AppClient.headers(), id);
        call.enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                if(response.body().getStatus() == 1){
                    onFinishedListener.onStatusBookmark(Boolean.valueOf(response.body().getData().toString()));
                }
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }

    @Override
    public void makeBookmark(final OnFinishedListener onFinishedListener, int id) {
        Call<ResponseObject> call = api.makeCollection(AppClient.headers(), id);
        call.enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                if(response.body().getStatus() == 1){
                    onFinishedListener.onStatusBookmark(Boolean.valueOf(response.body().getData().toString()));
                }
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
