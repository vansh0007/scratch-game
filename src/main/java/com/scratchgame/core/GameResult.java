package com.scratchgame.core;

import java.util.List;
import java.util.Map;

public class GameResult {
    private final String[][] matrix;
    private final double reward;
    private final Map<String, List<String>> appliedWinningCombinations;
    private final String appliedBonusSymbol;

    public GameResult(String[][] matrix, double reward,
                      Map<String, List<String>> appliedWinningCombinations,
                      String appliedBonusSymbol) {
        this.matrix = matrix;
        this.reward = reward;
        this.appliedWinningCombinations = appliedWinningCombinations;
        this.appliedBonusSymbol = appliedBonusSymbol;
    }

    public String[][] getMatrix() {
        return matrix;
    }

    public double getReward() {
        return reward;
    }

    public Map<String, List<String>> getAppliedWinningCombinations() {
        return appliedWinningCombinations;
    }

    public String getAppliedBonusSymbol() {
        return appliedBonusSymbol;
    }
}