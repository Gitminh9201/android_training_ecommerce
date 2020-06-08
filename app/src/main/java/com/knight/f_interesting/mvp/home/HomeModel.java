package com.knight.f_interesting.mvp.home;

import android.util.Log;

import com.knight.f_interesting.api.APIInterface;
import com.knight.f_interesting.api.Client;
import com.knight.f_interesting.models.Banner;
import com.knight.f_interesting.models.BannerResponse;
import com.knight.f_interesting.models.Brand;
import com.knight.f_interesting.models.BrandResponse;
import com.knight.f_interesting.models.Category;
import com.knight.f_interesting.models.CategoryResponse;
import com.knight.f_interesting.models.Group;
import com.knight.f_interesting.models.GroupResponse;

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
        APIInterface api = Client.client().create(APIInterface.class);
        final  Call<BannerResponse> callBanner = api.banners();
        final  Call<BrandResponse> callBrand = api.brands();
        final  Call<GroupResponse> callGroup = api.groups();
        callBanner.enqueue(new Callback<BannerResponse>() {
            @Override
            public void onResponse(Call<BannerResponse> call, Response<BannerResponse> response) {
                Log.e("Res", String.valueOf(response.body().getBanners().size()));
                if(response.body().getStatus() == 1)
                    banners = response.body().getBanners();
                else banners = new ArrayList<>();
                callBrand.enqueue(new Callback<BrandResponse>() {
                    @Override
                    public void onResponse(Call<BrandResponse> call, Response<BrandResponse> response) {
                        Log.e("Res", String.valueOf(response.body().getBrands().size()));
                        if(response.body().getStatus() == 1)
                            brands = response.body().getBrands();
                        else brands = new ArrayList<>();
                        callGroup.enqueue(new Callback<GroupResponse>() {
                            @Override
                            public void onResponse(Call<GroupResponse> call, Response<GroupResponse> response) {
                                if (response.body().getStatus() == 1)
                                    groups = response.body().getGroups();
                                else groups = new ArrayList<>();
                                onFinishedListener.onFinished(banners, brands, groups);
                            }

                            @Override
                            public void onFailure(Call<GroupResponse> call, Throwable t) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<BrandResponse> call, Throwable t) {
                        Log.e("Err", t.getMessage());
                        onFinishedListener.onFailure(t);
                    }
                });
            }

            @Override
            public void onFailure(Call<BannerResponse> call, Throwable t) {
                Log.e("Err", t.getMessage());
                onFinishedListener.onFailure(t);
            }
        });
    }
}
