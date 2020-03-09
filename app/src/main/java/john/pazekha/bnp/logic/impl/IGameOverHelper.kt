package john.pazekha.bnp.logic.impl

import john.pazekha.bnp.model.SYMBOL

interface IGameOverHelper {
    data class GameResult internal constructor (val isGameOver:Boolean, val winner: SYMBOL) {
        companion object Builder {
            fun gameGoesOn(): GameResult {
                return GameResult(false, SYMBOL.BLANK)
            }

            fun gameFinished(winner: SYMBOL): GameResult {
                return GameResult(true, winner)
            }
        }
    }

    fun checkGameOver(board: Array<Array<SYMBOL>>): GameResult
}
