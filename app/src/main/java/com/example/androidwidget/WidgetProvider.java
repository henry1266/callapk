package com.example.androidwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.RemoteViews;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WidgetProvider extends AppWidgetProvider {

    private static final String ACTION_PLUS = "com.example.androidwidget.ACTION_PLUS";
    private static final String ACTION_MINUS = "com.example.androidwidget.ACTION_MINUS";
    private static final String ACTION_RESET = "com.example.androidwidget.ACTION_RESET";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        
        String action = intent.getAction();
        if (action != null) {
            if (ACTION_PLUS.equals(action)) {
                makeApiCall("http://192.168.68.90:5000/api/update-number?delta=1");
            } else if (ACTION_MINUS.equals(action)) {
                makeApiCall("http://192.168.68.90:5000/api/update-number?delta=-1");
            } else if (ACTION_RESET.equals(action)) {
                makeApiCall("http://192.168.68.90:5000/api/set-number?value=-1");
            }
        }
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        // 設置加一按鈕的點擊事件
        Intent intentPlus = new Intent(context, WidgetProvider.class);
        intentPlus.setAction(ACTION_PLUS);
        PendingIntent pendingIntentPlus = PendingIntent.getBroadcast(context, 0, intentPlus, PendingIntent.FLAG_IMMUTABLE);
        views.setOnClickPendingIntent(R.id.btn_plus, pendingIntentPlus);

        // 設置減一按鈕的點擊事件
        Intent intentMinus = new Intent(context, WidgetProvider.class);
        intentMinus.setAction(ACTION_MINUS);
        PendingIntent pendingIntentMinus = PendingIntent.getBroadcast(context, 1, intentMinus, PendingIntent.FLAG_IMMUTABLE);
        views.setOnClickPendingIntent(R.id.btn_minus, pendingIntentMinus);

        // 設置重設按鈕的點擊事件
        Intent intentReset = new Intent(context, WidgetProvider.class);
        intentReset.setAction(ACTION_RESET);
        PendingIntent pendingIntentReset = PendingIntent.getBroadcast(context, 2, intentReset, PendingIntent.FLAG_IMMUTABLE);
        views.setOnClickPendingIntent(R.id.btn_reset, pendingIntentReset);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private void makeApiCall(final String urlString) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                int responseCode = connection.getResponseCode();
                // 只是簡單地觸發 API，不需要處理回應
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
