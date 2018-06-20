package com.example.android.artificialintelligencenews;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aania on 20.06.2018.
 */

public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils(){}

    public static List<Article> extractArticlesFromJsonResponse(String jsonResponse) {

        if(TextUtils.isEmpty(jsonResponse)){
            return null;
        }

        List<Article> articles = new ArrayList<>();

        try{
            JSONObject root = new JSONObject(jsonResponse);
            JSONObject response = root.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");

            for(int i = 0; i < results.length(); i++) {
                JSONObject articleInfo = results.getJSONObject(i);

                String categoryArticle = articleInfo.getString("sectionName");
                String titleArticle = articleInfo.getString("webTitle");
                String dateArticle = articleInfo.getString("webPublicationDate");
                String webUrlArticle = articleInfo.getString("webUrl");

                JSONObject fields = articleInfo.getJSONObject("fields");
                String authorArticle = fields.getString("byline");

                Article article = new Article(categoryArticle, titleArticle, dateArticle, authorArticle, webUrlArticle);
                articles.add(article);
            }


        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing from JSON String response", e);
        }

        return articles;
    }

}
