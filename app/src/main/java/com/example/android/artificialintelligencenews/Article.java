package com.example.android.artificialintelligencenews;

/**
 * Created by aania on 20.06.2018.
 */

public class Article {

    private String category;
    private String title;
    private String date;
    private String author;
    private String webUrl;

    public Article(String category, String title, String date, String author, String webUrl) {
        this.category = category;
        this.title = title;
        this.date = date;
        this.author = author;
        this.webUrl = webUrl;
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
}
