package com.myapps.onlysratchapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("referalcode")
    @Expose
    private String referalcode;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("upiid")
    @Expose
    private String upiid;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("firstotp")
    @Expose
    private String firstotp;
    @SerializedName("secondotp")
    @Expose
    private String secondotp;
    @SerializedName("counter")
    @Expose
    private String counter;
    @SerializedName("unique_request_number")
    @Expose
    private String uniqueRequestNumber;
    @SerializedName("beneficiary_id")
    @Expose
    private String beneficiaryId;
    @SerializedName("unique_transaction_reference")
    @Expose
    private String uniqueTransactionReference;
    @SerializedName("request")
    @Expose
    private String request;
    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("createat")
    @Expose
    private String createat;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("hash")
    @Expose
    private String hash;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpiid() {
        return upiid;
    }

    public void setUpiid(String upiid) {
        this.upiid = upiid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstotp() {
        return firstotp;
    }

    public void setFirstotp(String firstotp) {
        this.firstotp = firstotp;
    }

    public String getSecondotp() {
        return secondotp;
    }

    public void setSecondotp(String secondotp) {
        this.secondotp = secondotp;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public String getUniqueRequestNumber() {
        return uniqueRequestNumber;
    }

    public void setUniqueRequestNumber(String uniqueRequestNumber) {
        this.uniqueRequestNumber = uniqueRequestNumber;
    }

    public String getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(String beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public String getUniqueTransactionReference() {
        return uniqueTransactionReference;
    }

    public void setUniqueTransactionReference(String uniqueTransactionReference) {
        this.uniqueTransactionReference = uniqueTransactionReference;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getCreateat() {
        return createat;
    }

    public void setCreateat(String createat) {
        this.createat = createat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
