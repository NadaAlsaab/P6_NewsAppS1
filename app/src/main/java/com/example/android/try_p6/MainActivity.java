package com.example.android.try_p6;

import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.net.ConnectivityManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>{

    // URL
    private static final String stringUrl = "http://content.guardianapis.com/search?q=debates&api-key=test&show-fields=byline";

    // progress bar
    ProgressBar mProgressBar;

    // Adapter
    private Adapter mAdapter;

    // text view
    private TextView jsonInfo;

    // list
    ListView newsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        // Find a reference
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        jsonInfo = findViewById(R.id.empty_view);
        newsListView = (ListView) findViewById(R.id.list);
        newsListView.setEmptyView(jsonInfo);

        // creat a new adapter
        mAdapter = new Adapter(this, new ArrayList<News>());

        // set the adapter to the list view
        newsListView.setAdapter(mAdapter);


        // check the network connectivity status
        ConnectivityManager CM = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo NI = CM.getActiveNetworkInfo();

        if (NI != null && NI.isConnected()) { //connected
            newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                    // Find the current earthquake that was clicked on
                    News currentNews = (News) mAdapter.getItem(position);

                    // Convert the String URL into a URI object (to pass into the Intent constructor)
                    Uri earthquakeUri = Uri.parse(currentNews.getUrl());

                    // Create a new intent to view the earthquake URI
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                    // Send the intent to launch a new activity
                    startActivity(websiteIntent);
                }
            });

            // initialize loader
            getSupportLoaderManager().initLoader(0, null, this).forceLoad();
        }
        else{
            // error message
            jsonInfo.setText("No internet connection");

            // stop progress bar
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int id, @Nullable Bundle args) {
        return new LoaderNews (this ,stringUrl);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<News>> loader, List<News> data) {
        jsonInfo.setText("No data to display");
        mProgressBar.setVisibility(View.GONE);
        mAdapter = new Adapter(this, data);
        newsListView.setAdapter(mAdapter);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<News>> loader) {

    }
}
