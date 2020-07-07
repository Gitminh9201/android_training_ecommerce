package com.knight.f_interesting.mvp.person;

import android.content.Context;

import com.knight.f_interesting.api.APIInterface;
import com.knight.f_interesting.api.AppClient;
import com.knight.f_interesting.buses.UserBus;
import com.knight.f_interesting.models.ResponseObject;
import com.knight.f_interesting.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonModel implements PersonContract.Model {
    @Override
    public void getUser(final OnFinishedListener onFinishedListener, Context context) {
        User user = UserBus.current();
        if (user != null && user.getName() != null) {
            onFinishedListener.onFinish(user);
        } else requestApi(onFinishedListener, context);
    }

    private void requestApi(final OnFinishedListener onFinishedListener, Context context) {
        APIInterface api = AppClient.client().create(APIInterface.class);
        Call<ResponseObject<User>> call = api.getUser(AppClient.headers());
        call.enqueue(new Callback<ResponseObject<User>>() {
            @Override
            public void onResponse(Call<ResponseObject<User>> call, Response<ResponseObject<User>> response) {
                if (response.body() != null && response.body().getStatus() == 1)
                    onFinishedListener.onFinish(response.body().getData());
                else
                    onFinishedListener.onFinish(new User());
            }

            @Override
            public void onFailure(Call<ResponseObject<User>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
