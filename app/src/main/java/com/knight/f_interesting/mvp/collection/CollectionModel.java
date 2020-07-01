package com.knight.f_interesting.mvp.collection;

import android.content.Context;

import com.knight.f_interesting.api.APIInterface;
import com.knight.f_interesting.api.Client;
import com.knight.f_interesting.models.Product;
import com.knight.f_interesting.models.ResponseList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionModel implements CollectionContract.Model {
    @Override
    public void getData(final OnFinishedListener onFinishedListener, Context context) {
        APIInterface api = Client.client().create(APIInterface.class);
        Call<ResponseList<Product>> call = api.collection(Client.header(context));

        call.enqueue(new Callback<ResponseList<Product>>() {
            @Override
            public void onResponse(Call<ResponseList<Product>> call, Response<ResponseList<Product>> response) {
                if (response.body().getStatus() == 1)
                    onFinishedListener.onFinished(response.body().getData());
                else
                    onFinishedListener.onFinished(new ArrayList<Product>());
            }

            @Override
            public void onFailure(Call<ResponseList<Product>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
