package com.knight.f_interesting.mvp.products;

import com.knight.f_interesting.api.APIInterface;
import com.knight.f_interesting.api.Client;
import com.knight.f_interesting.models.Product;
import com.knight.f_interesting.models.ResponseList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsModel implements ProductsContract.Model {
    List<Product> products;

    @Override
    public void getData(final OnFinishedListener onFinishedListener, String keyword, int categoryId, int brandId, int groupId, int offset, int limit, int sort) {
        APIInterface api = Client.client().create(APIInterface.class);
        Map<String, String> params = new HashMap<>();
        params.put("category_id", String.valueOf(categoryId));
        params.put("keyword", keyword);
        params.put("brand_id", String.valueOf(brandId));
        params.put("group_id", String.valueOf(groupId));
        params.put("offset", String.valueOf(offset));
        params.put("limit", String.valueOf(limit));
        params.put("sort", String.valueOf(sort));
        Call<ResponseList<Product>> call = api.products(params);

        call.enqueue(new Callback<ResponseList<Product>>() {
            @Override
            public void onResponse(Call<ResponseList<Product>> call, Response<ResponseList<Product>> response) {
                if (response.body() != null && response.body().getStatus() == 1)
                    products = response.body().getData();
                else
                    products = new ArrayList<>();
                onFinishedListener.onFinished(products);
            }

            @Override
            public void onFailure(Call<ResponseList<Product>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
