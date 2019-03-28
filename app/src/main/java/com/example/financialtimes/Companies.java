package com.example.financialtimes;

import com.google.gson.annotations.SerializedName;

public class Companies {
    @SerializedName("name") private String company_name;
    @SerializedName("symbol") private String company_symbol;

    public String getCompany_name() {
        return company_name;
    }

    public String getCompany_symbol() {
        return company_symbol;
    }
}
