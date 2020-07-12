package com.knight.f_interesting.utils;

import android.app.Activity;
import android.content.Intent;

import com.knight.f_interesting.mvp.MainActivity;
import com.knight.f_interesting.mvp.address.AddressActivity;
import com.knight.f_interesting.mvp.auth.AuthActivity;
import com.knight.f_interesting.mvp.cart.CartActivity;
import com.knight.f_interesting.mvp.completed.CompletedActivity;
import com.knight.f_interesting.mvp.detail_product.DetailProductActivity;
import com.knight.f_interesting.mvp.person_history_order.OrderHistoryActivity;
import com.knight.f_interesting.mvp.person_information.InformationActivity;
import com.knight.f_interesting.mvp.result_products.ResultProductsActivity;
import com.knight.f_interesting.mvp.search.SearchActivity;
import com.knight.f_interesting.mvp.terms.TermsActivity;

public class Router {

    public final static String MAIN = "/main";
    public final static String LOGIN = "/login";
    public final static String INFORMATION = "/information";
    public final static String CART = "/toolbar_item_cart";
    public final static String PRODUCT_DETAIL = "/product_detail";
    public final static String ADDRESS = "/address";
    public final static String ORDER_HISTORY = "/order_history";
    public final static String TERMS = "/terms";
    public final static String COMPLETED = "/completed";
    public final static String SEARCH = "/toolbar_item_search";
    public final static String RESULT_PRODUCTS = "/result_products";

    public static void navigator(String route, Activity current, String[] arguments) {

        Class second;

        switch (route) {
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
            case TERMS:
                second = TermsActivity.class;
                break;
            case CART:
                second = CartActivity.class;
                break;
            case COMPLETED:
                second = CompletedActivity.class;
                break;
            case SEARCH:
                second = SearchActivity.class;
                break;
            case RESULT_PRODUCTS:
                Intent resultProductIntent = new Intent(current, ResultProductsActivity.class);
                resultProductIntent.putExtra("keyword", arguments[0]);
                current.startActivity(resultProductIntent);
                try {
                    if (Boolean.parseBoolean(arguments[1]))
                        current.finish();
                } catch (Exception e) {}
                return;
            case PRODUCT_DETAIL:
                Intent productDetailIntent = new Intent(current, DetailProductActivity.class);
                productDetailIntent.putExtra("id", Integer.parseInt(arguments[0]));
                current.startActivity(productDetailIntent);
                try {
                    if (Boolean.parseBoolean(arguments[1]))
                        current.finish();
                } catch (Exception e) {}
                return;
            case MAIN:
                Boolean splash = false;
                try {
                    if (Boolean.parseBoolean(arguments[0]))
                        splash = true;
                } catch (Exception e) {}
                Intent main = new Intent(current, MainActivity.class);
                if(!splash) main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                current.startActivity(main);
                if(splash) current.finish();
                return;
            default:
                second = MainActivity.class;
                break;
        }

        Intent intent = new Intent(current, second);
        current.startActivity(intent);
    }
}
