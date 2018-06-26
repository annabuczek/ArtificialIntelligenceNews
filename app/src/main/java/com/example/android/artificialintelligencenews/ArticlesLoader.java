package com.example.android.artificialintelligencenews;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Load List of Article by using AsyncTaskLoader
 */
public class ArticlesLoader extends AsyncTaskLoader<List<Article>> {

    /**
     * Query url
     */
    private String mUrl;

    /**
     * Constructs a new Articles Loader
     *
     * @param context of the activity
     * @param url     to load data from
     */
    public ArticlesLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    /**
     * Perform loading on a background thread
     *
     * @return List of Articles ready to show
     */
    @Override
    public List<Article> loadInBackground() {

        if (mUrl == null) {
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
