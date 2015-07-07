package com.example.android.kittenscats;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private List<Photo> mPhotoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        final PicassoRecyclerAdapter picassoRecyclerAdapter =
                new PicassoRecyclerAdapter(this, mPhotoList);
        FetchJsonPhotoList fetchJsonPhotoList = new FetchJsonPhotoList(this,
                mPhotoList,
                picassoRecyclerAdapter);

        RecyclerView.OnScrollListener scrollListener =
                new EndlessRecyclerOnScrollListener(layoutManager) {
                    @Override
                    public void onLoadMore(int current_page) {
                        FetchJsonPhotoList mFetchJsonPhotoList =
                                new FetchJsonPhotoList(getBaseContext(),
                                        mPhotoList,
                                        picassoRecyclerAdapter);
                        FetchJsonPhotoList.addPageNumber();
                        mFetchJsonPhotoList.execute();

                    }
                };

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.scrollToPosition(0);
        recyclerView.setAdapter(picassoRecyclerAdapter);
        recyclerView.addOnScrollListener(scrollListener);

        RecyclerItemClickListener.OnItemClickListener onItemClickListener =
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Photo currentImage = mPhotoList.get(position);
                        Intent intent = new Intent(getBaseContext(), PhotoDetailsActivity.class);
                        intent.putExtra("PHOTO_TRANSFER", currentImage);
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        Photo currentImage = mPhotoList.get(position);

                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_TEXT,
                                "Photo: " + currentImage.toShortUrl() +
                                        "\n" + "by: " + currentImage.getExistName() +
                                        "\n" + "Kittens & Cats app");
                        Intent.createChooser(shareIntent, "Share catlink to..");
                        startActivity(shareIntent);
                    }
                };

        RecyclerItemClickListener recyclerItemClickListener =
                new RecyclerItemClickListener(this, recyclerView, onItemClickListener);

        recyclerView.addOnItemTouchListener(recyclerItemClickListener);

        fetchJsonPhotoList.execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
//            Picasso.with(this).load(mFetchCatsImage.getUrlString(0)).into(imageView);
//            Picasso.with(this).load(mFetchCatsImage.getUrlString(1)).into(imageView2);
            Toast.makeText(this, "Hi! How are you? Settings", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.action_fill) {
            Toast.makeText(this, "Hi! How are you? Fill", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
