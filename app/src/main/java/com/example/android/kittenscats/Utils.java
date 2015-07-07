package com.example.android.kittenscats;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Leshii on 6/29/2015.
 * Useful utils
 */
public class Utils {

    public static String getUrlPhotoSearch(int itemsPerPage, int pageNumber) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.flickr.com")
                .appendPath("services")
                .appendPath("rest")
                .appendQueryParameter("method", "flickr.photos.search")
                .appendQueryParameter("api_key", "6f91ef5959d961087f0a6d1b105226df")
                .appendQueryParameter("tags", "cats,cat,kitten,kittens")
                .appendQueryParameter("tag_mode", "ANY")
                .appendQueryParameter("per_page", String.valueOf(itemsPerPage))
                .appendQueryParameter("page", String.valueOf(pageNumber))
                .appendQueryParameter("format", "json");

        Uri result = builder.build();
        return result.toString();
    }

    public static String getJSONStringByURL(String stringURL) {
        String jsonLine = null;
        try {
            URL url = new URL(stringURL);

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

            jsonLine = stringBuilder.toString();

            reader.close();
            inputStream.close();
            urlConnection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (jsonLine != null) {
            jsonLine = cleanFlickrApiJson(jsonLine);
        }

        return jsonLine;
    }

    private static String cleanFlickrApiJson(String jsonLine) {
        String result = jsonLine.replace("jsonFlickrApi(", ""); // Delete starts symbols
        return result.substring(0, result.length() - 1); //Delete end symbol ")"
    }

    // this class works with Base58 code for short URLs
    public static class Base58 {

        private static final String BASE58_ALPHABET =
                "123456789abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";

        public static String encode(long number) {
            String result = "";
            int baseLength = BASE58_ALPHABET.length();

            while (number >= baseLength) {
                double div = number / baseLength;
                int mod = (int) (number - (baseLength * (long) div));
                result = BASE58_ALPHABET.substring(mod, mod + 1) + result;
                number = (long) div;
            }

            if (number != 0) result =
                    BASE58_ALPHABET.substring((int) number, (int) number + 1) + result;
            return result;

        }

        public static long decode(String text) {
            String line = text;
            long result = 0;
            long multi = 1;

            while (line.length() > 0) {
                int length = line.length();
                String digit = line.substring(length - 1, length);
                result += multi * BASE58_ALPHABET.indexOf(digit);
                multi = multi * BASE58_ALPHABET.length();
                line = line.substring(0, length - 1);
            }

            return result;
        }
    }
}
