package com.example.hs_final;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

public class additionalprofile extends AppCompatActivity {

    Button backbtn,btnhome;
    EditText devnumber, devlocation;
    TextView usertype,tvname;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additionalprofile);
        getSupportActionBar().hide();

        usertype = (TextView)findViewById(R.id.usertype);
        backbtn = (Button)findViewById(R.id.backbtn);
        devnumber = (EditText)findViewById(R.id.devnumber);
        devlocation =(EditText)findViewById(R.id.devlocation);
        img = (ImageView)findViewById(R.id.img);
        btnhome = (Button)findViewById(R.id.btnhome);
        tvname = (TextView)findViewById(R.id.tvname);
        tvname.setText("Hi," +Myapp.myname);

        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!devnumber.getText().toString().isEmpty()) {

                    SharedPreferences.Editor e=Myapp.pref.edit();
                    e.putString("device",devnumber.getText().toString());
                    e.commit();
                    Myapp.mydevice=devnumber.getText().toString();
                    FirebaseMessaging.getInstance().subscribeToTopic(Myapp.mydevice);
                    Intent i = new Intent(getApplicationContext(), home.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Enter All details", Toast.LENGTH_LONG).show();
                }
            }
        });

        devlocation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    devlocation.setBackgroundResource(R.drawable.onfocusedittext);
                else
                    devlocation.setBackgroundResource(R.drawable.underline);
            }
        });

        devnumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    devnumber.setBackgroundResource(R.drawable.onfocusedittext);
                else
                    devnumber.setBackgroundResource(R.drawable.underline);
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),home.class);
                startActivity(i);
                finish();
            }
        });

        img.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                ImageChooser(1);

            }

        });



    }



    private void ImageChooser(int n)

    {

        Intent intent = new Intent();

        intent.setType("image/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select image for iv"), n);

    }



    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);



        if(requestCode==1 && resultCode == RESULT_OK && data.getData()!=null)

        {



            try {

                Bitmap bits = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());


                img.setImageBitmap(bits);

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

        else

        {

            Toast.makeText(getApplicationContext(),"please select again", Toast.LENGTH_LONG).show();

        }



    }

}


