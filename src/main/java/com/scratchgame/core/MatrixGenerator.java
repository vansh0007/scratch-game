package com.scratchgame.core;

import com.scratchgame.exceptions.GameException;

import java.util.*;

public class MatrixGenerator {
    private final GameConfig config;
    private final Random random;

    public MatrixGenerator(GameConfig config) {
        this.config = config;
        this.random = new Random();
    }


    /**
     * Generates a matrix based on the game configuration.
     *
     * @return a 2D array representing the generated matrix
     * @throws GameException if there is an error generating the matrix
     */

    public String[][] generateMatrix() throws GameException {
        String[][] matrix = new String[config.getRows()][config.getColumns()];
        generateStandardSymbols(matrix);
        generateBonusSymbols(matrix);
        return matrix;
    }



    /**
     * Generates standard symbols in the given matrix based on the probability configuration.
     *
     * @param  matrix   the 2D array representing the matrix
     * @throws GameException if there is an invalid cell position in the probability configuration
     */
    private void generateStandardSymbols(String[][] matrix) throws GameException {
        for (StandardSymbolProbability prob : config.getProbabilities().getStandardSymbols()) {
            int row = prob.getRow();
            int col = prob.getColumn();
            if (row >= config.getRows() || col >= config.getColumns()) {
                throw new GameException(GameException.ErrorCode.INVALID_PROBABILITY,
                        String.format("Invalid cell position [%d,%d] in probability configuration", row, col));
            }
            matrix[row][col] = selectRandomSymbol(prob.getSymbols());
        }
    }

    /**
     * Generates bonus symbols in the given matrix based on the probability configuration.
     *
     * @param  matrix   the 2D array representing the matrix
     * @throws GameException if there is an invalid cell position in the probability configuration
     */
    private void generateBonusSymbols(String[][] matrix) throws GameException {
        Map<String, Integer> bonusSymbols = config.getProbabilities().getBonusSymbols().getSymbols();
        String bonusSymbol = selectRandomSymbol(bonusSymbols);

        // Place bonus symbol in a random cell
        int row = random.nextInt(config.getRows());
        int col = random.nextInt(config.getColumns());
        matrix[row][col] = bonusSymbol;
    }


    /**
     * Selects a random symbol from the given symbol weights map based on their corresponding weights.
     *
     * @param  symbolWeights  a map of symbols and their weights
     * @return                the randomly selected symbol
     * @throws GameException if the symbol weights map is null or empty, or if the total weight is not positive
     */
    private String selectRandomSymbol(Map<String, Integer> symbolWeights) throws GameException {
        if (symbolWeights == null || symbolWeights.isEmpty()) {
            throw new GameException(GameException.ErrorCode.INVALID_PROBABILITY,
                    "Symbol weights cannot be empty");
        }

        int totalWeight = symbolWeights.values().stream().mapToInt(Integer::intValue).sum();
        if (totalWeight <= 0) {
            throw new GameException(GameException.ErrorCode.INVALID_PROBABILITY,
                    "Total weight must be positive");
        }

        int randomValue = random.nextInt(totalWeight);
        int cumulativeWeight = 0;

        for (Map.Entry<String, Integer> entry : symbolWeights.entrySet()) {
            cumulativeWeight += entry.getValue();
            if (randomValue < cumulativeWeight) {
                return entry.getKey();
            }
        }

        throw new GameException(GameException.ErrorCode.UNEXPECTED_ERROR,
                "Failed to select random symbol");
    }
}