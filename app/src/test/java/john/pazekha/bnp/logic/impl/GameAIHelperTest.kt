package john.pazekha.bnp.logic.impl

import john.pazekha.bnp.model.Position
import john.pazekha.bnp.model.SYMBOL
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import junitparams.converters.Param
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(JUnitParamsRunner::class)
class GameAIHelperTest {

    private lateinit var gameAiHelper: GameAIHelper

    @Before
    fun before() {
        gameAiHelper = GameAIHelper()
    }


    @Parameters(
        // board, symbol, expectedRow, expectedCol

        // Start game
        // --------------------
        ". . ." +
        ". . ." +
        ". . ., CROSS, 0, 0",
        // --------------------
        ". . ." +
        ". X ." +
        ". . ., DONUT, 0, 0",
        // --------------------
        ". . X" +
        ". . ." +
        ". . ., DONUT, 1, 1",
        // --------------------
        ". . ." +
        ". . X" +
        ". . ., DONUT, 0, 2",

        // Win next move
        // --------------------
        "O . X" +
        "X . ." +
        "X O O, CROSS, 1, 1",
        // --------------------
        "O . X" +
        "X . X" +
        "X O O, DONUT, 1, 1",
        // --------------------
        "O O X" +
        "X . X" +
        "X O O, CROSS, 1, 1",
        // --------------------
        "X . O" +
        ". X O" +
        ". . ., CROSS, 2, 2",
        // --------------------
        ". X O" +
        ". X O" +
        ". . ., CROSS, 2, 1",
        // --------------------
        ". X O" +
        ". X O" +
        ". . ., DONUT, 2, 2",

        // Win in two moves
        // --------------------
        "O X X" +
        "X O ." +
        "X . ., CROSS, 2, 2",

        // Draw in two moves
        // --------------------
        "O X X" +
        "X . ." +
        "X . ., DONUT, 1, 1",
        // --------------------
        "X X O" +
        "O O X" +
        ". . ., CROSS, 2, 0",
        // --------------------
        "X X O" +
        "O . X" +
        ". . ., DONUT, 1, 1"
        // --------------------
    )
    @Test
    fun calculateMoveFor(
        @Param(converter = BoardConverter::class) board: Array<Array<SYMBOL>>,
        symbol: SYMBOL,
        expectedRow: Int,
        expectedCol: Int
    ) {
        /* ******** GIVEN ********* */
        val expected = Position(expectedRow, expectedCol)

        /* ********  WHEN ********* */
        val result = gameAiHelper.calculateMoveFor(board, symbol)

        /* ********  THEN ********* */
        assertEquals(expected, result)
    }
}