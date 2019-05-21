package com.example.financialtimes;

import android.support.annotation.Nullable;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CryptoInterface {
    @GET("financial-times-API/crypto_api.php")
    Call<List<Currency>> cryptoApi(@Query("key") @Nullable String keyword);
}
