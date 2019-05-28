package com.example.financialtimes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProfileFetcher {
    @GET("financial-times-API/get_profiles.php")
    Call<List<Profile>> profileAPI(@Query("symbol") String symbol);
}
