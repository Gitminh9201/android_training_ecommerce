package com.knight.f_interesting.api;

import com.knight.f_interesting.models.BannerResponse;
import com.knight.f_interesting.models.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {

    final String middleUrl = "lv_training_ecommerce/public/";

    @GET("/api/categories")
    Call<List<Category>> categories();

    @GET(middleUrl + "api/banners")
    Call<BannerResponse> banners();
}
