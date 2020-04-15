package com.shahrvandi.shahrvandi;

import com.google.gson.annotations.SerializedName;

public class BasicResponseModel {

    @SerializedName("Description")
    public String description;

    @SerializedName("ErrorCode")
    public String errorCode;

    @SerializedName("IsValidResult")
    public String isValidResult;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setIsValidResult(String isValidResult) {
        this.isValidResult = isValidResult;
    }

    public String getDescription() {
        return description;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getIsValidResult() {
        return isValidResult;
    }
}
