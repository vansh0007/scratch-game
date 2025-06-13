package com.scratchgame.output;

import java.util.List;
import java.util.Map;

public class Output {
    private String[][] matrix;
    private double reward;
    private Map<String, List<String>> applied_winning_combinations;
    private String applied_bonus_symbol;

    public Output(String[][] matrix, double reward,
                  Map<String, List<String>> applied_winning_combinations,
                  String applied_bonus_symbol) {
        this.matrix = matrix;
        this.reward = reward;
        this.applied_winning_combinations = applied_winning_combinations;
        this.applied_bonus_symbol = applied_bonus_symbol;
    }

    // Getters for JSON serialization
    public String[][] getMatrix() {
        return matrix;
    }

    public double getReward() {
        return reward;
    }

    public Map<String, List<String>> getApplied_winning_combinations() {
        return applied_winning_combinations;
    }

    public String getApplied_bonus_symbol() {
        return applied_bonus_symbol;
    }
}