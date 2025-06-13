package com.scratchgame;

import com.scratchgame.core.GameConfig;
import com.scratchgame.core.GameEngine;
import com.scratchgame.core.MatrixGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatrixGeneratorTest {
    @Test
    void testGenerateMatrix() throws Exception {
        GameConfig config = GameEngine.loadConfig("src/main/resources/config.json");
        MatrixGenerator generator = new MatrixGenerator(config);

        String[][] matrix = generator.generateMatrix();

        assertEquals(3, matrix.length); // rows
        assertEquals(3, matrix[0].length); // columns

        // Check that all cells are filled
        for (String[] row : matrix) {
            for (String cell : row) {
                assertNotNull(cell);
            }
        }
    }
}