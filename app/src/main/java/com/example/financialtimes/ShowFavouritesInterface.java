package com.example.financialtimes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ShowFavouritesInterface {
    @GET("show_favourites.php")
    Call<List<Companies>> showFavourites(@Query("status") String status);
}
