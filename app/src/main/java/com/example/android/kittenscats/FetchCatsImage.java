package com.example.android.kittenscats;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Leshii on 6/24/2015.
 * This class gets json to show
 */
public class FetchCatsImage extends AsyncTask<String, Void, String> {

    private Context mContext;
    private ArrayList<String> imagesUrlArray = new ArrayList<>();

    private String stringUrl = "https://api.flickr.com/services/feeds/photos_public.gne" +
            "?tags=cats,cat,kitten,kittens&tagmode=ANY&format=json";

    public FetchCatsImage(Context context) {
        mContext = context;
    }

    public String getUrlString() {
        if (imagesUrlArray.size() == 0) return null;
        else
            return imagesUrlArray.get(0);
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
                    .getJSONObject("media").getString("m"));
        }
    }

}
