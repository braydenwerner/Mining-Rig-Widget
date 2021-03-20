package com.example.andriod_mining_rig;

import com.google.gson.annotations.SerializedName;

public class CurrentStats {
    @SerializedName("data")
    public CurrentStatsData currentStatsData;

    public CurrentStatsData getCurrentStatsData() {
        return currentStatsData;
    }

    public class CurrentStatsData {
        @SerializedName("time")
        private double time;
        @SerializedName("currentHashrate")
        private double currentHashrate;
        @SerializedName("validShares")
        private double validShares;
        @SerializedName("invalidShares")
        private double invalidShares;
        @SerializedName("staleShares")
        private double staleShares;
        @SerializedName("averageHashrate")
        private double averageHashrate;
        @SerializedName("activeWorkers")
        private int activeWorkers;
        @SerializedName("unpaid")
        private double unpaid;
        @SerializedName("coinsPerMin")
        private double coinsPerMin;
        @SerializedName("usdPerMin")
        private double usdPerMin;
        @SerializedName("btcPerMin")
        private double btcPerMin;

        public double getTime() {
            return time;
        }

        public double getCurrentHashrate() {
            return currentHashrate;
        }

        public double getValidShares() {
            return validShares;
        }

        public double getInvalidShares() {
            return invalidShares;
        }

        public double getStaleShares() {
            return staleShares;
        }

        public double getAverageHashrate() {
            return averageHashrate;
        }

        public int getActiveWorkers() {
            return activeWorkers;
        }

        public double getUnpaid() {
            return unpaid;
        }

        public double getCoinsPerMin() {
            return coinsPerMin;
        }

        public double getUsdPerMin() {
            return usdPerMin;
        }

        public double getBtcPerMin() {
            return btcPerMin;
        }
    }
}
