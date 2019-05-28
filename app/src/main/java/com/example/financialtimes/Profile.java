package com.example.financialtimes;

import com.google.gson.annotations.SerializedName;

public class Profile {
    @SerializedName("id") private String id;
    @SerializedName("name") private String name;
    @SerializedName("symbol") private String symbol;
    @SerializedName("profit") private String profit;
    @SerializedName("invested") private String invested;
    @SerializedName("coins") private String coins;

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getProfit() {
        return profit;
    }

    public String getInvested() {
        return invested;
    }

    public String getCoins() {
        return coins;
    }

    public String getId() {
        return id;
    }
}
