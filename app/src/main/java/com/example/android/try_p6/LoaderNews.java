package com.example.android.try_p6;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class LoaderNews extends AsyncTaskLoader<List<News>> {

    private URL mUrl;


    public LoaderNews(Context context, String mUrl) {
        super(context);
        try {
            this.mUrl = new URL(mUrl);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        return QueryUtils.getUrlData(mUrl);
    }
}