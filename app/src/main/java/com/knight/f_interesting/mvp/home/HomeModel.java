package com.knight.f_interesting.mvp.home;

import com.knight.f_interesting.api.APIInterface;
import com.knight.f_interesting.api.AppClient;
import com.knight.f_interesting.models.Banner;
import com.knight.f_interesting.models.Brand;
import com.knight.f_interesting.models.Group;
import com.knight.f_interesting.models.ResponseList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeModel implements HomeContract.Model {

    List<Banner> banners;
    List<Brand> brands;
    List<Group> groups;

    @Override
    public void getData(final OnFinishedListener onFinishedListener) {
        APIInterface api = AppClient.client().create(APIInterface.class);
        final  Call<ResponseList<Banner>> callBanner = api.banners();
        final  Call<ResponseList<Brand>> callBrand = api.brands();
        final  Call<ResponseList<Group>> callGroup = api.groups();
        callBanner.enqueue(new Callback<ResponseList<Banner>>() {
            @Override
            public void onResponse(Call<ResponseList<Banner>> call, Response<ResponseList<Banner>> response) {
                if(response.body().getStatus() == 1)
                    banners = response.body().getData();
                else banners = new ArrayList<>();
                callBrand.enqueue(new Callback<ResponseList<Brand>>() {
                    @Override
                    public void onResponse(Call<ResponseList<Brand>> call, Response<ResponseList<Brand>> response) {
                        if(response.body().getStatus() == 1)
                            brands = response.body().getData();
                        else brands = new ArrayList<>();
                        callGroup.enqueue(new Callback<ResponseList<Group>>() {
                            @Override
                            public void onResponse(Call<ResponseList<Group>> call, Response<ResponseList<Group>> response) {
                                if (response.body().getStatus() == 1)
                                    groups = response.body().getData();
                                else groups = new ArrayList<>();
                                onFinishedListener.onFinished(banners, brands, groups);
                            }

                            @Override
                            public void onFailure(Call<ResponseList<Group>> call, Throwable t) {
                                onFinishedListener.onFailure(t);
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<ResponseList<Brand>> call, Throwable t) {
                        onFinishedListener.onFailure(t);
                    }
                });
            }

            @Override
            public void onFailure(Call<ResponseList<Banner>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
