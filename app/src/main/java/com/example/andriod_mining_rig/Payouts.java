package com.example.andriod_mining_rig;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Payouts {
    @SerializedName("data")
    public List<CurrentPayoutData> currentPayoutsData;

    public List<CurrentPayoutData> getCurrentPayoutsData() { return currentPayoutsData; }

    public class CurrentPayoutData {
        @SerializedName("amount")
        private double amount;

        public double getAmount() { return amount; }
    }
}
