package com.myapps.onlysratchapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LevelResponse {
    @SerializedName("level")
    @Expose
    private Integer level;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("flag")
    @Expose
    private String flag;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
