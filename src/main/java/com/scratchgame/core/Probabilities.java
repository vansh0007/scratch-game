package com.scratchgame.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scratchgame.core.StandardSymbolProbability;

public class Probabilities {
    @JsonProperty("standard_symbols")
    private StandardSymbolProbability[] standardSymbols;

    @JsonProperty("bonus_symbols")
    private BonusSymbolProbability bonusSymbols;

    public StandardSymbolProbability[] getStandardSymbols() {
        return standardSymbols;
    }

    public void setStandardSymbols(StandardSymbolProbability[] standardSymbols) {
        this.standardSymbols = standardSymbols;
    }

    public BonusSymbolProbability getBonusSymbols() {
        return bonusSymbols;
    }

    public void setBonusSymbols(BonusSymbolProbability bonusSymbols) {
        this.bonusSymbols = bonusSymbols;
    }
}
