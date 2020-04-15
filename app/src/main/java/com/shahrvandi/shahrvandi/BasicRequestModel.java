package com.shahrvandi.shahrvandi;

import com.google.gson.annotations.SerializedName;
import com.shahrvandi.shahrvandi.Network.NetHandler;

public abstract class BasicRequestModel {

    @SerializedName("Key")
    public String key;

    public BasicRequestModel() {
        this.key = NetHandler.getServerKey();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
