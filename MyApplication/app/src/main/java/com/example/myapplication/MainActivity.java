package com.example.myapplication;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private static final String CHANNEL_ID = "MyChannel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Кнопка для показа уведомления
        Button notificationButton = findViewById(R.id.notificationButton);
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNotification();
            }
        });

        // Текстовое поле и кнопка для поиска
        final EditText searchText = findViewById(R.id.searchText);
        Button searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchQuery = searchText.getText().toString();
                if (!searchQuery.isEmpty()) {
                    openBrowser(searchQuery);
                } else {
                    Toast.makeText(MainActivity.this, "Input text", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Элементы ListView для отображения новостей
        ListView listView = findViewById(R.id.listView);
       ArrayList<NewsItem> newsList = new ArrayList<>();
        newsList.add(new NewsItem(getString(R.string.news_title_1), getString(R.string.news_text_1)));
        newsList.add(new NewsItem(getString(R.string.news_title_2), getString(R.string.news_text_2)));
        newsList.add(new NewsItem(getString(R.string.news_title_3), getString(R.string.news_text_3)));
        newsList.add(new NewsItem(getString(R.string.news_title_4), getString(R.string.news_text_4)));


        NewsAdapter newsAdapter = new NewsAdapter(this, R.layout.news_item, newsList);
        listView.setAdapter(newsAdapter);

// Обработка нажатия на элемент ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Получите выбранную новость
                NewsItem selectedNews = (NewsItem) parent.getItemAtPosition(position);

                // Перейдите на новую активность, передавая данные о новости
                Intent intent = new Intent(MainActivity.this, NewsDetailActivity.class);
                intent.putExtra("title", selectedNews.getTitle());
                intent.putExtra("text", selectedNews.getText());
                startActivity(intent);
            }
        });

        // Дополнительные кнопки с функционалом

        Button openYoutube = findViewById(R.id.openYoutube);
        openYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ваш функционал для кнопки 2
                // Запуск YouTube приложения
                String youtubeVideoId = "YOUR_VIDEO_ID"; // Замените YOUR_VIDEO_ID на реальный идентификатор видео

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + youtubeVideoId));
                intent.putExtra("VIDEO_ID", youtubeVideoId);

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    // Если YouTube приложение не установлено, откройте видео в браузере
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?" + youtubeVideoId));
                    startActivity(intent);
                }
            }
        });

        Button exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ваш код для выхода из приложения
                finish();
            }
        });

        // Создание канала уведомлений (для Android 8.0 и выше)
        createNotificationChannel();
    }

    private void showNotification() {
        Notification.Builder builder;

        // Проверка версии Android для использования правильного конструктора Notification.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Notification.Builder(this, CHANNEL_ID);
        } else {
            builder = new Notification.Builder(this);
        }

        builder.setContentTitle("Notification")
                .setContentText("Notification example")
                .setSmallIcon(R.drawable.ic_launcher_foreground);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            // Важно установить канал перед вызовом notify()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel();  // Убедитесь, что канал существует
            }
            notificationManager.notify(1, builder.build());
        }
    }

    private void openBrowser(String searchQuery) {
        String url = "https://www.google.com/search?q=" + searchQuery;
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "MyChannel";
            String description = "Описание канала";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}
