package com.example.android.kittenscats;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class PhotoDetailsActivity extends AppCompatActivity {

    private Photo currentImage;
    private ShareActionProvider mShareActionProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_details);

        Intent intent = getIntent();
        currentImage = (Photo) intent.getSerializableExtra("PHOTO_TRANSFER");

        ImageView imageView = (ImageView) findViewById(R.id.picture_detail);
        Picasso.with(this).load(currentImage.toUrlStringBySize("z")).into(imageView);

        TextView textOwner = (TextView) findViewById(R.id.picture_owner);
        textOwner.setText(currentImage.getExistName());

        TextView textTitle = (TextView) findViewById(R.id.picture_title);
        textTitle.setText(currentImage.getTitle());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photo_details, menu);

        // Fetch and store ShareActionProvider

        MenuItem shareItem = menu.findItem(R.id.menu_item_share);

        // To retrieve the Action Provider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        setShareIntent(createShareIntent());

        return true;
    }

    private Intent createShareIntent() {

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                "Photo: " + currentImage.toShortUrl() +
                        "\n" + "by: " + currentImage.getExistName() +
                        "\n" + "Kittens & Cats app");
        Intent.createChooser(shareIntent, "Share catlink to..");
        return shareIntent;
    }

    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }

    }
}
