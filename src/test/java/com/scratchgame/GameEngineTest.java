package com.scratchgame;

import com.scratchgame.core.GameConfig;
import com.scratchgame.core.GameEngine;
import com.scratchgame.core.GameResult;
import com.scratchgame.core.RewardCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameEngineTest {
    @Test
    void testPlay() throws Exception {
        GameConfig config = GameEngine.loadConfig("src/main/resources/config.json");
        GameEngine engine = new GameEngine(config);

       GameResult result = engine.play(100);

        assertNotNull(result.getMatrix());
        assertNotNull(result.getAppliedWinningCombinations());

        // The reward could be 0 or positive depending on the generated matrix
        assertTrue(result.getReward() >= 0);
    }
}