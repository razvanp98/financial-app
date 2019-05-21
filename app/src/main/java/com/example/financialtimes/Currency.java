package com.example.financialtimes;

import com.google.gson.annotations.SerializedName;

public class Currency {
    @SerializedName("name") private String name;
    @SerializedName("symbol") private String symbol;
    @SerializedName("lowest") private String lowest;
    @SerializedName("highest") private String highest;
    @SerializedName("latestVolume") private String latest_volume;
    @SerializedName("latestPrice") private String latest_price;
    @SerializedName("profileCount") private int count_no;

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getLowest() {
        return lowest;
    }

    public String getHighest() {
        return highest;
    }

    public String getLatest_volume() {
        return latest_volume;
    }

    public String getLatest_price() {
        return latest_price;
    }

    public int getCount_no() {
        return count_no;
    }
}
