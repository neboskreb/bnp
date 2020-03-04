package john.pazekha.bnp.controller

import john.pazekha.bnp.logic.IGameLogic
import john.pazekha.bnp.model.Position
import john.pazekha.bnp.model.SYMBOL
import john.pazekha.bnp.view.IView

interface IController {
    enum class STATE {
        BEFORE_GAME, IN_GAME, GAME_OVER
    }

    fun setView(viewModel: IView)
    fun setLogic(logic: IGameLogic)
    fun resume()

    fun onStartGame(symbol: SYMBOL)
    fun onUserMove(position: Position)
    fun onStopGame()
}