package com.scratchgame.core;

import com.scratchgame.exceptions.GameException;

import java.util.*;

public class RewardCalculator {
    private final GameConfig config;

    public RewardCalculator(GameConfig config) {
        this.config = config;
    }
    /**
     * Calculates the reward for a given matrix and bet amount.
     *
     * @param  matrix   the matrix representing the game board
     * @param  betAmount the amount bet by the player
     * @return          the result of the game, including the reward and applied winning combinations
     * @throws GameException if the matrix is null or empty, or if the bet amount is non-positive
     */
    public GameResult calculateReward(String[][] matrix, double betAmount) throws GameException {
        if (matrix == null || matrix.length == 0) {
            throw new GameException(GameException.ErrorCode.INVALID_MATRIX,
                    "Matrix cannot be null or empty");
        }

        if (betAmount <= 0) {
            throw new GameException(GameException.ErrorCode.INVALID_BET_AMOUNT,
                    "Bet amount must be positive");
        }

        return getGameResult(matrix, betAmount);
    }
    /**
     * Calculates the game result for a given matrix and bet amount.
     *
     * @param  matrix   the matrix representing the game board
     * @param  betAmount the amount bet by the player
     * @return          the result of the game, including the reward and applied winning combinations
     * @throws GameException if the matrix is null or empty, or if the bet amount is non-positive,
     *                       or if an unknown symbol is encountered, or if an error occurs during calculation
     */
    private GameResult getGameResult(String[][] matrix, double betAmount) throws GameException {
        try {
            Map<String, List<String>> appliedWinningCombinations = new HashMap<>();
            Map<String, Integer> symbolCounts = countSymbols(matrix);
            double reward = 0;
            String appliedBonusSymbol = null;

            // Check for standard symbol wins
            for (Map.Entry<String, Integer> entry : symbolCounts.entrySet()) {
                String symbol = entry.getKey();
                int count = entry.getValue();

                Symbol symbolConfig = config.getSymbols().get(symbol);
                if (symbolConfig == null) {
                    throw new GameException(GameException.ErrorCode.INVALID_SYMBOL,
                            "Unknown symbol: " + symbol);
                }

                // Skip bonus symbols for standard win checks
                if ("bonus".equals(symbolConfig.getType())) {
                    appliedBonusSymbol = symbol;
                    continue;
                }

                List<String> winningCombinations = new ArrayList<>();
                double symbolReward = 0;

                // Check all win combinations for this symbol
                for (Map.Entry<String, WinCombination> wcEntry : config.getWinCombinations().entrySet()) {
                    String wcName = wcEntry.getKey();
                    WinCombination wc = wcEntry.getValue();

                    if (matchesWinCombination(symbol, count, wc, matrix)) {
                        winningCombinations.add(wcName);
                        symbolReward = calculateSymbolReward(symbol, symbolReward, wc, betAmount);
                    }
                }

                if (!winningCombinations.isEmpty()) {
                    appliedWinningCombinations.put(symbol, winningCombinations);
                    reward += symbolReward;
                }
            }

            // Apply bonus only if there's a win
            if (reward > 0 && appliedBonusSymbol != null) {
                Symbol bonusSymbol = config.getSymbols().get(appliedBonusSymbol);
                if ("multiply_reward".equals(bonusSymbol.getImpact())) {
                    reward *= bonusSymbol.getRewardMultiplier();
                } else if ("extra_bonus".equals(bonusSymbol.getImpact())) {
                    reward += bonusSymbol.getExtra();
                }
                // "miss" does nothing
            } else {
                appliedBonusSymbol = null;
            }

            return new GameResult(matrix, reward, appliedWinningCombinations, appliedBonusSymbol);
        } catch (Exception e) {
            throw new GameException(GameException.ErrorCode.UNEXPECTED_ERROR,
                    "Error calculating reward", e);
        }
    }
    /**
     * Checks if the given symbol matches the win combination based on the count and win combination parameters.
     *
     * @param  symbol   the symbol to check
     * @param  count    the count of the symbol
     * @param  wc       the win combination to check against
     * @param  matrix   the matrix to check for linear combinations
     * @return          true if the symbol matches the win combination, false otherwise
     */
    private boolean matchesWinCombination(String symbol, int count, WinCombination wc, String[][] matrix) {
        if ("same_symbols".equals(wc.getWhen())) {
            return count >= wc.getCount();
        } else if ("linear_symbols".equals(wc.getWhen())) {
            return checkLinearCombinations(symbol, wc, matrix);
        }
        return false;
    }
    /**
     * Checks if the given symbol matches all the cells in the linear combination areas of the matrix.
     *
     * @param  symbol   the symbol to check
     * @param  wc       the win combination containing the linear combination areas
     * @param  matrix   the matrix to check for linear combinations
     * @return          true if the symbol matches all the cells in the linear combination areas, false otherwise
     */
    private boolean checkLinearCombinations(String symbol, WinCombination wc, String[][] matrix) {
        for (List<String> area : wc.getCoveredAreas()) {
            boolean allMatch = true;
            for (String cell : area) {
                String[] parts = cell.split(":");
                int row = Integer.parseInt(parts[0]);
                int col = Integer.parseInt(parts[1]);
                if (!symbol.equals(matrix[row][col])) {
                    allMatch = false;
                    break;
                }
            }
            if (allMatch) {
                return true;
            }
        }
        return false;
    }
    /**
     * Calculates the reward for a given symbol based on the current reward, win combination, and bet amount.
     *
     * @param  symbol       the symbol for which the reward is calculated
     * @param  currentReward the current reward for the symbol
     * @param  wc           the win combination containing the reward multiplier
     * @param  betAmount    the bet amount for the game
     * @return              the calculated reward for the symbol
     */
    private double calculateSymbolReward(String symbol, double currentReward, WinCombination wc, double betAmount) {
        double symbolMultiplier = config.getSymbols().get(symbol).getRewardMultiplier();
        if (currentReward == 0) {
            return betAmount * symbolMultiplier * wc.getRewardMultiplier();
        }
        return currentReward * wc.getRewardMultiplier();
    }
    /**
     * Counts the occurrences of each symbol in the given matrix.
     *
     * @param  matrix   the matrix containing symbols
     * @return          a map with symbols as keys and their counts as values
     */
    private Map<String, Integer> countSymbols(String[][] matrix) {
        Map<String, Integer> counts = new HashMap<>();
        for (String[] row : matrix) {
            for (String symbol : row) {
                counts.put(symbol, counts.getOrDefault(symbol, 0) + 1);
            }
        }
        return counts;
    }
}