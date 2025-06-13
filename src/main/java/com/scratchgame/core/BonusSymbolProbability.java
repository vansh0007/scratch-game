package com.scratchgame.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class BonusSymbolProbability {
    @JsonProperty("symbols")
    private Map<String, Integer> symbols;

    public Map<String, Integer> getSymbols() {
        return symbols;
    }

    public void setSymbols(Map<String, Integer> symbols) {
        this.symbols = symbols;
    }
}