package com.example.hs_final;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Map;



public class Myapp extends Application {

    public static FirebaseDatabase db;
    public static DatabaseReference ref;
    public static SharedPreferences pref;
    public static String mynumber,myname,mydevice, devs;
    public static DatabaseReference myref;
    public static Map<String,Object> userdata;
    public static Context con,basecon;

    private static Myapp sInstance;

    public static String serverkey="";
    public static String senderid="";


    @Override
    public void onCreate() {
        super.onCreate();
        db = FirebaseDatabase.getInstance();
        db.setPersistenceEnabled(true);
        ref = db.getReference();
        con = getApplicationContext();
        sInstance = this;
        pref = getSharedPreferences("myinfo", MODE_PRIVATE);
        SharedPreferences.Editor ed = pref.edit();

        ed.putString("DeviceStatus", "on");
        ed.commit();
        basecon=getBaseContext();
        mynumber=pref.getString("mynumber","");
        if(!mynumber.equals(""))
        {
            getUserdata();

            mydevice=pref.getString("device","");
            devs = pref.getString("DeviceStatus", "");

            if (devs == "on") {

                FirebaseMessaging.getInstance().subscribeToTopic(mydevice);
            }

            else if (devs == "off"){

                FirebaseMessaging.getInstance().unsubscribeFromTopic(Myapp.mydevice);
            }


        }


       // Toast.makeText(getBaseContext(),"Hello",Toast.LENGTH_LONG).show();
    }




    void getUserdata() {
        myref = db.getReference("users").child(mynumber);
        myref.keepSynced(true);
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                userdata = (Map<String, Object>) dataSnapshot.getValue();
                myname = userdata.get("name") + "";


                //   Toast.makeText(getApplicationContext(),"Data : "+userdata,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
