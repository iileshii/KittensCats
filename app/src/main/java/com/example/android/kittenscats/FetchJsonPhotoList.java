package com.example.android.kittenscats;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Leshii on 6/29/2015.
 * Fetch JSON Photo list and parse it
 */
public class FetchJsonPhotoList extends AsyncTask<Void, Void, Void> {

    private static final int ITEMS_PER_PAGE = 10;
    private static int sPageNumber = 1;
    private String mUrlPhotoSearch = "https://api.flickr.com/services/rest/" +
            "?method=flickr.photos.search&api_key=6f91ef5959d961087f0a6d1b105226df" +
            "&tags=cats,cat,kitten,kittens&tag_mode=ANY&per_page=20&format=json";
    private String mUrlUserInfo = "https://api.flickr.com/services/rest/" +
            "?method=flickr.people.getInfo&api_key=6f91ef5959d961087f0a6d1b105226df" +
            "&format=json&user_id=";

    private Context mContext;
    private List<Photo> mPhotoList;
    private PicassoRecyclerAdapter mPicassoRecyclerAdapter;


    public FetchJsonPhotoList(Context context,
                              List<Photo> photoList,
                              PicassoRecyclerAdapter picassoRecyclerAdapter) {
        mContext = context;
        mPhotoList = photoList;
        mPicassoRecyclerAdapter = picassoRecyclerAdapter;
    }

    public static void addPageNumber() {
        sPageNumber++;
    }

    public static int getPageNumber() {
        return sPageNumber;
    }

    public static int getItemsPerPage() {
        return ITEMS_PER_PAGE;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            String mUrl = Utils.getUrlPhotoSearch(ITEMS_PER_PAGE, sPageNumber);
            getPhotoDataFromJSON(Utils.getJSONStringByURL(mUrl));
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
            getOwnerDataFromJSON(currentPhoto);
            mPhotoList.add(currentPhoto);
            publishProgress();
        }
    }

    private void getOwnerDataFromJSON(Photo photo) throws JSONException {

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

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);

        mPicassoRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        mPicassoRecyclerAdapter.notifyDataSetChanged();
    }
}
