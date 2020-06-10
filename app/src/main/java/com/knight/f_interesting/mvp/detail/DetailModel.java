package com.knight.f_interesting.mvp.detail;

import com.knight.f_interesting.api.APIInterface;
import com.knight.f_interesting.api.Client;
import com.knight.f_interesting.models.Product;
import com.knight.f_interesting.models.Response;

import retrofit2.Call;
import retrofit2.Callback;

public class DetailModel implements DetailContract.Model {

    Product product;

    @Override
    public void getData(final OnFinishedListener onFinishedListener, int id) {
        APIInterface api = Client.client().create(APIInterface.class);
        Call<Response<Product>> call = api.detail(id);
        call.enqueue(new Callback<Response<Product>>() {
            @Override
            public void onResponse(Call<Response<Product>> call, retrofit2.Response<Response<Product>> response) {
                if (response.body().getStatus() == 1)
                    product = response.body().getData();
                else product = new Product();
                onFinishedListener.onFinished(product);
            }

            @Override
            public void onFailure(Call<Response<Product>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
