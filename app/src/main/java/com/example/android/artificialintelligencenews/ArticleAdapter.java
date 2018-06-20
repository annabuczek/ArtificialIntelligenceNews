package com.example.android.artificialintelligencenews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by aania on 20.06.2018.
 */

public class ArticleAdapter extends ArrayAdapter<Article> {

    private static final String CURRENT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final String NEW_DATE_FORMAT = "d MMM yyyy";

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
        dateTextView.setText(formatDateTime(article.getDate()));

        TextView authorTextView = convertView.findViewById(R.id.list_item_author);
        authorTextView.setText(article.getAuthor());
        return convertView;
    }

    /**
     * Helper method to format date and time
     * @param datetime String with date and time
     * @return properly formatted time
     */
    private String formatDateTime (String datetime) {
        // Create new SimpleDateFormat the same as received String datetime
        SimpleDateFormat currentDateFormat = new SimpleDateFormat(CURRENT_DATE_FORMAT);
        //Initialize final String
        String formattedDate = "";

        try {
            // Parse received datetime String into Date format
            Date date = currentDateFormat.parse(datetime);
            // Create new format you want date to be displayed in
            SimpleDateFormat newFormat = new SimpleDateFormat(NEW_DATE_FORMAT);
            // Format Date object to get formattedDate String
            formattedDate = newFormat.format(date);
        } catch (ParseException e) {
            Log.e("ArticleAdapter", "Problem parsing datatime Sring", e);
        }
        return formattedDate;


    }
}
