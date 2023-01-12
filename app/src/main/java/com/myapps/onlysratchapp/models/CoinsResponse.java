package com.myapps.onlysratchapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoinsResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("referalcode")
    @Expose
    private String referalcode;
    @SerializedName("credit")
    @Expose
    private String credit;
    @SerializedName("debit")
    @Expose
    private String debit;
    @SerializedName("cur_balance")
    @Expose
    private String curBalance;
    @SerializedName("createat")
    @Expose
    private String createat;
    @SerializedName("narration")
    @Expose
    private String narration;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReferalcode() {
        return referalcode;
    }

    public void setReferalcode(String referalcode) {
        this.referalcode = referalcode;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getDebit() {
        return debit;
    }

    public void setDebit(String debit) {
        this.debit = debit;
    }

    public String getCurBalance() {
        return curBalance;
    }

    public void setCurBalance(String curBalance) {
        this.curBalance = curBalance;
    }

    public String getCreateat() {
        return createat;
    }

    public void setCreateat(String createat) {
        this.createat = createat;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

}
