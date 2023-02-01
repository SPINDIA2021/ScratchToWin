package com.myapps.onlysratchapp.transferPoints

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class TRansferUserResponse {

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("referral_code")
    @Expose
    private var referralCode: String? = null

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getReferralCode(): String? {
        return referralCode
    }

    fun setReferralCode(referralCode: String?) {
        this.referralCode = referralCode
    }

}