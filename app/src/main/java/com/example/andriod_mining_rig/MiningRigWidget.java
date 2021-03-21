package com.example.andriod_mining_rig;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MiningRigWidget extends AppWidgetProvider {
    static RemoteViews views;
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        widgetText = "This is a string";
        // Construct the RemoteViews object
        views = new RemoteViews(context.getPackageName(), R.layout.mining_rig_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

       // views.setTextViewText(R.id.active_workers, "69");
//        final TextView activeWorkersText = findViewById(R.id.active_workers);
//        final TextView currentHashrateText = findViewById(R.id.current_hashrate);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.ethermine.org/miner/53ce4cED03649deeB0588aD4b355d985888df95c/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EthermineAPI ethermineAPI = retrofit.create(EthermineAPI.class);
        Call<CurrentStats> call = ethermineAPI.getCurrentStatsData();

        call.enqueue(new Callback<CurrentStats>() {
            @Override
            public void onResponse(Call<CurrentStats> call, Response<CurrentStats> response) {
                if (!response.isSuccessful()) {
                    System.out.println(response.code());
                    return;
                }

                CurrentStats currentStats = response.body();
                CurrentStats.CurrentStatsData currentStatsData = currentStats.getCurrentStatsData();
                int activeWorkers = currentStatsData.getActiveWorkers();
                double parsedHashrate = currentStatsData.getCurrentHashrate() * 0.000001;
                double usdPerHr = currentStatsData.getUsdPerMin() * 60;
                double usdPerDay = currentStatsData.getUsdPerMin() * 60 * 24;
                double usdPerWeek = usdPerDay * 7;
                double usdPerMonth = usdPerDay * 30.4167;
                double usdPerSemiAnnual = usdPerMonth * 6;
                double usdPerYr = usdPerDay * 365;

                NumberFormat formatter = new DecimalFormat("#0.00");
                System.out.println("Active workers: " + currentStatsData.getActiveWorkers());
                System.out.println("Current Hashrate: " + formatter.format(parsedHashrate));
                System.out.println("usdPerHr: " + formatter.format(usdPerHr));
                System.out.println("usdPerDay: " + formatter.format(usdPerDay));
                System.out.println ("usdPerWeek: " + formatter.format(usdPerWeek));
                System.out.println ("usdSemi: " + formatter.format(usdPerSemiAnnual));
                System.out.println("usdPerMonth: " + formatter.format(usdPerMonth));
                System.out.println("usdPerYr: " + formatter.format(usdPerYr));

                updateTextViewUI(currentStatsData.getActiveWorkers());
            }

            @Override
            public void onFailure(Call<CurrentStats> call, Throwable t) {
                System.out.println("did not work");
                System.out.println(t.getMessage());
            }
        });

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateTextViewUI(int activeWorkers) {
        System.out.println ("activeWorkers: " + activeWorkers);
        views.setTextViewText(R.id.active_workers, activeWorkers + "");
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        System.out.println ("creating widget");
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        System.out.println ("removing widget");
    }
}

