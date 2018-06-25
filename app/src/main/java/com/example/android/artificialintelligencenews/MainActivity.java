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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>> {

    //    private final static String jsonResponse = "{\"response\":{\"status\":\"ok\",\"userTier\":\"developer\",\"total\":20077,\"startIndex\":1,\"pageSize\":10,\"currentPage\":1,\"pages\":2008,\"orderBy\":\"relevance\",\"results\":[{\"id\":\"technology/2018/apr/19/artificial-intelligence-robots-and-a-human-touch\",\"type\":\"article\",\"sectionId\":\"technology\",\"sectionName\":\"Technology\",\"webPublicationDate\":\"2018-04-19T17:17:30Z\",\"webTitle\":\"Artificial intelligence, robots and a human touch | Letters\",\"webUrl\":\"https://www.theguardian.com/technology/2018/apr/19/artificial-intelligence-robots-and-a-human-touch\",\"apiUrl\":\"https://content.guardianapis.com/technology/2018/apr/19/artificial-intelligence-robots-and-a-human-touch\",\"fields\":{\"byline\":\"Letters\",\"thumbnail\":\"https://media.guim.co.uk/f17c375a2899cd250bfe9d67070025882c3ad494/0_384_5760_3456/500.jpg\"},\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"technology/2018/apr/25/european-commission-ai-artificial-intelligence\",\"type\":\"article\",\"sectionId\":\"technology\",\"sectionName\":\"Technology\",\"webPublicationDate\":\"2018-04-25T13:59:29Z\",\"webTitle\":\"Artificial intelligence: €20bn investment call from EU commission\",\"webUrl\":\"https://www.theguardian.com/technology/2018/apr/25/european-commission-ai-artificial-intelligence\",\"apiUrl\":\"https://content.guardianapis.com/technology/2018/apr/25/european-commission-ai-artificial-intelligence\",\"fields\":{\"byline\":\"Jennifer Rankin in Brussels\",\"thumbnail\":\"https://media.guim.co.uk/da8f70cca6dd9de6a2e0632fe04108d6bf980319/0_604_4096_2457/500.jpg\"},\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"commentisfree/2018/apr/16/the-guardian-view-on-artificial-intelligence-not-a-technological-problem\",\"type\":\"article\",\"sectionId\":\"commentisfree\",\"sectionName\":\"Opinion\",\"webPublicationDate\":\"2018-04-16T17:30:51Z\",\"webTitle\":\"The Guardian view on artificial intelligence: not a technological problem | Editorial\",\"webUrl\":\"https://www.theguardian.com/commentisfree/2018/apr/16/the-guardian-view-on-artificial-intelligence-not-a-technological-problem\",\"apiUrl\":\"https://content.guardianapis.com/commentisfree/2018/apr/16/the-guardian-view-on-artificial-intelligence-not-a-technological-problem\",\"fields\":{\"byline\":\"Editorial\",\"thumbnail\":\"https://media.guim.co.uk/33c9e0288564a7686fa30700b4d28210f1283553/0_154_4950_2970/500.jpg\"},\"isHosted\":false,\"pillarId\":\"pillar/opinion\",\"pillarName\":\"Opinion\"},{\"id\":\"stage/2018/may/16/artificial-intelligence-it-may-be-our-future-but-can-it-write-a-play\",\"type\":\"article\",\"sectionId\":\"stage\",\"sectionName\":\"Stage\",\"webPublicationDate\":\"2018-05-16T06:26:04Z\",\"webTitle\":\"Artificial intelligence: it may be our future but can it write a play?\",\"webUrl\":\"https://www.theguardian.com/stage/2018/may/16/artificial-intelligence-it-may-be-our-future-but-can-it-write-a-play\",\"apiUrl\":\"https://content.guardianapis.com/stage/2018/may/16/artificial-intelligence-it-may-be-our-future-but-can-it-write-a-play\",\"fields\":{\"byline\":\"Jane Howard\",\"thumbnail\":\"https://media.guim.co.uk/5a7d2fde347095d07df11688a9632cb836efa3ec/0_851_3840_2304/500.jpg\"},\"isHosted\":false,\"pillarId\":\"pillar/arts\",\"pillarName\":\"Arts\"},{\"id\":\"science/political-science/2017/oct/09/the-real-risks-of-artificial-intelligence\",\"type\":\"article\",\"sectionId\":\"science\",\"sectionName\":\"Science\",\"webPublicationDate\":\"2017-10-09T05:00:10Z\",\"webTitle\":\"The real risks of artificial intelligence\",\"webUrl\":\"https://www.theguardian.com/science/political-science/2017/oct/09/the-real-risks-of-artificial-intelligence\",\"apiUrl\":\"https://content.guardianapis.com/science/political-science/2017/oct/09/the-real-risks-of-artificial-intelligence\",\"fields\":{\"byline\":\"Jack Stilgoe\",\"thumbnail\":\"https://media.guim.co.uk/93c1677b712d6798fabacf1d7e9e534477359597/0_49_1162_697/500.jpg\"},\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"sport/blog/2017/nov/04/ai-judges-gymnastics-olympics\",\"type\":\"article\",\"sectionId\":\"sport\",\"sectionName\":\"Sport\",\"webPublicationDate\":\"2017-11-04T13:03:24Z\",\"webTitle\":\"Cracking the vault: Artificial intelligence judging comes to gymnastics\",\"webUrl\":\"https://www.theguardian.com/sport/blog/2017/nov/04/ai-judges-gymnastics-olympics\",\"apiUrl\":\"https://content.guardianapis.com/sport/blog/2017/nov/04/ai-judges-gymnastics-olympics\",\"fields\":{\"byline\":\"Paul Logothetis\",\"thumbnail\":\"https://media.guim.co.uk/7e8bc4877aa7d47f27ccaea78a2d211bd4557767/0_0_5568_3341/500.jpg\"},\"isHosted\":false,\"pillarId\":\"pillar/sport\",\"pillarName\":\"Sport\"},{\"id\":\"science/2017/nov/01/artificial-intelligence-risks-gm-style-public-backlash-experts-warn\",\"type\":\"article\",\"sectionId\":\"science\",\"sectionName\":\"Science\",\"webPublicationDate\":\"2017-11-01T10:30:14Z\",\"webTitle\":\"Artificial intelligence risks GM-style public backlash, experts warn\",\"webUrl\":\"https://www.theguardian.com/science/2017/nov/01/artificial-intelligence-risks-gm-style-public-backlash-experts-warn\",\"apiUrl\":\"https://content.guardianapis.com/science/2017/nov/01/artificial-intelligence-risks-gm-style-public-backlash-experts-warn\",\"fields\":{\"byline\":\"Ian Sample  Science editor\",\"thumbnail\":\"https://media.guim.co.uk/c1a4842ef290e844d22552c2daacc394e93c63dd/0_49_5364_3218/500.jpg\"},\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"lifeandstyle/2017/oct/15/brain-game-the-freaky-factor-of-artificial-intelligence\",\"type\":\"article\",\"sectionId\":\"lifeandstyle\",\"sectionName\":\"Life and style\",\"webPublicationDate\":\"2017-10-15T05:00:21Z\",\"webTitle\":\"Brain game: the freaky factor of artificial intelligence | Daniel Glaser\",\"webUrl\":\"https://www.theguardian.com/lifeandstyle/2017/oct/15/brain-game-the-freaky-factor-of-artificial-intelligence\",\"apiUrl\":\"https://content.guardianapis.com/lifeandstyle/2017/oct/15/brain-game-the-freaky-factor-of-artificial-intelligence\",\"fields\":{\"byline\":\"Daniel Glaser\",\"thumbnail\":\"https://media.guim.co.uk/8f130174143039fb4c33f60985aac21bcbe5ada5/0_320_8000_4800/500.jpg\"},\"isHosted\":false,\"pillarId\":\"pillar/lifestyle\",\"pillarName\":\"Lifestyle\"},{\"id\":\"technology/2017/oct/20/cbi-calls-for-commission-impact-artificial-intelligence-jobs-business-productivity-growth\",\"type\":\"article\",\"sectionId\":\"technology\",\"sectionName\":\"Technology\",\"webPublicationDate\":\"2017-10-20T05:01:45Z\",\"webTitle\":\"Artificial intelligence commission needed to predict impact, says CBI\",\"webUrl\":\"https://www.theguardian.com/technology/2017/oct/20/cbi-calls-for-commission-impact-artificial-intelligence-jobs-business-productivity-growth\",\"apiUrl\":\"https://content.guardianapis.com/technology/2017/oct/20/cbi-calls-for-commission-impact-artificial-intelligence-jobs-business-productivity-growth\",\"fields\":{\"byline\":\"Richard Partington\",\"thumbnail\":\"https://media.guim.co.uk/ecf735a9bc4b224687ea7132b9f7d0f3b9bfee73/0_1071_4043_2426/500.jpg\"},\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"technology/2018/jun/10/artificial-intelligence-cancer-detectors-the-five\",\"type\":\"article\",\"sectionId\":\"technology\",\"sectionName\":\"Technology\",\"webPublicationDate\":\"2018-06-10T05:59:43Z\",\"webTitle\":\"AI cancer detectors\",\"webUrl\":\"https://www.theguardian.com/technology/2018/jun/10/artificial-intelligence-cancer-detectors-the-five\",\"apiUrl\":\"https://content.guardianapis.com/technology/2018/jun/10/artificial-intelligence-cancer-detectors-the-five\",\"fields\":{\"byline\":\"Ian Tucker\",\"thumbnail\":\"https://media.guim.co.uk/4b271cda12ba594a6f59905f8cf156eda3f8e23b/428_352_4139_2483/500.jpg\"},\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"}]}}";
    private static final String QUERY_URL_STRING = "http://content.guardianapis.com/search?q=artificial%20intelligence&show-fields=byline,thumbnail&format=json&api-key=***REMOVED***";
    private ArticleAdapter mAdapter;
    private ProgressBar progressBar;
    private TextView messageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.list_view);
        progressBar = findViewById(R.id.progress_bar);
        messageTextView = findViewById(R.id.message_text_view);

        listView.setEmptyView(messageTextView);
        mAdapter = new ArticleAdapter(MainActivity.this, new ArrayList<Article>());
        listView.setAdapter(mAdapter);

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

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            getLoaderManager().initLoader(0, null, MainActivity.this);
        } else {
            progressBar.setVisibility(View.GONE);
            messageTextView.setText("No internet connection");
        }


    }

    @Override
    public Loader<List<Article>> onCreateLoader(int id, Bundle args) {

        return new ArticlesLoader(MainActivity.this, QUERY_URL_STRING);
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> data) {
        mAdapter.clear();

        progressBar.setVisibility(View.GONE);

        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }

        messageTextView.setText("No articles found");
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
        mAdapter.clear();
        mAdapter.addAll((new ArrayList<Article>()));
    }
}
