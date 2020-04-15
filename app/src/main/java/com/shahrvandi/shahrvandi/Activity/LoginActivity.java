package com.shahrvandi.shahrvandi.Activity;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shahrvandi.shahrvandi.ApiService.JsonPlaceHolderApi;
import com.shahrvandi.shahrvandi.Cache.SpHandler;
import com.shahrvandi.shahrvandi.DataHandler;
import com.shahrvandi.shahrvandi.LoginRequestModel;
import com.shahrvandi.shahrvandi.LoginResponseModel;
import com.shahrvandi.shahrvandi.MyActivity;
import com.shahrvandi.shahrvandi.Network.NetHandler;
import com.shahrvandi.shahrvandi.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends MyActivity {

    EditText editUserName,editPassword;
    Button btnLogin;
    TextView tvForgetPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        click();
    }

    private void click() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validForm()){
                    serverLogin(editUserName.getText().toString(),editPassword.getText().toString());
//                    Bundle bundle = new Bundle();
//                    bundle.putString("userName",editUserName.getText().toString());
//                    bundle.putString("password",editPassword.getText().toString());
//                    UserInfoFragment userInfoFragment=new UserInfoFragment();
//                    userInfoFragment.setArguments(bundle);
                }
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setLoadingMode(boolean b) {
//        if (b) {
//            progressBar.setVisibility(View.VISIBLE);
//            btnLogin.setVisibility(View.GONE);
//        } else {
//            progressBar.setVisibility(View.GONE);
//            btnLogin.setVisibility(View.VISIBLE);
//        }
    }

    private void emptyFields() {
//        etUsername.setText("");
//        etPassword.setText("");
    }

    private void serverLogin(String username, String pass) {

        setLoadingMode(true);

        JsonPlaceHolderApi jsonPlaceHolderApi = NetHandler.getRetrofit().create(JsonPlaceHolderApi.class);

        LoginRequestModel requestModel = new LoginRequestModel(username, pass);


        Call<LoginResponseModel> call = jsonPlaceHolderApi.login(requestModel);

        call.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {

                setLoadingMode(false);

                LoginResponseModel model = response.body();
                Log.d("Response",model.getEmail());
                Bundle bundle = new Bundle();
//                bundle.putString("userName",model.getEmail());
//                bundle.putString("password",editPassword.getText().toString());
//                UserInfoFragment userInfoFragment=new UserInfoFragment();
//                userInfoFragment.setArguments(bundle);
                if (model.getUsername() == null || model.getUsername().equals("null")) {
                    //TODO: show error
                    emptyFields();
                } else {
                    // user logged in successfully

                    DataHandler.loginResponse = model;
                    SpHandler.get(LoginActivity.this).setUserId(model.getUserId());
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {

                //TODO : show error
                setLoadingMode(false);


            }
        });
    }

    private boolean validForm() {
//        tvUsername.setTextColor(getResources().getColor(R.color.colorPrimaryText));
//        tvPassword.setTextColor(getResources().getColor(R.color.colorPrimaryText));
//        tvUsername.setText("نام کاربری خود را وارد کنید");
//        tvPassword.setText("رمز عبور خود را وارد کنید");
//        boolean flag = true;
//        if (etUsername.getText().toString().length() < 1) {
//            tvUsername.setTextColor(getResources().getColor(R.color.colorErrorText));
//            tvUsername.setText("نام کاربری نمی تواند خالی باشد");
//            flag = false;
//        }
//        if (etPassword.getText().toString().length() < 1) {
//            tvPassword.setTextColor(getResources().getColor(R.color.colorErrorText));
//            tvPassword.setText("رمز عبور نمیتواند خالی باشد");
//            flag = false;
//        }
//        return flag;
        return true;
    }
    private void init() {
        btnLogin =findViewById(R.id.btn_login);
        editUserName=findViewById(R.id.edit_user_name);
        editPassword=findViewById(R.id.edit_password);
        tvForgetPassword=findViewById(R.id.tv_forget_pass);
    }
}

