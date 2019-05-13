package com.example.financialtimes;

import retrofit2.Retrofit;

public class RemoveFavouriteAPI {
    public static final String URL_BASE = "http://192.168.1.4/remove_favourite.php/";
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
