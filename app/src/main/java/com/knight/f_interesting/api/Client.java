package com.knight.f_interesting.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

    private static Retrofit retrofit = null;

    public static final String BASE_URL = "http://192.168.43.228/";
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
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
