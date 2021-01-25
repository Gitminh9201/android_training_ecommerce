package com.knight.clothes.utils;

import android.app.Activity;
import android.content.Intent;

import com.knight.clothes.mvp.MainActivity;
import com.knight.clothes.mvp.address.AddressActivity;
import com.knight.clothes.mvp.auth.AuthActivity;
import com.knight.clothes.mvp.cart.CartActivity;
import com.knight.clothes.mvp.completed.CompletedActivity;
import com.knight.clothes.mvp.coupons.CouponsActivity;
import com.knight.clothes.mvp.detail_invoice.DetailInvoiceActivity;
import com.knight.clothes.mvp.detail_product.DetailProductActivity;
import com.knight.clothes.mvp.invoices.InvoicesActivity;
import com.knight.clothes.mvp.person_information.InformationActivity;
import com.knight.clothes.mvp.result_products.ResultProductsActivity;
import com.knight.clothes.mvp.search.SearchActivity;
import com.knight.clothes.mvp.terms.TermsActivity;

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
    public final static String COUPONS = "/coupons";
    public final static String INVOICE = "/invoice";

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
                second = InvoicesActivity.class;
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
            case COUPONS:
                second = CouponsActivity.class;
                break;
            case INVOICE:
                Intent invoiceDetailIntent = new Intent(current, DetailInvoiceActivity.class);
                invoiceDetailIntent.putExtra("id", Integer.parseInt(arguments[0]));
                current.startActivity(invoiceDetailIntent);
                return;
            case RESULT_PRODUCTS:
                Intent resultProductIntent = new Intent(current, ResultProductsActivity.class);
                resultProductIntent.putExtra("keyword", arguments[0]);
                resultProductIntent.putExtra("brandID", arguments[1]);
                resultProductIntent.putExtra("categoryID", arguments[2]);
                current.startActivity(resultProductIntent);
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
