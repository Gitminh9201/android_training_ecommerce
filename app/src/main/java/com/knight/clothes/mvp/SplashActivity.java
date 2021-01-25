package com.knight.clothes.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.knight.clothes.api.APIInterface;
import com.knight.clothes.api.AppClient;
import com.knight.clothes.buses.CartBus;
import com.knight.clothes.buses.ContextBus;
import com.knight.clothes.buses.UserBus;
import com.knight.clothes.models.Cart;
import com.knight.clothes.models.ResponseObject;
import com.knight.clothes.models.User;
import com.knight.clothes.utils.AppShared;
import com.knight.clothes.utils.AppUtils;
import com.knight.clothes.utils.Router;

import org.jetbrains.annotations.NotNull;

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

    private void init() {
        ContextBus.publish(getApplicationContext());
        AppUtils.setUpDB(getApplicationContext());
        getUser(this);
        getCart();
    }

    private void getCart() {
        List<Cart> carts = AppUtils.db.getCart();
        CartBus.publish(carts);
    }

    private void getUser(final Activity activity) {
        Log.e("AccessToken ", AppShared.getAccessToken(getApplicationContext()));
        APIInterface api = AppClient.client().create(APIInterface.class);
        Call<ResponseObject<User>> call = api.getUser(AppClient.headers());
        final Handler handler = new Handler();
        call.enqueue(new Callback<ResponseObject<User>>() {
            @Override
            public void onResponse(@NotNull Call<ResponseObject<User>> call, @NotNull Response<ResponseObject<User>> response) {
                if (response.body() != null && response.body().getStatus() == 1)
                    UserBus.publish(response.body().getData());
                else if (response.body() != null)
                    Log.e("msg: ", response.body().getMsg());

                handler.postDelayed(() -> Router.navigator(Router.MAIN, activity, new String[]{"true"}), 1000);
            }

            @Override
            public void onFailure(@NotNull Call<ResponseObject<User>> call, @NotNull Throwable t) {
//                Toast.makeText(getApplicationContext(), R.string.error_startup, Toast.LENGTH_LONG).show();
//                handler.postDelayed(() -> finish(), 1500);
                handler.postDelayed(() -> Router.navigator(Router.MAIN, activity, new String[]{"true"}), 1000);
            }
        });
    }
}
