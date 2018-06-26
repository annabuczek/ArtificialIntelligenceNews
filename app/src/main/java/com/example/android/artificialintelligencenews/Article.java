package com.example.android.artificialintelligencenews;

import android.graphics.Bitmap;

public class Article {

    private String category;
    private String title;
    private String date;
    private String author;
    private String webUrl;
    private Bitmap image;

    public Article(String category, String title, String date, String author, String webUrl, Bitmap image) {
        this.category = category;
        this.title = title;
        this.date = date;
        this.author = author;
        this.webUrl = webUrl;
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public Bitmap getImage() {
        return image;
    }

}
