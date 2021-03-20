package com.example.andriod_mining_rig;

import com.google.gson.annotations.SerializedName;

public class APIResponse {
    @SerializedName("body")
    private String text;

    public String getText() {
        return text;
    }
}
