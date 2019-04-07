package com.example.financialtimes;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String URL_BASE = "http://192.168.0.100/stocks_API.php/";
    public static Retrofit retrofit;

    public static Retrofit getAPI(){
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
