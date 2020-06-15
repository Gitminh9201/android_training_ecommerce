package com.knight.f_interesting.utils;
import android.content.Context;
import android.content.SharedPreferences;

public class AppShared {

    final static String NAME_SHARED = "AppShared - Fashion Interesting";
    final static String KEY_ACCESS_TOKEN = "AppShared - AccessToken";

    public static void setAccessToken(Context context, String accessToken) {
        SharedPreferences.Editor editor = context.getSharedPreferences(NAME_SHARED, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_ACCESS_TOKEN, accessToken);
    }

    public static String getAccessToken(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(NAME_SHARED, Context.MODE_PRIVATE);
        return preferences.getString(KEY_ACCESS_TOKEN, "");
    }
}
