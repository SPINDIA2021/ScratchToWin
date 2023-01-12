package com.myapps.onlysratchapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConversionResponse {

    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("coin")
    @Expose
    private Integer coin;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getCoin() {
        return coin;
    }

    public void setCoin(Integer coin) {
        this.coin = coin;
    }

}
