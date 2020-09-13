package com.example.nayanpatel.hs_final;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;

import java.net.URI;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class Myadpater extends RecyclerView.Adapter<Myadpater.MyViewHolder> {

    static Bitmap theBitmap;

private List<Model> moviesList;

public class MyViewHolder extends RecyclerView.ViewHolder {
   public ImageView iv;

    public MyViewHolder(View view) {
        super(view);

        iv=(ImageView)view.findViewById(R.id.img);


    }
}


    public Myadpater(List<Model> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Model movie = moviesList.get(position);

        Glide.with(holder.iv.getContext()).load(movie.img)
                .into(holder.iv);


        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* try {
                   theBitmap = Glide.
                            with(allimages.con.getApplicationContext()).
                            load("http://....").
                            asBitmap().
                            into(100, 100). // Width and height
                            get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }*/

             /*   Uri uri =
               *//* BitmapDrawable drawable = (BitmapDrawable) holder.iv.getDrawable();
                allimages.mBitmap = drawable.getBitmap();*/
                Intent i = new Intent(allimages.con.getApplicationContext(),imageslabel.class);
                allimages.con.getApplicationContext().startActivity(i);


            }
        });

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
