package com.shahrvandi.shahrvandi.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shahrvandi.shahrvandi.ApiService.JsonPlaceHolderApi;
import com.shahrvandi.shahrvandi.LoginRequestModel;
import com.shahrvandi.shahrvandi.LoginResponseModel;
import com.shahrvandi.shahrvandi.MyActivity;
import com.shahrvandi.shahrvandi.Network.NetHandler;
import com.shahrvandi.shahrvandi.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoActivity extends MyActivity {

    TextView tvUserName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
    }

    private void serverLogin(String username, String pass) {

        JsonPlaceHolderApi jsonPlaceHolderApi = NetHandler.getRetrofit().create(JsonPlaceHolderApi.class);

        LoginRequestModel requestModel = new LoginRequestModel(username, pass);


        Call<LoginResponseModel> call = jsonPlaceHolderApi.login(requestModel);

        call.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {

                LoginResponseModel model = response.body();
                Log.d("Response",model.getEmail());

                if (model.getUsername() == null || model.getUsername().equals("null")) {
                    //TODO: show error
                } else {
                    // user logged in successfully

//                    DataHandler.loginResponse = model;
//                    SpHandler.get(LoginActivity.this).setUserId(model.getUserId());
//                    Intent i = new Intent(LoginActivity.this, BaseActivity.class);
//                    startActivity(i);
//                    getActivity().finish();
                }
            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {

            }
        });
    }
}
