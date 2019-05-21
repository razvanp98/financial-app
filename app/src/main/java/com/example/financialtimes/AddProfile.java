package com.example.financialtimes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AddProfile {
    @GET("financial-times-API/add_profile.php")
        // Key is catched in the PHP script
    Call<Void> addProfileApi(@Query("profile_name") String name, @Query("symbol") String symbol, @Query("profit_share") Double share, @Query("amount") Double amount);
}
