package com.scratchgame;

import com.scratchgame.core.GameConfig;
import com.scratchgame.core.GameEngine;
import com.scratchgame.core.GameResult;
import com.scratchgame.core.RewardCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RewardCalculatorTest {

    /**
     * Test case for the calculateReward method of the RewardCalculator class.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    void testCalculateReward() throws Exception {
        GameConfig config = GameEngine.loadConfig("src/main/resources/config.json");
        RewardCalculator calculator = new RewardCalculator(config);

        // Test case with a winning combination
        String[][] winningMatrix = {
                {"A", "A", "A"},
                {"B", "10x", "B"},
                {"C", "C", "C"}
        };

        GameResult result = calculator.calculateReward(winningMatrix, 100);
        assertTrue(result.getReward() > 0);
        assertNotNull(result.getAppliedWinningCombinations());
        assertEquals("10x", result.getAppliedBonusSymbol());

        // Test case with no winning combination
        String[][] losingMatrix = {
                {"A", "B", "C"},
                {"D", "E", "F"},
                {"A", "B", "C"}
        };
        GameResult losingResult = calculator.calculateReward(losingMatrix, 100);
        assertEquals(0, losingResult.getReward());
        assertNull(losingResult.getAppliedBonusSymbol());
    }
}