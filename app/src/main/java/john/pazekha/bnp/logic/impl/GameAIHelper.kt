package john.pazekha.bnp.logic.impl

import john.pazekha.bnp.model.Position
import john.pazekha.bnp.model.SYMBOL
import john.pazekha.bnp.model.SYMBOL.*

/**
 * Implementation based on this white paper:
 * https://towardsdatascience.com/tic-tac-toe-creating-unbeatable-ai-with-minimax-algorithm-8af9e52c1e7d
 */
class GameAIHelper: IGameAIHelper {
    private val DUMMY_POSITION = Position(-1, -1)
    private val PLAYER_WINS   = BestMove(1, DUMMY_POSITION)
    private val OPPONENT_WINS = BestMove(-1, DUMMY_POSITION)
    private val GAME_DRAW     = BestMove(0, DUMMY_POSITION)

    private val gameOverHelper = GameOverHelper()

    override fun calculateMoveFor(board: Array<Array<SYMBOL>>, symbol: SYMBOL): Position {
        return recursiveCalculate(board, symbol).position
    }

    private fun recursiveCalculate(board: Array<Array<SYMBOL>>, player: SYMBOL) : BestMove {
        val winner : SYMBOL? = getWinner(board)
        if (winner != null) {
            return if (winner == player) {
                PLAYER_WINS
            } else {
                OPPONENT_WINS
            }
        }

        val moves = getPossibleMoves(board)
        if (moves.isEmpty()) {
            return GAME_DRAW
        }

        var bestMove = DUMMY_POSITION
        var bestScore = -2

        val opponent = opponentFor(player)
        for (position in moves) {
            val newBoard = deepCopy(board)
            newBoard[position.row][position.col] = player
            val opponentScore = recursiveCalculate(newBoard, opponent).score
            // Count negative score for opponent
            val score = -opponentScore
            if (score > bestScore) {
                bestScore = score
                bestMove = position
            }
        }

        return BestMove(bestScore, bestMove)
    }

    private fun getWinner(board: Array<Array<SYMBOL>>): SYMBOL? {
        val result = gameOverHelper.checkGameOver(board)
        if (!result.isGameOver) {
            return null
        }
        if (result.winner == BLANK) {
            return null
        }
        return result.winner
    }

    private fun opponentFor(player: SYMBOL): SYMBOL {
        return when (player) {
            CROSS -> DONUT
            DONUT -> CROSS
            else -> throw IllegalArgumentException()
        }
    }


    internal fun getPossibleMoves(board: Array<Array<SYMBOL>>): List<Position> {
        val result = ArrayList<Position>()
        for (row in board.indices) {
            for (col in board[row].indices) {
                if (board[row][col] == BLANK) {
                    result.add(Position(row, col))
                }
            }
        }
        return result
    }

    internal fun deepCopy(board: Array<Array<SYMBOL>>): Array<Array<SYMBOL>> {
        val result = board.copyOf()
        for (index in result.indices) {
            result[index] = result[index].copyOf()
        }
        return result
    }
}

data class BestMove(val score: Int, val position: Position)