package com.knight.f_interesting.utils;

import android.app.Activity;
import android.content.Intent;

import com.knight.f_interesting.mvp.MainActivity;
import com.knight.f_interesting.mvp.address.AddressActivity;
import com.knight.f_interesting.mvp.auth.AuthActivity;
import com.knight.f_interesting.mvp.cart.CartActivity;
import com.knight.f_interesting.mvp.completed.CompletedActivity;
import com.knight.f_interesting.mvp.person_history_order.OrderHistoryActivity;
import com.knight.f_interesting.mvp.person_information.InformationActivity;

import java.util.List;

public class Router {

    public final static String MAIN = "/main";
    public final static String LOGIN = "/login";
    public final static String INFORMATION = "/information";
    public final static String CART = "/cart";
    public final static String ADDRESS = "/address";
    public final static String ORDER_HISTORY = "/order_history";
    public final static String COMPLETED = "/completed";

    public static void navigator(String route, Activity current, List[] arguments){

        Class second;

        switch (route){
            case LOGIN:
                second = AuthActivity.class;
                break;
            case INFORMATION:
                second = InformationActivity.class;
                break;
            case ADDRESS:
                second = AddressActivity.class;
                break;
            case ORDER_HISTORY:
                second = OrderHistoryActivity.class;
                break;
            case CART:
                second = CartActivity.class;
                break;
            case COMPLETED:
                second = CompletedActivity.class;
                break;
            case MAIN:
                Intent intent = new Intent(current, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                current.startActivity(intent);
                return;
            default:
                second = MainActivity.class;
                break;
        }

        Intent intent = new Intent(current, second);
        current.startActivity(intent);
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
}
