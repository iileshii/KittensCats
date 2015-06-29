package com.example.android.kittenscats;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leshii on 6/24/2015.
 * This class gets json to show
 */
public class FetchCatsImage extends AsyncTask<String, Void, String> {

    private Context mContext;
    private ListView mListView;
    private List<String> imagesUrlArray = new ArrayList<>();

    private String stringUrl = "https://api.flickr.com/services/feeds/photos_public.gne" +
            "?tags=cats,cat,kitten,kittens&tagmode=ANY&format=json";

    public FetchCatsImage(Context context, ListView listView) {
        mContext = context;
        mListView = listView;
    }

    public String getUrlString(int index) {
        if (imagesUrlArray.size() < index + 1) return null;
        else
            return imagesUrlArray.get(index);
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            URL url = new URL(stringUrl);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder stringBuilder = new StringBuilder();
            String streamLine;

            while ((streamLine = reader.readLine()) != null) {
                stringBuilder.append(streamLine).append("\n");
            }

            if (stringBuilder.length() == 0) {
                return null;
            }

            getDataFromJSON(stringBuilder.toString().replace("jsonFlickrFeed(", ""));

            reader.close();
            inputStream.close();
            urlConnection.disconnect();

        } catch (java.io.IOException | JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

    private void getDataFromJSON(String stringJSON) throws JSONException {

        JSONObject jsonObject = new JSONObject(stringJSON);

        JSONArray jsonArray = jsonObject.getJSONArray("items");

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject jsonLine = (JSONObject) jsonArray.get(i);
            imagesUrlArray.add(jsonLine
                    .getJSONObject("media").getString("m").replace("_m.jpg", ".jpg"));
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        final PicassoArrayAdapter picassoArrayAdapter =
                new PicassoArrayAdapter(mContext, R.id.list_view, imagesUrlArray);

        mListView.setAdapter(picassoArrayAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext, PhotoDetailsActivity.class);
                intent.putExtra("PHOTO_TRANSFER", imagesUrlArray.get(i));
                mContext.startActivity(intent);
            }
        });

    }

    public List<String> getImagesUrlArray() {
        return imagesUrlArray;
    }
}
