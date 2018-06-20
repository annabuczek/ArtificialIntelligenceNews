package com.example.android.artificialintelligencenews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by aania on 20.06.2018.
 */

public class ArticleAdapter extends ArrayAdapter<Article> {

    public ArticleAdapter(@NonNull Context context, @NonNull List<Article> articles) {
        super(context, 0, articles);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Article article = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView categoryTextView = convertView.findViewById(R.id.list_item_category_tv);
        categoryTextView.setText(article.getCategory());

        TextView titleTextView = convertView.findViewById(R.id.list_item_title_tv);
        titleTextView.setText(article.getTitle());

        TextView dateTextView = convertView.findViewById(R.id.list_item_date);
        dateTextView.setText(article.getDate());

        TextView authorTextView = convertView.findViewById(R.id.list_item_author);
        authorTextView.setText(article.getAuthor());
        return convertView;
    }
}
