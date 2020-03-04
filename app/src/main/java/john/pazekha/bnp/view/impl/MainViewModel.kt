package john.pazekha.bnp.view.impl

import androidx.lifecycle.ViewModel
import john.pazekha.bnp.controller.IController
import john.pazekha.bnp.controller.IController.STATE
import john.pazekha.bnp.model.Move
import john.pazekha.bnp.model.Situation
import john.pazekha.bnp.model.WINNER
import john.pazekha.bnp.view.IView

class MainViewModel : IView, ViewModel() {
    // TODO: Implement the ViewModel

    override fun setController(controller: IController) {

    }

    override fun setState(state: STATE) {
        TODO("Not yet implemented")
    }

    override fun setSituation(situation: Situation) {
        TODO("Not yet implemented")
    }

    override fun setMove(move: Move) {
        TODO("Not yet implemented")
    }

    override fun setGameResult(result: WINNER) {
        TODO("Not yet implemented")
    }
}
