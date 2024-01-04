// NewsItem.java
package com.example.myapplication;

import java.io.Serializable;

public class NewsItem implements Serializable {
    private String title;
    private String text;

    public NewsItem(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}
