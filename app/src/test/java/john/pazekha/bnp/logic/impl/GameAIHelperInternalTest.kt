package john.pazekha.bnp.logic.impl

import john.pazekha.bnp.model.SYMBOL
import john.pazekha.bnp.model.SYMBOL.BLANK
import john.pazekha.bnp.model.SYMBOL.CROSS
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import junitparams.converters.Param
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(JUnitParamsRunner::class)
class GameAIHelperInternalTest {

    private lateinit var gameAiHelper: GameAIHelper

    @Before
    fun before() {
        gameAiHelper = GameAIHelper()
    }

    @Test
    fun testDeepCopy() {
        /* ******** GIVEN ********* */
        val input = arrayOf(
            arrayOf(BLANK,BLANK,BLANK),
            arrayOf(BLANK,BLANK,BLANK),
            arrayOf(BLANK,BLANK,BLANK))

        /* ********  WHEN ********* */
        val result = gameAiHelper.deepCopy(input)
        result[0][0] = CROSS
        result[1][1] = CROSS
        result[2][2] = CROSS

        /* ********  THEN ********* */
        assertEquals(BLANK, input[0][0])
        assertEquals(BLANK, input[1][1])
        assertEquals(BLANK, input[2][2])
    }

    @Parameters(
        // board,  expectedMoves

        // Start game
        // --------------------
        ". . ." +
        ". . ." +
        ". . .,    9",
        // --------------------
        "X . O" +
        ". X ." +
        "O . O,    4",
        // --------------------
        "X X O" +
        "O X O" +
        "O X O,    0"
        // --------------------
    )
    @Test
    fun testGetPossibleMoves(
        @Param(converter = BoardConverter::class) board: Array<Array<SYMBOL>>,
        expectedMoves: Int
        )
    {
        /* ********  WHEN ********* */
        val result = gameAiHelper.getPossibleMoves(board)

        /* ********  THEN ********* */
        assertEquals(expectedMoves, result.size)
    }
}