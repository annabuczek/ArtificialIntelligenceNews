package com.example.android.artificialintelligencenews;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>> {

    private static final String apiKey = BuildConfig.GUARDIAN_API_KEY;
    private static final int LOADER_ID = 0;
    private static final String QUERY_URL_STRING = "http://content.guardianapis.com/search?q=artificial%20intelligence";
    private static String QUERY_URL_STRING_FINAL = "";
    private ArticleAdapter mAdapter;
    private ProgressBar progressBar;
    private TextView messageTextView;
    private Button refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find views for further use
        ListView listView = findViewById(R.id.list_view);
        progressBar = findViewById(R.id.progress_bar);
        messageTextView = findViewById(R.id.message_text_view);
        refresh = findViewById(R.id.refresh_button);
        //Set visibility of the button as one to use only in special case
        refresh.setVisibility(View.GONE);

        // Get instance of LayoutInflater to inflate list view footer
        LayoutInflater footerInflater = getLayoutInflater();
        //Inflate footer layout to use it as a list view footer
        ViewGroup footer = (ViewGroup) footerInflater.inflate(R.layout.list_view_footer, listView, false);
        //Add footer to the ListView as a selectable object
        listView.addFooterView(footer, null, true);

        //On a ListView set empty view(TextView in this case) to show when list is empty
        listView.setEmptyView(messageTextView);

        // Set custom ArticleAdapter on a ListView
        mAdapter = new ArticleAdapter(MainActivity.this, new ArrayList<Article>());
        listView.setAdapter(mAdapter);

        // Set onItemClickListener on each item in a List<Article> to open a website
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Article currentItem = mAdapter.getItem(position);

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(currentItem.getWebUrl()));

                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivity(i);
                }
            }
        });

        //Check internet connection and initialize Loader
        if (isConnected()) {
            QUERY_URL_STRING_FINAL = buildQueryString(QUERY_URL_STRING, false);
            getLoaderManager().initLoader(LOADER_ID, null, MainActivity.this);
        } else {
            progressBar.setVisibility(View.GONE);
            messageTextView.setText(getString(R.string.no_connection));
            refresh.setVisibility(View.VISIBLE);

            // Try to initialize Loader if there is internet connection after tapping 'refresh' button
            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isConnected()) {
                        QUERY_URL_STRING_FINAL = buildQueryString(QUERY_URL_STRING, false);
                        getLoaderManager().initLoader(LOADER_ID, null, MainActivity.this);
                        progressBar.setVisibility(View.VISIBLE);
                        messageTextView.setVisibility(View.GONE);
                        refresh.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(MainActivity.this, getText(R.string.toast_no_connection), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        //Set onClickListener on a button contained by ListView footer
        Button footerButton = footer.findViewById(R.id.more_button);
        footerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //If there is internet connection build new query string and restart Loader to load new data
                if (isConnected()) {
                    QUERY_URL_STRING_FINAL = buildQueryString(QUERY_URL_STRING, true);
                    getLoaderManager().restartLoader(LOADER_ID, null, MainActivity.this);
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(MainActivity.this, getText(R.string.toast_no_connection_new), Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public Loader<List<Article>> onCreateLoader(int id, Bundle args) {

        //Create a new loader for a given url
        return new ArticlesLoader(MainActivity.this, QUERY_URL_STRING_FINAL);
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> data) {
        //Clear adapter from previous data
        mAdapter.clear();

        progressBar.setVisibility(View.GONE);

        //If there is valid List<Article> data set, then add it to adapter
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }

        messageTextView.setText(getString(R.string.no_articles));
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
        // Clear adapter if Loader is not used anymore
        mAdapter.clear();
        mAdapter.addAll((new ArrayList<Article>()));
    }

    /**
     * Helper method to check if device has access to internet connection
     *
     * @return true if there is internet connection or false if there isn't
     */
    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int menuItemId = item.getItemId();
        if (menuItemId == R.id.settings_menu_item) {
            Intent i = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Method to build URL String. Used before Loader is initialized.
     *
     * @param queryString base query String to add query params to
     * @param toNextPage  boolean to inform if URL strong is going to be used to perform query for next page
     * @return full URL String
     */
    private String buildQueryString(String queryString, Boolean toNextPage) {

        // Prepare URL base to build full URL query String
        Uri baseUri = Uri.parse(queryString);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        // Get instance of Shared Preferences and find all the preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean orderByNewest = sharedPreferences.getBoolean(getString(R.string.order_by_pref_key), false);
        String pageSize = sharedPreferences.getString(getString(R.string.page_size_pref_key), getString(R.string.page_size_pref_default));
        String category = sharedPreferences.getString(getString(R.string.category_pref_key), getString(R.string.category_pref_default));

        // Add query parameters used despite the case
        uriBuilder.appendQueryParameter("show-fields", "byline,thumbnail");
        uriBuilder.appendQueryParameter("format", "json");
        uriBuilder.appendQueryParameter("api-key", apiKey);

        // Add "order-by" query parameter if orderByNewest Preference is true.
        if (orderByNewest) {
            uriBuilder.appendQueryParameter("order-by", "newest");
        }

        // Add "page-size" query parameter according to user choice, but only if page size is between 1-50
        // Otherwise set page size as default and show Toast to inform the user
        int pageSizeInteger = Integer.parseInt(pageSize);
        if (pageSizeInteger > 0 && pageSizeInteger <= 50) {
            uriBuilder.appendQueryParameter("page-size", pageSize);
        } else {
            Toast.makeText(this, R.string.message_invalid_articles_num, Toast.LENGTH_SHORT).show();
        }

        // Add "section" query parameter according to the user choice, except all
        if (!category.equalsIgnoreCase("all")) {
            uriBuilder.appendQueryParameter("section", category);
        }

        // If toNextPage is true, it means user wants to see another page of current query
        // To do so check current page and add query parameter to see another page
        if (toNextPage) {
            if (QueryUtils.pageNumber > 0) {
                int nextPageNumber = QueryUtils.pageNumber + 1;
                String nextPageString = Integer.toString(nextPageNumber);
                Log.i("Check page", nextPageString);
                uriBuilder.appendQueryParameter("page", nextPageString);
            }
        }


        return uriBuilder.toString();
    }
}

