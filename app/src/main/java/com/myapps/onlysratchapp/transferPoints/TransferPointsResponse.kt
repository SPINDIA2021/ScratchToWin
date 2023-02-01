package com.myapps.onlysratchapp.transferPoints

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class TransferPointsResponse {
    @SerializedName("coin")
    @Expose
    private var coin: Int? = null

    fun getCoin(): Int? {
        return coin
    }

    fun setCoin(coin: Int?) {
        this.coin = coin
    }
}