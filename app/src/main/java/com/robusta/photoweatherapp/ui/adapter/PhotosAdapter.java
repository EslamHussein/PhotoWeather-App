package com.robusta.photoweatherapp.ui.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.robusta.photoweatherapp.R;
import com.robusta.photoweatherapp.model.dto.Photo;
import com.robusta.utils.OnClickPhoto;

import java.util.List;

/**
 * Created by Eslam Hussein on 10/28/17.
 */

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> {

    private List<Photo> mData;
    private OnClickPhoto onClickPhoto;

    public PhotosAdapter(List<Photo> mData, OnClickPhoto onClickPhoto) {
        this.mData = mData;
        this.onClickPhoto = onClickPhoto;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Photo photo = mData.get(position);
        holder.thumbnailImageView.setImageBitmap(photo.getBitmap());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickPhoto.click(photo);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnailImageView;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            thumbnailImageView = (ImageView) itemView.findViewById(R.id.image_view_thumbnail);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }

    }
}
