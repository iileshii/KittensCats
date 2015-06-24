package com.example.android.kittenscats;

import android.net.Uri;
import android.os.AsyncTask;
import android.widget.TextView;

/**
 * Created by Leshii on 6/24/2015.
 * This class gets image url to show
 */
public class GetCatsUrl extends AsyncTask {

    private TextView textView;
    private Uri mUri;

    public GetCatsUrl(TextView textView) {
        this.textView = textView;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("thecatapi.com")
                .appendPath("api")
                .appendPath("images")
                .appendPath("get")
                .appendQueryParameter("format", "src")
                .appendQueryParameter("type", "gif");
        this.mUri = builder.build();

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        textView.setText(mUri.toString());
    }
}
