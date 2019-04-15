package com.example.financialtimes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AddFavouriteInterface{
    @GET("addFavourite.php")
    Call<List<Companies>> favourite_API(@Query("key") String keyword);
}
