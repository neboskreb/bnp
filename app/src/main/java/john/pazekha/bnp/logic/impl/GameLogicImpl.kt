package john.pazekha.bnp.logic.impl

import john.pazekha.bnp.logic.IGameLogic
import john.pazekha.bnp.logic.IGameLogic.STATE
import john.pazekha.bnp.logic.IGameLogic.STATE.*
import john.pazekha.bnp.model.*
import john.pazekha.bnp.model.SYMBOL.*

class GameLogicImpl(
    private val gameOverHelper: IGameOverHelper,
    private val gameAIHelper: IGameAIHelper
) : IGameLogic {

    private val board = Array(3) { Array(3) { BLANK } }

    private var state: STATE = INITIAL
    private var userSymbol: SYMBOL = BLANK

    private lateinit var winner: WINNER

    override fun getState(): STATE {
        return state
    }

    override fun getSituation(): Situation {
        return Situation(board)
    }

    override fun setUserSymbol(symbol: SYMBOL) {
        userSymbol = symbol
    }

    override fun getUserSymbol(): SYMBOL {
        return userSymbol
    }

    override fun placeMove(move: Move) {
        val row = move.position.row
        val col = move.position.col
        if (board[row][col] != BLANK) throw IllegalStateException("Cell [$row][$col] is occupied")

        board[row][col] = move.symbol
        checkGameOver()
    }

    override fun calculateResponse(symbol: SYMBOL): Position {
        val response = gameAIHelper.calculateMoveFor(board, symbol)

        val row = response.row
        val col = response.col
        if (board[row][col] != BLANK) throw IllegalStateException("Computer tried to play on occupied cell [$row][$col]")

        board[row][col] = symbol
        state = WAITING_FOR_PLAYER
        checkGameOver()

        return response
    }

    override fun getWinner(): WINNER {
        return winner
    }

    override fun clear() {
        for (x in 0..2) {
            for (y in 0..2) {
                board[x][y] = BLANK
            }
        }
        state = INITIAL
        userSymbol = BLANK
    }

    private fun checkGameOver() {
        val gameResult = gameOverHelper.checkGameOver(board)
        if (gameResult.isGameOver) {
            state = FINISHED
            winner = when(gameResult.winner) {
                BLANK      -> WINNER.DRAW
                userSymbol -> WINNER.PLAYER
                else       -> WINNER.COMPUTER
            }
        }
    }
}