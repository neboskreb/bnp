package john.pazekha.bnp.view

import john.pazekha.bnp.controller.IController
import john.pazekha.bnp.model.Move
import john.pazekha.bnp.model.Situation
import john.pazekha.bnp.model.WINNER

interface IView {
    fun setController(controller: IController)

    fun setState(state: IController.STATE)
    fun setSituation(situation: Situation)
    fun setMove(move: Move)
    fun setGameResult(result: WINNER)
}