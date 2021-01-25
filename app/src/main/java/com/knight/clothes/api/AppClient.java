package com.knight.clothes.api;

import com.knight.clothes.buses.ContextBus;
import com.knight.clothes.utils.AppShared;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppClient {

    private static Retrofit retrofit = null;

    public static final String BASE_URL = "http://apiecommerce.huesoft.net/";
    public static final String MIDDLE_URL = "";

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
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Map<String, String> headers(){
        String accessToken = AppShared.getAccessToken(ContextBus.current());
        Map<String, String> header = new HashMap<>();
        if(accessToken.length() > 1) {
            header.put("Authorization", "Bearer " + accessToken);
        }
        header.put("Accept", "Application/json");
        return header;
    }
}
