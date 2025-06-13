package com.scratchgame.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scratchgame.exceptions.GameException;

import java.io.File;
import java.io.IOException;

public class GameEngine {
    private final GameConfig config;
    private final MatrixGenerator matrixGenerator;
    private final RewardCalculator rewardCalculator;

    public GameEngine(GameConfig config) {
        this.config = config;
        this.matrixGenerator = new MatrixGenerator(config);
        this.rewardCalculator = new RewardCalculator(config);
    }

    public GameResult play(double betAmount) throws GameException {
        String[][] matrix = matrixGenerator.generateMatrix();
        return rewardCalculator.calculateReward(matrix, betAmount);
    }

    public static GameConfig loadConfig(String configPath) throws GameException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            GameConfig config = mapper.readValue(new File(configPath), GameConfig.class);
            validateConfig(config);
            return config;
        } catch (IOException e) {
            throw new GameException(GameException.ErrorCode.FILE_READ_ERROR,
                    "Failed to read config file: " + configPath, e);
        } catch (Exception e) {
            throw new GameException(GameException.ErrorCode.JSON_PARSE_ERROR,
                    "Failed to parse config file", e);
        }
    }

    /**
     * Validates the configuration of the game.
     *
     * @param config The game configuration to validate.
     * @throws GameException If the configuration is invalid.
     */
    private static void validateConfig(GameConfig config) throws GameException {
        // Check if the configuration is null
        if (config == null) {
            throw new GameException(GameException.ErrorCode.INVALID_CONFIG,
                    "Config is null");
        }

        // Check if the symbols configuration is missing
        if (config.getSymbols() == null || config.getSymbols().isEmpty()) {
            throw new GameException(GameException.ErrorCode.MISSING_REQUIRED_FIELD,
                    "Symbols configuration is missing");
        }

        // Check if the probabilities configuration is missing
        if (config.getProbabilities() == null) {
            throw new GameException(GameException.ErrorCode.MISSING_REQUIRED_FIELD,
                    "Probabilities configuration is missing");
        }

        // Check if the win combinations configuration is missing
        if (config.getWinCombinations() == null || config.getWinCombinations().isEmpty()) {
            throw new GameException(GameException.ErrorCode.MISSING_REQUIRED_FIELD,
                    "Win combinations configuration is missing");
        }

        // Validate the probabilities
        Probabilities probabilities = config.getProbabilities();

        // Check if the standard symbols probabilities are missing
        if (probabilities.getStandardSymbols() == null ||
                probabilities.getStandardSymbols().length == 0) {
            throw new GameException(GameException.ErrorCode.INVALID_PROBABILITY,
                    "Standard symbols probabilities are missing");
        }

        // Check if the bonus symbols probabilities are missing
        BonusSymbolProbability bonusSymbols = probabilities.getBonusSymbols();
        if (bonusSymbols == null || bonusSymbols.getSymbols() == null) {
            throw new GameException(GameException.ErrorCode.INVALID_PROBABILITY,
                    "Bonus symbols probabilities are missing");
        }
    }
}