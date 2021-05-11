package com.example.hs_final;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import org.w3c.dom.Text;

public class home extends AppCompatActivity {

    Context con = this;
    TextView tvname,devno;
    ImageView profileedit;
    RelativeLayout grel1, Drel1,crel4,crel5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        grel1=findViewById(R.id.grel1);
        Drel1=findViewById(R.id.drel2);
        profileedit=findViewById(R.id.profileedit);
        tvname = (TextView)findViewById(R.id.tvname);
        tvname.setText("Hi, "+Myapp.myname);
        devno=findViewById(R.id.devno);
        crel4 = (RelativeLayout)findViewById(R.id.crel4);
        crel5 = (RelativeLayout) findViewById(R.id.crel5);

        devno.setText("Device : "+Myapp.mydevice);
        grel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), allimages.class);
                startActivity(i);

            }
        });
        profileedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor e=Myapp.pref.edit();
                e.clear();
                e.commit();
                FirebaseMessaging.getInstance().unsubscribeFromTopic(Myapp.mydevice);
                Myapp.mynumber="";
                Myapp.myname="";
                Intent i = new Intent(getApplicationContext(), login.class);
                startActivity(i);
                finish();
            }
        });

        Drel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                SharedPreferences.Editor ed = Myapp.pref.edit();
                String devs;
                devs = Myapp.pref.getString("DeviceStatus", "");
               //Toast.makeText(getApplicationContext(),devs,Toast.LENGTH_LONG).show();

                switch (devs) {
                    case "on": {
                        Toast.makeText(getApplicationContext(),"OFF", Toast.LENGTH_LONG).show();
                        ed.putString("DeviceStatus", "off");
                        FirebaseMessaging.getInstance().unsubscribeFromTopic(Myapp.mydevice);
                        ed.commit();
                        devno.setText("Device : OFF");
                        break;
                    }
                    case "off": {
                        ed.putString("DeviceStatus", "on");
                        Toast.makeText(getApplicationContext(),"ON", Toast.LENGTH_LONG).show();
                        FirebaseMessaging.getInstance().subscribeToTopic(Myapp.mydevice);
                        ed.commit();
                        devno.setText("Device : "+Myapp.mydevice);
                        break;
                    }
                }

                /*if (devs == "on"){
                    Toast.makeText(getApplicationContext(),"OFF",Toast.LENGTH_LONG).show();
                    ed.putString("DeviceStatus", "off");
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(Myapp.mydevice);
                    ed.commit();

                }

                else if (devs=="off")
                {
                    ed.putString("DeviceStatus", "on");
                    Toast.makeText(getApplicationContext(),"ON",Toast.LENGTH_LONG).show();
                    FirebaseMessaging.getInstance().subscribeToTopic(Myapp.mydevice);
                    ed.commit();
                }*/

            }
        });
        crel4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = new Dialog(home.this);
                d.setContentView(R.layout.layout);
                d.getWindow().setBackgroundDrawableResource(R.drawable.abc);
                TextView yesbtn = (TextView) d.findViewById(R.id.yesbtn);
                TextView nobtn = (TextView) d.findViewById(R.id.nobtn);
                yesbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String number = "8153052123";
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + number));
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(callIntent);
                    }
                });

                nobtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });
                d.show();
            }

        });

    }
}
