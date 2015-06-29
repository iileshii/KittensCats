package com.example.android.kittenscats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Leshii on 6/26/2015.
 * Picasso Array Adapter to show images by String Array
 */
public class PicassoArrayAdapter extends ArrayAdapter<String> {


    public PicassoArrayAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.viewholder, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) view.findViewById(R.id.picture);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // Get the image URL for the current position.
        String url = getItem(position);

        // Trigger the download of the URL asynchronously into the image view.
        Picasso.with(getContext())
                .load(url)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.image);

        return view;
    }

    static class ViewHolder {
        ImageView image;
    }
}
