package com.knight.f_interesting.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.knight.f_interesting.R;
import com.knight.f_interesting.api.APIInterface;
import com.knight.f_interesting.api.AppClient;
import com.knight.f_interesting.buses.CartBus;
import com.knight.f_interesting.buses.ContextBus;
import com.knight.f_interesting.buses.UserBus;
import com.knight.f_interesting.models.Cart;
import com.knight.f_interesting.models.ResponseObject;
import com.knight.f_interesting.models.User;
import com.knight.f_interesting.utils.AppUtils;
import com.knight.f_interesting.utils.Router;

import java.util.List;

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
        getUser(this);
        getCart();
    }

    private void getCart(){
        List<Cart> carts = AppUtils.db.getCart();
        CartBus.publish(carts);
    }

    private void getUser(final Activity activity){
        APIInterface api = AppClient.client().create(APIInterface.class);
        Call<ResponseObject<User>> call = api.getUser(AppClient.headers());
        final Handler handler = new Handler();
        call.enqueue(new Callback<ResponseObject<User>>() {
            @Override
            public void onResponse(Call<ResponseObject<User>> call, Response<ResponseObject<User>> response) {
                if (response.body() != null && response.body().getStatus() == 1)
                    UserBus.publish(response.body().getData());
                else {
                    if(response.body() != null)
                        Log.e("msg: ", response.body().getMsg());
                }
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Router.navigator(Router.MAIN, activity, new String[]{"true"});
                    }
                }, 1500);
            }

            @Override
            public void onFailure(Call<ResponseObject<User>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.error_startup, Toast.LENGTH_LONG).show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1500);
            }
        });
    }
}
