package john.pazekha.bnp.controller.impl

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import john.pazekha.bnp.controller.IController
import john.pazekha.bnp.logic.IGameLogic
import john.pazekha.bnp.model.*
import john.pazekha.bnp.view.IView
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ControllerImplTest {

    @Mock
    private lateinit var mockView: IView

    @Mock
    private lateinit var mockLogic: IGameLogic

    private lateinit var controller: ControllerImpl

    @Before
    fun before() {
        controller = ControllerImpl()
    }

    @Test
    fun resumeInitial() {
        /* ********  GIVEN ********* */
        whenever(mockLogic.getState()).thenReturn(IGameLogic.STATE.INITIAL)

        /* ********  WHEN ********* */
        controller.setView(mockView)
        controller.setLogic(mockLogic)
        controller.resume()

        /* ********  THEN ********* */
        verify(mockView).setState(IController.STATE.BEFORE_GAME)
        verify(mockView).setSituation(any())
    }

    @Test
    fun resumePlayersMove() {
        /* ********  GIVEN ********* */
        val array = Array(3) {Array(3) { SYMBOL.BLANK} }
        val mockSituation = Situation(array)
        whenever(mockLogic.getState()).thenReturn(IGameLogic.STATE.WAITING_FOR_PLAYER)
        whenever(mockLogic.getSituation()).thenReturn(mockSituation)
        whenever(mockLogic.getUserSymbol()).thenReturn(SYMBOL.CROSS)

        /* ********  WHEN ********* */
        controller.setView(mockView)
        controller.setLogic(mockLogic)
        controller.resume()

        /* ********  THEN ********* */
        verify(mockView).setState(IController.STATE.IN_GAME)
        verify(mockView).setSituation(mockSituation)
    }

    @Test
    fun resumeGameOver() {
        /* ********  GIVEN ********* */
        whenever(mockLogic.getState()).thenReturn(IGameLogic.STATE.FINISHED)
        whenever(mockLogic.getWinner()).thenReturn(WINNER.PLAYER)

        /* ********  WHEN ********* */
        controller.setView(mockView)
        controller.setLogic(mockLogic)
        controller.resume()

        /* ********  THEN ********* */
        verify(mockView).setState(IController.STATE.GAME_OVER)
        verify(mockView).setGameResult(WINNER.PLAYER)
    }

    @Test
    fun startGameCross() {
        /* ********  GIVEN ********* */
        whenever(mockLogic.getState()).thenReturn(IGameLogic.STATE.INITIAL)
        controller.setView(mockView)
        controller.setLogic(mockLogic)
        controller.resume()
        verify(mockView).setState(IController.STATE.BEFORE_GAME)

        /* ********  WHEN ********* */
        controller.onStartGame(SYMBOL.CROSS)

        /* ********  THEN ********* */
        verify(mockLogic).setUserSymbol(SYMBOL.CROSS)
        verify(mockView).setState(IController.STATE.IN_GAME)
        verify(mockLogic).clear()
    }

    @Test
    fun startGameDonut() {
        /* ********  GIVEN ********* */
        whenever(mockLogic.getState()).thenReturn(IGameLogic.STATE.INITIAL)
        whenever(mockLogic.calculateResponse(any())).thenReturn(Position(1,1))

        controller.setView(mockView)
        controller.setLogic(mockLogic)
        controller.resume()
        verify(mockView).setState(IController.STATE.BEFORE_GAME)

        /* ********  WHEN ********* */
        controller.onStartGame(SYMBOL.DONUT)

        /* ********  THEN ********* */
        verify(mockView).setState(IController.STATE.IN_GAME)
        verify(mockLogic).clear()
        verify(mockLogic).setUserSymbol(SYMBOL.DONUT)
        verify(mockLogic).calculateResponse(SYMBOL.CROSS)
        verify(mockView).setMove(any())
    }

    @Test
    fun userMove() {
        /* ********  GIVEN ********* */
        whenever(mockLogic.getState()).thenReturn(IGameLogic.STATE.WAITING_FOR_PLAYER)
        whenever(mockLogic.getUserSymbol()).thenReturn(SYMBOL.CROSS)
        whenever(mockLogic.calculateResponse(any())).thenReturn(Position(1, 1))

        controller.setView(mockView)
        controller.setLogic(mockLogic)
        controller.resume()
        verify(mockView).setState(IController.STATE.IN_GAME)

        /* ********  WHEN ********* */
        controller.onUserMove(Position(0, 0))

        /* ********  THEN ********* */
        verify(mockView).setMove(Move(SYMBOL.CROSS, Position(0, 0)))
        verify(mockLogic).placeMove(Move(SYMBOL.CROSS, Position(0, 0)))
        verify(mockLogic).calculateResponse(SYMBOL.DONUT)
        verify(mockView).setMove(Move(SYMBOL.DONUT, Position(1, 1)))
    }

    @Test
    fun gameOverUserWins() {
        /* ********  GIVEN ********* */
        whenever(mockLogic.getState()).thenReturn(IGameLogic.STATE.WAITING_FOR_PLAYER)
        whenever(mockLogic.getUserSymbol()).thenReturn(SYMBOL.CROSS)

        controller.setView(mockView)
        controller.setLogic(mockLogic)
        controller.resume()
        verify(mockView).setState(IController.STATE.IN_GAME)

        whenever(mockLogic.getState()).thenReturn(IGameLogic.STATE.FINISHED)
        whenever(mockLogic.getWinner()).thenReturn(WINNER.PLAYER)

        /* ********  WHEN ********* */
        controller.onUserMove(Position(0, 0))

        /* ********  THEN ********* */
        verify(mockLogic).placeMove(Move(SYMBOL.CROSS, Position(0, 0)))
        verify(mockView).setState(IController.STATE.GAME_OVER)
        verify(mockView).setGameResult(WINNER.PLAYER)
    }

    @Test
    fun gameOverComputerWins() {
        /* ********  GIVEN ********* */
        whenever(mockLogic.getState()).thenReturn(IGameLogic.STATE.WAITING_FOR_PLAYER)
        whenever(mockLogic.getUserSymbol()).thenReturn(SYMBOL.CROSS)

        controller.setView(mockView)
        controller.setLogic(mockLogic)
        controller.resume()
        verify(mockView).setState(IController.STATE.IN_GAME)

        whenever(mockLogic.calculateResponse(any())).thenReturn(Position(1, 1))
        whenever(mockLogic.getState()).thenReturn(IGameLogic.STATE.WAITING_FOR_PLAYER, IGameLogic.STATE.FINISHED)
        whenever(mockLogic.getWinner()).thenReturn(WINNER.COMPUTER)

        /* ********  WHEN ********* */
        controller.onUserMove(Position(0, 0))

        /* ********  THEN ********* */
        verify(mockLogic).placeMove(Move(SYMBOL.CROSS, Position(0, 0)))
        verify(mockView).setState(IController.STATE.GAME_OVER)
        verify(mockView).setGameResult(WINNER.COMPUTER)
    }

    @Test
    fun stopGame() {
        /* ********  GIVEN ********* */
        val array = Array(3) {Array(3) { SYMBOL.BLANK} }
        val mockSituation = Situation(array)
        whenever(mockLogic.getState()).thenReturn(IGameLogic.STATE.WAITING_FOR_PLAYER)
        whenever(mockLogic.getSituation()).thenReturn(mockSituation)
        whenever(mockLogic.getUserSymbol()).thenReturn(SYMBOL.CROSS)

        controller.setView(mockView)
        controller.setLogic(mockLogic)
        controller.resume()
        verify(mockView).setState(IController.STATE.IN_GAME)

        /* ********  WHEN ********* */
        controller.onStopGame()

        /* ********  THEN ********* */
        verify(mockView).setState(IController.STATE.BEFORE_GAME)
    }
}
