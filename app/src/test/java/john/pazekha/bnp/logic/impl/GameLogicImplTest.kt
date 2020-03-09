package john.pazekha.bnp.logic.impl

import com.nhaarman.mockitokotlin2.*
import john.pazekha.bnp.logic.IGameLogic
import john.pazekha.bnp.logic.impl.IGameOverHelper.GameResult
import john.pazekha.bnp.model.Move
import john.pazekha.bnp.model.Position
import john.pazekha.bnp.model.SYMBOL.*
import john.pazekha.bnp.model.WINNER
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GameLogicImplTest {
    @Mock
    lateinit var mockGameOverHelper: IGameOverHelper
    @Mock
    lateinit var mockGameAIHelper: IGameAIHelper

    @InjectMocks
    lateinit var logic: GameLogicImpl

    @Test
    fun testPlaceMove() {
        /* ********  GIVEN ********* */
        whenever(mockGameOverHelper.checkGameOver(any())).thenReturn(GameResult.gameGoesOn())

        /* ********  WHEN ********* */
        logic.placeMove(Move(CROSS, Position(1, 1)))

        /* ********  THEN ********* */
        verify(mockGameOverHelper, times(1)).checkGameOver(any())
    }

    @Test
    fun testCalculateResponse() {
        /* ********  GIVEN ********* */
        whenever(mockGameAIHelper.calculateMoveFor(any(), any())).thenReturn(Position(0, 0))
        whenever(mockGameOverHelper.checkGameOver(any())).thenReturn(GameResult.gameGoesOn())

        /* ********  WHEN ********* */
        logic.calculateResponse(CROSS)

        /* ********  THEN ********* */
        verify(mockGameAIHelper, times(1)).calculateMoveFor(any(), eq(CROSS))
        verify(mockGameOverHelper, times(1)).checkGameOver(any())
    }

    @Test
    fun testGetSituation() {
        /* ********  GIVEN ********* */
        whenever(mockGameOverHelper.checkGameOver(any())).thenReturn(GameResult.gameGoesOn())

        logic.clear()
        logic.placeMove(Move(CROSS, Position(1, 1)))

        /* ********  WHEN ********* */
        val result = logic.getSituation()

        /* ********  THEN ********* */
        assertEquals(CROSS, result.getCell(1, 1))
    }

    @Test
    fun testStateInitial() {
        /* ********  GIVEN ********* */
        logic.clear()

        /* ********  WHEN ********* */
        val result = logic.getState()

        /* ********  THEN ********* */
        assertEquals(IGameLogic.STATE.INITIAL, result)
    }

    @Test
    fun testStateInGame() {
        /* ********  GIVEN ********* */
        whenever(mockGameAIHelper.calculateMoveFor(any(), any())).thenReturn(Position(0, 0))
        whenever(mockGameOverHelper.checkGameOver(any())).thenReturn(GameResult.gameGoesOn())
        logic.clear()

        /* ********  WHEN ********* */
        logic.placeMove(Move(CROSS, Position(1, 1)))
        logic.calculateResponse(DONUT)
        val result = logic.getState()

        /* ********  THEN ********* */
        assertEquals(IGameLogic.STATE.WAITING_FOR_PLAYER, result)
    }

    @Test
    fun testStateGameOverComputerWins() {
        /* ********  GIVEN ********* */
        whenever(mockGameAIHelper.calculateMoveFor(any(), any())).thenReturn(Position(0, 0))
        whenever(mockGameOverHelper.checkGameOver(any())).thenReturn(
            GameResult.gameGoesOn(),
            GameResult.gameFinished(DONUT))
        logic.clear()
        logic.setUserSymbol(CROSS)

        /* ********  WHEN ********* */
        logic.placeMove(Move(CROSS, Position(1, 1)))
        logic.calculateResponse(DONUT)
        val result = logic.getState()
        val winner = logic.getWinner()

        /* ********  THEN ********* */
        assertEquals(IGameLogic.STATE.FINISHED, result)
        assertEquals(WINNER.COMPUTER, winner)
    }

    @Test
    fun testStateGameOverDraw() {
        /* ********  GIVEN ********* */
        whenever(mockGameAIHelper.calculateMoveFor(any(), any())).thenReturn(Position(0, 0))
        whenever(mockGameOverHelper.checkGameOver(any())).thenReturn(
            GameResult.gameGoesOn(),
            GameResult.gameFinished(BLANK))
        logic.clear()
        logic.setUserSymbol(CROSS)

        /* ********  WHEN ********* */
        logic.placeMove(Move(CROSS, Position(1, 1)))
        logic.calculateResponse(DONUT)
        val result = logic.getState()
        val winner = logic.getWinner()

        /* ********  THEN ********* */
        assertEquals(IGameLogic.STATE.FINISHED, result)
        assertEquals(WINNER.DRAW, winner)
    }
}