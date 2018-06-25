package com.example.android.artificialintelligencenews;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aania on 20.06.2018.
 */

public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils(){}

    public static List<Article> fetchArticles(String queryUrl) {

        URL url = createURLObject(queryUrl);
        String jsonString = "";
        try{
            jsonString = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem with making httpRequest", e);
        }
        List<Article> articles = extractArticlesFromJsonResponse(jsonString);

        return articles;
    }

    private static URL createURLObject(String queryUrl) {
        URL url = null;
        try{
            url = new URL(queryUrl);
        } catch (MalformedURLException e){
            Log.e(LOG_TAG, "Error creating URL object from given url String", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {

        String jsonString = "";
        if(url == null){
            return jsonString;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStreamResponse = null;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200){
                inputStreamResponse = urlConnection.getInputStream();
                jsonString = convertStreamToString(inputStreamResponse);
            } else {
                Log.e(LOG_TAG, "Error http response code " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem with making UrlConnection", e);
        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(inputStreamResponse != null){
                inputStreamResponse.close();
            }
        }
        return jsonString;
    }

    private static String convertStreamToString(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while((line = bufferedReader.readLine()) != null){
                output.append(line);
            }
        }
        return output.toString();
    }

    private static List<Article> extractArticlesFromJsonResponse(String jsonResponse) {

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

                String imageURL = fields.getString("thumbnail");
                Bitmap imageArticle = loadImageFromUrl(imageURL);

                if (imageArticle == null) {
                    return null;
                }

                Article article = new Article(categoryArticle, titleArticle, dateArticle, authorArticle, webUrlArticle, imageArticle);
                articles.add(article);
            }


        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing from JSON String response", e);
        }

        return articles;
    }

    private static Bitmap loadImageFromUrl(String imageUrl) {
        URL url = createURLObject(imageUrl);
        Bitmap bitmap = null;

        try {
            bitmap = makeHttpRequestForImage(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem with load image from Url", e);
        }

        return bitmap;
    }

    private static Bitmap makeHttpRequestForImage(URL url) throws IOException {

        Bitmap bitmap = null;

        if (url == null) {
            return bitmap;
        }

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try{
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.connect();

            if(httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);

            } else {
                Log.e(LOG_TAG, "Error http image request code " + httpURLConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.v(LOG_TAG, "Problem making connection for image url", e);
        } finally {
            if(httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if(inputStream != null) {
                inputStream.close();
            }
        }
        return bitmap;
    }

}
