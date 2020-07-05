package com.knight.f_interesting.mvp.store;

import com.knight.f_interesting.api.APIInterface;
import com.knight.f_interesting.api.AppClient;
import com.knight.f_interesting.models.Brand;
import com.knight.f_interesting.models.Category;
import com.knight.f_interesting.models.ResponseList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreModel implements StoreContract.Model {

    List<Brand> brands;
    List<Category> categories;

    @Override
    public void getData(final OnFinishedListener onFinishedListener) {
        APIInterface api = AppClient.client().create(APIInterface.class);
        final Call<ResponseList<Category>> callCategory = api.categories();
        final Call<ResponseList<Brand>> callBrand = api.brands();
        callCategory.enqueue(new Callback<ResponseList<Category>>() {
            @Override
            public void onResponse(Call<ResponseList<Category>> call, Response<ResponseList<Category>> response) {
                if (response.body() != null && response.body().getStatus() == 1)
                    categories = response.body().getData();
                else
                    categories = new ArrayList<>();
                callBrand.enqueue(new Callback<ResponseList<Brand>>() {
                    @Override
                    public void onResponse(Call<ResponseList<Brand>> call, Response<ResponseList<Brand>> response) {
                        if(response.body() != null && response.body().getStatus() == 1)
                            brands = response.body().getData();
                        else
                            brands = new ArrayList<>();
                        onFinishedListener.onFinished(categories, brands);
                    }

                    @Override
                    public void onFailure(Call<ResponseList<Brand>> call, Throwable t) {
                        onFinishedListener.onFailure(t);
                    }
                });
            }

            @Override
            public void onFailure(Call<ResponseList<Category>> call, Throwable t) {
            }
        });
    }
}
