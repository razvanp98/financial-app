package com.example.financialtimes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AddFavouriteInterface{
    @GET("financial-times-API/add_favourite.php")
        // Key is catched in the PHP script
    Call<Void> favourite_API(@Query("statusFavourite") String status, @Query("company_symbol") String symbol);
}