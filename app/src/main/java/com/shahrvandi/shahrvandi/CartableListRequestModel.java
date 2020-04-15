package com.shahrvandi.shahrvandi;

import com.google.gson.annotations.SerializedName;

public class CartableListRequestModel extends BasicRequestModel {

    @SerializedName("RoleId")
    private String roleId;

    @SerializedName("UserId")
    private String userId;

    public CartableListRequestModel(String roleId, String userId) {
        this.roleId = roleId;
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
