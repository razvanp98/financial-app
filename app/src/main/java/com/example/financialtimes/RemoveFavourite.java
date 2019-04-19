package com.example.financialtimes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RemoveFavourite{
    @GET("remove_favourite.php")
        // Key is catched in the PHP script
    Call<Void> remove_API(@Query("name") String name);
}