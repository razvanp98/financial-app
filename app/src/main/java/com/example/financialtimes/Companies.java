package com.example.financialtimes;

import com.google.gson.annotations.SerializedName;

public class Companies {
    // SerializedName annotation tells the library that those are the field names in JSON object
    // that must be expected
    @SerializedName("name") private String company_name;
    @SerializedName("symbol") private String company_symbol;
    @SerializedName("today") private String price_today;
    @SerializedName("yesterday") private String price_yesterday;
    @SerializedName("volume") private String volume;
    @SerializedName("favourite") private String favourite;

    public String getCompany_name() {
        return company_name;
    }

    public String getCompany_symbol() {
        return company_symbol;
    }

    public String getPrice_today() {
        return price_today;
    }

    public String getPrice_yesterday() {
        return price_yesterday;
    }

    public String getVolume() {
        return volume;
    }

    public String getFavourite() {
        return favourite;
    }
}
