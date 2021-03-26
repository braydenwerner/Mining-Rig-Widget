package com.example.andriod_mining_rig;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EthermineAPI {
    @GET("miner/53ce4cED03649deeB0588aD4b355d985888df95c/currentStats")
    Call<CurrentStats> getCurrentStatsData();

    @GET("miner/53ce4cED03649deeB0588aD4b355d985888df95c/payouts")
    Call<Payouts> getCurrentPayoutsData();
}
