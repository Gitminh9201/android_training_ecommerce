package com.knight.f_interesting.mvp.person_history_order;

import android.content.Context;

import com.knight.f_interesting.api.APIInterface;
import com.knight.f_interesting.api.AppClient;
import com.knight.f_interesting.models.Order;
import com.knight.f_interesting.models.ResponseList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHistoryModel implements OrderHistoryContract.Model {
    private Context context;
    public OrderHistoryModel(Context context){
        this.context = context;
    }

    @Override
    public void getData(final OnFinishedListener onFinishedListener) {
        APIInterface api = AppClient.client().create(APIInterface.class);
        Call<ResponseList<Order>> call = api.getOrders(AppClient.headers());
        call.enqueue(new Callback<ResponseList<Order>>() {
            @Override
            public void onResponse(Call<ResponseList<Order>> call, Response<ResponseList<Order>> response) {
                if (response.body() != null && response.body().getStatus() == 1)
                    onFinishedListener.onFinished(response.body().getData());
                else onFinishedListener.onFinished(new ArrayList<Order>());

            }
            @Override
            public void onFailure(Call<ResponseList<Order>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }

    @Override
    public void getData(final OnFinishedListener onFinishedListener, int offset) {
        APIInterface api = AppClient.client().create(APIInterface.class);
        Call<ResponseList<Order>> call = api.getOrders(AppClient.headers(), offset);
        call.enqueue(new Callback<ResponseList<Order>>() {
            @Override
            public void onResponse(Call<ResponseList<Order>> call, Response<ResponseList<Order>> response) {
                if (response.body() != null && response.body().getStatus() == 1)
                    onFinishedListener.onFinished(response.body().getData());
                else onFinishedListener.onFinished(new ArrayList<Order>());

            }
            @Override
            public void onFailure(Call<ResponseList<Order>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }

    @Override
    public void getData(final OnFinishedListener onFinishedListener, int offset, int limit) {
        APIInterface api = AppClient.client().create(APIInterface.class);
        Call<ResponseList<Order>> call = api.getOrders(AppClient.headers(), offset, limit);
        call.enqueue(new Callback<ResponseList<Order>>() {
            @Override
            public void onResponse(Call<ResponseList<Order>> call, Response<ResponseList<Order>> response) {
                if (response.body() != null && response.body().getStatus() == 1)
                    onFinishedListener.onFinished(response.body().getData());
                else onFinishedListener.onFinished(new ArrayList<Order>());

            }
            @Override
            public void onFailure(Call<ResponseList<Order>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
