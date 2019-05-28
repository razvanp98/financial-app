package com.example.financialtimes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RemoveProfile {
    @GET("financial-times-API/remove_profile.php")
        // Key is catched in the PHP script
    Call<Void> removeProfileApi(@Query("id") String name);
}
