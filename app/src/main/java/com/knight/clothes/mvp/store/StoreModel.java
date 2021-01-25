package com.knight.clothes.mvp.store;

import com.knight.clothes.api.APIInterface;
import com.knight.clothes.api.AppClient;
import com.knight.clothes.buses.ContentBus;
import com.knight.clothes.models.Category;
import com.knight.clothes.models.ResponseList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreModel implements StoreContract.Model {
    List<Category> categories;
    @Override
    public void getData(final OnFinishedListener onFinishedListener) {

        if(ContentBus.categories() != null && !ContentBus.categories().isEmpty()){
            onFinishedListener.onFinished(ContentBus.categories());
        }
        else {
            APIInterface api = AppClient.client().create(APIInterface.class);
            final Call<ResponseList<Category>> callCategory = api.categories();
            callCategory.enqueue(new Callback<ResponseList<Category>>() {
                @Override
                public void onResponse(Call<ResponseList<Category>> call, Response<ResponseList<Category>> response) {
                    if (response.body() != null && response.body().getStatus() == 1)
                        categories = response.body().getData();
                    else
                        categories = new ArrayList<>();
                    ContentBus.publishCategory(categories);
                    onFinishedListener.onFinished(categories);
                }

                @Override
                public void onFailure(Call<ResponseList<Category>> call, Throwable t) {
                    onFinishedListener.onFailure(t);
                }
            });
        }
    }
}
