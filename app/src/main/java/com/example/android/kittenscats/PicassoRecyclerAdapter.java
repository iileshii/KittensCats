package com.example.android.kittenscats;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Leshii on 7/5/2015.
 * Recycler view adapter
 */
public class PicassoRecyclerAdapter extends RecyclerView.Adapter<PhotosViewHolder> {
    private List<Photo> mPhotos;
    private Context mContext;

    public PicassoRecyclerAdapter(Context context, List<Photo> photos) {
        mContext = context;
        mPhotos = photos;
    }

    @Override
    public int getItemCount() {
        return (null != mPhotos ? mPhotos.size() : 0);
    }

    @Override
    public PhotosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder, null);
        return new PhotosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotosViewHolder holder, int position) {
        Photo photoItem = mPhotos.get(position);

        String url = photoItem.toUrlStringBySize("q");

        // Trigger the download of the URL asynchronously into the image view.
        Picasso.with(mContext)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.image);
        holder.title.setText(photoItem.getTitle());
        holder.author.setText(photoItem.getExistName());
    }
}
