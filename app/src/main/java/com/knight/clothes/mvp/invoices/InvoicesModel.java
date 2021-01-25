package com.knight.clothes.mvp.invoices;

import com.knight.clothes.api.APIInterface;
import com.knight.clothes.api.AppClient;
import com.knight.clothes.models.Invoice;
import com.knight.clothes.models.ResponseList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvoicesModel implements InvoicesContract.Model {

    @Override
    public void getData(final OnFinishedListener onFinishedListener) {
        APIInterface api = AppClient.client().create(APIInterface.class);
        Call<ResponseList<Invoice>> call = api.getOrders(AppClient.headers());
        call.enqueue(new Callback<ResponseList<Invoice>>() {
            @Override
            public void onResponse(Call<ResponseList<Invoice>> call, Response<ResponseList<Invoice>> response) {
                if (response.body() != null && response.body().getStatus() == 1)
                    onFinishedListener.onGetDataFinished(response.body().getData());
                else onFinishedListener.onGetDataFinished(new ArrayList<Invoice>());

            }
            @Override
            public void onFailure(Call<ResponseList<Invoice>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }

    @Override
    public void getData(final OnFinishedListener onFinishedListener, int offset) {
        APIInterface api = AppClient.client().create(APIInterface.class);
        Call<ResponseList<Invoice>> call = api.getOrders(AppClient.headers(), offset);
        call.enqueue(new Callback<ResponseList<Invoice>>() {
            @Override
            public void onResponse(Call<ResponseList<Invoice>> call, Response<ResponseList<Invoice>> response) {
                if (response.body() != null && response.body().getStatus() == 1)
                    onFinishedListener.onLoadMoreFinished(response.body().getData());
                else onFinishedListener.onLoadMoreFinished(new ArrayList<Invoice>());

            }
            @Override
            public void onFailure(Call<ResponseList<Invoice>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }

    @Override
    public void getData(final OnFinishedListener onFinishedListener, int offset, int limit) {
        APIInterface api = AppClient.client().create(APIInterface.class);
        Call<ResponseList<Invoice>> call = api.getOrders(AppClient.headers(), offset, limit);
        call.enqueue(new Callback<ResponseList<Invoice>>() {
            @Override
            public void onResponse(Call<ResponseList<Invoice>> call, Response<ResponseList<Invoice>> response) {
                if (response.body() != null && response.body().getStatus() == 1)
                    onFinishedListener.onLoadMoreFinished(response.body().getData());
                else onFinishedListener.onLoadMoreFinished(new ArrayList<Invoice>());

            }
            @Override
            public void onFailure(Call<ResponseList<Invoice>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
