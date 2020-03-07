package john.pazekha.bnp.view.impl

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import john.pazekha.bnp.controller.IController
import john.pazekha.bnp.controller.IController.STATE
import john.pazekha.bnp.model.*
import john.pazekha.bnp.model.SYMBOL.*
import john.pazekha.bnp.view.IView

class MainViewModel : IView, ViewModel() {
    private lateinit var controller: IController

    val startEnabled = MutableLiveData(true)
    val stopEnabled  = MutableLiveData(true)
    val radioEnabled = MutableLiveData(true)
    var userSymbolCross = true
    var userSymbolDonut = false

    val moveEnabled = Array(3) { Array(3) { MutableLiveData(true) } }
    val moveSymbol  = Array(3) { Array(3) { MutableLiveData(BLANK) } }


    override fun setController(controller: IController) {
        this.controller = controller
    }

    override fun setState(state: STATE) {
        when(state) {
            STATE.BEFORE_GAME -> setBeforeGame()
            STATE.IN_GAME     -> setInGame()
            STATE.GAME_OVER   -> setGameOver()
        }
    }

    private fun setGameOver() {
        startEnabled.value = true
        stopEnabled.value  = false
        radioEnabled.value = true
        setMoveButtonsEnabled(false)
    }

    private fun setInGame() {
        startEnabled.value = false
        stopEnabled.value  = true
        radioEnabled.value = false
    }

    private fun setBeforeGame() {
        startEnabled.value = true
        stopEnabled.value  = false
        radioEnabled.value = true
        setMoveButtonsEnabled(false)
    }

    private fun setMoveButtonsEnabled(enabled: Boolean) {
        for (row in moveEnabled) {
            for (button in row) {
                button.value = enabled
            }
        }
    }

    override fun setSituation(situation: Situation) {
        for (x in 0..2) {
            for (y in 0..2) {
                val symbol = situation.getCell(x, y)
                setSymbol(x, y, symbol)
            }
        }
    }

    override fun setMove(move: Move) {
        setSymbol(move.position.row, move.position.col, move.symbol)
    }

    private fun setSymbol(row: Int, col: Int, symbol: SYMBOL) {
        moveSymbol[row][col].value = symbol
        moveEnabled[row][col].value = (symbol == BLANK)
    }

    override fun setGameResult(result: WINNER) {
        TODO("Not yet implemented")
    }

    fun onClickStart(view: View) {
        controller.onStartGame(getUserSymbol())
    }

    fun onClickStop(view: View) {
        controller.onStopGame()
    }

    fun onClickMove(row: Int, col: Int) {
        controller.onUserMove(Position(col, row))
    }


    private fun getUserSymbol(): SYMBOL {
        return when {
            userSymbolCross -> CROSS
            userSymbolDonut -> DONUT
            else -> BLANK
        }
    }
}
