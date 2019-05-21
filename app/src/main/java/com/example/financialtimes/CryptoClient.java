package com.example.financialtimes;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CryptoClient {
    public static final String BASE_URL = "http://192.168.1.100/financial-times-API/crypto_api.php/";
    public static Retrofit retrofit;

    public static Retrofit getApi(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                                    .baseUrl(BASE_URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
        }
        return retrofit;
    }
}
