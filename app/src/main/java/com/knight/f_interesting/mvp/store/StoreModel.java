package com.knight.f_interesting.mvp.store;

import com.knight.f_interesting.api.APIInterface;
import com.knight.f_interesting.api.Client;
import com.knight.f_interesting.models.Category;
import com.knight.f_interesting.models.ResponseList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreModel implements StoreContract.Model {

    List<Category> categories;

    @Override
    public void getData(final OnFinishedListener onFinishedListener) {
        APIInterface api = Client.client().create(APIInterface.class);
        Call<ResponseList<Category>> call = api.categories();
        call.enqueue(new Callback<ResponseList<Category>>() {
            @Override
            public void onResponse(Call<ResponseList<Category>> call, Response<ResponseList<Category>> response) {
                if (response.body().getStatus() == 1)
                    categories = response.body().getData();
                else
                    categories = new ArrayList<>();
                onFinishedListener.onFinished(categories);
            }

            @Override
            public void onFailure(Call<ResponseList<Category>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
