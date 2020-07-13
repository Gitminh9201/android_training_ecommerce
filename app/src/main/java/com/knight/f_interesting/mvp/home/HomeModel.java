package com.knight.f_interesting.mvp.home;

import com.knight.f_interesting.api.APIInterface;
import com.knight.f_interesting.api.AppClient;
import com.knight.f_interesting.buses.ContentBus;
import com.knight.f_interesting.models.Banner;
import com.knight.f_interesting.models.Brand;
import com.knight.f_interesting.models.Coupon;
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
    List<Coupon> coupons;

    @Override
    public void getData(final OnFinishedListener onFinishedListener, final boolean reload) {
        if (!reload &&
                ContentBus.banners() != null && !ContentBus.banners().isEmpty()
                && ContentBus.coupons() != null && !ContentBus.coupons().isEmpty()
                && ContentBus.brands() != null && !ContentBus.brands().isEmpty()
                && ContentBus.groups() != null && !ContentBus.groups().isEmpty()) {
            onFinishedListener.onFinished(ContentBus.banners(), ContentBus.brands(), ContentBus.groups(), ContentBus.coupons());
        } else {
            APIInterface api = AppClient.client().create(APIInterface.class);
            final Call<ResponseList<Banner>> callBanner = api.banners();
            final Call<ResponseList<Brand>> callBrand = api.brands();
            final Call<ResponseList<Group>> callGroup = api.groups();
            final Call<ResponseList<Coupon>> callCoupon = api.coupons();
            callGroup.enqueue(new Callback<ResponseList<Group>>() {
                @Override
                public void onResponse(Call<ResponseList<Group>> call, Response<ResponseList<Group>> response) {
                    if (response.body() != null && response.body().getStatus() == 1)
                        groups = response.body().getData();
                    else groups = new ArrayList<>();
                    ContentBus.publishGroup(groups);
                    if (!reload
                            && ContentBus.banners() != null && !ContentBus.banners().isEmpty()
                            && ContentBus.brands() != null && !ContentBus.brands().isEmpty()
                            && ContentBus.coupons() != null && !ContentBus.coupons().isEmpty()) {
                        onFinishedListener.onFinished(ContentBus.banners(), ContentBus.brands(), groups, ContentBus.coupons());
                    } else callBanner.enqueue(new Callback<ResponseList<Banner>>() {
                        @Override
                        public void onResponse(Call<ResponseList<Banner>> call, Response<ResponseList<Banner>> response) {
                            if (response.body() != null && response.body().getStatus() == 1)
                                banners = response.body().getData();
                            else banners = new ArrayList<>();
                            ContentBus.publishBanner(banners);
                            if (!reload
                                    && ContentBus.brands() != null && !ContentBus.brands().isEmpty()
                                    && ContentBus.coupons() != null && !ContentBus.coupons().isEmpty()) {
                                onFinishedListener.onFinished(banners, ContentBus.brands(), groups, ContentBus.coupons());
                            } else
                                callBrand.enqueue(new Callback<ResponseList<Brand>>() {
                                    @Override
                                    public void onResponse(Call<ResponseList<Brand>> call, Response<ResponseList<Brand>> response) {
                                        if (response.body() != null && response.body().getStatus() == 1)
                                            brands = response.body().getData();
                                        else brands = new ArrayList<>();
                                        ContentBus.publishBrand(brands);
                                        if (!reload && ContentBus.coupons() != null && !ContentBus.coupons().isEmpty()) {
                                            onFinishedListener.onFinished(banners, brands, groups, ContentBus.coupons());
                                        } else
                                            callCoupon.enqueue(new Callback<ResponseList<Coupon>>() {
                                                @Override
                                                public void onResponse(Call<ResponseList<Coupon>> call, Response<ResponseList<Coupon>> response) {
                                                    if (response.body() != null && response.body().getStatus() == 1)
                                                        coupons = response.body().getData();
                                                    else coupons = new ArrayList<>();
                                                    ContentBus.publishCoupon(coupons);
                                                    onFinishedListener.onFinished(banners, brands, groups, coupons);
                                                }

                                                @Override
                                                public void onFailure(Call<ResponseList<Coupon>> call, Throwable t) {
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

                @Override
                public void onFailure(Call<ResponseList<Group>> call, Throwable t) {
                    onFinishedListener.onFailure(t);
                }
            });
        }
    }
}
