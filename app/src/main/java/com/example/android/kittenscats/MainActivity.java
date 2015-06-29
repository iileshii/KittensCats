package com.example.android.kittenscats;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity {

    private FetchCatsImage mFetchCatsImage;
    private ImageView imageView;
    private ImageView imageView2;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.image_view);
        imageView2 = (ImageView) findViewById(R.id.image_view2);

        mListView = (ListView) findViewById(R.id.list_view);

        mFetchCatsImage = new FetchCatsImage(this, mListView);
        mFetchCatsImage.execute();


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
            Picasso.with(this).load(mFetchCatsImage.getUrlString(0)).into(imageView);
            Picasso.with(this).load(mFetchCatsImage.getUrlString(1)).into(imageView2);
            return true;
        }

        if (id == R.id.action_fill) {
            Toast.makeText(this, "Hi! How are you?", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
