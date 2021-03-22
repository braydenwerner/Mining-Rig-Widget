package com.example.andriod_mining_rig;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MiningRigWidget extends AppWidgetProvider {
    static RemoteViews views;

    static void updateAppWidget(Context context, final AppWidgetManager appWidgetManager,
                                final int appWidgetId) {
        views = new RemoteViews(context.getPackageName(), R.layout.mining_rig_widget);

        String timeString =
                DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date());
        views.setTextViewText(R.id.last_updated, "Last Updated At: " + timeString);

        //  on click update
        Intent intentUpdate = new Intent(context, MiningRigWidget.class);
        intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] idArray = new int[]{appWidgetId};
        intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray);
        PendingIntent pendingUpdate = PendingIntent.getBroadcast(
                context, appWidgetId, intentUpdate,
                PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.tap_update, pendingUpdate);

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
                double parsedCurrentHashrate = currentStatsData.getReportedHashrate() * 0.000001;
                double parsedAverageHashrate = currentStatsData.getAverageHashrate() * 0.000001;
                double usdPerHr = currentStatsData.getUsdPerMin() * 60;
                double usdPerDay = currentStatsData.getUsdPerMin() * 60 * 24;
                double usdPerWeek = usdPerDay * 7;
                double usdPerMonth = usdPerDay * 30.4167;
                double usdPerSemiAnnual = usdPerMonth * 6;
                double usdPerYr = usdPerDay * 365;

                NumberFormat formatter = new DecimalFormat("#0.00");
//                System.out.println("usdPerHr: " + formatter.format(usdPerHr));
//                System.out.println("usdPerDay: " + formatter.format(usdPerDay));
//                System.out.println("usdPerWeek: " + formatter.format(usdPerWeek));
//                System.out.println("usdSemi: " + formatter.format(usdPerSemiAnnual));
//                System.out.println("usdPerMonth: " + formatter.format(usdPerMonth));
//                System.out.println("usdPerYr: " + formatter.format(usdPerYr));

                updateTextViewUI(currentStatsData.getActiveWorkers(),
                        formatter.format(parsedCurrentHashrate),
                        formatter.format(parsedAverageHashrate));
                appWidgetManager.updateAppWidget(appWidgetId, views);
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

    public static void updateTextViewUI(int activeWorkers,
                                        String currentHashrate,
                                        String averageHashrate) {
        views.setTextViewText(R.id.active_workers, activeWorkers + "");
        views.setTextViewText(R.id.current_hashrate, currentHashrate + " MH/s");
        views.setTextViewText(R.id.average_hashrate, averageHashrate + " MH/s");
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        System.out.println("onUpdate");
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
            Toast.makeText(context, "Widget has been updated! ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onEnabled(Context context) {
        System.out.println("creating widget");
    }

    @Override
    public void onDisabled(Context context) {
        System.out.println("removing widget");
    }
}

