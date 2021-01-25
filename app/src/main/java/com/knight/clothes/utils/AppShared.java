package com.knight.clothes.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.knight.clothes.buses.ContextBus;
import com.knight.clothes.models.Address;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AppShared {

    final static String NAME_SHARED = "AppShared - Fashion Interesting";
    final static String KEY_ACCESS_TOKEN = "AppShared - AccessToken";
    final static String KEY_ADDRESS_USER = "AppShared - AddressUser";
    final static String KEY_SUGGEST_SEARCH = "AppShared - SuggestSearch";

    public static void setSuggest(String keyword) {
        if(keyword != null && keyword.trim().length() != 0){
            List<String> data = getSuggest();
            data.add(keyword);
            Set<String> set = new HashSet<String>(data);
            SharedPreferences.Editor editor = ContextBus.current().getSharedPreferences(NAME_SHARED, Context.MODE_PRIVATE).edit();
            editor.putStringSet(KEY_SUGGEST_SEARCH, set).apply();
        }
    }

    public static List<String> getSuggest() {
        SharedPreferences preferences = ContextBus.current().getSharedPreferences(NAME_SHARED, Context.MODE_PRIVATE);
        Set<String> stringSet = preferences.getStringSet(KEY_SUGGEST_SEARCH, null);
        if (stringSet == null) return new ArrayList<>();
        else return new ArrayList<>(stringSet);
    }

    public static void setAddress(Context context, String phone, String province,
                                  String district, String ward, String address) {
        String data = "{\"phone\":\"" + phone + "\",\"province\":\"" + province + "\",\"district\":\""
                + district + "\",\"ward\":\"" + ward + "\",\"address\":\"" + address + "\"}";
        Log.e("Save", data);

        SharedPreferences.Editor editor = context.getSharedPreferences(NAME_SHARED, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_ADDRESS_USER, data).apply();
    }

    public static Address getAddress(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(NAME_SHARED, Context.MODE_PRIVATE);
        String jsonData = preferences.getString(KEY_ADDRESS_USER, "");
        try {
            JSONObject json = new JSONObject(jsonData);
            return Address.fromJSON(json);
        } catch (JSONException err) {
            return null;
        }
    }

    public static void setAccessToken(Context context, String accessToken) {
        SharedPreferences.Editor editor = context.getSharedPreferences(NAME_SHARED, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_ACCESS_TOKEN, accessToken).apply();
    }

    public static String getAccessToken(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(NAME_SHARED, Context.MODE_PRIVATE);
        return preferences.getString(KEY_ACCESS_TOKEN, "");
    }
}
