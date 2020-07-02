package com.knight.f_interesting.api;

import android.content.Context;
import android.util.Log;

import com.knight.f_interesting.utils.AppShared;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

    private static Retrofit retrofit = null;

    public static final String BASE_URL = "http://192.168.43.228/";
//    public static final String BASE_URL = "http://192.168.1.51/";
    public static final String MIDDLE_URL = "lv_training_ecommerce/public/";

    public static String url(){
        return BASE_URL + MIDDLE_URL;
    }

    /**
     * This method returns retrofit client instance
     *
     * @return Retrofit object
     */
    public static Retrofit client(){
        if(retrofit == null){
//            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
//                .connectTimeout(60, TimeUnit.SECONDS)
//                .readTimeout(60, TimeUnit.SECONDS)
//                .writeTimeout(60, TimeUnit.SECONDS)
//                .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
//                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Map<String, String> header(Context context){
        String accessToken = AppShared.getAccessToken(context);
        Log.e("AccessToken", accessToken);
        Map<String, String> header = new HashMap<>();
        if(accessToken.length() > 1) {
            header.put("Authorization", "Bearer " + accessToken);
        }
        return header;
    }
}
