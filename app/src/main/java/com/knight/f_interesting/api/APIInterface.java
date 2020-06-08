package com.knight.f_interesting.api;

import com.knight.f_interesting.models.BannerResponse;
import com.knight.f_interesting.models.BrandResponse;
import com.knight.f_interesting.models.CategoryResponse;
import com.knight.f_interesting.models.GroupResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {

    final String middleUrl = "lv_training_ecommerce/public/";

    @GET(middleUrl + "api/categories")
    Call<CategoryResponse> categories();

    @GET(middleUrl + "api/banners")
    Call<BannerResponse> banners();

    @GET(middleUrl + "api/brands")
    Call<BrandResponse> brands();

    @GET(middleUrl + "api/groups")
    Call<GroupResponse> groups();
}
