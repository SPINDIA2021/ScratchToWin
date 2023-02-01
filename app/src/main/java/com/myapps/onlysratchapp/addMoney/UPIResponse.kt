package com.myapps.onlysratchapp.addMoney

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class UPIResponse {

    @SerializedName("upiid")
    @Expose
    private var upiid: String? = null

    fun getUpiid(): String? {
        return upiid
    }

    fun setUpiid(upiid: String?) {
        this.upiid = upiid
    }

}