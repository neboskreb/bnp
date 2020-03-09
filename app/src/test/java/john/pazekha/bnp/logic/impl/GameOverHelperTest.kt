package john.pazekha.bnp.logic.impl

import john.pazekha.bnp.model.SYMBOL
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import junitparams.converters.Param
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith


@RunWith(JUnitParamsRunner::class)
class GameOverHelperTest {

    private lateinit var gameOverHelper: GameOverHelper


    @Before
    fun before() {
        gameOverHelper = GameOverHelper()
    }

    @Parameters(
        // board, expectedGameOver, expectedWinner
        "X . O" +
        ". X O" +
        ". . X, true, CROSS",
        // --------------------
        "X . O" +
        ". X O" +
        ". . O, true, DONUT",
        // --------------------
        "X X O" +
        "O O X" +
        "X O O, true, BLANK",
        // --------------------
        "O . X" +
        "O X ." +
        "X . ., true, CROSS",
        // --------------------
        "O . X" +
        "O X ." +
        "O . ., true, DONUT",
        // --------------------
        "O O X" +
        "X X O" +
        "O X X, true, BLANK",

        // --------------------
        ". . ." +
        ". . ." +
        ". . ., false, BLANK",
        // --------------------
        "O . X" +
        "O X ." +
        ". . ., false, BLANK",
        // --------------------
        "O O X" +
        "X X O" +
        "O X ., false, BLANK"
    )
    @Test
    fun checkGameOver(
        @Param(converter = BoardConverter::class) board: Array<Array<SYMBOL>>,
        expectedGameOver: Boolean,
        expectedWinner: SYMBOL
    ) {
        /* ********  WHEN ********* */
        val result = gameOverHelper.checkGameOver(board)

        /* ********  THEN ********* */
        assertEquals(expectedGameOver, result.isGameOver)
        assertEquals(expectedWinner,   result.winner)
    }
}