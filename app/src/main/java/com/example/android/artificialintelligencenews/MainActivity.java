package com.example.android.artificialintelligencenews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List articles = new ArrayList<Article>();
        articles.add(new Article("technilogy", "title of technology article",
                "11.05.18", "by Somebody", "webUrl"));
        articles.add(new Article("technilogy", "title of technology article",
                "11.05.18", "by Somebody", "webUrl"));
        articles.add(new Article("technilogy", "title of technology article",
                "11.05.18", "by Somebody", "webUrl"));
        articles.add(new Article("technilogy", "title of technology article",
                "11.05.18", "by Somebody", "webUrl"));
        articles.add(new Article("technilogy", "title of technology article",
                "11.05.18", "by Somebody", "webUrl"));
        articles.add(new Article("technilogy", "title of technology article",
                "11.05.18", "by Somebody", "webUrl"));
        articles.add(new Article("technilogy", "title of technology article",
                "11.05.18", "by Somebody", "webUrl"));

        ListView listView = findViewById(R.id.list_view);
        ArticleAdapter adapter = new ArticleAdapter(MainActivity.this, articles);

        listView.setAdapter(adapter);
    }
}
