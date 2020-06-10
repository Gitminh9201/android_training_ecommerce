package com.knight.f_interesting.utils;
import android.content.Context;
import android.content.res.Resources;

public class AppSizes {
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static float convertDpToPx(Context context, int dp){
        return  dp * context.getResources().getDisplayMetrics().density;
    }
}
