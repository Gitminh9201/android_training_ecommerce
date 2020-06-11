package com.knight.f_interesting.api;

import com.knight.f_interesting.models.Banner;
import com.knight.f_interesting.models.Brand;
import com.knight.f_interesting.models.Category;
import com.knight.f_interesting.models.Group;
import com.knight.f_interesting.models.Product;
import com.knight.f_interesting.models.Response;
import com.knight.f_interesting.models.ResponseList;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface APIInterface {

    @GET(Client.MIDDLE_URL + "api/categories")
    Call<ResponseList<Category>> categories();

    @GET(Client.MIDDLE_URL + "api/banners")
    Call<ResponseList<Banner>> banners();

    @GET(Client.MIDDLE_URL + "api/brands")
    Call<ResponseList<Brand>> brands();

    @GET(Client.MIDDLE_URL + "api/groups")
    Call<ResponseList<Group>> groups();

    @GET(Client.MIDDLE_URL + "api/products")
    Call<ResponseList<Product>> products(@QueryMap Map<String, String> options);

    @GET(Client.MIDDLE_URL + "api/products/get")
    Call<Response<Product>> detail(@Query("id") int id);

    //ids: "1,2,6"
    @GET(Client.MIDDLE_URL + "api/products/gets")
    Call<ResponseList<Product>> productCart(@Query("ids") String ids);
}
