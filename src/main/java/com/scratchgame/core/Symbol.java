package com.scratchgame.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Symbol {
    @JsonProperty("reward_multiplier")
    private double rewardMultiplier;

    @JsonProperty("type")
    private String type;

    @JsonProperty("extra")
    private double extra;

    @JsonProperty("impact")
    private String impact;

    // Getters and setters
    public double getRewardMultiplier() {
        return rewardMultiplier;
    }

    public void setRewardMultiplier(double rewardMultiplier) {
        this.rewardMultiplier = rewardMultiplier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getExtra() {
        return extra;
    }

    public void setExtra(double extra) {
        this.extra = extra;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }
}