package com.example.hs_final;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.hs_final.Myapp;
import com.example.hs_final.R;
import com.example.hs_final.home;
import com.example.hs_final.login;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);


        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!Myapp.mynumber.equals(""))
                {
                    Intent i = new Intent(getApplicationContext(), home.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Intent i = new Intent(getApplicationContext(), login.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 2000);

    }
}
