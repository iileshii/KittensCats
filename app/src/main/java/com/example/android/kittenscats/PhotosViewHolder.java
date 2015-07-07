package com.example.android.kittenscats;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Leshii on 7/5/2015.
 * Photo View Holder class
 */
class PhotosViewHolder extends RecyclerView.ViewHolder {
    ImageView image;
    TextView title;
    TextView author;

    public PhotosViewHolder(View itemView) {
        super(itemView);
        this.author = (TextView) itemView.findViewById(R.id.picture_author);
        this.image = (ImageView) itemView.findViewById(R.id.picture);
        this.title = (TextView) itemView.findViewById(R.id.picture_title);
    }
}
