package com.example.myapplication;// NewsAdapter.java
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myapplication.NewsItem;
import com.example.myapplication.R;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<NewsItem> {

    private int resourceLayout;


    public NewsAdapter(Context context, int resource, ArrayList<NewsItem> newsList) {
        super(context, resource, newsList);
        this.resourceLayout = resource;
    }

    // NewsAdapter.java
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(resourceLayout, null);
        }

        NewsItem newsItem = getItem(position);

        if (newsItem != null) {
            TextView titleTextView = v.findViewById(R.id.detailTitleView);
            TextView textTextView = v.findViewById(R.id.detailTextView);

            if (titleTextView != null) {
                titleTextView.setText(newsItem.getTitle());
            }

            if (textTextView != null) {
                // Ограничиваем текст только на главной странице
                if (isOnMainActivity()) {
                    String limitedText = limitText(newsItem.getText(), 50);
                    textTextView.setText(limitedText);
                } else {
                    textTextView.setText(newsItem.getText());
                }
            }
        }

        return v;
    }

    // Метод для ограничения текста
    private String limitText(String text, int maxLength) {
        if (text.length() > maxLength) {
            return text.substring(0, maxLength - 3) + "...";
        } else {
            return text;
        }
    }

    // Метод для определения, находитесь ли вы на главной странице
    private boolean isOnMainActivity() {
        return true;
    }

}
