package com.example.android.artificialintelligencenews;

import android.graphics.Bitmap;

/**
 * Created by aania on 20.06.2018.
 */

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

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
