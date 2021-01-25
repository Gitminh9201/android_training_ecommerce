package com.knight.clothes.mvp.coupons;

import com.knight.clothes.api.APIInterface;
import com.knight.clothes.api.AppClient;
import com.knight.clothes.buses.ContentBus;
import com.knight.clothes.models.Coupon;
import com.knight.clothes.models.ResponseList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CouponsModel implements CouponsContract.Model {
    List<Coupon> coupons;
    @Override
    public void getData(final OnFinishedListener onFinishedListener, boolean reload) {
        if(!reload && ContentBus.coupons() != null && !ContentBus.coupons().isEmpty()){
            onFinishedListener.onFinished(ContentBus.coupons());
        }
        else {
            APIInterface api = AppClient.client().create(APIInterface.class);
            final Call<ResponseList<Coupon>> callCoupon = api.coupons();
            callCoupon.enqueue(new Callback<ResponseList<Coupon>>() {
                @Override
                public void onResponse(Call<ResponseList<Coupon>> call, Response<ResponseList<Coupon>> response) {
                    if (response.body() != null && response.body().getStatus() == 1)
                        coupons = response.body().getData();
                    else coupons = new ArrayList<>();
                    ContentBus.publishCoupon(coupons);
                    onFinishedListener.onFinished(coupons);
                }

                @Override
                public void onFailure(Call<ResponseList<Coupon>> call, Throwable t) {
                    onFinishedListener.onFailure(t);
                }
            });
        }
    }
}
