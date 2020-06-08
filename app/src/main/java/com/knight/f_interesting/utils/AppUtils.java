package com.knight.f_interesting.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class AppUtils {
    static public String currencyVN(int price){
        if(price < 0)return "0 Đ";
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(locale);
        return currencyVN.format(price).toUpperCase();
    }
}
