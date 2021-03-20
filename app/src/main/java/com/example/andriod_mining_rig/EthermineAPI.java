package com.example.andriod_mining_rig;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EthermineAPI {
    @GET("currentStats")
    Call<CurrentStats> getCurrentStatsData();
}
