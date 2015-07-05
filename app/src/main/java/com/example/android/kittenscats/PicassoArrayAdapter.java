package com.example.android.kittenscats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Leshii on 6/26/2015.
 * Picasso Array Adapter to show images by String Array
 */
public class PicassoArrayAdapter extends ArrayAdapter<Photo> {


    public PicassoArrayAdapter(Context context, int resource, List<Photo> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.viewholder, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) view.findViewById(R.id.picture);
            holder.title = (TextView) view.findViewById(R.id.picture_title);
            holder.author = (TextView) view.findViewById(R.id.picture_author);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // Get the image URL for the current position.
        String url = getItem(position).toUrlStringBySize("q");

        // Trigger the download of the URL asynchronously into the image view.
        Picasso.with(getContext())
                .load(url)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.image);
        holder.title.setText(getItem(position).getTitle());
        holder.author.setText(getItem(position).getExistName());

        return view;
    }

    static class ViewHolder {
        ImageView image;
        TextView title;
        TextView author;
    }
}
