package com.example.android.artificialintelligencenews;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by aania on 21.06.2018.
 */

public class ArticlesLoader extends AsyncTaskLoader<List<Article>> {

    private String mUrl;

    public ArticlesLoader(Context context, String url){
        super(context);
        mUrl = url;
    }

    @Override
    public List<Article> loadInBackground() {

        if (mUrl == null){
            return null;
        }
        List<Article> articles = QueryUtils.fetchArticles(mUrl);

        return articles;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
