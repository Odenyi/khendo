package com.streaming.khendo;

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.khendo.R;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class myadapter extends RecyclerView.Adapter<myadapter.ImageViewHolder>{

    private Context mContext;
    private List<Upload> mUploads;

    public myadapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.singlerow, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Upload uploadCurrent = mUploads.get(position);
        holder.name.setText(uploadCurrent.getName());

        Glide.with(holder.imageView.getContext())
                .load(uploadCurrent.getImageUrl())
                .into(holder.imageView);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Gallery.class);
                intent.putExtra("image_url", uploadCurrent.getImageUrl());
                intent.putExtra("image_name", uploadCurrent.getName());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public  class ImageViewHolder extends RecyclerView.ViewHolder{
        public  TextView name;
        public CircleImageView imageView;

        //added this
        RelativeLayout parentLayout;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            name= itemView.findViewById(R.id.nametext);

            imageView = itemView.findViewById(R.id.img1);
            //addedthis
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

}


