package com.shahrvandi.shahrvandi.ApiService;

import com.shahrvandi.shahrvandi.CartableListRequestModel;
import com.shahrvandi.shahrvandi.CartableListResponseModel;
import com.shahrvandi.shahrvandi.LoginRequestModel;
import com.shahrvandi.shahrvandi.LoginResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {
    @POST("login")
    Call<LoginResponseModel> login(@Body LoginRequestModel requestModel);

    @POST("CartableList")
    Call<CartableListResponseModel> cartableList(@Body CartableListRequestModel model);
}
