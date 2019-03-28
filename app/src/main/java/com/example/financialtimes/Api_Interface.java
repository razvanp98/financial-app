package com.example.financialtimes;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.Query;

public interface Api_Interface {
    @GET("stocks_API.php")
    Call<List<Companies>> stocks_API(@Query("key") String keyword);
}
