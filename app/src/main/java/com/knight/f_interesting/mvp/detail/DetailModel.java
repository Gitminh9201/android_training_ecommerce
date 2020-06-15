package com.knight.f_interesting.mvp.detail;

import com.knight.f_interesting.api.APIInterface;
import com.knight.f_interesting.api.Client;
import com.knight.f_interesting.models.Product;
import com.knight.f_interesting.models.ResponseObject;

import retrofit2.Call;
import retrofit2.Callback;

public class DetailModel implements DetailContract.Model {

    Product product;

    @Override
    public void getData(final OnFinishedListener onFinishedListener, int id) {
        APIInterface api = Client.client().create(APIInterface.class);
        Call<ResponseObject<Product>> call = api.detail(id);
        call.enqueue(new Callback<ResponseObject<Product>>() {
            @Override
            public void onResponse(Call<ResponseObject<Product>> call, retrofit2.Response<ResponseObject<Product>> response) {
                if (response.body().getStatus() == 1)
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
}
