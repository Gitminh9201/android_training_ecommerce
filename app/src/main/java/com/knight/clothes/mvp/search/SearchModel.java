package com.knight.clothes.mvp.search;

import com.knight.clothes.api.APIInterface;
import com.knight.clothes.api.AppClient;
import com.knight.clothes.models.ProductSuggest;
import com.knight.clothes.models.ResponseList;
import com.knight.clothes.utils.AppShared;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchModel implements SearchContract.Model {

    @Override
    public void getSuggest(final OnFinishedListener onFinishedListener, String key) {
        APIInterface api = AppClient.client().create(APIInterface.class);
        Call<ResponseList<ProductSuggest>> call = api.productSuggest(key);
        call.enqueue(new Callback<ResponseList<ProductSuggest>>() {
            @Override
            public void onResponse(Call<ResponseList<ProductSuggest>> call, Response<ResponseList<ProductSuggest>> response) {
                if(response.body() != null && response.body().getStatus() == 1 && response.body().getData() != null){
                    onFinishedListener.onFinished(response.body().getData());
                }
                else onFinishedListener.onFinished(new ArrayList<ProductSuggest>());
            }

            @Override
            public void onFailure(Call<ResponseList<ProductSuggest>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }

    @Override
    public void getLocal(OnFinishedListener onFinishedListener) {
        List<String> data = AppShared.getSuggest();
        List<ProductSuggest> suggests = new ArrayList<>();
        for (String value:
             data) {
            suggests.add(new ProductSuggest(value));
        }
        onFinishedListener.onFinished(suggests);
    }
}
