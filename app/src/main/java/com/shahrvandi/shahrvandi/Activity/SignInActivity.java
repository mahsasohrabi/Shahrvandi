package com.shahrvandi.shahrvandi.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.shahrvandi.shahrvandi.MyActivity;
import com.shahrvandi.shahrvandi.R;

public class SignInActivity extends MyActivity {

    EditText editUserName,editStatus,editTell,editNationalCode,editEmail;
    Button btnSingIn;
    ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        init();
        click();
    }
    private void click() {
        btnSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignInActivity.this, "ثبت نام انجام شد.", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(SignInActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void init() {
        editUserName=findViewById(R.id.edit_user_name);
        editStatus=findViewById(R.id.edit_status);
        editTell=findViewById(R.id.edit_tell);
        editNationalCode=findViewById(R.id.edit_national_code);
        editEmail=findViewById(R.id.edit_email);
        btnSingIn=findViewById(R.id.btn_sing_in);
        imgBack=findViewById(R.id.img_back);
    }
}
