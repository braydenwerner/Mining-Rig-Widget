package com.example.andriod_mining_rig;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.active_workers_header);
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

                NumberFormat formatter = new DecimalFormat("#0.00");
                double parsedHashrate = currentStatsData.getCurrentHashrate() * 0.000001;
                double usdPerHr = currentStatsData.getUsdPerMin() * 60;
                double usdPerDay = currentStatsData.getUsdPerMin() * 60 * 24;
                double usdPerWeek = usdPerDay * 7;
                double usdPerMonth = usdPerDay * 30.4167;
                double usdPerSemiAnnual = usdPerMonth * 6;
                double usdPerYr = usdPerDay * 365;

                System.out.println("Active workers: " + currentStatsData.getActiveWorkers());
                System.out.println("Current Hashrate: " + formatter.format(parsedHashrate));
                System.out.println("usdPerHr: " + formatter.format(usdPerHr));
                System.out.println("usdPerDay: " + formatter.format(usdPerDay));
                System.out.println ("usdPerWeek: " + formatter.format(usdPerWeek));
                System.out.println ("usdSemi: " + formatter.format(usdPerSemiAnnual));
                System.out.println("usdPerMonth: " + formatter.format(usdPerMonth));
                System.out.println("usdPerYr: " + formatter.format(usdPerYr));

                System.out.println(response.toString());
            }

            @Override
            public void onFailure(Call<CurrentStats> call, Throwable t) {
                System.out.println("did not work");
                System.out.println(t.getMessage());
            }
        });
    }
}