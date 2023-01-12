package com.myapps.onlysratchapp.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class BaseResponseNew<T> {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private T result;



    public T getResponsePacket() {
        return result;
    }

    public void setResponsePacket(T responsePacket) {
        this.result = responsePacket;
    }

    public boolean isResponseStatus() {
        return status;
    }

    public void setResponseStatus(boolean responseStatus) {
        this.status = responseStatus;
    }

    public BaseResponseNew<T> withResponseStatus(Boolean status) {
        this.status = status;
        return this;
    }

    public String getResponseMessage() {
        return message;
    }

    public void setResponseMessage(String message) {
        this.message = message;
    }

    public BaseResponseNew<T> withResponseMessage(String message) {
        this.message = message;
        return this;
    }


    public BaseResponseNew<T> withResponsePacket(T result) {
        this.result = result;
        return this;
    }
}

