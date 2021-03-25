package com.example.andriod_mining_rig;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CoinGeckoAPI {
    @GET("markets?vs_currency=usd&ids=ethereum")
    Call<EthereumPrice> getCurrentEthereumPrice();
}
