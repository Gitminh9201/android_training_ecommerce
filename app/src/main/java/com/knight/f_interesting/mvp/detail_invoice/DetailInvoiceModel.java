package com.knight.f_interesting.mvp.detail_invoice;

import com.knight.f_interesting.api.APIInterface;
import com.knight.f_interesting.api.AppClient;
import com.knight.f_interesting.models.Invoice;
import com.knight.f_interesting.models.ResponseObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailInvoiceModel implements DetailInvoiceContract.Model {
    APIInterface api = AppClient.client().create(APIInterface.class);
    @Override
    public void getData(final OnFinishedListener onFinishedListener, final int id) {
        Call<ResponseObject<Invoice>> call = api.getOrderDetail(AppClient.headers(), id);
        call.enqueue(new Callback<ResponseObject<Invoice>>() {
            @Override
            public void onResponse(Call<ResponseObject<Invoice>> call, Response<ResponseObject<Invoice>> response) {
                if (response.body() != null && response.body().getStatus() == 1){
                    onFinishedListener.onGetDataFinished(response.body().getData());
                }
                else onFinishedListener.onGetDataFinished(new Invoice());
            }

            @Override
            public void onFailure(Call<ResponseObject<Invoice>> call, Throwable t) {
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
