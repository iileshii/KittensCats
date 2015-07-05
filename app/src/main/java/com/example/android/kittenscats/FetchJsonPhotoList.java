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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leshii on 6/29/2015.
 * Fetch JSON Photo list and parse it
 */
public class FetchJsonPhotoList extends AsyncTask<Void, Void, Void> {

    private String mUrlPhotoSearch = "https://api.flickr.com/services/rest/" +
            "?method=flickr.photos.search&api_key=6f91ef5959d961087f0a6d1b105226df" +
            "&tags=cats,cat,kitten,kittens&tag_mode=ANY&per_page=5&format=json";

    private String mUrlUserInfo = "https://api.flickr.com/services/rest/" +
            "?method=flickr.people.getInfo&api_key=6f91ef5959d961087f0a6d1b105226df" +
            "&format=json&user_id=";

    private Context mContext;
    private ListView mListView;
    private List<Photo> mPhotoList = new ArrayList<>();

    public FetchJsonPhotoList(Context context, ListView listView) {
        mContext = context;
        mListView = listView;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            getPhotoDataFromJSON(Utils.getJSONStringByURL(mUrlPhotoSearch));
            getOwnerDataFromJSON();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void getPhotoDataFromJSON(String stringJSON) throws JSONException {

        JSONObject jsonObject = new JSONObject(stringJSON);

        JSONArray jsonArray = jsonObject.getJSONObject("photos").getJSONArray("photo");

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject jsonLine = (JSONObject) jsonArray.get(i);

            String currentId = jsonLine.getString("id");
            String currentOwner = jsonLine.getString("owner");
            String currentSecret = jsonLine.getString("secret");
            String currentServer = jsonLine.getString("server");
            String currentFarm = jsonLine.getString("farm");
            String currentTitle = jsonLine.getString("title");

            Photo currentPhoto = new Photo(currentId, currentOwner, currentSecret,
                    currentServer, currentFarm, currentTitle);

            mPhotoList.add(currentPhoto);
        }
    }

    private void getOwnerDataFromJSON() throws JSONException {

        for (Photo photo : mPhotoList) {

            String userInfoUrl = mUrlUserInfo + photo.getOwnerId();
            String stringJSON = Utils.getJSONStringByURL(userInfoUrl);

            JSONObject jsonObject = new JSONObject(stringJSON);

            JSONObject personNames = jsonObject.getJSONObject("person");
            String realName = null;
            String userName;

            if (!personNames.isNull("realname")) {
                realName = personNames.getJSONObject("realname").getString("_content");
            }
            userName = personNames.getJSONObject("username").getString("_content");

            photo.setOwnerNames(realName, userName);
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        final PicassoArrayAdapter picassoArrayAdapter =
                new PicassoArrayAdapter(mContext, R.id.list_view, mPhotoList);

        mListView.setAdapter(picassoArrayAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext, PhotoDetailsActivity.class);
                intent.putExtra("PHOTO_TRANSFER", mPhotoList.get(i));
                mContext.startActivity(intent);
            }
        });
    }
}
