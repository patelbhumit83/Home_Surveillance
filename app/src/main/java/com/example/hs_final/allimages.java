package com.example.hs_final;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class allimages extends AppCompatActivity {



    RecyclerView recyclerView;
    com.example.hs_final.Myadpater myadpater;
    List<Model> list;
    ProgressDialog pd;
    static Context con;
    static Bitmap mBitmap;

    ImageView backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allimages);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         con= this;



         backbtn = (ImageView)findViewById(R.id.backbtn);
         backbtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent i = new Intent(getApplicationContext(),home.class);
                 startActivity(i);
                 finish();
             }
         });

        pd=new ProgressDialog(allimages.this);
        pd.setMessage("please wait");
        pd.setCancelable(false);
        pd.show();
        recyclerView=findViewById(R.id.rel);
        list=new ArrayList<>();
        myadpater=new Myadpater(list);




        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(allimages.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(myadpater);

        getData();



    }



    void getData()
    {
        Myapp.ref.child("device").child(Myapp.mydevice).child("pics").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                pd.dismiss();
                if(dataSnapshot.getValue()!=null)
                {
                    list.clear();
                    myadpater.notifyDataSetChanged();
                    Map<String,Object> dd=(Map<String, Object>)dataSnapshot.getValue();
                    List<String> keys=new ArrayList<>(dd.keySet());
                    for(int i=0;i<keys.size();i++)
                    {
                        list.add(new Model(keys.get(i),""+dd.get(keys.get(i))));
                    }
                    myadpater.notifyDataSetChanged();

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No data found", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
