package john.pazekha.bnp.logic

import john.pazekha.bnp.model.*

interface IGameLogic {
    enum class STATE {
        INITIAL, WAITING_FOR_PLAYER, CALCULATING_RESPONSE, FINISHED
    }

    fun getState(): STATE
    fun getSituation(): Situation
    fun setUserSymbol(donut: SYMBOL)
    fun getUserSymbol(): SYMBOL
    fun placeMove(move: Move)
    fun calculateResponse(symbol: SYMBOL): Position
    fun getWinner(): WINNER
    fun clear()
}