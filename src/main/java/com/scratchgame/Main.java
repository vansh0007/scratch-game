package com.scratchgame;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scratchgame.core.GameConfig;
import com.scratchgame.core.GameEngine;
import com.scratchgame.core.GameResult;
import com.scratchgame.exceptions.GameException;
import com.scratchgame.output.Output;

import java.util.HashMap;
import java.util.Map;

public class Main {

    /**
     * The main function of the program. It parses the command line arguments, loads the game configuration,
     * plays the game, and prepares and prints the output.
     *
     * @param  args  the command line arguments
     */
    public static void main(String[] args) {
        try {
            // Parse command line arguments
            Map<String, String> options = parseOptions(args);
            String configPath = options.getOrDefault("--config", null);
            double bettingAmount = parseBettingAmount(options);

            // Load game configuration and play
            GameConfig config = GameEngine.loadConfig(configPath);
            GameEngine gameEngine = new GameEngine(config);
            GameResult result = gameEngine.play(bettingAmount);

            // Prepare and print output
            Output output = new Output(
                    result.getMatrix(),
                    result.getReward(),
                    result.getAppliedWinningCombinations(),
                    result.getAppliedBonusSymbol()
            );

            ObjectMapper mapper = new ObjectMapper();
            System.out.println(mapper.writeValueAsString(output));

        } catch (GameException e) {
            System.err.println("Game Error [" + e.getErrorCode() + "]: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Unexpected Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
    /**
     * Parses the command line arguments and returns a map of options.
     *
     * @param  args  the command line arguments
     * @return       a map of options, where the key is the option name and the value is the option value
     * @throws GameException if a missing value for an option is found
     */
    private static Map<String, String> parseOptions(String[] args) throws GameException {
        Map<String, String> options = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            String option = args[i];
            if (option.startsWith("--")) {
                if (i + 1 < args.length) {
                    options.put(option, args[i + 1]);
                    i++;
                } else {
                    throw new GameException(GameException.ErrorCode.INVALID_CONFIG,
                            "Missing value for option " + option);
                }
            }
        }
        return options;
    }

    /**
     * Parses the betting amount from the given options map.
     *
     * @param  options  a map of options, where the key is the option name and the value is the option value
     * @return          the parsed betting amount
     * @throws GameException if the betting amount is invalid or not provided
     */
    private static double parseBettingAmount(Map<String, String> options) throws GameException {
        double bettingAmount = 0;
        String bettingAmountStr = options.get("--betting-amount");
        if (bettingAmountStr != null) {
            try {
                bettingAmount = Double.parseDouble(bettingAmountStr);
                if (bettingAmount <= 0) {
                    throw new GameException(GameException.ErrorCode.INVALID_BET_AMOUNT,
                            "Betting amount must be positive");
                }
            } catch (NumberFormatException e) {
                throw new GameException(GameException.ErrorCode.INVALID_BET_AMOUNT,
                        "Invalid betting amount format", e);
            } catch (GameException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new GameException(GameException.ErrorCode.INVALID_CONFIG,
                    "Config file path not provided");
        }
        return bettingAmount;
    }
}