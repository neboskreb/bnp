package john.pazekha.bnp.controller.impl

import john.pazekha.bnp.controller.IController
import john.pazekha.bnp.controller.IController.STATE.*
import john.pazekha.bnp.logic.IGameLogic
import john.pazekha.bnp.model.Move
import john.pazekha.bnp.model.Position
import john.pazekha.bnp.model.SYMBOL
import john.pazekha.bnp.model.SYMBOL.*
import john.pazekha.bnp.model.Situation
import john.pazekha.bnp.view.IView

class ControllerImpl : IController {

    private val EMPTY_SITUATION = Situation(Array(3) { Array(3) { BLANK } })
    private lateinit var view: IView

    private lateinit var logic: IGameLogic
    private lateinit var state: IController.STATE
    private lateinit var userSymbol: SYMBOL
    private lateinit var computerSymbol: SYMBOL

    override fun setView(viewModel: IView) {
        this.view = viewModel
    }

    override fun setLogic(logic: IGameLogic) {
        this.logic = logic
    }

    override fun resume() {
        when (logic.getState()) {
            IGameLogic.STATE.INITIAL -> {
                state = BEFORE_GAME
                view.setState(state)
                view.setSituation(EMPTY_SITUATION)
            }

            IGameLogic.STATE.WAITING_FOR_PLAYER -> {
                state = IN_GAME
                userSymbol = logic.getUserSymbol()
                computerSymbol = opposite(userSymbol)
                view.setState(state)
                view.setSituation(logic.getSituation())
            }

            IGameLogic.STATE.CALCULATING_RESPONSE -> {
                throw IllegalStateException("Not to be reachable in current implementation")
            }

            IGameLogic.STATE.FINISHED -> {
                state = GAME_OVER
                view.setState(state)
                view.setGameResult(logic.getWinner())
            }
        }
    }

    private fun opposite(userSymbol: SYMBOL): SYMBOL {
        return when (userSymbol) {
            DONUT -> CROSS
            CROSS -> DONUT
            else -> throw IllegalArgumentException()
        }
    }

    override fun onStartGame(symbol: SYMBOL) {
        if (state != BEFORE_GAME) throw IllegalStateException("A game is already in progress")

        state = IN_GAME
        view.setState(state)

        logic.clear()
        logic.setUserSymbol(symbol)

        userSymbol = symbol
        computerSymbol = opposite(userSymbol)

        if (userSymbol == CROSS) {
            // User's move
        } else {
            val response = logic.calculateResponse(computerSymbol)
            view.setMove(Move(computerSymbol, response))
        }
    }

    override fun onUserMove(position: Position) {
        if (state != IN_GAME) throw IllegalStateException("No game in progress")

        val userMove = Move(userSymbol, position)
        view.setMove(userMove)
        logic.placeMove(userMove)
        if (checkGameOver()) {
            return
        }

        val response = logic.calculateResponse(computerSymbol)
        view.setMove(Move(computerSymbol, response))
        if (checkGameOver()) {
            return
        }
    }

    private fun checkGameOver(): Boolean {
        if (logic.getState() == IGameLogic.STATE.FINISHED) {
            state = GAME_OVER
            view.setState(state)
            view.setGameResult(logic.getWinner())

            state = BEFORE_GAME
            return true
        } else {
            return false
        }
    }

    override fun onStopGame() {
        if (state != IN_GAME) throw IllegalStateException("No game in progress")

        logic.clear()
        state = BEFORE_GAME
        view.setState(state)
    }
}