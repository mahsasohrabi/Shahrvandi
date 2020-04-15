package com.shahrvandi.shahrvandi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginResponseModel extends BasicResponseModel {

    @SerializedName("CellPhone")
    private String cellPhone;

    @SerializedName("Email")
    private String email;

    @SerializedName("FirstName")
    private String firstName;

    @SerializedName("LastName")
    private String lastName;

    @SerializedName("Roles")
    private List<RoleModel> roles;

    @SerializedName("UserId")
    private String userId;

    @SerializedName("Username")
    private String username;

    public String getCellPhone() {
        return cellPhone;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<RoleModel> getRoles() {
        return roles;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setRoles(List<RoleModel> roles) {
        this.roles = roles;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
