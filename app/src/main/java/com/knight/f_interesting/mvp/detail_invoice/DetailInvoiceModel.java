package com.knight.f_interesting.mvp.detail_invoice;

import com.knight.f_interesting.api.APIInterface;
import com.knight.f_interesting.api.AppClient;
import com.knight.f_interesting.models.Order;
import com.knight.f_interesting.models.ResponseObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailInvoiceModel implements DetailInvoiceContract.Model {
    APIInterface api = AppClient.client().create(APIInterface.class);
    @Override
    public void getData(final OnFinishedListener onFinishedListener, final int id) {
        Call<ResponseObject<Order>> call = api.getOrderDetail(AppClient.headers(), id);
        call.enqueue(new Callback<ResponseObject<Order>>() {
            @Override
            public void onResponse(Call<ResponseObject<Order>> call, Response<ResponseObject<Order>> response) {
                if (response.body() != null && response.body().getStatus() == 1){
                    onFinishedListener.onGetDataFinished(response.body().getData());
                }
                else onFinishedListener.onGetDataFinished(new Order());
            }

            @Override
            public void onFailure(Call<ResponseObject<Order>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }

    @Override
    public void cancelInvoice(final OnFinishedListener onFinishedListener, final int id) {
        Call<ResponseObject> call = api.cancelOrder(AppClient.headers(), id);
        call.enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                System.out.println(response);
                if (response.body() != null && response.body().getStatus() == 1){
                    onFinishedListener.onCancelSuccess();
                    getData(onFinishedListener, id);
                }
                else onFinishedListener.onFailure(new Throwable());
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
