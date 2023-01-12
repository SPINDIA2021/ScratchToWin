package com.myapps.onlysratchapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReferUserResponse {
    @SerializedName("user")
    @Expose
    private String user;

    public ReferUserResponse(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
