package com.example.financialtimes;

import retrofit2.Retrofit;

public class RemoveProfileClient {
    public static final String URL_BASE = "http://192.168.1.100/financial-times-API/remove_profile.php/";
    public static Retrofit retrofit;

    // Retrofit 2 API builder
    public static Retrofit getAPI(){

        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL_BASE)
                    .build();
        }

        return retrofit;
    }
}
