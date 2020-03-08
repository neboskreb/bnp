package john.pazekha.bnp.logic.impl

import john.pazekha.bnp.model.SYMBOL
import john.pazekha.bnp.model.SYMBOL.*
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

    private val gameBoard: Array<Array<SYMBOL>> = Array(3) {Array(3) { BLANK } }


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
        @Param(converter = BoardConverter::class) board: Array<SYMBOL>,
        expectedGameOver: Boolean,
        expectedWinner: SYMBOL
    ) {
        /* ********  GIVEN ********* */
        setBoard(board)

        /* ********  WHEN ********* */
        val result = gameOverHelper.checkGameOver(gameBoard)

        /* ********  THEN ********* */
        assertEquals(expectedGameOver, result.isGameOver)
        assertEquals(expectedWinner,   result.winner)
    }

    private fun setBoard(cells: Array<SYMBOL>) {
        if (cells.size != 9) throw IllegalArgumentException()

        gameBoard[0][0] = cells[0]; gameBoard[1][0] = cells[1]; gameBoard[2][0] = cells[2]
        gameBoard[0][1] = cells[3]; gameBoard[1][1] = cells[4]; gameBoard[2][1] = cells[5]
        gameBoard[0][2] = cells[6]; gameBoard[1][2] = cells[7]; gameBoard[2][2] = cells[8]
    }
}