package com.knight.f_interesting.utils;

import android.app.Activity;
import android.content.Intent;

import com.knight.f_interesting.mvp.auth.AuthActivity;
import com.knight.f_interesting.mvp.cart.CartActivity;

public class Router {
    public static void goToAuth(Activity fist){
        Intent intent = new Intent(fist, AuthActivity.class);
        fist.startActivity(intent);
    }
    public static void goToCart(Activity fist){
        Intent intent = new Intent(fist, CartActivity.class);
        fist.startActivity(intent);
    }
    public static void goToDetail(Activity fist, Class second, int id){
        Intent intent = new Intent(fist, second);
        intent.putExtra("id", id);
        fist.startActivity(intent);
    }
    public static void goToDetail(Activity fist, Class second, int id, boolean replace){
        Intent intent = new Intent(fist, second);
        intent.putExtra("id", id);
        fist.startActivity(intent);
        fist.finish();
    }
    public static int inDetail(Activity activity){
        Intent intent = activity.getIntent();
        return intent.getIntExtra("id", 1);
    }
}
