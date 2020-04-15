package com.shahrvandi.shahrvandi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.shahrvandi.shahrvandi.Adapter.SliderAdapter;
import com.shahrvandi.shahrvandi.MyActivity;
import com.shahrvandi.shahrvandi.R;

public class PrimitiveActivity extends MyActivity {

    Button btnSignIn, btnLogin,btnGuest;
    ViewPager viewPager;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primitive);
        init();
        click();
        viewPager.setAdapter(new SliderAdapter(this));
        tabLayout.setupWithViewPager(viewPager, true);
    }

    private void click() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PrimitiveActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PrimitiveActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        btnGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PrimitiveActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        viewPager=findViewById(R.id.viewpager);
        tabLayout=findViewById(R.id.tablayout);
        btnLogin =findViewById(R.id.btn_login);
        btnSignIn=findViewById(R.id.btn_sing_in);
        btnGuest=findViewById(R.id.btn_guest);
    }
}
