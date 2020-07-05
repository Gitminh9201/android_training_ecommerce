package com.knight.f_interesting.mvp;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.knight.f_interesting.api.APIInterface;
import com.knight.f_interesting.api.AppClient;
import com.knight.f_interesting.buses.ContextBus;
import com.knight.f_interesting.buses.UserBus;
import com.knight.f_interesting.models.ResponseObject;
import com.knight.f_interesting.models.User;
import com.knight.f_interesting.utils.AppUtils;
import com.knight.f_interesting.utils.Router;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        ContextBus.publish(getApplicationContext());
        AppUtils.setUpDB(getApplicationContext());
        getUser();
        Router.navigator(Router.MAIN, this, null);
    }

    private void getUser(){
        APIInterface api = AppClient.client().create(APIInterface.class);
        Call<ResponseObject<User>> call = api.getUser(AppClient.headers());
        call.enqueue(new Callback<ResponseObject<User>>() {
            @Override
            public void onResponse(Call<ResponseObject<User>> call, Response<ResponseObject<User>> response) {
                if (response.body() != null && response.body().getStatus() == 1)
                    UserBus.publish(response.body().getData());
                else {
                    if(response.body() != null)
                        Log.e("msg: ", response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResponseObject<User>> call, Throwable t) {
                Log.e("AUTH", t.getMessage());
            }
        });
    }
}
