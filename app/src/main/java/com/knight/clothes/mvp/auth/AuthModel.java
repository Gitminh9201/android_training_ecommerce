package com.knight.clothes.mvp.auth;

import com.knight.clothes.api.APIInterface;
import com.knight.clothes.api.AppClient;
import com.knight.clothes.models.ResponseObject;
import com.knight.clothes.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthModel implements AuthContract.Model {

    private User user;

    @Override
    public void loginWithNumberPhone(final OnFinishedListener onFinishedListener, String username, String password) {

    }

    @Override
    public void loginWithGoogle(final OnFinishedListener onFinishedListener, String id, String email,
                                String name, String picture, String accessToken) {
        APIInterface api = AppClient.client().create(APIInterface.class);
        Call<ResponseObject<User>> call = api.loginGoogle(id, email, name, picture, accessToken);
        call.enqueue(new Callback<ResponseObject<User>>() {
            @Override
            public void onResponse(Call<ResponseObject<User>> call, Response<ResponseObject<User>> response) {
                if (response.body() != null && response.body().getStatus() == 1){
                    user = response.body().getData();
                }
                onFinishedListener.onFinished(user);
            }

            @Override
            public void onFailure(Call<ResponseObject<User>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }

    @Override
    public void loginWithFaceBook(final OnFinishedListener onFinishedListener, String id, String accessToken) {

    }
}
