package com.knight.f_interesting.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.knight.f_interesting.models.Cart;
import com.knight.f_interesting.utils.AppUtils;

import java.util.List;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppUtils.setUpDB(getApplicationContext());

        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }
}
