package com.example.android.artificialintelligencenews;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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

    private static String QUERY_URL_STRING = "http://content.guardianapis.com/search?q=artificial%20intelligence&show-fields=byline,thumbnail&format=json&api-key=***REMOVED***";
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
        ViewGroup footer = (ViewGroup)footerInflater.inflate(R.layout.list_view_footer, listView, false);
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
            getLoaderManager().initLoader(0, null, MainActivity.this);
        } else {
            progressBar.setVisibility(View.GONE);
            messageTextView.setText(getString(R.string.no_connection));
            refresh.setVisibility(View.VISIBLE);

            // Try to initialize Loader if there is internet connection after tapping 'refresh' button
            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isConnected()) {
                        getLoaderManager().initLoader(0, null, MainActivity.this);
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
                Log.i("MainActivity", "Click listener on footer");

                //If there is internet connection build new query string and restart Loader to load new data
                if (isConnected()) {
                    QUERY_URL_STRING = QueryUtils.buildNewQueryString(QUERY_URL_STRING);
                    getLoaderManager().restartLoader(0, null, MainActivity.this);
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
        return new ArticlesLoader(MainActivity.this, QUERY_URL_STRING);
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
     * @return true if there is internet connection or false if there isn't
     */
    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
}
