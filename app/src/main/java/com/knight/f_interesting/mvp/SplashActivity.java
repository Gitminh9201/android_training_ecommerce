package com.knight.f_interesting.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.knight.f_interesting.api.APIInterface;
import com.knight.f_interesting.api.Client;
import com.knight.f_interesting.buses.UserBus;
import com.knight.f_interesting.models.ResponseObject;
import com.knight.f_interesting.models.User;
import com.knight.f_interesting.utils.AppShared;
import com.knight.f_interesting.utils.AppUtils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppUtils.setUpDB(getApplicationContext());
        getUser();

        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    private void getUser(){
        String accessToken = AppShared.getAccessToken(getApplicationContext());
        Log.e("AccessToken", accessToken);
        if(accessToken.length() > 1){
            APIInterface api = Client.client().create(APIInterface.class);
            Map<String, String> header = new HashMap<>();
            header.put("Authorization", "Bearer " + accessToken);
            Call<ResponseObject<User>> call = api.getUser(header);
            call.enqueue(new Callback<ResponseObject<User>>() {
                @Override
                public void onResponse(Call<ResponseObject<User>> call, Response<ResponseObject<User>> response) {
                    Log.e("AUTH:", response.body().getData().getName());
                    if (response.body() != null && response.body().getStatus() == 1)
                        UserBus.publish(response.body().getData());
                }

                @Override
                public void onFailure(Call<ResponseObject<User>> call, Throwable t) {
                    Log.e("AUTH", "Null");
                }
            });
        }
        else Log.e("AUTH", "Null");
    }
}
