// NewsDetailActivity.java
package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NewsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_item);

        // Получаем данные о новости из Intent
        String title = getIntent().getStringExtra("title");
        String text = getIntent().getStringExtra("text");

        // Находим TextView в макете NewsDetailActivity и устанавливаем текст
        TextView titleTextView = findViewById(R.id.detailTitleView);
        TextView textTextView = findViewById(R.id.detailTextView);

        titleTextView.setText(title);
        textTextView.setText(text);


    }
}

